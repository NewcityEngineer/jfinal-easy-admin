package com.bdsht.xinehealth.task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.bdsht.xinehealth.common.ITask;
import com.bdsht.xinehealth.model.XhJobFoodNews;
import com.bdsht.xinehealth.util.CommonUtil;
import com.bdsht.xinehealth.util.HttpClientUtil;

public class CfsnTask implements ITask {

	@Override
	public void run() {
		System.out.println(">>>>>>>>>>>>>>>>中国食品安全网开始>>>>>>>>>>>>>>>");
		try {
			getNews("http://www.cfsn.cn/");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void stop() {
		System.out.println(">>>>>>>>>>>>>>>>中国食品安全网结束>>>>>>>>>>>>>>>>");
	}

	public static void main(String[] args) throws Exception {
//		String result = HttpClientUtil.QQHttpRequest("http://www.cfsn.cn/");
//		Document doc = Jsoup.parse(result);
//	
//		Elements elements = doc.select("div.dey4");
//		for (int i = 0; i < elements.size(); i++) {
//			Element element=elements.get(i);
//			if(element.text().contains("曝光")){
//				Elements infoElements=element.select("div ul a");
//				for (int j = 0; j < infoElements.size(); j++) {
//					String title=infoElements.get(j).text();
//					String info=infoElements.get(j).toString();
//					int i_start=info.indexOf("href=")+6;
//					int i_end=info.indexOf("target")-2;
//					info="http://www.cfsn.cn/"+info.substring(i_start, i_end);
//					System.out.println(title);
//					System.out.println(info);
//				}
//			}
//			
//		}
		getNews("http://www.cfsn.cn/");
	}

	public static void getNews(String url) throws ParseException {

		String result = HttpClientUtil.QQHttpRequest(url);
		Document doc = Jsoup.parse(result);
	
		Elements elements = doc.select("div.dey4");
		for (int i = 0; i < elements.size(); i++) {
			Element element=elements.get(i);
			if(element.text().contains("曝光")){
				Elements infoElements=element.select("div ul a");
				for (int j = 0; j < 6; j++) {
					String title=infoElements.get(j).text();
					String link=infoElements.get(j).toString();
					int i_start=link.indexOf("href=")+6;
					int i_end=link.indexOf("target")-2;
					link="http://www.cfsn.cn/"+link.substring(i_start, i_end);
				
					
					if (XhJobFoodNews.dao.findByUrl(link)==null&&XhJobFoodNews.dao.findByTitle(title)==null) {
						try {
							XhJobFoodNews news = new XhJobFoodNews();
							news.setTitle(title);
							news.setCategory(CommonUtil.getFoodNewsCatagory(title));
							news.setUrl(link);

							Document newsDoc = Jsoup.parse(HttpClientUtil.QQHttpRequest(link));
							String sourceAndTime=newsDoc.select("div.xw1f").text();
							
							int s_start=sourceAndTime.indexOf("来源：");
							int s_end=sourceAndTime.indexOf("作者");
							String source = sourceAndTime.substring(s_start, s_end).replaceAll("来源：", "").replaceAll("　 　 ", "").replace("　　", "");
							news.setSource(source);
							String time = sourceAndTime.substring(0, s_start).replace("　 　 ", "");
							news.setPublishTime(time);
							
							
							String content=newsDoc.select("div#cont1 p").removeAttr("style").toString().replaceAll("\n", "").replaceAll("<p>关键字：</p>", "");
							news.setContent(content);
	
							news.setCreateTime(CommonUtil.getCurrentTime("yyyy-MM-dd HH:mm:ss"));
							
							news.save();
							System.out.println(title);
						} catch (Exception e) {
							System.out.println(e);
							continue;
						}
					}
				}
			}
			
		}






	}

	//get current time
	public static String getCurrentDate(){

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date now = new Date();
		String dt = sdf.format(now);


		return dt;
	}




}
