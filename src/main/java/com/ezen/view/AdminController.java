package com.ezen.view;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;

import com.ezen.biz.dto.AdminVO;
import com.ezen.biz.dto.MemberVO;
import com.ezen.biz.dto.OrderVO;
import com.ezen.biz.dto.ProductVO;
import com.ezen.biz.dto.QnaVO;
import com.ezen.biz.dto.SalesQuantity;
import com.ezen.biz.service.AdminService;
import com.ezen.biz.service.MemberService;
import com.ezen.biz.service.OrderService;
import com.ezen.biz.service.ProductService;
import com.ezen.biz.service.QnaService;

import utils.Criteria;
import utils.PageMaker;

@Controller
@SessionAttributes("admin")
public class AdminController {
	@Autowired
	private AdminService adminService;
	@Autowired
	private ProductService productService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private MemberService memberService;
	@Autowired
	private QnaService qnaService;
	
	@GetMapping("/admin_login_form")
	public String adminLoginView(HttpSession session) {
		
		return "admin/main";
	}
	
	@PostMapping("/admin_login")
	public String adminLogin(AdminVO vo, Model model) {
		// (1) 관리자 ID 인증
		int result=adminService.adminCheck(vo);
		
		// (2) 정상적인 관리자이면 
		// - 관리자 정보 조회
		// - 상품목록 화면으로 이동
		if (result == 1) {
			model.addAttribute("admin", adminService.getAdmin(vo.getId()));
			return "redirect:admin_product_list";
		} else {
			// (3) 비정상적인 로그인 일때
			// - 메시지를 설정하고 로그인화면으로 이동
			if(result==0) {
				model.addAttribute("message", "비밀번호를 확인해주세요.");
			} else {
				model.addAttribute("message", "아이디를 확인해주세요.");
			}
			return "admin/main";
		}
		
	}
	
	@GetMapping("admin_logout")
	public String adminLogout(SessionStatus status) {
		status.setComplete();
		
		return "admin/main";
	}
	
	
	// 페이징 처리 전 소스
//	@GetMapping("/admin_product_list")
//	public String adminProductList( //productList.jsp 에 key를 param
//			@RequestParam(value="key", defaultValue="") String name, Model model) {
//		// (1) 전체 상품목록 조회
//		List<ProductVO> productList = productService.getListProduct(name);
//		
//		// (2) 상품 목록저장  --- productList.jsp 에 테이블이 productList 및 Size
//		model.addAttribute("productList", productList);
//		model.addAttribute("productListSize", productList.size());
//		
//		// (3) 화면 호출 : productList.jsp
//		
//		return "admin/product/productList";
//		
//	}
	
	//get,post 둘다 처리가능 <-- RequestMapping
	@RequestMapping("/admin_product_list")
	public String adminProductList( //productList.jsp 에 key를 param
			@RequestParam(value="pageNum", defaultValue="1") String pageNum, 
			@RequestParam(value="rowsPerPage", defaultValue="10") String rowsPerPage, 
			@RequestParam(value="key", defaultValue="") String name, 
			 Model model) {
		Criteria criteria= new Criteria();
		criteria.setPageNum(Integer.parseInt(pageNum));
		criteria.setRowsPerPage(Integer.parseInt(rowsPerPage));
		
		
		// (1) 전체 상품목록 조회
		List<ProductVO> productList = productService.getlistProductWithPaging(criteria, name);
		
		// (2) 화면에 표시할 페이지 버튼 정보 설정(PageMaker 클래스 이용)
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCriteria(criteria); // 현재 페이지 정보 저장
		pageMaker.setTotalCount(productService.countProductList(name)); // 전체 게시글의 수 저장
		
		// (2) model 객체에 상품 목록저장  --- productList.jsp 에 테이블이 productList 및 Size
		model.addAttribute("productList", productList);
		model.addAttribute("productListSize", productList.size());
		model.addAttribute("pageMaker", pageMaker);
		
		// (3) 화면 호출 : productList.jsp
		
		return "admin/product/productList";
		
	}
	
	
	//상품등록 화면표시
	@PostMapping("/admin_product_write_form")
	public String adminProductWriteView(Model model) {
		String[] kindList = {"CPU","메인보드","그래픽카드","파워","조립 PC","세일상품"};
		model.addAttribute("kindList",kindList);
		return "admin/product/productWrite";
	}
	// 상품 등록처리
	@PostMapping("/admin_product_write")
	public String adminProductWrite(ProductVO vo, HttpSession session, 
			@RequestParam(value="product_image") MultipartFile uploadFile) {
		
		if(!uploadFile.isEmpty()) {
			String fileName = uploadFile.getOriginalFilename();
			vo.setImage(fileName); // 테이블에 파일명 저장 용도
			
			// 전송된 이미지 파일을 이동할 실제 경로 구하기 -- ServletContext 는 webapp 까지 .. 이후는 지정
			String image_path = session.getServletContext().getRealPath("WEB-INF/resources/product_images/");
			try {
				uploadFile.transferTo(new File(image_path + fileName));
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}
		}
		productService.insertProduct(vo);
		return "redirect:admin_product_list";
	}
	
	@RequestMapping("/admin_product_detail")
	public String adminProductDetailAction(ProductVO vo, Model model) {
		String[] kindList = {"", "CPU","메인보드","그래픽카드","파워","조립 PC","세일상품"};
		ProductVO product = productService.getProduct(vo);
		model.addAttribute("productVO",product);//productDetail에 사용된 productVO 보고 작성.
		int index = Integer.parseInt(product.getKind());
		model.addAttribute("kind", kindList[index]);
		
		return "admin/product/productDetail";
	}
	
	@RequestMapping("/admin_product_update_form")
	public String adminProductUpdateView(ProductVO vo, Model model) {
		String[] kindlist = {"CPU","메인보드","그래픽카드","파워","조립 PC","세일상품"};
		
		ProductVO product = productService.getProduct(vo);
		model.addAttribute("productVO", product);
		model.addAttribute("kindList", kindlist);
		return "admin/product/productUpdate";
	}
	
	@PostMapping("/admin_product_update")
	public String adminProductUpdate(ProductVO vo, 
			@RequestParam(value="product_image") MultipartFile uploadFile,
			@RequestParam(value="nonmakeImg") String org_image,
			HttpSession session) {
		
		if(!uploadFile.isEmpty()) { // 상품 이미지를 수정한 경우임.
			String fileName = uploadFile.getOriginalFilename();
			vo.setImage(fileName); // 테이블에 파일명 저장 용도
			
			// 전송된 이미지 파일을 이동할 실제 경로 구하기 -- ServletContext 는 webapp 까지 .. 이후는 지정
			String image_path = session.getServletContext().getRealPath("WEB-INF/resources/product_images/");
			try {
				uploadFile.transferTo(new File(image_path + fileName));
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}
		} else { // 상품의 기존 이미지 사용
			vo.setImage(org_image);
		}
		// 베스트 상품, 신규 상품 처리
		if (vo.getUseyn() == null) {
			vo.setUseyn("n");
		} else {
			vo.setUseyn("y");
		}
		
		if (vo.getBestyn()==null) {
			vo.setBestyn("n");
		} else {
			vo.setBestyn("y");
		}
		productService.updateProduct(vo);
		return "redirect:admin_product_list";
	}
	
	@RequestMapping("/admin_order_list")
	public String adminOrderList(@RequestParam(value="key",defaultValue="") String mname,
			Model model) {
		
		List<OrderVO> orderList = orderService.getListOrder(mname);
		model.addAttribute("orderList", orderList);
		
		return "admin/order/orderList";
	}
	
	//주문완료 처리
	@RequestMapping("/admin_order_save")
	public String adminOrderSave(@RequestParam(value="result") int[] odseq) {
		
		for(int i=0;i<odseq.length;i++) {
			orderService.updateOrderResult(odseq[i]);
		}
		
		return "redirect:admin_order_list";
	}
	
	@RequestMapping("/admin_member_list")
	public String memberList(@RequestParam(value="key", defaultValue="") String name, Model model) {
		List<MemberVO> memberlist = memberService.getListMember(name);
		model.addAttribute("memberList",memberlist );
		return "admin/member/memberList";
	}
	
	
	@RequestMapping("/admin_qna_list")
	public String adminQnaList(Model model) {
		List<QnaVO> qnaList= qnaService.getListAllQna();
		
		model.addAttribute("qnaList", qnaList);
		
		return "admin/qna/qnaList";
	}
	
	@PostMapping("/admin_qna_detail")
	public String adminQnaDetail(QnaVO vo, Model model) {
		QnaVO qna = qnaService.getQna(vo.getQseq());
		model.addAttribute("qnaVO", qna);
		
		return "admin/qna/qnaDetail";
	}
	
	@PostMapping("/admin_qna_repsave")
	public String adminQnaRepSave(QnaVO vo) {
		
		qnaService.updateQna(vo);
		
		return "redirect:admin_qna_list";
	}
	
	// 상품별 판매 실적 화면 출력
	@RequestMapping("/admin_sales_record_form")
	public String adminProductSalesForm() {
		
		return "admin/order/salesRecords";
	}
	
	@RequestMapping("/sales_record_chart")
	@ResponseBody // 화면이 아닌 데이터를 리턴하는 메소드로 지정
	public List<SalesQuantity> salesRecordChart() {
		List<SalesQuantity> listSales =orderService.getProductSales();
		
		return listSales;
	}
	

}
