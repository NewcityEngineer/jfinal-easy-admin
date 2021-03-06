package com.bdsht.xinehealth.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseUserCmd<M extends BaseUserCmd<M>> extends Model<M> implements IBean {

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

	public void setTextcontent(java.lang.String textcontent) {
		set("textcontent", textcontent);
	}

	public java.lang.String getTextcontent() {
		return get("textcontent");
	}

	public void setTimestamp(java.util.Date timestamp) {
		set("timestamp", timestamp);
	}

	public java.util.Date getTimestamp() {
		return get("timestamp");
	}

	public void setMessagetype(java.lang.String messagetype) {
		set("messagetype", messagetype);
	}

	public java.lang.String getMessagetype() {
		return get("messagetype");
	}

	public void setSendstatus1(java.lang.Integer sendstatus1) {
		set("sendstatus1", sendstatus1);
	}

	public java.lang.Integer getSendstatus1() {
		return get("sendstatus1");
	}

	public void setSendstatus2(java.lang.Integer sendstatus2) {
		set("sendstatus2", sendstatus2);
	}

	public java.lang.Integer getSendstatus2() {
		return get("sendstatus2");
	}

	public void setUpdatetime(java.util.Date updatetime) {
		set("updatetime", updatetime);
	}

	public java.util.Date getUpdatetime() {
		return get("updatetime");
	}

	public void setNickname(java.lang.String nickname) {
		set("nickname", nickname);
	}

	public java.lang.String getNickname() {
		return get("nickname");
	}

}
