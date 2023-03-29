package com.ezen.view;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ezen.biz.dto.CartVO;
import com.ezen.biz.dto.MemberVO;
import com.ezen.biz.dto.OrderVO;
import com.ezen.biz.service.CartService;
import com.ezen.biz.service.OrderService;

@Controller
public class MypageController {
	
	@Autowired
	private CartService cartService;
	@Autowired
	private OrderService orderService;
	
	@PostMapping("/cart_insert")
	public String insertCart(CartVO vo, HttpSession session) {
		// 세션에 사용자 정보가 저장되어 있는지 확인(로그인 여부)
		MemberVO loginUser = (MemberVO)session.getAttribute("loginUser");
		
		if(loginUser ==null) { // 로그인이 안된 경우
			return "member/login";
		} else {
			vo.setId(loginUser.getId());
			cartService.insertCart(vo);
			
			return "redirect:cart_list";
		}
		
	}
	
	@GetMapping("/cart_list")
	public String listCart(HttpSession session, Model model) {
		// 로그인 확인
		MemberVO loginUser = (MemberVO)session.getAttribute("loginUser");
		if(loginUser ==null) {
			return "member/login";
		} else {
			// Cart List 조회 후
			List<CartVO> cartList =cartService.getlistCart(loginUser.getId());
			
			//장바구니 총액 계산
			int totalAmount = 0;
			for(CartVO vo : cartList) {
				totalAmount += vo.getQuantity() *vo.getPrice2();
			}
			
			// "cartList" 속성에 조회한 데이터를 저장
			model.addAttribute("cartList", cartList);
			model.addAttribute("totalPrice", totalAmount);
			// cartList.jsp 화면 호출
			return "mypage/cartList";
		}
		
	}
	
	@PostMapping("/cart_delete")
	public String cartDelete(@RequestParam(value="cseq") int[] cseq) {
		for(int i=0; i<cseq.length; i++) {
//			System.out.print(cseq[i] + ",");
			cartService.deleteCart(cseq[i]);
		}
		System.out.println();
		return "redirect:cart_list";
	}
	
	@PostMapping("/order_insert")
	public String orderInsert(HttpSession session, OrderVO order, Model model) {
		// 로그인 확인
		MemberVO loginUser = (MemberVO)session.getAttribute("loginUser");
		if(loginUser ==null) {
			return "member/login";
		} else {
			// order 객체에 사용자 ID 설정
			order.setId(loginUser.getId());
			int oseq = orderService.insertOrder(order);
			
			model.addAttribute("oseq",oseq);
			
			return "redirect:order_list";
		}
	}
	
	@GetMapping("/order_list")
	public String orderListAction(HttpSession session, OrderVO vo, Model model) {
		
//		orderService , getListOrderById 호출, 주문내역 조회
//		orderList : 주문내역 / totalPrice : 총주문금액
		// 로그인 확인
		MemberVO loginUser = (MemberVO)session.getAttribute("loginUser");
		if(loginUser ==null) {
			return "member/login";
		} else {
			// order 객체에 사용자 ID 설정
			vo.setId(loginUser.getId());
			vo.setResult("1");// 처리결과 : 미완료
			List<OrderVO> orderList=orderService.getListOrderById(vo);
			
			//주문 총액 계산
			int totalAmount = 0;
			
			for(OrderVO voa : orderList) {
			totalAmount = voa.getPrice2()*voa.getQuantity();
			}
			model.addAttribute("orderList", orderList);
			model.addAttribute("totalPrice", totalAmount);
		}
		return "mypage/orderList";
	
	}
	
	@GetMapping("/mypage")
	public String myPageView(HttpSession session, OrderVO vo, Model model) {
		// 로그인 확인
		MemberVO loginUser = (MemberVO)session.getAttribute("loginUser");
		if(loginUser == null) { // 로그인이 안된 경우
			return "member/login";
		} else {
			// (1) 사용자의 진행중인 주문번호 목록 조회
			vo.setId(loginUser.getId());
			vo.setResult("1"); // 처리결과 : "미처리"
			List<Integer> oseqList = orderService.getSeqOrdering(vo);
			
			// (2) 위의 각 주문번호에 대해 주문정보 조회 및 요약정보 생성
			List<OrderVO> summaryList = new ArrayList<>(); // 주문 요약정보 목록 저장
			for(int oseq : oseqList) {
				OrderVO order = new OrderVO();
				
				// 각 주문번호의 주문내역 조회
				order.setId(loginUser.getId());
				order.setOseq(oseq);
				order.setResult("1"); // 처리결과 : "미처리"
				List<OrderVO> orderList = orderService.getListOrderById(order);
				
				// 각 주문요약 정보 생성
				OrderVO summary = new OrderVO();
				summary.setOseq(orderList.get(0).getOseq());
				summary.setIndate(orderList.get(0).getIndate());
				
				// 상풍명 요약
				if (orderList.size() >=2) {
					summary.setPname(orderList.get(0).getPname() + " 외" + (orderList.size()-1) + " 건");
				} else { // 1개일 경우
					summary.setPname(orderList.get(0).getPname());
				}
				
				// 각 주문별 합계금액
				int amount = 0;
				for(int i=0; i<orderList.size(); i++) {
					amount += orderList.get(i).getQuantity() * orderList.get(i).getPrice2();
				}
				summary.setPrice2(amount);
				
				// 각 주문 요약정보를 리스트에 추가
				summaryList.add(summary);
			}
			
			// (3) 화면에 전달할 데이터 저장 및 화면 표시
			model.addAttribute("orderList", summaryList);
			model.addAttribute("title", "진행중인 주문내역");
		}
		return "mypage/mypage";
	}//myPageView
	
	@GetMapping("/order_detail")
	public String orderDetail(OrderVO vo, HttpSession session, Model model) {
		// 로그인 확인
		MemberVO loginUser = (MemberVO)session.getAttribute("loginUser");
		if(loginUser == null) { // 로그인이 안된 경우
			return "member/login";
		} else {
			// (1) 주문번호에 대한 주문목록 조회
			vo.setId(loginUser.getId());
			vo.setResult("");    // 처리 결과 : 처리, 미처리 모두 조회
			List<OrderVO> orderList = orderService.getListOrderById(vo);
			
			// (2) 주문자 정보 생성
			OrderVO orderDetail = new OrderVO();
			orderDetail.setOseq(orderList.get(0).getOseq());
			orderDetail.setIndate(orderList.get(0).getIndate());
			orderDetail.setMname(orderList.get(0).getMname());
			
			// (3) 주문총액 계산
			int totalAmount = 0;
			for(int i=0; i<orderList.size(); i++) {
				totalAmount += orderList.get(i).getQuantity()*orderList.get(i).getPrice2();
				
			}
			// (4) 화면에 출력할 정보 저장
			model.addAttribute("title", "My Page(주문 상세 정보)");
			model.addAttribute("orderDetail", orderDetail);
			model.addAttribute("totalPrice", totalAmount);
			model.addAttribute("orderList", orderList);
			
			
			return "mypage/orderDetail";
		}
		
		
	}
	
	@GetMapping("/order_all")
	public String orderAllView(HttpSession session, OrderVO vo, Model model) {
		// 로그인 확인
		MemberVO loginUser = (MemberVO)session.getAttribute("loginUser");
		if(loginUser == null) { // 로그인이 안된 경우
			return "member/login";
		} else {
			// (1) 사용자의 진행중인 주문번호 목록 조회
			vo.setId(loginUser.getId());
			vo.setResult(""); // 처리결과 : 모든 result
			List<Integer> oseqList = orderService.getSeqOrdering(vo);
			
			// (2) 위의 각 주문번호에 대해 주문정보 조회 및 요약정보 생성
			List<OrderVO> summaryList = new ArrayList<>(); // 주문 요약정보 목록 저장
			for(int oseq : oseqList) {
				OrderVO order = new OrderVO();
				
				// 각 주문번호의 주문내역 조회
				order.setId(loginUser.getId());
				order.setOseq(oseq);
				order.setResult(""); // 처리결과 : 모든 result
				List<OrderVO> orderList = orderService.getListOrderById(order);
				
				// 각 주문요약 정보 생성
				OrderVO summary = new OrderVO();
				summary.setOseq(orderList.get(0).getOseq());
				summary.setIndate(orderList.get(0).getIndate());
				
				// 상풍명 요약
				if (orderList.size() >=2) {
					summary.setPname(orderList.get(0).getPname() + " 외" + (orderList.size()-1) + " 건");
				} else { // 1개일 경우
					summary.setPname(orderList.get(0).getPname());
				}
				
				// 각 주문별 합계금액
				int amount = 0;
				for(int i=0; i<orderList.size(); i++) {
					amount += orderList.get(i).getQuantity() * orderList.get(i).getPrice2();
				}
				summary.setPrice2(amount);
				
				// 각 주문 요약정보를 리스트에 추가
				summaryList.add(summary);
			}
			
			// (3) 화면에 전달할 데이터 저장 및 화면 표시
			model.addAttribute("orderList", summaryList);
			model.addAttribute("title", "MY PAGE(총 주문내역)");
		}
		return "mypage/mypage";
	}
			
	
	
	
	
	
	
	
}// Controller
