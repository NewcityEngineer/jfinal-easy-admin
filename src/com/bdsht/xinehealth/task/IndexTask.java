package com.bdsht.xinehealth.task;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.IntField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DocsEnum;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.MultiFields;
import org.apache.lucene.index.TermsEnum;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.DocIdSetIterator;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.similarities.DefaultSimilarity;
import org.apache.lucene.search.similarities.TFIDFSimilarity;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Bits;
import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.bdsht.xinehealth.common.ITask;
import com.bdsht.xinehealth.model.XhJobFoodNews;
import com.jfinal.core.JFinal;

public class IndexTask implements ITask {
	private String indexDir = JFinal.me().getServletContext().getRealPath("/")+"temp";
	//private String indexDir = "d:/indexdir";
	static float tf = 1;
	static float idf = 0;
	private float tfidf_score;
	private static Logger log = LoggerFactory.getLogger(IndexTask.class);

	@Override
	public void run(){
		
		System.out.println(">>>>>>>>>>>>>>>>IndexTask开始>>>>>>>>>>>>>>>>");
		// 取得今天所有记录，设置索引
		Analyzer analyzer = new IKAnalyzer(true);

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

		Calendar c = Calendar.getInstance();
		c.setTime(new Date()); // 设置当前日期
		Date now = c.getTime();
		String today = df.format(now);

		List<XhJobFoodNews> newsList = XhJobFoodNews.dao.listToday(today);

		System.out.println(indexDir);
		// 创建或打开索引目录
		Directory directory;
		try {
			directory = FSDirectory.open(new File(indexDir));
			// 创建IndexWriter
			IndexWriterConfig conf = new IndexWriterConfig(Version.LUCENE_46,
					analyzer);
			IndexWriter indexWriter = new IndexWriter(directory, conf);
			indexWriter.deleteAll();



			// 遍历数组创建索引
			for (int i = 0; i < newsList.size(); i++) {
				XhJobFoodNews news = (XhJobFoodNews) (newsList.get(i));
				// 创建document并添加field
				Document document = new Document();
				document.add(new IntField("id", news.getId(), Field.Store.YES));
				document.add(new TextField("content", news.getContent(),
						Field.Store.YES));
				// 将document添加到索引中
				indexWriter.addDocument(document);
			}

			indexWriter.commit();
			indexWriter.close();
			// directory.close();

			for (int i = 0; i < newsList.size(); i++) {
				XhJobFoodNews news = (XhJobFoodNews) (newsList.get(i));
				String title = news.getTitle();
				String category=news.getCategory();
				if(category.equals("video")||category.equals("image")){
					continue;
				}else{
					float sumScore = 0;

					try {
						IndexReader reader = IndexReader.open(directory);
						IndexSearcher searcher = new IndexSearcher(reader);
						// 使用查询解析器创建Query
						QueryParser parser = new QueryParser(Version.LUCENE_46,
								"content", analyzer);
						Query query = parser.parse(title);
						// 输出解析后的查询语句
						//System.out.println("查询语句：" + query.toString());
						// 从索引中搜索得分排名前10的文档
						TopDocs topDocs = searcher.search(query, 10);
						ScoreDoc[] scoreDoc = topDocs.scoreDocs;
						// System.out.println("共检索到" + topDocs.totalHits + "条匹配结果");

						for (ScoreDoc sd : scoreDoc) {
							// 根据id获取document
							org.apache.lucene.document.Document d = searcher
									.doc(sd.doc);
							// System.out.println(d.get("id") + " score:" + sd.score);
							// 查看文档得分解析
							// System.out.println(searcher.explain(query, sd.doc));
							sumScore += sd.score;
						}

						String[] keywords = query.toString().replace("(", "")
								.replace(")", "").split("content:");
						double[] scores = new double[keywords.length];

						for (int j = 0; j < keywords.length; j++) {
							scores[j] = scoreCalculator(reader, "content", keywords[j]);
						}

						double[] copy = Arrays.copyOf(scores, scores.length);
						double max = getMax(copy);
						for (int j = 0; j < copy.length; j++) {
							if (copy[j] >= max)
								copy[j] = -1;
						}

						max = getMax(copy);
						for (int j = 0; j < copy.length; j++) {
							if (copy[j] >= max)
								copy[j] = -1;
						}

						max = getMax(copy);
						for (int j = 0; j < copy.length; j++) {
							if (copy[j] >= max)
								copy[j] = -1;
						}

						String finals = "";

						for (int j = 0; j < scores.length; j++) {
							if (scores[j] >= max)
								finals += keywords[j] + " ";
						}
						news.setKeywords(finals);
						//System.out.println(finals);

						if (scoreDoc.length > 0)
							sumScore = sumScore / (scoreDoc.length);
						reader.close();

					} catch (Exception e) {
						sumScore = 0;

						log.error("IndexAcion Error:" + e.getMessage());
						continue;
					} finally {
						news.setScore(String.valueOf(sumScore));
						news.update();
					}
				}
			}
			directory.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.out.println(e1.toString());
			
		}
		System.out.println(">>>>>>>>>>>>>>>>IndexTask结束>>>>>>>>>>>>>>>>");
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub

	}
	public void CreateKeywords() throws IOException {
		// 取得今天所有记录，设置索引
		Analyzer analyzer = new IKAnalyzer(true);

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

		Calendar c = Calendar.getInstance();
		c.setTime(new Date()); // 设置当前日期
		Date now = c.getTime();
		String today = df.format(now);

		List<XhJobFoodNews> newsList = XhJobFoodNews.dao.listToday(today);
		String[] contentArr = new String[newsList.size()];

		for (int i = 0; i < newsList.size(); i++) {
			XhJobFoodNews news = (XhJobFoodNews) (newsList.get(i));
			contentArr[i] = news.getTitle();
		}

		// // 创建或打开索引目录
		// Directory directory = FSDirectory.open(new File(indexDir));
		//
		// try {
		// searchIndex(contentArr[0], analyzer);
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		// luceneTest.searchIndex("梦想上清华", analyzer);
		// System.out.println(json);

		// // true:智能切分，false:细粒度切分
		// Analyzer analyzer = new IKAnalyzer(true);
		//
		// // createIndex(analyzer);
		//
		// // 对content进行分词，得到的结果是分词流
		// TokenStream ts = analyzer.tokenStream("text", contentArr[0]);
		// ts.reset();
		//
		// Map<String, String> map = new HashMap<String, String>();
		// CharTermAttribute attr = null;
		//
		// // 遍历分词流
		// while (ts.incrementToken()) {
		// attr = ts.getAttribute(CharTermAttribute.class);
		// System.out.println(attr.toString());
		// map.put(attr.toString(), "");
		// }
		//
		// for (Entry<String, String> entry : map.entrySet()) {
		// String key = entry.getKey().toString();
		// String value = entry.getValue().toString();
		// try {
		// searchIndex(key, analyzer);
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// //e.printStackTrace();
		// }
		// }
	}

	public float scoreCalculator(IndexReader reader, String field, String term)
			throws IOException {
		/** GET TERM FREQUENCY & IDF **/
		TFIDFSimilarity tfidfSIM = new DefaultSimilarity();
		Bits liveDocs = MultiFields.getLiveDocs(reader);
		TermsEnum termEnum = MultiFields.getTerms(reader, field).iterator(null);
		BytesRef bytesRef;

		while ((bytesRef = termEnum.next()) != null) {

			if (bytesRef.utf8ToString().trim().equals(term.trim())) {

				if (termEnum.seekExact(bytesRef) == true) {

					idf = tfidfSIM.idf(termEnum.docFreq(), reader.numDocs());
					DocsEnum docsEnum = termEnum.docs(liveDocs, null);
					if (docsEnum != null) {
						int doc;
						while ((doc = docsEnum.nextDoc()) != DocIdSetIterator.NO_MORE_DOCS) {
							tf = tfidfSIM.tf(docsEnum.freq());
							tfidf_score = tf * idf;
						}
					}
				}
			}
		}

		return tfidf_score;
	}

	/**
	 * 求给定双精度数组中值的最大值
	 * 
	 * @param inputData
	 *            输入数据数组
	 * @return 运算结果,如果输入值不合法，返回为-1
	 */
	public static double getMax(double[] inputData) {
		if (inputData == null || inputData.length == 0)
			return -1;
		int len = inputData.length;
		double max = inputData[0];
		for (int i = 0; i < len; i++) {
			if (max < inputData[i])
				max = inputData[i];
		}
		return max;
	}


}
