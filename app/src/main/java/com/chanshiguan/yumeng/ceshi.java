package com.chanshiguan.yumeng;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chanshiguan.yumeng.Adapter.goodsAdapter;

import java.util.ArrayList;
import java.util.List;

public class ceshi extends AppCompatActivity {

    private TextView tv_name;
    private ProgressBar pb;

    private DBAdapter dbAdepter ;

    private EditText nameText;
    private EditText countText;
    private EditText typeText;
    private EditText shopnameText;
    //private EditText shopText;
    private EditText idEntry;
    private EditText moneyText;
    private TextView labelView;
    //private TextView displayView;
    private List<goods> goodsList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ceshi);
        //获取展示数据
        tv_name=(TextView) findViewById(R.id.tv_name);

        Intent intent=getIntent();
        String name1=intent.getStringExtra("name1");
        tv_name.setText(name1);

        nameText = (EditText)findViewById(R.id.name);
        countText = (EditText)findViewById(R.id.count);
        moneyText = (EditText)findViewById(R.id.money);
        typeText = (EditText)findViewById(R.id.type);
        shopnameText = (EditText)findViewById(R.id.shopname);
        idEntry = (EditText)findViewById(R.id.id_entry);

        labelView = (TextView)findViewById(R.id.label);
        //displayView = (TextView)findViewById(R.id.display);



        Button addButton = (Button)findViewById(R.id.add);
        Button queryAllButton = (Button)findViewById(R.id.query_all);
        Button clearButton = (Button)findViewById(R.id.clear);
        Button deleteAllButton = (Button)findViewById(R.id.delete_all);

        Button queryButton = (Button)findViewById(R.id.query);
        Button deleteButton = (Button)findViewById(R.id.delete);
        Button updateButton = (Button)findViewById(R.id.update);


        addButton.setOnClickListener(addButtonListener);
        queryAllButton.setOnClickListener(queryAllButtonListener);
        clearButton.setOnClickListener(clearButtonListener);
        deleteAllButton.setOnClickListener(deleteAllButtonListener);

        queryButton.setOnClickListener(queryButtonListener);
        deleteButton.setOnClickListener(deleteButtonListener);
        updateButton.setOnClickListener(updateButtonListener);

        dbAdepter = new DBAdapter(this);
        dbAdepter.open();
    }

    View.OnClickListener addButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            People people = new People();
            people.Name = nameText.getText().toString();
            people.Count = Integer.parseInt(countText.getText().toString());
            people.Money = Float.parseFloat(moneyText.getText().toString());
            people.Type = typeText.getText().toString();
            people.Shopname = shopnameText.getText().toString();
            long colunm = dbAdepter.insert(people);
            if (colunm == -1 ){
                labelView.setText("添加过程错误！");
            } else {
                labelView.setText("成功添加数据，ID："+String.valueOf(colunm));

            }
        }
    };

    View.OnClickListener queryAllButtonListener = new View.OnClickListener() {
        @Override

        public void onClick(View v) {
            People[] peoples = dbAdepter.queryAllData();
            if (peoples == null){
                labelView.setText("数据库中没有数据");
                return;
            }
            labelView.setText("数据库：");

            // displayView.setText(msg);


            String money1;
            for(int j=0;j<peoples.length;j++){
                money1 = Double.toString(peoples[j].Money);

                goods good1 = new goods(peoples[j].Name,R.drawable.a1,money1, peoples[j].Shopname);
                goodsList.add(good1);

            }


            goodsAdapter adapter = new goodsAdapter(ceshi.this,
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

                    intent.setClass(ceshi.this, ceshi.class);
                    intent.putExtra("m1", m1);
                    intent.putExtra("m2", m2);
                    intent.putExtra("m3", m3);
                    intent.putExtra("m4", R.drawable.a1);
                    startActivity(intent);




                }
            });


        }



    };

    View.OnClickListener clearButtonListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            // displayView.setText("");
        }
    };

    View.OnClickListener deleteAllButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dbAdepter.deleteAllData();
            String msg = "数据全部删除";
            labelView.setText(msg);
        }
    };

    View.OnClickListener queryButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = Integer.parseInt(idEntry.getText().toString());
            String ty = tv_name.getText().toString();
            People[] peoples = dbAdepter.queryOneData(ty);

            if (peoples == null){
                //labelView.setText("数据库中没有ID为"+String.valueOf(id)+"的数据");
                return;
            }
            labelView.setText("数据库：");
            //  displayView.setText(peoples[0].toString());



            String money1;
            for(int j=0;j<peoples.length;j++){
                money1 = Double.toString(peoples[j].Money);

                goods good1 = new goods(peoples[j].Name,R.drawable.a1,money1, peoples[j].Shopname);
                goodsList.add(good1);

            }


            goodsAdapter adapter = new goodsAdapter(ceshi.this,
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

                    intent.setClass(ceshi.this, ceshi.class);
                    intent.putExtra("m1", m1);
                    intent.putExtra("m2", m2);
                    intent.putExtra("m3", m3);
                    intent.putExtra("m4", R.drawable.a1);
                    startActivity(intent);




                }
            });
        }
    };

    View.OnClickListener deleteButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            long id = Integer.parseInt(idEntry.getText().toString());
            long result = dbAdepter.deleteOneData(id);
            String msg = "删除ID为"+idEntry.getText().toString()+"的数据" + (result>0?"成功":"失败");
            labelView.setText(msg);
        }
    };

    View.OnClickListener updateButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            People people = new People();
            people.Name = nameText.getText().toString();
            people.Count = Integer.parseInt(countText.getText().toString());
            people.Money = Float.parseFloat(moneyText.getText().toString());
            people.Type = typeText.getText().toString();
            people.Shopname = shopnameText.getText().toString();
            long id = Integer.parseInt(idEntry.getText().toString());
            long count = dbAdepter.updateOneData(id, people);
            if (count == -1 ){
                labelView.setText("更新错误！");
            } else {
                labelView.setText("更新成功，更新数据"+String.valueOf(count)+"条");

            }
        }
    };
}
