package com.bdsht.xinehealth.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

public class HttpClientUtil {

	public static DefaultHttpClient httpClient = null;

	public static HttpClient getInstance() {
		if (httpClient == null) {
			httpClient = new DefaultHttpClient();
		}
		return httpClient;
	}

	public static void disconnect() {
		httpClient = null;
	}

	public static String doGet(String url) {
		return doGet(url, new ArrayList<BasicNameValuePair>());
	}

	public static String doGet(String url, List<BasicNameValuePair> data) {
		/* 建立HTTP Post连线 */
		HttpGet httpGet = new HttpGet(url);
		try {
			HttpResponse httpResponse = HttpClientUtil.getInstance().execute(
					httpGet);
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				return EntityUtils.toString(httpResponse.getEntity(), "utf8");
			} else {
				System.out.println("doGet Error Response: "
						+ httpResponse.getStatusLine().toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String doPost(String url) {
		return doPost(url, new ArrayList<BasicNameValuePair>());
	}

	public static String doPost(String url, List<BasicNameValuePair> data) {
		/* 建立HTTP Post连线 */
		HttpPost httpPost = new HttpPost(url);
		try {
			// 发出HTTP request
			// httpPost.setEntity(new UrlEncodedFormEntity(data, "GBK"));
			httpPost.setEntity(new UrlEncodedFormEntity(data, "UTF-8"));
			// 取得HTTP response
			HttpResponse httpResponse = HttpClientUtil.getInstance().execute(
					httpPost);
			// 若状态码为200 ok
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				// 取出回应字串
				return EntityUtils.toString(httpResponse.getEntity());
			} else {
				System.out.println("doPost Error Response: "
						+ httpResponse.getStatusLine().toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String sinaHttpRequest(String requestUrl){
		StringBuffer buffer = new StringBuffer();
		try {
			URL url = new URL(requestUrl);
			HttpURLConnection httpUrlConn = (HttpURLConnection) url
					.openConnection();
			httpUrlConn.setDoInput(true);
			httpUrlConn.setRequestMethod("GET");
			httpUrlConn.connect();

			// 将返回的输入流转换成字符串
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(
					inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(
					inputStreamReader);
			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return buffer.toString();
	}
	
	public static String httpRequest(String requestUrl) {
		StringBuffer buffer = new StringBuffer();
		try {
			URL url = new URL(requestUrl);
			HttpURLConnection httpUrlConn = (HttpURLConnection) url
					.openConnection();
			httpUrlConn.setDoInput(true);
			httpUrlConn.setRequestMethod("GET");
			httpUrlConn.connect();

			// 将返回的输入流转换成字符串
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(
					inputStream, "GBK");
			BufferedReader bufferedReader = new BufferedReader(
					inputStreamReader);
			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return buffer.toString();
	}
	
	public static String QQHttpRequest(String requestUrl) {
		StringBuffer buffer = new StringBuffer();
		try {
			URL url = new URL(requestUrl);
			HttpURLConnection httpUrlConn = (HttpURLConnection) url
					.openConnection();
			httpUrlConn.setDoInput(true);
			httpUrlConn.setRequestMethod("GET");
			httpUrlConn.connect();

			// 将返回的输入流转换成字符串
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(
					inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(
					inputStreamReader);
			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return buffer.toString();
	}

	public String getPageResponse(String urlstr, String encode)
			throws MalformedURLException, IOException,
			UnsupportedEncodingException {
		String resultContent;
		URL url = new URL(urlstr);
		URLConnection con = url.openConnection();
		con.setAllowUserInteraction(false);
		InputStream urlStream = url.openStream();

		byte b[] = new byte[urlStream.available()];
		int numRead = urlStream.read(b);
		resultContent = new String(b, 0, numRead);

		while (numRead != -1) {
			numRead = urlStream.read(b);
			if (numRead != -1) {
				String newContent = new String(b, 0, numRead, encode);
				resultContent += newContent;
			}
		}

		urlStream.close();

		return resultContent;
	}
}