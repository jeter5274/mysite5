package com.javaex.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.javaex.service.RboardService;
import com.javaex.vo.RboardVo;
import com.javaex.vo.UserVo;

@Controller
@RequestMapping("/rboard")
public class RboardController {

	@Autowired
	private RboardService rboardService;

	// 글 목록
	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
	public String getList(Model model) {
		System.out.println("[RboardController] getList");

		model.addAttribute("rboardList", rboardService.getList());

		return "rboard/list";
	}

	// 글 읽기
	@RequestMapping(value = "/read", method = { RequestMethod.GET, RequestMethod.POST })
	public String read(@RequestParam("no") int no, @RequestParam("userNo") int userNo, HttpSession session,
			Model model) {
		System.out.println("[RboardController] read");

		String hit = null;

		if (session.getAttribute("authUser") == null || userNo != ((UserVo) session.getAttribute("authUser")).getNo()) {
			// 로그인이 안되어 있거나 본인 글이 아닌 경우
			hit = "up";
		}
		model.addAttribute("post", rboardService.read(no, hit));

		return "rboard/read";
	}

	// 글 쓰기 폼
	@RequestMapping(value = "/writeForm", method = { RequestMethod.GET, RequestMethod.POST })
	public String writeForm(@RequestParam(value = "parentsNo", required = false, defaultValue = "-1") int pNo,
			Model model) {
		System.out.println("[RboardController] writeForm");

		// 파라미터에 부모 게시글 번호가 있을 경우
		if (pNo > 0) {
			model.addAttribute("parentsPost", rboardService.read(pNo));
		}

		return "rboard/writeForm";
	}

	// 글 등록
	@RequestMapping(value = "/write", method = { RequestMethod.GET, RequestMethod.POST })
	public String write(@ModelAttribute RboardVo rboardVo, HttpSession session) {
		System.out.println("[RboardController] write");

		rboardVo.setUserNo(((UserVo) session.getAttribute("authUser")).getNo());

		rboardService.write(rboardVo);

		return "redirect:/rboard/list";
	}

	// 글 수정 폼
	@RequestMapping(value = "/modifyForm", method = { RequestMethod.GET, RequestMethod.POST })
	public String modifyForm(@RequestParam("no") int no, Model model) {
		System.out.println("[RboardController] modifyForm");

		model.addAttribute("post", rboardService.read(no));

		return "rboard/modifyForm";
	}

	// 글 수정
	@RequestMapping(value = "/modify", method = { RequestMethod.GET, RequestMethod.POST })
	public String modify(@ModelAttribute RboardVo rboardVo) {
		System.out.println("[RboardController] modify");
		
		rboardService.modify(rboardVo);
		
		return "redirect:/rboard/list";
	}
	
	//글 삭제 
	@RequestMapping(value="/remove", method = { RequestMethod.GET, RequestMethod.POST })
	public String remove(@ModelAttribute RboardVo rboardVo) {
		System.out.println("[RboardController] remove");
		
		rboardService.remove(rboardVo);
		
		return "redirect:/rboard/list";
	}
	
	//게시글 검색
	@RequestMapping(value="/search")
	public String search(@RequestParam("keyword") String keyword, Model model) {
		System.out.println("[RboardController] search");
	
		model.addAttribute("rboardList", rboardService.search(keyword));

		return "rboard/list";
	}
}
