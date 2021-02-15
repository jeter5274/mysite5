package com.javaex.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	// 검색어로 글목록 가져오기
	public List<BoardVo> selectList2(String keyword) {
		System.out.println("[BoardDao] selectList2");
		System.out.println("keyword : " + keyword);

		return sqlSession.selectList("board.selectList2", keyword);
	}

	// 검색어로 글목록 가져오기 + 페이징
	public List<BoardVo> selectList3(String keyword, int startRnum, int endRnum) {
		System.out.println("[BoardDao] selectList3");
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("keyword", keyword);
		map.put("startRnum", startRnum);
		map.put("endRnum", endRnum);
		
		System.out.println(map);

		return sqlSession.selectList("board.selectList3", map);
	}
	
	//전체 글 갯수 가져오기
	public int selectTotalCnt(String keyword) {
		System.out.println("[BoardDao] selectTotalCnt");
		
		return sqlSession.selectOne("board.selectTotalCnt", keyword);
	}
}

