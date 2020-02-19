package com.chanshiguan.yumeng;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.chanshiguan.yumeng.Adapter.FriendItemAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static android.content.Context.MODE_PRIVATE;

public class MessageFragmentFriends extends Fragment  {
    private static final String TAG = "FirstFragment";
    private static final String BUNDLE_CONTEXT= "context";

    private String userID,url="/ChatFriends.php?Ip=";
    private SharedPreferences pref;

    private RecyclerView ChatRecyclerView;
    //获取的json数据中的数据集合
    private List<FriendBean.FriendsItemBean> list = new ArrayList<>();

    //声明请求队列
    private RequestQueue queue;

    //声明自定义请求类
    private MyRequest<FriendBean> request;
    private Random random = new Random();
    //声明自定义适配器
    private FriendItemAdapter adapter;

    private SwipeRefreshLayout swipeRefresh;


    public static MessageFragmentFriends newInstance(String context){
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_CONTEXT,context);

        MessageFragmentFriends messageFragment = new MessageFragmentFriends();
        messageFragment.setArguments(bundle);
        return messageFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.message_fragment_friends,container,false);
        ChatRecyclerView = view.findViewById(R.id.ChatRecyclerView);
        swipeRefresh = view.findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);

        url = getString(R.string.server)+url+getString(R.string.server)+"&Id=";

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        ChatRecyclerView.setLayoutManager(linearLayoutManager);

        pref = getActivity().getSharedPreferences("LoginMessage",MODE_PRIVATE);
        boolean islanched = pref.getBoolean("isLunched",false);
        //开启网络下载数据的方法
        userID = pref.getString("UserName","0");
        if(islanched)url = url+userID;
        else url = url + "0";
        startTask();

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ChatRecyclerView.removeAllViews();
                list= new ArrayList<>();
                initRefresh();
            }
        });

        return view;
    }

    /**
     * 网络下载数据
     */
    private void startTask() {

        //用Volley中静态方法初始化queue对象
        queue = Volley.newRequestQueue(getActivity());

        //创建request对象，
        //参数分别为自定义MyRequest构造方法中参数
        request = new MyRequest<FriendBean>(url, FriendBean.class,
                new Response.Listener<FriendBean>() {
                    @Override
                    public void onResponse(FriendBean response) {

                        //通过接口回调得到返回回来的数据
                        if(response!=null){

                            //如果不为空用本地list接收
                            list.addAll(response.getFriendsItem());

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

    /**
     * 初始化适配器
     */
    private void initAdapter() {

        //创建自定义适配器对象
        adapter = new FriendItemAdapter(getActivity(),list);

        //设置recyclerview适配器
        ChatRecyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new FriendItemAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, Bundle data) {
                //DO your fucking bussiness here!
                Intent intent =new Intent();
                data.putInt("port",2000+data.getInt("FriendPosition"));
                data.putString("getID",userID);
                intent.putExtras(data);
                intent.setClass(getActivity(),MessageActivity.class);
                startActivity(intent);
            }
        });
        //刷新适配器
        adapter.notifyDataSetChanged();
    }
    /**
     * 下拉刷新动作
     */
    private void initRefresh(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(200);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        startTask();
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }
        }).start();
    }
}
