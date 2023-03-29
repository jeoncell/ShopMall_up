package utils;

// 현재 페이지와 관련된 정보 저장
// - 현재 페이지 번호
// - 페이지당 게시글의 수
//

public class Criteria {
	private int pageNum; // 현재 페이지 번호
	private int rowsPerPage; // 페이지당 게시글의 수
	
	// 생성자 --super() 지우기..
	// 기본값  : 페이지 번호 = 1, 게시글의 수 = 10
	public Criteria() {
		this(1, 10);
	}
	
	public Criteria(int pageNum, int rowsPerPage) {
		this.pageNum = pageNum;
		this.rowsPerPage = rowsPerPage;
	}

	public int getPageNum() {
		return pageNum;
	}
	
	// 페이지 번호는 0이상 이어야함.
	public void setPageNum(int pageNum) {
		if (pageNum <= 0) {
			this.pageNum = 1;
		} else {
			this.pageNum = pageNum;	
		}
		
	}

	public int getRowsPerPage() {
		return rowsPerPage;
	}
	
	// 페이지당 게시글의 수 조절 (5~20 사이)
	public void setRowsPerPage(int rowsPerPage) {
		if(rowsPerPage <= 5) {
			this.rowsPerPage = 5;	
		} else if (rowsPerPage > 20) {
			this.rowsPerPage = 20;
		} else {
			this.rowsPerPage = rowsPerPage;
		}
	}
	
	
	
	
}
