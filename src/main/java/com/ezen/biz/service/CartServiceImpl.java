package com.ezen.biz.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezen.biz.dao.CartDAO;
import com.ezen.biz.dto.CartVO;

@Service("cartService")
public class CartServiceImpl implements CartService {
	
	@Autowired
	private CartDAO cDao; 
	
	@Override
	public void insertCart(CartVO vo) {
		cDao.insertCart(vo);

	}

	@Override
	public List<CartVO> getlistCart(String id) {
		
		return cDao.listCart(id);
	}

	@Override
	public void deleteCart(int cseq) {
		
		cDao.deleteCart(cseq);
	}

	@Override
	public void updateCart(int cseq) {
		
		cDao.updateCart(cseq);
	}

}
