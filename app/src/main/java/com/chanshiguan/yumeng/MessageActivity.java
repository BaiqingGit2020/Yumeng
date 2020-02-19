package com.chanshiguan.yumeng;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.chanshiguan.yumeng.MessageUtil.MessageAdapter;
import com.chanshiguan.yumeng.MessageUtil.ChatMessage;
import com.chanshiguan.yumeng.MessageUtil.MessageDBHelper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MessageActivity extends AppCompatActivity implements View.OnClickListener{

    private List<ChatMessage> MessageList = new ArrayList<>();
    private static final String TAG = "MessageActivity";
    private EditText inputText;
    private Button send;
    private RecyclerView MessageRecyclerView;
    private MessageAdapter adapter;
    private MessageDBHelper messageDBHelper;
    private  Bundle data;
    private String DB_NAME;

    private String name;
    private int port;
    private Socket socket;
    private BufferedReader in = null;
    private PrintWriter out = null;

    //接收线程发送过来信息，并用TextView追加显示
    public Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ChatMessage Message = new ChatMessage(data.getString("FriendId"),data.getString("getID"),
                    (String) msg.obj, ChatMessage.TYPE_RECEIVED);
            MessageList.add(Message);
            InsertMessages(Message);
            //当有新消息时，刷新RecyclerView中的显示
            adapter.notifyItemInserted(MessageList.size() - 1);
            // 将RecyclerView定位到最后一行
            MessageRecyclerView.scrollToPosition(MessageList.size() - 1);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        //绑定视图
        Intent intent = getIntent();
        data = intent.getExtras();
        port = data.getInt("port");
        DB_NAME = "ChatMessage"+data.getString("FriendId");
        messageDBHelper = new MessageDBHelper(this,DB_NAME);
        inputText = (EditText) findViewById(R.id.input_text);
        send = (Button) findViewById(R.id.send);
        //设置List
        MessageRecyclerView = (RecyclerView) findViewById(R.id.Message_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        MessageRecyclerView.setLayoutManager(layoutManager);
        //读取数据库
        MessageList.clear();
        MessageList.addAll(quaryDB());
        //设置适配器
        adapter = new MessageAdapter(MessageList);
        MessageRecyclerView.setAdapter(adapter);
        send.setOnClickListener(this);
        // 将RecyclerView定位到最后一行
        MessageRecyclerView.scrollToPosition(MessageList.size() - 1);


        new Thread(new Runnable() {
            @Override
            public void run() {
                connection();// 连接到服务器
                try {
                    while (true) {//死循环守护，监控服务器发来的消息
                        if (!socket.isClosed()) {//如果服务器没有关闭
                            Log.i(TAG, "run: socket is not closed");
                            if (socket.isConnected()) {//连接正常
                                if (!socket.isInputShutdown()) {//如果输入流没有断开
                                    String getLine;
                                    if ((getLine = in.readLine()) != null) {//读取接收的信息
                                        getLine += "\n";
                                        Message message=new Message();
                                        message.obj=getLine;
                                        mHandler.sendMessage(message);//通知UI更新
                                    } else {

                                    }
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.send:
                String content = inputText.getText().toString();
                if (!"".equals(content)) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            if(socket!=null) {
                                if (socket.isConnected()) {//如果服务器连接
                                    if (!socket.isOutputShutdown()) {//如果输出流没有断开
                                        out.println(content);//点击按钮发送消息
                                    }
                                }
                            }
                        }
                    }).start();
                    ChatMessage Message = new ChatMessage(data.getString("getID"),data.getString("FriendId"),
                            content, ChatMessage.TYPE_SENT);
                    MessageList.add(Message);
                    InsertMessages(Message);
                    //当有新消息时，刷新RecyclerView中的显示
                    adapter.notifyItemInserted(MessageList.size() - 1);
                    // 将RecyclerView定位到最后一行
                    MessageRecyclerView.scrollToPosition(MessageList.size() - 1);
                    inputText.setText(""); // 清空输入框中的内容
                }
                break;
        }
    }

    /**将信息插入数据库
     * @param chatMessage
     */
    private void InsertMessages(ChatMessage chatMessage) {

        SQLiteDatabase db = messageDBHelper.getWritableDatabase();
        ContentValues message = new ContentValues();
        message.put("GetID",chatMessage.getGetID());
        message.put("SendID",chatMessage.getSendID());
        message.put("Content",chatMessage.getContent());
        message.put("Type",chatMessage.getType());
        db.insert(DB_NAME,null,message);
    }

    /**
     *
     */
    public void 测试用插入数据(){
        ChatMessage Message1 = new ChatMessage(data.getString("sendID"),data.getString("getID"),
                "Hello guy.", ChatMessage.TYPE_RECEIVED);
        MessageList.add(Message1);
        InsertMessages(Message1);
        ChatMessage Message2 = new ChatMessage(data.getString("sendID"),data.getString("getID"),
                "Hello. Who is that?", ChatMessage.TYPE_SENT);
        MessageList.add(Message2);
        InsertMessages(Message2);
    }

    /**查询相应数据库，返回List
     * @return
     */
    public List<ChatMessage> quaryDB(){
        List<ChatMessage> messageList = new ArrayList<>();
        SQLiteDatabase db = messageDBHelper.getWritableDatabase();
        Cursor cursor = db.query(DB_NAME, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                // 遍历Cursor对象，取出数据并打印
                String getID = cursor.getString(cursor.getColumnIndex
                        ("GetID"));
                String sendID = cursor.getString(cursor.getColumnIndex
                        ("SendID"));
                String content = cursor.getString(cursor.getColumnIndex
                        ("Content"));
                int Type = cursor.getInt(cursor.getColumnIndex("Type"));
                messageList.add(new ChatMessage(sendID,getID,content,Type));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return messageList;
    }

    /**
     * 连接服务器
     */
    private void connection() {
        try {
//            InetAddress ip = InetAddress.getByName(getString(R.string.host));
//            Log.i(TAG, "connection: "+ip.getHostAddress());
            socket = new Socket(getString(R.string.host),port);//连接服务器
            if(socket.isConnected()) Log.i(TAG, "connection: success");
            in = new BufferedReader(new InputStreamReader(socket
                    .getInputStream()));//接收消息的流对象
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
                    socket.getOutputStream())), true);//发送消息的流对象
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    socket.close();
                }
                catch (Exception e){}
            }
        }).start();
    }
}
