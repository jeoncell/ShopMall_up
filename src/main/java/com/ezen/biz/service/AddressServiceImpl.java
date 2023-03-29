package com.ezen.biz.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezen.biz.dao.AddressDAO;
import com.ezen.biz.dto.AddressVO;

@Service
public class AddressServiceImpl implements AddressService {
	@Autowired
	private AddressDAO aDao;
	
	@Override
	public List<AddressVO> selectAddressByDong(String dong) {
		
		return aDao.selectAddressByDong(dong);
	}

}
