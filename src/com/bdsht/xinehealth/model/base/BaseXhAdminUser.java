package com.bdsht.xinehealth.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseXhAdminUser<M extends BaseXhAdminUser<M>> extends Model<M> implements IBean {

	public void setId(java.lang.Integer id) {
		set("id", id);
	}

	public java.lang.Integer getId() {
		return get("id");
	}

	public void setUserId(java.lang.String userId) {
		set("userId", userId);
	}

	public java.lang.String getUserId() {
		return get("userId");
	}

	public void setUserPwd(java.lang.String userPwd) {
		set("userPwd", userPwd);
	}

	public java.lang.String getUserPwd() {
		return get("userPwd");
	}

	public void setName(java.lang.String name) {
		set("name", name);
	}

	public java.lang.String getName() {
		return get("name");
	}

	public void setCreateTime(java.lang.String createTime) {
		set("createTime", createTime);
	}

	public java.lang.String getCreateTime() {
		return get("createTime");
	}

	public void setUpdateTime(java.lang.String updateTime) {
		set("updateTime", updateTime);
	}

	public java.lang.String getUpdateTime() {
		return get("updateTime");
	}

	public void setTimestamp(java.lang.String timestamp) {
		set("timestamp", timestamp);
	}

	public java.lang.String getTimestamp() {
		return get("timestamp");
	}

}
