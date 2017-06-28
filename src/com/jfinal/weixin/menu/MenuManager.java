package com.jfinal.weixin.menu;


import com.jfinal.kit.JsonKit;
import com.jfinal.kit.PropKit;
import com.jfinal.weixin.sdk.api.ApiConfig;
import com.jfinal.weixin.sdk.api.ApiConfigKit;
import com.jfinal.weixin.sdk.api.ApiResult;
import com.jfinal.weixin.sdk.api.MenuApi;

/**
 * @author Javen
 * @Email javenlife@126.com
 * 菜单管理器类 
 */
public class MenuManager  {
	public static void main(String[] args) { 

		// 将菜单对象转换成json字符串
		//有问题：主菜单项多了一个type
		String jsonMenu = JsonKit.toJson(getTestMenu()).toString();
		System.out.println(jsonMenu);
		ApiConfig ac = new ApiConfig();

		// 配置微信 API 相关常量
		ac.setAppId(PropKit.get("appId"));
		ac.setAppSecret(PropKit.get("appSecret"));
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
		String appId=PropKit.get("appId");
		String oauth_redirect_uri=PropKit.get("oauth_redirect_uri");

		ViewButton btn11 = new ViewButton();
		btn11.setName("外汇牌价");
		btn11.setType("view");
		
		btn11.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + appId
				+ "&redirect_uri=http%3A%2F%2F" + oauth_redirect_uri
				+ "%2Fweapp%2FgetBankExchangeList&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect");
		
		ViewButton btn12 = new ViewButton();
		btn12.setName("心e卡片");
		btn12.setType("view");

		btn12.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + appId
				+ "&redirect_uri=http%3A%2F%2F" + oauth_redirect_uri
				+ "%2Fnews%2FgetAllCards&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect");

		ViewButton btn13 = new ViewButton();
		btn13.setName("心e视频");
		btn13.setType("view");
		btn13.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + appId
				+ "&redirect_uri=http%3A%2F%2F" + oauth_redirect_uri
				+ "%2Fnews%2FgetAllVideos&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect");

		
		ViewButton btn14 = new ViewButton();
		btn14.setName("心e微杂志");
		btn14.setType("view");
		btn14.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + appId
				+ "&redirect_uri=http%3A%2F%2F" +oauth_redirect_uri
				+ "%2Fnews%2FgetAllWeMagazines&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect");
		
		ViewButton btn15 = new ViewButton();
		btn15.setName("心e图文");
		btn15.setType("view");
		btn15.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + appId
				+ "&redirect_uri=http%3A%2F%2F" + oauth_redirect_uri
				+ "%2Fnews%2FgetAllNews&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect");


		
//		ViewButton btn12 = new ViewButton();
//		btn12.setName("心e微应用");
//		btn12.setType("view");
//		btn12.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + appId
//				+ "&redirect_uri=http%3A%2F%2F" +oauth_redirect_uri
//				+ "%2Fweapp%2F&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect");
		ViewButton btn21 = new ViewButton();
		btn21.setName("心意茶");
		btn21.setType("view");
		btn21.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + appId
				+ "&redirect_uri=http%3A%2F%2F" +oauth_redirect_uri
				+ "%2Fgoods%2FgetXineTea&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect");

		ViewButton btn22 = new ViewButton();
		btn22.setName("心e工艺品");
		btn22.setType("view");
		btn22.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + appId
				+ "&redirect_uri=http%3A%2F%2F" +oauth_redirect_uri
				+ "%2Fgoods%2FgetXineSmallGoods&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect");

		ViewButton btn23 = new ViewButton();
		btn23.setName("硅胶商品");
		btn23.setType("view");
		btn23.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + appId
				+ "&redirect_uri=http%3A%2F%2F" + oauth_redirect_uri
				+ "%2Fgoods&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect");

		ClickButton btn24 = new ClickButton();
		btn24.setName("心e纸飞机");
		btn24.setType("click");
		btn24.setKey("心e纸飞机");

		ViewButton btn25 = new ViewButton();
		btn25.setName("二维码名片");
		btn25.setType("view");
		btn25.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=" +appId
				+ "&redirect_uri=http%3A%2F%2F" + oauth_redirect_uri
				+ "%2Fcard%2FgetNameCard&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect");

		// 心e家园
		
		ClickButton btn31 = new ClickButton();
		btn31.setName("关于心e");
		btn31.setType("click");
		btn31.setKey("关于心e");
		
		ViewButton btn32 = new ViewButton();
		btn32.setName("心e微应用");
		btn32.setType("view");
		btn32.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + appId
				+ "&redirect_uri=http%3A%2F%2F" +oauth_redirect_uri
				+ "%2Fweapp%2F&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect");
		
		ViewButton btn33 = new ViewButton();
		btn33.setName("微官网");
		btn33.setType("view");
		btn33.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=" +appId
				+ "&redirect_uri=http%3A%2F%2F" + oauth_redirect_uri
				+ "%2Fhome&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect");

		ClickButton btn34 = new ClickButton();
		btn34.setName("心e招聘");
		btn34.setType("click");
		btn34.setKey("心e招聘");
		
//		ViewButton btn32 = new ViewButton();
//		btn32.setName("微社区");
//		btn32.setType("view");
//		btn32.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=" +appId
//				+ "&redirect_uri=http%3A%2F%2F" + oauth_redirect_uri
//				+ "%2Fhome%2FgetMicroCommunity&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect");

		ViewButton btn35 = new ViewButton();
		btn35.setName("我的会员卡");
		btn35.setType("view");
		btn35.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + appId
				+ "&redirect_uri=http%3A%2F%2F" + oauth_redirect_uri
				+ "%2Fmember%2Fmembercenter&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect");

		

//		ViewButton btn35 = new ViewButton();
//		btn35.setName("心e游戏");
//		btn35.setType("view");

		//		btn35.setUrl("http://tianlixin.cyansoft.cn/cocosgame/index.html");

//		btn35.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + appId
//				+ "&redirect_uri=http%3A%2F%2F" + oauth_redirect_uri
//				+"%2Fgame%2FcocosGame&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect");



		ComButton mainBtn1 = new ComButton();
		mainBtn1.setName("心e文化");
		mainBtn1.setSub_button(new Button[] { btn11, btn12,btn13,btn15,btn14});

		ComButton mainBtn2 = new ComButton();
		mainBtn2.setName("心e商品");
		mainBtn2.setSub_button(new Button[] { btn21,btn22, btn23, btn24,btn25});// btn22,
		// btn23

		ComButton mainBtn3 = new ComButton();
		mainBtn3.setName("心e家园");
		mainBtn3.setSub_button(new Button[] { btn31,  btn33,btn34,btn32, btn35});// btn34,

		Menu menu = new Menu();
		menu.setButton(new Button[] { mainBtn1, mainBtn2, mainBtn3 });

		return menu;
	}
}
