package com.javaex.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.javaex.service.FileUploadService;

@Controller
@RequestMapping("/fileUp")
public class FileUploadController {

	@Autowired
	private FileUploadService fileUploadService;
	
	@RequestMapping(value="/form", method= {RequestMethod.GET, RequestMethod.POST} )
	public String form() {
		System.out.println("[fileUploadController] form");
		
		return "fileUpload/form";
	}
	
	@RequestMapping(value="/upload", method= {RequestMethod.GET, RequestMethod.POST} )
	public String upload(@RequestParam("file") MultipartFile file, Model model) {
		System.out.println("[fileUploadController] upload");
		
		model.addAttribute("saveName", fileUploadService.restore(file));

		return "fileUpload/result";
	}
}
