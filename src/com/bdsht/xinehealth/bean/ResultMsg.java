package com.bdsht.xinehealth.bean;

public class ResultMsg<T> {
	private String result;
	private String description;
	private T content;

	public ResultMsg() {
		super();
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public T getContent() {
		return content;
	}

	public void setContent(T content) {
		this.content = content;
	}

}
