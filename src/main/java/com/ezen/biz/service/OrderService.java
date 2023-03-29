package com.ezen.biz.service;

import java.util.List;

import com.ezen.biz.dto.OrderVO;
import com.ezen.biz.dto.SalesQuantity;

public interface OrderService {

	int selectMaxOseq();

	int insertOrder(OrderVO vo);

	void insertOrderDetail(OrderVO vo);

	List<OrderVO> getListOrderById(OrderVO vo);

	List<Integer> getSeqOrdering(OrderVO vo);
	
	List<OrderVO> getListOrder(String mname);
	
	void updateOrderResult(int odseq);
	
	List<SalesQuantity> getProductSales();
}