package com.bdsht.xinehealth.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseXhUserInfo<M extends BaseXhUserInfo<M>> extends Model<M> implements IBean {

	public void setId(java.lang.Integer id) {
		set("id", id);
	}

	public java.lang.Integer getId() {
		return get("id");
	}

	public void setOpenid(java.lang.String openid) {
		set("openid", openid);
	}

	public java.lang.String getOpenid() {
		return get("openid");
	}

	public void setUnionid(java.lang.String unionid) {
		set("unionid", unionid);
	}

	public java.lang.String getUnionid() {
		return get("unionid");
	}

	public void setNickname(java.lang.String nickname) {
		set("nickname", nickname);
	}

	public java.lang.String getNickname() {
		return get("nickname");
	}

	public void setImgurl(java.lang.String imgurl) {
		set("imgurl", imgurl);
	}

	public java.lang.String getImgurl() {
		return get("imgurl");
	}

	public void setProvince(java.lang.String province) {
		set("province", province);
	}

	public java.lang.String getProvince() {
		return get("province");
	}

	public void setSex(java.lang.String sex) {
		set("sex", sex);
	}

	public java.lang.String getSex() {
		return get("sex");
	}

	public void setCity(java.lang.String city) {
		set("city", city);
	}

	public java.lang.String getCity() {
		return get("city");
	}

	public void setCountry(java.lang.String country) {
		set("country", country);
	}

	public java.lang.String getCountry() {
		return get("country");
	}

	public void setName(java.lang.String name) {
		set("name", name);
	}

	public java.lang.String getName() {
		return get("name");
	}

	public void setTelephone(java.lang.String telephone) {
		set("telephone", telephone);
	}

	public java.lang.String getTelephone() {
		return get("telephone");
	}

	public void setHeight(java.lang.String height) {
		set("height", height);
	}

	public java.lang.String getHeight() {
		return get("height");
	}

	public void setWeight(java.lang.String weight) {
		set("weight", weight);
	}

	public java.lang.String getWeight() {
		return get("weight");
	}

}