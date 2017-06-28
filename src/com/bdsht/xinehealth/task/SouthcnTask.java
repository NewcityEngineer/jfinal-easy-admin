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
import com.jfinal.kit.StrKit;

public class SouthcnTask implements ITask {

	@Override
	public void run() {
		System.out.println(">>>>>>>>>>>>>>>>南方网开始>>>>>>>>>>>>>>>");
		try {
			getNews("http://fds.southcn.com/sybgt/node_340409.htm");
			getNews("http://fds.southcn.com/sybgt/node_340409_2.htm");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void stop() {
		System.out.println(">>>>>>>>>>>>>>>>南方网结束>>>>>>>>>>>>>>>>");

	}

	public static void main(String[] args) throws Exception {
		

				getNews("http://fds.southcn.com/sybgt/node_340409.htm");
		//		System.out.println(elements);
	}

	public static void getNews(String url) throws ParseException {

		String result = HttpClientUtil.QQHttpRequest(url);
		Document doc = Jsoup.parse(result);

		Elements elements = doc.select("div.j-link");

		for (int i = 0; i < elements.size(); i++) {
			Element element=elements.get(i);

			String link=element.select("a").get(0).toString();
			int l_start=link.indexOf("href=")+6;
			int l_end=link.indexOf("target=")-2;
			link=link.substring(l_start, l_end);
			String img=element.select("a img").toString();
			if(!StrKit.isBlank(img)){
				int i_start=img.indexOf("src=")+5;
				int i_end=img.indexOf("border")-2;
				img=img.substring(i_start, i_end);
			}
			String title=element.select("h3").text();
			String time=element.select("div.time").text();
			
			if (XhJobFoodNews.dao.findByUrl(link)==null&&XhJobFoodNews.dao.findByTitle(title)==null) {
				try {
					XhJobFoodNews news = new XhJobFoodNews();
					news.setTitle(title);
					news.setCategory(CommonUtil.getFoodNewsCatagory(title));
					news.setUrl(link);
					news.setImgurl(img);
					news.setPublishTime(time);
					Document newsDoc = Jsoup.parse(HttpClientUtil.QQHttpRequest(link));
					String source=newsDoc.select("span#source_baidu").text().replaceAll("来源：", "");
					System.out.println(source);
					news.setSource(source);
					news.setCreateTime(CommonUtil.getCurrentTime("yyyy-MM-dd HH:mm:ss"));
					String content=newsDoc.select("div#content p").removeAttr("style").toString().replaceAll("\n", "");
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
