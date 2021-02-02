package com.javaex.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javaex.vo.BoardVo;
import com.javaex.vo.RboardVo;

@Repository
public class BoardDao {

	@Autowired
	private SqlSession sqlSession;

	// 글 목록
	public List<BoardVo> selectList() {
		System.out.println("[BoardDao] selectList");

		return sqlSession.selectList("board.selectList");
	}

	// 1개 글 내용
	public BoardVo selectPost(int no) {
		System.out.println("[BoardDao] selectPost");

		return sqlSession.selectOne("board.selectPost", no);
	}

	// 조회수 +1
	public int updateHit(int no) {
		System.out.println("[BoardDao] updateHit");

		return sqlSession.update("board.updateHit", no);
	}

	// 글 저장
	public int insert(BoardVo boardVo) {
		System.out.println("[BoardDao] insertPost");

		return sqlSession.insert("board.insert", boardVo);
	}
	// 글 수정
	public int updatePost(BoardVo boardVo) {
		System.out.println("[BoardDao] updatePost");

		return sqlSession.update("board.updatePost", boardVo);
	}

	// 글 삭제
	public int delete(int no) {
		System.out.println("[BoardDao] delete");

		return sqlSession.delete("board.delete", no);
	}

	// 글검색
	public List<RboardVo> selectSearchList(String keyword) {
		System.out.println("[BoardDao] selectSearchList");

		return sqlSession.selectList("board.selectSearchList", keyword);
	}
}
