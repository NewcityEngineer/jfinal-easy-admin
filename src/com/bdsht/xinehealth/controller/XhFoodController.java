package com.bdsht.xinehealth.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;




import com.bdsht.xinehealth.bean.EasyuiPageBean;
import com.bdsht.xinehealth.bean.JsonTreeData;
import com.bdsht.xinehealth.bean.ResultMsg;
import com.bdsht.xinehealth.interceptor.AdminUserInterceptor;
import com.bdsht.xinehealth.interceptor.UserOperation;
import com.bdsht.xinehealth.model.FoodInfo;
import com.bdsht.xinehealth.model.XhRecipe;
import com.bdsht.xinehealth.util.StaticURL;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Page;

public class XhFoodController extends Controller{

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
		Page<FoodInfo> healthFoodStyles;
		if(page==null||rows==null){
			healthFoodStyles = FoodInfo.dao.paginate(1, 10);
			//renderJson(healthFoodTitles);
		}else{
			healthFoodStyles = FoodInfo.dao.paginate(Integer.parseInt(page), Integer.parseInt(rows));

		}

		EasyuiPageBean<FoodInfo> easyuiPageBean=new EasyuiPageBean<FoodInfo>();
		easyuiPageBean.setTotal(FoodInfo.dao.findAll().size());
		easyuiPageBean.setRows(healthFoodStyles.getList());
		renderJson(easyuiPageBean);
	}
	@Before(AdminUserInterceptor.class)
	@UserOperation(optName="增加食物相生相克操作",optStyle="增加")
	public void add() {

		ResultMsg<FoodInfo> result=new ResultMsg<FoodInfo>();
		try{
			//			UploadFile uploadFile = this.getFile("img","food_recipe");
			String species= getPara("species");
			String name = getPara("name");
			String effect = getPara("effect");
			String suitable = getPara("suitable");
			String unsuited = getPara("unsuited");
			String choose = getPara("choose");
			String store = getPara("store");
			String cook = getPara("cook");
			String better = getPara("better");
			String worse = getPara("worse");
			String health = getPara("health");
			String othername = getPara("othername");
			// // 拼接文件上传的完整路径
			if(!StrKit.isBlank(species)&&!StrKit.isBlank(name)&&!StrKit.isBlank(effect)&&!StrKit.isBlank(suitable)
					&&!StrKit.isBlank(unsuited)&&!StrKit.isBlank(choose)&&!StrKit.isBlank(store)
					&&!StrKit.isBlank(cook)&&!StrKit.isBlank(better)&&!StrKit.isBlank(othername))
				if (FoodInfo.dao.findByName(name) == null) {
					FoodInfo foodInfo =new FoodInfo();
					foodInfo.setSpecies(species);
					foodInfo.setName(name);
					foodInfo.setEffect(effect);
					foodInfo.setSuitable(suitable);
					foodInfo.setUnsuited(unsuited);
					foodInfo.setChoose(choose);
					foodInfo.setStore(store);
					foodInfo.setCook(cook);
					foodInfo.setBetter(better);
					foodInfo.setWorse(worse);
					foodInfo.setHealth(health);
					foodInfo.setOthername(othername);
					foodInfo.save();
					result.setResult("success");
					result.setDescription("添加成功！");
					result.setContent(foodInfo);
				}else{
					result.setResult("failure");
					result.setDescription("该食物相生相克已经存在！");
				}
			else{
				
				
				StringBuffer sb=new StringBuffer();
				if(StrKit.isBlank(species)){
					sb.append("食品类别不能为空！");
				}else if(StrKit.isBlank(name)){
					sb.append("食品名称不能为空！");
				}else if(StrKit.isBlank(effect)){
					sb.append("营养功效不能为空！");
				}else if(StrKit.isBlank(suitable)){
					sb.append("适宜人群不能为空！");
				}else if(StrKit.isBlank(unsuited)){
					sb.append("不宜人群不能为空！");
				}else if(StrKit.isBlank(choose)){
					sb.append("选购不能为空！");
				}else if(StrKit.isBlank(store)){
					sb.append("储存不能为空！");
				}else if(StrKit.isBlank(cook)){
					sb.append("烹饪不能为空！");
				}else if(StrKit.isBlank(better)){
					sb.append("相生功效不能为空！");
				}else if(StrKit.isBlank(othername)){
					sb.append("别名不能为空！");
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
	@UserOperation(optName="修改食物相生相克操作",optStyle="更新")
	public void update() {
		int id=getParaToInt("id");
		FoodInfo foodInfo =FoodInfo.dao.findById(id);
		ResultMsg<FoodInfo> result=new ResultMsg<FoodInfo>();
		String species= getPara("species");
		String name = getPara("name");
		String effect = getPara("effect");
		String suitable = getPara("suitable");
		String unsuited = getPara("unsuited");
		String choose = getPara("choose");
		String store = getPara("store");
		String cook = getPara("cook");
		String better = getPara("better");
		String worse = getPara("worse");
		String health = getPara("health");
		String othername = getPara("othername");
	
		foodInfo.setSpecies(species);
		foodInfo.setName(name);
		foodInfo.setEffect(effect);
		foodInfo.setSuitable(suitable);
		foodInfo.setUnsuited(unsuited);
		foodInfo.setChoose(choose);
		foodInfo.setStore(store);
		foodInfo.setCook(cook);
		foodInfo.setBetter(better);
		foodInfo.setWorse(worse);
		foodInfo.setHealth(health);
		foodInfo.setOthername(othername);
		foodInfo.update();
		result.setResult("success");
		result.setDescription("更改成功!");
		result.setContent(foodInfo);
		renderJson(result);
	}
	@Before(AdminUserInterceptor.class)
	@UserOperation(optName="删除食物相生相克操作",optStyle="删除")
	public void delete() {
		ResultMsg<FoodInfo> result=new ResultMsg<FoodInfo>();
		try{
			int id = getParaToInt("id");
			System.out.println(id);
			FoodInfo foodInfo = FoodInfo.dao.findById(id);
			foodInfo.delete();
			result.setResult("success");	
			result.setDescription("删除成功！");
			result.setContent(foodInfo);
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
	
	public void findBySpecies(){
		
		List<FoodInfo> foodInfos=FoodInfo.dao.findBySpecies(getPara("species"));
		renderJson(foodInfos);
	}
	
	public void queryFoodInteraction(){

		String name=getPara("name");

		String json="";
		StringBuffer sb=new StringBuffer();
		sb.append("{");
		sb.append("\"interaction\":");

		Map<String, String> map=new HashMap<String, String>();

		if(name.contains("，")){

			String[] nameArr=name.split("，");
			for (int i = 0; i < nameArr.length; i++) {

				List<FoodInfo> foodInfo=FoodInfo.dao.find("select * from food_info where name='"+nameArr[i]+"'");
				if(foodInfo.size()>0){

					String better=foodInfo.get(0).getBetter();
					String worse=foodInfo.get(0).getWorse();
					String[] betArr=better.split("；");
					String[] worArr=worse.split("；");
					for (int j = 0; j < nameArr.length; j++) {

						for (String string : betArr) {
							if(string.split("：")[0].contains(nameArr[j])){

								String betterMap=map.get("better");

								if(betterMap==null){
									map.put("better", "{\"better\":\""+nameArr[i]+"与"+string+"\"}");
								}else{
									if(!betterMap.contains(nameArr[j])){
										map.put("better", betterMap+","+"{\"better\":\""+nameArr[i]+"与"+string+"\"}");
									}	
								}
							}
						}

						for(String string :worArr){

							if(string.split("：")[0].contains(nameArr[j])){

								String worseMap=map.get("worse");

								if(worseMap==null){
									map.put("worse", "{\"worse\":\""+nameArr[i]+"与"+string+"\"}");
								}else{
									if(!worseMap.contains(nameArr[j])){
										map.put("worse", worseMap+","+"{\"worse\":\""+nameArr[i]+"与"+string+"\"}");
									}	
								}
							}
						}

					}





				}

			}



			String bet=map.get("better");
			String wor=map.get("worse");
			if(bet==null&&wor==null){

				sb.append("\"none\",\"key\":\"none\",");
			}else if(wor==null&&bet!=null){
				sb.append("["+bet+"],\"key\":\"better\",");

			}else if(wor!=null&&bet==null){

				sb.append("["+wor+"],\"key\":\"worse\",");
			}else{

				sb.append("["+bet+","+wor+"],\"key\":\"worse\",");

			}
			sb.append("\"food_info\":[");
			List<String> list=new ArrayList<String>();
			for (int i = 0; i < nameArr.length; i++) {

				if(!getFoodInfoJson(nameArr[i]).equals("")){

					list.add(nameArr[i]);
				}


			}

			for (String string2 : list) {
				sb.append(getFoodInfoJson(string2)+",");
			}

			json+=sb.toString();
			json=json.substring(0, json.length()-1);
			json=json+"]}";

		}else{
			//查询症状
			if(getFoodInfoJson(name).equals("")){
				sb.append("\"no\",\"key\":\"none\",");
				//sb.append("\"food_info\":[");

				List<FoodInfo> foodinfos=FoodInfo.dao.find("select * from food_info where suitable like '%"+name+"%' or unsuited like '%"+name+"%'");
				// or better like '%"+name+"%' or worse like '%"+name+"'

				String food_better="";
				String food_worse="";
				for (FoodInfo foodInfo : foodinfos) {
					if(foodInfo.getSuitable().contains(name)){
						food_better+="{\"name\":\""+foodInfo.getName()+"\"}"+",";
					}else if(foodInfo.getUnsuited().contains(name)){
//						food_worse+="\""+foodInfo.getName()+"\""+",";
						food_worse+="{\"name\":\""+foodInfo.getName()+"\"}"+",";
					}

					//sb.append(getFoodInfoJson(foodInfo.getName())+",");
				}
				if(!food_better.equals(""))
					food_better=food_better.substring(0, food_better.length()-1);
				if(!food_worse.equals(""))
					food_worse=food_worse.substring(0, food_worse.length()-1);
				sb.append("\"food_better\":["+food_better+"],");
				sb.append("\"food_worse\":["+food_worse+"]}");

				json+=sb.toString();


			}else{
				sb.append("\"no\",\"key\":\"none\",");
				sb.append("\"food_info\":["+getFoodInfoJson(name)+"]}");
				json=sb.toString();
			}

		}

		//		setAttr("json", json);
		//		
		//		render("1.html");

		renderJson(json);
	}

	
	public void getFoodInfo(){
		
		List<FoodInfo> foodInfos=FoodInfo.dao.find("select * from food_info where name='"+getPara("name")+"'");
		
		if(foodInfos.size()>0){
			FoodInfo foodInfo=foodInfos.get(0);
			setAttr("foodInfo", foodInfo);
			render("getFood.html");
		}else{
			FoodInfo foodInfo=new FoodInfo();
			foodInfo.setName("");
			foodInfo.setSpecies("");
			foodInfo.setEffect("");
			foodInfo.setSuitable("");
			foodInfo.setUnsuited("");
			foodInfo.setChoose("");
			foodInfo.setStore("");
			foodInfo.setCook("");
			foodInfo.setBetter("");
			foodInfo.setWorse("");
			setAttr("foodInfo", foodInfo);
			render("query.html");
		}
	}

	public String getFoodInfoJson(String name){

		StringBuffer sb=new StringBuffer();

		List<FoodInfo> foodInfos=FoodInfo.dao.find("select * from food_info where name='"+name+"'");
		if(foodInfos.size()==0){
			return "";
		}else{
			FoodInfo foodInfo=foodInfos.get(0);
			sb.append("{");
			sb.append("\"species\":\""+foodInfo.getSpecies()+"\",");
			sb.append("\"name\":\""+foodInfo.getName()+"\",");
			sb.append("\"effect\":\""+foodInfo.getEffect()+"\",");
			sb.append("\"suitable\":\""+foodInfo.getSuitable()+"\",");
			sb.append("\"unsuited\":\""+foodInfo.getUnsuited()+"\",");
			sb.append("\"choose\":\""+foodInfo.getChoose()+"\",");
			sb.append("\"store\":\""+foodInfo.getStore()+"\",");
			sb.append("\"cook\":\""+foodInfo.getCook()+"\",");
			sb.append("\"better\":\""+foodInfo.getBetter()+"\",");
			sb.append("\"worse\":\""+foodInfo.getWorse()+"\"");
			sb.append("}");

			return sb.toString();
		}


	}
	
	
	

}
