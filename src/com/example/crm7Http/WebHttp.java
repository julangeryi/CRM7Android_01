package com.example.crm7Http;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

public class WebHttp implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Properties pro;
	private String SERVICE_URL;
	private String SERVICE_NS;

	public WebHttp() {
		pro = new Properties();
		try {
			pro.load(getClass().getResourceAsStream("/configuration.properties"));

		} catch (IOException e) {
			System.out.println("no file");
			e.printStackTrace();
		}
		SERVICE_URL = pro.getProperty("SERVICE_URL");
		SERVICE_NS = pro.getProperty("SERVICE_NS");
	}

	// Login
	public String getResponse(String userName,String passWord) {
		String response = "";
		String methodName = "VerifyCredentials";
		String soapAction = SERVICE_NS + methodName;
		HttpTransportSE ht = new HttpTransportSE(SERVICE_URL);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
		SoapObject request = new SoapObject(SERVICE_NS, methodName);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);
		request.addProperty("id", userName);
		request.addProperty("pass", passWord);
		try {
			ht.call(soapAction, envelope);
			response = envelope.getResponse().toString();
		} catch (Exception e) {
			response = "网络超时：网络信号欠佳，请等候";
		}
		return response;
	}

	// LoginInfo--(userID,duty,roleID)
	public String userInfo(String userName) {
		String result = "";
		String methodName = "GetRoll";
		String soapAction = SERVICE_NS + methodName;
		HttpTransportSE ht = new HttpTransportSE(SERVICE_URL);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
		SoapObject request = new SoapObject(SERVICE_NS, methodName);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);
		request.addProperty("id", userName);
		try {
			ht.call(soapAction, envelope);
			SoapObject so = (SoapObject) envelope.bodyIn;
			SoapObject dataSet = ((SoapObject) ((SoapObject) so.getProperty(0)).getProperty(1));
			int detailNum = ((SoapObject) dataSet.getProperty(0)).getPropertyCount();
			for (int i = 0; i < detailNum; i++) {
				result += soapUserInfo(((SoapObject) dataSet.getProperty(0)).getProperty(i).toString());
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("lll");
		}
		return result;
	}

	// Anlage Info
	public String anlageInfo() {
		String result = "";
		String methodName = "GetAnalage";
		String soapAction = SERVICE_NS + methodName;
		HttpTransportSE ht = new HttpTransportSE(SERVICE_URL);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
		SoapObject request = new SoapObject(SERVICE_NS, methodName);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);
		try {
			ht.call(soapAction, envelope);
			SoapObject so = (SoapObject) envelope.bodyIn;
			SoapObject dataSet = ((SoapObject) ((SoapObject) so.getProperty(0)).getProperty(1));
			int detailNum = ((SoapObject) dataSet.getProperty(0)).getPropertyCount();
			for (int i = 0; i < detailNum; i++) {
				String temp = ((SoapObject) dataSet.getProperty(0)).getProperty(i).toString();
				result += temp.substring(7, temp.length() - 3).split("=")[1] + "=";
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("lll");
		}
		return result;
	}

	// QueryWareHouse
	public String CX_KW(String steelGradeID) {
		String result = "";
		String methodName = "CX_KW";
		String soapAction = SERVICE_NS + methodName;
		HttpTransportSE ht = new HttpTransportSE(SERVICE_URL);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
		SoapObject request = new SoapObject(SERVICE_NS, methodName);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);
		request.addProperty("GjId", steelGradeID);
		try {
			ht.call(soapAction, envelope);
			SoapObject so = (SoapObject) envelope.bodyIn;
			result = so.toString().substring(13, so.toString().length() - 3).split("=")[1];
		} catch (Exception e) {
			e.printStackTrace();
			result = "网络超时，请检查网络";
			System.out.println("lll");
		}
		return result;
	}

	// MES_HQWL
	public String matHQWL(String steelGradeID, String anlage) {
		String result = "";
		String methodName = "MES_HQWL";
		String soapAction = SERVICE_NS + methodName;
		HttpTransportSE ht = new HttpTransportSE(SERVICE_URL);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
		SoapObject request = new SoapObject(SERVICE_NS, methodName);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);
		request.addProperty("GjId", steelGradeID);
		request.addProperty("Analage", anlage);
		try {
			ht.call(soapAction, envelope);
			SoapObject so = (SoapObject) envelope.bodyIn;
			result = so.toString().substring(13, so.toString().length() - 3).split("=")[1];
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("lll");
		}
		return result;
	}

	// MatStateQuery
	public String matStateQuery(String steelGradeNo) {
		String result = "";
		String methodName = "WLCheck";
		String soapAction = SERVICE_NS + methodName;
		HttpTransportSE ht = new HttpTransportSE(SERVICE_URL);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
		SoapObject request = new SoapObject(SERVICE_NS, methodName);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);
		request.addProperty("matlocalnumber", steelGradeNo);
		try {
			ht.call(soapAction, envelope);
			SoapObject innerSoapObject = (SoapObject) ((SoapObject) ((SoapObject) envelope.bodyIn).getProperty(0))
					.getProperty(1);
			if (0 == innerSoapObject.getPropertyCount()) {
				result = "此物料没有找到";
			} else {
				SoapObject inner = (SoapObject) innerSoapObject.getProperty(0);
				String tempResult = ((SoapObject) inner.getProperty(0)).toString();
				for (String info : tempResult.substring(8, tempResult.length() - 2).split(";")) {
					result += info.split("=")[1] + ";";
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("aaa");
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
		return result;
	}

	// 转运查询(Transport)
	public String queryTransport(String carNo, String startTime, String endTime) {
		String result = "";
		String methodName = "ZY_CX";
		String soapAction = SERVICE_NS + methodName;
		HttpTransportSE ht = new HttpTransportSE(SERVICE_URL);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
		SoapObject request = new SoapObject(SERVICE_NS, methodName);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);
		request.addProperty("CarNo", carNo);
		request.addProperty("begintime", startTime);
		request.addProperty("endtime", endTime);
		try {
			ht.call(soapAction, envelope);
			SoapObject so = (SoapObject) envelope.bodyIn;
			SoapObject dataSet = ((SoapObject) ((SoapObject) so.getProperty(0)).getProperty(1));
			// 1--有值 ，0 --无值
			if (0 == dataSet.getPropertyCount()) {
				result = "此车次，在此这个时间段里面，没有记录";
			} else {
				int detailNum = ((SoapObject) dataSet.getProperty(0)).getPropertyCount();
				if (detailNum > 10) {
					result += "一共筛选出" + detailNum + "条数据，无法全部列举出来,只列举其中的五条\r\n";
				}
				for (int i = 0; i < 5; i++) {
					result += soapTransportProcess(
							((SoapObject) ((SoapObject) dataSet.getProperty(0)).getProperty(i)).toString()) + "\r\n";
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("aaa");
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
		return result;
	}

	public String New_RK(String GjId, String KwId, String aREA, String rOW_NO, String cOLUMN_NO, String lAYER_NO,
			String UserName) {
		String result = "";
		String methodName = "New_RK";
		String soapAction = SERVICE_NS + methodName;
		HttpTransportSE ht = new HttpTransportSE(SERVICE_URL);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
		SoapObject request = new SoapObject(SERVICE_NS, methodName);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);
		request.addProperty("GjId", GjId);
		request.addProperty("KwId", KwId);
		request.addProperty("aREA", aREA);
		request.addProperty("rOW_NO", rOW_NO);
		request.addProperty("cOLUMN_NO", cOLUMN_NO);
		request.addProperty("lAYER_NO", lAYER_NO);
		request.addProperty("UserName", UserName);
		try {
			ht.call(soapAction, envelope);
			result = envelope.bodyIn.toString().substring(15, envelope.bodyIn.toString().length() - 3).split("=")[1];
		} catch (Exception e) {
		}
		return result;
	}

	// 根据库位信息查询编号
	public String Cx_KW1(String stockName) {
		String result = "";
		String methodName = "Cx_KW1";
		String soapAction = SERVICE_NS + methodName;
		HttpTransportSE ht = new HttpTransportSE(SERVICE_URL);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
		SoapObject request = new SoapObject(SERVICE_NS, methodName);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);
		request.addProperty("KwId", stockName);
		try {
			ht.call(soapAction, envelope);
			result = envelope.bodyIn.toString().substring(15, envelope.bodyIn.toString().length() - 3).split("=")[1];
		} catch (Exception e) {
		}
		return result;
	}

	public boolean GetRoll_ZY(String gjid) {
		boolean flag = false;
		String methodName = "GetRoll_ZY";
		String soapAction = SERVICE_NS + methodName;
		HttpTransportSE ht = new HttpTransportSE(SERVICE_URL);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
		SoapObject request = new SoapObject(SERVICE_NS, methodName);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);
		request.addProperty("id", gjid);
		try {
			ht.call(soapAction, envelope);
			flag = envelope.bodyIn.toString().substring(19, envelope.bodyIn.toString().length() - 3).split("=")[1]
					.equals("true");
		} catch (Exception e) {

		}
		return flag;
	}

	// 保存入库转运信息
	public String RKZY(String GjId, String GlobalUsername, String dqkw) {
		String result = "";
		String methodName = "RKZY";
		String soapAction = SERVICE_NS + methodName;
		HttpTransportSE ht = new HttpTransportSE(SERVICE_URL);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
		SoapObject request = new SoapObject(SERVICE_NS, methodName);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);
		request.addProperty("GjId", GjId);
		request.addProperty("GlobalUsername", GlobalUsername);
		request.addProperty("dqkw", dqkw);
		try {
			ht.call(soapAction, envelope);
			result = envelope.bodyIn.toString().substring(13, envelope.bodyIn.toString().length() - 3).split("=")[1];
		} catch (Exception e) {
			// TODO: handle exception
		}
		return result;
	}

	// 出库
	public String CX(String GjId, String UserName) {
		String result = "";
		String methodName = "CK";
		String soapAction = SERVICE_NS + methodName;
		HttpTransportSE ht = new HttpTransportSE(SERVICE_URL);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
		SoapObject request = new SoapObject(SERVICE_NS, methodName);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);
		request.addProperty("GjId", GjId);
		request.addProperty("UserName", UserName);
		try {
			ht.call(soapAction, envelope);
			result = envelope.bodyIn.toString().substring(11, envelope.bodyIn.toString().length() - 3).split("=")[1];
		} catch (Exception e) {
		}
		return result;
	}

	public boolean ScanIn(String arr, String user, String[] info) {
		boolean result = false;
		String methodName = "ScanIn_2";
		String soapAction = SERVICE_NS + methodName;
		HttpTransportSE ht = new HttpTransportSE(SERVICE_URL);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
		SoapObject request = new SoapObject(SERVICE_NS, methodName);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);

		request.addProperty("info", arr);
		request.addProperty("user", user);
		request.addProperty("region", info[0]);
		request.addProperty("row", (info[1].equals("") || info[1] == null) ? "" : info[1]);
		request.addProperty("col", (info[2].equals("") || info[2] == null) ? "" : info[2]);
		request.addProperty("layer", (info[3].equals("") || info[3] == null) ? "" : info[3]);
		try {
			ht.call(soapAction, envelope);
			result = envelope.getResponse().toString().equals("true");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("..");
		}
		return result;
	}

	public String PkCx() {
		String result = "";
		String methodName = "PkCx";
		String soapAction = SERVICE_NS + methodName;
		HttpTransportSE ht = new HttpTransportSE(SERVICE_URL);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
		SoapObject request = new SoapObject(SERVICE_NS, methodName);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);
		try {
			ht.call(soapAction, envelope);
			result = envelope.getResponse().toString();
		} catch (Exception e) {

		}
		return result;
	}

	public String MES_HQWL(String gjID, String anlage) {
		String result = "";
		String methodName = "MES_HQWL";
		String soapAction = SERVICE_NS + methodName;
		HttpTransportSE ht = new HttpTransportSE(SERVICE_URL);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
		SoapObject request = new SoapObject(SERVICE_NS, methodName);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);
		request.addProperty("GjId", gjID);
		request.addProperty("Analage", anlage);
		try {
			ht.call(soapAction, envelope);
			result = envelope.getResponse().toString();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return result;
	}

	public String MES_CRM(String GjId, String KwId, String UserName) {
		String result = "";
		String methodName = "MES_CRM";
		String soapAction = SERVICE_NS + methodName;
		HttpTransportSE ht = new HttpTransportSE(SERVICE_URL);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
		SoapObject request = new SoapObject(SERVICE_NS, methodName);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);
		request.addProperty("GjId", GjId);
		request.addProperty("KwId", KwId);
		request.addProperty("UserName", UserName);
		try {
			ht.call(soapAction, envelope);
			result = envelope.getResponse().toString();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return result;
	}
	
	// 二维码
	public String get2DInfo(String matNo) {
		String result = "";
		String methodName = "get2DInfo";
		String soapAction = SERVICE_NS + methodName;
		HttpTransportSE ht = new HttpTransportSE(SERVICE_URL);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
		SoapObject request = new SoapObject(SERVICE_NS, methodName);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);
		request.addProperty("id", matNo);
		try {
			ht.call(soapAction, envelope);
			SoapObject temp = (SoapObject) ((SoapObject) envelope.getResponse()).getProperty(1);
			if (0 == temp.getPropertyCount()) {
				result = "数据库没有此物料";
			} else {
				SoapObject tmp = (SoapObject) temp.getProperty(0);
				if (tmp.getPropertyCount() > 0) {
					result = tmp.getProperty(0).toString().substring(8, tmp.getProperty(0).toString().length() - 3);
				} else {
					result = "数据库没有此物料";
				}
			}
		} catch (Exception e) {

		}
		return result;
	}
	
	//统一入库接口
	public String inWareHouse(String gjid,String kw,String row,String column ,String layer,String user){
		String result = "";
		
		String methodName = "inWareHouse";
		String soapAction = SERVICE_NS + methodName;
		HttpTransportSE ht = new HttpTransportSE(SERVICE_URL);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
		SoapObject request = new SoapObject(SERVICE_NS, methodName);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);
		request.addProperty("gjid", gjid);
		request.addProperty("kw", kw);
		request.addProperty("row", row);
		request.addProperty("column", column);
		request.addProperty("layer", layer);
		request.addProperty("user", user);
		try {
			ht.call(soapAction, envelope);
			SoapObject temp = (SoapObject) envelope.bodyIn;
			result = temp.toString();
			System.out.println(result.split("=")[1]);
		} catch (Exception e) {
		}
		return result;
	}

	// upDataLoad
	public boolean upDataLoad(ArrayList<String> upLoadList) {
		boolean result = false;
		String insertValue = "";
		String methodName = "dataUpLoad";
		String soapAction = SERVICE_NS + methodName;
		HttpTransportSE ht = new HttpTransportSE(SERVICE_URL);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
		SoapObject request = new SoapObject(SERVICE_NS, methodName);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);
		for (String temp : upLoadList) {
			insertValue += temp.equals("") ? "-" + "/" : temp + "/";
		}
		request.addProperty("info", insertValue);
		try {
			ht.call(soapAction, envelope);
			result = true;
		} catch (Exception e) {
			result = false;
		}
		return result;
	}

	// Transport
	private String soapTransportProcess(String source) {
		String result = "";
		String[] dataArray = source.substring(8, source.length() - 2).replace(" ", "").split(";");
		result = "物料号：" + dataArray[1].split("=")[1] + "\r\n" + "出库库位:" + dataArray[2].split("=")[1] + "\r\n" + "规格："
				+ dataArray[3].split("=")[1] + "*" + dataArray[4].split("=")[1] + "*" + dataArray[5].split("=")[1]
				+ "\r\n" + "重量:" + dataArray[6].split("=")[1] + "\r\n" + "钢种：" + dataArray[7].split("=")[1] + "\r\n"
				+ "出库人：" + dataArray[8].split("=")[1] + "\r\n" + "出库时间：" + dataArray[9].split("=")[1] + "\r\n" + "司机："
				+ dataArray[10].split("=")[1] + "\r\n" + "入库人：" + dataArray[11].split("=")[1] + "\r\n" + "入库时间:"
				+ dataArray[12].split("=")[1] + "\r\n" + "入库库位：" + dataArray[14].split("=")[1];
		return result;
	}

	private String soapUserInfo(String source) {
		String result = "";
		String[] dataArray = source.substring(8, source.length() - 2).replace(" ", "").split(";");
		result = dataArray[0].split("=")[1] + "=" + dataArray[1].split("=")[1] + "=" + dataArray[2].split("=")[1] + ":";
		return result;

	}

}
