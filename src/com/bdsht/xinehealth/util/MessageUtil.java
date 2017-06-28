package com.bdsht.xinehealth.util;

import java.util.ArrayList;
import java.util.List;

import com.jfinal.kit.PropKit;
import com.jfinal.weixin.sdk.api.ApiConfig;
import com.jfinal.weixin.sdk.api.ApiConfigKit;
import com.jfinal.weixin.sdk.api.MediaApi;
import com.jfinal.weixin.sdk.api.MediaArticles;
import com.jfinal.weixin.sdk.api.MessageApi;

public class MessageUtil {

	public static void main(String[] args) {
		
		ApiConfig ac = new ApiConfig();
		// 配置微信 API 相关常量
		ac.setAppId(PropKit.get("appId"));
		ac.setAppSecret(PropKit.get("appSecret"));
		ApiConfigKit.setThreadLocalApiConfig(ac);

		List<MediaArticles> list=new ArrayList<MediaArticles>();

		MediaArticles mediaArticles1=new MediaArticles();
		mediaArticles1.setTitle("心e旅游");
		mediaArticles1.setThumb_media_id("https://mmbiz.qlogo.cn/mmbiz_jpg/oWS8PldIu1fZ7m8GNz0ticEwLzyeK7MF60vmBVGuLPk41JtywvSU8KzAtuoR8UJdtzVUNP373St8eqBQljc3veg/0");
		mediaArticles1.setContent("http://wx.xinewang.com/xinedata/");
	
		list.add(mediaArticles1);
		MediaApi.addNews(list);
		
	}
	
}
