package com.jfinal.weixin.service;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.weixin.entity.BaiduAmbitusEntity;
import com.jfinal.weixin.sdk.msg.out.News;
import com.jfinal.weixin.sdk.utils.HttpUtils;

/**
 * @author Javen
 * @Email javenlife@126.com
 * 百度周边
 */
public class BaiduAmbitus {
	
	
	private static List<BaiduAmbitusEntity> getAmbitus(String keyWord,String location){
		 String ak="w5CHR6GMqwCkcTx8l4DqTWls";
		 String url="http://api.map.baidu.com/telematics/v3/local?" +
					"location={location}" +
					"&keyWord={keyWord}" +
					"&output=json" +
					"&ak="+ak;
		url=url.replace("{location}", location);
		url=url.replace("{keyWord}", keyWord);
		
//System.out.println("url>>"+url);
		
		List<BaiduAmbitusEntity> list=new ArrayList<BaiduAmbitusEntity>();
		String resurlJson=HttpUtils.get(url);
		JSONObject jsonObject=JSON.parseObject(resurlJson);
//System.out.println(jsonObject.toString());
		String status=jsonObject.getString("status");
		int total=jsonObject.getInteger("total");
		if (status.equalsIgnoreCase("success")) {
			JSONArray pointList=jsonObject.getJSONArray("pointList");
			int maxItem=0;
			if (total>=10) {
				maxItem=10;
			}else {
				maxItem=pointList.size();
			}
			for (int i = 0; i <maxItem; i++) {
			 JSONObject  result=pointList.getJSONObject(i);
			 String name=result.getString("name");
				JSONObject loc=result.getJSONObject("location");
				String lng=loc.getString("lng");
				String lat=loc.getString("lat");
				String address=result.getString("address");
				
				String distance=result.getString("distance");
				String district=result.getString("district");
				
				
			/*	JSONObject additionalInformation=result.getJSONObject("additionalInformation");
				String infName=additionalInformation.getString("name");
				
			//	System.out.println(infName);
				
				String telephone=additionalInformation.getString("telephone");
				String infAddress=additionalInformation.getString("address");
				String price=additionalInformation.getString("price");
				String tag=additionalInformation.getString("tag");
				JSONArray links=additionalInformation.getJSONArray("link");
				JSONObject linkJsonObject=links.getJSONObject(0);
				String link=linkJsonObject.getString("url");
			//	System.out.println(link);
*/				
				list.add(new BaiduAmbitusEntity(name, lng, lat, address, distance, district));
			}
			return list;
		}else {
			System.out.println("周边查询失败");
		}
		return null;
	}
	
	
	
	public static List<News> getAmbitusService(String keyWord,String location){
		System.out.println("keyWord>"+keyWord+"  location>"+location);
		if (keyWord.trim().equals("") || location.trim().equals("")) {
			return null;
		}
		 List<BaiduAmbitusEntity> list= getAmbitus(keyWord, location);
		 if (list!=null) {
			 List<News> articles=new ArrayList<News>();
			 for (BaiduAmbitusEntity baiduAmbitusEntity : list) {
				 News article=new News();
				 article.setDescription(baiduAmbitusEntity.getName());
				 article.setTitle(baiduAmbitusEntity.getName()+"\n"+baiduAmbitusEntity.getAddress()+" 距离"+baiduAmbitusEntity.getDistance()+"m");
				// String path = request.getContextPath();
				// String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
			     
			     /*System.out.println(">>>>>>>"+path);
			     System.out.println(">>>>>>>"+basePath);*/
				 article.setPicUrl("");
				 //article.setUrl(basePath+"map?lng="+baiduAmbitusEntity.getLng()+"&lat="+baiduAmbitusEntity.getLat());
				 article.setUrl("http://api.map.baidu.com/marker?location="+baiduAmbitusEntity.getLat()+","+baiduAmbitusEntity.getLng()+"&title="+baiduAmbitusEntity.getName()+"&content="+baiduAmbitusEntity.getAddress()+"&output=html&coord_type=gcj02");
				 //article.setUrl("http://apis.map.qq.com/uri/v1/marker?marker=coord:"+baiduAmbitusEntity.getLat()+","+baiduAmbitusEntity.getLng()+";title:"+baiduAmbitusEntity.getName()+";addr:"+baiduAmbitusEntity.getAddress()+"&coord_type=1");
				 articles.add(article);
			 }
			 return articles;
		}
		
		return null;
	}
	
	public static String getGuide(){
		StringBuffer buffer = new StringBuffer();
        buffer.append("\ue13c 附近周边操作指南").append("\n\n");  
        buffer.append("亲  需要发送位置才可以查询附近的酒店、KTV等信息哦！").append("\n");  
        buffer.append("点击对话框下面左边的键盘，切换输入状态，点击+，发送位置即可！").append("\n");  
        buffer.append("回复“?”显示主菜单");  
        return buffer.toString();  
	}
	public static void main(String[] args) {
		List<BaiduAmbitusEntity> list = getAmbitus("厕所", "114.037125,22.645319");
		for (BaiduAmbitusEntity baiduAmbitusEntity : list) {
			System.out.println(baiduAmbitusEntity.toString());
		}
	}
}
