package com.bluedon.analyse.test;


import java.io.IOException;
import java.util.Date;

import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.tika.exception.TikaException;
import org.junit.Test;

import com.bluedon.analyse.biz.index.FileIndex;
import com.bluedon.analyse.biz.index.impl.FileIndexImpl;
import com.bluedon.analyse.biz.search.AdvanceQueryImpl;
import com.bluedon.analyse.model.Doc;
/**
 * 
 * @author Fdong
 *
 */
public class TestAnalyzer {
		
		/**
		 * 初始化索引库
		 */
		//@Test
		public void  initIndex () throws CorruptIndexException, LockObtainFailedException, IOException {
			long b = System.currentTimeMillis();
			FileIndex iu = new FileIndexImpl();
			iu.InitIndex(true);
			long c = System.currentTimeMillis();
			System.out.println(c-b);
			System.out.println((c-b)/1000/60);
		}	
		
		/**
		 * 查询高亮显示 
		 */
		//@Test  
		public void search(String inertStr) throws CorruptIndexException, IOException, ParseException, InvalidTokenOffsetsException, TikaException {
			AdvanceQueryImpl q = new AdvanceQueryImpl();
			long b = System.currentTimeMillis();
			q.searcherByHighlighter(inertStr,null,null);
			long c = System.currentTimeMillis();
			System.out.println("xxxxxxxxxxx"+(c-b));
		}
		
		
		/**
		 * 新加一下个文档索引
		 */
		//@Test
		public void addIndex(Doc doc) throws CorruptIndexException, LockObtainFailedException, IOException {
			FileIndex fileIndex = new FileIndexImpl();
			long b = System.currentTimeMillis();
			fileIndex.addIndex(doc);
			long c = System.currentTimeMillis();
			System.out.println("耗时:"+(c-b));	
		}
		/**
		 * 删除一个文档的的索引
		 */
		@Test
		public void delIndex(Doc doc) throws CorruptIndexException, LockObtainFailedException, IOException {
			FileIndex fileIndex = new FileIndexImpl();
			long b = System.currentTimeMillis();
			fileIndex.delIndex(doc);
			long c = System.currentTimeMillis();
			System.out.println("耗时:"+(c-b));	
		}
		
		/**
		 * 删除一个文档的的索引
		 */
		@Test
		public void updateIndex(Doc doc) throws CorruptIndexException, LockObtainFailedException, IOException {
			FileIndex fileIndex = new FileIndexImpl();
			long b = System.currentTimeMillis();
			fileIndex.updateIndex(doc);
			long c = System.currentTimeMillis();
			System.out.println("耗时:"+(c-b));	
		}
		
		@Test
		public static void main(String[] args) throws CorruptIndexException, LockObtainFailedException, IOException, ParseException, InvalidTokenOffsetsException, TikaException {
			TestAnalyzer  testAnalyzer = new TestAnalyzer();
			Doc doc = new Doc();

			doc.setId("2");
			doc.setContent("重庆麻la断根");
			doc.setTitle("中国万岁,火锅");
			doc.setNavi("gb 接收");
			doc.setDate(new Date().getTime());
			testAnalyzer.updateIndex(doc);
			//新加一个索引
//			testAnalyzer.initIndex();  //初如化

		//	testAnalyzer.addIndex(doc);  //新加一个索引
			
//			testAnalyzer.delIndex();   //删除一个文档的索引
			testAnalyzer.updateIndex(doc);
			testAnalyzer.search("中国万岁,火锅");   //搜索
		
			
		}


}
