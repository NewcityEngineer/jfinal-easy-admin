package com.bdsht.xinehealth.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseXhIngredients<M extends BaseXhIngredients<M>> extends Model<M> implements IBean {

	public void setId(java.lang.Integer id) {
		set("id", id);
	}

	public java.lang.Integer getId() {
		return get("id");
	}

	public void setIngreId(java.lang.String ingreId) {
		set("ingreId", ingreId);
	}

	public java.lang.String getIngreId() {
		return get("ingreId");
	}

	public void setFoodStyleSid(java.lang.String foodStyleSid) {
		set("food_style_sId", foodStyleSid);
	}

	public java.lang.String getFoodStyleSid() {
		return get("food_style_sId");
	}

	public void setName(java.lang.String name) {
		set("name", name);
	}

	public java.lang.String getName() {
		return get("name");
	}

	public void setFunction(java.lang.String function) {
		set("function", function);
	}

	public java.lang.String getFunction() {
		return get("function");
	}

	public void setDescription(java.lang.String description) {
		set("description", description);
	}

	public java.lang.String getDescription() {
		return get("description");
	}

	public void setPurchase(java.lang.String purchase) {
		set("purchase", purchase);
	}

	public java.lang.String getPurchase() {
		return get("purchase");
	}

	public void setTropism(java.lang.String tropism) {
		set("tropism", tropism);
	}

	public java.lang.String getTropism() {
		return get("tropism");
	}

	public void setEffect(java.lang.String effect) {
		set("effect", effect);
	}

	public java.lang.String getEffect() {
		return get("effect");
	}

	public void setSuitPeople(java.lang.String suitPeople) {
		set("suitPeople", suitPeople);
	}

	public java.lang.String getSuitPeople() {
		return get("suitPeople");
	}

	public void setNotSuitPeople(java.lang.String notSuitPeople) {
		set("notSuitPeople", notSuitPeople);
	}

	public java.lang.String getNotSuitPeople() {
		return get("notSuitPeople");
	}

	public void setSuitWith(java.lang.String suitWith) {
		set("suitWith", suitWith);
	}

	public java.lang.String getSuitWith() {
		return get("suitWith");
	}

	public void setNotSuitWith(java.lang.String notSuitWith) {
		set("notSuitWith", notSuitWith);
	}

	public java.lang.String getNotSuitWith() {
		return get("notSuitWith");
	}

	public void setFoodLawTaboo(java.lang.String foodLawTaboo) {
		set("foodLawTaboo", foodLawTaboo);
	}

	public java.lang.String getFoodLawTaboo() {
		return get("foodLawTaboo");
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

	public void setSuitSeason(java.lang.String suitSeason) {
		set("suitSeason", suitSeason);
	}

	public java.lang.String getSuitSeason() {
		return get("suitSeason");
	}

	public void setNotSuitSeason(java.lang.String notSuitSeason) {
		set("notSuitSeason", notSuitSeason);
	}

	public java.lang.String getNotSuitSeason() {
		return get("notSuitSeason");
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