package com.example.crm7Base;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.app.Application;

public class Crm7ActivityManager extends Application {
	
	private List<Activity> activityList = new LinkedList<Activity>();
	private static Crm7ActivityManager instance;

	private Crm7ActivityManager() {

	}

	public static Crm7ActivityManager getInstance() {
		if (null == instance) {
			instance = new Crm7ActivityManager();
		}
		return instance;
	}

	public void addActivity(Activity activity) {
		activityList.add(activity);
	}

	public void exit() {
		if (activityList != null) {
			Activity activity;
			for (int i = 0; i < activityList.size(); i++) {
				activity = activityList.get(i);
				if (activity != null) {
					if (!activity.isFinishing()) {
						activity.finish();
					}
					activity = null;
				}
				activityList.remove(i);
				i--;
			}
		}
	}
}