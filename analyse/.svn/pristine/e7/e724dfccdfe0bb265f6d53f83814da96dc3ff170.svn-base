package com.bluedon.analyse.service;

import java.io.IOException;
import java.util.List;

import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.tika.exception.TikaException;

import com.bluedon.analyse.biz.index.FileIndex;
import com.bluedon.analyse.biz.index.impl.FileIndexImpl;
import com.bluedon.analyse.biz.search.AdvanceQueryImpl;
import com.bluedon.analyse.biz.search.Page;
import com.bluedon.analyse.model.Doc;

public class AnalServiceImpl implements IAnalService {

	@Override
	public void  initIndex () throws CorruptIndexException, LockObtainFailedException, IOException {
		long b = System.currentTimeMillis();
		FileIndex iu = new FileIndexImpl();
		iu.InitIndex(true);
		long c = System.currentTimeMillis();
		System.out.println("Time:"+(c-b)+"ms");
//		System.out.println((c-b)/1000/60);
	}	

	@Override
	public List<Doc> search(String inertStr,Page page) throws CorruptIndexException, IOException, ParseException, InvalidTokenOffsetsException, TikaException {
		List<Doc> result = null;
		AdvanceQueryImpl q = new AdvanceQueryImpl();
//		long b = System.currentTimeMillis();
	//	result = q.searcherByHighlighter(inertStr,null,null);
		result = q.searcherByHighlighter(inertStr,page);
//		long c = System.currentTimeMillis();
//		System.out.println("xxxxxxxxxxx"+(c-b));
		
		return result;
	}

	@Override
	public Doc searchById(String id) throws NumberFormatException,CorruptIndexException, IOException {
//		long b = System.currentTimeMillis();
		AdvanceQueryImpl q = new AdvanceQueryImpl();
		Doc doc = q.searcherById(id);
//		long c = System.currentTimeMillis();
//		System.out.println("耗时:"+(c-b));
		return doc;
	}
	@Override
	public int addIndex(Doc doc) throws CorruptIndexException, LockObtainFailedException, IOException {
		FileIndex fileIndex = new FileIndexImpl();
		long b = System.currentTimeMillis();
		int result=fileIndex.addIndex(doc);
		long c = System.currentTimeMillis();
		System.out.println("Time:"+(c-b)+"ms");	
		return result;
	}

	@Override
	public void delIndex(Doc doc) throws CorruptIndexException, LockObtainFailedException, IOException {
		FileIndex fileIndex = new FileIndexImpl();
		long b = System.currentTimeMillis();
		fileIndex.delIndex(doc);
		long c = System.currentTimeMillis();
		System.out.println("Time:"+(c-b)+"ms");	
	}

	@Override
	public void updateIndex(Doc doc) throws CorruptIndexException, LockObtainFailedException, IOException {
		FileIndex fileIndex = new FileIndexImpl();
		long b = System.currentTimeMillis();
		fileIndex.updateIndex(doc);
		long c = System.currentTimeMillis();
		System.out.println("Time:"+(c-b)+"ms");	
	}


}
