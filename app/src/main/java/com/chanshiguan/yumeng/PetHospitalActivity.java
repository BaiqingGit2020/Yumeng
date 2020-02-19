package com.chanshiguan.yumeng;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.chanshiguan.yumeng.Adapter.HospitalAdapter;
import com.chanshiguan.yumeng.Bean.HospitalBean;

import java.util.ArrayList;
import java.util.List;

public class PetHospitalActivity extends AppCompatActivity {


    //log标签
    private static final String TAG = "===";

    //声明recyclerview引用
    private RecyclerView PetHospitalRecyclerView;

    //获取的json数据中的数据集合
    private List<HospitalBean.HospitalItemBean> list = new ArrayList<>();

    //声明请求队列
    private RequestQueue queue;

    //声明自定义请求类
    private MyRequest<HospitalBean> request;

    //声明自定义适配器
    private HospitalAdapter adapter;

    private SwipeRefreshLayout swipeRefresh;

    //用插件自动生成初始化view代码的方法
    private void assignViews() {
        PetHospitalRecyclerView = (RecyclerView) findViewById(R.id.PetHospitalRecyclerView);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.PetHospitalTab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    //网络请求数据的网址
    private String url ="/HospitalItem.php?Ip=";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_hospital);
//        完善网址信息
        url=getString(R.string.server)+url+getString(R.string.server);
        Log.i(TAG, url);
        //初始化控件
        assignViews();

        //设置recyclerview要实现的类型为StaggeredGrid瀑布流类型
        //并再构造方法中指定列数2，纵向排列
        int resId = R.anim.layout_animation_fall_down;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(this, resId);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        PetHospitalRecyclerView.setLayoutManager(linearLayoutManager);
        PetHospitalRecyclerView.setLayoutAnimation(animation);
        swipeRefresh = findViewById(R.id.PetHospitalSwipeRefresh);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        //开启网络下载数据的方法
        startTask();

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initRefresh();
            }
        });
    }
    private void initRefresh(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(200);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        startTask();
                        swipeRefresh.setRefreshing(false);

                    }
                });
            }
        }).start();
    }
    //初始化适配器
    private void initAdapter() {

        //创建自定义适配器对象
        adapter = new HospitalAdapter(this,list);

        //设置recyclerview适配器
        PetHospitalRecyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new HospitalAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, Bundle data) {
                Intent intent = new Intent();
                intent.setData(Uri.parse("baidumap://map/geocoder?src=andr.baidu.openAPIdemo&address="+
                        data.getString("ItemAddress","天安门")));
                startActivity(intent);
            }
        });

        //刷新适配器
        adapter.notifyDataSetChanged();
    }

    //网络下载数据
    private void startTask() {

        //用Volley中静态方法初始化queue对象
        queue = Volley.newRequestQueue(this);

        //创建request对象，
        //参数分别为自定义MyRequest构造方法中参数
        request = new MyRequest<HospitalBean>(url, HospitalBean.class,
                new Response.Listener<HospitalBean>() {
                    @Override
                    public void onResponse(HospitalBean response) {

                        //通过接口回调得到返回回来的数据
                        if(response!=null){

                            //如果不为空用本地list接收
                            list.addAll(response.getHospitalItem());

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

}
