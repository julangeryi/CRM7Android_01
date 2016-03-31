package com.example.crm7SqliteDB.dao.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.crm7CustomedPlugIn.TBPanKu;
import com.example.crm7SqliteDB.DBOpenHelper;
import com.example.crm7SqliteDB.dao.OffLineDBDao;
import com.lidroid.xutils.DbUtils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

@SuppressLint("SimpleDateFormat")
public class OffLineDBDaoImpl implements OffLineDBDao {

	private Context context;

	public OffLineDBDaoImpl(Context context) {
		this.context = context;
	}

	@Override
	public boolean verification(String userName, String userPass) {
		boolean result = false;
		DBOpenHelper helper = new DBOpenHelper(context);
		SQLiteDatabase database = null;
		try {
			database = helper.getReadableDatabase();
			String sql = "select t.c_password from tb_user t where t.c_loginname = ?";
			Cursor cursor = database.rawQuery(sql, new String[] { userName });
			while (cursor.moveToNext()) {
				result = cursor.getString(0).equals(userPass);
			}
		} catch (Exception e) {
			System.out.println("Exception");
		} finally {
			if (database != null) {
				database.close();
			}
		}

		return result;
	}

	@Override
	public String offLineTransport(Context context, String[] array) {
		String result = "";
		DBOpenHelper helper = new DBOpenHelper(context);
		SQLiteDatabase database = null;
		try {
			database = helper.getWritableDatabase();
			String sql = "select rowid as 序号,mat_no as 钢卷号,carno as 车号,driver as 司机,stock_name as 库位,outdate as 出库时间,outperson as 出库人  from tb_zy  "
					+ "where carno= '" + array[0] + "'  " + " and outdate between '"
					+ new SimpleDateFormat("yyyy-M-d 00:00").format(new Date(System.currentTimeMillis())) + "' and '"
					+ new SimpleDateFormat("yyyy-M-d 23:59").format(new Date(System.currentTimeMillis())) + "' "
					+ "order by outdate desc ";
			Cursor cursor = database.rawQuery(sql, null);
			while (cursor.moveToNext()) {
				result += "钢卷号: " + cursor.getString(1) + "\r\n" + "出库库位: " + cursor.getString(4) + "\r\n" + "出库人: "
						+ cursor.getString(6) + "\r\n" + "出库时间：" + cursor.getString(5) + "\r\n";
				result += "---------------" + "\r\n";
			}
		} catch (Exception e) {
		} finally {
			if (database != null) {
				database.close();
			}
		}
		return result;
	}

	@Override
	public String offLineTransport(Context context, String[] array, int limit) {
		String result = "";
		DBOpenHelper helper = new DBOpenHelper(context);
		SQLiteDatabase database = null;
		try {
			database = helper.getWritableDatabase();
			String sql = "select rowid as 序号,mat_no as 钢卷号,carno as 车号,driver as 司机,stock_name as 库位,outdate as 出库时间,outperson as 出库人  from tb_zy  "
					+ "where carno= '" + array[0] + "'  " + " and outdate between '"
					+ new SimpleDateFormat("yyyy-M-d 00:00").format(new Date(System.currentTimeMillis())) + "' and '"
					+ new SimpleDateFormat("yyyy-M-d 23:59").format(new Date(System.currentTimeMillis())) + "' "
					+ "order by outdate desc limit '" + limit + "' ";
			Cursor cursor = database.rawQuery(sql, null);
			while (cursor.moveToNext()) {
				result += "钢卷号: " + cursor.getString(1) + "\r\n" + "出库库位: " + cursor.getString(4) + "\r\n" + "出库人: "
						+ cursor.getString(6) + "\r\n" + "出库时间：" + cursor.getString(5) + "\r\n";
				result += "---------------" + "\r\n";
			}
		} catch (Exception e) {
		} finally {
			if (database != null) {
				database.close();
			}
		}
		return result;
	}

	@Override
	public int offLineTransportCount(Context contrext, String carNo) {
		int count = 0;
		DBOpenHelper helper = new DBOpenHelper(context);
		SQLiteDatabase database = null;
		try {
			database = helper.getReadableDatabase();
			String sql = "select count(*) from tb_zy where carno= '" + carNo + "'";
			Cursor cursor = database.rawQuery(sql, null);
			while (cursor.moveToNext()) {
				count = cursor.getInt(0);
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			if (database != null) {
				database.close();
			}
		}
		return count;
	}

	public void offLineTransportDelete(Context context2, String endTime) {
		DBOpenHelper helper = new DBOpenHelper(context);
		SQLiteDatabase database = null;
		try {
			database = helper.getReadableDatabase();
			String sql = "delete from tb_zy where outdate between '2000-01-01 00:00:00' and ?";
			database.rawQuery(sql, new String[] { endTime });
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			if (database != null) {
				database.close();
			}
		}

	}

	@Override
	public String offLineClearData(Context context) {
		String result = "删除失败";
		DBOpenHelper helper = new DBOpenHelper(context);
		SQLiteDatabase database = null;
		try {
			database = helper.getWritableDatabase();
			String sql = "delete from tb_panku";
			database.execSQL(sql);
			result = "删除成功";
		} catch (Exception e) {
		} finally {
			if (database != null) {
				database.close();
			}
		}
		return result;
	}

	@Override
	public int offLineCount(Context context) {
		int result = 0;
		DBOpenHelper helper = new DBOpenHelper(context);
		SQLiteDatabase database = null;
		try {
			database = helper.getReadableDatabase();
			String sql = "select count(*) from tb_panku";
			Cursor c = database.rawQuery(sql, null);
			while (c.moveToNext()) {
				result = c.getInt(0);
			}
		} catch (Exception e) {
		} finally {
			if (database != null) {
				database.close();
			}
		}
		return result;
	}

	@Override
	public String offLinePosition(Context context, String userName) {
		String result = "";
		DBOpenHelper helper = new DBOpenHelper(context);
		SQLiteDatabase database = null;
		try {
			database = helper.getReadableDatabase();
			String sql = "SELECT T1.C_USERID, T3.C_DUTY, T2.C_ROLEID" + " FROM TB_USER T1 INNER JOIN"
					+ " TB_USER_ROLE T2 ON T1.C_USERID = T2.C_USERID INNER JOIN"
					+ " TB_ROLE T3 ON T2.C_ROLEID = T3.SID and T1.C_LOGINNAME = ? ORDER BY T1.C_USERID";
			Cursor c = database.rawQuery(sql, new String[] { userName });
			while (c.moveToNext()) {
				result += c.getString(1) + "=";
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			if (database != null) {
				database.close();
			}
		}
		return result;
	}

	@Override
	public int OffLineCarNo(Context context, String carNo) {
		int result = 0;
		DBOpenHelper helper = new DBOpenHelper(context);
		SQLiteDatabase database = null;
		try {
			database = helper.getReadableDatabase();
			String sql = "select Count(*) from TB_PANKU where kph= ?";
			Cursor c = database.rawQuery(sql, new String[] { carNo });
			while (c.moveToNext()) {
				result = c.getInt(0);
			}
		} catch (Exception e) {
		} finally {
			if (database != null) {
				database.close();
			}
		}
		return result;
	}

	@Override
	public boolean scanIn(Context context, String[] arr, String userName, String[] strings) {
		// arr[0] 卡号 arr[1]牌号 arr[2]厚 arr[3] 宽 arr[4] 长
		boolean result = false;
		DBOpenHelper helper = new DBOpenHelper(context);
		SQLiteDatabase database = null;
		try {
			database = helper.getWritableDatabase();
			String sql = "";
			if (arr.length > 2) {// 二维
				sql = "INSERT INTO TB_PANKU(" + " " + "PKSJ," + " STOCK_NO," + " KPH, PH," + " PKR,"
						+ " ROW_NO,COLUMN_NO,LAYER_NO) " + "VALUES ('"
						+ new SimpleDateFormat("yyyy-M-d HH:mm").format(new Date(System.currentTimeMillis())) + "',"
						+ "'" + strings[0] + "','" + arr[0] + "','" + arr[1] + "','" + userName + "','" + strings[1]
						+ "','" + strings[2] + "','" + strings[3] + "')";

			} else { // 一维
				sql = "insert into tb_panku(pksj,stock_no,KPH,PKR,ROW_NO,COLUMN_NO,LAYER_NO) values('"
						+ new SimpleDateFormat("yyyy-M-d HH:mm").format(new Date(System.currentTimeMillis())) + "', '"
						+ strings[0] + "','" + arr[1] + "','" + userName + "','" + strings[1] + "','" + strings[2]
						+ "','" + strings[3] + "')";
			}
			database.execSQL(sql);
			result = true;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("--");
		} finally {
			if (database != null) {
				database.close();
			}
		}
		return result;
	}

	@Override
	public String offLineCheckNum(Context context) {

		String result = "";
		DBOpenHelper helper = new DBOpenHelper(context);
		SQLiteDatabase database = null;
		try {
			database = helper.getReadableDatabase();
			String sql = "select count(*) from TB_PANKU";
			Cursor c = database.rawQuery(sql, null);
			while (c.moveToNext()) {
				result = c.getInt(0) + "条";
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			if (database != null) {
				database.close();
			}
		}
		return result;
	}

	@Override
	public String offLineUpData(Context context) {
		String result = "";
		DBOpenHelper helper = new DBOpenHelper(context);
		SQLiteDatabase database = null;
		try {
			database = helper.getReadableDatabase();
			String sql = "select * from tb_panku";
			Cursor c = database.rawQuery(sql, null);
			while (c.moveToNext()) {
				result += c.getString(0) + c.getString(1);
			}
		} catch (Exception e) {

		} finally {
			if (database != null) {
				database.close();
			}
		}
		return result;
	}

	@Override
	public String offLineCheckWareInfo(Context context) {
		String result = "";
		DBOpenHelper helper = new DBOpenHelper(context);
		SQLiteDatabase database = null;
		try {
			database = helper.getReadableDatabase();
			String sql = "select kph as 卡号,pksj as 盘库时间,pkr as 盘库人,ROW_NO as 行,COLUMN_NO as 列,LAYER_NO as 层,stock_no as 库位 from tb_panku";
			Cursor c = database.rawQuery(sql, null);
			while (c.moveToNext()) {
				result += c.getString(0) + ";" + c.getString(1) + ";" + c.getString(2) + ";" + c.getString(3) + ";"
						+ c.getString(4) + ";" + c.getString(5) + ";" + c.getString(6) + "+";
			}
		} catch (Exception e) {
		} finally {
			if (database != null) {
				database.close();
			}
		}
		return result;
	}

	@Override
	public List<String> offLineUser() {
		List<String> result = new ArrayList<String>();
		DBOpenHelper helper = new DBOpenHelper(context);
		SQLiteDatabase database = null;
		try {
			database = helper.getReadableDatabase();
			String sql = "select t.c_loginname from tb_user t order by t.c_loginname";
			Cursor cursor = database.rawQuery(sql, null);
			result.add("请选择用户");
			while (cursor.moveToNext()) {
				result.add(cursor.getString(0));
			}
		} catch (Exception e) {
			System.out.println("Exception");
		} finally {
			if (database != null) {
				database.close();
			}
		}
		return result;
	}

	@Override
	public boolean offLineTransInfoScanIn(Context context, String[] arr) {
		String sql = "";
		SQLiteDatabase database = null;
		DBOpenHelper helper = new DBOpenHelper(context);
		// 规格：厚长宽
		if (arr[0].equals("2D")) {
			sql = "INSERT INTO TB_ZY(" + "CARNO, STOCK_NAME,OUTDATE, MAT_NO," + "WEIGHT,STEELGRADE,SURFACE, "
					+ "WIDTH, THICKNESS, LENGTH1," + "DRIVER,OUTPERSON) " + "VALUES ('" + arr[1] + "','" + arr[2]
					+ "','" + new SimpleDateFormat("yyyy-M-d HH:mm").format(new Date(System.currentTimeMillis()))
					+ "','" + arr[3] + "','" + arr[4] + "','" + arr[5] + "','" + arr[6] + "','" + arr[8] + "','"
					+ arr[8] + "','" + arr[9] + "','" + arr[10] + "','" + arr[11] + "')";
		} else if (arr[0].equals("1D")) {
			sql = "insert into tb_zy(outdate,stock_name,carno,mat_no,driver,outperson) values" + "('"
					+ new SimpleDateFormat("yyyy-M-d HH:mm").format(new Date(System.currentTimeMillis())) + "','"
					+ arr[1] + "','" + arr[2] + "','" + arr[3] + "','" + arr[4] + "','" + arr[5] + "')";
		}
		try {
			database = helper.getReadableDatabase();
			database.execSQL(sql);
			return true;
		} catch (Exception e) {
			return false;
		} finally {
			if (database != null) {
				database.close();
			}
		}
	}

	@Override
	public List<ArrayList<String>> offLineLoadInfo(Context context) {

		String sql = "select kph,ph,stock_no,pkr,row_no,column_no,layer_no,pksj from tb_panku";

		SQLiteDatabase database = null;
		List<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
		DBOpenHelper helper = new DBOpenHelper(context);
		try {
			database = helper.getReadableDatabase();
			Cursor cursor = database.rawQuery(sql, null);
			while (cursor.moveToNext()) {
				ArrayList<String> temp = new ArrayList<String>();
				for (int i = 0; i < cursor.getColumnCount(); i++) {
					temp.add(cursor.getString(i) == null ? " " : cursor.getString(i));
				}
				result.add(temp);
			}
		} catch (Exception e) {

		} finally {
			if (database != null) {
				database.close();
			}
		}
		return result;
	}

	@Override
	public List<TBPanKu> offLineExport(Context context) {
		String sql = "select kph,ph,stock_no,pkr,row_no,column_no,layer_no,pksj from tb_panku";
		SQLiteDatabase database = null;
		List<TBPanKu> result = new ArrayList<TBPanKu>();
		DBOpenHelper helper = new DBOpenHelper(context);
		try {

			database = helper.getReadableDatabase();
			Cursor cursor = database.rawQuery(sql, null);
			while (cursor.moveToNext()) {
				String[] info = new String[8];
				for (int i = 0; i < cursor.getColumnCount(); i++) {
					info[i] = (cursor.getString(i) == null ? " " : cursor.getString(i));
				}

				TBPanKu tb = new TBPanKu();
				tb.setKph(info[0]);
				tb.setPh(info[1]);
				tb.setStock_no(info[2]);
				tb.setPkr(info[3]);
				tb.setRow_no(info[4]);
				tb.setColumn_no(info[5]);
				tb.setLayer_no(info[6]);
				tb.setPksj(info[7]);

				result.add(tb);
			}
		} catch (Exception e) {

		} finally {
			if (database != null) {
				database.close();
			}
		}
		return result;
	}

	@Override
	public List<TBPanKu> offLineExport(Context context, int flag) {
		String sql = "select kph,ph,stock_no,pkr,row_no,column_no,layer_no,pksj from tb_panku_back";
		SQLiteDatabase database = null;
		List<TBPanKu> result = new ArrayList<TBPanKu>();
		DBOpenHelper helper = new DBOpenHelper(context);
		try {

			database = helper.getReadableDatabase();
			Cursor cursor = database.rawQuery(sql, null);
			while (cursor.moveToNext()) {
				String[] info = new String[8];
				for (int i = 0; i < cursor.getColumnCount(); i++) {
					info[i] = (cursor.getString(i) == null ? " " : cursor.getString(i));
				}
				TBPanKu tb = new TBPanKu();
				tb.setKph(info[0]);
				tb.setPh(info[1]);
				tb.setStock_no(info[2]);
				tb.setPkr(info[3]);
				tb.setRow_no(info[4]);
				tb.setColumn_no(info[5]);
				tb.setLayer_no(info[6]);
				tb.setPksj(info[7]);

				result.add(tb);
			}
		} catch (Exception e) {

		} finally {
			if (database != null) {
				database.close();
			}
		}
		return result;
	}

	@Override
	public boolean offLineDeleteInfo(Context context, List<String> info) {
		boolean result = false;

		SQLiteDatabase database = null;
		try {
			DBOpenHelper helper = new DBOpenHelper(context);
			database = helper.getReadableDatabase();
			for (String temp : info) {
				String sql = "delete from tb_panku where kph = '" + temp + "'";
				database.execSQL(sql);
			}
			result = true;
		} catch (Exception e) {
			result = false;
		} finally {
			if (database != null) {
				database.close();
			}
		}
		return result;
	}

	@Override
	public List<String> offLineLibrary(Context context, String loginName) {
		List<String> offLibrary = new ArrayList<String>();
		DbUtils dbUtils = DbUtils.create(context, "/storage/sdcard/world.db3");
		String sql = "SELECT T1.C_USERID, T3.C_DUTY, T2.C_ROLEID FROM TB_USER T1 "
				+ "INNER JOIN TB_USER_ROLE T2 ON T1.C_USERID = T2.C_USERID "
				+ "INNER JOIN TB_ROLE T3 ON T2.C_ROLEID = T3.SID and " + "T1.C_LOGINNAME = ? "
				+ "ORDER BY T1.C_USERID ";
		Cursor cursor = dbUtils.getDatabase().rawQuery(sql, new String[] { loginName });
		while (cursor.moveToNext()) {
			offLibrary.add(cursor.getString(1));
		}
		return offLibrary;
	}

	@Override
	public List<String> offLineAnlage(Context context) {
		List<String> offLineAnlage = new ArrayList<String>();
		DbUtils dbUtils = DbUtils.create(context, "/storage/sdcard/world.db3");
		String sql = "SELECT t.anlage FROM tb_anlage t ORDER BY t.anlage";
		Cursor cursor = dbUtils.getDatabase().rawQuery(sql, null);
		while (cursor.moveToNext()) {
			offLineAnlage.add(cursor.getString(0));
		}
		return offLineAnlage;
	}

	@Override
	public String offLineBackData(Context context, List<TBPanKu> list) {
		String result = "error";
		DbUtils dbUtils = DbUtils.create(context,"/storage/sdcard/world.db3");
		for(int i=0;i<list.size();i++){
			TBPanKu panku = list.get(i);
			String sql = "insert into tb_panku_back(kph,ph,stock_no,pkr,row_no,column_no,layer_no,pksj) "
					    +"values('"+panku.getKph()+"','"+panku.getPh()+"','"+panku.getStock_no()+"','"+panku.getPkr()+"','"+panku.getRow_no()+"','"+panku.getColumn_no()+"','"+panku.getLayer_no()+"','"+panku.getPksj()+"')";
			dbUtils.getDatabase().execSQL(sql);
			result = "success";
		}
		
		return result;
	}

}
