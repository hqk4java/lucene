package com.bluedon.analyse.filter.impl;

import java.io.IOException;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.TermDocs;
import org.apache.lucene.search.DocIdSet;
import org.apache.lucene.search.Filter;
import org.apache.lucene.util.OpenBitSet;

import com.bluedon.analyse.filter.IWordFilter;


public class WordFilterImpl extends Filter{

	private IWordFilter iFilter;
	public WordFilterImpl(IWordFilter iFilter){
		this.iFilter  = iFilter; //数据通过接口传入  
	}
	
	//覆盖getDocIdSet()方法  
	@Override 
	public DocIdSet getDocIdSet(IndexReader reader) throws IOException {
		 //创建一个bit,默认所有的元素都是0  
		OpenBitSet obs  = new OpenBitSet(reader.maxDoc()); 
		//obs.set(10);   //设置doc 为10的bimap为1
		if(iFilter.set()) { //true:仅指定文档可见  
			set(reader,obs);
		}else {   //false:仅指定文档不可见  
			clear(reader,obs);
		}
		//先把元素填满
		
		return obs;
	}
	
	//加入
	@SuppressWarnings("unused")
	private void set(IndexReader reader,OpenBitSet obs) throws IOException {
		
		int[] docs = new int[1];  //索引中的文档位置  
		int[] freqs =new int[1];  //索引中的出现次数
		//获取ID所在的，并且将其设置为0
		//获取id所在的doc的位置，并且将其设置为非零数字  
		for (String delId:iFilter.getValues()) {
			//共聚TermDocs
			TermDocs tds = reader.termDocs(new Term(iFilter.getField(),delId));
			 //会将查询出来的位置存储在docs中，将出现的频率存储到freqs中  
             //返回获取询的条数  
			int count = tds.read(docs, freqs);
			if(count==1) {
				//将这个位置的元素增加
				obs.set(docs[0]);  //将这个位置的元素展示  
			}
		}
	}
	//清除
	@SuppressWarnings("unused")
	private void clear(IndexReader reader,OpenBitSet obs) throws IOException {
		//先把所有元素填满,即增加
		obs.set(0,reader.maxDoc()-1);
	     int[] docs = new int[1];  //索引中的文档位置  
         int[] freqs = new int[1]; //索引中的出现次数  
         //获取id所在的doc的位置，并且将其设置为0 
		for (String delId:iFilter.getValues()) {
			//共聚TermDocs
			TermDocs tds = reader.termDocs(new Term(iFilter.getField(),delId));
			//会将查询出来的对象的位置存储到docs中，出现的频率存储到freqs中，返回获取的条数
			 //条数=tds.read(查询出来的位置, 出现的频率);    
			int count = tds.read(docs, freqs);
			if(count==1) {
				//将这个位置的元素过滤
				obs.clear(docs[0]);
			}
		}
	}

}
