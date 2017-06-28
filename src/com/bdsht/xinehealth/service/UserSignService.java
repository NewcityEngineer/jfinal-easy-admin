package com.bdsht.xinehealth.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.bdsht.xinehealth.model.Member;
import com.bdsht.xinehealth.model.UserChecksign;
import com.bdsht.xinehealth.model.UserSign;
import com.jfinal.weixin.sdk.msg.in.InTextMsg;

public class UserSignService {
	public String sign(InTextMsg inTextMsg) throws Exception {

		//String appUrl="http://wx.xinewang.com/application/home";
		String appUrl="https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx107b4f536b4c47db&redirect_uri=http://wx.xinewang.com/weapp/&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";
		String memberurl="https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx107b4f536b4c47db&redirect_uri=http://wx.xinewang.com/member/membercenter&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";

		String respContent = "签到功能暂时不可用";
		String fromUserName = inTextMsg.getFromUserName();

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String currentTime = df.format(new Date());
		int totalCredits = 0;

		List<Member> memberList=Member.dao.find("select * from member where openid='"+fromUserName+"'");


		if (memberList.size() ==0) {
			respContent = "您还没有成为会员，请点击菜单【心e家园】中的【我的会员卡】，自动成为会员后再签到得积分。";

			return respContent;
		}

		List<UserChecksign> userChecksignList=UserChecksign.dao.find("select * from user_checksign where openid='"+fromUserName+"'");

		int count = 0;
		String oldday = "";
		if(userChecksignList.size()>0){
			// 取得用户最后一次登录时间

			oldday = userChecksignList.get(0).getTime()+"";
			// 取得用户连续签到次数
			count = userChecksignList.get(0).getCount();
		}

		//		int flag = 0;
		//		int count = 0;
		//		


		// 如果用户没有签到过，则要去会员表中查看是否是会员，如果不是则不能签到。
		if (userChecksignList.size() ==0) {

			// 增加用户签到统计信息
			new UserChecksign().set("openid", fromUserName).set("count", 1).set("time", currentTime).save();

			// 增加用户签到信息
			new UserSign().set("openid", fromUserName).set("createtime", currentTime).set("credits", 1).save();


			// 更新会员卡积分
			//new Member().findById(fromUserName).set("credits", memberList.get(0).getCredits()+1).update();
			List<Member> newMemberList=Member.dao.find("select * from member where openid='"+fromUserName+"'");

			Member member=newMemberList.get(0);
			member.set("credits", member.getCredits()+1);
			member.update();
			// 取得会员卡的最新积分


			String credits = "199";
			if(newMemberList.size()>0){
				credits=member.getCredits()+"";
			}

			//get game credit

			String gamecredit="0";
//			List<XinecardgameCredits> xinecardgameCreditsList=XinecardgameCredits.dao.find("select * from xinecardgame_credits where openid='"+fromUserName+"'");
//			if(xinecardgameCreditsList.size()>0){
//
//				gamecredit=xinecardgameCreditsList.get(0).getCredit()+"";
//			}


			respContent = "感谢您的签到，我们会为您奉献正能量卡片！"
					+"\n"+"本次签到获得1积分。"
					+"\n"+"您现在的心e积分是：" + credits+ "分。"
					+"\n"+"连续7天签到积分加倍。"
					+"\n"+"输入“帮助”或“help”可以使用心e平台其他功能！"
					+"\n"
					+"\n"+"点击\"<a href='"+memberurl+"'>个人中心</a>\"可进入个人中心！"
					+"\n"+"点击\"<a href='"+appUrl+"'>心e微应用</a>\"可进入微应用系统！";

					return respContent;
		}
		// 如果签到信息存在
		else {
			// 查询现在日期和签到最后日期相差几天
			Date today = df.parse(currentTime);
			Date yesterday = df.parse(oldday);

			int day = daysOfTwo(yesterday, today);

			// 今天已经签到
			if (day < 1) {

				List<Member> newMemberList=Member.dao.find("select * from member where openid='"+fromUserName+"'");
				totalCredits=newMemberList.get(0).getCredits();

				//get game credit

				String gamecredit="0";
//				List<XinecardgameCredits> xinecardgameCreditsList=XinecardgameCredits.dao.find("select * from xinecardgame_credits where openid='"+fromUserName+"'");
//				if(xinecardgameCreditsList.size()>0){
//
//					gamecredit=xinecardgameCreditsList.get(0).getCredit()+"";
//				}



				respContent = "您今天已经签到过了，我们会为您奉献正能量卡片！"
						+"\n"+"您现在的心e积分是：" + totalCredits+ "分。"
						//+"\n"+"您的游戏积分是："+gamecredit+"分。"
						//+"\n"+"在每日的心e卡片和视频中玩心e速记游戏，可以获得游戏积分。再次感谢您关注心e。"
						+"\n"+"输入“帮助”或“help”可以使用心e平台其他功能！"
						+"\n"
						+"\n"+"点击\"<a href='"+memberurl+"'>个人中心</a>\"可进入个人中心！"
						+"\n"+"点击\"<a href='"+appUrl+"'>心e微应用</a>\"可进入微应用系统！";


				return respContent;
			}
			// 连续签到
			else if (day == 1) {
				// 已经连续7天签到
				if (count == 7) {
					count = 0;
				}

				count = count + 1;
			}
			// 没有连续签到
			else {
				count = 1;
			}

			// 更新签到统计表
			List<UserChecksign> userChecksigns=UserChecksign.dao.find("select * from user_checksign where openid='"+fromUserName+"'");
			UserChecksign userChecksign=userChecksigns.get(0);
			userChecksign.set("count", count).set("time", currentTime).update();

			// 增加用户签到信息
			new UserSign().set("openid",fromUserName).set("createtime", currentTime).set("credits", count).save();


			List<Member> members=Member.dao.find("select * from member where openid='"+fromUserName+"'");
			Member member=members.get(0);
			member.set("credits", member.getCredits() + count).update();

			// 输出信息
			//List<Member> newMemberList=Member.dao.find("select * from member where openid='"+fromUserName+"'");
			//Member newsMember=Member.dao.findById(fromUserName);

			//			String credits = "199";
			//			if(newMemberList.size()>0){
			//				credits=newMemberList.get(0).getCredits()+"";
			//			}

			//credits=member.getCredits()+"";

			//get game credit

			String gamecredit="0";
//			List<XinecardgameCredits> xinecardgameCreditsList=XinecardgameCredits.dao.find("select * from xinecardgame_credits where openid='"+fromUserName+"'");
//			if(xinecardgameCreditsList.size()>0){
//
//				gamecredit=xinecardgameCreditsList.get(0).getCredit()+"";
//			}

			respContent = "感谢您的签到，我们会为您奉献正能量卡片！"
					+"\n"+"本次签到获得" + count+ "积分。"
					+"\n"+"您现在的心e积分是：" + member.getCredits() + "分。"
					+"\n"+ "连续7天签到积分加倍。"
					//+"\n"+"您的游戏积分是："+gamecredit+"分。"
					//+"\n"+"在每日的心e卡片和视频中玩心e速记游戏，可以获得游戏积分。"
					+"\n"+"输入“帮助”或“help”可以使用心e平台其他功能！"
					+"\n"
					+"\n"+"点击\"<a href='"+memberurl+"'>个人中心</a>\"可进入个人中心！"
					+"\n"+"点击\"<a href='"+appUrl+"'>心e微应用</a>\"可进入微应用系统！";

		}



		return respContent;
	}

	public static int daysOfTwo(Date fDate, Date oDate) {

		Calendar aCalendar = Calendar.getInstance();

		aCalendar.setTime(fDate);

		int day1 = aCalendar.get(Calendar.DAY_OF_YEAR);

		aCalendar.setTime(oDate);

		int day2 = aCalendar.get(Calendar.DAY_OF_YEAR);

		return day2 - day1;

	}
}
