package com.bdsht.xinehealth.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.bdsht.xinehealth.bean.EasyuiPageBean;
import com.bdsht.xinehealth.bean.ResultMsg;
import com.bdsht.xinehealth.interceptor.AdminUserInterceptor;
import com.bdsht.xinehealth.interceptor.UserOperation;
import com.bdsht.xinehealth.model.XhJobFoodNews;
import com.bdsht.xinehealth.util.StaticURL;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Page;

public class XhJobFoodNewsController extends Controller{

	String recipe_path=StaticURL.FOOD_RECIPE;

	public void listAll(){
		renderJson(XhJobFoodNews.dao.findAll());
	}



	public void all() {
		//
		String page=getPara("page");
		String rows=getPara("rows");
		Page<XhJobFoodNews> healthFoodStyles;
		if(page==null||rows==null){
			healthFoodStyles = XhJobFoodNews.dao.paginate(1, 10);
			//renderJson(healthFoodTitles);
		}else{
			healthFoodStyles = XhJobFoodNews.dao.paginate(Integer.parseInt(page), Integer.parseInt(rows));

		}

		EasyuiPageBean<XhJobFoodNews> easyuiPageBean=new EasyuiPageBean<XhJobFoodNews>();
		easyuiPageBean.setTotal(XhJobFoodNews.dao.findAll().size());
		easyuiPageBean.setRows(healthFoodStyles.getList());
		renderJson(easyuiPageBean);
	}
	@Before(AdminUserInterceptor.class)
	@UserOperation(optName="增加食品曝光信息操作",optStyle="增加")
	public void add() {

		ResultMsg<XhJobFoodNews> result=new ResultMsg<XhJobFoodNews>();
		try{
			String title = getPara("title");
			String publishTime = getPara("publishTime");
			String content = getPara("content");
			String url = getPara("url");
			String source = getPara("source");
			String category = getPara("category");
			String imgurl = getPara("imgurl");

			if(!StrKit.isBlank(title)&&!StrKit.isBlank(publishTime)&&!StrKit.isBlank(content)
					&&!StrKit.isBlank(source)&&!StrKit.isBlank(category)&&!StrKit.isBlank(imgurl)){
				if (XhJobFoodNews.dao.findByTitle(title) == null) {
					XhJobFoodNews xhJobFoodNews = new XhJobFoodNews();
					xhJobFoodNews.setTitle(title);
					xhJobFoodNews.setPublishTime(publishTime);
					xhJobFoodNews.setContent(content);
					xhJobFoodNews.setUrl(url);
					xhJobFoodNews.setSource(source);
					xhJobFoodNews.setCategory(category);
					xhJobFoodNews.setImgurl(imgurl);
					xhJobFoodNews.save();
					result.setResult("success");
					result.setDescription("增加成功！");	
					result.setContent(xhJobFoodNews);
				}else{
					result.setResult("failure");
					result.setDescription("该文章已经存在！");
				}
			}else{
				StringBuffer sb=new StringBuffer();
				if(StrKit.isBlank(title)){
					sb.append("文章标题不能为空！");
				}else if(StrKit.isBlank(publishTime)){
					sb.append("发布时间不能为空！");
				}else if(StrKit.isBlank(content)){
					sb.append("文章内容不能为空！");
				}else if(StrKit.isBlank(source)){
					sb.append("文章来源不能为空！");
				}else if(StrKit.isBlank(category)){
					sb.append("文章分类不能为空！");
				}else if(StrKit.isBlank(imgurl)){
					sb.append("图片不能为空！");
				}
				result.setResult("failure");
				result.setDescription(sb.toString());
			}
		}catch(Exception e){
			result.setResult("failure");
			result.setDescription(e.getMessage());

		}
		renderJson(result);
	}
	@Before(AdminUserInterceptor.class)
	@UserOperation(optName="修改食品曝光信息操作",optStyle="更新")
	public void update() {
		int id = getParaToInt("id");
		ResultMsg<XhJobFoodNews> result=new ResultMsg<XhJobFoodNews>();
		XhJobFoodNews xhJobFoodNews=XhJobFoodNews.dao.findById(id);
		if (xhJobFoodNews != null) {

			String title = getPara("title");
			String publishTime = getPara("publishTime");
			String content = getPara("content");
			String url = getPara("url");
			String source = getPara("source");
			String category = getPara("category");
			String imgurl = getPara("imgurl");

			xhJobFoodNews.setTitle(title);
			xhJobFoodNews.setPublishTime(publishTime);
			xhJobFoodNews.setContent(content);
			xhJobFoodNews.setUrl(url);
			xhJobFoodNews.setSource(source);
			xhJobFoodNews.setCategory(category);
			xhJobFoodNews.setImgurl(imgurl);
			xhJobFoodNews.update();
			//XhRecipe.setImg(fileName);
			result.setResult("success");
			result.setContent(xhJobFoodNews);
			result.setDescription("更改成功！");
		}

		renderJson(result);
	}
	@Before(AdminUserInterceptor.class)
	@UserOperation(optName="删除食品曝光信息操作",optStyle="删除")
	public void delete() {
		ResultMsg<XhJobFoodNews> result=new ResultMsg<XhJobFoodNews>();
		try{
			int id = getParaToInt("id");
			System.out.println(id);
			XhJobFoodNews xhJobFoodNews = XhJobFoodNews.dao.findById(id);
			xhJobFoodNews.delete();
			result.setResult("success");	
			result.setDescription("删除成功！");
			result.setContent(xhJobFoodNews);
		}catch(Exception e){
			result.setResult("failure");
			result.setDescription(e.toString());
		}
		renderJson(result);
	}

	public String getCurrentTime(String format) {

		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date now = new Date();
		String dt = sdf.format(now);

		return dt;
	}


}
