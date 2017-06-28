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

public class Sj88Task implements ITask {

	@Override
	public void run() {
		System.out.println(">>>>>>>>>>>>>>>>四季养生开始>>>>>>>>>>>>>>>");
		try {
			getNews("http://www.sj88.com/hangye/baoguang/");
			getNews("http://www.sj88.com/hangye/baoguang/index_2.html");
			getNews("http://www.sj88.com/hangye/baoguang/index_3.html");
			getNews("http://www.sj88.com/hangye/baoguang/index_4.html");
			getNews("http://www.sj88.com/hangye/baoguang/index_5.html");
			getNews("http://www.sj88.com/hangye/baoguang/index_6.html");
			getNews("http://www.sj88.com/hangye/baoguang/index_7.html");
			getNews("http://www.sj88.com/hangye/baoguang/index_8.html");
			getNews("http://www.sj88.com/hangye/baoguang/index_9.html");
			getNews("http://www.sj88.com/hangye/baoguang/index_10.html");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(">>>>>>>>>>>>>>>>四季养生结束>>>>>>>>>>>>>>>>");
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub

	}

	public static void main(String[] args) throws Exception {
//		String result = HttpClientUtil.QQHttpRequest("http://www.sj88.com/hangye/baoguang/");
//		Document doc = Jsoup.parse(result);
//		//System.out.println(doc);
//		Elements elements = doc.select("ul.n_sp_left_specList li");
//		//System.out.println(elements);
//		for (int i = 0; i < elements.size(); i++) {
//			Element element=elements.get(i);
//			String linkTag=element.select("a.n_imgBox").toString();
//			int l_start=linkTag.indexOf("href=")+6;
//			int l_end=linkTag.indexOf("class")-2;
//			System.out.println(linkTag.substring(l_start, l_end));
//			String title=element.select("dl dt a").text();
//			System.out.println(title);
//			String imgTag=element.select("img").toString();
//			int i_start=imgTag.indexOf("src=")+5;
//			int i_end=imgTag.indexOf("alt")-2;
//			System.out.println("http://www.sj88.com"+imgTag.substring(i_start, i_end));
//		}
		getNews("http://www.sj88.com/hangye/baoguang/");
		//		System.out.println(elements);
	}

	public static void getNews(String url) throws ParseException {

		String result = HttpClientUtil.QQHttpRequest(url);
		Document doc = Jsoup.parse(result);
		Elements elements = doc.select("ul.n_sp_left_specList li");
		for (int i = 0; i < elements.size(); i++) {
			Element element=elements.get(i);
			
			String linkTag=element.select("a.n_imgBox").toString();
			int l_start=linkTag.indexOf("href=")+6;
			int l_end=linkTag.indexOf("class")-2;
			String link=linkTag.substring(l_start, l_end);
			
			String title=element.select("dl dt a").text();
			String imgTag=element.select("img").toString();
			
			int i_start=imgTag.indexOf("src=")+5;
			int i_end=imgTag.indexOf("alt")-2;
			String imgurl="http://www.sj88.com"+imgTag.substring(i_start, i_end);
			if (XhJobFoodNews.dao.findByUrl(link)==null&&XhJobFoodNews.dao.findByTitle(title)==null) {
				try {
					XhJobFoodNews news = new XhJobFoodNews();
					news.setTitle(title);
					news.setCategory(CommonUtil.getFoodNewsCatagory(title));
					news.setUrl(link);
					news.setImgurl(imgurl);
					Document newsDoc = Jsoup.parse(HttpClientUtil.QQHttpRequest(link));
					String sourceAndTime=newsDoc.select("div.c-artice-info").text();
					int source_start=sourceAndTime.indexOf("来源");
					String time=sourceAndTime.substring(0, source_start-1).replaceAll("/", "-");
					int source_end=sourceAndTime.indexOf("编辑")-1;
					String source=sourceAndTime.substring(source_start, source_end).replaceAll("来源：", "");
					news.setPublishTime(time);
					news.setSource(source);
					news.setCreateTime(CommonUtil.getCurrentTime("yyyy-MM-dd HH:mm:ss"));
					String content=newsDoc.select("div.c-artice-con").toString().replaceAll("\n", "");
					news.setContent(content);
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
