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
import com.bdsht.xinehealth.model.XhRecipe;
import com.bdsht.xinehealth.model.XhStyle;
import com.bdsht.xinehealth.model.XhTitle;
import com.bdsht.xinehealth.util.CommonUtil;
import com.bdsht.xinehealth.util.StaticURL;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.upload.UploadFile;

public class XhRecipeController extends Controller{

	String recipe_path=StaticURL.FOOD_RECIPE;

	public void listAll(){
		renderJson(XhRecipe.dao.findAll());
	}
	
	public void listByStyleId(){
		
		
		int styleId=getParaToInt("styleId");
		
		List<XhRecipe> xhRecipes=XhRecipe.dao.findByStyleId(styleId);
		
		renderJson(xhRecipes);
		
	}
	
	
	
	public void all() {
		//
		String page=getPara("page");
		String rows=getPara("rows");
		Page<XhRecipe> healthFoodStyles;
		if(page==null||rows==null){
			healthFoodStyles = XhRecipe.dao.paginate(1, 10);
			//renderJson(healthFoodTitles);
		}else{
			healthFoodStyles = XhRecipe.dao.paginate(Integer.parseInt(page), Integer.parseInt(rows));

		}

		EasyuiPageBean<XhRecipe> easyuiPageBean=new EasyuiPageBean<XhRecipe>();
		easyuiPageBean.setTotal(XhRecipe.dao.findAll().size());
		easyuiPageBean.setRows(healthFoodStyles.getList());
		renderJson(easyuiPageBean);
	}
	@Before(AdminUserInterceptor.class)
	@UserOperation(optName="增加健康食谱操作",optStyle="增加")
	public void add() {

		ResultMsg<XhRecipe> result=new ResultMsg<XhRecipe>();
		try{
			UploadFile uploadFile = this.getFile("img","food_recipe");
			String food_style_sId= getPara("food_style_sId");
			String name = getPara("name");
			String function = getPara("function");
			String raw = getPara("raw");
			String method = getPara("method");
			String effect = getPara("effect");
			// // 拼接文件上传的完整路径


			result.setResult("failurea");
			StringBuffer sb=new StringBuffer();
			if(StrKit.isBlank(food_style_sId)){
				sb.append("食谱所属二级分类未选择！");
				result.setDescription(sb.toString());
			}else if(StrKit.isBlank(name)){
				sb.append("食谱名称不能为空！");
				result.setDescription(sb.toString());
			}else if(uploadFile==null){
				sb.append("图片未选择！");
				result.setDescription(sb.toString());
			}else if(StrKit.isBlank(function)){
				sb.append("食谱作用不能为空！");
				result.setDescription(sb.toString());
			}else if(StrKit.isBlank(raw)){
				sb.append("食谱材料不能为空！");
				result.setDescription(sb.toString());
			}else if(StrKit.isBlank(method)){
				sb.append("食谱做法不能为空！");
				result.setDescription(sb.toString());
			}else {
				if (XhRecipe.dao.findByName(name) == null) {
					XhRecipe xhRecipe = new XhRecipe();
					int style_id=Integer.parseInt(food_style_sId);

					XhStyle xhStyle=XhStyle.dao.findById(style_id);
					String sId=xhStyle.getSId();
					String tId=XhTitle.dao.findById(xhStyle.getFoodTitleTid()).getTId();
					try{
						CommonUtil.uploadCardToOSS(StaticURL.XINE_HEALTH_BUCKETNAME,"images/"+recipe_path , uploadFile.getFileName(),uploadFile.getUploadPath()+"/");
						String fileName =uploadFile.getFileName();
						xhRecipe.setImg(StaticURL.ALIYUN_OSS_XINE_HEALTH +recipe_path+"/"+fileName);


						xhRecipe.setRecipeId("hr"+tId+sId+CommonUtil.getCurrentTime("yyyyMMdd"));
						xhRecipe.setFoodStyleSid(food_style_sId);
						xhRecipe.setName(name);
						xhRecipe.setFunction(function);
						xhRecipe.setRaw(raw);
						xhRecipe.setMethod(method);
						xhRecipe.setEffect(effect);
						xhRecipe.setCount(0);

						xhRecipe.setOldTime(getCurrentTime("yyyy-MM-dd HH:mm:ss"));
						xhRecipe.setNewTime(getCurrentTime("yyyy-MM-dd HH:mm:ss"));
						xhRecipe.setTimestamp(getCurrentTime("yyyyMMddHHmmss"));
						xhRecipe.save();
						result.setContent(xhRecipe);
						result.setDescription("上传成功！！");
						result.setResult("success");
					}catch(Exception e){
						result.setResult("failure");
						result.setDescription(e.toString());
					}

				}else{
					result.setDescription("该食谱已经存在！");
				}
			}
		}catch(Exception e){
			result.setResult("failure");
			result.setDescription(e.getMessage());

		}
		renderJson(result);
	}
	@Before(AdminUserInterceptor.class)
	@UserOperation(optName="修改健康食谱操作",optStyle="更新")
	public void update() {
		UploadFile uploadFile = this.getFile("img","food_style");
		int id = getParaToInt("id");
		String timestamp = getPara("timestamp");
		ResultMsg<XhRecipe> result=new ResultMsg<XhRecipe>();
		XhRecipe xhRecipe=XhRecipe.dao.findByIdAndTimestamp(id, timestamp);
		if (xhRecipe != null) {

			String recipeId = getPara("recipeId");
			String food_style_sId= getPara("food_style_sId");
			String name = getPara("name");
			String function = getPara("function");
			String raw = getPara("raw");
			String method = getPara("method");
			String effect = getPara("effect");


			xhRecipe.setRecipeId(recipeId);
			xhRecipe.setFoodStyleSid(food_style_sId);
			xhRecipe.setName(name);
			xhRecipe.setFunction(function);
			xhRecipe.setRaw(raw);
			xhRecipe.setMethod(method);
			xhRecipe.setEffect(effect);
			//XhRecipe.setImg(fileName);
			// // 拼接文件上传的完整路径
			try{
				String imgname=xhRecipe.getImg();
				if(!imgname.substring(imgname.lastIndexOf("/"), imgname.length()).equals(uploadFile.getFileName())){
					CommonUtil.uploadCardToOSS(StaticURL.XINE_HEALTH_BUCKETNAME,"images/"+recipe_path , uploadFile.getFileName(),uploadFile.getUploadPath()+"/");
					xhRecipe.setImg(StaticURL.ALIYUN_OSS_XINE_HEALTH +recipe_path+"/"+uploadFile.getFileName());
				}

				xhRecipe.setNewTime(getCurrentTime("yyyy-MM-dd HH:mm:ss"));
				xhRecipe.setTimestamp(getCurrentTime("yyyyMMddHHmmss"));

				xhRecipe.update();
				result.setResult("success");
				result.setContent(xhRecipe);
				result.setDescription("更改成功！");
			}catch(Exception e){
				result.setResult("failure");
				result.setDescription(e.toString());
			}

		}

		renderJson(result);
	}
	@Before(AdminUserInterceptor.class)
	@UserOperation(optName="删除健康食谱操作",optStyle="删除")
	public void delete() {
		ResultMsg<XhRecipe> result=new ResultMsg<XhRecipe>();
		try{
			int id = getParaToInt("id");
			System.out.println(id);
			XhRecipe xhRecipe = XhRecipe.dao.findById(id);
			xhRecipe.delete();
			result.setResult("success");	
			result.setDescription("删除成功！");
			result.setContent(xhRecipe);
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
