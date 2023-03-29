package com.ezen.view;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ezen.biz.dto.ProductVO;
import com.ezen.biz.service.ProductService;

@Controller
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@RequestMapping(value="/product_detail", method=RequestMethod.GET)
	public String productDetailAction(ProductVO product,Model model) {
		
		// 상품상세조회
		ProductVO productVO=productService.getProduct(product);
		
		// JSP에 결과 조회
		model.addAttribute("productVO",productVO);
		
		// JSP 화면 호출
		return "product/productDetail";
		
	}
	
	@GetMapping("/category")
	public String productKindAction(ProductVO product,Model model) {
		
		// 상품상세조회
		List<ProductVO> kindList=productService.getProductListByKind(product.getKind());
		
		// JSP에 결과 조회
		model.addAttribute("productKindList",kindList);
		
		// JSP 화면 호출
		return "product/productKind";
		
	}
}
