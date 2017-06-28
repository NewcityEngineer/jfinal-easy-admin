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

public class CnfdnTask implements ITask {

	@Override
	public void run() {
		System.out.println(">>>>>>>>>>>>>>>>中国食品监督网开始>>>>>>>>>>>>>>>");
		try {
			getNews("http://www.cnfdn.com/baoguangtai/index.html");//中国食品监督网
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(">>>>>>>>>>>>>>>>中国食品监督网结束>>>>>>>>>>>>>>>>");
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub

	}

	public static void main(String[] args) throws Exception {
//		String result = HttpClientUtil.httpRequest("http://www.cnfdn.com/baoguangtai/1205_11632.html");
//		Document doc = Jsoup.parse(result);
//		System.out.println(doc);
//		Elements elements = doc.select("table.biankuang1");
		
		getNews("http://www.cnfdn.com/baoguangtai/index.html");
		//		System.out.println(elements);
	}

	public static void getNews(String url) throws ParseException {

		String result = HttpClientUtil.httpRequest(url);
		Document doc = Jsoup.parse(result);
		Elements elements = doc.select("table.biankuang1");

		for (int i = 0; i < elements.size(); i++) {
			Element element=elements.get(i);
			if(element.text().contains("食品曝光内容列表")){
				Elements link=element.select("a");
				for (int j = 0; j < link.size(); j++) {
					if(link.get(j).toString().contains("title")){
						String info = link.get(j).toString();
						String title=link.get(j).text();
						String newurl = info.substring(info.indexOf("href=") + 6,
								info.indexOf("target") - 2);
						if (XhJobFoodNews.dao.findByUrl(newurl)==null&&XhJobFoodNews.dao.findByTitle(title)==null) {
							try {
								XhJobFoodNews news = new XhJobFoodNews();
								news.setTitle(title);
								news.setCategory(CommonUtil.getFoodNewsCatagory(title));
								news.setUrl(newurl);

								Document newsDoc = Jsoup.parse(HttpClientUtil.httpRequest(newurl));
								
								
								Elements content=newsDoc.select("div#MyContent p").removeAttr("style");
								String sourceAndTime=newsDoc.select("td.neirongyegongneng").toString();
								int s_start=sourceAndTime.indexOf("来源：");
								int s_end=sourceAndTime.indexOf("&nbsp");
								String source = sourceAndTime.substring(s_start, s_end).replace("来源：", "");
								news.setSource(source);
								int t_start=sourceAndTime.indexOf("更新时间：");
								int t_end=sourceAndTime.indexOf("<span");
								String time = sourceAndTime.substring(t_start, t_end).
										replace("更新时间：", "").replace("&nbsp;", "").
										replace("年", "-").replace("月", "-")
										.replace("日", "");
								news.setPublishTime(time);
								news.setCreateTime(CommonUtil.getCurrentTime("yyyy-MM-dd HH:mm:ss"));
								System.out.println(time);
								news.setContent(content.toString());
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





	}

	//get current time
	public static String getCurrentDate(){

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date now = new Date();
		String dt = sdf.format(now);


		return dt;
	}




}
