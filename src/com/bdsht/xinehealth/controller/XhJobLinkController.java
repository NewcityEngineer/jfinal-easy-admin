package com.bdsht.xinehealth.controller;

import com.bdsht.xinehealth.bean.EasyuiPageBean;
import com.bdsht.xinehealth.bean.ResultMsg;
import com.bdsht.xinehealth.interceptor.AdminUserInterceptor;
import com.bdsht.xinehealth.interceptor.UserOperation;
import com.bdsht.xinehealth.model.XhJobLink;
import com.bdsht.xinehealth.util.CommonUtil;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Page;

public class XhJobLinkController extends Controller{


	public void listAll(){
		renderJson(XhJobLink.dao.findAll());
	}



	public void all() {
		//
		String page=getPara("page");
		String rows=getPara("rows");
		Page<XhJobLink> healthFoodStyles;
		if(page==null||rows==null){
			healthFoodStyles = XhJobLink.dao.paginate(1, 10);
			//renderJson(healthFoodTitles);
		}else{
			healthFoodStyles = XhJobLink.dao.paginate(Integer.parseInt(page), Integer.parseInt(rows));

		}

		EasyuiPageBean<XhJobLink> easyuiPageBean=new EasyuiPageBean<XhJobLink>();
		easyuiPageBean.setTotal(XhJobLink.dao.findAll().size());
		easyuiPageBean.setRows(healthFoodStyles.getList());
		renderJson(easyuiPageBean);
	}
	@Before(AdminUserInterceptor.class)
	@UserOperation(optName="增加网络爬虫链接操作",optStyle="增加")
	public void add() {
		ResultMsg<XhJobLink> result=new ResultMsg<XhJobLink>();
		String name=getPara("name");
		String link=getPara("link");
		String description=getPara("description");
		String freq=getPara("freq");
		String style=getPara("style");
		if(!StrKit.isBlank(name)&&!StrKit.isBlank(link)&&!StrKit.isBlank(description)&&!StrKit.isBlank(freq)&&!StrKit.isBlank(style)){
			if (XhJobLink.dao.findByName(name)==null) {
				XhJobLink xhJobLink=new XhJobLink();
				xhJobLink.setName(name);
				xhJobLink.setStyle(style);
				xhJobLink.setLink(link);
				xhJobLink.setDescription(description);
				xhJobLink.setFreq(freq);
				xhJobLink.setOldTime(CommonUtil.getCurrentTime("yyyy-MM-dd HH:mm:ss"));
				xhJobLink.setNewTime(CommonUtil.getCurrentTime("yyyy-MM-dd HH:mm:ss"));
				xhJobLink.setTimestamp(CommonUtil.getCurrentTime("yyyyMMddHHmmss"));
				xhJobLink.save();
				result.setResult("success");
				result.setDescription("添加成功！");
				result.setContent(xhJobLink);
			}else{
				result.setResult("failure");
				result.setDescription("该爬虫已提交！");
			}
		}else{
			
			StringBuffer sb=new StringBuffer();
			if(StrKit.isBlank(name)){
				sb.append("网站名称不能为空！");
			}else if(StrKit.isBlank(link)){
				sb.append("网站链接不能为空！");
			}else if(StrKit.isBlank(description)){
				sb.append("网站简介不能为空！");
			}else if(StrKit.isBlank(freq)){
				sb.append("爬取频率不能为空！");
			}else if(StrKit.isBlank(style)){
				sb.append("爬取类别不能为空！");
			}
			
			result.setResult("failure");
			result.setDescription(sb.toString());
		}
		renderJson(result);
	}
	@Before(AdminUserInterceptor.class)
	@UserOperation(optName="修改网络爬虫链接操作",optStyle="更新")
	public void update() {
		ResultMsg<XhJobLink> result=new ResultMsg<XhJobLink>();
		int id = getParaToInt("id");
		String timestamp = getPara("timestamp");
		XhJobLink xhJobLink = XhJobLink.dao.findByIdAndTimestamp(id, timestamp);
		if (xhJobLink != null) {
			String name=getPara("name");
			String link=getPara("link");
			String description=getPara("description");
			String freq=getPara("freq");
			String style=getPara("style");

			xhJobLink.setName(name);
			xhJobLink.setStyle(style);
			xhJobLink.setLink(link);
			xhJobLink.setDescription(description);
			xhJobLink.setFreq(freq);
			xhJobLink.setNewTime(CommonUtil.getCurrentTime("yyyy-MM-dd HH:mm:ss"));
			xhJobLink.setTimestamp(CommonUtil.getCurrentTime("yyyyMMddHHmmss"));

			xhJobLink.update();
			result.setContent(xhJobLink);
			result.setResult("success");
			result.setDescription("修改成功！");
		}else{
			result.setResult("failure");
			result.setDescription("已被修改！");
		}

		renderJson(result);
	}
	@Before(AdminUserInterceptor.class)
	@UserOperation(optName="删除网络爬虫链接操作",optStyle="删除")
	public void delete() {
		ResultMsg<XhJobLink> result=new ResultMsg<XhJobLink>();
		try{
			int id = getParaToInt("id");
			System.out.println(id);
			XhJobLink xhJobLink =XhJobLink.dao.findById(id);
			xhJobLink.delete();
			result.setResult("success");
			result.setDescription("删除成功！");
			result.setContent(xhJobLink);
		}catch(Exception e){
			result.setResult("failure");
			result.setDescription(e.toString());
		}


		renderJson(result);
	}





}
