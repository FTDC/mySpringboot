package com.ftdc.baseutil.bean;

public class Pager {
	private int page;
	private int n;
	private int pageCount;
	private int total;
	int start;//分页起点
	public Pager(int page, int n, int total) {
		this.page = page;
		this.n = n;
		this.total = total;
		cal();
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getN() {
		return n;
	}

	public void setN(int n) {
		this.n = n;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	/**
	 * 计算各参数的值
	 */
	private void cal() {
		// 计算总页数
		if (n <= 0) {
			n = 10;
		}
		pageCount = total / n;
		if (total % n > 0) {
			pageCount = pageCount + 1;
		}
		if (pageCount <= 0) {
			pageCount = 1;
		}
		// 判断当前页的合理范围
		if (page < 1) {
			page = 1;
		} else {
			if (page > pageCount) {
				page = pageCount;
			}
		};
		
	};
	
	


	
	/** 
	* @return the start  得到分页的起点
	*/
	public int getStart() {
		start=(page-1)*n;
		return start;
	}

	
//	/**
//	* @param start the start to set 
//	**/
//	public void setStart(int start) {
//		this.start = start;
//	};
};
