package com.bdsht.xinehealth.task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.bdsht.xinehealth.common.ITask;
import com.bdsht.xinehealth.model.XhJobFoodNews;
import com.bdsht.xinehealth.util.CommonUtil;
import com.bdsht.xinehealth.util.HttpClientUtil;

public class CyscTask implements ITask {

	@Override
	public void run() {
		System.out.println(">>>>>>>>>>>>>>>>中国经济网开始>>>>>>>>>>>>>>>");
		try {
			getNews("http://www.ce.cn/cysc/sp/");//
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}////中国经济网
		System.out.println(">>>>>>>>>>>>>>>>中国经济网结束>>>>>>>>>>>>>>>");
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub

	}

	public static void main(String[] args) throws Exception {
		getNews("http://www.ce.cn/cysc/sp/");//

	}

	public static void getNews(String url) throws ParseException {

		String result = HttpClientUtil.httpRequest(url);
		Document doc = Jsoup.parse(result);
		Elements elements = doc.select("li.gjcnr");
		for (int i = 0; i < elements.size(); i++) {
			String info = elements.get(i).toString();
			String title=elements.get(i).text();
			String newurl = info.substring(info.indexOf("href=") + 6,
					info.indexOf("target") - 2);
			if(newurl.contains("./")){
				newurl=newurl.replace("./", url);
			}



			if (XhJobFoodNews.dao.findByUrl(newurl)==null&&XhJobFoodNews.dao.findByTitle(title)==null) {
				try {
					XhJobFoodNews news = new XhJobFoodNews();
					news.setTitle(title);
					news.setCategory(CommonUtil.getFoodNewsCatagory(title));
					news.setUrl(newurl);
					
					Document newsDoc = Jsoup.parse(HttpClientUtil.httpRequest(newurl));

					String source = newsDoc.select("span#articleSource").text().toString().replace("来源：", "");
					news.setSource(source);
					String time = newsDoc.select("span#articleTime").text().toString()
							.replace("年", "-").replace("月", "-")
							.replace("日", "");
					news.setPublishTime(time);
					String content = newsDoc.select("div.TRS_Editor").toString().replaceAll("\n", "");
					if(content.contains("img")){
						try{
							Elements imgElements=newsDoc.select("img");
							String img=imgElements.get(3).toString();
							int start=img.indexOf("src=")+5;
							int end=img.indexOf("oldsrc")-2;
							news.setImgurl(img.substring(start, end));
						}catch(Exception e){

						}
					}
					news.setCreateTime(CommonUtil.getCurrentTime("yyyy-MM-dd HH:mm:ss"));
					news.setContent(content);
					news.save();
					System.out.println(title);
				} catch (Exception e) {
					System.out.println(e);
					continue;
				}
			}
		}

		System.out.println("插入结束");


	}

	//get current time
	public static String getCurrentDate(){

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date now = new Date();
		String dt = sdf.format(now);


		return dt;
	}




}
