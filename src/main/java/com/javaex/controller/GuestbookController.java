package com.javaex.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.javaex.service.GuestbookService;
import com.javaex.vo.GuestbookVo;

@Controller
@RequestMapping(value="/guestbook")
public class GuestbookController {

	@Autowired
	private GuestbookService gbService;
	
	@RequestMapping(value="/addList", method= {RequestMethod.GET, RequestMethod.POST})
	public String addList(Model model) {
		System.out.println("[GBController] addList");
		
		model.addAttribute("gbList", gbService.addList());
		
		return "guestbook/addList";
	}
	
	@RequestMapping(value ="/add", method= {RequestMethod.GET, RequestMethod.POST})
	public String add(@ModelAttribute GuestbookVo gbVo) {
		System.out.println("[GBController] add");
		
		gbService.add(gbVo);
		
		return "redirect:/guestbook/addList";
		
	}
	
	@RequestMapping(value="/deleteForm", method= {RequestMethod.GET, RequestMethod.POST})
	public String delForm() {
		System.out.println("[GBController] deleteForm");
		
		return "guestbook/deleteForm";
	}
	
	@RequestMapping(value="/delete", method= {RequestMethod.GET, RequestMethod.POST})
	public String delete(@ModelAttribute GuestbookVo gbVo) {
		System.out.println("[GBController] delete");
		
		int count = gbService.delete(gbVo);
		
		if(count == 0) { //삭제 실패
			
			return "redirect:/guestbook/deleteForm?result=fail&no="+gbVo.getNo();
			
		}else {	//삭제 성공
			
			return "redirect:/guestbook/addList";
		}
		
	}
	
	//ajaxList
	@RequestMapping(value="/ajaxList", method = {RequestMethod.GET, RequestMethod.POST} )
	public String ajaxList() {
		System.out.println("[GBController] ajaxList");
		
		return "guestbook/ajaxList";
	}
}
