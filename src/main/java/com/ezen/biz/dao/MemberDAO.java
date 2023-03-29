package com.ezen.biz.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ezen.biz.dto.MemberVO;

@Repository
public class MemberDAO {
	
	@Autowired
	SqlSessionTemplate mybatis;
	
	
	// 회원 상세정보 조회
	public MemberVO getMember(String id) {
		
		return mybatis.selectOne("MemberMapper.getMember",id);
	}
	
	// 회원ID 존재 여부 확인
	public int confirmID(String id) {
		
		String pwd = mybatis.selectOne("MemberMapper.confirmID",id);
		
		if(pwd !=null)
		return 1; // id가 존재
		else return 0;
	}
	
	//회원 로그인 확인
	public int loginID(MemberVO vo) {
		int result = -1;
		String pwd = mybatis.selectOne("MemberMapper.confirmID",vo);
		
		if(pwd==null) { // id가 존재하지 않음.
			result = -1;
		} else if(pwd.equals(vo.getPwd())) {// 정상 로그인
			result = 1;
		} else {
			result = 0; // 비밀번호 불일치
		}
		return result;
	}
	
	// 회원 추가
	public void insertMember(MemberVO vo) {
		
		mybatis.insert("MemberMapper.insertMember", vo);
	}
	
	// 이름과 이메일로 아이디 찾기
	public String selectIdByNameEmail(MemberVO vo) {
		
		return mybatis.selectOne("MemberMapper.selectIdByNameEmail",vo);
	}
	
	// 아이디, 이름, 이메일로 비밀번호 찾기
	public String selectPwdByIdNameEmail(MemberVO vo) {
		
		return mybatis.selectOne("MemberMapper.selectPwdByIdNameEmail",vo);
	}
	
	// 비밀번호 변경
	public void changePwd(MemberVO vo) {
		
		mybatis.update("MemberMapper.changePwd",vo);
	}
	
	// 전체 회원 목록
	public List<MemberVO> listMember(String name) {
		return mybatis.selectList("MemberMapper.listMember",name);
	}
	
	
}
