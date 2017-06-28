package com.bdsht.xinehealth.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseNewsLatest<M extends BaseNewsLatest<M>> extends Model<M> implements IBean {

	public void setId(java.lang.Integer id) {
		set("id", id);
	}

	public java.lang.Integer getId() {
		return get("id");
	}

	public void setMediaId(java.lang.String mediaId) {
		set("media_id", mediaId);
	}

	public java.lang.String getMediaId() {
		return get("media_id");
	}

	public void setEnabled(java.lang.Boolean enabled) {
		set("enabled", enabled);
	}

	public java.lang.Boolean getEnabled() {
		return get("enabled");
	}

	public void setTimestamp(java.util.Date timestamp) {
		set("timestamp", timestamp);
	}

	public java.util.Date getTimestamp() {
		return get("timestamp");
	}

}
