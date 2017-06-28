package com.bdsht.xinehealth.model;

import java.util.List;

import com.bdsht.xinehealth.model.base.BaseXhJobFoodNews;
import com.jfinal.plugin.activerecord.Page;

/**
 * Generated by JFinal.
 */
@SuppressWarnings("serial")
public class XhJobFoodNews extends BaseXhJobFoodNews<XhJobFoodNews> {
	public static final XhJobFoodNews dao = new XhJobFoodNews();

	public XhJobFoodNews findByUrl(String newurl) {
		
		return this.findFirst("select * from xh_job_food_news where url=?",newurl);

	}
	
	public boolean existByUrl(String url){
		XhJobFoodNews xhJobFoodNews=this.findFirst("select * from xh_job_food_news where url=?",url);
		if(xhJobFoodNews==null){
			return false;
		}else{
			return true;
		}
	}
	
	public XhJobFoodNews findByTitle(String title) {
		return this.findFirst("select * from xh_job_food_news where title=?",title);
	}

	public List<XhJobFoodNews> listToday(String today) {
		return this.find("select * from xh_job_food_news where createTime like '%"+today+"%'");
	}
	
	public List<XhJobFoodNews> findAll(){

		return this.find("select * from xh_job_food_news");

	}

	/*public XhJobFoodNews findByIdAndTimestamp(int id,String timestamp){

		return this.findFirst("select * from xh_job_food_news where id=? and timestamp=?",id,timestamp);

	}*/

	public XhJobFoodNews findById(int id){
		return this.findFirst("select * from xh_job_food_news where id=?",id);

	}
	public XhJobFoodNews findByName(String name){
		return this.findFirst("select * from xh_job_food_news where name=?",name);

	}

	public Page<XhJobFoodNews> paginate(int pageNumber, int pageSize) {
		return paginate(pageNumber, pageSize, "select *", "from xh_job_food_news order by id asc");
	}

	
}
