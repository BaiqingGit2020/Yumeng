package com.chanshiguan.yumeng;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.chanshiguan.yumeng.Adapter.FoodRemarkAdapter;
import com.chanshiguan.yumeng.Bean.FoodRemarkBean;

import java.util.ArrayList;
import java.util.List;

public class ItemDetailFragmentRemark extends Fragment implements View.OnClickListener{
    private Bundle ItemData;
    private String ItemId;
    private String url;
    private RequestQueue queue;
    private RecyclerView RemarkRecyclerView;
    //声明自定义适配器
    private FoodRemarkAdapter adapter;
    //评论详情List
    private List<FoodRemarkBean.RemarkItemBean> ItemRemark = new ArrayList<>();
    //声明自定义请求类
    private MyRequest<FoodRemarkBean> request;

    private static final String TAG = "ItemDetailFragmentDetai";

    public static ItemDetailFragmentRemark newInstance(Bundle ItemData){

        ItemDetailFragmentRemark itemDetailFragmentRemark = new ItemDetailFragmentRemark();
        itemDetailFragmentRemark.setArguments(ItemData);
        return itemDetailFragmentRemark;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_detail_remark,container,false);
        //获得项目信息
        ItemData=getArguments();
        ItemId=ItemData.getString("ItemId");
        //完成请求URL
        url = getString(R.string.server)+"/ItemRemark.php?Id=";
        url=url+ItemId;
        Log.i(TAG,url);
        //绑定列表视图
        assignViews(view);
        //加载瀑布流视图
        LinearLayoutManager staggeredGridLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        RemarkRecyclerView.setLayoutManager(staggeredGridLayoutManager);
        //开启网络下载任务
        startTask();

        return view;
    }
    //初始化适配器
    private void initAdapter() {

        //创建自定义适配器对象
        adapter = new FoodRemarkAdapter(getActivity(),ItemRemark);

        //设置recyclerview适配器
        RemarkRecyclerView.setAdapter(adapter);

        //刷新适配器
        adapter.notifyDataSetChanged();
    }

    /**用插件自动生成初始化view代码的方法
     * @param v
     */
    private void assignViews(View v) {
        RemarkRecyclerView = (RecyclerView) v.findViewById(R.id.RemarkrecyclerView);
    }
    public void onClick(View v){
    }
    /**
     * 网络下载数据
     */
    private void startTask() {

        //用Volley中静态方法初始化queue对象
        queue = Volley.newRequestQueue(getActivity());

        //创建request对象，
        //参数分别为自定义MyRequest构造方法中参数
        request = new MyRequest<FoodRemarkBean>(url, FoodRemarkBean.class,
                new Response.Listener<FoodRemarkBean>() {
                    @Override
                    public void onResponse(FoodRemarkBean response) {

                        //通过接口回调得到返回回来的数据
                        if(response!=null){
                            //如果不为空用本地list接收
                            ItemRemark.addAll(response.getRemarkItem());
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
