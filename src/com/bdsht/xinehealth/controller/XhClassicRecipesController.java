package com.bdsht.xinehealth.controller;

import java.util.ArrayList;
import java.util.List;

import com.bdsht.xinehealth.bean.EasyuiPageBean;
import com.bdsht.xinehealth.bean.JsonTreeData;
import com.bdsht.xinehealth.bean.ResultMsg;
import com.bdsht.xinehealth.interceptor.AdminUserInterceptor;
import com.bdsht.xinehealth.interceptor.UserOperation;
import com.bdsht.xinehealth.model.XhClassicrecipes;
import com.bdsht.xinehealth.model.XhIngredients;
import com.bdsht.xinehealth.model.XhTitle;
import com.bdsht.xinehealth.util.CommonUtil;
import com.bdsht.xinehealth.util.StaticURL;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Page;

public class XhClassicRecipesController extends Controller {
	String food_classicRecipes = StaticURL.FOOD_ClASSICRECIPES;

	public void listAll() {

		renderJson(XhClassicrecipes.dao.findAll());

	}

	public void listByIngreId() {
		int ingreId = getParaToInt("ingreId");
		List<XhClassicrecipes> xhNutritionalsList = XhClassicrecipes.dao.findByIngreId(ingreId);
		renderJson(xhNutritionalsList);
	}

	public void all() {
		//
		String page = getPara("page");
		String rows = getPara("rows");
		Page<XhClassicrecipes> healthFoodTitles;
		if (page == null || rows == null) {
			healthFoodTitles = XhClassicrecipes.dao.paginate(1, 10);
			// renderJson(healthFoodTitles);
		} else {
			healthFoodTitles = XhClassicrecipes.dao.paginate(
					Integer.parseInt(page), Integer.parseInt(rows));

		}

		EasyuiPageBean<XhClassicrecipes> easyuiPageBean = new EasyuiPageBean<XhClassicrecipes>();
		easyuiPageBean.setTotal(XhClassicrecipes.dao.findAll().size());
		easyuiPageBean.setRows(healthFoodTitles.getList());
		renderJson(easyuiPageBean);
	}
	@Before(AdminUserInterceptor.class)
	@UserOperation(optName="增加经典食谱操作",optStyle="增加")
	public void add() {
		// UploadFile uploadFile = this.getFile("img","food_title");

		String food_style_ingreId = getPara("food_style_ingreId");
		String title = getPara("title");
		String main = getPara("main");
		String effect = getPara("effect");
		String xhRecipeId = StrKit.isBlank(getPara("xhRecipeId")) ? "0"
				: getPara("xhRecipeId");
		ResultMsg<XhClassicrecipes> resultMsg = new ResultMsg<XhClassicrecipes>();

		// // 拼接文件上传的完整路径
		// String fileName = title_path+ uploadFile.getFileName();
		if (!StrKit.isBlank(food_style_ingreId) && !StrKit.isBlank(title)
				&& !StrKit.isBlank(main) && !StrKit.isBlank(effect)) {
			if (XhClassicrecipes.dao.findByTitle(title) == null) {

				XhClassicrecipes xhClassicrecipes = new XhClassicrecipes();
				xhClassicrecipes.setCrsId(XhIngredients.dao.findById(
						food_style_ingreId).getIngreId()
						+ "cr");
				xhClassicrecipes.setFoodStyleIngreid(food_style_ingreId);
				xhClassicrecipes.setTitle(title);
				xhClassicrecipes.setMain(main);
				xhClassicrecipes.setEffect(effect);
				xhClassicrecipes.setXhRecipeId(Integer.parseInt(xhRecipeId));

				xhClassicrecipes.setOldTime(CommonUtil
						.getCurrentTime("yyyy-MM-dd HH:mm:ss"));
				xhClassicrecipes.setNewTime(CommonUtil
						.getCurrentTime("yyyy-MM-dd HH:mm:ss"));
				xhClassicrecipes.setTimestamp(CommonUtil
						.getCurrentTime("yyyyMMddHHmmss"));
				xhClassicrecipes.save();
				resultMsg.setResult("success");
				resultMsg.setDescription("添加成功！");
				resultMsg.setContent(xhClassicrecipes);
			} else {
				resultMsg.setResult("fileure");
				resultMsg.setDescription("该经典食谱已添加！");
			}
		} else {
			StringBuffer sb = new StringBuffer();
			if (StrKit.isBlank(food_style_ingreId)) {
				sb.append("食材未选择！");
			} else if (StrKit.isBlank(title)) {
				sb.append("食材名称不能为空！");
			} else if (StrKit.isBlank(main)) {
				sb.append("食谱主料不能为空！");
			} else if (StrKit.isBlank(effect)) {
				sb.append("主要功效不能为空！");
			}
			resultMsg.setResult("failure");
			resultMsg.setDescription(sb.toString());
		}
		renderJson(resultMsg);
	}
	@Before(AdminUserInterceptor.class)
	@UserOperation(optName="修改经典食谱操作",optStyle="更新")
	public void update() {
		// UploadFile uploadFile = this.getFile("img","food_title");
		ResultMsg<XhClassicrecipes> resultMsg = new ResultMsg<XhClassicrecipes>();
		int id = getParaToInt("id");
		String timestamp = getPara("timestamp");
		XhClassicrecipes xhClassicrecipes = XhClassicrecipes.dao
				.findByIdAndTimestamp(id, timestamp);
		if (xhClassicrecipes != null) {
			String food_style_ingreId = getPara("food_style_ingreId");
			String title = getPara("title");
			String main = getPara("main");
			String effect = getPara("effect");
			String xhRecipeId = StrKit.isBlank(getPara("xhRecipeId")) ? "0"
					: getPara("xhRecipeId");

			xhClassicrecipes.setCrsId(XhIngredients.dao.findById(
					food_style_ingreId).getIngreId()
					+ "cr");
			xhClassicrecipes.setFoodStyleIngreid(food_style_ingreId);
			xhClassicrecipes.setTitle(title);
			xhClassicrecipes.setMain(main);
			xhClassicrecipes.setEffect(effect);
			xhClassicrecipes.setXhRecipeId(Integer.parseInt(xhRecipeId));
			xhClassicrecipes.setNewTime(CommonUtil
					.getCurrentTime("yyyy-MM-dd HH:mm:ss"));
			xhClassicrecipes.setTimestamp(CommonUtil
					.getCurrentTime("yyyyMMddHHmmss"));

			xhClassicrecipes.update();
			resultMsg.setResult("success");
			resultMsg.setDescription("修改成功！");
			resultMsg.setContent(xhClassicrecipes);
		} else {
			resultMsg.setResult("failure");
			resultMsg.setDescription("正在被修改!");
		}

		renderJson(resultMsg);
	}
	@Before(AdminUserInterceptor.class)
	@UserOperation(optName="删除经典食谱操作",optStyle="增加")
	public void delete() {
		ResultMsg<XhClassicrecipes> resultMsg = new ResultMsg<XhClassicrecipes>();
		try {
			int id = getParaToInt("id");
			System.out.println(id);
			XhClassicrecipes xhClassicrecipes = XhClassicrecipes.dao
					.findById(id);
			xhClassicrecipes.delete();
			resultMsg.setResult("success");
			resultMsg.setDescription("删除成功！");
			resultMsg.setContent(xhClassicrecipes);
		} catch (Exception e) {
			resultMsg.setResult("failure");
			resultMsg.setDescription(e.toString());
		}

		renderJson(resultMsg);
	}

	public void getJsonTreeData() {
		// UploadFile uploadFile = this.getFile("img","food_title");
		List<XhTitle> resultList = XhTitle.dao.findAll();
		List<JsonTreeData> treeDataList = new ArrayList<JsonTreeData>();

		for (XhTitle healthFoodTitle : resultList) {
			JsonTreeData treeData = new JsonTreeData();

			treeData.setId(String.valueOf(healthFoodTitle.getId()));
			treeData.setPid(null);
			treeData.setText(healthFoodTitle.getName());
			treeData.setState(null);
			treeData.setChildren(null);

			treeDataList.add(treeData);
		}

		renderJson(treeDataList);
	}

}
