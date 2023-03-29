package com.ezen.biz.service;

import java.util.List;

import com.ezen.biz.dto.MemberVO;

public interface MemberService {

	// 회원 상세정보 조회
	MemberVO getMember(String id);

	// 회원ID 존재 여부 확인
	int confirmID(String id);
	
	// 회원 인증
	public int loginID(MemberVO vo);
	
	// 회원 추가
	void insertMember(MemberVO vo);
	
	// 이름과 이메일로 아이디 찾기
	public String selectIdByNameEmail(MemberVO vo);
	
	// 아이디, 이름, 이메일로 비밀번호 찾기
	public String selectPwdByIdNameEmail(MemberVO vo);
	
	// 비밀번호 변경
	public void changePwd(MemberVO vo);
	
	//전체 회원목록
	public List<MemberVO> getListMember(String name);
}