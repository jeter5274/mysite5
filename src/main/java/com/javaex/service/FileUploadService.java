package com.javaex.service;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileUploadService {

	public String restore(MultipartFile file) {
		System.out.println("[fileUploadService] restore");

		//서버 저장 위치
		String saveDir = "C:\\javaStudy\\upload";
		
		//DB에 저장할 파일정보 수집 
		
			//오리지널 파일 이름
		String orgName = file.getOriginalFilename();
		
			//확장자
		String exName = orgName.substring(orgName.lastIndexOf("."));
		
			//서버 저장파일 이름
		String saveName = System.currentTimeMillis() + UUID.randomUUID().toString() + exName ;
		
			//서버 파일패스 => 저장경로
		String filePath = saveDir + "\\" +saveName;
			
			//파일 사이즈
		long fileSize = file.getSize();
		
		System.out.println("orgName : " +orgName+ ", exName : " +exName+ ", saveName : " +saveName+ ", saveDir : " +saveDir+ ", filePath : " +filePath+ ", fileSize : " +fileSize);
		
		//서버 하드 디스크에 저장
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
		
		return saveName;
	}
}
