package com.chanshiguan.yumeng;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.chanshiguan.yumeng.Bean.FoodDetailBean;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;
import java.util.List;

public class ItemDetailFragmentDetail extends Fragment implements View.OnClickListener{
    private Bundle ItemData;
    //五张详细信息图片
    private ImageView[] ItemDetailImage= new ImageView[5];
    //详细项目的ID
    private String ItemId;
    //请求图片的URL
    private String url;
    //请求队列
    private RequestQueue queue;
    //相信信息图片数量
    private int ImageNumber;
    //信息图片URLBEANList
    private List<FoodDetailBean.ImageUrlBean> ImageUrls = new ArrayList<>();

    //声明自定义请求类
    private MyRequest<FoodDetailBean> request;
    private static final String TAG = "ItemDetailFragmentDetai";

    //声明一个lmageloader引用
    private ImageLoader loader;

    //声明image的设置属性引用
    private DisplayImageOptions dios;

    public static ItemDetailFragmentDetail newInstance(Bundle ItemData){

        ItemDetailFragmentDetail itemDetailFragmentDetail = new ItemDetailFragmentDetail();
        itemDetailFragmentDetail.setArguments(ItemData);
        return itemDetailFragmentDetail;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_detail_detail,container,false);
        //取得项目数据
        ItemData=getArguments();
        ItemId=ItemData.getString("ItemId");

        url = getString(R.string.server)+"/ItemDetailImage.php?Ip="+getString(R.string.server)+"&Id=";
        Log.i(TAG,url);
        url=url+ItemId;
        Log.i(TAG,url);
        //开启网络数据下载
        startTask();
        //初始化图片加载工具
        initimageloader(getActivity());
        //绑定视图
       initimageview(view);

        return view;
    }
    public void onClick(View v){

    }

    /**
     * @param v
     * 最多五张详细信息
     */
    private void initimageview(View v){
        ItemDetailImage[0]=v.findViewById(R.id.ItemDetailImage01);
        ItemDetailImage[1]=v.findViewById(R.id.ItemDetailImage02);
        ItemDetailImage[2]=v.findViewById(R.id.ItemDetailImage03);
        ItemDetailImage[3]=v.findViewById(R.id.ItemDetailImage04);
        ItemDetailImage[4]=v.findViewById(R.id.ItemDetailImage05);

    }

    /**
     * 向ImageView加载网络图片
     */
    private void LoadeImage(){
        for(int i=0;i<ImageNumber;i++){
            Log.i(TAG,""+i);
            Log.i(TAG,ImageUrls.get(i).getDetailImageUrl());
            loader.displayImage(ImageUrls.get(i).getDetailImageUrl(),ItemDetailImage[i],dios);
        }
    }

    /**
     * @param context
     * 初始化loader
     */
    private void initimageloader(Context context){
        //用imagerloader中静态方法创建对象
        loader = ImageLoader.getInstance();

        //初始化loader对象的配置信息
        loader.init(ImageLoaderConfiguration.createDefault(context));

        //创建image属性的配置信息
        dios = new DisplayImageOptions.Builder()
                //下载失败显示的图片
                .showImageOnFail(R.mipmap.ic_launcher)
                //正在下载中显示的图片
                .showImageOnLoading(R.mipmap.ic_launcher)
                //图片的配置设置
                .bitmapConfig(Bitmap.Config.RGB_565)
                //是否有运行时缓存
                .cacheInMemory(true)
                //是否有本地缓存
                .cacheOnDisk(true)
                //创建
                .build();
    }
    //网络下载数据
    private void startTask() {

        //用Volley中静态方法初始化queue对象
        queue = Volley.newRequestQueue(getActivity());

        //创建request对象，
        //参数分别为自定义MyRequest构造方法中参数
        request = new MyRequest<FoodDetailBean>(url, FoodDetailBean.class,
                new Response.Listener<FoodDetailBean>() {
                    @Override
                    public void onResponse(FoodDetailBean response) {

                        //通过接口回调得到返回回来的数据
                        if(response!=null){
                            ImageNumber=response.getImageNumber();
                            //如果不为空用本地list接收
                            ImageUrls.addAll(response.getImageUrl());
                            //数据一旦回调成功初始化数据源和适配器
                            Log.i(TAG,""+ImageNumber);
                            Log.i(TAG,response.getImageUrl().get(0).getDetailImageUrl());
                            Log.i(TAG,""+ImageNumber);
                            LoadeImage();
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

}


