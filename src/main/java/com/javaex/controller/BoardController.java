package com.javaex.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.javaex.service.BoardService;
import com.javaex.vo.BoardVo;
import com.javaex.vo.UserVo;

@Controller
@RequestMapping(value="/board")
public class BoardController {

	@Autowired
	private BoardService boardService;
	
	//글 목록
	@RequestMapping(value="/list", method= {RequestMethod.GET, RequestMethod.POST} )
	public String getList(Model model){
		System.out.println("[boardController] getList");
		
		model.addAttribute("boardList", boardService.getList());
		
		return "board/list";
	}
	
	//글 읽기
	@RequestMapping(value="/read", method= {RequestMethod.GET, RequestMethod.POST} )
	public String read(@RequestParam("no") int no, @RequestParam("userNo") int userNo, HttpSession session, Model model) {
		System.out.println("[boardController] read");
		
		String hit = "up";
		
		if(userNo == ((UserVo)session.getAttribute("authUser")).getNo()) { //본인 글이 아닌 경우
			hit = "hold";
		}
		
		model.addAttribute("post", boardService.read(no, hit));
		
		return "board/read";
	}
	
	//글 쓰기 폼
	@RequestMapping(value="/writeForm", method= {RequestMethod.GET, RequestMethod.POST} )
	public String writeForm() {
		System.out.println("[boardController] writeForm");
		
		return "board/writeForm";
	}
	
	//글 저장
	@RequestMapping(value="/write", method= {RequestMethod.GET, RequestMethod.POST} )
	public String write(@ModelAttribute BoardVo boardVo, HttpSession session) {
		System.out.println("[boardController] write");
		
		boardVo.setUserNo(((UserVo)session.getAttribute("authUser")).getNo());
		
		boardService.write(boardVo);
		
		return "redirect:/board/list";
	}
	
	//글 수정 폼
	@RequestMapping(value="/modifyForm", method= {RequestMethod.GET, RequestMethod.POST} )
	public String modifyForm(@RequestParam("no") int no, Model model) {
		System.out.println("[boardController] modifyForm");
		
		model.addAttribute("post", boardService.modifyForm(no));
		
		return "board/modifyForm";
	}
	
	//글 수정
	@RequestMapping(value="/modify", method= {RequestMethod.GET, RequestMethod.POST} )
	public String modify(@ModelAttribute BoardVo boardVo) {
		System.out.println("[boardController] modify");
		
		boardService.modify(boardVo);
		
		return "redirect:/board/list";
	}
	
	@RequestMapping(value="/remove", method= {RequestMethod.GET, RequestMethod.POST} )
	public String remove(int no) {
		System.out.println("[boardController] remove");
		
		boardService.remove(no);
		
		return "redirect:/board/list";
	}
}
