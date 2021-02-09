package com.javaex.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.javaex.service.GuestbookService;
import com.javaex.vo.GuestbookVo;

@Controller
@RequestMapping(value = "/api/guestbook")
public class ApiGuestbookController {

	@Autowired
	private GuestbookService gbService;
	
	//전체 리스트 가져오기(ajax)
	@ResponseBody
	@RequestMapping(value="/list", method= {RequestMethod.GET, RequestMethod.POST})
	public List<GuestbookVo> list() {
		System.out.println("[ApiGuestbookController] list");
		
		return gbService.addList();
	}
	
	//글작성(ajax)
	@ResponseBody
	@RequestMapping(value="/write", method= {RequestMethod.GET, RequestMethod.POST})
	public GuestbookVo write(@ModelAttribute GuestbookVo gbVo) {
		System.out.println("[ApiGuestbookController] write");
		
		//입력된 Vo 전달하고 저장된 Vo를 받음
		return gbService.writeResultVo(gbVo);
	}
	
}
