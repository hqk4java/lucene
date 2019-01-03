package com.bluedon.analyse.biz.sameword.impl;

import java.util.HashMap;
import java.util.Map;

import com.bluedon.analyse.biz.sameword.SameWord;


/**
 * @classname: SameWordImpl
 * @desc : ͬ���ʵ����
 * @author Fdong
 */
public class SameWordImpl  implements SameWord{
	
	//���ͬ���Map����
	Map<String,String[]> sameWordMap = new HashMap<String,String[]>(); 
	
	
	public SameWordImpl() {
		sameWordMap.put("�ٶ�", new String[]{"����","baidu"});
		sameWordMap.put("����", new String[]{"�쳯","�׶�","�찲��"});	
	}

	
	/**
	 * ��ȡͬ���
	 */
	public String[] getSameWords(String name) {
		 return sameWordMap.get(name);
	}

	/**
	 * ����ͬ���
	 */
	public void setSameWords() {
		
	}

}
