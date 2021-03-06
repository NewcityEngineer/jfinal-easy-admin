package com.bdsht.xinehealth.model;

import java.util.List;

import com.bdsht.xinehealth.model.base.BaseXhLink;
import com.jfinal.plugin.activerecord.Page;

/**
 * Generated by JFinal.
 */
@SuppressWarnings("serial")
public class XhLink extends BaseXhLink<XhLink> {
	public static final XhLink dao = new XhLink();
	public List<XhLink> findAll(){

		return this.find("select * from xh_link");

	}


	public XhLink findById(int id){
		return this.findFirst("select * from xh_link where id=?",id);

	}
	public XhLink findByLink(String link){
		return this.findFirst("select * from xh_link where link=?",link);

	}

	public Page<XhLink> paginate(int pageNumber, int pageSize) {
		return paginate(pageNumber, pageSize, "select *", "from xh_link order by id asc");
	}


}
