package com.bdsht.xinehealth.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bdsht.xinehealth.bean.EasyuiPageBean;
import com.bdsht.xinehealth.bean.JsonTreeData;
import com.bdsht.xinehealth.bean.ResultMsg;
import com.bdsht.xinehealth.bean.XhIngredientsBean;
import com.bdsht.xinehealth.interceptor.AdminUserInterceptor;
import com.bdsht.xinehealth.interceptor.UserOperation;
import com.bdsht.xinehealth.model.XhClassicrecipes;
import com.bdsht.xinehealth.model.XhIngredients;
import com.bdsht.xinehealth.model.XhNutritional;
import com.bdsht.xinehealth.model.XhStyle;
import com.bdsht.xinehealth.model.XhTitle;
import com.bdsht.xinehealth.util.CommonUtil;
import com.bdsht.xinehealth.util.StaticURL;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.upload.UploadFile;

public class XhIngredientsController extends Controller{
	String food_ingredients=StaticURL.FOOD_INGREDIENTS;

	
	public void listAll(){
		
		
		renderJson(XhIngredients.dao.findAll());
		
	}
	
	public void listByStyleId(){
		int styleId=getParaToInt("styleId");
		List<XhIngredients> xhIngredientsList=XhIngredients.dao.findByStyleId(styleId);
		System.out.println(xhIngredientsList.size());
		renderJson(xhIngredientsList);
	}
	
	public void listByIngreId(){
		int ingreId=getParaToInt("ingreId");
		XhIngredients xhIngredients=XhIngredients.dao.findById(ingreId);
		XhIngredientsBean xhIngredientsBean=new XhIngredientsBean();
		xhIngredientsBean.setXhIngredientsBase(xhIngredients);
		xhIngredientsBean.setXhNutritional(XhNutritional.dao.findByIngreId(ingreId));
		xhIngredientsBean.setXhClassicrecipesList(XhClassicrecipes.dao.findByIngreId(ingreId));
		renderJson(xhIngredientsBean);
	}
	
	public void all() {
		//
		String page=getPara("page");
		String rows=getPara("rows");
		Page<XhIngredients> healthFoodStyles;
		if(page==null||rows==null){
			healthFoodStyles = XhIngredients.dao.paginate(1, 10);
			//renderJson(healthFoodTitles);
		}else{
			healthFoodStyles = XhIngredients.dao.paginate(Integer.parseInt(page), Integer.parseInt(rows));

		}

		EasyuiPageBean<XhIngredients> easyuiPageBean=new EasyuiPageBean<XhIngredients>();
		easyuiPageBean.setTotal(XhIngredients.dao.findAll().size());
		easyuiPageBean.setRows(healthFoodStyles.getList());
		renderJson(easyuiPageBean);
	}
	@Before(AdminUserInterceptor.class)
	@UserOperation(optName="增加食材操作",optStyle="增加")
	public void add() {
		UploadFile uploadFile = this.getFile("img","food_ingredients");
		String food_style_sId= getPara("food_style_sId");
		String name = getPara("name");
		String function = getPara("function");
		String description = getPara("description");
		String purchase = getPara("purchase");
		String tropism = getPara("tropism");
		String effect = getPara("effect");
		String suitPeople = getPara("suitPeople");
		String notSuitPeople = getPara("notSuitPeople");
		String suitWith = getPara("suitWith");
		String notSuitWith = getPara("notSuitWith");
		String foodLawTaboo = getPara("foodLawTaboo");
		String prescription = getPara("prescription");
		String suitSeason=getPara("suitSeason");
		String notSuitSeason=getPara("notSuitSeason");
		// // 拼接文件上传的完整路径
		ResultMsg<XhIngredients> resultMsg=new ResultMsg<XhIngredients>();

		if (!StrKit.isBlank(food_style_sId)&&!StrKit.isBlank(name)&& uploadFile != null
				&&!StrKit.isBlank(function)&&!StrKit.isBlank(description)&&!StrKit.isBlank(purchase)&&!StrKit.isBlank(tropism)
				&&!StrKit.isBlank(effect)&&!StrKit.isBlank(suitPeople)&&!StrKit.isBlank(notSuitPeople)&&!StrKit.isBlank(suitWith)
				&&!StrKit.isBlank(foodLawTaboo)) {
			if (XhIngredients.dao.findByName(name) == null) {
				XhIngredients xhIngredients = new XhIngredients();
				try{
					CommonUtil.uploadCardToOSS(StaticURL.XINE_HEALTH_BUCKETNAME,"images/"+food_ingredients , uploadFile.getFileName(),uploadFile.getUploadPath()+"/");
					String fileName =uploadFile.getFileName();
					xhIngredients.setImg(StaticURL.ALIYUN_OSS_XINE_HEALTH +food_ingredients+"/"+fileName);

					int style_id=Integer.parseInt(food_style_sId);
					XhStyle xhStyle=XhStyle.dao.findById(style_id);
					String sId=xhStyle.getSId();
					String tId=XhTitle.dao.findById(xhStyle.getFoodTitleTid()).getTId();

					xhIngredients.setIngreId("i"+tId+sId+getCurrentTime("yyyyMMdd"));

					//				healthFoodRecipe.setIngreId(ingreId);
					xhIngredients.setFoodStyleSid(food_style_sId);
					xhIngredients.setName(name);
					xhIngredients.setFunction(function);
					xhIngredients.setDescription(description);
					xhIngredients.setPurchase(purchase);
					xhIngredients.setTropism(tropism);
					xhIngredients.setEffect(effect);
					xhIngredients.setSuitPeople(suitPeople);
					xhIngredients.setNotSuitPeople(notSuitPeople);
					xhIngredients.setSuitWith(suitWith);
					xhIngredients.setNotSuitWith(notSuitWith);
					xhIngredients.setFoodLawTaboo(foodLawTaboo);
					xhIngredients.setPrescription(prescription);
					xhIngredients.setSuitSeason(suitSeason);
					xhIngredients.setNotSuitSeason(notSuitSeason);

					xhIngredients.setOldTime(getCurrentTime("yyyy-MM-dd HH:mm:ss"));
					xhIngredients.setNewTime(getCurrentTime("yyyy-MM-dd HH:mm:ss"));
					xhIngredients.setTimestamp(getCurrentTime("yyyyMMddHHmmss"));
					xhIngredients.save();
					resultMsg.setResult("success");
					resultMsg.setContent(xhIngredients);
					resultMsg.setDescription("上传成功！");
				}catch(Exception e){
					resultMsg.setResult("failure");
					resultMsg.setDescription(e.toString());
				}


			}else{
				resultMsg.setResult("failure");
				resultMsg.setDescription("食材信息已上传！");
			}
		}else{
			StringBuffer sb=new StringBuffer();

			if(StrKit.isBlank(food_style_sId)){ 
				sb.append("二级分类未选择！");
			}else if(StrKit.isBlank(name)){ 
				sb.append("食材名称不能为空！");
			}else if(uploadFile==null){ 
				sb.append("图片不能为空！");
			}else if(StrKit.isBlank(function)){ 
				sb.append("作用不能为空！");
			}else if(StrKit.isBlank(description)){ 
				sb.append("说明不能为空！");
			}else if(StrKit.isBlank(purchase)){ 
				sb.append("选购不能为空！");
			}else if(StrKit.isBlank(tropism)){ 
				sb.append("性味归经不能为空！");
			}else if(StrKit.isBlank(effect)){ 
				sb.append("作用不能为空！");
			}else if(StrKit.isBlank(suitPeople)){ 
				sb.append("适宜人群不能为空！");
			}else if(StrKit.isBlank(notSuitPeople)){ 
				sb.append("不适宜人群不能为空！");
			}else if(StrKit.isBlank(suitWith)){ 
				sb.append("适合搭配不能为空！");
			}else if(StrKit.isBlank(foodLawTaboo)){ 
				sb.append("不能为空！");
			}
			resultMsg.setResult("failure");
			resultMsg.setDescription(sb.toString());
		}
		renderJson(resultMsg);
	}
	@Before(AdminUserInterceptor.class)
	@UserOperation(optName="修改食材操作",optStyle="更新")
	public void update() {
		UploadFile uploadFile = this.getFile("img","food_ingredients");
		ResultMsg<XhIngredients> resultMsg=new ResultMsg<XhIngredients>();
		try{


			int id = getParaToInt("id");
			String timestamp = getPara("timestamp");
			XhIngredients healthFoodIngredients = XhIngredients.dao
					.findByIdAndTimestamp(id, timestamp);

			if (healthFoodIngredients != null) {

				String ingreId = getPara("ingreId");
				String food_style_sId= getPara("food_style_sId");
				String name = getPara("name");
				String function = getPara("function");
				String description = getPara("description");
				String purchase = getPara("purchase");
				String tropism = getPara("tropism");
				String effect = getPara("effect");
				String suitPeople = getPara("suitPeople");
				String notSuitPeople = getPara("notSuitPeople");
				String suitWith = getPara("suitWith");
				String notSuitWith = getPara("notSuitWith");
				String foodLawTaboo = getPara("foodLawTaboo");
				String prescription = getPara("prescription");
				String suitSeason=getPara("suitSeason");
				String notSuitSeason=getPara("notSuitSeason");


				healthFoodIngredients.setIngreId(ingreId);
				healthFoodIngredients.setFoodStyleSid(food_style_sId);
				healthFoodIngredients.setName(name);
				healthFoodIngredients.setFunction(function);
				healthFoodIngredients.setDescription(description);
				healthFoodIngredients.setPurchase(purchase);
				healthFoodIngredients.setTropism(tropism);
				healthFoodIngredients.setEffect(effect);
				healthFoodIngredients.setSuitPeople(suitPeople);
				healthFoodIngredients.setNotSuitPeople(notSuitPeople);
				healthFoodIngredients.setSuitWith(suitWith);
				healthFoodIngredients.setNotSuitWith(notSuitWith);
				healthFoodIngredients.setFoodLawTaboo(foodLawTaboo);
				healthFoodIngredients.setPrescription(prescription);
				healthFoodIngredients.setSuitSeason(suitSeason);
				healthFoodIngredients.setNotSuitSeason(notSuitSeason);
				
				//healthFoodRecipe.setImg(fileName);
				// // 拼接文件上传的完整路径
					String imgname=healthFoodIngredients.getImg();
					System.out.println("imaname:"+imgname);
					if(!imgname.substring(imgname.lastIndexOf("/"), imgname.length()).equals(uploadFile.getFileName())){
						CommonUtil.uploadCardToOSS(StaticURL.XINE_HEALTH_BUCKETNAME,"images/"+food_ingredients , uploadFile.getFileName(),uploadFile.getUploadPath()+"/");
						healthFoodIngredients.setImg(StaticURL.ALIYUN_OSS_XINE_HEALTH +food_ingredients+"/"+uploadFile.getFileName());

					}

					healthFoodIngredients.setNewTime(getCurrentTime("yyyy-MM-dd HH:mm:ss"));
					healthFoodIngredients.setTimestamp(getCurrentTime("yyyyMMddHHmmss"));

					healthFoodIngredients.update();
					resultMsg.setResult("success");
					resultMsg.setDescription("更改成功！");
					resultMsg.setContent(healthFoodIngredients);
			}else {
				resultMsg.setResult("failure");
				resultMsg.setDescription("正在被修改，请刷新页面，再修改！");
			}
		}catch(Exception e){
			resultMsg.setResult("failure");
			resultMsg.setDescription(e.toString());
		}
		renderJson(resultMsg);
	}
	@Before(AdminUserInterceptor.class)
	@UserOperation(optName="删除食材操作",optStyle="删除")
	public void delete() {
		ResultMsg<XhIngredients> resultMsg=new ResultMsg<XhIngredients>();
		try{
			int id = getParaToInt("id");
			System.out.println(id);
			XhIngredients healthFoodIngredients = XhIngredients.dao.findById(id);
			healthFoodIngredients.delete();
			resultMsg.setResult("success");
			resultMsg.setDescription("删除成功！");
			resultMsg.setContent(healthFoodIngredients);
		}catch(Exception e){
			resultMsg.setResult("failure");
			resultMsg.setDescription(e.toString());
		}
		renderJson(resultMsg);
	}

	public String getCurrentTime(String format) {

		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date now = new Date();
		String dt = sdf.format(now);

		return dt;
	}

	public void getJsonTreeData() {
		List<XhIngredients> resultList = XhIngredients.dao.findAll();
		List<JsonTreeData> treeDataList = new ArrayList<JsonTreeData>();

		for (XhIngredients healthFoodIngredients : resultList) {
			JsonTreeData treeData = new JsonTreeData();

			treeData.setId(String.valueOf(healthFoodIngredients.getId()));
			treeData.setText(healthFoodIngredients.getName());
			treeData.setState("closed");


			treeDataList.add(treeData);
		}

		renderJson(treeDataList);
	}

}
