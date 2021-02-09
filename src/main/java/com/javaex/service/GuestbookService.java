package com.javaex.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaex.dao.GuestbookDao;
import com.javaex.vo.GuestbookVo;

@Service
public class GuestbookService {
	
	@Autowired
	private GuestbookDao gbDao;
	
	public List<GuestbookVo> addList(){
		System.out.println("[GBService] addList");
		
		return gbDao.getList(); 
	}
	
	public int add(GuestbookVo gbVo) {
		System.out.println("[GBService] add");
		
		return gbDao.insert(gbVo);
	}
	
	public int delete(GuestbookVo gbVo) {
		System.out.println("[GBService] delete");
		
		return gbDao.delete(gbVo);
	}
	
	//ajax 글저장
	public GuestbookVo writeResultVo(GuestbookVo gbVo) {
		System.out.println("[GBService] writeResultVo");
		
		//글저장
		gbDao.insertSelectKey(gbVo);
		
		//방금 저장된 글 받아오기
		return gbDao.selectOne(gbVo.getNo());
	}
}


