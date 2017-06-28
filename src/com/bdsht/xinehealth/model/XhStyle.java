package com.bdsht.xinehealth.model;

import java.util.List;

import com.bdsht.xinehealth.model.base.BaseXhStyle;
import com.jfinal.plugin.activerecord.Page;

/**
 * Generated by JFinal.
 */
@SuppressWarnings("serial")
public class XhStyle extends BaseXhStyle<XhStyle> {
	public static final XhStyle dao = new XhStyle();
	
	public List<XhStyle> findAll(){

		return this.find("select * from xh_style");

	}

	public XhStyle findByIdAndTimestamp(int id,String timestamp){

		return this.findFirst("select * from xh_style where id=? and timestamp=?",id,timestamp);

	}

	public XhStyle findById(int id){
		return this.findFirst("select * from xh_style where id=?",id);

	}
	public XhStyle findByName(String name){
		return this.findFirst("select * from xh_style where name=?",name);

	}

	public Page<XhStyle> paginate(int pageNumber, int pageSize) {
		return paginate(pageNumber, pageSize, "select *", "from xh_style order by id asc");
	}

	public List<XhStyle> findByTitleId(int titleId) {
		return this.find("select * from xh_style where food_title_tId=?",titleId);
	}
}
