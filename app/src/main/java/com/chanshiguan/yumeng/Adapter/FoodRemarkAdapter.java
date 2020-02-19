package com.chanshiguan.yumeng.Adapter;


import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chanshiguan.yumeng.Bean.FoodRemarkBean;
import com.chanshiguan.yumeng.R;

import java.util.List;

/**
 * Created by Bane on 2016/8/22 0022.
 */

//自定义适配器类，继承自Recyclerview的adapter类，定义范型为继承Recyclerview的viewHolder
// 的MyViewHolder类，用于recyclerview的适配器
public class FoodRemarkAdapter extends RecyclerView.Adapter<FoodRemarkAdapter.MyViewHolder> implements View.OnClickListener{

    //声明上下文引用，用于加载布局文件
    private Context context;

    //声明两个集合用于接受构造方法传来的参数在本地使用
    private List<FoodRemarkBean.RemarkItemBean> list;


    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view , Bundle data);
    }
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick(v,(Bundle) v.getTag());
        }
    }

    //用构造方法传入需要的参数，
    public FoodRemarkAdapter(Context context, List<FoodRemarkBean.RemarkItemBean> list){

        this.context= context;
        this.list=list;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //返回MyViewHolder对象，通过构造方法传入加载布局文件得到的view对象
        View view = LayoutInflater.from(context).inflate(R.layout.food_remark,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        view.setOnClickListener(this);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        //通过itemview得到每个图片的pararms对象
        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) holder.itemView.getLayoutParams();


        //设置修改参数
        holder.itemView.setLayoutParams(params);

        holder.RemarkUserName.setText(list.get(position).getRemarkUserName());
        holder.RemarkTime.setText(list.get(position).getRemarkTime());
        holder.RemarkDetail.setText(list.get(position).getRemarkDetail());

    }

    @Override
    public int getItemCount() {
        //返回数据源大小
        return list.size();
    }

    //自定义MyViewHolder类用于复用
    class MyViewHolder extends RecyclerView.ViewHolder{
        //声明imageview对象
        private TextView RemarkUserName,RemarkTime,RemarkDetail;

        //构造方法中初始化imageview对象
        public MyViewHolder(View itemView) {
            super(itemView);
            RemarkUserName =  itemView.findViewById(R.id.RemarkUserName);
            RemarkTime = itemView.findViewById(R.id.RemarkUserTime);
            RemarkDetail = itemView.findViewById(R.id.RemarkUserDetail);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
        }
    }
}
