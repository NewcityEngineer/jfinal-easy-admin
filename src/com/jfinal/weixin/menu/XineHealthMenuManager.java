package com.jfinal.weixin.menu;


import com.jfinal.kit.JsonKit;
import com.jfinal.kit.PropKit;
import com.jfinal.weixin.sdk.api.ApiConfig;
import com.jfinal.weixin.sdk.api.ApiConfigKit;
import com.jfinal.weixin.sdk.api.ApiResult;
import com.jfinal.weixin.sdk.api.MenuApi;
import com.jfinal.weixin.sdk.api.ShorturlApi;

/**
 * @author Javen
 * @Email javenlife@126.com
 * 菜单管理器类 
 */
public class XineHealthMenuManager  {
	
	public static ApiConfig getApiConfig() {
		ApiConfig ac = new ApiConfig();

		// 配置微信 API 相关常量
		ac.setToken(PropKit.get("token"));
		ac.setAppId(PropKit.get("appId"));
		ac.setAppSecret(PropKit.get("appSecret"));

		/**
		 * 是否对消息进行加密，对应于微信平台的消息加解密方式： 1：true进行加密且必须配置 encodingAesKey
		 * 2：false采用明文模式，同时也支持混合模式
		 */
		ac.setEncryptMessage(PropKit.getBoolean("encryptMessage", false));
		ac.setEncodingAesKey(PropKit.get("encodingAesKey", "setting it in config file"));
		return ac;
	}
	
	public static void main(String[] args) { 

		// 将菜单对象转换成json字符串
		//有问题：主菜单项多了一个type
		String jsonMenu = JsonKit.toJson(getTestMenu()).toString();
		System.out.println(jsonMenu);
		ApiConfig ac = new ApiConfig();

		// 配置微信 API 相关常量
		ac.setAppId("***");
		ac.setAppSecret("****");
		ApiConfigKit.setThreadLocalApiConfig(ac);

		//创建菜单
		ApiResult apiResult=MenuApi.createMenu(jsonMenu);


		System.out.println(apiResult.getJson());
	}  



	/** 
	 * 组装菜单数据 
	 *  
	 * @return 
	 */  
	private static Menu getTestMenu() {  
		String appId="****";
		String oauth_redirect_uri="***";
		
		
		ViewButton btn11 = new ViewButton();
		btn11.setName("信息中心");
		btn11.setType("view");
		
		btn11.setUrl("http://info.xinewang.com");
		
		ViewButton btn12 = new ViewButton();
		btn12.setName("相生相克");
		btn12.setType("view");

		btn12.setUrl("http://xinejiankang.oss-cn-beijing.aliyuncs.com/index.html");

		ViewButton btn13 = new ViewButton();
		btn13.setName("草药信息查询");
		btn13.setType("view");
		btn13.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + appId
				+ "&redirect_uri=http%3A%2F%2F" + oauth_redirect_uri
				+ "%2Fnews%2FgetAllVideos&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect");

		


		ViewButton btn21 = new ViewButton();
		btn21.setName("我要分享");
		btn21.setType("view");
		btn21.setUrl("http://s.p.qq.com/pub/jump?d=AAAPoDzT");

		ViewButton btn22 = new ViewButton();
		btn22.setName("不要做小白鼠");
		btn22.setType("view");
		btn22.setUrl("http://baoguang.xinewang.com");


		// 心e家园
		
		String userInfoUrl="http://xinehealth.xinewang.com/xhUserInfo/";
		
		
//		ViewButton btn31 = new ViewButton();
//		btn31.setName("填写信息");
//		btn31.setType("view");
//		btn31.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + appId
//				+ "&redirect_uri=http%3A%2F%2F" +oauth_redirect_uri
//				+ "%2Fweapp%2F&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect");
		ViewButton btn31 = new ViewButton();
		btn31.setName("填写信息");
		btn31.setType("view");
		btn31.setUrl(userInfoUrl);
		
		ViewButton btn32 = new ViewButton();
		btn32.setName("DIY[愉快]");
		btn32.setType("view");
		btn32.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=" +appId
				+ "&redirect_uri=http%3A%2F%2F" + oauth_redirect_uri
				+ "%2Fhome&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect");


		ViewButton btn33 = new ViewButton();
		btn33.setName("定时提醒");
		btn33.setType("view");
		btn33.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + appId
				+ "&redirect_uri=http%3A%2F%2F" + oauth_redirect_uri
				+ "%2Fmember%2Fmembercenter&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect");




		ComButton mainBtn1 = new ComButton();
		mainBtn1.setName("心e健康");
		mainBtn1.setSub_button(new Button[] { btn11, btn12,btn13});

		ComButton mainBtn2 = new ComButton();
		mainBtn2.setName("我要参与");
		mainBtn2.setSub_button(new Button[] { btn21,btn22});// btn22,
		// btn23

		ComButton mainBtn3 = new ComButton();
		mainBtn3.setName("个人中心");
		mainBtn3.setSub_button(new Button[] { btn31,  btn32,btn33});// btn34,

		Menu menu = new Menu();
		menu.setButton(new Button[] { mainBtn1, mainBtn2, mainBtn3 });

		return menu;
	}
}
