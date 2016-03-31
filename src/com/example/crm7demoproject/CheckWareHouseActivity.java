package com.example.crm7demoproject;

import java.util.ArrayList;
import java.util.List;

import com.example.crm7Base.Crm7ActivityManager;
import com.example.crm7Constant.GlobalSettings;
import com.example.crm7CustomedPlugIn.OffLineScan2DInfo;
import com.example.crm7CustomedPlugIn.Scan2DInfo;
import com.example.crm7Http.WebHttp;
import com.example.crm7SqliteDB.service.OffLineDBService;
import com.example.crm7SqliteDB.service.impl.OffLineDBServiceImpl;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("InflateParams")
public class CheckWareHouseActivity extends Activity {

	private String spinnerInfo = "";
	private String anlageInfo = "";
	private String rawInfo = "";
	private String columnInfo = "";
	private String layerInfo = "";
	private TextView outQueryInfo;

	private EditText checkWareScanInput;

	private Spinner checkWareHouseSpinner1;
	private List<String> checkWareHouseSpinner1List;

	private Spinner checkAnlageSpinner1;
	private List<String> checkAnlageSpinner1List;

	private Spinner checkRawSpinner3;
	private List<String> checkRawSpinner3List;

	private Spinner checkColumnSpinner2;
	private List<String> checkColumnSpinner2List;

	private Spinner checkLayerSpinner4;
	private List<String> checkLayerSpinner4List;

	private EditText checkCount;
	// private boolean onLineCheckFlag = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_check_ware_house);
		Crm7ActivityManager.getInstance().addActivity(CheckWareHouseActivity.this);

		checkWareScanInput = (EditText) findViewById(R.id.check_WareScanInput);
		checkCount = (EditText) findViewById(R.id.check_count);

		checkCount.setInputType(InputType.TYPE_NULL);

		checkWareHouseSpinner1 = (Spinner) findViewById(R.id.check_wareHouseSpinner1);
		checkAnlageSpinner1 = (Spinner) findViewById(R.id.check_anlageSpinner1);
		checkRawSpinner3 = (Spinner) findViewById(R.id.check_rawSpinner3);
		checkColumnSpinner2 = (Spinner) findViewById(R.id.check_columnSpinner2);
		checkLayerSpinner4 = (Spinner) findViewById(R.id.check_layerSpinner4);

		checkWareHouseSpinner1List = new ArrayList<String>();
		checkRawSpinner3List = new ArrayList<String>();
		checkColumnSpinner2List = new ArrayList<String>();
		checkLayerSpinner4List = new ArrayList<String>();
		checkAnlageSpinner1List = new ArrayList<String>();

		checkWareHouseSpinner1List.add("----请选择库位----");
		checkAnlageSpinner1List.add("----选择产线----");
		checkRawSpinner3List.add("选择行");
		checkColumnSpinner2List.add("选择列");
		checkLayerSpinner4List.add("选择层");
		for (int i = 1; i < 201; i++) {
			checkRawSpinner3List.add(i + "");
			if (i < 100) {
				checkColumnSpinner2List.add(i + "");
				checkLayerSpinner4List.add(i + "");
			}
		}

		outQueryInfo = (TextView) findViewById(R.id.out_queryInfo);

		// 库位
		final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
				checkWareHouseSpinner1List);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		checkWareHouseSpinner1.setAdapter(adapter);
		checkWareHouseSpinner1.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, final View view, int position, long id) {
				spinnerInfo = adapter.getItem(position).toString().startsWith("---") ? ""
						: adapter.getItem(position).toString();
				parent.setVisibility(View.VISIBLE);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				parent.setVisibility(View.VISIBLE);
			}
		});

		// 产线
		final ArrayAdapter<String> anlageAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
				checkAnlageSpinner1List);
		anlageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		checkAnlageSpinner1.setAdapter(anlageAdapter);
		checkAnlageSpinner1.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, final View view, int position, long id) {
				anlageInfo = anlageAdapter.getItem(position).toString().startsWith("----") ? ""
						: anlageAdapter.getItem(position).toString();
				parent.setVisibility(View.VISIBLE);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				parent.setVisibility(View.VISIBLE);
			}
		});
		// 行
		final ArrayAdapter<String> rawAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
				checkRawSpinner3List);
		rawAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		checkRawSpinner3.setAdapter(rawAdapter);
		checkRawSpinner3.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, final View view, int position, long id) {
				rawInfo = rawAdapter.getItem(position).toString().startsWith("选") ? "0"
						: rawAdapter.getItem(position).toString();
				parent.setVisibility(View.VISIBLE);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				parent.setVisibility(View.VISIBLE);
			}
		});
		// 列
		final ArrayAdapter<String> columnAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
				checkColumnSpinner2List);
		columnAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		checkColumnSpinner2.setAdapter(columnAdapter);
		checkColumnSpinner2.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, final View view, int position, long id) {
				columnInfo = columnAdapter.getItem(position).toString().startsWith("选") ? "0"
						: columnAdapter.getItem(position).toString();
				parent.setVisibility(View.VISIBLE);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				parent.setVisibility(View.VISIBLE);
			}
		});
		// 层
		final ArrayAdapter<String> layerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
				checkLayerSpinner4List);
		layerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		checkLayerSpinner4.setAdapter(layerAdapter);
		checkLayerSpinner4.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, final View view, int position, long id) {
				layerInfo = layerAdapter.getItem(position).toString().startsWith("选") ? "0"
						: layerAdapter.getItem(position).toString();
				parent.setVisibility(View.VISIBLE);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				parent.setVisibility(View.VISIBLE);
			}
		});

		if (GlobalSettings.Xz_Pk == "2") { // 离线盘库
			OffLineDBService offLineDBService = new OffLineDBServiceImpl();
			String position = offLineDBService.offLinePosition(this, GlobalSettings.UserName);
			for (String temp : position.split("=")) {
				checkWareHouseSpinner1List.add(temp);
			}
		} else if (GlobalSettings.Xz_Pk == "1") {
			OffLineDBService offLineDBService = new OffLineDBServiceImpl();
			checkWareHouseSpinner1List.addAll(offLineDBService.offLineLibrary(CheckWareHouseActivity.this, GlobalSettings.UserName));
			checkAnlageSpinner1List.addAll(offLineDBService.offLineAnlage(CheckWareHouseActivity.this));

		}
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
		if(4 == keyCode){
			AlertDialog.Builder builder = new AlertDialog.Builder(CheckWareHouseActivity.this);
			final AlertDialog dialog = builder.create();
			LayoutInflater inflater = getLayoutInflater();
			ViewGroup group = (ViewGroup) inflater.inflate(R.layout.dialog_exit,null);
			Button btnExit = (Button) group.findViewById(R.id.ext_btn_1);
			Button btnCancel = (Button) group.findViewById(R.id.ext_btn_2);
			btnExit.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Crm7ActivityManager.getInstance().exit();
					dialog.cancel();
				}
			});
			btnCancel.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					dialog.cancel();
				}
			});
			dialog.setTitle("是否退出?");
			dialog.setView(group);
			dialog.show();
		}
		
		return super.onKeyUp(keyCode, event);
	}

	public void checkWareBackup(final View view) {
		boolean onOffLineCondition = false;
		String checkWareInfo = checkWareScanInput.getText().toString();
		checkWareScanInput.setText("");
		if (GlobalSettings.Xz_Pk == "1") { // 在线
			onOffLineCondition = spinnerInfo.equals("") || checkWareInfo.equals("") || anlageInfo.equals("");
		} else {
			onOffLineCondition = spinnerInfo.equals("") || checkWareInfo.equals("");
		}
		if (onOffLineCondition) {
			Toast.makeText(CheckWareHouseActivity.this, "确认扫描到条码或选择库位或产线", Toast.LENGTH_LONG).show();
		} else {
			if (checkWareInfo.length() > 20) { // 二维
				if (GlobalSettings.Xz_Pk == "1") { // 二维在线
					onLine2D(view, checkWareInfo);
				} else { // 二位离线
					offLine2D(view, checkWareInfo);
				}
			} else { // 一维
				if (GlobalSettings.Xz_Pk == "1") {
					onLine1D(view, checkWareInfo); // 一维在线
				} else {
					offLine1D(view, checkWareInfo);
				}
			}
		}

	}

	private void onLine1D(final View view, final String scanTextInfo) {
		new Thread(new Runnable() {
			String count = "";
			boolean onLineCheckFlag = false;

			@Override
			public void run() {
				WebHttp webHttp = new WebHttp();
				onLineCheckFlag = webHttp.ScanIn(scanTextInfo + "|" + scanTextInfo + "|", GlobalSettings.UserName,
						new String[] { webHttp.Cx_KW1(spinnerInfo), rawInfo, columnInfo, layerInfo });
				if (onLineCheckFlag) {
					count = webHttp.PkCx();
					view.post(new Runnable() {
						@Override
						public void run() {
							outQueryInfo.setText(scanTextInfo + "保存成功");
							checkCount.setText(count + "");
						}
					});
				}
			}
		}).start();

	}

	private void offLine1D(final View view, String checkWareInfo) {
		OffLineDBService offLineDBService = new OffLineDBServiceImpl();
		if (offLineDBService.offLineCarNo(this, checkWareInfo) != 0) {
			String info = "卡号为：" + checkWareInfo + "已经在数据库中，不能保存";
			Toast.makeText(this, info, Toast.LENGTH_LONG).show();
		} else {
			String re = offLineDBService.scanIn(this,  new String[]{checkWareInfo,checkWareInfo}, GlobalSettings.UserName,
					new String[] { spinnerInfo, rawInfo, columnInfo, layerInfo }) ? "保存成功" : "未保存成功";
			outQueryInfo.setText(checkWareInfo + " " + re);
		}
		checkCount.setText(offLineDBService.offLineCheckNum(this));
	}

	private void onLine2D(final View view, final String checkInfo) {
		new Thread(new Runnable() {
			String count = "";
			boolean onLineCheckFlag = false;

			@Override
			public void run() {
				String onLine2DInfo = "";
				String backResult = "";
				WebHttp webHttp = new WebHttp();
				backResult = webHttp.get2DInfo(Scan2DInfo.scanInfoSplit(checkInfo));
				if (backResult.equals("数据库没有此物料")) {
					onLineCheckFlag = false;
				} else {
					for (String split1 : backResult.split(";")) {
						onLine2DInfo += (split1.split("=")[1].equals("0")||split1.split("=")[1].equals("")||split1.split("=")[1]==null)?" "+"|":split1.split("=")[1] + "|";
					}
					onLineCheckFlag = webHttp.ScanIn(onLine2DInfo, GlobalSettings.UserName,
							new String[] { webHttp.Cx_KW1(spinnerInfo), rawInfo, columnInfo, layerInfo });
				}
				if (onLineCheckFlag) {
					count = webHttp.PkCx();
					view.post(new Runnable() {
						@Override
						public void run() {
							outQueryInfo.setText(Scan2DInfo.scanInfoSplit(checkInfo) + "保存成功");
							checkCount.setText(count + "");
						}
					});
				} else {
					view.post(new Runnable() {
						@Override
						public void run() {
							outQueryInfo.setText("保存失败,请检查扫描数据");
						}
					});
				}

			}
		}).start();
	}

	private void offLine2D(final View view, String checkWareInfo) {
		String[] t = OffLineScan2DInfo.offLineScanInfo(checkWareInfo);
		OffLineDBService offLineDBService = new OffLineDBServiceImpl();
		String kph = t[0];
		if (offLineDBService.offLineCarNo(this, kph) != 0) {
			String info = "卡号为：" + kph + "已经在数据库中，不能保存";
			Toast.makeText(this, info, Toast.LENGTH_LONG).show();
		} else {
			String re = offLineDBService.scanIn(this,  t, GlobalSettings.UserName,
					new String[] { spinnerInfo, rawInfo, columnInfo, layerInfo }) ? "保存成功" : "未保存成功";
			outQueryInfo.setText(kph + " " + re);
		}
		checkCount.setText(offLineDBService.offLineCheckNum(this));
	}

	public void checkBack(View view) {
		Intent intent = this.getIntent();
		Class<?> destination = GlobalSettings.Xz_Pk == "2" ? OffLineMainActivity.class : MainActivity.class;
		intent.setClass(this, destination);
		this.startActivity(intent);
		this.finish();

	}

}
