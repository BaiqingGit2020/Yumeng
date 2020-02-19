package com.chanshiguan.yumeng;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

public class ShopFragment extends Fragment {
    private Bundle bundle;
    private TextView mTextView;
    private static final String TAG = "ShopFragment";
    private static final String BUNDLE_CONTEXT= "context";
    TabLayout mytab;
    private TextView mTextMessage;

    private SearchView mSearchView;
    private ListView lListView;
    private EditText et_name;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private MyFragmentPagerAdapter myFragmentPagerAdapter;

    private TabLayout.Tab one;
    private TabLayout.Tab two;
    private TabLayout.Tab three;
    private TabLayout.Tab four;
    private TabLayout.Tab five;

    public static ShopFragment newInstance(String context){
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_CONTEXT,context);

        ShopFragment firstFragment = new ShopFragment();
        firstFragment.setArguments(bundle);
        return firstFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.shop_fragment,container,false);
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();

                intent.setClass(getActivity(), ceshi.class);

                startActivity(intent);
            }
        });
        //初始化视图
        initViews(view);



        mSearchView = (SearchView) view.findViewById(R.id.searchView);


        // 设置搜索文本监听
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // 当点击搜索按钮时触发该方法
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent();
                String name1=mSearchView.getQuery().toString();
                intent.setClass(getActivity(), shop2.class);
                intent.putExtra("name1", name1);
                startActivity(intent);

                return false;
            }

            // 当搜索内容改变时触发该方法
            @Override
            public boolean onQueryTextChange(String newText) {
                if (!TextUtils.isEmpty(newText)){

                }else{

                }
                return false;
            }
        });
        return view;
    }
    private void initViews(View view) {

        //使用适配器将ViewPager与Fragment绑定在一起
        mViewPager= (ViewPager) view.findViewById(R.id.viewPager);
        myFragmentPagerAdapter = new MyFragmentPagerAdapter(getActivity().getSupportFragmentManager());
        mViewPager.setAdapter(myFragmentPagerAdapter);

        //将TabLayout与ViewPager绑定在一起
        mTabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
        mTabLayout.setupWithViewPager(mViewPager);

        //指定Tab的位置
        one = mTabLayout.getTabAt(0);
        two = mTabLayout.getTabAt(1);
        three = mTabLayout.getTabAt(2);
        four = mTabLayout.getTabAt(3);
        five = mTabLayout.getTabAt(4);

        //设置Tab的图标，假如不需要则把下面的代码删去
        one.setIcon(R.drawable.dog);
        two.setIcon(R.drawable.cat);
        three.setIcon(R.drawable.little);
        four.setIcon(R.drawable.fish);
        five.setIcon(R.drawable.qita);
    }

}
