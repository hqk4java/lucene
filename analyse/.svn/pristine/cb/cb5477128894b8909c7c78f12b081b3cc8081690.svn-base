package com.bluedon.analyse.model;

import java.io.File;
import java.io.Serializable;
import java.util.List;
/**
 * 
 * @author modify for lzy on 2018/2/2
 *
 */
public class Doc implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String id;       //文档ID
	private String title;    //文档标题
	private String content;  //文档内容
	private List<File> patchfile;   //附牛
	private String fileArea;  //附件文本域的内容
	private String fileType ; //文档类型, 00－附件类        01－文字类  02-混合
	private long date;       //创建日期
	private String navi;	//导航菜单
	private String operationPlatform; //模块
	private String url;		//链接地址
	
	public Doc() {
		super();
	}
	
	public Doc(String id) {
		super();
		this.id = id;
	}

	public Doc(String id, String title, String content, List<File> patchfile,
			String fileArea, String fileType, long date, String navi,
			String operationPlatform, String url) {
		super();
		this.id = id;
		this.title = title;
		this.content = content;
		this.patchfile = patchfile;
		this.fileArea = fileArea;
		this.fileType = fileType;
		this.date = date;
		this.navi = navi;
		this.operationPlatform = operationPlatform;
		this.url = url;
	}

	public String getNavi() {
		return navi;
	}
	public void setNavi(String navi) {
		this.navi = navi;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public List<File> getPatchfile() {
		return patchfile;
	}
	public void setPatchfile(List<File> patchfile) {
		this.patchfile = patchfile;
	}
	public String getFileArea() {
		return fileArea;
	}
	public void setFileArea(String fileArea) {
		this.fileArea = fileArea;
	}
	public String getOperationPlatform() {
		return operationPlatform;
	}
	public void setOperationPlatform(String operationPlatform) {
		this.operationPlatform = operationPlatform;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public long getDate() {
		return date;
	}
	public void setDate(long date) {
		this.date = date;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}

}
