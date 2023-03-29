package com.ezen.view;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.ezen.biz.dto.AddressVO;
import com.ezen.biz.service.AddressService;

@Controller
public class AddressController {
	
	/*
	 * @Autowired private AddressService addressService;
	 * 
	 * // 주소 찾기 화면 표시
	 * 
	 * @GetMapping("/find_zip_num") public String findZipNumView() {
	 * 
	 * return "member/findZipNum"; }
	 * 
	 * // 주소 찾기 화면 표시
	 * 
	 * @PostMapping("/find_zip_num") public String findZipNumAction(AddressVO vo,
	 * Model model) { List<AddressVO> addressList =
	 * addressService.selectAddressByDong(vo.getDong());
	 * 
	 * model.addAttribute("addressList", addressList);
	 * 
	 * return "member/findZipNum"; }
	 */

}
