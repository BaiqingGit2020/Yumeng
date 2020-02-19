package com.chanshiguan.yumeng;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.leon.lib.settingview.LSettingItem;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

public class MyFragment extends Fragment implements View.OnClickListener{
    private Bundle bundle;
//    用户名
    private  String UserName;
//    用户名控件和更换头像控件
    private TextView ViewUserName,mGoAlbm_btn;
//    LSettingItem控件：登出
    private LSettingItem one,collect,setting;
//    头像控件
    private CircleImageView head;
    private static final String TAG = "MyFragment";
    private static final String BUNDLE_CONTEXT= "context";

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    private Bitmap image;
    //相册请求码
    private static final int ALBUM_REQUEST_CODE = 1;
    //剪裁请求码
    private static final int CROP_REQUEST_CODE = 3;
    //存储权限请求码
    private static final int STORAGE_REQUEST_CODE = 2;

    public static MyFragment newInstance(String context){
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_CONTEXT,context);

        MyFragment myFragment = new MyFragment();
        myFragment.setArguments(bundle);
        return myFragment;
    }

    /**
     * 登录时绘制头像和用户名
     */
    @Override
    public void onStart() {
        super.onStart();
        //判断登录
        pref = getActivity().getSharedPreferences("LoginMessage",MODE_PRIVATE);
        UserName = pref.getString("NakeName","请先登录");
        Log.i(TAG,UserName);
        //设置用户名
        ViewUserName.setText(UserName);
        head.setImageResource(R.drawable.headimage);
        //申请权限，如果有权限则加载头像
        if(pref.getBoolean("isLunched",false)) {
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.
                    permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new
                        String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_REQUEST_CODE);
            } else {
                try {
                    //检查文件加载头像
                    File file = new File("/sdcard/yumeng/");
                    if (file.exists()) {
                        FileInputStream fis = new FileInputStream("/sdcard/yumeng/head"+UserName+".png");
                        Bitmap bitmap = BitmapFactory.decodeStream(fis);
                        head.setImageBitmap(bitmap);
                    }
                } catch (IOException e) {

                }
                Log.d(TAG, "path1 create:");
            }
        }

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_fragment,container,false);
        //初始化界面元素
        ViewUserName = view.findViewById(R.id.UserName);
        one = view.findViewById(R.id.item_one);
        head = view.findViewById(R.id.profile_image);
        collect = view.findViewById(R.id.item_collect);
        setting = view.findViewById(R.id.item_setting);
        mGoAlbm_btn =  view.findViewById(R.id.mGoAlbm_btn);
        //注册点击事件
        mGoAlbm_btn.setOnClickListener(this);
        head.setOnClickListener(this);
        one.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click(boolean isChecked) {
                Log.i(TAG,"click1");
                Toast.makeText(getActivity(),"点击了钱包",Toast.LENGTH_SHORT).show();
            }
        });
        collect.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click(boolean isChecked) {
                startActivity(new Intent(getActivity(),CollectionActivity.class));
            }
        });
        setting.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click(boolean isChecked) {
                startActivity(new Intent(getActivity(),UserSetingActivity.class));
            }
        });
        return view;

    }
    public void onClick(View v){
        switch (v.getId()){
            case R.id.profile_image:
                Log.i(TAG,"click2");
                Intent intent = new Intent(getActivity(),LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.mGoAlbm_btn:
                if(isLunched())getPicFromAlbm();
                else Toast.makeText(getActivity(),"请先登录",Toast.LENGTH_LONG).show();
                break;
        }
    }
    private void getPicFromAlbm() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, ALBUM_REQUEST_CODE);
    }
    /**
     * 裁剪图片
     */
    private void cropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", true);
        Log.i("11111",intent.toString());
        startActivityForResult(intent, CROP_REQUEST_CODE);
    }


    /**
     * 处理返回值
     * @param requestCode
     * @param resultCode
     * @param intent
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        switch (requestCode) {

            case ALBUM_REQUEST_CODE:    //调用相册后返回
                if (resultCode == RESULT_OK) {
                    Uri uri = intent.getData();
                    cropPhoto(uri);
                }
                break;
            case CROP_REQUEST_CODE:     //调用剪裁后返回
                try{
                    Bundle bundle = intent.getExtras();
                    if (bundle != null) {
                        //在这里获得了剪裁后的Bitmap对象，可以用于上传
                        image = bundle.getParcelable("data");
                        //设置到ImageView上
                        head.setImageBitmap(image);
                        //也可以进行一些保存、压缩等操作后上传
                        saveBitmap(image);
                    }
                }catch (Exception e){Toast.makeText(getActivity(),"意外的错误",Toast.LENGTH_LONG).show();}

                break;

        }
    }

    /**处理权限返回值
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        switch (requestCode) {
            //权限允许，检查头像
            case STORAGE_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.
                        PERMISSION_GRANTED) {
                    try{
                        File file = new File("/sdcard/yumeng/");
                        if(file.exists()){
                        FileInputStream fis = new FileInputStream("/sdcard/yumeng/head"+UserName+".png");
                        Bitmap bitmap  = BitmapFactory.decodeStream(fis);
                        head.setImageBitmap(bitmap);}
                    }catch (IOException e){
                    }
                } else {
                    Toast.makeText(getActivity(), "You denied the permission", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }
    /**
     * 保存bitmap到本地
     *
     * @param
     * @return
     */
    public  void saveBitmap(Bitmap mBitmap) {
        File file = new File("/sdcard/yumeng/");
        //如果不存在则新建文件夹
        if(!file.exists())file.mkdirs();
        File f = new File("/sdcard/yumeng/" + "head" +UserName+ ".png");
        try {
            f.createNewFile();
        } catch (IOException e) {
            Log.i(TAG,e.toString());
        }
        //保存头像
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
        try {
            fOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 判断登录
     *
     */
    public boolean isLunched(){
        SharedPreferences pref = getActivity().getSharedPreferences("LoginMessage",MODE_PRIVATE);
        boolean status = pref.getBoolean("isLunched",false);
        return  status;
    }
}
