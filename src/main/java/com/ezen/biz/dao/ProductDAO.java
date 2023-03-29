package com.ezen.biz.dao;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ezen.biz.dto.ProductVO;

import utils.Criteria;

@Repository
public class ProductDAO {
	
	@Autowired
	SqlSessionTemplate mybatis;
	
	public ProductVO getProduct(ProductVO product) {
		
		return mybatis.selectOne("ProductMapper.getProduct", product);
	}
	
	public List<ProductVO> getProductListByKind(String kind) {
		
		return mybatis.selectList("ProductMapper.getProductListByKind", kind);
	}
	

	public List<ProductVO> getNewProductList() {
		
		return mybatis.selectList("ProductMapper.getNewProductList");
	}
	public List<ProductVO> getBestProductList() {
		
		return mybatis.selectList("ProductMapper.getBestProductList");
	}

	
	//name == 조회할 상품명
	public int countProductList(String name) {
		
		return mybatis.selectOne("ProductMapper.countProductList", name);
	}
	
	public List<ProductVO> listProduct(String name) {
		return mybatis.selectList("ProductMapper.listProduct", name);
	}
	
	public List<ProductVO> listProductWithPaging(Criteria criteria, String name) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("criteria", criteria);
		map.put("name", name);
		
		return mybatis.selectList("ProductMapper.listProductWithPaging", map);
	}
	
	public void insertProduct(ProductVO vo) {
		
		mybatis.insert("ProductMapper.insertProduct", vo);
	}
	
	public void updateProduct(ProductVO vo) {
		mybatis.update("ProductMapper.updateProduct", vo);
	}
	
}
