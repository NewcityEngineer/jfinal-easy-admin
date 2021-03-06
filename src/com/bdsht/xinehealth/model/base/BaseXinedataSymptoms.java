package com.bdsht.xinehealth.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseXinedataSymptoms<M extends BaseXinedataSymptoms<M>> extends Model<M> implements IBean {

	public void setId(java.lang.Integer id) {
		set("id", id);
	}

	public java.lang.Integer getId() {
		return get("id");
	}

	public void setName(java.lang.String name) {
		set("name", name);
	}

	public java.lang.String getName() {
		return get("name");
	}

	public void setLevel(java.lang.String level) {
		set("level", level);
	}

	public java.lang.String getLevel() {
		return get("level");
	}

	public void setIntroduction(java.lang.String introduction) {
		set("introduction", introduction);
	}

	public java.lang.String getIntroduction() {
		return get("introduction");
	}

	public void setStyle(java.lang.String style) {
		set("style", style);
	}

	public java.lang.String getStyle() {
		return get("style");
	}

	public void setPerform(java.lang.String perform) {
		set("perform", perform);
	}

	public java.lang.String getPerform() {
		return get("perform");
	}

	public void setReason(java.lang.String reason) {
		set("reason", reason);
	}

	public java.lang.String getReason() {
		return get("reason");
	}

	public void setLife(java.lang.String life) {
		set("life", life);
	}

	public java.lang.String getLife() {
		return get("life");
	}

	public void setDiet(java.lang.String diet) {
		set("diet", diet);
	}

	public java.lang.String getDiet() {
		return get("diet");
	}

	public void setBetter(java.lang.String better) {
		set("better", better);
	}

	public java.lang.String getBetter() {
		return get("better");
	}

	public void setWorse(java.lang.String worse) {
		set("worse", worse);
	}

	public java.lang.String getWorse() {
		return get("worse");
	}

	public void setFoodname(java.lang.String foodname) {
		set("foodname", foodname);
	}

	public java.lang.String getFoodname() {
		return get("foodname");
	}

	public void setMaterial(java.lang.String material) {
		set("material", material);
	}

	public java.lang.String getMaterial() {
		return get("material");
	}

	public void setSeasoning(java.lang.String seasoning) {
		set("seasoning", seasoning);
	}

	public java.lang.String getSeasoning() {
		return get("seasoning");
	}

	public void setMethod(java.lang.String method) {
		set("method", method);
	}

	public java.lang.String getMethod() {
		return get("method");
	}

	public void setEffect(java.lang.String effect) {
		set("effect", effect);
	}

	public java.lang.String getEffect() {
		return get("effect");
	}

}
