package com.example.crm7demoproject;

import java.util.ArrayList;
import java.util.List;

import com.example.crm7Constant.GlobalSettings;
import com.example.crm7CustomedPlugIn.MyDialog;
import com.example.crm7CustomedPlugIn.Scan2DInfo;
import com.example.crm7Http.WebHttp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MatInfoQueryActivity extends Activity {
	
	private String userInfo = "";
	private String anlageInfo ="";
	
	private Spinner matInfoWareHouseSpiner;
	private Spinner matInfoProductLineSpinner;
	
	private Spinner rowSpinner5;
	private Spinner columnSpinner4;
	private Spinner layerSpinner3;
	
	private List<String> rowInfo;
	private List<String> columnLayerInfo;
	
	private List<String> matInfoWareHouseList;
	private List<String> matInfoProductLineList;
	
	private TextView matInfoQueryInfo;
	
	private EditText matInfoScanInput;
	
	private String selectAnlageInfo = "";
	private String queryWareHouseSelectInfo = "";
	
	private String selectRawInfo = "";
	private String selectColumnInfo = "";
	private String selectLayerInfo = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mat_info_query);
		
		matInfoScanInput = (EditText) findViewById(R.id.matInfo_scanInput);
		matInfoQueryInfo = (TextView)findViewById(R.id.matInfo_queryInfo);
		
		matInfoWareHouseSpiner = (Spinner) findViewById(R.id.matInfo_wareHouseSpinner);
		matInfoProductLineSpinner = (Spinner)findViewById(R.id.matInfo_productLineSpinner);
		
		rowSpinner5 = (Spinner) findViewById(R.id.rowSpinner5);
		columnSpinner4 = (Spinner) findViewById(R.id.columnSpinner4);
		layerSpinner3 =(Spinner) findViewById(R.id.layerSpinner3);
		
		matInfoWareHouseList = new ArrayList<String>();
		matInfoProductLineList = new ArrayList<String>();
		rowInfo = new ArrayList<String>();
		columnLayerInfo = new ArrayList<String>();
		rowInfo.add("请选择行");
		columnLayerInfo.add("选择列层");
		for(int i=1;i<201;i++){
			rowInfo.add(i+"");
			if(i<=99){
				columnLayerInfo.add(i+"");
			}
		}
		matInfoWareHouseList.add("---------请选择库区---------");
		matInfoProductLineList.add("---------请选择产线--------");

		new Thread(new Runnable() {
			@Override
			public void run() {
				WebHttp webHttp = new WebHttp();
				userInfo = webHttp.userInfo(GlobalSettings.UserName);
				anlageInfo = webHttp.anlageInfo();
				for(String info : userInfo.split(":")){
					matInfoWareHouseList.add(info.split("=")[1]);
				}
				for(String aInfo : anlageInfo.split("=")){
					matInfoProductLineList.add(aInfo);
				}
			}
		}).start();
		
		//库位
		final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
				matInfoWareHouseList);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		matInfoWareHouseSpiner.setAdapter(adapter);
		matInfoWareHouseSpiner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, final View view, int position, long id) {
				String selectInfo = adapter.getItem(position).toString();
				queryWareHouseSelectInfo = selectInfo.startsWith("-----")?"":selectInfo;
				parent.setVisibility(View.VISIBLE);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				parent.setVisibility(View.VISIBLE);
			}
		});
		// 产线
		final ArrayAdapter<String> productLineAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, matInfoProductLineList);
		productLineAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		matInfoProductLineSpinner.setAdapter(productLineAdapter);
		matInfoProductLineSpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, final View view, int position, long id) {
				String selectInfo = productLineAdapter.getItem(position).toString();
				selectAnlageInfo = selectInfo.startsWith("-----")?"":selectInfo;
				parent.setVisibility(View.VISIBLE);
			}
			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				parent.setVisibility(View.VISIBLE);
			}
		});
		
		// 行
		final ArrayAdapter<String> rowAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, rowInfo);
		rowAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		rowSpinner5.setAdapter(rowAdapter);
		rowSpinner5.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, final View view, int position, long id) {
				String selectInfo = rowAdapter.getItem(position).toString();
				selectRawInfo = selectInfo.startsWith("请")?"":selectInfo;
				parent.setVisibility(View.VISIBLE);
			}
			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				parent.setVisibility(View.VISIBLE);
			}
		});
		
		// 列
		final ArrayAdapter<String> columnAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, columnLayerInfo);
		columnAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		columnSpinner4.setAdapter(columnAdapter);
		columnSpinner4.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, final View view, int position, long id) {
				String selectInfo = columnAdapter.getItem(position).toString();
				selectColumnInfo = selectInfo.startsWith("选")?"":selectInfo;
				parent.setVisibility(View.VISIBLE);
			}
			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				parent.setVisibility(View.VISIBLE);
			}
		});
		
		// 层
		final ArrayAdapter<String> layerAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, columnLayerInfo);
		layerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		layerSpinner3.setAdapter(layerAdapter);
		layerSpinner3.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, final View view, int position, long id) {
				String selectInfo = layerAdapter.getItem(position).toString();
				selectLayerInfo = selectInfo.startsWith("选")?"":selectInfo;
				parent.setVisibility(View.VISIBLE);
			}
			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				parent.setVisibility(View.VISIBLE);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.mat_info_query, menu);
		return true;
	}
	
	//Query
	@SuppressLint("DefaultLocale")
	public void matInfoQuery(final View view){
		String scanInfo = matInfoScanInput.getText().toString().trim().toUpperCase();
		final String scanInfoMatNo = scanInfo.length()<20?scanInfo:Scan2DInfo.scanInfoSplit(scanInfo);
		
		if(queryWareHouseSelectInfo.equals("")||selectAnlageInfo.equals("")){
			Toast.makeText(MatInfoQueryActivity.this, "请确认库位和产线已经输入", Toast.LENGTH_SHORT).show();
		}else{
			matInfoScanInput.setText("");
			new Thread(new Runnable() {
				String yskw = "";
				String newRk = "";
				String getResult = "";
				@Override
				public void run() {
					WebHttp webHttp = new WebHttp();
					getResult = webHttp.MES_HQWL(scanInfoMatNo, selectAnlageInfo);
					view.post(new Runnable() {
						@Override
						public void run() {
							Toast.makeText(MatInfoQueryActivity.this, getResult, Toast.LENGTH_SHORT+Toast.LENGTH_LONG).show();
						}
					}) ;
					yskw = webHttp.CX_KW(scanInfoMatNo);
					if(yskw.length()>1){//钢卷在系统中，只能是在途/生产、库中
						newRk = webHttp.New_RK(scanInfoMatNo, webHttp.Cx_KW1(queryWareHouseSelectInfo),
								"CRM7", selectRawInfo, selectColumnInfo, selectLayerInfo, GlobalSettings.UserName);
						view.post(new Runnable() {
							public void run() {
								matInfoQueryInfo.setText(scanInfoMatNo+" "+yskw);
								Toast.makeText(MatInfoQueryActivity.this, newRk, Toast.LENGTH_LONG).show();
							}
						});
					}else{
						final String insertInfo = webHttp.MES_CRM(scanInfoMatNo, webHttp.Cx_KW1(queryWareHouseSelectInfo), GlobalSettings.UserName);
						view.post(new Runnable() {
							@Override
							public void run() {
								Dialog dialog = new Dialog(MatInfoQueryActivity.this);
								
								MyDialog.Builder builder = new MyDialog.Builder(MatInfoQueryActivity.this);
								builder.setTitle("操作提示");  
								builder.setMessage(R.string.dialog_msg);  
								
								builder.setConfirmButton("确 定", new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog, int which) {
										Toast.makeText(MatInfoQueryActivity.this, insertInfo, Toast.LENGTH_LONG).show();
									}
								});
								builder.setBackButton(R.string.dialog_back, new DialogInterface.OnClickListener() {  
							            @Override  
							            public void onClick(DialogInterface arg0, int arg1) { 
							            	arg0.cancel();
							            }  
							        });  
								dialog = builder.create();
								dialog.show();
							}
						});
					}
					
				}
			}).start();
		}
	}
	
	//Back
	public void matInfoBack(View view){
		Intent intent = this.getIntent();
		intent.setClass(this, MainActivity.class);
		this.startActivity(intent);
		this.finish();
	}
}
