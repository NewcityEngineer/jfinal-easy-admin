package com.bdsht.xinehealth.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseXhClassicrecipes<M extends BaseXhClassicrecipes<M>> extends Model<M> implements IBean {

	public void setId(java.lang.Integer id) {
		set("id", id);
	}

	public java.lang.Integer getId() {
		return get("id");
	}

	public void setCrsId(java.lang.String crsId) {
		set("crsId", crsId);
	}

	public java.lang.String getCrsId() {
		return get("crsId");
	}

	public void setFoodStyleIngreid(java.lang.String foodStyleIngreid) {
		set("food_style_ingreId", foodStyleIngreid);
	}

	public java.lang.String getFoodStyleIngreid() {
		return get("food_style_ingreId");
	}

	public void setTitle(java.lang.String title) {
		set("title", title);
	}

	public java.lang.String getTitle() {
		return get("title");
	}

	public void setMain(java.lang.String main) {
		set("main", main);
	}

	public java.lang.String getMain() {
		return get("main");
	}

	public void setEffect(java.lang.String effect) {
		set("effect", effect);
	}

	public java.lang.String getEffect() {
		return get("effect");
	}

	public void setXhRecipeId(java.lang.Integer xhRecipeId) {
		set("xhRecipeId", xhRecipeId);
	}

	public java.lang.Integer getXhRecipeId() {
		return get("xhRecipeId");
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
