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

public class SinaTask implements ITask {

	@Override
	public void run() {
		System.out.println(">>>>>>>>>>>>>>>>新浪新闻结束>>>>>>>>>>>>>>>>");
		try {
			getNews("http://search.sina.com.cn/?q=%E9%A3%9F%E5%93%81%E5%AE%89%E5%85%A8&c=news&from=channel&ie=utf-8");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void stop() {
		System.out.println(">>>>>>>>>>>>>>>>新浪新闻结束>>>>>>>>>>>>>>>>");
	}

	public static void main(String[] args) throws Exception {
	
		getNews("http://search.sina.com.cn/?q=%E9%A3%9F%E5%93%81%E5%AE%89%E5%85%A8&c=news&from=channel&ie=utf-8");
	}

	public static void getNews(String url) throws ParseException {

		String result = HttpClientUtil.httpRequest(url);
		Document doc = Jsoup.parse(result);
		
		Elements elements = doc.select("div.r-info h2");
		for (int i = 0; i < elements.size(); i++) {
			Element element=elements.get(i);
			String title=element.select("a").text();
			String link=element.toString();
			int l_start=link.indexOf("href=")+6;
			int l_end=link.indexOf("target=")-2;
			link=link.substring(l_start, l_end);
			String sourceAndTime=element.select("span.fgray_time").text();
			int s_end=sourceAndTime.indexOf(" ");
			String source=sourceAndTime.substring(0, s_end);
			String time=sourceAndTime.substring(s_end+1, sourceAndTime.length());
			if (XhJobFoodNews.dao.findByUrl(link)==null&&XhJobFoodNews.dao.findByTitle(title)==null) {
				try {
					XhJobFoodNews news = new XhJobFoodNews();
					news.setTitle(title);
					news.setCategory(CommonUtil.getFoodNewsCatagory(title));
					news.setUrl(link);
					System.out.println(link);
					Document newsDoc = Jsoup.parse(HttpClientUtil.sinaHttpRequest(link));
					
					news.setSource(source);
					news.setPublishTime(time);
					
					Elements imgElements=newsDoc.select("p img");
					String img=null;
					if(imgElements.size()>0&&imgElements.get(0).toString().contains(".jpg")){
						img=imgElements.get(0).toString();
						int i_start=img.indexOf("src")+5;
						int i_end=img.indexOf(".jpg")+4;
						img=img.substring(i_start,i_end);
					}
					news.setImgurl(img);
					
					String content=newsDoc.select("div#artibody p").removeAttr("style").toString().replaceAll("\n", "");
					news.setContent(content);
					System.out.println(content);
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

	//get current time
	public static String getCurrentDate(){

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date now = new Date();
		String dt = sdf.format(now);


		return dt;
	}




}
