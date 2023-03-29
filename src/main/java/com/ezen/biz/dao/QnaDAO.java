package com.ezen.biz.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ezen.biz.dto.QnaVO;

@Repository
public class QnaDAO {
	
	@Autowired
	private SqlSessionTemplate mybatis;
	
	public List<QnaVO> listQna(String id){
		
		return mybatis.selectList("QnaMapper.listQna",id);
		 
	}
	
	public QnaVO getQna(int qseq) {
		return mybatis.selectOne("QnaMapper.getQna", qseq);
	}
	
	public void insertQna(QnaVO vo) {
		mybatis.insert("QnaMapper.insertQna",vo);
	}
	
	public List<QnaVO> listAllQna() {
		return mybatis.selectList("QnaMapper.listAllQna");
	}
	
	public void updateQna(QnaVO vo) {
		mybatis.update("QnaMapper.updateQna",vo);
	}
	
}
