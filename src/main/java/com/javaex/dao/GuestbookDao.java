package com.javaex.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javaex.vo.GuestbookVo;

@Repository
public class GuestbookDao {
	
	@Autowired
	private SqlSession sqlSession;

	public List<GuestbookVo> getList() {
		System.out.println("[GBDao] getList");
		return sqlSession.selectList("guestbook.getList");

	}
	
	public int insert(GuestbookVo gbVo) {
		System.out.println("[GBDao] insert");
		
		return sqlSession.insert("guestbook.insert", gbVo);
	}
	
	public int delete(GuestbookVo gbVo) {
		System.out.println("[GBDao] delete");
		
		return sqlSession.delete("guestbook.delete", gbVo);
	}
	
	//글저장 (selectKey)
	public void insertSelectKey(GuestbookVo gbVo) {
		System.out.println("[GBDao] insertSelectKey");

		sqlSession.insert("guestbook.insertSelectKey", gbVo);

	}
	
	//글 1개 가져오기
	public GuestbookVo selectOne(int no) {
		System.out.println("[GBDao] selectOne");
		
		return sqlSession.selectOne("guestbook.select", no);
	}
}
