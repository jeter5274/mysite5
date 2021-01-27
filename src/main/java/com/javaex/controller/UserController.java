package com.javaex.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.javaex.dao.UserDao;
import com.javaex.vo.UserVo;

@Controller
@RequestMapping(value = "/user")
public class UserController {

	@Autowired
	private UserDao userDao;

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
		System.out.println(userVo.toString());

		int count = userDao.insert(userVo);

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
		
		System.out.println(userVo.toString());
		
		UserVo authUser = userDao.selectUser(userVo);
		System.out.println("controller : " +authUser);
		
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
}
