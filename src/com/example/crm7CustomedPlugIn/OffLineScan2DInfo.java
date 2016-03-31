package com.example.crm7CustomedPlugIn;

import java.util.ArrayList;
import java.util.List;

public class OffLineScan2DInfo {

	public static String[] offLineScanInfo(String info) {
		String result[] = null;
		String offLineResult[] = new String[] { " ", " ", " ", " ", " " };
		if (info.split("\n").length > 5) {
			result = info.split("\n");
			offLineResult[0] = result[0].split(":").length > 1 ? result[0].split(":")[1] : "-";
			offLineResult[1] = result[1].split(":").length > 1 ? result[1].split(":")[1] : "-";
			try {
				offLineResult[2] = result[2].split(":")[1].split("×")[0] ;
			} catch (Exception e) {
				offLineResult[2] = " ";
			}
			try {
				offLineResult[3] = result[2].split(":")[1].split("×")[1] ;
			} catch (Exception e) {
				offLineResult[3] = " ";
			}
			try {
				offLineResult[4] = result[2].split(":")[1].split("×")[2];
			} catch (Exception e) {
				offLineResult[4] = " ";
			}

		} else {
			result = info.split(":");
			if (result.length > 3) {
				try {
					offLineResult[0] = result[1].split("\\*")[0];
				} catch (Exception e) {
					offLineResult[0] = " ";
				}
				try {
					offLineResult[1] = result[2].split("\\*")[0];
				} catch (Exception e) {
					offLineResult[1] = " ";
				}
				try {
					String tempTWL = result[3];
					if(tempTWL.split("\\*").length>=3){  //旧标签
						offLineResult[2] = tempTWL.split("\\*")[0];
						offLineResult[3] = tempTWL.split("\\*")[1];
						offLineResult[4] = tempTWL.split("\\*")[2];
					}else{
						tempTWL = splicSpecition(result[3].split("\\*")[0]);
						offLineResult[2] = tempTWL.split("×")[0];
						offLineResult[3] = tempTWL.split("×")[1];
						offLineResult[4] = tempTWL.split("×")[2];
					}
				} catch (Exception e) {
					offLineResult[2] = " ";
					offLineResult[3] = " ";
					offLineResult[4] = " ";
				}

			}
		}
		return offLineResult;

	}
	
	private static String splicSpecition(String info){
		String thickness = "";
		String width = "";
		String length = "";
		List<Object> numList = new ArrayList<Object>();
		int location[] = new int[5];
		numList.add('0');
		numList.add('1');
		numList.add('2');
		numList.add('3');
		numList.add('4');
		numList.add('5');
		numList.add('6');
		numList.add('7');
		numList.add('8');
		numList.add('9');
		numList.add('.');
		int j=-1;
		for(int i=0;i<info.length();i++){
			if(!numList.contains(info.charAt(i))){
				location[++j] = i;
			}
		}
		thickness = info.substring(0,location[0]);
		width = info.substring(location[1]+1,location[2]);
		length = info.substring(location[3]+1);
		return thickness+"×"+width+"×"+length;
	}

}
