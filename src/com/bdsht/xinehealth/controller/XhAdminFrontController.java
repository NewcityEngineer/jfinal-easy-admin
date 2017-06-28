package com.bdsht.xinehealth.controller;

import com.bdsht.xinehealth.bean.ResultMsg;
import com.bdsht.xinehealth.model.XhAdminUser;
import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;

public class XhAdminFrontController extends Controller {


	public void index(){
		//render("index.html");

		String name =(String) getSession().getAttribute("name");

		if (name == null || name.length() == 0) {
			render("xhLogin.html");
		}else{
			render("index.html");
		}
	}
	
	
	public void login(){
		String userId=getPara("userId");
		String userPwd=getPara("userPwd");
		ResultMsg<XhAdminUser> resultMsg=new ResultMsg<XhAdminUser>();

		if(StrKit.isBlank(userId)){
			resultMsg.setResult("failure");
			resultMsg.setDescription("登录名未输入！");
		}else if(StrKit.isBlank(userPwd)){
			resultMsg.setResult("failure");
			resultMsg.setDescription("密码未输入！");
		}else{

			if(XhAdminUser.dao.findByUserId(userId)==null){
				resultMsg.setResult("failure");
				resultMsg.setDescription("没有该用户！");
			}else{
				XhAdminUser xhAdminUser=XhAdminUser.dao.findByUserIdAndUserPwd(userId,userPwd);
				if(xhAdminUser==null){
					resultMsg.setResult("failure");
					resultMsg.setDescription("登录名或密码错误！");
				}else{
					resultMsg.setResult("success");
					resultMsg.setDescription("登录成功！");
					resultMsg.setContent(xhAdminUser);
					
					setSessionAttr("name", xhAdminUser.getName());
				}
			}

		}

		renderJson(resultMsg);

	}

	
	public void logout(){
		String name =(String) getSession().getAttribute("name");

		if (name != null && name.length() > 0) {

			getSession().removeAttribute("name");
		}

		render("xhLogin.html");
	}


	public void oneLevel_Category(){

		render("oneLevel_Category.html");

	}

	public void twoLevel_Category(){

		render("twoLevel_Category.html");

	}

	public void xhRecipe(){
		render("xhRecipe.html");
	}
	public void xhIngredients(){
		render("xhIngredients.html");
	}

	public void xhNutritional(){
		render("xhNutritional.html");
	}
	public void xhClassicRecipe(){
		render("xhClassicRecipes.html");
	}

	public void xhFood(){
		render("xhFood.html");
	}

	public void xhSymptom(){
		render("xhSymptom.html");
	}

	public void xhJobFoodNews(){
		render("xhJobFoodNews.html");
	}

	public void xhJobLink(){
		render("xhJobLink.html");
	}
	public void xhLink(){
		render("xhLink.html");
	}

	public void xhAdminUser(){

		render("xhAdminUser.html");
	}
	
	public void xhAdminUserOptlog(){
		render("xhAdminUserOptlog.html");
	}

}
