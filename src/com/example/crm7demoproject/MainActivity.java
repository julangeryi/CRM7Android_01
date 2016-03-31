package com.example.crm7demoproject;

import com.example.crm7Constant.Constant;
import com.example.crm7Constant.GlobalSettings;
import com.example.crm7demoproject.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	

	
	public void inWareHouse(View view){
		Intent intent = this.getIntent();
		intent.setClass(this, InWareHouseActivity.class);
		this.startActivityForResult(intent, Constant.Main2InWareHouse);
	}
	
	//MatInfoQuery
	public void matInfoQuery(View view){
		Intent intent = this.getIntent();
		intent.setClass(this, MatInfoQueryActivity.class);
		this.startActivityForResult(intent, Constant.Main2MatInfoQuery);
	}
	
	//MatStateQuery
	public void matStateQuery(View view){
		Intent intent = this.getIntent();
		intent.setClass(this, MatStateQueryActivity.class);
		this.startActivityForResult(intent, Constant.Main2MatStateQuery);
	}

	//OutWareHouse
	public void outWareHouse(View view){
		GlobalSettings.Xz = "1";
		Intent intent = this.getIntent();
		intent.setClass(this, OutWareHouseActivity.class);
		this.startActivityForResult(intent, Constant.Main2OutWareHouse);
	}
	
	//Query
	public void query(View view){
		GlobalSettings.Xz_CX = "1";
		Intent intent = this.getIntent();
		intent.setClass(this, TransportActivity.class);
		this.startActivityForResult(intent, Constant.Main2Transport);
	}
	//CheckWareHouse
	public void checkWareHouse(View view){
		Intent intent = this.getIntent();
		intent.setClass(this, CheckWareHouseActivity.class);
		this.startActivityForResult(intent, Constant.Main2CheckWareHouse);
	}
}
