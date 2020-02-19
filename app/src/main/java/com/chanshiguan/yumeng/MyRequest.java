package com.chanshiguan.yumeng;



import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;


/**
 * Created by baiqing on 2019/03/28.
 */

//自定义请求类继承自request,实现未实现的方法，包括构造方法
// 并按照需要的数据自己添加参数生成构造方法，定义范型T适用于任何bean类型的数据回传
public class MyRequest<T> extends Request<T>{

    //声明一个Class引用
    private Class<T> clazz;

    //声明回调接口引用
    private Response.Listener<T> rListener;

    //将需要传入的数据添加到构造方法中
    public MyRequest(String url,Class<T> clazz,Response.Listener<T> rListener ,Response.ErrorListener listener) {
        super(Method.GET,url, listener);
        this.clazz =clazz;
        this.rListener =rListener;
    }

    public MyRequest(int method, String url, Class<T> clazz,Response.Listener<T> rListener,Response.ErrorListener listener) {
        super(method, url, listener);
        this.clazz =clazz;
        this.rListener =rListener;
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {

        //网络访问后续得到的数据response取到json字符串
        String json = new String(response.data);

        //用Gson解析得到相应的bean对象
        T t  = new Gson().fromJson(json,clazz);

        //将此对象按照编码返回
        return Response.success(t, HttpHeaderParser.parseCacheHeaders(response));
    }

    @Override
    protected void deliverResponse(T response) {
        if(response!=null){

            //如果上述方法中得到额数据不为空，通过接口回调传回数据
            //类似于异步任务中的最后两个方法
            rListener.onResponse(response);
        }
    }
}
