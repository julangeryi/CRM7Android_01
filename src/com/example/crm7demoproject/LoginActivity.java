package com.example.crm7demoproject;

import java.util.ArrayList;
import java.util.List;

import com.example.crm7Constant.GlobalSettings;
import com.example.crm7CustomedPlugIn.LocalToast;
import com.example.crm7Http.WebHttp;
import com.example.crm7SqliteDB.service.OffLineDBService;
import com.example.crm7SqliteDB.service.impl.OffLineDBServiceImpl;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

@ContentView(R.layout.activity_login)
public class LoginActivity extends Activity {

	private String userName = "";

	@ViewInject(R.id.usernameEditText)
	private Spinner userNameView;

	@ViewInject(R.id.passwordEditText)
	private TextView passWordView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ViewUtils.inject(LoginActivity.this);

		//Initial UserSpinner List
		List<String> userList = new ArrayList<String>();
		OffLineDBService offLineDBService = new OffLineDBServiceImpl();
		userList.addAll(offLineDBService.offLineUser(LoginActivity.this));
		
		final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
				userList);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		userNameView.setAdapter(adapter);
		
		//Spinner Action listener
		userNameView.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, final View view, int position, long id) {
				userName = adapter.getItem(position).toString().trim().startsWith("-----") ? ""
						: adapter.getItem(position).toString();
				parent.setVisibility(View.VISIBLE);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				parent.setVisibility(View.VISIBLE);
			}
		});
	}

	// 在线登陆
	public void login(final View view) {

		final String passWord = passWordView.getText().toString();
		final LocalToast localToast = new LocalToast(LoginActivity.this);
		new Thread(new Runnable() {
			@Override
			public void run() {
				if(userName.equals("")||userName==null||userName.equals("请选择用户")||passWord.equals("")||passWord==null){
					view.post(new Runnable() {
						public void run() {
							Toast.makeText(LoginActivity.this, R.string.errorOfLogin, Toast.LENGTH_SHORT).show();
						}
					});
				}else{
					WebHttp webHttp = new WebHttp();
					view.post(new Runnable() {
						@Override
						public void run() {
							localToast.localToast(" 登陆中", getLayoutInflater());							
						}
					});
					String result = webHttp.getResponse(userName,passWord);
					if (result.equals("网络超时：网络信号欠佳，请等候")) {
						view.post(new Runnable() {
							public void run() {
								Toast.makeText(LoginActivity.this, "网络超时：网络信号欠佳，请等候", Toast.LENGTH_SHORT).show();
								reset(view);
							}
						});
					} else if (result.equals("true")) {
						GlobalSettings.UserName = userName;
						GlobalSettings.Xz_Pk = "1";
						Intent intent = new Intent();
						intent.setClass(LoginActivity.this, MainActivity.class);
						startActivity(intent);
						LoginActivity.this.finish();
					} else {
						view.post(new Runnable() {
							public void run() {
								Toast.makeText(LoginActivity.this, R.string.errorOfLogin, Toast.LENGTH_SHORT).show();
								reset(view);
							}
						});
					}
				}
			}
		}).start();
	}

	// 离线登陆
	public void offLineLogin(View view) {
		final String passWord = passWordView.getText().toString();
		boolean checkFlag = (userName.equals("") || userName == null) || (passWord.equals("") || passWord == null);
		if (checkFlag) {
			Toast.makeText(LoginActivity.this, "请检查离线输入的用户名或密码", Toast.LENGTH_SHORT).show();
		} else {
			OffLineDBService offLineDBService = new OffLineDBServiceImpl();
			if (offLineDBService.verification(LoginActivity.this, userName, passWord)) {
				GlobalSettings.UserName = userName;
				GlobalSettings.Xz_Pk = "2";
				Intent offIntent = new Intent();
				offIntent.setClass(this, OffLineMainActivity.class);
				this.startActivity(offIntent);
				this.finish();
			} else {
				Toast.makeText(LoginActivity.this, "用户名和密码不匹配", Toast.LENGTH_SHORT).show();
			}
		}
	}

	// 退出
	public void exit(View view) {
		this.finish();
	}

	// 输入重置
	public void reset(View view) {
		passWordView.setText("");
	}
}
