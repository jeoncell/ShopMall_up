package com.ezen.view;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.ezen.biz.dto.AddressVO;
import com.ezen.biz.dto.MemberVO;
import com.ezen.biz.service.AddressService;
import com.ezen.biz.service.MemberService;

@Controller
@SessionAttributes("loginUser")
public class MemberController {
	
	@Autowired
	private MemberService memberService;
	
	//동의 약관 화면 표시
	@GetMapping("/contract")
	public String contractView() {
		
		return "member/contract";
	}
	
	//회원가입 화면 표시
	@PostMapping("/join_form")
	public String joinView() {
		
		return "member/join";
	}
	
	// ID 중복체크 화면 표시
	@GetMapping(value="/id_check_form")
	public String idCheckView(MemberVO vo, Model model) {
		// id 중복확인 조회
		int result = memberService.confirmID(vo.getId());
		
		model.addAttribute("id", vo.getId());
		model.addAttribute("message", result); // 1 : 이미 사용중 , -1 : 사용가능
		return "member/idcheck";
	}
	
	// ID 중복체크 수행
	@PostMapping("/id_check_form")
	public String idCheckAction(MemberVO vo, Model model) {
		// id 중복확인 조회
		int result = memberService.confirmID(vo.getId());
		
		model.addAttribute("id", vo.getId());
		model.addAttribute("message", result); // 1 : 이미 사용중 , -1 : 사용가능
		return "member/idcheck";
	}
	
	@Autowired
	private AddressService addressService;

	// 주소 찾기 화면 표시
	@GetMapping("/find_zip_num")
	public String findZipNumView() {
		
		return "member/findZipNum";
	}

	// 주소 찾기 화면 표시
	@PostMapping("/find_zip_num")
	public String findZipNumAction(AddressVO vo, Model model) {
		List<AddressVO> addressList = addressService.selectAddressByDong(vo.getDong());
		
		model.addAttribute("addressList", addressList);
		
		return "member/findZipNum";
	}
	
	// 회원가입 처리
	@PostMapping("/join")
	public String joinAction(MemberVO vo,
			@RequestParam(value="addr1", defaultValue="") String addr1,
			@RequestParam(value="addr2", defaultValue="") String addr2) {
		vo.setAddress(addr1+" "+addr2);
		memberService.insertMember(vo);
		return "member/login";
	}
	
	// 로그인 화면 출력
	@GetMapping("/login_form")
	public String loginView() {
		return "member/login";
	}
	
	@GetMapping("/find_id_form")
	public String findIdFormView() {
		return "member/findIdAndPassword";
	}
	

	
	
	
	@PostMapping("/find_id")
	public String findIdAction(MemberVO vo, Model model) {
		
		String id = memberService.selectIdByNameEmail(vo);

		if(id !=null) { // 아이디 조회 성공
			model.addAttribute("message", 1);
			model.addAttribute("id", id);
			
		}else {
			model.addAttribute("message", -1);
		}

		return "member/findResult"; //아이디 조회결과 화면표시
	}
	
	// 사용자 인증
	@PostMapping("/login")
	public String loginAction(MemberVO vo, Model model) {
		int result = memberService.loginID(vo);
		if(result==1) {//로그인 성공
			model.addAttribute("loginUser", memberService.getMember(vo.getId()));
			
			return "redirect:index";
		} else {
			return "member/login_fail";
		}
	}
	
	// 로그아웃
	@GetMapping("/logout")
	public String logout(SessionStatus status) {
		
		status.setComplete(); // 세션 해지
		
		return "member/login";
	}
	
	
//  비밀번호 알려주기	
//	@PostMapping("/find_pwd")
//	public String findPwActionView(MemberVO vo, Model model) {
//		
//		String pwd = memberService.selectPwdByIdNameEmail(vo);
//
//		if(pwd !=null) { // 아이디 조회 성공
//			model.addAttribute("message", 1);
//			model.addAttribute("pwd", pwd);
//			
//		}else {
//			model.addAttribute("message", -1);
//		}
//
//		return "member/findResult"; //아이디 조회결과 화면표시
//	}
	
	
	// 비밀번호 찾기 (비밀번호 재설정)
	@PostMapping("/find_pwd")
	public String findPwAction(MemberVO vo, Model model) {
		
		String pwd = memberService.selectPwdByIdNameEmail(vo);

		if(pwd !=null) { // 아이디 조회 성공
			model.addAttribute("message", 1);
			model.addAttribute("id", vo.getId());
			
		}else {
			model.addAttribute("message", -1);
		}

		return "member/findPwdResult"; //아이디 조회결과 화면표시
	}
	
	@PostMapping("/change_pwd")
	public String changePwdAction(MemberVO vo) {
		memberService.changePwd(vo);
		return "member/changePwdOk";
	}
	
	
	
}
