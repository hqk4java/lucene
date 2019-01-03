package com.bluedon.analyse.biz.search;

public class Page {
	
	private int curPage;
	
	private int size = 10;
	
	private int total;
	
	public Page(int curPage, int size) {
		super();
		this.curPage = curPage;
		this.size = size;
	}

	public int getCurPage() {
		return curPage;
	}

	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getTotalPages(){
		return (total + size - 1) / size;
	}
	
	public int getStart(){
		if(curPage > 0){
			return (curPage - 1) * size;
		}else{
			return 0;
		}
	}
	
}
