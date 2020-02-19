package com.chanshiguan.yumeng;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;

import com.chanshiguan.yumeng.Adapter.goodsAdapter;

import java.util.ArrayList;
import java.util.List;

public class shop2 extends AppCompatActivity {

    private ProgressBar pb;

    private DBAdapter dbAdepter ;
    private SearchView mSearchView;

    //private TextView displayView;
    private List<goods> goodsList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop2);
        //获取展示数据
        Intent intent=getIntent();
        String name1=intent.getStringExtra("name1");
        dbAdepter = new DBAdapter(this);
        dbAdepter.open();


        // String ty = tv_name.getText().toString();
        People[] peoples = dbAdepter.queryOneData2(name1);
        if (peoples == null){
            //labelView.setText("数据库中没有ID为"+String.valueOf(id)+"的数据");
            return;
        }
        String money1;
        for(int j=0;j<peoples.length;j++){
            money1 = Double.toString(peoples[j].Money);

            goods good1 = new goods(peoples[j].Name,R.drawable.a1,money1, peoples[j].Shopname);
            goodsList.add(good1);

        }


        goodsAdapter adapter = new goodsAdapter(shop2.this,
                R.layout.good_item, goodsList);
        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {


                goods goods2 = goodsList.get(position);
                String m1=goods2.getName();
                String m2=goods2.getShopname1();
                String m3=goods2.getmoney();
                Intent intent = new Intent();

                intent.setClass(shop2.this, shopping.class);
                intent.putExtra("m1", m1);
                intent.putExtra("m2", m2);
                intent.putExtra("m3", m3);
                intent.putExtra("m4", R.drawable.a1);
                startActivity(intent);
            }
        });
        mSearchView = (SearchView) findViewById(R.id.searchView);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // 当点击搜索按钮时触发该方法
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent();
                String name1=mSearchView.getQuery().toString();
                intent.setClass(shop2.this, shop.class);
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
    }
}
