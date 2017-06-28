package com.bdsht.xinehealth.interceptor;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdsht.xinehealth.model.XhAdminOptlog;
import com.bdsht.xinehealth.util.CommonUtil;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.kit.StrKit;
import com.jfinal.weixin.sdk.kit.IpKit;

public class AdminUserInterceptor implements Interceptor {


	private static Logger logger = LoggerFactory.getLogger(AdminUserInterceptor.class);

	private ThreadLocal<XhAdminOptlog> tlocal = new ThreadLocal<XhAdminOptlog>();
	@Override
	public void intercept(Invocation inv) {
		try {

			long beginTime = System.currentTimeMillis();

			// 接收到请求，记录请求内容
			//			ServletRequestAttributes attributes = (ServletRequestAttributes) joinPoint.getArgs();
			HttpServletRequest request = inv.getController().getRequest();

			String beanName = inv.getControllerKey();
			String methodName = inv.getMethodName();
			String uri = request.getRequestURI();
			String remoteAddr = IpKit.getRealIp(request);
			String sessionId = request.getSession().getId();
			String userName = (String) request.getSession().getAttribute("name");
			if(!StrKit.isBlank(userName)){
				System.out.println("userName:"+userName);
				XhAdminOptlog optLog = new XhAdminOptlog();

				optLog.setBeanName(beanName);
				optLog.setUserName(userName);
				optLog.setMethodName(methodName);
				optLog.setRemoteAddr(remoteAddr);
				optLog.setSessionId(sessionId);
				optLog.setUri(uri);
				Method methodSignature = inv.getMethod();
				UserOperation action = methodSignature.getAnnotation(UserOperation.class);
				optLog.setOptStyle(action.optStyle());
				optLog.setOptName(action.optName());
				
				optLog.setOptObjId(inv.getController().getPara("id"));
				
				optLog.setRequestTime(beginTime+"");
				optLog.setActionName(inv.getActionKey());
				optLog.setCreateTime(CommonUtil.getCurrentTime("yyyy-MM-dd HH:mm:ss"));
				
				
				
				
				tlocal.set(optLog);
				optLog.save();
				inv.invoke();
			}else{
				inv.getController().renderJson("未登录，请进入首页登录！");
			}

			//			MethodSignature signature = (MethodSignature) inv.getArgs().;
			//			Method methodSignature = signature.getMethod();
			//
			//			UserOperation action = methodSignature.getAnnotation(UserOperation.class);
			//
			//			optLog.setActionName(action.name());


			//String optStyle = action.optStyle();

		} catch (Exception e) {
			logger.error("***操作请求日志记录失败doBefore()***");// , e
			System.out.println(e);
		}



	}

}
