package com.chanshiguan.yumeng;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class ItemDetailFragmentMain extends Fragment implements View.OnClickListener{
    private Bundle ItemData;
    private ImageView ItemImage;
    private TextView ItemNameTextView,ItemPriiceTextView;
    private String ItemId,ItemName,ItemPrice;
    private static final String TAG = "ItemDetailFragmentMain";

    //声明一个lmageloader引用
    private ImageLoader loader;

    //声明image的设置属性引用
    private DisplayImageOptions dios;

    public static ItemDetailFragmentMain newInstance(Bundle ItemData){

        ItemDetailFragmentMain itemDetailFragmentMain = new ItemDetailFragmentMain();
        itemDetailFragmentMain.setArguments(ItemData);
        return itemDetailFragmentMain;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_detail_main,container,false);
        //绑定视图
        ItemImage =view.findViewById(R.id.item_detail_image);
        ItemNameTextView = view.findViewById(R.id.item_detail_name);
        ItemPriiceTextView = view.findViewById(R.id.item_detail_price);
        //获得项目参数
        ItemData=getArguments();
        ItemId=ItemData.getString("ItemId");
        ItemName=ItemData.getString("ItemName");
        ItemPrice = ItemData.getString("ItemPrice");

        ItemNameTextView.setText(ItemName);
        ItemPriiceTextView.setText("单价：￥："+ItemPrice+" 元");
        //初始化图片加载工具
        initimageloader(getActivity());
        //加载图片
        loader.displayImage(ItemData.getString("ItemImageUrl"),ItemImage,dios);

        return view;
    }
    public void onClick(View v){

    }
    private void initimageloader(Context context){
        //用imagerloader中静态方法创建对象
        loader = ImageLoader.getInstance();

        //初始化loader对象的配置信息
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
}

