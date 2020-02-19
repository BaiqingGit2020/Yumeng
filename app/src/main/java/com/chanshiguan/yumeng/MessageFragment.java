package com.chanshiguan.yumeng;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chanshiguan.yumeng.Adapter.ItemDetailFragmentPagerAdapter;

import java.util.ArrayList;


public class MessageFragment extends Fragment implements View.OnClickListener{

    Bundle ItemData;
    private static final String BUNDLE_CONTEXT= "context";
    private TabLayout tabLayout;
    private ViewPager viewPager;
    //存放标签页标题
    private ArrayList<String> tab_title_list = new ArrayList<>();
    //存放ViewPager下的Fragment
    private ArrayList<Fragment> fragment_list = new ArrayList<>();

    private MessageFragmentFriends fragmentFriends;
    private MessageFragmentExpert fragmentExpert;
    //适配器
    private ItemDetailFragmentPagerAdapter adapter;

    public static MessageFragment newInstance(String context){
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_CONTEXT,context);

        MessageFragment messageFragment = new MessageFragment();
        messageFragment.setArguments(bundle);
        return messageFragment;
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.message_fragment,container,false);
        //初始化控件
        initview(view);
        return view;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){

        }
    }
    private void initview(View view){
        tabLayout = (TabLayout) view.findViewById(R.id.MessageTablayout);
        viewPager = (ViewPager) view.findViewById(R.id.MessagViewpager);

        tab_title_list.add("消息");
        tab_title_list.add("专家说");

        tabLayout.addTab(tabLayout.newTab().setText(tab_title_list.get(0)));
        tabLayout.addTab(tabLayout.newTab().setText(tab_title_list.get(1)));
        fragmentFriends = MessageFragmentFriends.newInstance("UserId");
        fragmentExpert = MessageFragmentExpert.newInstance("UserName");
        fragment_list.add(fragmentFriends);
        fragment_list.add(fragmentExpert);

        adapter = new ItemDetailFragmentPagerAdapter(getActivity().getSupportFragmentManager(), tab_title_list, fragment_list);
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
