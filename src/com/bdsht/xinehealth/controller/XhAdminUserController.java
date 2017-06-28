package com.bdsht.xinehealth.controller;


import com.bdsht.xinehealth.bean.EasyuiPageBean;
import com.bdsht.xinehealth.bean.ResultMsg;
import com.bdsht.xinehealth.interceptor.AdminUserInterceptor;
import com.bdsht.xinehealth.interceptor.UserOperation;
import com.bdsht.xinehealth.model.XhAdminUser;
import com.bdsht.xinehealth.util.CommonUtil;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Page;

public class XhAdminUserController extends Controller {
	
	
	public void all() {
		//
		String page=getPara("page");
		String rows=getPara("rows");
		Page<XhAdminUser> xhAdminUsers;
		if(page==null||rows==null){
			xhAdminUsers = XhAdminUser.dao.paginate(1, 10);
			//renderJson(healthFoodTitles);
		}else{
			xhAdminUsers = XhAdminUser.dao.paginate(Integer.parseInt(page), Integer.parseInt(rows));

		}

		EasyuiPageBean<XhAdminUser> easyuiPageBean=new EasyuiPageBean<XhAdminUser>();
		easyuiPageBean.setTotal(XhAdminUser.dao.findAll().size());
		easyuiPageBean.setRows(xhAdminUsers.getList());
		renderJson(easyuiPageBean);
	}
	@Before(AdminUserInterceptor.class)
	@UserOperation(optName="配置监管员操作",optStyle="增加")
	public void add() {
		ResultMsg<XhAdminUser> result=new ResultMsg<XhAdminUser>();
		String name=getPara("name");
		String userId=getPara("userId");
		String userPwd=getPara("userPwd");
		if(!StrKit.isBlank(name)&&!StrKit.isBlank(userId)&&!StrKit.isBlank(userPwd)){
			if (XhAdminUser.dao.findByName(name)==null) {
				XhAdminUser xhAdminUser=new XhAdminUser();
				xhAdminUser.setName(name);
				xhAdminUser.setUserId(userId);
				xhAdminUser.setUserPwd(userPwd);
				
				xhAdminUser.setCreateTime(CommonUtil.getCurrentTime("yyyy-MM-dd HH:mm:ss"));
				xhAdminUser.setUpdateTime(CommonUtil.getCurrentTime("yyyy-MM-dd HH:mm:ss"));
				xhAdminUser.setTimestamp(CommonUtil.getCurrentTime("yyyyMMddHHmmss"));
				xhAdminUser.save();
				result.setResult("success");
				result.setDescription("添加成功！");
				result.setContent(xhAdminUser);
			}else{
				result.setResult("failure");
				result.setDescription("该管理员信息已提交！");
			}
		}else{
			
			StringBuffer sb=new StringBuffer();
			if(StrKit.isBlank(name)){
				sb.append("用户名不能为空！");
			}else if(StrKit.isBlank(userId)){
				sb.append("用户登录名不能为空！");
			}else if(StrKit.isBlank(userPwd)){
				sb.append("用户密码不能为空！");
			}
			
			result.setResult("failure");
			result.setDescription(sb.toString());
		}
		renderJson(result);
	}
	
	@Before(AdminUserInterceptor.class)
	@UserOperation(optName="更改监管员操作",optStyle="更新")
	public void update() {
		ResultMsg<XhAdminUser> result=new ResultMsg<XhAdminUser>();
		int id = getParaToInt("id");
		String timestamp = getPara("timestamp");
		XhAdminUser xhAdminUser = XhAdminUser.dao.findByIdAndTimestamp(id, timestamp);
		if (xhAdminUser != null) {
			String name=getPara("name");
			String userId=getPara("userId");
			String userPwd=getPara("userPwd");

			xhAdminUser.setName(name);
			xhAdminUser.setUserId(userId);
			xhAdminUser.setUserPwd(userPwd);
			
			xhAdminUser.setUpdateTime(CommonUtil.getCurrentTime("yyyy-MM-dd HH:mm:ss"));
			xhAdminUser.setTimestamp(CommonUtil.getCurrentTime("yyyyMMddHHmmss"));

			xhAdminUser.update();
			result.setContent(xhAdminUser);
			result.setResult("success");
			result.setDescription("修改成功！");
		}else{
			result.setResult("failure");
			result.setDescription("已被修改！");
		}

		renderJson(result);
	}
	@Before(AdminUserInterceptor.class)
	@UserOperation(optName="删除监管员操作",optStyle="删除")
	public void delete() {
		ResultMsg<XhAdminUser> result=new ResultMsg<XhAdminUser>();
		try{
			int id = getParaToInt("id");
			System.out.println(id);
			XhAdminUser xhAdminUser =XhAdminUser.dao.findById(id);
			xhAdminUser.delete();
			result.setResult("success");
			result.setDescription("删除成功！");
			result.setContent(xhAdminUser);
		}catch(Exception e){
			result.setResult("failure");
			result.setDescription(e.toString());
		}


		renderJson(result);
	}
}
