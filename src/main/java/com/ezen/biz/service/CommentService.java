package com.ezen.biz.service;

import java.util.List;

import com.ezen.biz.dto.ProductCommentVO;

import utils.Criteria;

public interface CommentService {

	int getCountCommentList(int pseq);

	List<ProductCommentVO> getCommentListWithPaging(Criteria criteria, int pseq);

	public int saveComment(ProductCommentVO vo);
}