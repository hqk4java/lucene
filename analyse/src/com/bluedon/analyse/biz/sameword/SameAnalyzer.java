package com.bluedon.analyse.biz.sameword;

import java.io.Reader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;

import com.chenlb.mmseg4j.Dictionary;
import com.chenlb.mmseg4j.MaxWordSeg;
import com.chenlb.mmseg4j.analysis.MMSegTokenizer;

public class SameAnalyzer extends Analyzer{

	private SameWord iSameWord;
	public SameAnalyzer(SameWord iSameWord) {
		this.iSameWord = iSameWord;
	}
	@Override
	public TokenStream tokenStream(String fileName, Reader reader) {
//		Dictionary dic  =Dictionary.getInstance("D:\\lucene\\lucenejar\\mmseg4j-all-1.8.5-with-dic\\data");
//		Dictionary dic  =Dictionary.getInstance();
		Dictionary dic  =Dictionary.getInstance(SameAnalyzer.class.getClassLoader().getResource(".").getPath()+"mmseg4j/data");
		return new SameTokenFilter(new MMSegTokenizer(new MaxWordSeg(dic),reader)
		                             ,iSameWord);
	}
	public static void main(String[] args) {
		System.out.println(SameAnalyzer.class.getClassLoader().getResource(".").getPath());
//		/wgj-project/lucene/target/classes/mmseg4j-all-1.8.5-with-dic/data
	}
}
