package com.example.crm7CustomedPlugIn;

public class Scan2DInfo {
	
	public static String scanInfoSplit(String temp){
		String result = "";
		try{
			result = temp.split("\\*")[1].split(":")[1].trim();	
		}catch(Exception e){
			System.out.println(temp+"---");
		}
		
		return result ;
	}
}
