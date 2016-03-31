package com.example.crm7demoproject;

import com.example.crm7Constant.Constant;
import com.example.crm7Constant.GlobalSettings;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class QueryActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_query);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.query, menu);
		return true;
	}
	
	// ----未完---
	public void queryMatIno(View view){
//		Intent intent = this.getIntent();
//		intent.setClass(this, MatInfoQueryActivity.class);
//		this.startActivityForResult(intent, Constant.Query2MatInfoQuery);
	}
	
	public void queryTransport(View view){
		
		GlobalSettings.Xz_CX = "1";
		Intent intent = this.getIntent();
		intent.setClass(this, TransportActivity.class);
		this.startActivityForResult(intent, Constant.Query2Transport);
	}
	
	
	
	public void queryBack(View view){
		Intent intent = this.getIntent();
		intent.setClass(this, MainActivity.class);
		this.startActivity(intent);
		this.finish();
	}
}
