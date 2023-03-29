package com.ezen.biz.service;

import java.util.List;

import com.ezen.biz.dto.QnaVO;

public interface QnaService {

	List<QnaVO> getlistQna(String id);

	QnaVO getQna(int qseq);

	void insertQna(QnaVO vo);
	
	public List<QnaVO> getListAllQna();
	
	public void updateQna(QnaVO vo);
}