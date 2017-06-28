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
import com.bdsht.xinehealth.model.XhStyle;
import com.bdsht.xinehealth.util.CommonUtil;
import com.bdsht.xinehealth.util.StaticURL;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.upload.UploadFile;

public class XhStyleController extends Controller {
	String style_path=StaticURL.FOOD_STYLE;

	
	public void listByTitleId(){
		
		int titleId=getParaToInt("titleId");
		List<XhStyle> xhStyles=XhStyle.dao.findByTitleId(titleId);
		renderJson(xhStyles);
	}
	
	public void listAll(){
		
		renderJson(XhStyle.dao.findAll());
		
	}
	public void all() {
		//
		String page=getPara("page");
		String rows=getPara("rows");
		Page<XhStyle> healthFoodStyles;
		if(page==null||rows==null){
			healthFoodStyles = XhStyle.dao.paginate(1, 10);
			//renderJson(healthFoodTitles);
		}else{
			healthFoodStyles = XhStyle.dao.paginate(Integer.parseInt(page), Integer.parseInt(rows));

		}

		EasyuiPageBean<XhStyle> easyuiPageBean=new EasyuiPageBean<XhStyle>();
		easyuiPageBean.setTotal(XhStyle.dao.findAll().size());
		easyuiPageBean.setRows(healthFoodStyles.getList());
		renderJson(easyuiPageBean);
	}
	@Before(AdminUserInterceptor.class)
	@UserOperation(optName="增加二级分类操作",optStyle="增加")
	public void add() {
		UploadFile uploadFile = this.getFile("img","food_style");
		String sId = getPara("sId");
		String food_title_tId= getPara("food_title_tId");
		String name = getPara("name");
		String description = getPara("description");
		String rule = getPara("rule");
		String element = getPara("element");
		String main = getPara("main");
		String other = getPara("other");
		String prescription = getPara("prescription");
		// // 拼接文件上传的完整路径
		ResultMsg<XhStyle> result=new ResultMsg<XhStyle>();

		if (!StrKit.isBlank(sId) && !StrKit.isBlank(name)&&!StrKit.isBlank(food_title_tId)&& uploadFile != null) {
			if (XhStyle.dao.findByName(name) == null) {
				XhStyle healthFoodStyle = new XhStyle();
				healthFoodStyle.setSId(sId);
				healthFoodStyle.setFoodTitleTid(food_title_tId);
				healthFoodStyle.setName(name);
				healthFoodStyle.setDescription(description);
				healthFoodStyle.setRule(rule);
				healthFoodStyle.setElement(element);
				healthFoodStyle.setMain(main);
				healthFoodStyle.setOther(other);
				healthFoodStyle.setPrescription(prescription);
				try{

					CommonUtil.uploadCardToOSS(StaticURL.XINE_HEALTH_BUCKETNAME,"images/"+style_path , uploadFile.getFileName(),uploadFile.getUploadPath()+"/");
					String fileName =uploadFile.getFileName();
					healthFoodStyle.setImg(StaticURL.ALIYUN_OSS_XINE_HEALTH +style_path+"/"+fileName);


					//				String fileName = style_path+ uploadFile.getFileName();
					//				healthFoodStyle.setImg(fileName);

					healthFoodStyle.setOldTime(getCurrentTime("yyyy-MM-dd HH:mm:ss"));
					healthFoodStyle.setNewTime(getCurrentTime("yyyy-MM-dd HH:mm:ss"));
					healthFoodStyle.setTimestamp(getCurrentTime("yyyyMMddHHmmss"));
					healthFoodStyle.save();

					result.setResult("success");
					result.setDescription("上传成功！");
					result.setContent(healthFoodStyle);
				}catch(Exception e){
					result.setResult("failure");
					result.setDescription(e.toString());
				}


			}
		}else{
			result.setResult("failurea");
			result.setDescription("上传失败！");
			StringBuffer sb=new StringBuffer();
			if(StrKit.isBlank(sId)){
				sb.append("二级分类编码为空！");
			}else if(StrKit.isBlank(name)){
				sb.append("二级分类名称为空！");
			}else if(StrKit.isBlank(food_title_tId)){
				sb.append("一级分类名称未选择！");
			}else if(uploadFile==null){
				sb.append("图片未上传！");
			}
			result.setDescription(sb.toString());
		}

		renderJson(result);
	}
	@Before(AdminUserInterceptor.class)
	@UserOperation(optName="修改二级分类操作",optStyle="更新")
	public void update() {
		UploadFile uploadFile = this.getFile("img","food_style");
		int id = getParaToInt("id");
		System.out.println(id);
		String timestamp = getPara("timestamp");
		ResultMsg<XhStyle> result=new ResultMsg<XhStyle>();
		XhStyle healthFoodStyle = XhStyle.dao.findByIdAndTimestamp(id, timestamp);
		if (healthFoodStyle != null) {

			String sId = getPara("sId");
			String food_title_tId= getPara("food_title_tId");
			String name = getPara("name");
			String description = getPara("description");
			String rule = getPara("rule");
			String element = getPara("element");
			String main = getPara("main");
			String other = getPara("other");
			String prescription = getPara("prescription");
			// // 拼接文件上传的完整路径

			healthFoodStyle.setSId(sId);
			healthFoodStyle.setFoodTitleTid(food_title_tId);
			healthFoodStyle.setName(name);
			healthFoodStyle.setDescription(description);
			healthFoodStyle.setRule(rule);
			healthFoodStyle.setElement(element);
			healthFoodStyle.setMain(main);
			healthFoodStyle.setOther(other);
			healthFoodStyle.setPrescription(prescription);
			try{
				String imgname=healthFoodStyle.getImg();
			
				if(!imgname.substring(imgname.lastIndexOf("/"), imgname.length()).equals(uploadFile.getFileName())){
					//				healthFoodStyle.setImg(fileName);
					CommonUtil.uploadCardToOSS(StaticURL.XINE_HEALTH_BUCKETNAME,"images/"+style_path , uploadFile.getFileName(),uploadFile.getUploadPath()+"/");
					healthFoodStyle.setImg(StaticURL.ALIYUN_OSS_XINE_HEALTH +style_path+"/"+uploadFile.getFileName());

				}
				healthFoodStyle.setNewTime(getCurrentTime("yyyy-MM-dd HH:mm:ss"));
				healthFoodStyle.setTimestamp(getCurrentTime("yyyyMMddHHmmss"));
				healthFoodStyle.update();
				result.setResult("success");
				result.setContent(healthFoodStyle);
				result.setDescription("更改成功！");
			}catch(Exception e){
				result.setResult("failure");
				result.setDescription(e.toString());
			}


		}

		renderJson(result);
	}
	@Before(AdminUserInterceptor.class)
	@UserOperation(optName="删除二级分类操作",optStyle="删除")
	public void delete() {
		ResultMsg<XhStyle> result=new ResultMsg<XhStyle>();
		try{
			int id = getParaToInt("id");
			System.out.println(id);
			XhStyle healthFoodTitle = XhStyle.dao.findById(id);
			healthFoodTitle.delete();

			result.setResult("success");	
			result.setDescription("删除成功！");
			result.setContent(healthFoodTitle);
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
		List<XhStyle> resultList = XhStyle.dao.findAll();
		List<JsonTreeData> treeDataList = new ArrayList<JsonTreeData>();

		for (XhStyle healthFoodStyle : resultList) {
			JsonTreeData treeData = new JsonTreeData();

			treeData.setId(String.valueOf(healthFoodStyle.getId()));
			treeData.setText(healthFoodStyle.getName());
			treeData.setState("closed");


			treeDataList.add(treeData);
		}

		renderJson(treeDataList);
	}
}
