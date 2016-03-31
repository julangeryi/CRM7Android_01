package com.example.crm7demoproject;


import java.util.ArrayList;
import java.util.List;

import com.example.crm7Base.Crm7ActivityManager;
import com.example.crm7SqliteDB.service.OffLineDBService;
import com.example.crm7SqliteDB.service.impl.OffLineDBServiceImpl;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class OffLineCheckWareHouseActivity extends Activity {

	private EditText offLineCountInput;
	// private EditText queryTransC;
	private ListView listView1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_off_line_check_ware_house);
		offLineCountInput = (EditText) findViewById(R.id.offLineCountInput);
		listView1 = (ListView) findViewById(R.id.listView1);
		Crm7ActivityManager.getInstance().addActivity(OffLineCheckWareHouseActivity.this);
		// queryTransC = (EditText) findViewById(R.id.queryTrans_C);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
		if(4 == keyCode){
			AlertDialog.Builder builder = new AlertDialog.Builder(OffLineCheckWareHouseActivity.this);
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

	public void offLineQuery(View view) {
		OffLineDBService offLineDBService = new OffLineDBServiceImpl();
		int count = offLineDBService.offLineCount(this);
		offLineCountInput.setText("盘库总数：" + count + "条");
	}

	public void detail(View view){
			final OffLineDBService offLineDBService = new OffLineDBServiceImpl();
			AlertDialog.Builder builder = new AlertDialog.Builder(OffLineCheckWareHouseActivity.this);
			final AlertDialog dialog = builder.create();
			LayoutInflater inflater =  getLayoutInflater();
			ViewGroup alertView = (ViewGroup) inflater.inflate(R.layout.alert_query, null);
			dialog.setView(alertView);
			dialog.setTitle("盘库详细信息");
			dialog.setMessage("数据量如果过大，全部显示需要1，2分钟，是否全部显示？");
			dialog.show();
			Button alertBtnOk = (Button) alertView.findViewById(R.id.alert_btn_1);
			alertBtnOk.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					final int count = offLineDBService.offLineCount(OffLineCheckWareHouseActivity.this);
					final List<String> resultList = new ArrayList<String>();
					offLineCountInput.setText("盘库总数：" + count + "条");
					final String checkWareInfo = offLineDBService.offLineCheckWareInfo(OffLineCheckWareHouseActivity.this);
					for(int i=0;i<count;i++){
						String info[] = checkWareInfo.split("\\+")[i].split(";");
						String rs = "";
						rs += "序号:"+(i+1)+" 卡号:" + info[0] + "\r\n";
						rs += "行:" + info[3]+" "+" 列:" + info[4]+" "+"库位:" + info[6];
						resultList.add(rs);
					}
					listView1.setAdapter(new QueryAdapter(resultList, OffLineCheckWareHouseActivity.this));
					dialog.cancel();
				}
			});
			
			Button alertBtnCancel = (Button) alertView.findViewById(R.id.alert_btn_2);
			alertBtnCancel.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					dialog.cancel();
				}
			});
	}
	
	public void offLineBack(View view) {
		Intent intent = this.getIntent();
		intent.setClass(this, OffLineMainActivity.class);
		this.startActivity(intent);
		this.finish();
	}
}

class QueryAdapter extends BaseAdapter {
	private List<String> result;
	private Context context;

	public QueryAdapter(List<String> result, Context context) {
		super();
		this.result = result;
		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return result.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@SuppressLint("ResourceAsColor")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView textView = new TextView(this.context);
		textView.setText(result.get(position));
		if(position%2!=0){
			textView.setBackgroundColor(R.color.darkgrey);
		}
		return textView;
	}

}
