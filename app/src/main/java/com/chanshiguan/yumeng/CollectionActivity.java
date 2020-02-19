package com.chanshiguan.yumeng;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.chanshiguan.yumeng.Adapter.NewsItemAdapter;
import com.chanshiguan.yumeng.Bean.MainNewsBean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class CollectionActivity extends AppCompatActivity {

    //log标签
    private static final String TAG = "===";

    //声明recyclerview引用
    private RecyclerView CollectionlRecyclerView;
    private Bundle bundle;
    private TextView LocateTextView,MainViewUserName,webView;
    private ImageView MainViewHeadView,FoodItem,HospitalItem;

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    private String UserId;
    private int post;
    private static final String BUNDLE_CONTEXT= "context";

    private RequestQueue queue;//声明请求队列
    private MyRequest<MainNewsBean> request;//声明自定义请求类
    private NewsItemAdapter adapter;//声明自定义适配器
    private List<MainNewsBean.NewsItemBean> list = new ArrayList<>();

    private SwipeRefreshLayout swipeRefresh;

    /**
     * 初始化view代码的方法
     */
    private void assignViews() {
        CollectionlRecyclerView = (RecyclerView) findViewById(R.id.CollectionlRecyclerView);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.CollectionTab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    //网络请求数据的网址
    private String url ="/CollectNewsItem.php?Id=";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        pref = getSharedPreferences("LoginMessage",MODE_PRIVATE);
        UserId = pref.getString("UserName","0");
//        完善网址信息
        url=getString(R.string.server)+url+UserId;
        Log.i(TAG, url);
        //初始化控件
        assignViews();

        //设置recyclerview要实现的类型为StaggeredGrid瀑布流类型
        //并再构造方法中指定列数2，纵向排列
        int resId = R.anim.layout_animation_fall_down;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(this, resId);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        CollectionlRecyclerView.setLayoutManager(linearLayoutManager);
        CollectionlRecyclerView.setLayoutAnimation(animation);
        swipeRefresh = findViewById(R.id.CollectionSwipeRefresh);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        //开启网络下载数据的方法
        startTask();
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                CollectionlRecyclerView.removeAllViews();
                list = new ArrayList<>();
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
    /**
     *     //初始化适配器
     */
    private void initAdapter() {

        //创建自定义适配器对象
        adapter = new NewsItemAdapter(CollectionActivity.this,list);

        //设置recyclerview适配器
        CollectionlRecyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new NewsItemAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, Bundle data) {
                //DO your fucking bussiness here!
                Intent intent =new Intent();
                data.putString("UserName",UserId);
                intent.putExtras(data);
                intent.setClass(CollectionActivity.this,WebViewActivity.class);
                startActivity(intent);
            }
            @Override
            public void onItemLongClick(View view, Bundle data) {
                showPopMenu(view,data.getInt("ItemPosition"),data.getString("NewsID"));
                Log.i(TAG, "onItemLongOnClick: "+data.getString("NewsID"));
            }
        });
        //刷新适配器
        adapter.notifyDataSetChanged();
    }

    /**
     * 网络下载数据
     */
    private void startTask() {

        //用Volley中静态方法初始化queue对象
        queue = Volley.newRequestQueue(CollectionActivity.this);

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
                            //检查是否为空
                            check();
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
    public void showPopMenu(View view,final int pos,String NewsId){
        PopupMenu popupMenu = new PopupMenu(this,view);
        popupMenu.getMenuInflater().inflate(R.menu.menu_item,popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                String url = getString(R.string.server)+"/DeleteCollect.php";
                //实例化okHttp
                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(5,TimeUnit.MINUTES)
                        .readTimeout(5,TimeUnit.MINUTES)
                        .build();
                //okHttp参数
                RequestBody requestBody = new FormBody.Builder()
                        .add("NewsId",NewsId)
                        .build();
                Request request = new Request.Builder()
                        .url(url)
                        .post(requestBody)
                        .build();
                Call call = client.newCall(request);
                //异步请求
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                    }
                    @Override
                    public void onResponse(Call call, okhttp3.Response response) throws IOException {
                        String result=response.body().string();
                        if(result.equals("success!")){
                            post=pos;
                            adapter.removeItem(pos);
                            Looper.prepare();
                            Toast.makeText(CollectionActivity.this,"删除成功",Toast.LENGTH_LONG).show();
                            Message message=new Message();
                            message.obj=pos;
                            mHandler.sendMessage(message);//通知UI更新
                            Looper.loop();
                        }
                        else{
                            Looper.prepare();
                            Toast.makeText(CollectionActivity.this,"删除失败！",Toast.LENGTH_LONG).show();
                            Looper.loop();
                        }
                    }
                });
                return false;
            }
        });
        popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu menu) {
            }
        });
        popupMenu.show();
    }
    public Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
        super.handleMessage(msg);
        adapter.notifyItemRemoved((int)msg.obj);
        if((int)msg.obj != list.size()){ // 如果移除的是最后一个，忽略
            adapter.notifyItemRangeChanged((int)msg.obj, list.size() - (int)msg.obj);
        }
        check();
        }
    };

    private void check(){
        RelativeLayout relativeLayout = findViewById(R.id.collect_background);
        if(adapter!=null){
            if(adapter.getItemCount()==0){
                relativeLayout.setBackgroundResource(R.mipmap.collect_background);
            }
            else relativeLayout.setBackgroundResource(R.color.text_write);
        }
    }
}

