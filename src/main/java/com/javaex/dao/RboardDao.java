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

	// 글 목록
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

	// 새글 등록
	public int insertNewPost(RboardVo rboardVo) {
		System.out.println("[RboardDao] insertNewPost");

		return sqlSession.insert("rboard.insertNew", rboardVo);
	}

	// 답글 등록
	public int insertReply(RboardVo rboardVo) {
		System.out.println("[RboardDao] insertReply");

		return sqlSession.insert("rboard.insertReply", rboardVo);
	}

	// 그룹 order+1
	public int updateGroup(RboardVo rboardVo) {
		System.out.println("[RboardDao] updateGroup");

		return sqlSession.update("rboard.updateGroup", rboardVo);
	}

	// 글 수정
	public int updatePost(RboardVo rboardVo) {
		System.out.println("[RboardDao] updatePost");

		return sqlSession.update("rboard.updatePost", rboardVo);
	}

	// 다음 답글 가져오기
	public RboardVo selectNextPost(RboardVo rboardVo) {
		System.out.println("[RboardDao] selectNextPost");

		return sqlSession.selectOne("rboard.selectNextPost", rboardVo);
	}

	// 글 삭제
	public int delete(int no) {
		System.out.println("[RboardDao] delete");

		return sqlSession.delete("rboard.delete", no);
	}

	// 글 삭제시, Group order 정리
	public int updateGroupDel(RboardVo rboardVo) {
		System.out.println("[RboardDao] updateGroupDel");

		return sqlSession.update("rboard.updateGroupDel", rboardVo);
	}

	// 답글이 있는 글의 삭제표시
	public int updateDelStatus(int no) {
		System.out.println("[RboardDao] updateDelStatus");

		return sqlSession.update("rboard.updateDelStatus", no);
	}
	
	//삭제 상태의 글 목록
	public List<RboardVo> selectDelList() {
		System.out.println("[RboardDao] selectDelList");

		return sqlSession.selectList("rboard.selectDelList");
	}
	
	//답글 가져오기
	public RboardVo selectChkReply(RboardVo rboardVo) {
		System.out.println("[RboardDao] selectChkReply");

		return sqlSession.selectOne("rboard.selectChkReply", rboardVo);
	}
	
	//글검색
	public List<RboardVo> selectSearchList(String keyword) {
		System.out.println("[RboardDao] selectSearchList");

		return sqlSession.selectList("rboard.selectSearchList", keyword);
	}
}