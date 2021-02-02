package com.javaex.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaex.dao.RboardDao;
import com.javaex.vo.RboardVo;

@Service
public class RboardService {

	@Autowired
	private RboardDao rboardDao;
	
	//글 목록
	public List<RboardVo> getList() {
		System.out.println("[RboardService] getList");

		return rboardDao.selectList();
	}

	// 글 읽기
	public RboardVo read(int no) { 
		System.out.println("[RboardService] read");
		
		RboardVo post = rboardDao.selectPost(no);

		return post;
	}
	
	//글 읽기 & 조회수 +1
	public RboardVo read(int no, String hit) { 
		System.out.println("[RboardService] read and hit up");

		if("up".equals(hit)) {	
			rboardDao.updateHit(no);	
		}
		
		return read(no);
	}	
	
	//글 등록
	public int write(RboardVo rboardVo) {
		System.out.println("[RboardService] write");
		
		if(rboardVo.getGroupNo() > 0) {
			
			rboardVo.setOrderNo(rboardVo.getOrderNo()+1);
			rboardVo.setDepth(rboardVo.getDepth()+1);
			
			rboardDao.updateGroup(rboardVo);
			
			return rboardDao.insertReply(rboardVo);
			
		}else {
			
			return rboardDao.insertNewPost(rboardVo);
			
		}
	}
	
	// 글 수정
	public int modify(RboardVo rboardVo) {
		System.out.println("[RboardService] modify");
		
		return rboardDao.updatePost(rboardVo);
	}
	
	//글 삭제
	//답글이 있을 경우 삭제된 글입니다. 라고 표시, 없는 경우 삭제 - 단, 부모글이 삭제된 상태면 같이 삭제
	public int remove(RboardVo rboardVo) {
		System.out.println("[RboardService] remove");
		int count=0;
		RboardVo nextVo = rboardDao.selectNextPost(rboardVo);
		
		if(nextVo == null) {
			//답글이 없으면 삭제
			count += rboardDao.delete(rboardVo.getNo());
		}else {
			//답글이 있을 경우
			if(rboardVo.getDepth() > nextVo.getDepth()) {
				
				//답글이 나의 답글이 아닌 경우(조부모의 답글이 2개일 경우?)
				count += rboardDao.delete(rboardVo.getNo());
				
				//order 정리
				rboardDao.updateGroupDel(rboardVo);
				
			}else {
				//나의 답글인 경우 삭제상태로 변경
				rboardDao.updateDelStatus(rboardVo.getNo());
			}
		}
		
		//삭제상태의 글들을 관리(답글이 지워져서 없어진 경우 삭제)
		count += manageDelPost();
		
		return count;
	}
	
	//삭제상태의 글에 답글이 없을 경우 삭제
	public int manageDelPost() {
		System.out.println("[RboardService] manageDelPost");
		
		int count=0;
		
		//삭제상태인 글 목록을 불러옴
		List<RboardVo> delList = rboardDao.selectDelList();
		
		for(int i = 0; i<delList.size(); i++) {
			
			//삭제상태의 글의 답글이 없을 경우 삭제함
			if(rboardDao.selectChkReply(delList.get(i)) == null){
				count += rboardDao.delete(delList.get(i).getNo());
			}
		}
		
		return count;
	}
}
