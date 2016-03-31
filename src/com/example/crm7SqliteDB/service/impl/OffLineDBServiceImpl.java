package com.example.crm7SqliteDB.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.example.crm7CustomedPlugIn.TBPanKu;
import com.example.crm7SqliteDB.dao.OffLineDBDao;
import com.example.crm7SqliteDB.dao.impl.OffLineDBDaoImpl;
import com.example.crm7SqliteDB.service.OffLineDBService;

import android.content.Context;

public class OffLineDBServiceImpl implements OffLineDBService {
	
	@Override
	public List<String> offLineUser(Context context){
		OffLineDBDao offLineDao = new OffLineDBDaoImpl(context);
		return offLineDao.offLineUser();
	}
	
	@Override
	public boolean verification(Context context,String userName, String passWord) {
		OffLineDBDao offLineDao = new OffLineDBDaoImpl(context);
		return offLineDao.verification(userName, passWord);
	}

	@Override
	public String offLineTransport(Context context, String [] array) {
		OffLineDBDao offLineDBDao = new OffLineDBDaoImpl(context);
		return offLineDBDao.offLineTransport(context, array);
	}
	
	@Override
	public String offLineTransport(Context context, String[] array, int limit) {
		OffLineDBDao offLineDBDao = new OffLineDBDaoImpl(context);
		return offLineDBDao.offLineTransport(context, array,limit);
	}
	@Override
	public int offLineTransportCount(Context context,String carNo) {
		OffLineDBDao offLineDBDao = new OffLineDBDaoImpl(context);
		return offLineDBDao.offLineTransportCount(context,carNo);
	}

	@Override
	public void offLineTransportDelete(Context context, String endTime) {
		new OffLineDBDaoImpl(context).offLineTransportDelete(context,endTime);
	}

	@Override
	public String offLineClearData(Context context) {
		return new OffLineDBDaoImpl(context).offLineClearData(context);
		
	}

	@Override
	public int offLineCount(Context context) {
		OffLineDBDao offLineDBDao = new OffLineDBDaoImpl(context);
		return offLineDBDao.offLineCount(context);
	}

	@Override
	public String offLinePosition(Context context,String userName) {
		OffLineDBDao offLineDBDao = new OffLineDBDaoImpl(context);
		return offLineDBDao.offLinePosition(context,userName);
	}

	@Override
	public int offLineCarNo(Context context, String carNo) {
		OffLineDBDao offLineDBDao = new OffLineDBDaoImpl(context);
		return offLineDBDao.OffLineCarNo(context, carNo);
	}

	@Override
	public boolean scanIn(Context context, String[] arr, String userName, String[] strings) {
		OffLineDBDao offLineDBDao = new OffLineDBDaoImpl(context);
		return offLineDBDao.scanIn(context,arr,userName,strings);
	}

	@Override
	public String offLineCheckNum(Context context) {
		OffLineDBDao offLineDBDao = new OffLineDBDaoImpl(context);
		return offLineDBDao.offLineCheckNum(context);
	}

	@Override
	public String offLineUpData(Context context) {
		OffLineDBDao offLineDBDao = new OffLineDBDaoImpl(context);
		return offLineDBDao.offLineUpData(context);
	}

	@Override
	public String offLineCheckWareInfo(Context context) {
		OffLineDBDao offLineDBDao = new OffLineDBDaoImpl(context);
		return offLineDBDao.offLineCheckWareInfo(context);
	}

	@Override
	public boolean offLineTransInfoScanIn(Context context,String arr[]) {
		OffLineDBDao offLineDBDao = new OffLineDBDaoImpl(context);
		return offLineDBDao.offLineTransInfoScanIn(context,arr);
	}

	@Override
	public List<ArrayList<String>> offLineLoadInfo(Context context) {
		OffLineDBDao offLineDBDao = new OffLineDBDaoImpl(context);
		return offLineDBDao.offLineLoadInfo(context);
	}

	@Override
	public boolean offLineDeleteInfo(Context context,List<String> info) {
		OffLineDBDao offLineDBDao = new OffLineDBDaoImpl(context);
		return offLineDBDao.offLineDeleteInfo(context, info);
	}

	@Override
	public List<String> offLineLibrary(Context context, String loginName) {
		OffLineDBDao offLineDBDao = new OffLineDBDaoImpl(context);
		return offLineDBDao.offLineLibrary(context,loginName);
	}

	@Override
	public List<String> offLineAnlage(Context context) {
		OffLineDBDao offLineDBDao = new OffLineDBDaoImpl(context);
		return offLineDBDao.offLineAnlage(context);
	}

	@Override
	public List<TBPanKu> offLineExport(Context context) {
		OffLineDBDao offLineDBDao = new OffLineDBDaoImpl(context);
		return offLineDBDao.offLineExport(context);
	}

	@Override
	public List<TBPanKu> offLineExport(Context context, int flag) {
		OffLineDBDao offLineDBDao = new OffLineDBDaoImpl(context);
		return offLineDBDao.offLineExport(context,flag);
	}

	@Override
	public String offLineBackData(Context context,List<TBPanKu> list) {
		OffLineDBDao offLineDBDao = new OffLineDBDaoImpl(context);
		return offLineDBDao.offLineBackData(context,list);
	}
	
	
}
