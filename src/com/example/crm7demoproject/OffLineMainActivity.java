package com.example.crm7demoproject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.crm7Base.Crm7ActivityManager;
import com.example.crm7Constant.Constant;
import com.example.crm7Constant.GlobalSettings;
import com.example.crm7CustomedPlugIn.CreateSimpleExcelToDisk;
import com.example.crm7CustomedPlugIn.MyDialog;
import com.example.crm7CustomedPlugIn.TBPanKu;
import com.example.crm7Http.WebHttp;
import com.example.crm7SqliteDB.service.OffLineDBService;
import com.example.crm7SqliteDB.service.impl.OffLineDBServiceImpl;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

@SuppressLint("InflateParams")
public class OffLineMainActivity extends Activity {

	private String deleteInfo = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_off_line_main);
		Crm7ActivityManager.getInstance().addActivity(OffLineMainActivity.this);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (4 == keyCode) {
			AlertDialog.Builder builder = new AlertDialog.Builder(OffLineMainActivity.this);
			final AlertDialog dialog = builder.create();
			LayoutInflater inflater = getLayoutInflater();
			ViewGroup group = (ViewGroup) inflater.inflate(R.layout.dialog_exit, null);
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

	// 离线盘库
	public void offLineCheckWareHouse(View view) {
		Intent intent = this.getIntent();
		intent.setClass(this, CheckWareHouseActivity.class);
		this.startActivityForResult(intent, Constant.OffLineMain2OffLineCheckWareHouse);
	}

	// 转运查询
	public void offLineQueryTransport(View view) {
		GlobalSettings.Xz_CX = "2";
		Intent intent = this.getIntent();
		intent.setClass(this, TransportActivity.class);
		this.startActivityForResult(intent, Constant.OffLineMain2Transport);
	}

	// 清空数据
	public void offLineClearData(View view) {
		Dialog dialog = new MyDialog(OffLineMainActivity.this);

		MyDialog.Builder builder = new MyDialog.Builder(OffLineMainActivity.this);
		builder.setTitle(R.string.dialog_title);
		builder.setMessage(R.string.dialog_off_clear);

		builder.setConfirmButton(R.string.dialog_confirm, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				OffLineDBService offLineDBService = new OffLineDBServiceImpl();
				// 先备份
				final List<TBPanKu> loadDataList = offLineDBService.offLineExport(OffLineMainActivity.this);
				String result = offLineDBService.offLineBackData(OffLineMainActivity.this, loadDataList);
				if(result.equals("success")){
					deleteInfo = offLineDBService.offLineClearData(OffLineMainActivity.this);
					Toast.makeText(OffLineMainActivity.this, deleteInfo, Toast.LENGTH_SHORT).show();
				}
				// 在删除
				dialog.cancel();
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

	// 盘库信息查询
	public void offLineCheckWareHouseQuery(View view) {
		Intent intent = this.getIntent();
		intent.setClass(this, OffLineCheckWareHouseActivity.class);
		this.startActivityForResult(intent, Constant.OffLineMain2OffLineCheckWareHouseQuery);
	}

	// 上传数据
	public void offLineUpLoadData(final View view) {
		final OffLineDBService offLineDBService = new OffLineDBServiceImpl();
		final List<ArrayList<String>> loadDataList = offLineDBService.offLineLoadInfo(OffLineMainActivity.this);
		Toast.makeText(OffLineMainActivity.this, "数据上传中,一共需要上传的数据有：" + loadDataList.size() + "条", Toast.LENGTH_LONG).show();

		new Thread(new Runnable() {
			WebHttp webHttp = new WebHttp();
			boolean upLoadFlag = false;
			List<String> kphList = new ArrayList<String>();
			int i = 0;

			@Override
			public void run() {
				for (ArrayList<String> temp : loadDataList) {
					upLoadFlag = webHttp.upDataLoad(temp);
					if (upLoadFlag) {
						i++;
						kphList.add(temp.get(2));
					} else {
						break;
					}
				}

				view.post(new Runnable() {
					@Override
					public void run() {
						Toast.makeText(OffLineMainActivity.this, "上传成功" + i + "条", Toast.LENGTH_LONG).show();
					}
				});
			}
		}).start();

	}

	// 导出到Excel
	@SuppressLint("SimpleDateFormat")
	public void offLineExport(View view) {

		final OffLineDBService offLineDBService = new OffLineDBServiceImpl();

		AlertDialog.Builder builder = new AlertDialog.Builder(OffLineMainActivity.this);
		final AlertDialog dialog = builder.create();

		LayoutInflater inflater = getLayoutInflater();
		ViewGroup group = (ViewGroup) inflater.inflate(R.layout.export_excel, null);

		Button exportBtnPanku = (Button) group.findViewById(R.id.export_btn_panku);
		Button exportBtnBack = (Button) group.findViewById(R.id.export_btn_back);

		exportBtnPanku.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(OffLineMainActivity.this, "开始导出", Toast.LENGTH_SHORT).show();
				final List<TBPanKu> loadDataList = offLineDBService.offLineExport(OffLineMainActivity.this);
				CreateSimpleExcelToDisk.CreateExcel(Environment.getExternalStorageDirectory() + "/"
						+ new SimpleDateFormat("yyyyMMddHHmmss").format(new Date(System.currentTimeMillis())) + GlobalSettings.UserName,
						loadDataList);
				Toast.makeText(OffLineMainActivity.this, "导出完毕", Toast.LENGTH_SHORT).show();
				dialog.cancel();
			}
		});

		exportBtnBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(OffLineMainActivity.this, "开始导出", Toast.LENGTH_SHORT).show();
				final List<TBPanKu> loadDataList = offLineDBService.offLineExport(OffLineMainActivity.this,1);
				Toast.makeText(OffLineMainActivity.this, "导出完毕", Toast.LENGTH_SHORT).show();
				CreateSimpleExcelToDisk.CreateExcel(Environment.getExternalStorageDirectory() + "/"
						+ new SimpleDateFormat("yyyyMMddHHmmss").format(new Date(System.currentTimeMillis())) + GlobalSettings.UserName+"_备份",
						loadDataList);
				dialog.cancel();
			}
		});
		dialog.setTitle("选择需要导出的数据");
		dialog.setView(group);
		dialog.show();

	}

	// 复制Excel
	public void copyExcel(String oldPath, String newPath) {
		try {
			int byteread = 0;
			File oldFile = new File(oldPath);
			File newFile = new File(newPath);
			if (!newFile.exists()) {
				newFile.createNewFile();
			}
			if (oldFile.exists()) { // 文件存在时
				InputStream inStream = new FileInputStream(oldPath); // 读入原文件
				FileOutputStream fs = new FileOutputStream(newPath);
				byte[] buffer = new byte[1444];
				while ((byteread = inStream.read(buffer)) != -1) {
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
				fs.close();
			}
		} catch (Exception e) {
			System.out.println("复制单个文件操作出错");
			e.printStackTrace();
		}
	}

	// 保存
	public void copyFile(String oldPath, String newPath) {
		try {
			int byteread = 0;
			File oldFile = new File(oldPath);
			File newFile = new File(newPath + "/worldBack.db3");
			if (!newFile.exists()) {
				newFile.createNewFile();
			}
			if (oldFile.exists()) { // 文件存在时
				InputStream inStream = new FileInputStream(oldPath); // 读入原文件
				FileOutputStream fs = new FileOutputStream(newPath + "/worldBack.db3");
				byte[] buffer = new byte[1444];
				while ((byteread = inStream.read(buffer)) != -1) {
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
				fs.close();
			}
		} catch (Exception e) {
			System.out.println("复制单个文件操作出错");
			e.printStackTrace();
		}
	}
}
