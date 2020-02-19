package com.chanshiguan.yumeng.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.chanshiguan.yumeng.R;
import com.chanshiguan.yumeng.goods;

import java.util.List;

public class goodsAdapter extends ArrayAdapter <goods> {
    private int resourceId;
    public goodsAdapter(Context context, int textViewResourceId,
                        List<goods> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       goods goods = getItem(position); // 获取当前项的Fruit实例
        View view = LayoutInflater.from(getContext()).inflate(resourceId, parent,
                false);
        ImageView fruitImage = (ImageView) view.findViewById(R.id.fruit_image);
        TextView goodsName = (TextView) view.findViewById(R.id.goods_name);
        TextView goodsMoney = (TextView) view.findViewById(R.id.goods_money);
        TextView shopName = (TextView) view.findViewById(R.id.shop_name);
        fruitImage.setImageResource(goods.getImageId());
        goodsName.setText(goods.getName());
        goodsMoney.setText(goods.getmoney());
        shopName.setText(goods.getShopname1());
        return view;
    }
}