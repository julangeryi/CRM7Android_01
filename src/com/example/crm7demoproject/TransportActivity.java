package com.example.crm7demoproject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.example.crm7Base.Crm7ActivityManager;
import com.example.crm7Constant.GlobalSettings;
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
import android.text.InputType;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class TransportActivity extends Activity implements View.OnTouchListener {

	private TextView editText1;
	private EditText etStartTime;
	private EditText etEndTime;
	private String startTime = "";
	private String endTime = "";
	private String queryCond_1 = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_transport);
		editText1 = (TextView) findViewById(R.id.editText1);

		etStartTime = (EditText) this.findViewById(R.id.et_start_time);
		etEndTime = (EditText) this.findViewById(R.id.et_end_time);

		etStartTime.setOnTouchListener(this);
		etEndTime.setOnTouchListener(this);
		
		Crm7ActivityManager.getInstance().addActivity(TransportActivity.this);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
		if(4 == keyCode){
			AlertDialog.Builder builder = new AlertDialog.Builder(TransportActivity.this);
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

	@SuppressLint("SimpleDateFormat")
	public void queryTransQuery(final View view) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d HH:mm");
		startTime = etStartTime.getText().toString().replace("  ", " ").concat(":00");
		endTime = etEndTime.getText().toString().replace("  ", " ").concat(":59");
		Date sd = null;
		Date ed = null;
		try {
				sd = (Date) sdf.parse(startTime);
				ed = (Date) sdf.parse(endTime);
				startTime = sdf.format(sd);
				endTime = sdf.format(ed);
		} catch (ParseException e) {
			sd = new Date();
			ed = new Date();
		}
		
		final boolean queryCondition_01 = !editText1.getText().toString().equals("")&&editText1.getText().toString() != null;
		final boolean queryCondition_02 = sd.getTime() - ed.getTime() > 0 ? false : true; 
		
		if (queryCondition_01) {
			if (GlobalSettings.Xz_CX == "1" && queryCondition_02) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						WebHttp webHttp = new WebHttp();
							view.post(new Runnable() {
								@Override
								public void run() {
									Toast.makeText(TransportActivity.this, "转运信息查询中",
											Toast.LENGTH_LONG + Toast.LENGTH_SHORT).show();
								}
							});
							queryCond_1 = webHttp.queryTransport(editText1.getText().toString(), startTime, endTime);
							view.post(new Runnable() {
								public void run() {
									TextView t = (TextView) findViewById(R.id.queryTrans_C);
									t.setText(queryCond_1);
								}
							});
					}
				}).start();
				// 转运离线查询
			} else if(GlobalSettings.Xz_CX == "1" && !queryCondition_02){
				Toast.makeText(TransportActivity.this, "转运信息查询中",
						Toast.LENGTH_LONG + 60000).show();
				
			} if(GlobalSettings.Xz_CX == "2") {
				OffLineDBService offLineDBService = new OffLineDBServiceImpl();

//				// 返回信息
				String offLineTemp = " ";
				
				// 总数
				int count = offLineDBService.offLineTransportCount(TransportActivity.this,editText1.getText().toString());
				if (count > 5) {
					offLineTemp = "查询共有:"+count+"条,只显示最近的五条\r\n";
					offLineTemp += offLineDBService.offLineTransport(TransportActivity.this,new String[] { editText1.getText().toString(), " ", " "},5);
				}else{
					offLineTemp = offLineDBService.offLineTransport(TransportActivity.this,new String[] { editText1.getText().toString(), " ", " "});
					offLineTemp = offLineTemp.equals("") ? "系统中无此车运输记录" : offLineTemp;
				}
				TextView t = (TextView) findViewById(R.id.queryTrans_C);
				t.setText(offLineTemp);
			}

		} else {
			Toast.makeText(TransportActivity.this, "请输入车号", Toast.LENGTH_SHORT).show();
		}

	}

	public void queryTransBack(View view) {
		System.out.println(GlobalSettings.Xz_CX);
		Class<? extends Activity> context = null;
		Intent intent = this.getIntent();
		context = GlobalSettings.Xz_CX.equals("1") ? MainActivity.class : OffLineMainActivity.class;
		intent.setClass(this, context);
		this.startActivity(intent);
		this.finish();
	}

	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			View view = View.inflate(this, R.layout.data_time_dialog, null);
			final DatePicker datePicker = (DatePicker) view.findViewById(R.id.datePicker1);
			final TimePicker timePicker = (android.widget.TimePicker) view.findViewById(R.id.timePicker1);
			builder.setView(view);

			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(System.currentTimeMillis());
			datePicker.init(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), null);

			timePicker.setIs24HourView(true);
			timePicker.setCurrentHour(cal.get(Calendar.HOUR_OF_DAY));
			timePicker.setCurrentMinute(Calendar.MINUTE);

			if (v.getId() == R.id.et_start_time) {
				final int inType = etStartTime.getInputType();
				etStartTime.setInputType(InputType.TYPE_NULL);
				etStartTime.onTouchEvent(event);
				etStartTime.setInputType(inType);
				etStartTime.setSelection(etStartTime.getText().length());
				builder.setTitle("选取起始时间");
				builder.setPositiveButton("确  定", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

						StringBuffer sb = new StringBuffer();
						sb.append(String.format("%d-%02d-%02d", datePicker.getYear(), datePicker.getMonth() + 1,
								datePicker.getDayOfMonth()));
						sb.append("  ");
						sb.append(timePicker.getCurrentHour()).append(":").append(timePicker.getCurrentMinute());

						etStartTime.setText(sb);
						dialog.cancel();
					}
				});

			} else if (v.getId() == R.id.et_end_time) {
				int inType = etEndTime.getInputType();
				etEndTime.setInputType(InputType.TYPE_NULL);
				etEndTime.onTouchEvent(event);
				etEndTime.setInputType(inType);
				etEndTime.setSelection(etEndTime.getText().length());

				builder.setTitle("选取结束时间");
				builder.setPositiveButton("确  定", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

						StringBuffer sb = new StringBuffer();
						sb.append(String.format("%d-%02d-%02d", datePicker.getYear(), datePicker.getMonth() + 1,
								datePicker.getDayOfMonth()));
						sb.append("  ");
						sb.append(timePicker.getCurrentHour()).append(":").append(timePicker.getCurrentMinute());
						etEndTime.setText(sb);

						dialog.cancel();
					}
				});
			}
			Dialog dialog = builder.create();
			dialog.show();
		}

		return true;
	}

}
