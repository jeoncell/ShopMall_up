package com.ezen.biz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezen.biz.dao.AdminDAO;
import com.ezen.biz.dto.AdminVO;

@Service("adminService")
public class AdminServiceImpl implements AdminService {

	@Autowired
	private AdminDAO adminDAO;
	
	@Override
	public int adminCheck(AdminVO vo) {
		String pwd_in_db = adminDAO.adminCheck(vo.getId()); 
		if(pwd_in_db==null)
			return -1; // 관리자 id 존재x
		else if (pwd_in_db.equals(vo.getPwd())) 
			return 1;  // 정상적인 관리자
		else return 0; // 비밀번호 틀림
	}

	@Override
	public AdminVO getAdmin(String id) {
		
		return adminDAO.getAdmin(id);
	}

}
