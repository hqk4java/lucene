package com.bluedon.analyse.biz.search;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.search.function.FieldScoreQuery;
import org.apache.lucene.search.function.FieldScoreQuery.Type;
import org.apache.lucene.search.highlight.Formatter;
import org.apache.lucene.search.highlight.Fragmenter;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.search.highlight.SimpleSpanFragmenter;
import org.apache.lucene.util.Version;

import com.bluedon.analyse.biz.index.impl.FileIndexImpl;
import com.bluedon.analyse.biz.sameword.SameAnalyzer;
import com.bluedon.analyse.biz.sameword.impl.SameWordImpl;
import com.bluedon.analyse.biz.score.ScoreQuery;
import com.bluedon.analyse.biz.score.ScoreQuery.MyCustomScoreQuery;
import com.bluedon.analyse.filter.impl.WordFilterImpl;
import com.bluedon.analyse.model.Doc;
import com.bluedon.analyse.util.IniConfig;


public class AdvanceQueryImpl {
	
	public  List<Doc> searcherByHighlighter(String name){
		return searcherByHighlighter(name, null , new Page(1, 10));
	}
	
	public  List<Doc> searcherByHighlighter(String name, Page p){
		return searcherByHighlighter(name, null , p);
	}
	
	public List<Doc> searcherByHighlighter(String name, WordFilterImpl myFilter){
		return searcherByHighlighter(name, null , new Page(1, 10));
	}
	
	//高亮
	public  List<Doc> searcherByHighlighter(String name,WordFilterImpl myFilter, Page p) {
		List<Doc> DocLists = new ArrayList<Doc>();
		try {
			Analyzer a  = new SameAnalyzer(new SameWordImpl());
			IndexSearcher searcher = new IndexSearcher(IndexReader.open(FileIndexImpl.getDirectory()));
			
			MultiFieldQueryParser parser  = new  MultiFieldQueryParser(Version.LUCENE_36, new String[]{"title","content","fileArea"},a); //可以查询多个域
			
			Query query = parser.parse(name);
			

			TopScoreDocCollector results = TopScoreDocCollector.create(p.getStart() + p.getSize(), false);
			searcher.search(query, myFilter, results);
			TopDocs tds = results.topDocs(p.getStart(), p.getSize());
			p.setTotal(tds.totalHits);
			 
			System.out.println("总共匹配的条数"+tds.totalHits);
			for(ScoreDoc sd:tds.scoreDocs) {
				Document doc = searcher.doc(sd.doc);
				Doc d = new Doc();
				String title ="";
				String content ="";
				String fileArea = "";
				
				content = lighterStr(a,query,doc.get("content"),"content");  //高亮处理
				title = lighterStr(a,query,doc.get("title"),"title");    //高亮处理
				fileArea = lighterStr(a,query,doc.get("fileArea"),"fileArea");   //高亮处理
				//处理 content内容显示字数，只显示高亮部分
//				if(content.length() > 100) {
//					int index = content.indexOf("<font");
//					if(index > 20)
//						content = content.substring(index - 20, index+50);
//					else
//						content = content.substring(index, index+60);
//					content += "...";
//				}
				
				d.setId(doc.get("id"));
				d.setTitle(title);
				d.setContent(content);
				d.setFileArea(fileArea);
				d.setDate(Long.parseLong(doc.get("date")));
				d.setFileType(doc.get("fileType"));
				d.setNavi(doc.get("navi"));
				d.setOperationPlatform(doc.get("operationPlatform"));
				d.setPatchfile(dealFileName(doc.get("patchfile")));
				d.setUrl(doc.get("url"));
				DocLists.add(d);
			}
			
			searcher.close();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (InvalidTokenOffsetsException e) {
			e.printStackTrace();
		}finally {
			return DocLists; 
		}
	}
	/**
	 * 根据ID获取文档
	 * @param name
	 * @param myFilter
	 * @param p
	 * @return
	 */
	public Doc searcherById(String name) {
		Doc d = new Doc();
		try {
			IndexSearcher searcher = new IndexSearcher(IndexReader.open(FileIndexImpl.getDirectory()));

			Term term=new Term("id", name);
	        
	        Query query=new TermQuery(term);
	        
	        TopDocs tds = searcher.search(query, 10);
			
			int total = 0;
			for(ScoreDoc sd:tds.scoreDocs) {
				Document doc = searcher.doc(sd.doc);
				String status = doc.get("status");
				
				d.setId(doc.get("id"));
				d.setTitle(doc.get("title"));
				d.setContent(doc.get("content"));
				d.setFileArea(doc.get("fileArea"));
				d.setDate(Long.parseLong(doc.get("date")));
				d.setFileType(doc.get("fileType"));
				d.setNavi(doc.get("navi"));
				d.setOperationPlatform(doc.get("operationPlatform"));
				d.setPatchfile(dealFileName(doc.get("patchfile")));
				d.setUrl(doc.get("url"));
				
				total++;
				break;
			}
			
			System.out.println("总共匹配的条数"+total);
			searcher.close();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return d; 
	}
	
	public List<File> dealFileName(String patchFileStr) {
		List<File> fileList = new ArrayList<File>();
		if(patchFileStr==null) 
			return null;
		String[]  list  = patchFileStr.split("!!##&&%%");
		for (int i = 0; i < list.length; i++) {
			File f = new File(list[i]);
			fileList.add(f);
		}
		return fileList;
	}
	
	private static String lighterStr(Analyzer a, Query query,String txt,String fieldname) throws IOException, InvalidTokenOffsetsException {
		String str = null;
		QueryScorer scorer = new QueryScorer(query);
		Fragmenter  fragmenter = new SimpleSpanFragmenter(scorer);
		Formatter  fmt = new SimpleHTMLFormatter(IniConfig.get("lucene_highlighter", "begin"),
				IniConfig.get("lucene_highlighter", "end"));
		
		Highlighter lighter = new Highlighter(fmt,scorer);
		lighter.setTextFragmenter(fragmenter);
		
		Fragmenter sf =new SimpleFragmenter(150);//减少片断的默认大小
		lighter.setTextFragmenter(sf);
		str = lighter.getBestFragment(a,fieldname,txt);
		if(str==null)  return txt;
		
		return str;
		
	}
	
	public static MyCustomScoreQuery getMyCustomScoreQuery(Query query) {
		//1.创建一个评分域
		FieldScoreQuery fs = new FieldScoreQuery("score",Type.INT);
		//2.根据评分域各原有的Query创建自己定义的Query对象
		ScoreQuery.MyCustomScoreQuery   mq = new ScoreQuery().new MyCustomScoreQuery(query, fs);
		return mq;
         

	}

	
}
