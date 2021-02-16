package com.javaex.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.javaex.service.GalleryService;
import com.javaex.vo.GalleryVo;
import com.javaex.vo.UserVo;

@Controller
@RequestMapping("/gallery")
public class GalleryController {

	@Autowired
	private GalleryService galleryService;
	
	//전체 리스트 출력
	@RequestMapping(value="/list", method = {RequestMethod.GET, RequestMethod.POST})
	public String list(Model model) {
		System.out.println("[GalleryController] list");
		
		model.addAttribute("galleryList", galleryService.getList());		
		
		return "gallery/list";
	}
	
	//이미지 업로드
	@RequestMapping(value="/upload", method = {RequestMethod.GET, RequestMethod.POST})
	public String upload(@ModelAttribute GalleryVo galleryVo, @RequestParam("file") MultipartFile file, HttpSession session) {
		System.out.println("[GalleryController] upload");
		
		galleryVo.setUserNo(((UserVo)session.getAttribute("authUser")).getNo());
		galleryService.upload(galleryVo, file);
		
		return "redirect:/gallery/list";
	}
}
