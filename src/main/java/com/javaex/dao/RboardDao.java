package com.javaex.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javaex.vo.RboardVo;

@Repository
public class RboardDao {

	@Autowired
	private SqlSession sqlSession;

	//글 목록
	public List<RboardVo> selectList() {
		System.out.println("[RboardDao] selectList");

		return sqlSession.selectList("rboard.selectList");
	}

	// 1개 글 내용
	public RboardVo selectPost(int no) {
		System.out.println("[RboardDao] selectPost");

		return sqlSession.selectOne("rboard.selectPost", no);
	}

	// 조회수 +1
	public int updateHit(int no) {
		System.out.println("[RboardDao] updateHit");

		return sqlSession.update("rboard.updateHit", no);
	}
	
	//새글 등록
	public int insertNewPost(RboardVo rboardVo) {
		System.out.println("[RboardDao] insertNewPost");
		
		return sqlSession.insert("rboard.insertNew", rboardVo);
	}
	
	//답글 등록
	public int insertReply(RboardVo rboardVo) {
		System.out.println("[RboardDao] insertReply");
		
		return sqlSession.insert("rboard.insertReply", rboardVo);
	}
	
	//그룹 order+1
	public int updateGroup(RboardVo rboardVo) {
		System.out.println("[RboardDao] updateGroup");
		
		return sqlSession.update("rboard.updateGroup", rboardVo);
	}
	
	//글 수정
	public int updatePost(RboardVo rboardVo) {
		System.out.println("[RboardDao] updatePost");
		
		return sqlSession.update("rboard.updatePost", rboardVo);
	}
}