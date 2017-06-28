package com.bdsht.xinehealth.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeTran {
	
	public static void main(String[] args) {
		System.out.println(getCurrentTime("yyyy-MM-dd HH:mm:ss"));
		System.out.println(getCurrentTime("yyyyMMddHHmmss"));
	}
	
	public static String getCurrentTime(String format){

		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date now = new Date();
		String dt = sdf.format(now);
		return dt;
	}
}
