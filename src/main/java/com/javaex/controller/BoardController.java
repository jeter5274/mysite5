package com.javaex.controller;

import java.util.List;
import java.util.Map;

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
@RequestMapping(value = "/board")
public class BoardController {

	@Autowired
	private BoardService boardService;

	// 글 목록
	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
	public String getList(Model model) {
		System.out.println("[boardController] getList");

		model.addAttribute("boardList", boardService.getList());

		return "board/list";
	}

	// 글 읽기
	@RequestMapping(value = "/read", method = { RequestMethod.GET, RequestMethod.POST })
	public String read(@RequestParam("no") int no, @RequestParam("userNo") int userNo, HttpSession session,
			Model model) {
		System.out.println("[boardController] read");

		String hit = null;

		if (session.getAttribute("authUser") == null || userNo != ((UserVo) session.getAttribute("authUser")).getNo()) {
			// 로그인이 안되어 있거나 본인 글이 아닌 경우
			hit = "up";
		}

		model.addAttribute("post", boardService.read(no, hit));

		return "board/read";
	}

	// 글 쓰기 폼
	@RequestMapping(value = "/writeForm", method = { RequestMethod.GET, RequestMethod.POST })
	public String writeForm() {
		System.out.println("[boardController] writeForm");

		return "board/writeForm";
	}

	// 글 저장
	@RequestMapping(value = "/write", method = { RequestMethod.GET, RequestMethod.POST })
	public String write(@ModelAttribute BoardVo boardVo, HttpSession session) {
		System.out.println("[boardController] write");

		boardVo.setUserNo(((UserVo) session.getAttribute("authUser")).getNo());

		boardService.write(boardVo);
		//boardService.addBoard(boardVo); //글 1234개 저장

		return "redirect:/board/list";
	}

	// 글 수정 폼
	@RequestMapping(value = "/modifyForm", method = { RequestMethod.GET, RequestMethod.POST })
	public String modifyForm(@RequestParam("no") int no, Model model) {
		System.out.println("[boardController] modifyForm");

		model.addAttribute("post", boardService.modifyForm(no));

		return "board/modifyForm";
	}

	// 글 수정
	@RequestMapping(value = "/modify", method = { RequestMethod.GET, RequestMethod.POST })
	public String modify(@ModelAttribute BoardVo boardVo) {
		System.out.println("[boardController] modify");

		boardService.modify(boardVo);

		return "redirect:/board/list";
	}

	@RequestMapping(value = "/remove", method = { RequestMethod.GET, RequestMethod.POST })
	public String remove(int no) {
		System.out.println("[boardController] remove");

		boardService.remove(no);

		return "redirect:/board/list";
	}

	// 게시글 검색
	@RequestMapping(value = "/search")
	public String search(@RequestParam("keyword") String keyword, Model model) {
		System.out.println("[boardController] search");

		model.addAttribute("boardList", boardService.search(keyword));

		return "board/list";
	}
	
	// 글목록(검색기능 포함)
	@RequestMapping(value="/list2", method= {RequestMethod.GET, RequestMethod.POST})
	public String list2(@RequestParam(value="keyword", required=false, defaultValue="") String keyword, Model model) {
		System.out.println("[boardController] list2");
		System.out.println("keyword : " +keyword);
		 
		List<BoardVo> boardList = boardService.getBoardList2(keyword);
		model.addAttribute("boardList", boardList);
		
		return "board/list2";
	}
	
	//글목록 + 검색 + 페이징
	@RequestMapping(value="/list3", method= {RequestMethod.GET, RequestMethod.POST})
	public String list3(@RequestParam(value="keyword", required=false, defaultValue="") String keyword,
						@RequestParam(value="crtPage", required=false, defaultValue="1") int crtPage,
						Model model) {
		System.out.println("[boardController] list3");
		//System.out.println("keyword : " +keyword);
		//System.out.println("crtPage : " +crtPage);
		 
		Map<String, Object> pMap = boardService.getBoardList3(keyword, crtPage);
		System.out.println("[boardController] " +pMap);
		model.addAttribute("pMap", pMap);
		
		return "board/list3";
	}
}
