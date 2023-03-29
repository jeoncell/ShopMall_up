package com.ezen.biz.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezen.biz.dao.ProductDAO;
import com.ezen.biz.dto.ProductVO;

import utils.Criteria;

@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	private ProductDAO pDao;
	
	@Override
	public ProductVO getProduct(ProductVO product) {
		
		return pDao.getProduct(product);
	}

	@Override
	public List<ProductVO> getProductListByKind(String kind) {
		
		return pDao.getProductListByKind(kind);
	}

	@Override
	public List<ProductVO> getNewProductList() {
		
		return pDao.getNewProductList();
	}

	@Override
	public List<ProductVO> getBestProductList() {
		
		return pDao.getBestProductList();
	}

	@Override
	public int countProductList(String name) {
		
		return pDao.countProductList(name);
	}

	@Override
	public List<ProductVO> getListProduct(String name) {
		
		return pDao.listProduct(name);
	}

	@Override
	public void insertProduct(ProductVO vo) {
		
		pDao.insertProduct(vo);
	}

	@Override
	public void updateProduct(ProductVO vo) {
		
		pDao.updateProduct(vo);
	}

	@Override
	public List<ProductVO> getlistProductWithPaging(Criteria criteria, String name) {
		
		return pDao.listProductWithPaging(criteria, name);
	}

}
