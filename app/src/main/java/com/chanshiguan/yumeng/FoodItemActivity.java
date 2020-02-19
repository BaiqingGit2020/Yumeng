package com.chanshiguan.yumeng;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.chanshiguan.yumeng.Adapter.FoodAdapter;
import com.chanshiguan.yumeng.Bean.FoodBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


//导入架包Volley,Image-Loader,Gson,RecyclerView
//用Volley网络访问框架实现网络的数据下载，用image-loader框架实现网络图片的下载和缓存
//Gson用于json数据的解析，recyclerview用于实现瀑布流效果
public class FoodItemActivity extends AppCompatActivity {

    //log标签
    private static final String TAG = "===";

    //声明recyclerview引用
    private RecyclerView mRecyclerView;

    //创建一个list集合存储recyclerview中的图片的高度
    private List<Integer> heighs = new ArrayList<>();

    //获取的json数据中的数据集合
    private List<FoodBean.ItemBean> list = new ArrayList<>();

    //声明请求队列
    private RequestQueue queue;

    //声明自定义请求类
    private MyRequest<FoodBean> request;

    //声明自定义适配器
    private FoodAdapter adapter;

    private SwipeRefreshLayout swipeRefresh;

    //用插件自动生成初始化view代码的方法
    private void assignViews() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
    }

    //网络请求数据的网址
    private String url ="/ItemMessage.php?ip=";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_item);
//        完善网址信息
        url=getString(R.string.server)+url+getString(R.string.server);
        Log.i(TAG, url);
        //初始化控件
        assignViews();

        //设置recyclerview要实现的类型为StaggeredGrid瀑布流类型
        //并再构造方法中指定列数2，纵向排列
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,RecyclerView.VERTICAL);
        mRecyclerView.setLayoutManager(staggeredGridLayoutManager);
        swipeRefresh = findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        //开启网络下载数据的方法
        startTask();

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initRefresh();
            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
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
//                        startTask();
                        swipeRefresh.setRefreshing(false);
//
                    }
                });
            }
        }).start();
    }
    //初始化适配器
    private void initAdapter() {

        //创建自定义适配器对象
        adapter = new FoodAdapter(this,list,heighs);

        //设置recyclerview适配器
        mRecyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new FoodAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, Bundle data) {
                //DO your fucking bussiness here!
                Intent intent =new Intent();
                intent.putExtras(data);
                intent.setClass(FoodItemActivity.this,ItemDetailActivity.class);
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
        request = new MyRequest<FoodBean>(url, FoodBean.class,
                new Response.Listener<FoodBean>() {
                    @Override
                    public void onResponse(FoodBean response) {

                        //通过接口回调得到返回回来的数据
                        if(response!=null){

                            //如果不为空用本地list接收
                            list.addAll(response.getItem());

                            //数据一旦回调成功初始化数据源和适配器
                            initData();

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

    private void initData() {
        //设置随机数
        Random random = new Random();

        for (int i = 0; i <list.size() ; i++) {

            //集合中存储每个回调图片对应的随机高度
            heighs.add(random.nextInt(200)+800);

        }
    }
}