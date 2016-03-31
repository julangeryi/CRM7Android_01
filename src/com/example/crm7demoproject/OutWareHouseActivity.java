package com.example.crm7demoproject;

import com.example.crm7Constant.GlobalSettings;
import com.example.crm7CustomedPlugIn.Scan2DInfo;
import com.example.crm7Http.WebHttp;
import com.example.crm7SqliteDB.service.OffLineDBService;
import com.example.crm7SqliteDB.service.impl.OffLineDBServiceImpl;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

@ContentView(R.layout.activity_out_ware_house)
public class OutWareHouseActivity extends Activity {

	private String scanInfo = "";
	private String gjid = "";

	@ViewInject(R.id.out_inputText)
	private EditText outInputText;

	@ViewInject(R.id.out_queryInfo)
	private EditText outQueryInfo;

	@ViewInject(R.id.editText1)
	private EditText editTextOfCarNum;

	@ViewInject(R.id.editText2)
	private EditText editTextOfDriverNum;

	private String ckresult = " ";
	private String infoOf2D = " ";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ViewUtils.inject(OutWareHouseActivity.this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.out_ware_house, menu);
		return true;
	}

	@SuppressLint("DefaultLocale")
	public void outWareHouse(final View view) {
		scanInfo = this.outInputText.getText().toString().trim();
		final String carNum = editTextOfCarNum.getText().toString().trim();
		final String driverNum = editTextOfDriverNum.getText().toString().trim();
		this.outInputText.setText("");

		outInputText.setFocusable(true);
		outInputText.requestFocus(R.id.out_inputText);

		gjid = scanInfo.length() < 20 ? scanInfo : Scan2DInfo.scanInfoSplit(scanInfo);
		boolean flagCarNum = carNum.equals("") || carNum == null;
		boolean flagDriverNum = driverNum.equals("") || driverNum == null;
		// 正常出库
		if (flagCarNum && flagDriverNum) {
			Toast.makeText(OutWareHouseActivity.this, "出库操作处理中", Toast.LENGTH_LONG).show();

			new Thread(new Runnable() {
				@Override
				public void run() {

					WebHttp webHttp = new WebHttp();
					String yskw = webHttp.CX_KW(gjid).toString();
					if (yskw.length() > 1) {
						ckresult = GlobalSettings.Xz == "1" ? webHttp.CX(gjid, GlobalSettings.UserName) : "普通查询";
						view.post(new Runnable() {
							@Override
							public void run() {
								outQueryInfo.setText("钢卷号为 :" + gjid + ckresult);
							}
						});
					} else {
						view.post(new Runnable() {
							@Override
							public void run() {
								outQueryInfo.setText(gjid + " 该钢卷在系统中不存在");
							}
						});
					}
				}

			}).start();
		} else if ((!flagCarNum) && (!flagDriverNum)) { // 转运

			new Thread(new Runnable() {

				@Override
				public void run() {

					view.post(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							Toast.makeText(OutWareHouseActivity.this, "出库转运操作处理中", Toast.LENGTH_LONG).show();
						}
					});

					WebHttp webHttp = new WebHttp();
					String yskw = webHttp.CX_KW(gjid).toString();
					infoOf2D = webHttp.get2DInfo(gjid);

					if (yskw.length() > 1) {
						ckresult = GlobalSettings.Xz == "1" ? webHttp.CX(gjid, GlobalSettings.UserName) : "普通查询";
						view.post(new Runnable() {
							@Override
							public void run() {
								// TODO Auto-generated method stub
								outQueryInfo.setText("钢卷号为 :" + gjid + ckresult);
							}
						});
						if (ckresult.equals("操作成功")) {
							// 转运数据插入
							
							OffLineDBService offLineDBService = new OffLineDBServiceImpl();
							if (scanInfo.length() > 20) {
								offLineDBService.offLineTransInfoScanIn(OutWareHouseActivity.this,
										change2Array(infoOf2D.split(";"), carNum, yskw, driverNum));
							} else {
								offLineDBService.offLineTransInfoScanIn(OutWareHouseActivity.this,
										new String[] { "1D", yskw, carNum, gjid, driverNum, GlobalSettings.UserName });
							}
						}

					} else {
						view.post(new Runnable() {
							@Override
							public void run() {
								Toast.makeText(OutWareHouseActivity.this, "出库失败", Toast.LENGTH_LONG).show();
							}
						});
					}
				}
			}).start();

		} else {
			if (flagCarNum) {
				Toast.makeText(OutWareHouseActivity.this, "如果使用转运运功能，必须填写车号和司机，如果只是出库，车号和司机不需要填写", Toast.LENGTH_SHORT)
						.show();
			}
			if (flagDriverNum) {
				Toast.makeText(OutWareHouseActivity.this, "如果使用转运运功能，必须填写车号和司机，如果只是出库，车号和司机不需要填写", Toast.LENGTH_SHORT)
						.show();
			}
		}
	}

	public void outBack(View view) {
		Intent intent = this.getIntent();
		intent.setClass(this, MainActivity.class);
		this.startActivity(intent);
		this.finish();

	}

	private String[] change2Array(String[] infoOf2DArray, String carNum, String yskw, String driverNum) {
		String[] arr = new String[12];
		arr[0] = "2D";
		arr[1] = carNum;// "车号";
		arr[2] = yskw; // "库区"
		arr[3] = gjid; // 卡号
		try {
			arr[4] = infoOf2DArray[2].split("=")[1];// 重量
		} catch (Exception e) {
			arr[4] = " ";
		}
		try {
			arr[5] = infoOf2DArray[1].split("=")[1]; // 钢种
		} catch (Exception e) {
			arr[5] = " ";
		}
		try {
			arr[6] = infoOf2DArray[4].split("=")[1];// 表面
		} catch (Exception e) {
			arr[6] = " ";
		}
		try {
			arr[7] = infoOf2DArray[5].split("=")[1];// 宽度
		} catch (Exception e) {
			arr[7] = " ";
		}
		try {
			arr[8] = infoOf2DArray[5].split("=")[1];// 厚度
		} catch (Exception e) {
			arr[8] = " ";
		}
		try {
			arr[9] = infoOf2DArray[6].split("=")[1];// 长度
		} catch (Exception e) {
			arr[9] = " ";
		}
		arr[10] = driverNum;// 司机
		arr[11] = GlobalSettings.UserName;// 出库人
		return arr;
	}

}
