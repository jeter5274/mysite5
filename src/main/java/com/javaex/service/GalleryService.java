package com.javaex.service;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.javaex.dao.GalleryDao;
import com.javaex.vo.GalleryVo;

@Service
public class GalleryService {
	
	@Autowired
	private GalleryDao galleryDao;
	
	//리스트
	public List<GalleryVo> getList(){
		System.out.println("[GalleryService] getList");
		
		return galleryDao.selectList();
	}
	
	//업로드
	public int upload(GalleryVo galleryVo, MultipartFile file) {
		System.out.println("[GalleryService] upload");
		
		//파일 저장 위치
		String saveDir = "C:\\javaStudy\\upload";
		
		//파일 이름
		String orgName = file.getOriginalFilename();
		
		//확장자 가져오기
		String exName = orgName.substring(orgName.lastIndexOf("."));
		
		//저장이름
		String saveName = System.currentTimeMillis() + UUID.randomUUID().toString() + exName;
		
		//저장경로(filePath)
		String filePath = saveDir + "\\" + saveName;
		
		galleryVo.setSaveName(saveName);
		galleryVo.setFilePath(filePath);
		
		galleryVo.setOrgName(file.getOriginalFilename());
		galleryVo.setFileSize(file.getSize());
		
		System.out.println(galleryVo);
		System.out.println(file.getOriginalFilename());
		
		//DB에 갤러리 게시글 정보를 저장
		int dbResult = galleryDao.insertFileInfo(galleryVo);
		//저장 성공/실패에 따라 이미지 저장할 수 있는 개선을 고려하여 변수에 결과정보를 담아놓음
		
		//서버에 첨부파일(이미지) 저장
		try {
			
			byte[] fileData = file.getBytes();
			OutputStream out = new FileOutputStream(filePath);
			BufferedOutputStream bos = new BufferedOutputStream(out);
			
			bos.write(fileData);
			bos.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return dbResult;
	}
	
	//갤러리글 삭제
	public int remove(int no) {
		System.out.println("[GalleryService] remove");
		
		return galleryDao.deleteFileInfo(no);
	}

}
