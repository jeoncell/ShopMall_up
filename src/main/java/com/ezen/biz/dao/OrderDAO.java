package com.ezen.biz.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ezen.biz.dto.OrderVO;
import com.ezen.biz.dto.SalesQuantity;

@Repository
public class OrderDAO {
	
	@Autowired
	private SqlSessionTemplate mybatis;
	
	public int selectMaxOseq() {
		
		return mybatis.selectOne("OrderMapper.selectMaxOseq");
	}

	public void insertOrder(OrderVO vo) {
		
		mybatis.insert("OrderMapper.insertOrder",vo);
	}

	public void insertOrderDetail (OrderVO vo){
		
		mybatis.insert("OrderMapper.insertOrderDetail",vo);
	}

	public List<OrderVO> listOrderById(OrderVO vo){
		
		return mybatis.selectList("OrderMapper.listOrderById", vo);
	}

	public List<Integer> selectSeqOrdering(OrderVO vo){
		
		return mybatis.selectList("OrderMapper.selectSeqOrdering",vo);
	}
	
	public List<OrderVO> listOrder(String mname){
	
		return mybatis.selectList("OrderMapper.listOrder",mname);
	}
	
	public void updateOrderResult(int odseq) {
		
		mybatis.update("OrderMapper.updateOrderResult",odseq);
	}
	
	// 제품별 실적 조회
	public List<SalesQuantity> getProductSales() {
		return mybatis.selectList("OrderMapper.listProductSales");
	}
	
}
