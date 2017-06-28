package com.bdsht.xinehealth.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bdsht.xinehealth.model.UserCmd;
import com.bdsht.xinehealth.model.Weixinuserinfo;
import com.jfinal.kit.PropKit;
import com.jfinal.weixin.sdk.api.ApiResult;
import com.jfinal.weixin.sdk.api.SnsAccessToken;
import com.jfinal.weixin.sdk.api.SnsAccessTokenApi;
import com.jfinal.weixin.sdk.api.SnsApi;

public class UserinfoService {

	static String nickname="";

	public  Weixinuserinfo saveUserinfo(String code,String key){

		//用户同意授权，获取code

		String openId="";
		if (code!=null) {
			//					String appId=ApiConfigKit.getApiConfig().getAppId();
			//					String secret=ApiConfigKit.getApiConfig().getAppSecret();

			String appId=PropKit.get("appId");
			String secret=PropKit.get("appSecret");


			//通过code换取网页授权access_token
			SnsAccessToken snsAccessToken=SnsAccessTokenApi.getSnsAccessToken(appId,secret,code);
			String json=snsAccessToken.getJson();
			System.out.println("json>>"+json);
			String token=snsAccessToken.getAccessToken();
			System.out.println("token:"+token);
			openId=snsAccessToken.getOpenid();
			//拉取用户信息(需scope为 snsapi_userinfo)
			ApiResult apiResult=SnsApi.getUserInfo(token, openId);
			System.out.println("openId:"+openId);	
			JSONObject jsonObject=JSON.parseObject(apiResult.getJson());
			System.out.println(jsonObject.toString());
			nickname=jsonObject.getString("nickname");
			String sex=jsonObject.getString("sex");
			String city=jsonObject.getString("city");
			String province=jsonObject.getString("province");
			String country=jsonObject.getString("country");
			String headimgurl=jsonObject.getString("headimgurl");

			System.out.println("nickname:"+nickname);
			try {
				System.out.println("nickname:"+URLEncoder.encode(nickname, "utf-8"));
			} catch (UnsupportedEncodingException e) {
				
				e.printStackTrace();
			}
			
			Weixinuserinfo weixinuserinfo=Weixinuserinfo.dao.findByOpenid(openId);

			//List<Weixinuserinfo> weixinuserinfoList=Weixinuserinfo.dao.find("select * from weixinuserinfo where openid='"+openId+"'");

			

			if(weixinuserinfo==null){
				weixinuserinfo=new Weixinuserinfo().set("openid", openId).set("nickname", nickname).set("sex", sex)
						.set("city", city).set("province", province).set("country", country)
						.set("headimgurl", headimgurl);
				weixinuserinfo.save();	
			}else{
				if(!nickname.equals(weixinuserinfo.getNickname())||!headimgurl.equals(weixinuserinfo.getHeadimgurl())){
					weixinuserinfo.update();
				}
			}

			System.out.println("sex:"+sex);//用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
			System.out.println("city:"+city);//城市
			System.out.println("province:"+province);//省份
			System.out.println("country:"+country);//国家
			System.out.println("headimgurl:"+headimgurl);

			if(!key.equals("留言")){
				userOperationDetails(openId, key, "菜单点击事件消息");
			}
			return weixinuserinfo;
			//renderText("apiResult:"+apiResult.getJson());
		}else {
			//renderText("code is  null");
			return null;
		}

	}

	private static void userOperationDetails(String openid, String textcontent, String messagetype) {
		
//		ApiResult apiResult=UserApi.getUserInfo(openid);
//		JSONObject jsonObject=JSON.parseObject(apiResult.getJson());
//		
//		String nickname=jsonObject.getString("nickname");
		
		new UserCmd().set("openid", openid).set("textcontent", textcontent).set("messagetype", messagetype).save();

	}


}
