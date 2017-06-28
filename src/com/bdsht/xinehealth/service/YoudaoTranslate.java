package com.bdsht.xinehealth.service;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.kit.PropKit;
import com.jfinal.weixin.sdk.utils.HttpUtils;

/**
 * @author Javen
 * 百度翻译
 */
public class YoudaoTranslate {
	public static String Regex = "[\\+ ~!@#%^-_=]?";
	//static String transArray[][]={{"zh","en"},{"zh","jp"},{"zh","kor"},{"zh","ru"},{"zh","yue"},{"en","zh"},{"jp","zh"}};
	static String transArray[][]={{"zh","en"},{"en","zh"}};
	private static  String YoudaoTranslates(String q) {

		String query=urlEncodeUTF8(q);
		String keyfrom = PropKit.get("keyfrom");
		String key = PropKit.get("key");
		System.out.println(q+ " "+query);
		//http://fanyi.youdao.com/openapi.do?keyfrom=CongStudyEnglish&key=1494648756&type=data&doctype=json&version=1.1&q=%E8%8B%B9%E6%9E%9C
		String url = "http://fanyi.youdao.com/openapi.do?type=data&doctype=json&version=1.1&keyfrom="
				+ keyfrom
				+ "&q="+ query
				+"&key="+key;


		return HttpUtils.get(url);
	}

	public static void main(String[] args) {
		System.out.println(YoudaoTranslates("苹果"));
	}

	public static Map<String, String> Translates(String content) {
		//String result = null;
		//List<String> list=new ArrayList<String>();
		Map<String, String> map=new HashMap<String, String>();
		try {

			String keyWord = content.trim();
			if(keyWord.equals("")){
				//result=getGuide();
			}else {

				String translateJsonStr = YoudaoTranslates(keyWord);
				System.out.println("resutl 》》"+translateJsonStr);
				if (translateJsonStr != null) {

					JSONObject jsonObject = JSON.parseObject(translateJsonStr);
					System.out.println(jsonObject.toString());
					int errorCode=Integer.parseInt(jsonObject.getString("errorCode"));
					if (errorCode==0) {


						System.out.println(checkString(keyWord));



						String translation="";
						
						JSONArray translationArr=JSON.parseArray(jsonObject.getString("translation"));
						for (int i = 0; i < translationArr.size(); i++) {
							//							translation=translation+(i+1)+"、"+translationArr.getString(i)+"<br>";
							translation=translationArr.getString(i)+"<br>";
						}



						map.put("translation", translation);

						if(checkString(keyWord).contains("ch")){
							String word=translationArr.getString(0).toLowerCase();
							if(word.contains("the ")){
								word=word.replaceAll("the ", "");
							}
							map.put("word", word);
							
							
						}
						else{
							map.put("word",keyWord);

						}
						try{
						 JSONObject basicObject = JSON.parseObject(jsonObject.getString("basic"));
						 JSONArray explains=null;
						
							explains=JSON.parseArray(basicObject.getString("explains"));
							map.put("resultCode", "success");
							
							String basic="";
							for (int i = 0; i < explains.size(); i++) {
								basic=basic+(i+1)+"、"+explains.getString(i)+"<br>";
							}

							map.put("basic", basic);

							String web="";
							JSONArray webArray=JSON.parseArray(jsonObject.getString("web"));

							for (int i = 0; i < webArray.size(); i++) {
								JSONObject obj= JSON.parseObject(webArray.get(i).toString());
								JSONArray valueArr=JSON.parseArray(obj.getString("value"));
								web=web+"<b>--"+obj.getString("key")+"</b><br>";
								for (int j = 0; j < valueArr.size(); j++) {
									web=web+"&nbsp;&nbsp;"+(j+1)+"、"+valueArr.getString(j)+"<br>";
								}

							}
							map.put("web", web);

							
						}
						catch(Exception e){
							
							map.put("resultCode", "error");
						
						}
						
						
					}else {
						//result="翻译出现异常。";
					}
				} else {
					//result = "无法翻译您所输入的内容！\n"+ getGuide();
				}
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		return map;
	}

	/**
	 * UTF-8编码
	 *
	 * @param source
	 * @return
	 */
	private static String urlEncodeUTF8(String source) {
		String result = source;
		try {
			result = java.net.URLEncoder.encode(source, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static String getGuide(){
		StringBuffer buffer = new StringBuffer();
		buffer.append("\ue320 翻译操作指南").append("\n\n");  
		buffer.append("1、\ue513中->\ue50c英").append("\n");  
		//buffer.append("2、\ue513中->\ue50b日").append("\n");  
		//buffer.append("3、\ue513中->\ue514韩").append("\n");  
		// buffer.append("4、\ue513中->\ue512俄罗斯语").append("\n");  
		// buffer.append("5、\ue513中->\ue513粤语").append("\n");
		buffer.append("2、\ue50c英->\ue513中").append("\n");
		//buffer.append("7、\ue50b日->\ue513中").append("\n");
		buffer.append("回复：翻译+序号@内容").append("\n\n");  
		buffer.append("案例：翻译1@抛弃").append("\n");  
		// buffer.append("自动翻译：翻译@我爱你").append("\n");  
		buffer.append("表示：中文\ue513翻译为英语\ue50c").append("\n\n");  
		//buffer.append("回复“?”显示主菜单");  
		return buffer.toString();  

	}

	public static String checkString(String str) {
		String res = "";
		if (str != null) {
			for (int i = 0; i < str.length(); i++) {
				//只要字符串中有中文则为中文
				if (!checkChar(str.charAt(i))) {
					res = "ch";
					break;
				} else {
					res = "en";
				}
			}
		}
		return res;
	}


	//英文占1byte，非英文（可认为是中文）占2byte，根据这个特性来判断字符
	public static boolean checkChar(char ch) {
		if ((ch + "").getBytes().length == 1) {
			return true;//英文
		} else {
			return false;//中文
		}
	}
}
