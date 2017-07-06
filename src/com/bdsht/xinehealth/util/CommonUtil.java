package com.bdsht.xinehealth.util;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;

public class CommonUtil {
	public static String getCurrentTime(String format) {

		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date now = new Date();
		String dt = sdf.format(now);

		return dt;
	}

	public static String getTimestamp() {
		return String.valueOf(System.currentTimeMillis());
	}

	// 方法名称：isSameWeek(String date1,String date2)
	// 功能描述：判断date1和date2是否在同一周
	// 输入参数：date1,date2
	// 输出参数：
	// 返 回 值：false 或 true
	// 其它说明：主要用到Calendar类中的一些方法
	// -----------------------------
	public static boolean isSameWeek(String date1, String date2) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date d1 = null;
		Date d2 = null;
		try {
			d1 = format.parse(date1);
			d2 = format.parse(date2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal1.setTime(d1);
		cal2.setTime(d2);
		int subYear = cal1.get(Calendar.YEAR) - cal2.get(Calendar.YEAR);
		// subYear==0,说明是同一年
		if (subYear == 0) {
			if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
				return true;
		}
		// 例子:cal1是"2005-1-1"，cal2是"2004-12-25"
		// java对"2004-12-25"处理成第52周
		// "2004-12-26"它处理成了第1周，和"2005-1-1"相同了
		// 大家可以查一下自己的日历
		// 处理的比较好
		// 说明:java的一月用"0"标识，那么12月用"11"
		else if (subYear == 1 && cal2.get(Calendar.MONTH) == 11) {
			if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
				return true;
		}
		// 例子:cal1是"2004-12-31"，cal2是"2005-1-1"
		else if (subYear == -1 && cal1.get(Calendar.MONTH) == 11) {
			if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
				return true;

		}

		return false;
	}

	public static int daysBetween(String smdate, String bdate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();

		try {
			cal.setTime(sdf.parse(smdate));
			long time1 = cal.getTimeInMillis();
			cal.setTime(sdf.parse(bdate));
			long time2 = cal.getTimeInMillis();
			long between_days = (time2 - time1) / (1000 * 3600 * 24);

			return Integer.parseInt(String.valueOf(between_days));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			return 20;
		}
	}

	public static String setExpireTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar ca = Calendar.getInstance();

		ca.add(Calendar.HOUR_OF_DAY, 2);

		return sdf.format(ca.getTime());
	}
	
	public static void uploadCardToOSS(String bucketName,String folder,String fileName,String path) {
		String endpoint = "*****";
		String accessKeyId = "****";
		String accessKeySecret = "****";
		
		String key = folder+"/" + fileName;
		System.out.println("key:"+key);
		
//		// 二维码图片名称
//		String fileName = assetsName.concat(".jpg");
//		System.out.println("fileName:"+fileName);
//		// 二维码图片存放路径
		path = path.concat(fileName);
		System.out.println("path:"+path);

		/*
		 * Constructs a client instance with your account for accessing OSS
		 */
		OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);

		try {
			client.putObject(bucketName, key, new File(path));
		} catch (OSSException oe) {
			System.out.println("Caught an OSSException, which means your request made it to OSS, "
					+ "but was rejected with an error response for some reason.");
			System.out.println("Error Message: " + oe.getErrorCode());
			System.out.println("Error Code:       " + oe.getErrorCode());
			System.out.println("Request ID:      " + oe.getRequestId());
			System.out.println("Host ID:           " + oe.getHostId());
		} catch (ClientException ce) {
			System.out.println("Caught an ClientException, which means the client encountered "
					+ "a serious internal problem while trying to communicate with OSS, "
					+ "such as not being able to access the network.");
			System.out.println("Error Message: " + ce.getMessage());
			
			System.out.println("Error Message: " + ce.getErrorCode());
			System.out.println("Error Code:       " + ce.getErrorCode());
			System.out.println("Request ID:      " + ce.getRequestId());
			System.out.println("Error Message:           " + ce.getErrorMessage());
			//System.out.println(">>>>"+ce.getError);
		} finally {
			/*
			 * Do not forget to shut down the client finally to release all
			 * allocated resources.
			 */
			client.shutdown();
		}
	}
	public static String getFoodNewsCatagory(String title){

		if(title.contains("速冻")){
			return "速冻食品类";
		}else if(title.contains("婴儿")){
			return "婴儿食品类";
		}else if(title.contains("饮品")||title.contains("酒")){
			return "饮品酒类";
		}else if(title.contains("粮")||title.contains("油")){
			return "粮油类";
		}else if(title.contains("肉")||title.contains("副食")){
			return "肉食副食类";
		}else if(title.contains("蔬菜")||title.contains("水果")){
			return "蔬菜水果类";
		}else if(title.contains("调味品")||title.contains("花椒")
				||title.contains("味精")||title.contains("食糖")
				||title.contains("饴糖")||title.contains("番茄酱")
				||title.contains("酱油")||title.contains("食盐")
				||title.contains("蚝油")||title.contains("鱼露")
				||title.contains("辣椒")||title.contains("鱼露")){
			return "调味品类";
		}else {
			return "食品曝光";
		}

	}
}
