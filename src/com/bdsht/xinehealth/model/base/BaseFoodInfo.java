package com.bdsht.xinehealth.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseFoodInfo<M extends BaseFoodInfo<M>> extends Model<M> implements IBean {

	public void setId(java.lang.Integer id) {
		set("id", id);
	}

	public java.lang.Integer getId() {
		return get("id");
	}

	public void setSpecies(java.lang.String species) {
		set("species", species);
	}

	public java.lang.String getSpecies() {
		return get("species");
	}

	public void setName(java.lang.String name) {
		set("name", name);
	}

	public java.lang.String getName() {
		return get("name");
	}

	public void setEffect(java.lang.String effect) {
		set("effect", effect);
	}

	public java.lang.String getEffect() {
		return get("effect");
	}

	public void setSuitable(java.lang.String suitable) {
		set("suitable", suitable);
	}

	public java.lang.String getSuitable() {
		return get("suitable");
	}

	public void setUnsuited(java.lang.String unsuited) {
		set("unsuited", unsuited);
	}

	public java.lang.String getUnsuited() {
		return get("unsuited");
	}

	public void setChoose(java.lang.String choose) {
		set("choose", choose);
	}

	public java.lang.String getChoose() {
		return get("choose");
	}

	public void setStore(java.lang.String store) {
		set("store", store);
	}

	public java.lang.String getStore() {
		return get("store");
	}

	public void setCook(java.lang.String cook) {
		set("cook", cook);
	}

	public java.lang.String getCook() {
		return get("cook");
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

	public void setHealth(java.lang.String health) {
		set("health", health);
	}

	public java.lang.String getHealth() {
		return get("health");
	}

	public void setOthername(java.lang.String othername) {
		set("othername", othername);
	}

	public java.lang.String getOthername() {
		return get("othername");
	}

}