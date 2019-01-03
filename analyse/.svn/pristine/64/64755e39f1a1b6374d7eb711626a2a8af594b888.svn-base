package com.bluedon.analyse.biz.score;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.FieldCache;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.function.CustomScoreProvider;
import org.apache.lucene.search.function.CustomScoreQuery;
import org.apache.lucene.search.function.FieldScoreQuery;
import org.apache.lucene.search.function.FieldScoreQuery.Type;
import org.apache.lucene.search.function.ValueSourceQuery;

import com.bluedon.analyse.biz.index.impl.FileIndexImpl;




public class ScoreQuery {
	
	public void searchByScoreQuery() {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			IndexSearcher searcher = new IndexSearcher(IndexReader.open(FileIndexImpl.getDirectory()));
			Query q = new TermQuery(new Term("content","java"));
			//1.创建一个评分域
			FieldScoreQuery fs = new FieldScoreQuery("score",Type.INT);
			//2.根据评分域各原有的Query创建自己定义的Query对象
			MyCustomScoreQuery query = new MyCustomScoreQuery(q,fs); //q:自己的query fs:评分
			
			TopDocs tds = null;
			tds = searcher.search(query, 100);
			for(ScoreDoc sd : tds.scoreDocs) {
				Document d = searcher.doc(sd.doc);
				System.out.println(sd.doc+":("+sd.score+")["+d.get("filename")+"]["+d.get("path")+"]--->+"+d.get("score")+"-->"+d.get("size")+"" +
						"--->"+sdf.format(new Date(Long.valueOf(d.get("date")))));
			}
			searcher.close();
		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	} 
	
	
	@SuppressWarnings("serial")
	public  class MyCustomScoreQuery extends CustomScoreQuery{

		public MyCustomScoreQuery(Query subQuery, ValueSourceQuery valSrcQuery) {
			super(subQuery, valSrcQuery);
		}

		@Override
		public CustomScoreProvider getCustomScoreProvider(IndexReader reader)
				throws IOException {
			//默认情况实现的评分是通过原有的评分 * 传入进来的评分域所获取的评分来确定最终打分
			//为了根据不同的需求进行评分，需要自己进行评分设定
			/**
			 * 自定评分的步骤
			 * 创建一个类继承于CustomScoreProvider
			 * 覆盖customScore方法
			 */
			//return super.getCustomScoreProvider(reader);  //默认的
			return new MyCustomScoreProvider(reader);  //用自己定义的
		}
		public class MyCustomScoreProvider extends CustomScoreProvider {

			public MyCustomScoreProvider(IndexReader reader) {
				super(reader);
			}
			
			/**
			 * subQueryScore：表示默认文档的打分
			 * valSrcScore：表示的评分域打分
			 */
			@Override
			public float customScore(int doc, float subQueryScore,
					float valSrcScore) throws IOException {
//				return super.customScore(doc, subQueryScore, valSrcScore);
				System.out.println("subQueryScore : " +subQueryScore);
				System.out.println("valSrcScore : " +valSrcScore);
				return subQueryScore / 1;
			}
			
			
		}
		

	}

	public void searchBySFilenameQuery() {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			IndexSearcher searcher = new IndexSearcher(IndexReader.open(FileIndexImpl.getDirectory()));
			Query q = new TermQuery(new Term("content","java"));
			//1.创建一个评分域
			FilenameScoreQuery query = new FilenameScoreQuery(q); //q:自己的query fd:评分
			
			TopDocs tds = null;
			tds = searcher.search(query, 100);
			for(ScoreDoc sd : tds.scoreDocs) {
				Document d = searcher.doc(sd.doc);
				System.out.println(sd.doc+":("+sd.score+")["+d.get("filename")+"]["+d.get("path")+"]--->+"+d.get("score")+"-->"+d.get("size")+"" +
						"--->"+sdf.format(new Date(Long.valueOf(d.get("date")))));
			}
			searcher.close();
		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	} 
	//根据文件名打分
	
	@SuppressWarnings("serial")
	public class FilenameScoreQuery extends CustomScoreQuery{

		public FilenameScoreQuery(final Query subQuery) {
			super(subQuery);
		}

		@Override
		public CustomScoreProvider getCustomScoreProvider(final IndexReader reader)
				throws IOException {
			return new FilenameScoreProvider(reader);
		} 
		
		
	}
	
	public class FilenameScoreProvider extends CustomScoreProvider{
		String[] filenames = null;
		public FilenameScoreProvider(IndexReader reader) {
			super(reader);
			try {
				filenames = FieldCache.DEFAULT.getStrings(reader, "filename");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		@Override
		public float customScore(int doc, float subQueryScore, float valSrcScore)
				throws IOException {
			
			/**
			 * 如何根据doc获取相应的field的值
			 * 在reader没有关闭这前，所有的数据会储到一个域缓存中,可以通过域缓存获取很多可用的信息         
			 * FieldCache.DEFAULT.getStrings(reader, "filename"); 可以获取所有的filename域的信息                                                                                   
			 */
		    String filename = filenames[doc];
		    if(filename.endsWith(".txt") || filename.endsWith(".c")) {
		    	return  subQueryScore *1.5f;
		    }
			return subQueryScore /1.5f;
		}
		
		
	}
	
	public class DateScoreProvider extends CustomScoreProvider{

		long[] dates = null;
		public DateScoreProvider(IndexReader reader) {
			super(reader);
			try {
				dates = FieldCache.DEFAULT.getLongs(reader, "date");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		@Override
		public float customScore(int doc, float subQueryScore, float valSrcScore)
				throws IOException {
			long date = dates[doc];
			long today = new Date().getTime();
			long year = 1000*60*60*24*365;
			if(today-date<=year) {
				//加放自己的评规则
			}
			return super.customScore(doc, subQueryScore, valSrcScore);
		}
		
	}

}
