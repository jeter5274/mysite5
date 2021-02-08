package com.javaex.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.javaex.service.UserService;
import com.javaex.vo.UserVo;

@Controller
@RequestMapping(value = "/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	// 회원가입 폼
	@RequestMapping(value = "/joinForm", method = { RequestMethod.GET, RequestMethod.POST })
	public String joinForm() {
		System.out.println("/user/joinForm");

		return "user/joinForm";
	}

	// 회원가입
	@RequestMapping(value = "/join", method = { RequestMethod.GET, RequestMethod.POST })
	public String join(@ModelAttribute UserVo userVo) {
		System.out.println("/user/join");
		//System.out.println(userVo.toString());

		userService.join(userVo);
		
		return "user/joinOk";
	}
	
	// 로그인 폼
	@RequestMapping(value = "/loginForm", method = { RequestMethod.GET, RequestMethod.POST })
	public String loginForm() {
		System.out.println("/user/loginForm");
		
		return "user/loginForm";
	}
	
	//로그인
	@RequestMapping(value="/login", method = { RequestMethod.GET, RequestMethod.POST })
	public String login(@ModelAttribute UserVo userVo, HttpSession session) {
		System.out.println("/user/login");
		
		//System.out.println(userVo.toString());
		
		UserVo authUser = userService.login(userVo);
		//System.out.println("controller : " +authUser);
		
		if(authUser == null) { //실패했을 때
			
			return "redirect:/user/loginForm?result=fail";
			
		}else { //성공했을 때
			
			session.setAttribute("authUser", authUser);
			
			return "redirect:/";
		}		
	}
	
	//로그아웃
	@RequestMapping(value="/logout", method = {RequestMethod.GET, RequestMethod.POST})
	public String logout(HttpSession session) {
		System.out.println("/user/logout");
		
		session.removeAttribute("authUser");
		session.invalidate();
		
		return "redirect:/";
	}
	
	//회원정보 수정 폼
	@RequestMapping(value = "/modiForm", method= {RequestMethod.GET, RequestMethod.POST})
	public String modiForm(HttpSession session, Model model) {
		
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		//UserVo authUserInfo = userDao.selectUserInfo(authUser);
		//System.out.println("controller : " +authUserInfo.toString());
		model.addAttribute("authUserInfo", userService.modifyForm(authUser.getNo()) );
		
		return "user/modifyForm";
	}
	
	//회원정보 수정
	@RequestMapping(value = "/modify", method= {RequestMethod.GET, RequestMethod.POST})
	public String modify(@ModelAttribute UserVo userVo, HttpSession session) {
		
		userVo.setNo(((UserVo)session.getAttribute("authUser")).getNo());
		//System.out.println(userVo.toString());
		
		userService.modify(userVo);
		
		((UserVo)session.getAttribute("authUser")).setName(userVo.getName());
		
		return "redirect:/";
	}
	
	//회원가입 id 중복체크
	@ResponseBody
	@RequestMapping(value="/idcheck", method= {RequestMethod.GET, RequestMethod.POST})
	public String idcheck(@RequestParam("id") String id) {
		System.out.println("/user/idcheck");
		System.out.println("checkid :" +id);

		String result = userService.idcheck(id);
		System.out.println(result);
		
		//return "redirect:/user/joinForm?result="+result;
		return result;	//@ResponseBody -> response의 body영역에 data만 보낸다. (return 값)
	}
}
 