package com.chanshiguan.yumeng;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.bumptech.glide.Glide;
import com.chanshiguan.yumeng.Adapter.NewsItemAdapter;
import com.chanshiguan.yumeng.Bean.MainNewsBean;
import com.leon.lib.settingview.LSettingItem;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class MainFragment extends Fragment implements View.OnClickListener,OnBannerListener {
    private Bundle bundle;
    private TextView MainViewUserName,webView;
    private ImageView MainViewHeadView,FoodItem,HospitalItem;
    private RecyclerView NewsRecyclerView;
    private LSettingItem LocationLSetting;

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    private String UserName;
    private String url="/MainNews.php";
    private static final String TAG = "MainFragment";
    private static final String BUNDLE_CONTEXT= "context";

    private RequestQueue queue;//声明请求队列
    private MyRequest<MainNewsBean> request;//声明自定义请求类
    private NewsItemAdapter adapter;//声明自定义适配器
    private List<MainNewsBean.NewsItemBean> list = new ArrayList<>();

    public LocationClient mLocationClient;
    private Banner mBanner;
    private MyImageLoader mMyImageLoader;
    private ArrayList<String> imagePath;
    private ArrayList<String> imageTitle;

    public static MainFragment newInstance(String context){
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_CONTEXT,context);

        MainFragment mainFragment = new MainFragment();
        mainFragment.setArguments(bundle);
        return mainFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment,container,false);
        bundle = getArguments();
        if(getActivity()!=null){
        mLocationClient = new LocationClient(getActivity().getApplicationContext());
        mLocationClient.registerLocationListener(new MyLocationListener());}
        //初始化控件
        MainViewUserName = view.findViewById(R.id.user_name);
        MainViewHeadView = view.findViewById(R.id.headimage);
        FoodItem = view.findViewById(R.id.item3);
        HospitalItem =view.findViewById(R.id.item1);
        NewsRecyclerView = view.findViewById(R.id.NewsRecyclerView);
        LocationLSetting = view.findViewById(R.id.item_location);
        int resId = R.anim.layout_animation_fall_down;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getActivity(), resId);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        NewsRecyclerView.setLayoutManager(linearLayoutManager);
        NewsRecyclerView.setLayoutAnimation(animation);

        HospitalItem.setOnClickListener(this);
        FoodItem.setOnClickListener(this);
        url = getString(R.string.server)+url;

        initData();
        initView(view);

        startTask();

        List<String> permissionList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.
                permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.
                permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.
                permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!permissionList.isEmpty()) {
            String [] permissions = permissionList.toArray(new String[permissionList.
                    size()]);
            ActivityCompat.requestPermissions(getActivity(), permissions, 1);
        } else {
            requestLocation();
        }

        return view;
    }
    public void onClick(View v){
        switch (v.getId()){
            case R.id.item3:startActivity(new Intent(getActivity(),FoodItemActivity.class));
            break;
            case R.id.item1:startActivity(new Intent(getActivity(),PetHospitalActivity.class));
            break;
            default:
                break;
        }

    }
    @Override
    public void onStart() {
        super.onStart();
        //获取本地登录状态
        pref = getActivity().getSharedPreferences("LoginMessage",MODE_PRIVATE);
        UserName = pref.getString("NakeName","请先登录");
        Log.i(TAG,"user name:"+UserName);
        MainViewUserName.setText(UserName);
        //申请权限，如果有权限则加载头像
        if(pref.getBoolean("isLunched",false)) {
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.
                    permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new
                        String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            } else {
                try {
                    //检查文件，绘制头像
                    File file = new File("/sdcard/yumeng/");
                    if (file.exists()) {
                        FileInputStream fis = new FileInputStream("/sdcard/yumeng/head"+UserName+".png");
                        Bitmap bitmap = BitmapFactory.decodeStream(fis);
                        MainViewHeadView.setImageBitmap(bitmap);
                    }
                } catch (IOException e) {

                }
                Log.d(TAG, "get headimage");
            }
        }
    }
    private void startTask() {

        //用Volley中静态方法初始化queue对象
        queue = Volley.newRequestQueue(getActivity());

        //创建request对象，
        //参数分别为自定义MyRequest构造方法中参数
        request = new MyRequest<MainNewsBean>(url, MainNewsBean.class,
                new Response.Listener<MainNewsBean>() {
                    @Override
                    public void onResponse(MainNewsBean response) {

                        //通过接口回调得到返回回来的数据
                        if(response!=null){

                            //如果不为空用本地list接收
                            list.addAll(response.getNewsItem());

                            //数据一旦回调成功初始化数据源和适配器
                            initAdapter();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i(TAG, "onErrorResponse: 访问失败");
                    }
                });
        //将request对象加入到请求队列queue中进行下载数据
        queue.add(request);
    }
    private void initAdapter() {

        //创建自定义适配器对象
        adapter = new NewsItemAdapter(getActivity(),list);

        //设置recyclerview适配器
        NewsRecyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new NewsItemAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, Bundle data) {
                //DO your fucking bussiness here!
                Intent intent =new Intent();
                data.putString("UserName",bundle.getString(BUNDLE_CONTEXT));
                intent.putExtras(data);
                intent.setClass(getActivity(),WebViewActivity.class);
                startActivity(intent);
            }
            @Override
            public void onItemLongClick(View view, Bundle data) {
            }
        });
        //刷新适配器
        adapter.notifyDataSetChanged();
    }

    public class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            if(getActivity()==null)return;
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    StringBuilder currentPosition = new StringBuilder();
                    currentPosition.append(location.getCity()).append(".");
                    currentPosition.append(location.getDistrict()).append(".");
                    currentPosition.append(location.getStreet()).append(" ");
                    LocationLSetting.setLeftText(currentPosition.toString());
                }
            });
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    for (int result : grantResults) {
                        if (result != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(getActivity(), "必须同意所有权限才能使用本程序",
                                    Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    requestLocation();
                } else {
                    Toast.makeText(getActivity(), "发生未知错误", Toast.LENGTH_SHORT).show();
                    getActivity().finish();
                }
                break;
            default:
        }
    }
    private void requestLocation() {
        initLocation();
        mLocationClient.start();
    }
    private void initLocation(){
        LocationClientOption option = new LocationClientOption();
        option.setScanSpan(5000);
        option.setIsNeedAddress(true);
        mLocationClient.setLocOption(option);
    }

    private void initData() {
        imagePath = new ArrayList<>();
        imageTitle = new ArrayList<>();
        imagePath.add(getString(R.string.server)+"/BannerImage/animalcity.jpg");
        imagePath.add(getString(R.string.server)+"/BannerImage/animalhall.jpg");
        imagePath.add(getString(R.string.server)+"/BannerImage/catfood.jpg");
        imageTitle.add("爱宠大机密热映");
        imageTitle.add("我与爱宠的美好时光");
        imageTitle.add("精品猫粮，不容错过");
    }

    /**初始化轮播视图
     * @param view
     */
    private void initView(View view) {
        mMyImageLoader = new MyImageLoader();
        mBanner = view.findViewById(R.id.banner);
        //设置样式，里面有很多种样式可以自己都看看效果
        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
        //设置图片加载器
        mBanner.setImageLoader(mMyImageLoader);
        //设置轮播的动画效果,里面有很多种特效,可以都看看效果。
        mBanner.setBannerAnimation(Transformer.ZoomOutSlide);
        //轮播图片的文字
        mBanner.setBannerTitles(imageTitle);
        //设置轮播间隔时间
        mBanner.setDelayTime(4000);
        //设置是否为自动轮播，默认是true
        mBanner.isAutoPlay(true);
        //设置指示器的位置，小点点，居中显示
        mBanner.setIndicatorGravity(BannerConfig.CENTER);
        //设置图片加载地址
        mBanner.setImages(imagePath)
                //轮播图的监听
                .setOnBannerListener(this)
                //开始调用的方法，启动轮播图。
                .start();

    }
    /**
     * 轮播图的监听
     *
     * @param position
     */
    @Override
    public void OnBannerClick(int position) {
        switch (position){
            case 0:
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://baike.baidu.com/item/%E7%88%B1%E5%AE%A0%E5%A4%A7%E6%9C%BA%E5%AF%86/18834383?fr=aladdin"));
                startActivity(intent);
                break;
            case 1:
                getActivity().startActivity(new Intent(getActivity(),CollectionActivity.class));
                break;
            case 2:startActivity(new Intent(getActivity(),FoodItemActivity.class));
                break;
            default:
                break;
        }

    }
    /**
     * 图片加载类
     */
    private class MyImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(context.getApplicationContext())
                    .load(path)
                    .into(imageView);
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mLocationClient.stop();
    }
}


