package com.bdsht.xinehealth.service;

import com.jfinal.kit.PropKit;
import com.jfinal.weixin.sdk.msg.in.InTextMsg;
import com.jfinal.weixin.sdk.msg.out.OutNewsMsg;

public class MemberService {
	public static String prefix = "会员卡";
	public static OutNewsMsg getMemberCard(InTextMsg inTextMsg) {

		String appId=PropKit.get("appId");
		String oauth_redirect_uri=PropKit.get("oauth_redirect_uri");
		
		
		OutNewsMsg outMsg = new OutNewsMsg(inTextMsg);
		outMsg.addNews("会员卡", "您的专属会员卡",
				"",
				"https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + appId
				+ "&redirect_uri=http%3A%2F%2F"+oauth_redirect_uri+"%2Fmember%2fgetCard&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect");
		return outMsg;
	}
}
