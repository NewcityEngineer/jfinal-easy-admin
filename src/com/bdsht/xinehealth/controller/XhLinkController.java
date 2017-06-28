package com.bdsht.xinehealth.controller;

import com.bdsht.xinehealth.bean.EasyuiPageBean;
import com.bdsht.xinehealth.bean.ResultMsg;
import com.bdsht.xinehealth.interceptor.AdminUserInterceptor;
import com.bdsht.xinehealth.interceptor.UserOperation;
import com.bdsht.xinehealth.model.XhLink;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Page;

public class XhLinkController extends Controller{


	public void listAll(){
		renderJson(XhLink.dao.findAll());
	}



	public void all() {
		//
		String page=getPara("page");
		String rows=getPara("rows");
		Page<XhLink> healthFoodStyles;
		if(page==null||rows==null){
			healthFoodStyles = XhLink.dao.paginate(1, 10);
			//renderJson(healthFoodTitles);
		}else{
			healthFoodStyles = XhLink.dao.paginate(Integer.parseInt(page), Integer.parseInt(rows));

		}

		EasyuiPageBean<XhLink> easyuiPageBean=new EasyuiPageBean<XhLink>();
		easyuiPageBean.setTotal(XhLink.dao.findAll().size());
		easyuiPageBean.setRows(healthFoodStyles.getList());
		renderJson(easyuiPageBean);
	}
	@Before(AdminUserInterceptor.class)
	@UserOperation(optName="增加常用链接操作",optStyle="增加")
	public void add() {
		ResultMsg<XhLink> result=new ResultMsg<XhLink>();
		String style=getPara("style");
		String link=getPara("link");
		String description=getPara("description");
		String user=getPara("user");
		if(!StrKit.isBlank(style)&&!StrKit.isBlank(link)&&!StrKit.isBlank(description)&&!StrKit.isBlank(user)){
			if (XhLink.dao.findByLink(link)==null) {
				XhLink xhLink=new XhLink();
				xhLink.setStyle(style);
				xhLink.setLink(link);
				xhLink.setUser(user);
				xhLink.setDescription(description);
				xhLink.save();
				result.setResult("success");
				result.setDescription("添加成功！");
				result.setContent(xhLink);
			}else{
				result.setResult("failure");
				result.setDescription("该常用链接已提交！");
			}
		}else{
			
			StringBuffer sb=new StringBuffer();
			if(StrKit.isBlank(style)){
				sb.append("链接分类不能为空！");
			}else if(StrKit.isBlank(link)){
				sb.append("链接不能为空！");
			}else if(StrKit.isBlank(description)){
				sb.append("链接简介不能为空！");
			}else if(StrKit.isBlank(user)){
				sb.append("分享人不能为空！");
			}
			
			result.setResult("failure");
			result.setDescription(sb.toString());
		}
		renderJson(result);
	}
	@Before(AdminUserInterceptor.class)
	@UserOperation(optName="修改常用链接操作",optStyle="更新")
	public void update() {
		ResultMsg<XhLink> result=new ResultMsg<XhLink>();
		int id = getParaToInt("id");
		XhLink xhLink = XhLink.dao.findById(id);
		if (xhLink != null) {
			String style=getPara("style");
			String link=getPara("link");
			String description=getPara("description");
			String user=getPara("user");

			xhLink.setStyle(style);
			xhLink.setLink(link);
			xhLink.setUser(user);
			xhLink.setDescription(description);

			xhLink.update();
			result.setContent(xhLink);
			result.setResult("success");
			result.setDescription("修改成功！");
		}else{
			result.setResult("failure");
			result.setDescription("已被修改！");
		}

		renderJson(result);
	}
	@Before(AdminUserInterceptor.class)
	@UserOperation(optName="删除常用链接操作",optStyle="删除")
	public void delete() {
		ResultMsg<XhLink> result=new ResultMsg<XhLink>();
		try{
			int id = getParaToInt("id");
			System.out.println(id);
			XhLink xhLink =XhLink.dao.findById(id);
			xhLink.delete();
			result.setResult("success");
			result.setDescription("删除成功！");
			result.setContent(xhLink);
		}catch(Exception e){
			result.setResult("failure");
			result.setDescription(e.toString());
		}


		renderJson(result);
	}





}
