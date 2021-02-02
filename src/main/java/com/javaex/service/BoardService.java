package com.javaex.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaex.dao.BoardDao;
import com.javaex.vo.BoardVo;
import com.javaex.vo.RboardVo;

@Service
public class BoardService {

	@Autowired
	private BoardDao boardDao;

	// 글 목록
	public List<BoardVo> getList() {
		System.out.println("[boardService] getList");

		return boardDao.selectList();

	}

	// 글 읽기
	public BoardVo read(int no, String hit) {
		System.out.println("[boardService] read");

		if ("up".equals(hit)) {
			boardDao.updateHit(no);
		}

		BoardVo post = boardDao.selectPost(no);

		return post;
	}

	// 글 저장
	public int write(BoardVo boardVo) {
		System.out.println("[boardService] write");

		return boardDao.insert(boardVo);
	}

	// 글 수정 폼
	public BoardVo modifyForm(int no) {
		System.out.println("[boardService] modifyForm");

		return boardDao.selectPost(no);
	}

	// 글 수정
	public int modify(BoardVo boardVo) {
		System.out.println("[boardService] modify");

		return boardDao.updatePost(boardVo);
	}

	// 글 삭제
	public int remove(int no) {
		System.out.println("[boardService] remove");

		return boardDao.delete(no);
	}

	// 게시글 검색 - 제목/글쓴이
	public List<RboardVo> search(String keyword) {
		System.out.println("[boardService] search");

		keyword = "%" + keyword + "%";

		return boardDao.selectSearchList(keyword);
	}
}
