/**
 * Copyright (c) 2015-2016, Javen Zhou  (javenlife@126.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */

package com.bdsht.xinehealth.controller;

import com.jfinal.core.Controller;

/**
 * @author Javen
 * 2016年1月15日
 */
public class IndexController extends Controller {
	public void index(){
		setAttr("test", "这里是测试...");
		render("index.html");
	}
}
