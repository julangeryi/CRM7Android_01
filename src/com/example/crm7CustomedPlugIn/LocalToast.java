package com.example.crm7CustomedPlugIn;

import com.example.crm7demoproject.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("InflateParams")
public class LocalToast {
	
	private Context context;
	
	public LocalToast(Context context) {
		this.context = context;
	}

	public Toast localToast(String message,LayoutInflater layoutInflater){
		Toast toast = new Toast(context);
		View v = layoutInflater.inflate(R.layout.toast_layout, null);
		TextView textView = (TextView)v.findViewById(R.id.text);
		textView.setText(message);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.setDuration(Toast.LENGTH_LONG);
		toast.setView(v);
		toast.show();
		return toast;
	}
}
