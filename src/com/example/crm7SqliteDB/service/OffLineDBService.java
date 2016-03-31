package com.example.crm7SqliteDB.service;


import java.util.ArrayList;
import java.util.List;

import com.example.crm7CustomedPlugIn.TBPanKu;

import android.content.Context;

public interface OffLineDBService {
	
	//离线转运插入
	public boolean offLineTransInfoScanIn(Context context,String [] arr);
	
	//离线用户组
	public List<String> offLineUser(Context context);
	
	//离线用户名密码
	public boolean verification(Context context,String userName,String passWord);
	
	//离线转运查询
	public String offLineTransport(Context context,String [] array);
	public String offLineTransport(Context context,String [] array,int limit);
	
	//离线转运数据总数
	public int offLineTransportCount(Context context,String carNo);
	
	//离线转运数删除
	public void offLineTransportDelete(Context context,String endTime);
	
	//离线数据删除之前的备份
	public String offLineBackData(Context context,List<TBPanKu> list);
	
	//离线数据删除
	public String offLineClearData(Context context);
	
	//盘库信息
	public int offLineCount(Context context);
	
	//离线盘库库位查询
	public String offLinePosition(Context context,String userName);
	
	//离线盘库对应卡号查询
	public int offLineCarNo(Context context,String carNo);
	
	//离线盘库 插入
	public boolean scanIn(Context context, String[] arr, String userName,String[] strings);
	
	//离线盘库总是
	public String offLineCheckNum(Context context);
	
	//上传数据
	public String offLineUpData(Context context);

	//盘库数据查询
	public String offLineCheckWareInfo(Context offLineCheckWareHouseActivity);

	//离线上传
	public List<ArrayList<String>> offLineLoadInfo(Context context);
	
	//上传删除
	public boolean offLineDeleteInfo(Context context,List<String> info);
	
	//离线获取库位
	public List<String> offLineLibrary(Context context ,String loginName);
	
	//离线产线
	public List<String> offLineAnlage(Context context );
	
	//离线导出excel
	public List<TBPanKu> offLineExport(Context context);
	
	//离线导出excel 备份
	public List<TBPanKu> offLineExport(Context context,int flag);
	
}
