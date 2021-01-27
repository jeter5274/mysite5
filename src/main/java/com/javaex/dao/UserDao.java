package com.javaex.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javaex.vo.UserVo;

@Repository
public class UserDao {

	@Autowired
	private SqlSession sqlSession;
	
	//회원가입 -> 회원정보 저장
	public int insert(UserVo userVo) {
		
		System.out.println("[USER DAO] insert");
		System.out.println(userVo.toString());
		
		int count = sqlSession.insert("user.insert", userVo);
		
		return count;
	}
	
	//로그인 -> 회원정보 가져오기
	public UserVo selectUser(UserVo userVo) {
		System.out.println("[USER DAO] selectUser");
		System.out.println(userVo.toString());
		
		return sqlSession.selectOne("user.selectUser", userVo);
	}
}
