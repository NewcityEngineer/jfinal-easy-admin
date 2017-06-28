package com.bdsht.xinehealth.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseXhStyle<M extends BaseXhStyle<M>> extends Model<M> implements IBean {

	public void setId(java.lang.Integer id) {
		set("id", id);
	}

	public java.lang.Integer getId() {
		return get("id");
	}

	public void setSId(java.lang.String sId) {
		set("sId", sId);
	}

	public java.lang.String getSId() {
		return get("sId");
	}

	public void setFoodTitleTid(java.lang.String foodTitleTid) {
		set("food_title_tId", foodTitleTid);
	}

	public java.lang.String getFoodTitleTid() {
		return get("food_title_tId");
	}

	public void setName(java.lang.String name) {
		set("name", name);
	}

	public java.lang.String getName() {
		return get("name");
	}

	public void setDescription(java.lang.String description) {
		set("description", description);
	}

	public java.lang.String getDescription() {
		return get("description");
	}

	public void setRule(java.lang.String rule) {
		set("rule", rule);
	}

	public java.lang.String getRule() {
		return get("rule");
	}

	public void setElement(java.lang.String element) {
		set("element", element);
	}

	public java.lang.String getElement() {
		return get("element");
	}

	public void setMain(java.lang.String main) {
		set("main", main);
	}

	public java.lang.String getMain() {
		return get("main");
	}

	public void setOther(java.lang.String other) {
		set("other", other);
	}

	public java.lang.String getOther() {
		return get("other");
	}

	public void setPrescription(java.lang.String prescription) {
		set("prescription", prescription);
	}

	public java.lang.String getPrescription() {
		return get("prescription");
	}

	public void setImg(java.lang.String img) {
		set("img", img);
	}

	public java.lang.String getImg() {
		return get("img");
	}

	public void setOldTime(java.lang.String oldTime) {
		set("oldTime", oldTime);
	}

	public java.lang.String getOldTime() {
		return get("oldTime");
	}

	public void setNewTime(java.lang.String newTime) {
		set("newTime", newTime);
	}

	public java.lang.String getNewTime() {
		return get("newTime");
	}

	public void setTimestamp(java.lang.String timestamp) {
		set("timestamp", timestamp);
	}

	public java.lang.String getTimestamp() {
		return get("timestamp");
	}

}