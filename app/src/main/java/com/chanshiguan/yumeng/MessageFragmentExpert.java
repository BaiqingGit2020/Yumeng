package com.chanshiguan.yumeng;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
import com.chanshiguan.yumeng.Adapter.ExpertZoneAdapter;
import com.chanshiguan.yumeng.Bean.ExpertZoneBean;

import java.util.ArrayList;
import java.util.List;

public class MessageFragmentExpert extends Fragment  {
    private static final String TAG = "FirstFragment";
    private static final String BUNDLE_CONTEXT= "context";

    private String url="/ZoneExpert.php?Ip=";
    private SharedPreferences pref;

    private RecyclerView ExpertRecyclerView;
    //获取的json数据中的数据集合
    private List<ExpertZoneBean.ExpertZoneItemBean> list = new ArrayList<>();

    //声明请求队列
    private RequestQueue queue;

    //声明自定义请求类
    private MyRequest<ExpertZoneBean> request;

    //声明自定义适配器
    private ExpertZoneAdapter adapter;
    private SwipeRefreshLayout swipeRefresh;

    private FloatingActionButton AddExpert;
    public static MessageFragmentExpert newInstance(String context){
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_CONTEXT,context);

        MessageFragmentExpert messageFragment = new MessageFragmentExpert();
        messageFragment.setArguments(bundle);
        return messageFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.message_fragment_expert,container,false);

        ExpertRecyclerView = view.findViewById(R.id.ExpertRecyclerView);
        swipeRefresh = view.findViewById(R.id.ExpertSwipeRefresh);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);

        url = getString(R.string.server)+url+getString(R.string.server);

        LinearLayoutManager staggeredGridLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        ExpertRecyclerView.setLayoutManager(staggeredGridLayoutManager);

        startTask();

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ExpertRecyclerView.removeAllViews();
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
        request = new MyRequest<ExpertZoneBean>(url, ExpertZoneBean.class,
                new Response.Listener<ExpertZoneBean>() {
                    @Override
                    public void onResponse(ExpertZoneBean response) {

                        //通过接口回调得到返回回来的数据
                        if(response!=null){

                            //如果不为空用本地list接收
                            list.addAll(response.getExpertZoneItem());

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
        adapter = new ExpertZoneAdapter(getActivity(),list);

        //设置recyclerview适配器
        ExpertRecyclerView.setAdapter(adapter);

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
