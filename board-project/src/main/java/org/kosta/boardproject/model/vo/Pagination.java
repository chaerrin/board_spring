package org.kosta.boardproject.model.vo;

public class Pagination{
	private int nowPage=1; // 현재페이지
	private int postCountPerPage=4; // 페이지당 게시물 수 
	private int pageCountPerPageGroup=5;// 페이지 그룹당 페이지 수 
	private int totalPostCount; // 총 게시물 수 
	
	public Pagination(int totalPostCount) {
		super();
		this.totalPostCount = totalPostCount;
	}

	public Pagination(int nowPage, int totalPostCount) {
		super();
		this.nowPage = nowPage;
		this.totalPostCount = totalPostCount;
	}
	public int getNowPage() { // 현재페이지
		return nowPage;
	}

	public void setPostCountPerPage(int postCountPerPage) {
		this.postCountPerPage = postCountPerPage;
	}

	public int getStartRowNumber() { // 시작번호
		return (this.nowPage-1)*this.postCountPerPage+1;
		// (현재페이지-1) * 페이지당 게시물수 +1 
	}

	public int getEndRowNumber() {	// 끝번호
		int endRowNumber = this.nowPage*this.postCountPerPage;
		if(endRowNumber>this.totalPostCount)
			endRowNumber = this.totalPostCount;
		return endRowNumber;
		//현재 페이지 * 페이지당 게시물 수 
		// 마지막 번호가 총게시물 수 번호보다 작으면 총게시물수=마지막번호
	}
	
	public int getTotalPage() { // 총페이지 수 
		int totalPage=this.totalPostCount / this.postCountPerPage;
		if(this.totalPostCount % this.postCountPerPage!=0)
			totalPage +=1;
		return totalPage;
		// 총페이지수 = 총게시물 수 / 페이지당 게시물 수 
		// 만약 총게시물수 를 페이지당 게시물 수로 나누었을때 나머지가 0이 아니면
		// 총페이지수 +1
	}
	
	public int getTotalPageGroup() { // 총페이지 그룹 수 
		int totalPageGroup= getTotalPage() / this.pageCountPerPageGroup;
		if(getTotalPage()%this.pageCountPerPageGroup!=0)
			totalPageGroup += 1;
		return totalPageGroup;
		// 총페이지 그룹 = 총페이지 수 / 페이지 그룹당 페이지 수 
		// 만약 총페이지 를 페이지 그룹당 페이지 수로 나누었을 때 나머지가  0이 아니면
		// 총페이지 그룹 +1
	}
	
	public int getNowPageGroup() {	// page group 의 시작 page, 끝 page 와 previous, next 를 나타내는 데에 필요함
		int nowPageGroup=this.nowPage / this.pageCountPerPageGroup;
		if(this.nowPage%this.pageCountPerPageGroup!=0)
			nowPageGroup += 1;
		return nowPageGroup;
		// 현재 페이지 그룹 = 현재페이지 / 페이지그룹당 페이지수 
		// 만약 현재페이지 를 페이지그룹당 페이지수 로 나누었을 때 0이 아니면
		// 현재 페이지 그룹 + 1
	}
	
	public int getStartPageOfPageGroup() {// 페이지그룹의 시작 페이지
		return (this.getNowPageGroup()-1)*this.pageCountPerPageGroup+1;
		// 페이지그룹의 시작 페이지
		// (현재 페이지 그룹 -1) * 페이지그룹당 페이지수 +1
		//           (2-1) * 5+1 =6 
	}
	
	public int getEndPageOfPageGroup() {
		int endPage=this.getNowPageGroup()*this.pageCountPerPageGroup;
		if(endPage>this.getTotalPage())
			endPage = this.getTotalPage();
		return endPage;
		// 페이지그룹의 마지막 페이지
		// 마지막페이지 = 현재페이지그룹 * 페이지그룹당 페이지 수 
		//    15   		 =     3           *           5
	}
	
	public boolean isPreviousPageGroup() {
		boolean flag=false;
		if(this.getNowPageGroup()>1)
			flag = true;
		return flag;
		// 이전페이지 그룹
		// 현재 페이지그룹이 1보다 크면 true
	}
	
	public boolean isNextPageGroup() {
		boolean flag=false;
		if(this.getNowPageGroup()<this.getTotalPageGroup())
			flag = true;
		return flag;
		// 다음페이지그룹
		// 현재 페이지그룹이 전체 페이지 그룹보다 작으면 ture
	}
}
