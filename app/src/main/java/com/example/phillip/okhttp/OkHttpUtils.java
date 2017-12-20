package com.example.phillip.okhttp;


import android.os.Handler;
import android.os.Looper;



import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Description : OkHttp�������ӷ�װ������
 * Author : lxy
 * Date   : 2016/05/05
 */

public class OkHttpUtils {
    private static OkHttpUtils mInstance;

    private OkHttpClient client;
    //��ʱʱ��
    public static final int TIMEOUT=1000*60;

    //json����
    public static final MediaType JSON = MediaType
            .parse("application/json; charset=utf-8");

    private Handler handler = new Handler(Looper.getMainLooper());

    public OkHttpUtils() {
        this.init();
    }

    private void init() {
        client=new OkHttpClient();

        //���ó�ʱ
        client.newBuilder().connectTimeout(TIMEOUT, TimeUnit.SECONDS).
                writeTimeout(TIMEOUT,TimeUnit.SECONDS).readTimeout(TIMEOUT, TimeUnit.SECONDS)
                .build();
    }

    /**
     * ͨ������ģʽ�������
     * @return OkHttpUtils
     */
    public synchronized static OkHttpUtils getInstance() {
        if (mInstance == null) {
            mInstance = new OkHttpUtils();
        }
        return mInstance;
    }



    /**
     * post����  json����Ϊbody
     *
     */
    public void postJson(String url,String json,final HttpCallBack callBack){
        RequestBody body = RequestBody.create(JSON,json);
        final Request request = new Request.Builder().url(url).post(body).build();

        OnStart(callBack);

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                OnError(callBack, e.getMessage());
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    onSuccess(callBack, response.body().string());
                } else {
                    OnError(callBack, response.message());
                }
            }
        });
    }
    /**
     * post����  map��body
     *
     * @param url
     * @param map
     * @param callBack
     */
    public void postMap(String url,Map<String,String> map,final HttpCallBack callBack){
        FormBody.Builder builder=new FormBody.Builder();

        //����map
        if(map!=null){
            for (Map.Entry<String,String> entry : map.entrySet()){
                builder.add(entry.getKey(), entry.getValue().toString());
            }
        }
        RequestBody body = builder.build();
        Request request = new Request.Builder().url(url).post(body).build();
        OnStart(callBack);
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                OnError(callBack,e.getMessage());
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){
                    onSuccess(callBack,response.body().string());
                }else{
                    OnError(callBack, response.message());
                }
            }
        });
    }


    /**
     * get ����
     *
     * @param url
     * @param callBack
     */
    public void getJson(String url,final HttpCallBack callBack){
        Request request = new Request.Builder().url(url).build();
        OnStart(callBack);
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                OnError(callBack,e.getMessage());
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()){
                    onSuccess(callBack,response.body().string());
                }else{
                    OnError(callBack,response.message());
                }
            }
        });
    }
    public void OnStart(HttpCallBack callBack){
        if(callBack!=null){
            callBack.onstart();
        }
    }
    public void onSuccess(final HttpCallBack callBack,final String data){
        if(callBack!=null){
            handler.post(new Runnable() {
                @Override
                public void run() {//�����̲߳���
                    callBack.onSusscess(data);
                }
            });
        }
    }
    public void OnError(final HttpCallBack callBack,final String msg){
        if(callBack!=null){
            handler.post(new Runnable() {
                @Override
                public void run() {
                    callBack.onError(msg);
                }
            });
        }
    }
    public static abstract class HttpCallBack{
        //��ʼ
        public void onstart(){};
        //�ɹ��ص�
        public abstract void onSusscess(String data);
        //ʧ��
        public void onError(String meg){};
    }

}