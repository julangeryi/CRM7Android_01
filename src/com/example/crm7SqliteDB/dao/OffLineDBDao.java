package com.example.crm7SqliteDB.dao;

import java.util.ArrayList;
import java.util.List;

import com.example.crm7CustomedPlugIn.TBPanKu;

import android.content.Context;

public interface OffLineDBDao {
	
	//离线转运
	public boolean offLineTransInfoScanIn(Context context,String [] arr);
	
	//离线用户组
	public List<String> offLineUser();

	// 离线用户名
	public boolean verification(String userName, String userPass);

	// 离线转运
	public String offLineTransport(Context context, String[] array);
	public String offLineTransport(Context context, String[] array,int limit);

	// 离线总数
	public int offLineTransportCount(Context contrext,String carNo);
	
	//离线删除
	public void offLineTransportDelete(Context context,String endTime);
	
	//清除数据
	public String offLineClearData(Context context);
	
	//盘库查询
	public int offLineCount(Context context);

	//离线库位
	public String offLinePosition(Context context,String userName);
	
	//离线卡号查询
	public int OffLineCarNo(Context context,String carNo);

	//离线插入
	public boolean scanIn(Context context, String[] arr, String userName, String[] strings);
	
	public String offLineCheckNum(Context context);
	
	public String offLineUpData(Context context);

	public String offLineCheckWareInfo(Context context);
	
	//上传数据
	public List<ArrayList<String>> offLineLoadInfo(Context context);
	//上传删除
	public boolean offLineDeleteInfo(Context context,List<String> info);

	public List<String> offLineLibrary(Context context, String loginName);

	public List<String> offLineAnlage(Context context);
	
	//离线导出excel
	public List<TBPanKu> offLineExport(Context context);
	
	//离线导出excel 备份
	public List<TBPanKu> offLineExport(Context context,int flag);
	
	//离线删除之前备份
	public String offLineBackData(Context context,List<TBPanKu> list);
	
}
