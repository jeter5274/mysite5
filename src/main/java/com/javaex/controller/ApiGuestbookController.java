package com.javaex.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
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

	// 전체 리스트 가져오기(ajax)
	@ResponseBody
	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
	public List<GuestbookVo> list() {
		System.out.println("[ApiGuestbookController] list");

		return gbService.addList();
	}

	// 글작성(ajax)
	@ResponseBody
	@RequestMapping(value = "/write", method = { RequestMethod.GET, RequestMethod.POST })
	public GuestbookVo write(@ModelAttribute GuestbookVo gbVo) {
		System.out.println("[ApiGuestbookController] write");
		// 입력된 Vo 전달하고 저장된 Vo를 받음
		return gbService.writeResultVo(gbVo);
	}

	// 글작성(ajax / json형식)
	@ResponseBody
	@RequestMapping(value = "/write2", method = { RequestMethod.GET, RequestMethod.POST })
	public GuestbookVo write2(@RequestBody GuestbookVo gbVo) {
		System.out.println("[ApiGuestbookController] write2");
		System.out.println(gbVo);
		// 입력된 Vo 전달하고 저장된 Vo를 받음
		return gbService.writeResultVo(gbVo);
	}

	// 글 삭제(ajax)
	@ResponseBody
	@RequestMapping(value = "/remove", method = { RequestMethod.GET, RequestMethod.POST })
	public int remove(@ModelAttribute GuestbookVo gbVo) {
		System.out.println("[ApiGuestbookController] remove");
		System.out.println(gbVo);

		return gbService.delete(gbVo);

	}
}
