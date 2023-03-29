package com.ezen.view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ezen.biz.dto.MemberVO;
import com.ezen.biz.dto.ProductCommentVO;
import com.ezen.biz.service.CommentService;

import utils.Criteria;
import utils.PageMaker;

@RestController //rest controller
@RequestMapping("/comments") //comments로 들어온 url만을 제어하겠다.
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
	@GetMapping(value="/list", produces="application/json; charset=UTF-8")
	public Map<String, Object> commentList(ProductCommentVO vo, Criteria criteria) {
		Map<String, Object> commentInfo = new HashMap<>();
		
		// 상품 댓글 목록 조회
		List<ProductCommentVO> commentList 
				= commentService.getCommentListWithPaging(criteria, vo.getPseq());
		
		// 페이지 정보 작성
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCriteria(criteria);
		
		pageMaker.setTotalCount(commentService.getCountCommentList(vo.getPseq()));
		
		commentInfo.put("total", commentList.size());
		commentInfo.put("commentList", commentList);
		commentInfo.put("pageInfo", pageMaker);
		
		return commentInfo;
	}
	

	@PostMapping(value="/save")
	public String saveCommentAction(ProductCommentVO commentVO, HttpSession session) {
		
		MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
		
		if (loginUser == null) {
			
			return "not_logedin";
		} else {
			
			commentVO.setWriter(loginUser.getId());	// 사용자 ID 저장
			
			// 상품평 저장
			if (commentService.saveComment(commentVO) > 0) {
				return "success";
			} else {
				return "fail";
			}
		}
	}
	
	
}
