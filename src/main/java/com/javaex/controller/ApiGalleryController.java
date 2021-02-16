package com.javaex.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.javaex.service.GalleryService;

@Controller
@RequestMapping(value = "/api/gallery")
public class ApiGalleryController {
	
	@Autowired
	private GalleryService galleryService;
	
	@ResponseBody
	@RequestMapping(value="/remove", method= {RequestMethod.GET, RequestMethod.POST})
	public int remove(@RequestParam("no") int no) {
		System.out.println("[GalleryApiController] remove");
		
		return galleryService.remove(no);
	}
}
