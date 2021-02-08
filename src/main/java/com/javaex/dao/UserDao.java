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
		//System.out.println(userVo.toString());
		
		int count = sqlSession.insert("user.insert", userVo);
		
		return count;
	}
	
	//로그인 -> 회원정보 가져오기(매개변수 UserVo)
	public UserVo selectUser(UserVo userVo) {
		System.out.println("[USER DAO] selectUser");
		//System.out.println(userVo.toString());
		
		return sqlSession.selectOne("user.selectUser", userVo);
	}
	
	//회원정보 수정 폼 -> 회원정보 가져오기(매개변수 int)
	public UserVo selectUser(int no) {
		System.out.println("[USER DAO] selectUserInfo");
		//System.out.println(userVo.toString());
		
		return sqlSession.selectOne("user.selectUserInfo", no);
	}
	
	//회원정보 수정
	public int updateUser(UserVo userVo) {
		System.out.println("[USER DAO] updateUser");
		//System.out.println(userVo.toString());
		
		return sqlSession.update("user.updateUser", userVo);
	}
	
	//회원가입 id중복체크
	public UserVo selectOne(String id) {
		System.out.println("[USER DAO] selectOne : " +id);
		
		return sqlSession.selectOne("user.selectById", id);
	}
}
