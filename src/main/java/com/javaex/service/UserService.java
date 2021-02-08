package com.javaex.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaex.dao.UserDao;
import com.javaex.vo.UserVo;

@Service
public class UserService {

	@Autowired
	private UserDao userDao;
	
	//회원가입
	public int join(UserVo userVo) {
		System.out.println("[UserService] join");
		
		return userDao.insert(userVo);
	}
	
	//로그인
	public UserVo login(UserVo userVo) {
		System.out.println("[UserService] login");
		
		return userDao.selectUser(userVo);
	}
	
	//수정 폼
	public UserVo modifyForm(int no) {
		System.out.println("[UserService] modifyForm");
		
		return userDao.selectUser(no);
	}
	
	//수정
	public int modify(UserVo userVo) {
		System.out.println("[UserService] modify");
		
		return userDao.updateUser(userVo);
	}
	
	//회원가입 id중복체크
	public String idcheck(String id) {
		System.out.println("[UserService] idcheck : " + id);
		
		String result;
		UserVo userVo = userDao.selectOne(id);
		
		if(userVo == null) { //사용할 수 있는 id
			result = "can";
		}else { //사용할 수 없는 id
			result = "cant";
		}
		
		return result;
	}
}
