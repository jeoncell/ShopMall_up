package com.ezen.biz.service;

import java.util.List;

import com.ezen.biz.dto.AddressVO;

public interface AddressService {

	//주소 확인
	List<AddressVO> selectAddressByDong(String dong);

}