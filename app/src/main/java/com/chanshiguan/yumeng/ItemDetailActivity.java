package com.chanshiguan.yumeng;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.chanshiguan.yumeng.Adapter.ItemDetailFragmentPagerAdapter;

import java.util.ArrayList;

public class ItemDetailActivity extends AppCompatActivity implements View.OnClickListener{

    Bundle ItemData;

    private TextView ItemServer,ItemCar,ItemBuy;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    //存放标签页标题
    private ArrayList<String> tab_title_list = new ArrayList<>();
    //存放ViewPager下的Fragment
    private ArrayList<Fragment> fragment_list = new ArrayList<>();

    private ItemDetailFragmentRemark  remarkfragment;
    private ItemDetailFragmentMain mainfragment;
    private ItemDetailFragmentDetail detailfragment;
    //适配器
    private ItemDetailFragmentPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        ItemData = getIntent().getExtras();

//        Toast.makeText(ItemDetailActivity.this,"ID="+ItemData.getString("ItemId"),Toast.LENGTH_LONG).show();

        initview();

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.item_server:
                break;
            case R.id.item_car:
                break;
            case R.id.item_buy:
                break;
        }
    }
    private void initview(){
        tabLayout = (TabLayout) findViewById(R.id.my_tablayout);
        viewPager = (ViewPager) findViewById(R.id.my_viewpager);
        ItemServer = findViewById(R.id.item_server);
        ItemCar = findViewById(R.id.item_car);
        ItemBuy = findViewById(R.id.item_buy);

        ItemServer.setOnClickListener(this);
        ItemCar.setOnClickListener(this);
        ItemBuy.setOnClickListener(this);

        tab_title_list.add("商品");
        tab_title_list.add("详情");
        tab_title_list.add("评价");

        tabLayout.addTab(tabLayout.newTab().setText(tab_title_list.get(0)));
        tabLayout.addTab(tabLayout.newTab().setText(tab_title_list.get(1)));
        tabLayout.addTab(tabLayout.newTab().setText(tab_title_list.get(2)));
        mainfragment = ItemDetailFragmentMain.newInstance(ItemData);
        detailfragment = ItemDetailFragmentDetail.newInstance(ItemData);
        remarkfragment = ItemDetailFragmentRemark.newInstance(ItemData);
        fragment_list.add(mainfragment);
        fragment_list.add(detailfragment);
        fragment_list.add(remarkfragment);
        adapter = new ItemDetailFragmentPagerAdapter(getSupportFragmentManager(), tab_title_list, fragment_list);
        viewPager.setAdapter(adapter);//给ViewPager设置适配器
        tabLayout.setupWithViewPager(viewPager);//将TabLayout与Viewpager联动起来
        tabLayout.setTabsFromPagerAdapter(adapter);//给TabLayout设置适配器

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //选中了tab的逻辑
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                //未选中tab的逻辑
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                //再次选中tab的逻辑
            }

        });
    }

}
