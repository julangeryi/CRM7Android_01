package com.example.crm7demoproject;

import com.example.crm7CustomedPlugIn.Scan2DInfo;
import com.example.crm7Http.WebHttp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MatStateQueryActivity extends Activity {

	private TextView matSteelGradeNumScanText;
	private TextView matQueryInfoText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mat_state_query);
		matSteelGradeNumScanText = (TextView) findViewById(R.id.mat_steelGradeNumScan);
		matQueryInfoText = (TextView) findViewById(R.id.mat_queryInfo);
	}

	// 查询
	@SuppressLint("DefaultLocale")
	public void matClear(final View view) {
		String steelScanGradeNo = matSteelGradeNumScanText.getText().toString().trim();
		final String steelGradeNo = steelScanGradeNo.length()>20?Scan2DInfo.scanInfoSplit(steelScanGradeNo):steelScanGradeNo;
		new Thread(new Runnable() {
			String matQueryInfo = "";
			@Override
			public void run() {
				WebHttp webHttp = new WebHttp();
				String tempInfo = webHttp.matStateQuery(steelGradeNo);
				matQueryInfo = tempInfo.split(";").length>2?"营销部库存标识为:"+tempInfo.split(";")[3]+"\r\n"+"配车状态:"+tempInfo.split(";")[4]
								+"\r\n"+"冷轧钢卷号为"+tempInfo.split(";")[2]:tempInfo;
				view.post(new Runnable() {
					@Override
					public void run() {
						matQueryInfoText.setText(matQueryInfo);
						matSteelGradeNumScanText.setText("");
					}
				});
			}
		}).start();
	}
	// 返回
	public void matBack(View view) {

		Intent intent = this.getIntent();
		intent.setClass(this, MainActivity.class);
		this.startActivity(intent);
		this.finish();
	}
}
