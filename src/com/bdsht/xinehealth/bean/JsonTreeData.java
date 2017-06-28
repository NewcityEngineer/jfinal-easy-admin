package com.bdsht.xinehealth.bean;

import java.util.List;
import java.util.Map;

public class JsonTreeData {
	private String id; // json id
	private String pid; //
	private String text; // json 显示文本
	private String state; // json 'open','closed'
	private Map<String, Object> attributes;// 其他参数
	public List<JsonTreeData> children;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Map<String, Object> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

	public List<JsonTreeData> getChildren() {
		return children;
	}

	public void setChildren(List<JsonTreeData> children) {
		this.children = children;
	}
	

}
