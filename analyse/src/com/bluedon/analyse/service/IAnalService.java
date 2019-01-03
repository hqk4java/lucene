package com.bluedon.analyse.service;

import java.io.IOException;
import java.util.List;

import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.tika.exception.TikaException;

import com.bluedon.analyse.biz.search.Page;
import com.bluedon.analyse.model.Doc;

public interface IAnalService {
	/**
	 * 初始化索引库
	 */
	void initIndex() throws CorruptIndexException, LockObtainFailedException,
			IOException;

	/**
	 * 查询高亮显示
	 */
	List<Doc> search(String inertStr, Page page) throws CorruptIndexException,
			IOException, ParseException, InvalidTokenOffsetsException,
			TikaException;
	/**
	 * 根据业务ID获取文档内容
	 * @param id
	 * @param page
	 * @return
	 * @throws CorruptIndexException
	 * @throws IOException
	 * @throws ParseException
	 * @throws InvalidTokenOffsetsException
	 * @throws TikaException
	 */
	Doc searchById(String id) throws NumberFormatException,CorruptIndexException, IOException;

	/**
	 * 新加一下个文档索引
	 */
	int addIndex(Doc doc) throws CorruptIndexException,
			LockObtainFailedException, IOException;

	/**
	 * 删除一个文档的的索引
	 */
	void delIndex(Doc doc) throws CorruptIndexException,
			LockObtainFailedException, IOException;

	/**
	 * 更新一个文档的的索引
	 */
	void updateIndex(Doc doc) throws CorruptIndexException,
			LockObtainFailedException, IOException;
}
