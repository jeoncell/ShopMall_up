package com.ezen.view;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.ezen.biz.dto.MemberVO;
import com.ezen.biz.dto.QnaVO;
import com.ezen.biz.service.QnaService;

@Controller
public class QnaController {
	
	@Autowired
	private QnaService qnaService;
	
	@GetMapping("/qna_list")
	public String qnaList(HttpSession session, QnaVO vo, Model model) {
		// 사용자 로그인 확인
		MemberVO loginUser = (MemberVO)session.getAttribute("loginUser");
		
		if(loginUser == null) { // 로그인이 안된 경우
			return "member/login";
		} else { 
			// 사용자 아이디를 매개변수로 QNA 목록조회
			List<QnaVO> qnaList = qnaService.getlistQna(loginUser.getId());

			// 화면에 전달할 데이터를 "qnaList" 속성을 저장
			model.addAttribute("qnaList", qnaList);
			
			// qna/qnaList.jsp 표시			
			return "qna/qnaList";
		}	
	}
	
	//QnA 게시글 상세보기
	@GetMapping("/qna_view")
	public String qnaView(HttpSession session, QnaVO vo, Model model) {
		// 사용자 로그인 확인
		MemberVO loginUser = (MemberVO)session.getAttribute("loginUser");
		
		if(loginUser == null) { // 로그인이 안된 경우
			return "member/login";
		} else { 
		// 화면에서 입력받은 qseq를 입력 파라미터로 하여 Qna 상세 조회
			QnaVO qna = qnaService.getQna(vo.getQseq());
			
		// 조회결과를 qnaVO 속성에 저장
			model.addAttribute("qnaVO", qna);
			
		// qna/qnaView.jsp 화면 표시
			return "qna/qnaView";
		}
	
	}	
	
	@GetMapping("/qna_write_form")
	public String qnaWriteView(HttpSession session) {
		// 사용자 로그인 확인
		MemberVO loginUser = (MemberVO)session.getAttribute("loginUser");
		
		if(loginUser == null) { // 로그인이 안된 경우
			return "member/login";
		} else { 
			return "qna/qnaWrite";
		}
	}
	
	@PostMapping("/qna_write")
	public String qnaWriteAction(QnaVO vo, HttpSession session) {
		//사용자 로그인 확인
		MemberVO loginUser = (MemberVO)session.getAttribute("loginUser");
		
		if(loginUser == null) { // 로그인이 안된 경우
			return "member/login";
		} else { 
			vo.setId(loginUser.getId());
			qnaService.insertQna(vo);
			
			return "redirect:qna_list";
			//redirect 는 위에 리스트를 보여주는 qna_list가 있을 때 사용하여 불러준다.
		}
	}
	
	
	
	
	
	
	
	
}