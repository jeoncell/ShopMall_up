package com.ezen.biz.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezen.biz.dao.QnaDAO;
import com.ezen.biz.dto.QnaVO;

@Service("qnaService")
public class QnaServiceImpl implements QnaService {

	@Autowired
	private QnaDAO qDao;
	
	@Override
	public List<QnaVO> getlistQna(String id) {
		
		return qDao.listQna(id);
	}

	@Override
	public QnaVO getQna(int qseq) {
		
		return qDao.getQna(qseq);
	}

	@Override
	public void insertQna(QnaVO vo) {
		
		qDao.insertQna(vo);
	}

	@Override
	public List<QnaVO> getListAllQna() {
		
		return qDao.listAllQna();
	}

	@Override
	public void updateQna(QnaVO vo) {
		
		 qDao.updateQna(vo);
	}

}
