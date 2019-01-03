package com.bluedon.analyse.filter;
/**
 * @classname: IWordFilter
 * @desc : 自定义过滤器接口
 * @author Fdong
 */
public interface IWordFilter {
	  
	//设置指定文档  
    public String[] getValues();  
    //获取域  
    public String getField();  
    /* 
     * 设置是否可见 
     * true:仅指定文档可见 
     * false:仅指定文档不可见 
     */  
    public boolean set();  
}
 