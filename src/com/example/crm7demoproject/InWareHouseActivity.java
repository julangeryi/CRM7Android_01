package com.example.crm7demoproject;

import java.util.ArrayList;
import java.util.List;

import com.example.crm7Constant.GlobalSettings;
import com.example.crm7CustomedPlugIn.Scan2DInfo;
import com.example.crm7Http.WebHttp;
import com.example.crm7SqliteDB.service.OffLineDBService;
import com.example.crm7SqliteDB.service.impl.OffLineDBServiceImpl;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

@ContentView(R.layout.activity_in_ware_house)
public class InWareHouseActivity extends Activity {

	@ViewInject(R.id.in_editText1)
	private EditText inEditText1;

	@ViewInject(R.id.in_spinner1)
	private Spinner inSpinner1;
	private String inSpinner1Select = "";

	@ViewInject(R.id.in_spinner4)
	private Spinner inRaw;
	private String inRawSelect = "";

	@ViewInject(R.id.in_spinner3)
	private Spinner inColumn;
	private String inColumnSelect = "";

	@ViewInject(R.id.in_spinner2)
	private Spinner inLayer;
	private String inLayerSelect = "";

	@ViewInject(R.id.in_WareContent)
	private EditText inWareContent;
	private String gjid = "";
	private String yskw = "";
	private String newRk = "";
	private String stockId = "";

	private Message msg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		ViewUtils.inject(InWareHouseActivity.this);

		msg = Message.obtain();
		final List<String> wareHouse = new ArrayList<String>();

		// info of Row
		List<String> rowInfo = new ArrayList<String>();
		wareHouse.add("-------请选择库位-------");
		rowInfo.add("选择行");
		for (int i = 1; i < 201; i++) {
			rowInfo.add(i + "");
		}

		// info of column & layer
		List<String> columnLayerInfo = new ArrayList<String>();
		columnLayerInfo.add("选择列层");
		for (int i = 1; i < 100; i++) {
			columnLayerInfo.add(i + "");
		}

		// info of Library
		OffLineDBService offLineDBService = new OffLineDBServiceImpl();
		wareHouse.addAll(offLineDBService.offLineLibrary(this, GlobalSettings.UserName));

		// 库位
		final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
				wareHouse);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		inSpinner1.setAdapter(adapter);
		inSpinner1.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, final View view, int position, long id) {
				inSpinner1Select = adapter.getItem(position).toString().startsWith("-----") ? ""
						: adapter.getItem(position).toString();
				parent.setVisibility(View.VISIBLE);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				parent.setVisibility(View.VISIBLE);
			}
		});

		// 行
		final ArrayAdapter<String> adapterRow = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
				rowInfo);
		adapterRow.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		inRaw.setAdapter(adapterRow);
		inRaw.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, final View view, int position, long id) {
				inRawSelect = adapterRow.getItem(position).toString().startsWith("选择") ? "--"
						: adapterRow.getItem(position).toString();
				parent.setVisibility(View.VISIBLE);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				parent.setVisibility(View.VISIBLE);
			}
		});

		// 列
		final ArrayAdapter<String> adapterColumn = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
				columnLayerInfo);
		adapterColumn.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		inColumn.setAdapter(adapterColumn);
		inColumn.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, final View view, int position, long id) {
				inColumnSelect = adapterColumn.getItem(position).toString().startsWith("选择") ? "--"
						: adapterRow.getItem(position).toString();
				parent.setVisibility(View.VISIBLE);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				parent.setVisibility(View.VISIBLE);
			}
		});

		// 层
		final ArrayAdapter<String> adapterLayer = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
				columnLayerInfo);
		adapterLayer.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		inLayer.setAdapter(adapterLayer);
		inLayer.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, final View view, int position, long id) {
				inLayerSelect = adapterLayer.getItem(position).toString().startsWith("选择") ? "--"
						: adapterRow.getItem(position).toString();
				parent.setVisibility(View.VISIBLE);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				parent.setVisibility(View.VISIBLE);
			}
		});

	}

	public void inWare(final View view) {

		String scanInput = inEditText1.getText().toString().trim();
		inEditText1.setText("");

		final inWareHandler inWareHandler = new inWareHandler();

		if (inSpinner1Select.equals("") || scanInput.equals("") || scanInput == null) {
			Toast.makeText(this, "请确认库位是否选择或扫描框已经扫描条码", Toast.LENGTH_SHORT).show();
		} else {
			if (scanInput.length() > 20)// 二维码
				gjid = Scan2DInfo.scanInfoSplit(scanInput);
			else
				gjid = scanInput;
			Toast.makeText(this, "入库操作中，请等候", Toast.LENGTH_LONG).show();

			new Thread(new Runnable() {
				@Override
				public void run() {
					WebHttp webHttp = new WebHttp();
//					String info = webHttp.inWareHouse(gjid, inSpinner1Select, inRawSelect, inColumnSelect,
//							inLayerSelect, GlobalSettings.UserName);
//					try {
//						yskw = info.split("=")[1].split("~")[0];
//						newRk = info.split("=")[1].split("~")[2];
//					} catch (Exception e) {
						yskw = webHttp.CX_KW(gjid);
						stockId = webHttp.Cx_KW1(inSpinner1Select);
						newRk = webHttp.New_RK(gjid, stockId, "CRM7", inRawSelect, inColumnSelect, inLayerSelect,
								GlobalSettings.UserName);
//					}
					if (yskw.length() > 1) {
						msg.what = 100;
						inWareHandler.sendMessage(msg);
					} else if (yskw.length() == 1) {
						try{
							msg.what = 101;
							inWareHandler.sendMessage(msg);
						}catch(Exception e){
							msg.what = 102;
							inWareHandler.sendMessage(msg);
						}
					}
				}
			}).start();
		}
	}

	public void inWareBack(View view) {
		Intent intent = this.getIntent();
		intent.setClass(this, MainActivity.class);
		this.startActivity(intent);
		this.finish();
	}
	class inWareHandler extends Handler {

		public inWareHandler() {

		}

		public inWareHandler(Looper L) {
			super(L);
		}

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 100:
				inWareContent.setText("钢卷:"+gjid+" 原始库位:"+yskw+"\r\n"+"新入库操作："+newRk);
				break;
			case 101:
				inWareContent.setText("错误信息："+newRk);
				break;
			case 102:
				inWareContent.setText("发生插入异常，导致入库失败");
				break;
			default:
				break;
			}
		}
	}
}


