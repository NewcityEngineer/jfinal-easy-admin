package com.bdsht.xinehealth.controller;

import java.util.List;

import com.bdsht.xinehealth.bean.EasyuiPageBean;
import com.bdsht.xinehealth.model.XhAdminOptlog;
import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Page;

public class XhAdminUserOptlogController extends Controller {
	public void all() {
		//
		String page=getPara("page");
		String rows=getPara("rows");
		Page<XhAdminOptlog> xhAdminOptlogs;
		if(page==null||rows==null){
			xhAdminOptlogs = XhAdminOptlog.dao.paginate(1, 10);
			//renderJson(healthFoodTitles);
		}else{
			xhAdminOptlogs = XhAdminOptlog.dao.paginate(Integer.parseInt(page), Integer.parseInt(rows));

		}

		EasyuiPageBean<XhAdminOptlog> easyuiPageBean=new EasyuiPageBean<XhAdminOptlog>();
		easyuiPageBean.setTotal(XhAdminOptlog.dao.findAll().size());
		easyuiPageBean.setRows(xhAdminOptlogs.getList());
		renderJson(easyuiPageBean);
	}

	public void search(){

		String userName=getPara("userName");
		if(StrKit.isBlank(userName)){
			all();
		}else{
			List<XhAdminOptlog> XinedataUseropes=null;
			if(!StrKit.isBlank(userName)){
				XinedataUseropes=XhAdminOptlog.dao.findByUserName(userName);
			}

			EasyuiPageBean<XhAdminOptlog> easyuiPageBean = new EasyuiPageBean<XhAdminOptlog>();
			easyuiPageBean.setTotal(XhAdminOptlog.dao.findAll().size());
			easyuiPageBean.setRows(XinedataUseropes);
			renderJson(easyuiPageBean);
		}
	}
}
