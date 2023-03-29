package com.ezen.biz.service;

import java.util.List;

import com.ezen.biz.dto.CartVO;

public interface CartService {

	// 장바구니에 담기
	void insertCart(CartVO vo);

	// 장바구니 목록
	List<CartVO> getlistCart(String id);

	// 장바구니 취소
	void deleteCart(int cseq);

	//
	void updateCart(int cseq);

}