package com.bdsht.xinehealth.routes;

import com.bdsht.xinehealth.controller.XhAdminFrontController;
import com.bdsht.xinehealth.controller.XhAdminUserController;
import com.bdsht.xinehealth.controller.XhAdminUserOptlogController;
import com.bdsht.xinehealth.controller.XhClassicRecipesController;
import com.bdsht.xinehealth.controller.XhFoodController;
import com.bdsht.xinehealth.controller.XhIngredientsController;
import com.bdsht.xinehealth.controller.XhJobFoodNewsController;
import com.bdsht.xinehealth.controller.XhJobLinkController;
import com.bdsht.xinehealth.controller.XhLinkController;
import com.bdsht.xinehealth.controller.XhNutritionalController;
import com.bdsht.xinehealth.controller.XhRecipeController;
import com.bdsht.xinehealth.controller.XhStyleController;
import com.bdsht.xinehealth.controller.XhSymptomController;
import com.bdsht.xinehealth.controller.XhTitleController;
import com.bdsht.xinehealth.controller.XhUserCollectionController;
import com.bdsht.xinehealth.controller.XhUserCommentController;
import com.bdsht.xinehealth.controller.XhUserHealthInfoController;
import com.bdsht.xinehealth.controller.XhUserInfoController;
import com.bdsht.xinehealth.controller.XhUserSharingController;
import com.jfinal.config.Routes;

public class XhRoutes extends Routes {

	@Override
	public void config() {
		//心e健康管理后台前端路由
		this.add("xhAdmin",XhAdminFrontController.class,"xhAdmin");
		//心e健康基础数据
		this.add("/xhTitle",XhTitleController.class);
		this.add("/xhStyle",XhStyleController.class);
		this.add("/xhRecipe",XhRecipeController.class);
		this.add("/xhIngredients",XhIngredientsController.class);
		this.add("/xhNutritional",XhNutritionalController.class);
		this.add("/xhClassicRecipes",XhClassicRecipesController.class);
	
		//食物相生相克
		this.add("/xhFood",XhFoodController.class);
		this.add("/xhSymptom",XhSymptomController.class);
		
		//网路爬虫数据管理
		this.add("/xhJobFoodNews",XhJobFoodNewsController.class);
		this.add("/xhJobLink",XhJobLinkController.class);
		//网站常用链接
		this.add("/xhLink",XhLinkController.class);
		//管理员信息
		this.add("/xhAdminUser",XhAdminUserController.class);
		this.add("/xhAdminUserOptlog",XhAdminUserOptlogController.class);
		
		this.add("/xhUserInfo",XhUserInfoController.class);
		this.add("/xhUserHealthInfo",XhUserHealthInfoController.class);
		this.add("/xhUserSharing",XhUserSharingController.class);
		this.add("/xhUserComment",XhUserCommentController.class);
		this.add("/xhUserCollection",XhUserCollectionController.class);
		
		
	}

}
