package com.chanshiguan.yumeng.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chanshiguan.yumeng.FriendBean;
import com.chanshiguan.yumeng.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;

public class FriendItemAdapter extends RecyclerView.Adapter<FriendItemAdapter.MyViewHolder> implements View.OnClickListener{
    //声明上下文引用，用于加载布局文件
    private Context context;
    //声明一个lmageloader引用
    private ImageLoader loader;

    //声明image的设置属性引用
    private DisplayImageOptions dios;

    //声明两个集合用于接受构造方法传来的参数在本地使用
    private List<FriendBean.FriendsItemBean> list;

    /**
     *Item点击事件接口
     * Bundle作为消息传递
     */
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
    public FriendItemAdapter(Context context, List<FriendBean.FriendsItemBean> list){

        this.context= context;
        this.list=list;
//用imagerloader中静态方法创建对象
        loader = ImageLoader.getInstance();

        //初始化loader对象的配置信息
        if(!loader.isInited())
        loader.init(ImageLoaderConfiguration.createDefault(context));

        //创建image属性的配置信息
        dios = new DisplayImageOptions.Builder()
                //下载失败显示的图片
                .showImageOnFail(R.mipmap.itembackground)
                //正在下载中显示的图片
                .showImageOnLoading(R.mipmap.itembackground)
                //图片的配置设置
                .bitmapConfig(Bitmap.Config.RGB_565)
                //是否有运行时缓存
                .cacheInMemory(true)
                //是否有本地缓存
                .cacheOnDisk(true)
                //创建
                .build();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //返回MyViewHolder对象，通过构造方法传入加载布局文件得到的view对象
        View view = LayoutInflater.from(context).inflate(R.layout.friend_item,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        view.setOnClickListener(this);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(FriendItemAdapter.MyViewHolder holder, int position) {

        //通过itemview得到每个图片的pararms对象
        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) holder.itemView.getLayoutParams();


        //设置修改参数
        holder.itemView.setLayoutParams(params);

        //通过loader对象的displayImage方法将网址中下载的图片按照设置的图片配置显示再imageview中
        loader.displayImage(list.get(position).getFriendHeadUrl(),holder.FriendHeadImage,dios);
        Log.i("===",list.get(position).getFriendId());
        holder.FriendName.setText(list.get(position).getFriendName());
        holder.FriendDetail.setText(list.get(position).getFriendDetail());
        //为点击事件传递参数
        Bundle bundle = new Bundle();
        bundle.putString("FriendId",list.get(position).getFriendId());
        bundle.putString("FriendName",list.get(position).getFriendName());
        bundle.putString("FriendDetail",list.get(position).getFriendDetail());
        bundle.putString("FriendImageUrl",list.get(position).getFriendHeadUrl());
        bundle.putInt("FriendPosition",position);
        holder.itemView.setTag(bundle);

    }

    @Override
    public int getItemCount() {
        //返回数据源大小
        return list.size();
    }

    //自定义MyViewHolder类用于复用
    class MyViewHolder extends RecyclerView.ViewHolder{
        //声明imageview对象
        private ImageView FriendHeadImage;
        private TextView FriendName,FriendDetail;

        //构造方法中初始化imageview对象
        public MyViewHolder(View itemView) {
            super(itemView);
            FriendName =  itemView.findViewById(R.id.FriendName);
            FriendDetail = itemView.findViewById(R.id.FriendDetail);
            FriendHeadImage = itemView.findViewById(R.id.FriendImage);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
        }
    }
}
