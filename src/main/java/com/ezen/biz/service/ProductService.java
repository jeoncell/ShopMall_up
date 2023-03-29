package com.ezen.biz.service;

import java.util.List;

import com.ezen.biz.dto.ProductVO;

import utils.Criteria;

public interface ProductService {
//interface 는 모든 메서드는 public 이라서 따로 작성하지 않아도 된다.
	ProductVO getProduct(ProductVO product);

	List<ProductVO> getProductListByKind(String kind);

	List<ProductVO> getNewProductList();

	List<ProductVO> getBestProductList();
	
	int countProductList(String name);
	
	List<ProductVO> getListProduct(String name);
	
	void insertProduct(ProductVO vo);
	
	void updateProduct(ProductVO vo);
	
	List<ProductVO> getlistProductWithPaging(Criteria criteria, String name);
}