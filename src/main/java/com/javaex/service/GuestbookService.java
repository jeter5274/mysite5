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
}
