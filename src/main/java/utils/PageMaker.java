package utils;

import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

// 화면에 표시할 페이지 버튼정보
public class PageMaker {
	private Criteria criteria; // 현재 페이지 정보
	private int totalCount;	   // 조회된 전체 게시글의 수
	private int startPage;	 // 화면에 표시할 시작 페이지 번호
	private int endPage;	 // 화면에 표시할 끝 페이지 번호
	private boolean prev;	 // 이전 버튼 유무
	private boolean next;	 // 다음 버튼 유무
	private int cntPageNum = 10; // 한 화면에 표시할 페이지 버튼의 수
	private int realEndPage; // 실제 끝 페이지 번호
	
	// 화면에서 페이지 번호를 클릭하면, 요청하는 URL의 Query String에 
	// 페이지 번호와 페이지 당 게시글의 수를 조립하는 메소드
	// <예>
	// 화면 페이지 클릭 : 3
	// 결과  : ?pageNum=3&rowsPerPage=10
	public String makeQuery(int page) {
		UriComponents uriComp = UriComponentsBuilder.newInstance()
				.queryParam("pageNum", page)
				.queryParam("rowsPerPage", criteria.getRowsPerPage())
				.build();
		
		return uriComp.toString();
	}
	
	public Criteria getCriteria() {
		return criteria;
	}
	public void setCriteria(Criteria criteria) {
		this.criteria = criteria;
	}
	public int getTotalCount() {
		return totalCount;
	}
	
	// 전체 게시글의 수가 정해지면 다른 멤버 변수 초기화
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
		
		// 멤버변수 초기화
		fieldInit();
	}
	
	//끝 페이지 번호 계산 , 3/10 = 0.3 => 올림 => 1 * 페이지수 = 10
//					8/10 = 0.8 => 올림 => 1 * 페이지 수 = 10
//					15/10 = 1.5 => 올림 => 2 * 페이지 수 = 20
//					22/10 = 2.2 => 올림 => 3 * 페이지 수 = 30
//					215/10 = 21.5 => 올림 => 22 * 페이지 수 = 220
	public void fieldInit() {
		// (1) 끝 페이지 번호 계산
		 				//올림(현재 페이지 번호 / 한 화면에 표시할 페이지 버튼의 수)* 한 화면에 표시할 페이지 버튼의 수
		endPage = (int) (Math.ceil(criteria.getPageNum() / (double)cntPageNum)*cntPageNum);
		
		// (2) 시작 페이지 계산
				// (끝 페이지 번호 - 한 화면에 표시할 페이지 버튼의 수 + 1)
		startPage = endPage - cntPageNum + 1;
		
		// (3) 실제 끝 페이지 번호 계산
		 		//	올림(전체 게시글의 수 / 페이지 당 게시글의 수)
		realEndPage = (int) (Math.ceil(totalCount / (double)criteria.getRowsPerPage()));
		
		// (4) 실제 끝 페이지 번호로 endPage 값 수정
		if (endPage > realEndPage)
			endPage = realEndPage;
		
		// (5) 이전, 다음 버튼 유무 표시
		prev = startPage == 1 ? false : true; // 시작 페이지가 1일 경우 '이전' 버튼 없음
		next = endPage * criteria.getRowsPerPage() < totalCount ? true : false;
		
		
	}
	
	
	public int getStartPage() {
		return startPage;
	}
	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}
	public int getEndPage() {
		return endPage;
	}
	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}
	public boolean isPrev() {
		return prev;
	}
	public void setPrev(boolean prev) {
		this.prev = prev;
	}
	public boolean isNext() {
		return next;
	}
	public void setNext(boolean next) {
		this.next = next;
	}
	public int getCntPageNum() {
		return cntPageNum;
	}
	public void setCntPageNum(int cntPageNum) {
		this.cntPageNum = cntPageNum;
	}
	public int getRealEndPage() {
		return realEndPage;
	}
	public void setRealEndPage(int realEndPage) {
		this.realEndPage = realEndPage;
	}
	
	@Override
	public String toString() {
		return "PageMaker [criteria=" + criteria + ", totalCount=" + totalCount + ", startPage=" + startPage
				+ ", endPage=" + endPage + ", prev=" + prev + ", next=" + next + ", cntPageNum=" + cntPageNum
				+ ", realEndPage=" + realEndPage + "]";
	}
	
	
	
}
