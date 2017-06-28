package com.bdsht.xinehealth.bean;

import java.util.List;

import com.bdsht.xinehealth.model.XhClassicrecipes;
import com.bdsht.xinehealth.model.XhIngredients;
import com.bdsht.xinehealth.model.XhNutritional;

public class XhIngredientsBean {

	public XhIngredients xhIngredientsBase;
	public XhNutritional xhNutritional;
	public List<XhClassicrecipes> xhClassicrecipesList;

	
	public XhIngredients getXhIngredientsBase() {
		return xhIngredientsBase;
	}
	public void setXhIngredientsBase(XhIngredients xhIngredientsBase) {
		this.xhIngredientsBase = xhIngredientsBase;
	}
	public XhNutritional getXhNutritional() {
		return xhNutritional;
	}
	public void setXhNutritional(XhNutritional xhNutritional) {
		this.xhNutritional = xhNutritional;
	}
	public List<XhClassicrecipes> getXhClassicrecipesList() {
		return xhClassicrecipesList;
	}
	public void setXhClassicrecipesList(List<XhClassicrecipes> xhClassicrecipesList) {
		this.xhClassicrecipesList = xhClassicrecipesList;
	}
	
	
}
