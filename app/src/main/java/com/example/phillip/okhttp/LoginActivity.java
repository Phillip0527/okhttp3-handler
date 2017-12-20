package com.example.phillip.okhttp;

import org.json.JSONException;
import org.json.JSONObject;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends Activity {
	private LocationManager locationManager;
	private Button btnLogin;
	private ImageButton ibHelp;
	private EditText etUserName;
	private EditText etPassword;
	private TextView errormessage;
	private ProgressBar progress;
	private static final String url = Constants.IPPRO + "/app/login/login.htm";
	public static final String url_visoncheck = Constants.IPPRO
			+ "/app/requestVersion/getServiceAppVersion.htm";
	private static final String TAG = "TestTag";
	private String username;
	private String password;
	public int serverVersion ;
	public int localVersion;
	public String versionName;
	public String updateMessage;

	public String contentdata="";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		SharedPreferences sharedPreferences = getSharedPreferences("session",	Context.MODE_PRIVATE);
		String lognamestr = sharedPreferences.getString("logname", "");
		updateMessage="";
		btnLogin = (Button) findViewById(R.id.loginButton);
		// ibHelp = (ImageButton) findViewById(R.id.helpButton);
		etUserName = (EditText) findViewById(R.id.loginame);
		if(lognamestr!=null && !"".equals(lognamestr)){
			etUserName.setText(lognamestr);
		}
		etPassword = (EditText) findViewById(R.id.password);
		errormessage = (TextView) findViewById(R.id.username_Pwd_Problem);
		progress = (ProgressBar) findViewById(R.id.progress_bar);
		// 登录
		//etUserName.setText("dlwanlei");
		//etPassword.setText("000000");
		btnLogin.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				username = etUserName.getText().toString();
				password = etPassword.getText().toString();
				if (TextUtils.isEmpty(username.trim())
						|| TextUtils.isEmpty(password.trim())) {
					errormessage.setVisibility(View.VISIBLE);
					errormessage.setText("用户名密码不能为空");
				} else {
//					// 判断网络
//					if (!IsNetworkAvailable
//							.isNetworkAvailable(getApplicationContext())) {
//						IsNetworkAvailable.showNetDialog(LoginActivity.this);
//					} else {
						// 注意每次需new一个实例,新建的任务只能执行一次,否则会出现异常
					Map<String,String> m=new HashMap<String,String>();
					m.put("lname",username);
					m.put("password",password);
					OkHttpUtils.getInstance().postMap(url, m, new OkHttpUtils.HttpCallBack() {
						@Override
						public void onSusscess(String result) {
// 超时判断
							if (result.equals("-1")) {
								Toast toast = Toast.makeText(getApplicationContext(),
										"连接服务器失败，请检查后重试!", Toast.LENGTH_LONG);
								toast.setGravity(Gravity.CENTER, 0, 0);
								toast.show();
								btnLogin.setVisibility(View.VISIBLE);
								progress.setVisibility(View.GONE);
							} else {
								JSONObject object = null;
								try {
									object = new JSONObject(result);
								} catch (JSONException e) {
									e.printStackTrace();
								}
								String user = object.optString("mtecc.session.user");// 用户id
								String username = object
										.optString("mtecc.session.username");
								String userorg = object.optString("mtecc.session.userorg");// 用户机构id
								String orglevel = object
										.optString("mtecc.session.orglevel");//用户等级
								String userorgname = object
										.optString("mtecc.session.userorgname");
								String logtime = object.optString("mtecc.session.logtime");
								String logname = object.optString("mtecc.session.logname");
								String password = object
										.optString("mtecc.session.password");
								String scopeMeter=object.optString("mtecc.session.scopeMeter");
								if (!user.equals(null) && user.length() > 0) {
									SharedPreferences sharedPreferences = getSharedPreferences(
											"session", Context.MODE_PRIVATE);
									Editor editor = sharedPreferences.edit();
									editor.putString("userid", user);
									editor.putString("username", username);
									editor.putString("userorgid", userorg);
									editor.putString("orglevel", orglevel);
									editor.putString("userorgname", userorgname);
									editor.putString("logtime", logtime);
									editor.putString("logname", logname);
									editor.putString("password", password);
									editor.putString("scopeMeter", scopeMeter);
									editor.commit();// 提交修改
									Intent in = new Intent(LoginActivity.this,
											MmpMainActivity.class);
									startActivity(in);
									finish();
								} else {
									TextView textView = (TextView) findViewById(R.id.username_Pwd_Problem);
									textView.setText("error");
									textView.setVisibility(View.VISIBLE);
									btnLogin.setVisibility(View.VISIBLE);
									progress.setVisibility(View.GONE);
								}
							}
						}
					});
//						LongTask task = new LongTask();
//						task.execute(username, password);
//					}
				}
				/*
				 * Intent in = new Intent(LoginActivity.this,
				 * MmpMainActivity.class); startActivity(in); finish();
				 */

			}
		});
		
	}
	






}
