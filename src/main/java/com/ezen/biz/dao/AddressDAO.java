package com.ezen.biz.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ezen.biz.dto.AddressVO;

@Repository("addressDAO")
public class AddressDAO {

	@Autowired
	SqlSessionTemplate mybatis;
	//주소 확인
	public List<AddressVO> selectAddressByDong(String dong){
			
		return mybatis.selectList("MemberMapper.selectAddressByDong", dong);
	}
}
