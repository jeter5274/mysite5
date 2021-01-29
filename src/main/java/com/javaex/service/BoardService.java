package com.javaex.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaex.dao.BoardDao;
import com.javaex.vo.BoardVo;
import com.javaex.vo.UserVo;

@Service
public class BoardService {

	@Autowired
	private BoardDao boardDao;
	
	@Autowired
	private HttpSession session;
	
	//글 목록
	public List<BoardVo> getList(){
		System.out.println("[boardService] getList");
		
		return boardDao.selectList();
		
	}
	
	//글 읽기
	public BoardVo read(int no) { //매개변수에 HttpSession을 불러오면 controller에서 호출시 매개변수를 맞춰야함
		System.out.println("[boardService] read");
		
		BoardVo post = boardDao.selectPost(no);

		/* 본인의 글이 아니면 조회수 +1
		 * 
		 * 세션정보를 사용하기위해 Autowired 어노테이션을 쓰느냐, 
		 * controller에서 매개변수로 받아 전달하는게 나은가?
		 * 
		 * controller에서는 client의 요청과 데이터를 전달하고 결과를 전달받아 뷰 응답하는 것이 역할이며,
		 * 활용하지 않는 세션을 전달만하기 위해 불러올 필요가 없을 것 같음
		 * 또한 글을 읽는 사람이 작성자인지 판단하는 것은 비즈니스 로직이라고 판단되어 Autowired를 활용 
		 */
		if(session.getAttribute("authUser") == null || post.getUserNo() != ((UserVo)session.getAttribute("authUser")).getNo()) {
			boardDao.updateHit(no);			//DB hit +1
			post.setHit(post.getHit()+1);	//이미 불러온 post hit+1
		}
		
		return post;
	}
	
	//글 저장
	public int write(BoardVo boardVo) {
		System.out.println("[boardService] write");
		
		boardVo.setUserNo(((UserVo)session.getAttribute("authUser")).getNo());
		
		return boardDao.insert(boardVo);
	}
	
	//글 수정 폼
	public BoardVo modifyForm(int no) {
		System.out.println("[boardService] modifyForm");
		
		return boardDao.selectPost(no);
	}
	
	//글 수정
	public int modify(BoardVo boardVo) {
		System.out.println("[boardService] modify");
		
		return boardDao.updatePost(boardVo);
	}
	
	//글 삭제
	public int remove(int no) {
		System.out.println("[boardService] remove");
		
		return boardDao.delete(no);
	}
}
