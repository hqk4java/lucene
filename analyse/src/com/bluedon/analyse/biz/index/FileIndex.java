package com.bluedon.analyse.biz.index;

import java.util.ArrayList;

import com.bluedon.analyse.model.Doc;


public interface FileIndex {

	/**
	 * 开始初始化索引库
	 */
	public   void InitIndex(boolean  hasNew);
	
	/**
	 * 批量新增一个索引
	 */
	public  Integer delIndex(ArrayList<Doc> docList);
	
	/**
	 * 删除一个索引库中一个元素
	 */
	public  Integer delIndex(Doc doc);
	/**
	 *更新索引 
	 */
	public  Integer updateIndex(Doc doc);
	/**
	 *批量更新
	 */
	public  Integer updateIndex(ArrayList<Doc> docList);
	
	/**
	 * 批量新增一个索引
	 */
	public  Integer addIndex(ArrayList<Doc> docList) ;
	
	/**
	 *新增一个索引
	 */
	public  Integer addIndex(Doc doc);			
}
