package com.bdsht.xinehealth.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseXhNutritional<M extends BaseXhNutritional<M>> extends Model<M> implements IBean {

	public void setId(java.lang.Integer id) {
		set("id", id);
	}

	public java.lang.Integer getId() {
		return get("id");
	}

	public void setFoodStyleIngreid(java.lang.String foodStyleIngreid) {
		set("food_style_ingreId", foodStyleIngreid);
	}

	public java.lang.String getFoodStyleIngreid() {
		return get("food_style_ingreId");
	}

	public void setHeat(java.lang.String heat) {
		set("heat", heat);
	}

	public java.lang.String getHeat() {
		return get("heat");
	}

	public void setProtein(java.lang.String protein) {
		set("protein", protein);
	}

	public java.lang.String getProtein() {
		return get("protein");
	}

	public void setFat(java.lang.String fat) {
		set("fat", fat);
	}

	public java.lang.String getFat() {
		return get("fat");
	}

	public void setCarbohydrates(java.lang.String carbohydrates) {
		set("carbohydrates", carbohydrates);
	}

	public java.lang.String getCarbohydrates() {
		return get("carbohydrates");
	}

	public void setVitaminB1(java.lang.String vitaminB1) {
		set("vitaminB1", vitaminB1);
	}

	public java.lang.String getVitaminB1() {
		return get("vitaminB1");
	}

	public void setVitaminB2(java.lang.String vitaminB2) {
		set("vitaminB2", vitaminB2);
	}

	public java.lang.String getVitaminB2() {
		return get("vitaminB2");
	}

	public void setVitaminE(java.lang.String vitaminE) {
		set("vitaminE", vitaminE);
	}

	public java.lang.String getVitaminE() {
		return get("vitaminE");
	}

	public void setCalcium(java.lang.String calcium) {
		set("calcium", calcium);
	}

	public java.lang.String getCalcium() {
		return get("calcium");
	}

	public void setPotassium(java.lang.String potassium) {
		set("potassium", potassium);
	}

	public java.lang.String getPotassium() {
		return get("potassium");
	}

	public void setPhosphorus(java.lang.String phosphorus) {
		set("phosphorus", phosphorus);
	}

	public java.lang.String getPhosphorus() {
		return get("phosphorus");
	}

	public void setSodium(java.lang.String sodium) {
		set("sodium", sodium);
	}

	public java.lang.String getSodium() {
		return get("sodium");
	}

	public void setMagnesium(java.lang.String magnesium) {
		set("magnesium", magnesium);
	}

	public java.lang.String getMagnesium() {
		return get("magnesium");
	}

	public void setIron(java.lang.String iron) {
		set("iron", iron);
	}

	public java.lang.String getIron() {
		return get("iron");
	}

	public void setZinc(java.lang.String zinc) {
		set("zinc", zinc);
	}

	public java.lang.String getZinc() {
		return get("zinc");
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