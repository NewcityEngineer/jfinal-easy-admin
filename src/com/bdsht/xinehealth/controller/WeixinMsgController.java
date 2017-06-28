
package com.bdsht.xinehealth.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bdsht.xinehealth.model.Member;
import com.bdsht.xinehealth.model.User;
import com.bdsht.xinehealth.model.UserCmd;
import com.bdsht.xinehealth.model.XinedataAuthority;
import com.bdsht.xinehealth.model.XinedataMessage;
import com.bdsht.xinehealth.service.MemberService;
import com.bdsht.xinehealth.service.UserSignService;
import com.jfinal.kit.PropKit;
import com.jfinal.kit.StrKit;
import com.jfinal.log.Log;
import com.jfinal.weixin.face.FaceService;
import com.jfinal.weixin.sdk.api.ApiConfig;
import com.jfinal.weixin.sdk.api.ApiConfigKit;
import com.jfinal.weixin.sdk.api.ApiResult;
import com.jfinal.weixin.sdk.api.UserApi;
import com.jfinal.weixin.sdk.jfinal.MsgControllerAdapter;
import com.jfinal.weixin.sdk.msg.in.InImageMsg;
import com.jfinal.weixin.sdk.msg.in.InLinkMsg;
import com.jfinal.weixin.sdk.msg.in.InLocationMsg;
import com.jfinal.weixin.sdk.msg.in.InShortVideoMsg;
import com.jfinal.weixin.sdk.msg.in.InTextMsg;
import com.jfinal.weixin.sdk.msg.in.InVideoMsg;
import com.jfinal.weixin.sdk.msg.in.InVoiceMsg;
import com.jfinal.weixin.sdk.msg.in.event.InCustomEvent;
import com.jfinal.weixin.sdk.msg.in.event.InFollowEvent;
import com.jfinal.weixin.sdk.msg.in.event.InLocationEvent;
import com.jfinal.weixin.sdk.msg.in.event.InMassEvent;
import com.jfinal.weixin.sdk.msg.in.event.InMenuEvent;
import com.jfinal.weixin.sdk.msg.in.event.InQrCodeEvent;
import com.jfinal.weixin.sdk.msg.in.event.InShakearoundUserShakeEvent;
import com.jfinal.weixin.sdk.msg.in.event.InTemplateMsgEvent;
import com.jfinal.weixin.sdk.msg.in.event.InVerifyFailEvent;
import com.jfinal.weixin.sdk.msg.in.event.InVerifySuccessEvent;
import com.jfinal.weixin.sdk.msg.in.speech_recognition.InSpeechRecognitionResults;
import com.jfinal.weixin.sdk.msg.out.News;
import com.jfinal.weixin.sdk.msg.out.OutNewsMsg;
import com.jfinal.weixin.sdk.msg.out.OutTextMsg;
import com.jfinal.weixin.sdk.msg.out.OutVoiceMsg;
import com.jfinal.weixin.service.BaiduAmbitus;
import com.jfinal.weixin.service.BaiduWeatherService;
import com.jfinal.weixin.service.IdService;
import com.jfinal.weixin.service.PhoneService;
import com.jfinal.weixin.util.WeixinUtil;

/**
 * 将此 DemoController 在YourJFinalConfig 中注册路由， 并设置好weixin开发者中心的 URL 与 token ，使
 * URL 指向该 DemoController 继承自父类 WeixinController 的 index
 * 方法即可直接运行看效果，在此基础之上修改相关的方法即可进行实际项目开发
 */
public class WeixinMsgController extends MsgControllerAdapter {

	private static Logger log = LoggerFactory.getLogger(WeixinMsgController.class);

	public static String nearbyContent;// 附近
	public static String location;// 地理位置114.037125,22.645319
	public static String weahterContent;
	public String Regex = "[\\+ ~!@#%^-_=]?";
	static Log logger = Log.getLog(WeixinMsgController.class);
	//	private static final String helpStr = "帮助指南 \n\n" + "1、人脸识别" + "\n"
	//			+ "2、在线翻译" + "\n" + "3、天气查询" + "\n" + "4、手机归属地查询" + "\n" + "5、身份证查询" + "\n" + "6、附近查询"
	//			+ "\n\n"
	//			+ "公众号功能持续完善中\n\n";
	//private static final String helpStr = "帮助指南 \n\n1、人脸识别\n2、在线翻译\n3、天气查询\n4、手机归属地查询\n5、身份证查询\n6、附近查询\n\n公众号功能持续完善中\n\n";



	/**
	 * 如果要支持多公众账号，只需要在此返回各个公众号对应的 ApiConfig 对象即可 可以通过在请求 url 中挂参数来动态从数据库中获取
	 * ApiConfig 属性值
	 */
	public ApiConfig getApiConfig() {
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


	/**
	 * 实现父类抽方法，处理文本消息 本例子中根据消息中的不同文本内容分别做出了不同的响应，同时也是为了测试 jfinal weixin
	 * sdk的基本功能： 本方法仅测试了 OutTextMsg、OutNewsMsg、OutMusicMsg 三种类型的OutMsg，
	 * 其它类型的消息会在随后的方法中进行测试
	 */
	protected void processInTextMsg(InTextMsg inTextMsg) {


		String msgContent = inTextMsg.getContent().trim();
		String fromUserName=inTextMsg.getFromUserName();
		//String oauth_redirect_uri=PropKit.get("oauth_redirect_uri");


		// 更新用户交互的最新时间
		updateUserLastSendMessageDatetime(fromUserName);
		userOperationDetails(fromUserName, msgContent, "文本消息");
		System.out.println(">>>>>>>>>>>>>>");
		System.out.println("msgContent:"+msgContent);
		// 帮助提示
		try{
			List<XinedataMessage> xinedataMessages=XinedataMessage.dao.find("select * from xinedata_message where state=1 and style='"+msgContent.trim()+"'");
			if(msgContent.equals("会员卡")){
				OutNewsMsg outMsg =MemberService.getMemberCard(inTextMsg);
				render(outMsg);
			}else if (msgContent.trim().equals("正")) {
				UserSignService userSignService = new UserSignService();
				try {
					msgContent = userSignService.sign(inTextMsg);
				} catch (Exception e) {
					e.printStackTrace();
				}
				renderOutTextMsg(msgContent+getRandomWishWord());

			}else if (msgContent.startsWith("天气")) {
				msgContent = msgContent.replaceAll("^天气" + Regex, "").trim();

				if (!msgContent.equals("")) {
					OutNewsMsg outMsg = BaiduWeatherService.getWeatherService(msgContent, inTextMsg);
					location = null;
					weahterContent = null;

					render(outMsg);
				} else {
					// 内容为空》》》》 天气

					if (!StrKit.isBlank(location)) {
						// 地址不为空
						OutNewsMsg outMsg = BaiduWeatherService.getWeatherService(location, inTextMsg);
						location = null;
						weahterContent = null;

						render(outMsg);
					} else {
						msgContent = BaiduWeatherService.getGuide();

						renderOutTextMsg(msgContent);
					}
				}
			} else if (msgContent.startsWith("归属地") || msgContent.contains("归属地@")) {
				msgContent = msgContent.replaceAll("^归属地" + Regex, "");
				msgContent = PhoneService.getPhoneInfo(msgContent);
				renderOutTextMsg(msgContent);

			} else if (msgContent.startsWith("身份证") || msgContent.contains("身份证@")) {
				msgContent = msgContent.replaceAll("^身份证" + Regex, "");
				msgContent = IdService.getIdInfo(msgContent);
				renderOutTextMsg(msgContent);

			} else if (msgContent.startsWith("附近")) {
				msgContent = msgContent.replaceAll("^附近" + Regex, "");
				nearbyContent = msgContent;
				if (location == null || nearbyContent == null || location.trim().equals("")
						|| nearbyContent.trim().equals("")) {
					msgContent = BaiduAmbitus.getGuide();
					renderOutTextMsg(msgContent);

				} else {
					List<News> ambitusService = BaiduAmbitus.getAmbitusService(nearbyContent, location);
					if (ambitusService.size() > 0) {
						OutNewsMsg outMsg = new OutNewsMsg(inTextMsg);
						outMsg.addNews(ambitusService);
						render(outMsg);
						nearbyContent = null;
						location = null;
						return;
					} else {
						msgContent = "\ue252 查询周边失败，请检查。";
						renderOutTextMsg(msgContent);
					}
				}
			} else if ("授权".equalsIgnoreCase(msgContent)) {
				String url = PropKit.get("domain") + "/oauth2/oauth";
				String urlStr = "<a href=\"" + url + "\">点击我授权</a>";
				renderOutTextMsg("授权地址" + urlStr);
			} else if ("jssdk".equalsIgnoreCase(msgContent)) {
				String url = PropKit.get("domain") + "/jssdk";
				String urlStr = "<a href=\"" + url + "\">JSSDK</a>";
				renderOutTextMsg("地址" + urlStr);
			}
//			else if(msgContent.equals("今日外汇牌价")){
//				OutNewsMsg outMsg =ForeignExchangeService.getForeignExchange(inTextMsg);
//				render(outMsg);
//			}
			else if(xinedataMessages.size()>0){
				XinedataMessage xinedataMessage=xinedataMessages.get(0);
				if(msgContent.trim().equals("留言") || msgContent.trim().equals("回复")){
					List<XinedataAuthority> xinedataAuthorities=XinedataAuthority.dao.find("select * from xinedata_authority where level='word' and openid='"+fromUserName+"'");
					if(xinedataAuthorities.size()>0)
						msgContent = "<a href=\""+xinedataMessage.getContent()+"\">查看留言列表</a>";
				}else if(msgContent.trim().equals("题目管理")){
					List<XinedataAuthority> xinedataAuthorities=XinedataAuthority.dao.find("select * from xinedata_authority where level='game' and openid='"+fromUserName+"'");
					if(xinedataAuthorities.size()>0)

						msgContent = "<a href=\""+xinedataMessage.getContent()+"\">题目管理系统</a>";
				}else if (msgContent.equals("1") || msgContent.equals("人脸识别")) {
					msgContent = xinedataMessage.getContent() + WeixinUtil.emoji(0x1F4F7);
				}else if (msgContent.equals("2") || msgContent.equals("在线翻译")||msgContent.startsWith("翻译")){
					msgContent="<a href=\""+xinedataMessage.getContent()+"/wordvideo\">点击此次进行翻译</a>";
				}else if (msgContent.equals("3") || msgContent.equals("天气查询")){
					msgContent = WeixinUtil.emoji(0xe44a)+xinedataMessage.getContent();
				}else if (msgContent.equals("4") || msgContent.equals("手机归属地查询")){
					msgContent = WeixinUtil.emoji(0xe00a)+xinedataMessage.getContent();
				}else if (msgContent.equals("5") || msgContent.equals("身份证查询")){
					msgContent = WeixinUtil.emoji(0xe321)+xinedataMessage.getContent();
				}else {

					msgContent=xinedataMessage.getContent();	


				}
				if(msgContent.contains("；"))
					renderOutTextMsg(msgContent.replaceAll("；", "\n"));
				else
					renderOutTextMsg(msgContent);
			}else {
				renderOutTextMsg("亲，您发送的消息：《" + msgContent + "》我们已经收到，再次感谢您关注心e");
			}



		}catch(Exception e){
			log.error(e.toString());

		}
	}

	/**
	 * 实现父类抽方法，处理图片消息
	 */
	protected void processInImageMsg(InImageMsg inImageMsg) {
		String fromUserName=inImageMsg.getFromUserName();
		userOperationDetails(fromUserName,inImageMsg.getPicUrl(), "图片消息"); 
		// 更新用户交互的最新时间
		updateUserLastSendMessageDatetime(fromUserName);

		//				OutImageMsg outMsg = new OutImageMsg(inImageMsg);
		//				 // 将刚发过来的图片再发回去
		//				 outMsg.setMediaId(inImageMsg.getMediaId());


		//render(outMsg);
		try{
			String picUrl =inImageMsg.getPicUrl();
			String respContent=FaceService.detect(picUrl);
			renderOutTextMsg(respContent);
		}catch(Exception e){

			log.error(e.toString());
		}

		//renderOutTextMsg("您发送的是图片消息，图片链接为:"+inImageMsg.getPicUrl());

	}

	/**
	 * 实现父类抽方法，处理语音消息
	 */
	protected void processInVoiceMsg(InVoiceMsg inVoiceMsg) {
		String fromUserName=inVoiceMsg.getFromUserName();
		userOperationDetails(fromUserName,inVoiceMsg.getMediaId(), "语音消息"); 
		// 更新用户交互的最新时间
		updateUserLastSendMessageDatetime(fromUserName);
		try{
			OutVoiceMsg outMsg = new OutVoiceMsg(inVoiceMsg);
			// 将刚发过来的语音再发回去
			outMsg.setMediaId(inVoiceMsg.getMediaId());
			render(outMsg);
		}catch(Exception e){

			log.error(e.toString());
		}
	}

	/**
	 * 实现父类抽方法，处理视频消息
	 */
	protected void processInVideoMsg(InVideoMsg inVideoMsg) {
		/*
		 * 腾讯 api 有 bug，无法回复视频消息，暂时回复文本消息代码测试 OutVideoMsg outMsg = new
		 * OutVideoMsg(inVideoMsg); outMsg.setTitle("OutVideoMsg 发送");
		 * outMsg.setDescription("刚刚发来的视频再发回去"); // 将刚发过来的视频再发回去，经测试证明是腾讯官方的 api
		 * 有 bug，待 api bug 却除后再试 outMsg.setMediaId(inVideoMsg.getMediaId());
		 * render(outMsg);
		 */
		String fromUserName=inVideoMsg.getFromUserName();
		userOperationDetails(fromUserName,inVideoMsg.getMediaId(), "视频消息"); 

		// 更新用户交互的最新时间
		updateUserLastSendMessageDatetime(fromUserName);
		try{
			OutTextMsg outMsg = new OutTextMsg(inVideoMsg);
			outMsg.setContent("\t视频消息已成功接收，该视频的 mediaId 为: " + inVideoMsg.getMediaId());
			render(outMsg);
		}catch(Exception e){

			log.error(e.toString());
		}
	}

	@Override
	protected void processInShortVideoMsg(InShortVideoMsg inShortVideoMsg) {
		String fromUserName=inShortVideoMsg.getFromUserName();
		userOperationDetails(fromUserName,inShortVideoMsg.getMediaId(), "短视频消息"); 
		// 更新用户交互的最新时间
		updateUserLastSendMessageDatetime(fromUserName);

		try{
			OutTextMsg outMsg = new OutTextMsg(inShortVideoMsg);
			outMsg.setContent("\t视频消息已成功接收，该视频的 mediaId 为: " + inShortVideoMsg.getMediaId());
			render(outMsg);
		}catch(Exception e){

			log.error(e.toString());
		}
	}

	/**
	 * 实现父类抽方法，处理地址位置消息
	 */
	protected void processInLocationMsg(InLocationMsg inLocationMsg) {
		// OutTextMsg outMsg = new OutTextMsg(inLocationMsg);
		// outMsg.setContent("已收到地理位置消息:" + "\nlocation_X = " +
		// inLocationMsg.getLocation_X() + "\nlocation_Y = "
		// + inLocationMsg.getLocation_Y() + "\nscale = " +
		// inLocationMsg.getScale() + "\nlabel = "
		// + inLocationMsg.getLabel());
		// render(outMsg);
		String fromUserName=inLocationMsg.getFromUserName();


		userOperationDetails(fromUserName,"x:"+inLocationMsg.getLocation_X()+";y:"+inLocationMsg.getLocation_Y(), "位置消息"); 
		// 更新用户交互的最新时间
		updateUserLastSendMessageDatetime(fromUserName);


		try{
			String Location_X = inLocationMsg.getLocation_X();

			String Location_Y = inLocationMsg.getLocation_Y();
			System.out.println("Location_X:" + Location_X + " Location_Y:" + Location_Y);
			location = Location_Y + "," + Location_X;

			String respContent = "";
			if (StrKit.isBlank(nearbyContent) && StrKit.isBlank(weahterContent)) {
				respContent = "您发送的是地理位置消息！\n\n 1、查询天气 直接回复【天气】\n2、查询附近 如：附近酒店";
				renderOutTextMsg(respContent);
			} else {
				if (!StrKit.isBlank(nearbyContent)) {
					List<News> ambitusService = BaiduAmbitus.getAmbitusService(nearbyContent, location);
					if (ambitusService.size() > 0) {
						OutNewsMsg outMsg = new OutNewsMsg(inLocationMsg);
						outMsg.addNews(ambitusService);
						render(outMsg);
						nearbyContent = null;
						location = null;
						return;
					} else {
						respContent = "\ue252 查询周边失败，请检查。";
						renderOutTextMsg(respContent);
					}
				} else if (!StrKit.isBlank(weahterContent)) {
					respContent = BaiduWeatherService.getWeatherService(location);
					weahterContent = null;
					location = null;
					renderOutTextMsg(respContent);
				}

			}
		}catch(Exception e){

			log.error(e.toString());
		}
	}

	@Override
	protected void processInQrCodeEvent(InQrCodeEvent inQrCodeEvent) {
		String respContent="";
		String fromUserName=inQrCodeEvent.getFromUserName();
		// 更新用户交互的最新时间
		updateUserLastSendMessageDatetime(fromUserName);
		XinedataMessage xinedataMessage=XinedataMessage.dao.find("select * from xinedata_message where state=1 and style='关注'").get(0);

		try{

			if (InQrCodeEvent.EVENT_INQRCODE_SUBSCRIBE.equals(inQrCodeEvent.getEvent())) {

				logger.debug("扫码未关注：" + inQrCodeEvent.getFromUserName());
				//			OutTextMsg outMsg = new OutTextMsg(inQrCodeEvent);
				//			outMsg.setContent("感谢您的关注，二维码内容：" + inQrCodeEvent.getEventKey());
				//			
				//			userOperationDetails(fromUserName, "扫码未关注", "订阅消息");
				//			render(outMsg);

				//respContent= "亲，您好，[愉快]欢迎来到心e国度[跳跳]，在这里，我们将用硅胶魔法带给您的生活根本的改变。[玫瑰]在这里，心e产品时刻守护着您的健康，您只需关注心e动态，即可成为健康达人。[奋斗]在这里，心e邀您一起守护地球，您只要践行低碳理念，就能为绿色地球贡献力量。[酷]还在等什么？跟心e一起，踏上奇妙旅程。[转圈]";
				respContent=xinedataMessage.getContent();
				System.out.println(fromUserName+">>>>>>>>>扫码未关注");
				userOperationDetails(fromUserName, "关注", "订阅消息");
				renderOutTextMsg(respContent);
			}
			if (InQrCodeEvent.EVENT_INQRCODE_SCAN.equals(inQrCodeEvent.getEvent())) {
				logger.debug("扫码已关注：" + inQrCodeEvent.getFromUserName());
				//String key = inQrCodeEvent.getEventKey();

				//respContent= "亲，您好，[愉快]欢迎来到心e国度[跳跳]，在这里，我们将用硅胶魔法带给您的生活根本的改变。[玫瑰]在这里，心e产品时刻守护着您的健康，您只需关注心e动态，即可成为健康达人。[奋斗]在这里，心e邀您一起守护地球，您只要践行低碳理念，就能为绿色地球贡献力量。[酷]还在等什么？跟心e一起，踏上奇妙旅程。[转圈]";
				respContent=xinedataMessage.getContent();
				System.out.println(fromUserName+">>>>>>>>>扫码已关注");
				userOperationDetails(fromUserName, "已关注", "订阅消息");
				renderOutTextMsg(respContent);
			}
			//取消关注
			if(inQrCodeEvent.getEvent().equals("unsubscribe")){
				System.out.println(fromUserName+">>>>>>>>>取消关注");
				userOperationDetails(fromUserName, "取消关注", "取消订阅消息");
			}
		}catch(Exception e){

			log.error(e.toString());
		}
	}

	@Override
	protected void processInLocationEvent(InLocationEvent inLocationEvent) {

		String fromUserName=inLocationEvent.getFromUserName();
		userOperationDetails(fromUserName,"地理位置是：\n" + inLocationEvent.getLatitude() + "\n" + inLocationEvent.getLongitude(), "地理位置事件"); 
		// 更新用户交互的最新时间
		updateUserLastSendMessageDatetime(fromUserName);
		logger.debug("发送地理位置事件：" + inLocationEvent.getFromUserName());
		try{
			OutTextMsg outMsg = new OutTextMsg(inLocationEvent);

			outMsg.setContent("地理位置是：\n" + inLocationEvent.getLatitude() + "\n" + inLocationEvent.getLongitude());
			render(outMsg);
		}catch(Exception e){

			log.error(e.toString());
		}
	}

	@Override
	protected void processInMassEvent(InMassEvent inMassEvent) {
		logger.debug("测试方法：processInMassEvent()");
		renderNull();
	}

	/**
	 * 实现父类抽方法，处理自定义菜单事件
	 */
	protected void processInMenuEvent(InMenuEvent inMenuEvent) {
		logger.debug("菜单事件：" + inMenuEvent.getFromUserName());
		//		OutTextMsg outMsg = new OutTextMsg(inMenuEvent);
		//		outMsg.setContent("菜单事件内容是：" + inMenuEvent.getEventKey());
		//		render(outMsg);

		String fromUserName=inMenuEvent.getFromUserName();
		// 更新用户交互的最新时间
		updateUserLastSendMessageDatetime(fromUserName);
		String eventKey=inMenuEvent.getEventKey();
		//userOperationDetails(fromUserName, eventKey, "菜单点击事件消息");
		// 根据key值判断用户点击的按钮
		try{
			if (eventKey.equals("关于心e")) {

				userOperationDetails(fromUserName, "关于心e", "菜单点击事件消息");

				OutNewsMsg outMsg = new OutNewsMsg(inMenuEvent);
				outMsg.addNews("关于心e", "",
						"https://mmbiz.qlogo.cn/mmbiz/oWS8PldIu1eE1N6P0vfueX2GeAnq39VBD1qicFejd4bQoEjyunkhD2jQiaKCiabD12OU53F7UUJCI4CVVx0ibMWicLA/0?wx_fmt=jpeg",
						"http://mp.weixin.qq.com/s?__biz=MzA5OTU5MTE4Mg==&mid=209866895&idx=1&sn=f8167102ed44f6a5e3ed9906b627146a#rd");
				outMsg.addNews("企业简介", "",
						"https://mmbiz.qlogo.cn/mmbiz/oWS8PldIu1eE1N6P0vfueX2GeAnq39VBBFA2cCmwuS9MzwWp43xEhibIedj4aKJ3cOflG20BrxnMBTJddqDdjVg/0?wx_fmt=jpeg",
						"http://mp.weixin.qq.com/s?__biz=MzA5OTU5MTE4Mg==&mid=209866895&idx=2&sn=e6531e545639f1fdb5a1d301b00ff8ef#rd");
				outMsg.addNews("消费理念", "",
						"https://mmbiz.qlogo.cn/mmbiz/oWS8PldIu1eE1N6P0vfueX2GeAnq39VBMAPVxuNzic6oZwepX6w8e9ibBaQbls5YxjYicGGhEG3eicm54pZCJazialw/0?wx_fmt=jpeg",
						"http://mp.weixin.qq.com/s?__biz=MzA5OTU5MTE4Mg==&mid=209866895&idx=3&sn=30016d7883e63d8eddf53f7497513584#rd");
				render(outMsg);
			}

			if (eventKey.equals("心e纸飞机")) {
				userOperationDetails(fromUserName, "心e纸飞机", "菜单点击事件消息");

				OutNewsMsg outMsg = new OutNewsMsg(inMenuEvent);
				outMsg.addNews("纸飞机1说明书", "",
						"https://mmbiz.qlogo.cn/mmbiz/oWS8PldIu1dOgibpgV1dbttUfTYzPrAo0ichia1qjD1E4240XNpMFDFFicGF9nxxGtgcZnkWBCyAIm0mcmMQ4mpKZg/0?wx_fmt=jpeg",
						"http://mp.weixin.qq.com/s?__biz=MzA5OTU5MTE4Mg==&mid=211718217&idx=1&sn=df6d91ea27894149f7b7ac6d8064cc1a#rd");
				outMsg.addNews("纸飞机2说明书", "",
						"https://mmbiz.qlogo.cn/mmbiz/oWS8PldIu1dOgibpgV1dbttUfTYzPrAo03iaMhQu5RFAsqq7j8iaFISsV9tEYLVcBxFYOkEmPIT88501SEQXMwXSA/0?wx_fmt=jpeg",
						"http://mp.weixin.qq.com/s?__biz=MzA5OTU5MTE4Mg==&mid=211718217&idx=2&sn=065b8a4f68cc3837c8d87c7139cc118d#rd");
				outMsg.addNews("纸飞机3说明书", "",
						"https://mmbiz.qlogo.cn/mmbiz/oWS8PldIu1dOgibpgV1dbttUfTYzPrAo0p18w2ibK1sqkbIOnQh13eNATAclpIibMQdqRruNzyV9GnJ2FQan0hl7w/0?wx_fmt=jpeg",
						"http://mp.weixin.qq.com/s?__biz=MzA5OTU5MTE4Mg==&mid=211718217&idx=3&sn=d7f03e114e4ad03da8a7a76eea6570bf#rd");


				outMsg.addNews("纸飞机4说明书","",
						"https://mmbiz.qlogo.cn/mmbiz/oWS8PldIu1dOgibpgV1dbttUfTYzPrAo0XdcXAjVxMufxb99pN1XUvDjM6GsvAFDsPAYnqQfspyuK5fYF8h01ag/0?wx_fmt=jpeg",
						"http://mp.weixin.qq.com/s?__biz=MzA5OTU5MTE4Mg==&mid=211718217&idx=4&sn=f857a05956f265dfe95fa7f780147abb#rd");

				outMsg.addNews("纸飞机5说明书","",
						"https://mmbiz.qlogo.cn/mmbiz/oWS8PldIu1dOgibpgV1dbttUfTYzPrAo02t5TwGotTjE3D781u6JKuiaxuEHlV9rBsiaKAwrjTa58Wpwcs56qJgXQ/0?wx_fmt=jpeg"
						,"http://mp.weixin.qq.com/s?__biz=MzA5OTU5MTE4Mg==&mid=211718217&idx=5&sn=59af96862904f7e1ebd049fe46f3672b#rd");

				outMsg.addNews("纸飞机6说明书","",
						"https://mmbiz.qlogo.cn/mmbiz/oWS8PldIu1dOgibpgV1dbttUfTYzPrAo03IewuWPK0stNIvRRyAEHTXeYicjmYhFaDGIXlC5Cqsg8VN5LRyDDdow/0?wx_fmt=jpeg"
						,"http://mp.weixin.qq.com/s?__biz=MzA5OTU5MTE4Mg==&mid=211718217&idx=6&sn=eb59083b29b82f2223001e741549f30d#rd");

				outMsg.addNews("纸飞机7说明书","",
						"https://mmbiz.qlogo.cn/mmbiz/oWS8PldIu1dOgibpgV1dbttUfTYzPrAo0XDLEITfkcqHKnpKJGAQSTaOr15ptQW9XwdZkVKCfCJlgRnZgTRa10Q/0?wx_fmt=jpeg"
						,"http://mp.weixin.qq.com/s?__biz=MzA5OTU5MTE4Mg==&mid=211718217&idx=7&sn=051150960dc6b94410e38683682ac19f#rd");

				outMsg.addNews("纸飞机8说明书","",
						"https://mmbiz.qlogo.cn/mmbiz/oWS8PldIu1dOgibpgV1dbttUfTYzPrAo0Tsy2bVeLpTo7pbicxsXMC7F9Hum61mZSW9zSjTsjb6gKFt9epF9gcLg/0?wx_fmt=jpeg"
						,"http://mp.weixin.qq.com/s?__biz=MzA5OTU5MTE4Mg==&mid=211718217&idx=8&sn=f684243e3cf01c4104018373f6166a8b#rd");

				render(outMsg);

			}

			if (eventKey.equals("心e招聘")) {
				userOperationDetails(fromUserName, "心e招聘", "菜单点击事件消息");

				OutNewsMsg outMsg = new OutNewsMsg(inMenuEvent);
				outMsg.addNews("【我们招聘啦！】", "还在为闲散时光的白白浪费而无可奈何吗？ 还在因无法实现梦想而独自悲伤吗？今天，布达斯哈特帮你实现梦想，闲暇时间也能创造人生辉煌!!!!",
						"https://mmbiz.qlogo.cn/mmbiz/oWS8PldIu1d9gKZMDUTOOUTeDzK5m8NiaqHQM3wZTic5VrsuNvgQfkYRokapm5GgLe7mxmdCibjnmBxAFqwXazz8g/0?wx_fmt=jpeg",
						"http://mp.weixin.qq.com/s?__biz=MzA5OTU5MTE4Mg==&mid=209876074&idx=1&sn=f8ae393967330ed50f0fccef53a72d05#rd");
				render(outMsg);
			}

		}catch(Exception e){

			log.error(e.toString());
		}

	}

	@Override
	protected void processInSpeechRecognitionResults(InSpeechRecognitionResults inSpeechRecognitionResults) {
		logger.debug("语音识别事件：" + inSpeechRecognitionResults.getFromUserName());

		String fromUserName=inSpeechRecognitionResults.getFromUserName();
		// 更新用户交互的最新时间
		updateUserLastSendMessageDatetime(fromUserName);

		try{
			OutTextMsg outMsg = new OutTextMsg(inSpeechRecognitionResults);

			outMsg.setContent("语音识别内容是：" + inSpeechRecognitionResults.getRecognition());
			render(outMsg);
		}catch(Exception e){

			log.error(e.toString());
		}
	}

	/**
	 * 实现父类抽方法，处理链接消息 特别注意：测试时需要发送我的收藏中的曾经收藏过的图文消息，直接发送链接地址会当做文本消息来发送
	 */
	protected void processInLinkMsg(InLinkMsg inLinkMsg) {
		String fromUserName=inLinkMsg.getFromUserName();
		// 更新用户交互的最新时间
		updateUserLastSendMessageDatetime(fromUserName);
		try{

			OutNewsMsg outMsg = new OutNewsMsg(inLinkMsg);

			outMsg.addNews("链接消息已成功接收", "链接使用图文消息的方式发回给你，还可以使用文本方式发回。点击图文消息可跳转到链接地址页面，是不是很好玩 :)",
					"http://mmbiz.qpic.cn/mmbiz/zz3Q6WSrzq1ibBkhSA1BibMuMxLuHIvUfiaGsK7CC4kIzeh178IYSHbYQ5eg9tVxgEcbegAu22Qhwgl5IhZFWWXUw/0",
					inLinkMsg.getUrl());
			render(outMsg);
		}catch(Exception e){

			log.error(e.toString());
		}
	}

	@Override
	protected void processInCustomEvent(InCustomEvent inCustomEvent) {

		String fromUserName=inCustomEvent.getFromUserName();
		// 更新用户交互的最新时间
		updateUserLastSendMessageDatetime(fromUserName);

		System.out.println("processInCustomEvent() 方法测试成功");
	}

	/**
	 * 实现父类抽方法，处理关注/取消关注消息
	 */
	protected void processInFollowEvent(InFollowEvent inFollowEvent) {
		//		OutTextMsg outMsg = new OutTextMsg(inFollowEvent);
		//		outMsg.setContent("感谢关注 JFinal Weixin 极速开发服务号，为您节约更多时间，去陪恋人、家人和朋友 :) \n\n\n " + helpStr);
		//		// 如果为取消关注事件，将无法接收到传回的信息
		//		render(outMsg);
		XinedataMessage xinedataMessage=XinedataMessage.dao.find("select * from xinedata_message where state=1 and style='关注'").get(0);

		String fromUserName=inFollowEvent.getFromUserName();
		updateUserLastSendMessageDatetime(fromUserName);
		String event=inFollowEvent.getEvent();
		String respContent="";
		try{
			if (InQrCodeEvent.EVENT_INQRCODE_SUBSCRIBE.equals(event)) {

				logger.debug("扫码未关注：" + respContent);
				//			OutTextMsg outMsg = new OutTextMsg(inQrCodeEvent);
				//			outMsg.setContent("感谢您的关注，二维码内容：" + inQrCodeEvent.getEventKey());
				//			
				//			userOperationDetails(fromUserName, "扫码未关注", "订阅消息");
				//			render(outMsg);

				//respContent= "亲，您好，[愉快]欢迎来到心e国度[跳跳]，在这里，我们将用硅胶魔法带给您的生活根本的改变。[玫瑰]在这里，心e产品时刻守护着您的健康，您只需关注心e动态，即可成为健康达人。[奋斗]在这里，心e邀您一起守护地球，您只要践行低碳理念，就能为绿色地球贡献力量。[酷]还在等什么？跟心e一起，踏上奇妙旅程。[转圈]";
				respContent=xinedataMessage.getContent();
				System.out.println(fromUserName+">>>>>>>>>扫码未关注");
				userOperationDetails(fromUserName, "关注", "订阅消息");
				
				
				renderOutTextMsg(respContent);
			}
			if (InQrCodeEvent.EVENT_INQRCODE_SCAN.equals(event)) {
				logger.debug("扫码已关注：" + respContent);
				//String key = inQrCodeEvent.getEventKey();

				//respContent= "亲，您好，[愉快]欢迎来到心e国度[跳跳]，在这里，我们将用硅胶魔法带给您的生活根本的改变。[玫瑰]在这里，心e产品时刻守护着您的健康，您只需关注心e动态，即可成为健康达人。[奋斗]在这里，心e邀您一起守护地球，您只要践行低碳理念，就能为绿色地球贡献力量。[酷]还在等什么？跟心e一起，踏上奇妙旅程。[转圈]";
				respContent=xinedataMessage.getContent();
				System.out.println(fromUserName+">>>>>>>>>扫码已关注");
				userOperationDetails(fromUserName, "已关注", "订阅消息");
				
				
				ApiConfig ac = new ApiConfig();
				// 配置微信 API 相关常量
				ac.setAppId(PropKit.get("appId"));
				ac.setAppSecret(PropKit.get("appSecret"));
				ApiConfigKit.setThreadLocalApiConfig(ac);

				ApiResult apiResult=UserApi.getUserInfo(fromUserName);
				JSONObject jsonObject=JSON.parseObject(apiResult.getJson());

				String nickname=jsonObject.getString("nickname");
				System.out.println("************"+nickname);
				
				renderOutTextMsg(respContent);
			}
			//取消关注
			if(event.equals("unsubscribe")){
				logger.debug("取消关注：" + respContent);
				System.out.println(fromUserName+">>>>>>>>>取消关注");
				userOperationDetails(fromUserName, "取消关注", "取消订阅消息");
				
				
				
			}


		}catch(Exception e){

			log.error(e.toString());
		}
	}

	// 处理接收到的模板消息是否送达成功通知事件
	protected void processInTemplateMsgEvent(InTemplateMsgEvent inTemplateMsgEvent) {

		String fromUserName=inTemplateMsgEvent.getFromUserName();
		updateUserLastSendMessageDatetime(fromUserName);

		String status = inTemplateMsgEvent.getStatus();
		renderOutTextMsg("模板消息是否接收成功：" + status);
	}

	@Override
	protected void processInShakearoundUserShakeEvent(InShakearoundUserShakeEvent inShakearoundUserShakeEvent) {

		String fromUserName=inShakearoundUserShakeEvent.getFromUserName();
		updateUserLastSendMessageDatetime(fromUserName);

		logger.debug("摇一摇周边设备信息通知事件：" + inShakearoundUserShakeEvent.getFromUserName());
		try{
			OutTextMsg outMsg = new OutTextMsg(inShakearoundUserShakeEvent);

			outMsg.setContent("摇一摇周边设备信息通知事件UUID：" + inShakearoundUserShakeEvent.getUuid());
			render(outMsg);
		}catch(Exception e){

			log.error(e.toString());
		}
	}

	public static void main(String[] args) {
		String str="\ue00a 手机归属地查询操作指南：\n\n归属地@手机号码\n";
		System.out.println(str);

	}

	@Override
	protected void processInVerifySuccessEvent(InVerifySuccessEvent inVerifySuccessEvent) {

		String fromUserName=inVerifySuccessEvent.getFromUserName();
		updateUserLastSendMessageDatetime(fromUserName);

		logger.debug("资质认证成功通知事件：" + inVerifySuccessEvent.getFromUserName());
		try{
			OutTextMsg outMsg = new OutTextMsg(inVerifySuccessEvent);

			outMsg.setContent("资质认证成功通知事件：" + inVerifySuccessEvent.getExpiredTime());
			render(outMsg);
		}catch(Exception e){

			log.error(e.toString());
		}
	}

	@Override
	protected void processInVerifyFailEvent(InVerifyFailEvent inVerifyFailEvent) {
		String fromUserName=inVerifyFailEvent.getFromUserName();
		updateUserLastSendMessageDatetime(fromUserName);

		logger.debug("资质认证失败通知事件：" + inVerifyFailEvent.getFromUserName());
		try{
			OutTextMsg outMsg = new OutTextMsg(inVerifyFailEvent);

			outMsg.setContent("资质认证失败通知事件：" + inVerifyFailEvent.getFailReason());
			render(outMsg);
		}catch(Exception e){

			log.error(e.toString());
		}
	}




	private static void userOperationDetails(String openid, String textcontent, String messagetype) {


		//		ApiResult apiResult=UserApi.getUserInfo(openid);
		//		JSONObject jsonObject=JSON.parseObject(apiResult.getJson());
		//
		//		String nickname=jsonObject.getString("nickname");

		try{
			new UserCmd().set("openid", openid).set("textcontent", textcontent).set("messagetype", messagetype).save();
		}catch(Exception e){

			log.error(e.toString());
		}
	}


	private static void updateUserLastSendMessageDatetime(String openid) {

		List<User> users=User.dao.find("select * from user where openid='"+openid+"'");
		try{
			ApiConfig ac = new ApiConfig();
			// 配置微信 API 相关常量
			ac.setAppId(PropKit.get("appId"));
			ac.setAppSecret(PropKit.get("appSecret"));
			ApiConfigKit.setThreadLocalApiConfig(ac);

			ApiResult apiResult=UserApi.getUserInfo(openid);
			JSONObject jsonObject=JSON.parseObject(apiResult.getJson());

			String nickname=jsonObject.getString("nickname");
			System.out.println("************"+nickname);
			int subscribe=jsonObject.getInteger("subscribe");
			if(users.size()==0){

				new User().set("openid", openid).set("nickname", nickname).save();
			}else{
				User user=users.get(0);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date now = new Date();
				String dt = sdf.format(now);
				if(subscribe==1){


					user.set("timestamp", dt).set("nickname", nickname).set("enabled", 1);
					user.update();

					Member member=Member.dao.find("select * from member where openid='"+openid+"'").get(0);
					member.setNickname(nickname);
					member.update();
				}else{
					user.set("timestamp", dt).set("enabled", 0);
					user.update();

				}

			}
		}catch(Exception e){
			log.error(e.toString());
		}


	}


	public static String getRandomWishWord(){

		List<XinedataMessage> xinedataMessages=XinedataMessage.dao.find("select * from xinedata_message where state=1 and style LIKE '祝福语%'");
		if(xinedataMessages.size()>0){
			Random random = new Random();
			int a=random.nextInt(xinedataMessages.size());
			return "\n\n"+xinedataMessages.get(a).getContent();
		}else
			return "";
	}




}
