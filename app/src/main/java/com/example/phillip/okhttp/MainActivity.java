package com.example.phillip.okhttp;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = "TestActivity";
    private final OkHttpClient client = new OkHttpClient();
    private TextView tv;
    JSONObject object;

    private static final String url = Constants.IPPRO + "/app/login/login.htm";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv=findViewById(R.id.tv);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    enqueueHandler();
//                    execute();
//                    enqueue();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    try {
                        String result=msg.obj.toString();
                        object=new JSONObject(result);
                        String user = object.optString("mtecc.session.user");// 用户id
                        String username = object.optString("mtecc.session.username");
                        String userorg = object.optString("mtecc.session.userorg");// 用户机构id
                        tv.setText(user+"-"+username+"-"+userorg);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };

    //同步请求
    private void execute() throws Exception {

        RequestBody formBody = new FormBody.Builder()
                .add("lname", "dlwanlei")
                .add("password", "000000")
                .build();

        Request request= new Request.Builder().url(url).post(formBody).build();
        Response response=client.newCall(request).execute();
        final String result=response.body().string();
        if (response.isSuccessful()) {
            object=new JSONObject(result);
            final String user = object.optString("mtecc.session.user");// 用户id
            final String username = object.optString("mtecc.session.username");
            final String userorg = object.optString("mtecc.session.userorg");// 用户机构id
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tv.setText(user+":"+username+":"+userorg);
                }
            });
        }else{

        }

    }

    //异步请求
    private void enqueue(){
        RequestBody formBody=new FormBody.Builder()
                .add("lname", "dlwanlei")
                .add("password", "000000")
                .build();

        Request request = new Request.Builder().url(url).post(formBody).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String result=response.body().string();
                try {
                    object=new JSONObject(result);
                    final String user = object.optString("mtecc.session.user");// 用户id
                    final String username = object.optString("mtecc.session.username");
                    final String userorg = object.optString("mtecc.session.userorg");// 用户机构id
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tv.setText(user+":"+username+":"+userorg);
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //handler异步请求
    private void enqueueHandler(){
        RequestBody formBody=new FormBody.Builder()
                .add("lname", "dlwanlei")
                .add("password", "000000")
                .build();

        Request request = new Request.Builder().url(url).post(formBody).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String result=response.body().string();
                Message message= Message.obtain(handler);
                message.what=1;
                message.obj=result;
                handler.sendMessage(message);
            }
        });
    }
}
