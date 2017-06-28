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
import com.bdsht.xinehealth.model.XhTitle;
import com.bdsht.xinehealth.util.CommonUtil;
import com.bdsht.xinehealth.util.StaticURL;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.upload.UploadFile;

public class XhTitleController extends Controller {

	String title_path=StaticURL.FOOD_TITLE;

	
	public void listAll(){
		
		renderJson(XhTitle.dao.findAll());
	}

	public void findByTitleName(){
		
		renderJson(XhTitle.dao.findByName(getPara("name")));
	}
	
	public void all() {
		//
		String page=getPara("page");
		String rows=getPara("rows");
		Page<XhTitle> healthFoodTitles;
		if(page==null||rows==null){
			healthFoodTitles = XhTitle.dao.paginate(1, 10);
			//renderJson(healthFoodTitles);
		}else{
			healthFoodTitles = XhTitle.dao.paginate(Integer.parseInt(page), Integer.parseInt(rows));

		}

		EasyuiPageBean<XhTitle> easyuiPageBean=new EasyuiPageBean<XhTitle>();
		easyuiPageBean.setTotal(XhTitle.dao.findAll().size());
		easyuiPageBean.setRows(healthFoodTitles.getList());
		renderJson(easyuiPageBean);
	}
	@Before(AdminUserInterceptor.class)
	@UserOperation(optName="增加一级分类操作",optStyle="增加")
	public void add() {
		UploadFile uploadFile = this.getFile("img","food_title");
		System.out.println("uploadPath"+uploadFile.getUploadPath());
		String name = getPara("name");
		String description = getPara("description");
		System.out.println(name);
		// // 拼接文件上传的完整路径
		System.out.println("uploadFile:"+uploadFile);




		ResultMsg<XhTitle> result=new ResultMsg<XhTitle>();
		//result.setMessage(message);

		//
		// this.setAttr("fileName", fileName);
		// System.out.println("================fileName:"+fileName);
		String tId = getPara("tId");
		System.out.println(name + ">>>>>" + description + ">>>>>>>" + tId
				+ ">>>>>" + uploadFile);
		if (name != null && description != null && tId != null
				&& uploadFile != null) {
			if (XhTitle.dao.findByName(name) == null) {
				XhTitle healthFoodTitle = new XhTitle();
				healthFoodTitle.setName(name);
				healthFoodTitle.setDescription(description);
				try{
					CommonUtil.uploadCardToOSS(StaticURL.XINE_HEALTH_BUCKETNAME,"images/"+title_path , uploadFile.getFileName(),uploadFile.getUploadPath()+"/");
					String fileName =uploadFile.getFileName();
					healthFoodTitle.setImg(StaticURL.ALIYUN_OSS_XINE_HEALTH +title_path+"/"+fileName);

					healthFoodTitle
					.setOldTime(getCurrentTime("yyyy-MM-dd HH:mm:ss"));
					healthFoodTitle
					.setNewTime(getCurrentTime("yyyy-MM-dd HH:mm:ss"));
					healthFoodTitle.setTimestamp(getCurrentTime("yyyyMMddHHmmss"));
					healthFoodTitle.setTId(tId);
					healthFoodTitle.save();

					result.setContent(healthFoodTitle);
					result.setDescription("上传成功！");
					result.setResult("success");
				}catch(Exception e){
					result.setResult("failure");
					result.setDescription(e.toString());
				}

			}
		}else{
			result.setResult("failure");
			result.setDescription("name:"+name + " description:" + description + " tId:" + tId
					+ " uploadFile:" + uploadFile);
		}
		renderJson(result);
	}
	@Before(AdminUserInterceptor.class)
	@UserOperation(optName="修改一级分类操作",optStyle="更新")
	public void update() {
		UploadFile uploadFile = this.getFile("img","food_title");
		int id = getParaToInt("id");
		String timestamp = getPara("timestamp");
		XhTitle healthFoodTitle = XhTitle.dao
				.findByIdAndTimestamp(id, timestamp);
		ResultMsg<XhTitle> result=new ResultMsg<XhTitle>();
		try{
			if (healthFoodTitle != null) {
				String name = getPara("name");
				String description = getPara("description");


				String tId = getPara("tId");
				if (!healthFoodTitle.getTId().equals(tId))
					healthFoodTitle.setTId(tId);
				if (!healthFoodTitle.getName().equals(name))
					healthFoodTitle.setName(name);
				if (!healthFoodTitle.getDescription().equals(description))
					healthFoodTitle.setDescription(description);
				String imgname=healthFoodTitle.getImg();
				try{
					if(uploadFile!=null){

						// // 拼接文件上传的完整路径
						//					String fileName =title_path+ uploadFile.getFileName();

						if(!imgname.substring(imgname.lastIndexOf("/"), imgname.length()).equals(uploadFile.getFileName())){
							CommonUtil.uploadCardToOSS(StaticURL.XINE_HEALTH_BUCKETNAME,"images/"+title_path , uploadFile.getFileName(),uploadFile.getUploadPath()+"/");
							healthFoodTitle.setImg(StaticURL.ALIYUN_OSS_XINE_HEALTH +title_path+"/"+uploadFile.getFileName());
						}


					}

					healthFoodTitle
					.setNewTime(getCurrentTime("yyyy-MM-dd HH:mm:ss"));
					healthFoodTitle.setTimestamp(getCurrentTime("yyyyMMddHHmmss"));

					healthFoodTitle.update();

					result.setDescription("更改成功！");
					result.setResult("success");
				}catch(Exception e){
					result.setResult("failure");
					result.setDescription(e.toString());
				}
			}
		}catch(Exception e){
			result.setDescription(e.getMessage());
			result.setResult("failure");
		}
		renderJson(result);
	}
	@Before(AdminUserInterceptor.class)
	@UserOperation(optName="删除一级分类操作",optStyle="删除")
	public void delete() {

		int id = getParaToInt("id");
		ResultMsg<XhTitle> result=new ResultMsg<XhTitle>();
		System.out.println(id);
		try{
			XhTitle healthFoodTitle =XhTitle.dao.findById(id);
			healthFoodTitle.delete();
			result.setResult("success");
			result.setDescription("删除成功！");
		}catch(Exception e){
			result.setResult("failure");
			result.setDescription(e.toString());
		}
		// JsonBean<HealthFoodTitle> jsonBean=new JsonBean<HealthFoodTitle>();
		// jsonBean.setResult("success");
		// jsonBean.setMessage("成功删除");
		renderJson(result);
	}

	public void getJsonTreeData() {
		//UploadFile uploadFile = this.getFile("img","food_title");
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



	public String getCurrentTime(String format) {

		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date now = new Date();
		String dt = sdf.format(now);

		return dt;
	}

}
