package com.bdsht.xinehealth.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bdsht.xinehealth.bean.EasyuiPageBean;
import com.bdsht.xinehealth.bean.JsonTreeData;
import com.bdsht.xinehealth.bean.ResultMsg;
import com.bdsht.xinehealth.interceptor.AdminUserInterceptor;
import com.bdsht.xinehealth.interceptor.UserOperation;
import com.bdsht.xinehealth.model.FoodInfo;
import com.bdsht.xinehealth.model.XhRecipe;
import com.bdsht.xinehealth.model.XinedataSymptoms;
import com.bdsht.xinehealth.util.StaticURL;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Page;

public class XhSymptomController extends Controller{

	String recipe_path=StaticURL.FOOD_RECIPE;

	public void listAll(){
		renderJson(FoodInfo.dao.findAll());
	}

	/*public void listByStyleId(){


		int styleId=getParaToInt("styleId");

		List<XhRecipe> xhRecipes=XhRecipe.dao.findByStyleId(styleId);

		renderJson(xhRecipes);

	}*/



	public void all() {
		//
		String page=getPara("page");
		String rows=getPara("rows");
		Page<XinedataSymptoms> healthFoodStyles;
		if(page==null||rows==null){
			healthFoodStyles = XinedataSymptoms.dao.paginate(1, 10);
			//renderJson(healthFoodTitles);
		}else{
			healthFoodStyles = XinedataSymptoms.dao.paginate(Integer.parseInt(page), Integer.parseInt(rows));

		}

		EasyuiPageBean<XinedataSymptoms> easyuiPageBean=new EasyuiPageBean<XinedataSymptoms>();
		easyuiPageBean.setTotal(XinedataSymptoms.dao.findAll().size());
		easyuiPageBean.setRows(healthFoodStyles.getList());
		renderJson(easyuiPageBean);
	}
	@Before(AdminUserInterceptor.class)
	@UserOperation(optName="增加症状信息操作",optStyle="增加")
	public void add() {

		ResultMsg<XinedataSymptoms> result=new ResultMsg<XinedataSymptoms>();
		try{
			//			UploadFile uploadFile = this.getFile("img","food_recipe");
			String name= getPara("name");
			String level = getPara("level");
			String introduction = getPara("introduction");
			String style = getPara("style");//
			String perform = getPara("perform");
			String reason = getPara("reason");
			String life = getPara("life");
			String diet = getPara("diet");
			String better = getPara("better");
			String worse = getPara("worse");
			String foodname = getPara("foodname");
			String material = getPara("material");//
			String seasoning = getPara("seasoning");//
			String method = getPara("method");//
			String effect = getPara("effect");//
			// // 拼接文件上传的完整路径
			if(!StrKit.isBlank(name)&&!StrKit.isBlank(level)&&!StrKit.isBlank(introduction)&&!StrKit.isBlank(perform)
					&&!StrKit.isBlank(reason)&&!StrKit.isBlank(life)&&!StrKit.isBlank(diet)
					&&!StrKit.isBlank(better)&&!StrKit.isBlank(worse)&&!StrKit.isBlank(foodname))
				if (XinedataSymptoms.dao.findByName(name) == null) {
					XinedataSymptoms xinedataSymptoms =new XinedataSymptoms();
					xinedataSymptoms.setName(name);
					xinedataSymptoms.setLevel(level);
					xinedataSymptoms.setIntroduction(introduction);
					xinedataSymptoms.setStyle(style);
					xinedataSymptoms.setPerform(perform);
					xinedataSymptoms.setReason(reason);
					xinedataSymptoms.setLife(life);
					xinedataSymptoms.setDiet(diet);
					xinedataSymptoms.setBetter(better);
					xinedataSymptoms.setWorse(worse);
					xinedataSymptoms.setFoodname(foodname);
					xinedataSymptoms.setMaterial(material);
					xinedataSymptoms.setSeasoning(seasoning);
					xinedataSymptoms.setMethod(method);
					xinedataSymptoms.setEffect(effect);
					xinedataSymptoms.save();
					result.setResult("success");
					result.setDescription("添加成功！");
					result.setContent(xinedataSymptoms);
				}else{
					result.setResult("failure");
					result.setDescription("该食物相生相克已经存在！");
				}
			else{
				
				StringBuffer sb=new StringBuffer();
				if(StrKit.isBlank(name)){
					sb.append("病症名称不能为空！");
				}else if(StrKit.isBlank(level)){
					sb.append("病症类别不能为空！");
				}else if(StrKit.isBlank(introduction)){
					sb.append("病症说明不能为空！");
				}else if(StrKit.isBlank(perform)){
					sb.append("临床表现不能为空！");
				}else if(StrKit.isBlank(reason)){
					sb.append("致病原因不能为空！");
				}else if(StrKit.isBlank(life)){
					sb.append("生活注意不能为空！");
				}else if(StrKit.isBlank(diet)){
					sb.append("饮食注意不能为空！");
				}else if(StrKit.isBlank(better)){
					sb.append("宜食食物及功效不能为空！");
				}else if(StrKit.isBlank(worse)){
					sb.append("慎食食物及原因不能为空！");
				}else if(StrKit.isBlank(foodname)){
					sb.append("推荐食谱名称不能为空！");
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
	@UserOperation(optName="修改症状信息操作",optStyle="更新")
	public void update() {
		int id=getParaToInt("id");
		ResultMsg<XinedataSymptoms> result=new ResultMsg<XinedataSymptoms>();
		String name= getPara("name");
		String level = getPara("level");
		String introduction = getPara("introduction");
		String style = getPara("style");//
		String perform = getPara("perform");
		String reason = getPara("reason");
		String life = getPara("life");
		String diet = getPara("diet");
		String better = getPara("better");
		String worse = getPara("worse");
		String foodname = getPara("foodname");
		String material = getPara("material");//
		String seasoning = getPara("seasoning");//
		String method = getPara("method");//
		String effect = getPara("effect");//
		XinedataSymptoms xinedataSymptoms=XinedataSymptoms.dao.findById(id);
		xinedataSymptoms.setName(name);
		xinedataSymptoms.setLevel(level);
		xinedataSymptoms.setIntroduction(introduction);
		xinedataSymptoms.setStyle(style);
		xinedataSymptoms.setPerform(perform);
		xinedataSymptoms.setReason(reason);
		xinedataSymptoms.setLife(life);
		xinedataSymptoms.setDiet(diet);
		xinedataSymptoms.setBetter(better);
		xinedataSymptoms.setWorse(worse);
		xinedataSymptoms.setFoodname(foodname);
		xinedataSymptoms.setMaterial(material);
		xinedataSymptoms.setSeasoning(seasoning);
		xinedataSymptoms.setMethod(method);
		xinedataSymptoms.setEffect(effect);
		xinedataSymptoms.update();
		result.setResult("success");
		result.setDescription("更改成功!");
		result.setContent(xinedataSymptoms);
		renderJson(result);
	}
	@Before(AdminUserInterceptor.class)
	@UserOperation(optName="删除症状信息操作",optStyle="删除")
	public void delete() {
		ResultMsg<XinedataSymptoms> result=new ResultMsg<XinedataSymptoms>();
		try{
			int id = getParaToInt("id");
			System.out.println(id);
			XinedataSymptoms xinedataSymptoms = XinedataSymptoms.dao.findById(id);
			xinedataSymptoms.delete();
			result.setResult("success");	
			result.setDescription("删除成功！");
			result.setContent(xinedataSymptoms);
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

	public void getJsonTreeData() {
		List<XhRecipe> resultList = XhRecipe.dao.findAll();
		List<JsonTreeData> treeDataList = new ArrayList<JsonTreeData>();

		for (XhRecipe XhRecipe : resultList) {
			JsonTreeData treeData = new JsonTreeData();

			treeData.setId(String.valueOf(XhRecipe.getId()));
			treeData.setText(XhRecipe.getName());
			treeData.setState("closed");


			treeDataList.add(treeData);
		}

		renderJson(treeDataList);
	}

}
