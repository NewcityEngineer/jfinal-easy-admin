/**
 * Copyright (c) 2015-2016, Javen Zhou  (javenlife@126.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.jfinal.weixin.template;

public class DataItem {
	private TempItem first;
	private TempItem product;
	private TempItem price;
	private TempItem time;
	private TempItem remark;
	public TempItem getFirst() {
		return first;
	}
	public void setFirst(TempItem first) {
		this.first = first;
	}
	public TempItem getProduct() {
		return product;
	}
	public void setProduct(TempItem product) {
		this.product = product;
	}
	public TempItem getPrice() {
		return price;
	}
	public void setPrice(TempItem price) {
		this.price = price;
	}
	public TempItem getTime() {
		return time;
	}
	public void setTime(TempItem time) {
		this.time = time;
	}
	public TempItem getRemark() {
		return remark;
	}
	public void setRemark(TempItem remark) {
		this.remark = remark;
	}
	
	
}
