package com.bdsht.xinehealth.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.bdsht.xinehealth.bean.EasyuiPageBean;
import com.bdsht.xinehealth.bean.ResultMsg;
import com.bdsht.xinehealth.interceptor.AdminUserInterceptor;
import com.bdsht.xinehealth.interceptor.UserOperation;
import com.bdsht.xinehealth.model.XhNutritional;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;

public class XhNutritionalController extends Controller{

	
	public void listAll(){
		
		renderJson(XhNutritional.dao.findAll());
		
	}
	
	public void listByIngreId(){
		int ingreId=getParaToInt("ingreId");
		XhNutritional xhNutritionals=XhNutritional.dao.findByIngreId(ingreId);
		renderJson(xhNutritionals);
	}
	
	
	public void all() {
		//
		String page=getPara("page");
		String rows=getPara("rows");
		Page<XhNutritional> healthFoodNutritional;
		if(page==null||rows==null){
			healthFoodNutritional = XhNutritional.dao.paginate(1, 10);
			//renderJson(healthFoodTitles);
		}else{
			healthFoodNutritional = XhNutritional.dao.paginate(Integer.parseInt(page), Integer.parseInt(rows));

		}

		EasyuiPageBean<XhNutritional> easyuiPageBean=new EasyuiPageBean<XhNutritional>();
		easyuiPageBean.setTotal(XhNutritional.dao.findAll().size());
		easyuiPageBean.setRows(healthFoodNutritional.getList());
		renderJson(easyuiPageBean);
	}
	@Before(AdminUserInterceptor.class)
	@UserOperation(optName="增加食材营养含量操作",optStyle="增加")
	public void add() {
		ResultMsg<XhNutritional> result=new ResultMsg<XhNutritional>();
		String food_style_ingreId = getPara("food_style_ingreId");
		//String description = getPara("description");
		System.out.println("food_style_ingreId:"+food_style_ingreId);
		String heat=getPara("heat")==null?"0":getPara("heat");
		String protein=getPara("protein")==null?"0":getPara("protein");
		String fat=getPara("fat")==null?"0":getPara("fat");
		String carbohydrates=getPara("carbohydrates")==null?"0":getPara("carbohydrates");
		String vitaminB1=getPara("vitaminB1")==null?"0":getPara("vitaminB1");
		String vitaminB2=getPara("vitaminB2")==null?"0":getPara("vitaminB2");
		String vitaminE=getPara("vitaminE")==null?"0":getPara("vitaminE");
		String calcium=getPara("calcium")==null?"0":getPara("calcium");
		String potassium=getPara("potassium")==null?"0":getPara("potassium");
		String phosphorus=getPara("phosphorus")==null?"0":getPara("phosphorus");
		String sodium=getPara("sodium")==null?"0":getPara("sodium");
		String magnesium=getPara("magnesium")==null?"0":getPara("magnesium");
		String iron=getPara("iron")==null?"0":getPara("iron");
		String zinc=getPara("zinc")==null?"0":getPara("zinc");

		if (food_style_ingreId!=null) {
			if (XhNutritional.dao.findByIngreId(Integer.parseInt(food_style_ingreId)) == null) {
				XhNutritional healthFoodNutritional = new XhNutritional();
				healthFoodNutritional.setFoodStyleIngreid(food_style_ingreId);
				healthFoodNutritional.setHeat(heat);;
				healthFoodNutritional.setProtein(protein);
				healthFoodNutritional.setFat(fat);
				healthFoodNutritional.setCarbohydrates(carbohydrates);
				healthFoodNutritional.setVitaminB1(vitaminB1);
				healthFoodNutritional.setVitaminB2(vitaminB2);
				healthFoodNutritional.setVitaminE(vitaminE);
				healthFoodNutritional.setCalcium(calcium);
				healthFoodNutritional.setPotassium(potassium);
				healthFoodNutritional.setPhosphorus(phosphorus);
				healthFoodNutritional.setSodium(sodium);
				healthFoodNutritional.setMagnesium(magnesium);
				healthFoodNutritional.setIron(iron);
				healthFoodNutritional.setZinc(zinc);


				healthFoodNutritional.setOldTime(getCurrentTime("yyyy-MM-dd HH:mm:ss"));
				healthFoodNutritional.setNewTime(getCurrentTime("yyyy-MM-dd HH:mm:ss"));
				healthFoodNutritional.setTimestamp(getCurrentTime("yyyyMMddHHmmss"));
				healthFoodNutritional.save();
				result.setResult("success");
				result.setDescription("添加成功！");
				result.setContent(healthFoodNutritional);
			}else{
				result.setResult("failure");
				result.setDescription("该营养成分信息已上传！");
			}
		}else{
			result.setResult("failure");
			result.setDescription("食材未选择！");
		}
		renderJson(result);
	}
	@Before(AdminUserInterceptor.class)
	@UserOperation(optName="修改食材营养含量操作",optStyle="更新")
	public void update() {
		ResultMsg<XhNutritional> result=new ResultMsg<XhNutritional>();
		int id = getParaToInt("id");
		String timestamp = getPara("timestamp");
		XhNutritional healthFoodNutritional = XhNutritional.dao
				.findByIdAndTimestamp(id, timestamp);
		if (healthFoodNutritional != null) {
			String heat=getPara("heat")==null?"0":getPara("heat");
			String protein=getPara("protein")==null?"0":getPara("protein");
			String fat=getPara("fat")==null?"0":getPara("fat");
			String carbohydrates=getPara("carbohydrates")==null?"0":getPara("carbohydrates");
			String vitaminB1=getPara("vitaminB1")==null?"0":getPara("vitaminB1");
			String vitaminB2=getPara("vitaminB2")==null?"0":getPara("vitaminB2");
			String vitaminE=getPara("vitaminE")==null?"0":getPara("vitaminE");
			String calcium=getPara("calcium")==null?"0":getPara("calcium");
			String potassium=getPara("potassium")==null?"0":getPara("potassium");
			String phosphorus=getPara("phosphorus")==null?"0":getPara("phosphorus");
			String sodium=getPara("sodium")==null?"0":getPara("sodium");
			String magnesium=getPara("magnesium")==null?"0":getPara("magnesium");
			String iron=getPara("iron")==null?"0":getPara("iron");
			String zinc=getPara("zinc")==null?"0":getPara("zinc");

			healthFoodNutritional.setFoodStyleIngreid(getPara("food_style_ingreId"));
			healthFoodNutritional.setHeat(heat);
			healthFoodNutritional.setProtein(protein);
			healthFoodNutritional.setFat(fat);
			healthFoodNutritional.setCarbohydrates(carbohydrates);
			healthFoodNutritional.setVitaminB1(vitaminB1);
			healthFoodNutritional.setVitaminB2(vitaminB2);
			healthFoodNutritional.setVitaminE(vitaminE);
			healthFoodNutritional.setCalcium(calcium);
			healthFoodNutritional.setPotassium(potassium);
			healthFoodNutritional.setPhosphorus(phosphorus);
			healthFoodNutritional.setSodium(sodium);
			healthFoodNutritional.setMagnesium(magnesium);
			healthFoodNutritional.setIron(iron);
			healthFoodNutritional.setZinc(zinc);

			healthFoodNutritional
			.setNewTime(getCurrentTime("yyyy-MM-dd HH:mm:ss"));
			healthFoodNutritional.setTimestamp(getCurrentTime("yyyyMMddHHmmss"));

			healthFoodNutritional.update();
			result.setContent(healthFoodNutritional);
			result.setResult("success");
			result.setDescription("修改成功！");
		}else{
			result.setResult("failure");
			result.setDescription("已被修改！");
		}

		renderJson(result);
	}
	@Before(AdminUserInterceptor.class)
	@UserOperation(optName="删除食材营养含量操作",optStyle="删除")
	public void delete() {
		ResultMsg<XhNutritional> result=new ResultMsg<XhNutritional>();
		try{
			int id = getParaToInt("id");
			System.out.println(id);
			XhNutritional healthFoodNutritional =XhNutritional.dao.findById(id);
			healthFoodNutritional.delete();
			result.setResult("success");
			result.setDescription("删除成功！");
			result.setContent(healthFoodNutritional);
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
