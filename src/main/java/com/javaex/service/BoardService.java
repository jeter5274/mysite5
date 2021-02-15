package com.javaex.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	// 리스트 + 검색
	public List<BoardVo> getBoardList2(String keyword) {
		System.out.println("[boardService] getBoardList2");
		System.out.println("keyword : " + keyword);

		return boardDao.selectList2(keyword);

	}

	// 리스트 + 검색 + 페이징
	public Map<String, Object> getBoardList3(String keyword, int crtPage) {
		System.out.println("[boardService] getBoardList3");
		
		//crtPage -> 시작번호, 끝번호 1 => 1, 10	2 => 11, 20		3 => 21, 30
		
		////////////////////////////
		//리스트 구하기
		////////////////////////////
		
		int listCnt = 10;							//페이지당 글 갯수
		
		crtPage = crtPage > 0 ? crtPage : 1;		//현재 페이지가 0보다 작으면 1로
		
		int startRnum = listCnt*(crtPage-1) + 1;	//시작 글번호 startRnum
		
		int endRnum = (startRnum+listCnt) - 1;		//끝 글번호 endRnum

		List<BoardVo> boardList = boardDao.selectList3(keyword, startRnum, endRnum);
		
		
		////////////////////////////
		//페이징 계산
		////////////////////////////
		
		int pageBtnCount = 10;						//페이지당 버튼 갯수
		
		//전체 글 갯수 구하기
		int totalCount = boardDao.selectTotalCnt(keyword);
		
		//1 => 1 ~ 5
		//2 => 1 ~ 5
		//3 => 1 ~ 5
		//4 => 1 ~ 5
		//5 => 1 ~ 5
		//6 => 6 ~ 10
		//7 => 6 ~ 10
		//8 => 6 ~ 10
		//9 => 6 ~ 10
		//10 => 6 ~ 10
		
		int endPageBtnNo = (int)Math.ceil(crtPage/(double)pageBtnCount)*pageBtnCount;	//마지막 버튼 번호 
		
		int startPageBtnNo = endPageBtnNo-(pageBtnCount-1); //시작 버튼 번호
		
		//다음버튼 boolean
		boolean next;
		
		if(endPageBtnNo * listCnt < totalCount) {
			next = true;
		}else {
			next = false;
			endPageBtnNo = (int)Math.ceil(totalCount/(double)listCnt);
		}
		
		//이전버튼	boolean
		boolean prev;
		
		if(startPageBtnNo != 1) {
			prev = true;
		}else {
			prev = false;
		}
		
		//boardList, prev, startPageBtnNo, endPageBtnNo, next => jsp에 맵으로 전달
		Map<String, Object> pMap = new HashMap<String, Object>();
		pMap.put("boardList", boardList);
		pMap.put("prev", prev);
		pMap.put("startPageBtnNo", startPageBtnNo);
		pMap.put("endPageBtnNo", endPageBtnNo);
		pMap.put("next", next);
		
		System.out.println("[boardService] " +pMap);
		
		return pMap;

	}

	// 글 1234개 저장
	public int addBoard(BoardVo boardVo) {
		System.out.println("[boardService] addBoard");
		
		for(int i=1; i<=1234; i++) {
			boardVo.setTitle(i+" 번째 글 제목입니다.");
			boardVo.setContent(i+" 번째 글 내용입니다.");
			
			boardDao.insert(boardVo);
		}
		
		return 0;
	}
}
