package com.javaex.service;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
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

	// 리스트
	public Map<String, Object> getList(int crtPage, String keyword) {
		System.out.println("[GalleryService] getList");

		// 전체글 갯수
		int totalPostCnt = galleryDao.selectPostCnt(keyword);
		System.out.println(totalPostCnt);
		
		//setPaging(현재페이지, 페이지 당 글 갯수, 페이징 버튼 갯수, 전체 글 갯수)
		Map<String, Object> pageMap = setPaging(crtPage, 12, 10, totalPostCnt);
		
		pageMap.put("galleryList", galleryDao.selectList((int)pageMap.get("startPostNo"), (int)pageMap.get("endPostNo"), keyword));
		
		return pageMap;
	}

	// 업로드
	public int upload(GalleryVo galleryVo, MultipartFile file) {
		System.out.println("[GalleryService] upload");

		// 파일 저장 위치
		String saveDir = "C:\\javaStudy\\upload";

		// 파일 이름
		String orgName = file.getOriginalFilename();

		// 확장자 가져오기
		String exName = orgName.substring(orgName.lastIndexOf("."));

		// 저장이름
		String saveName = System.currentTimeMillis() + UUID.randomUUID().toString() + exName;

		// 저장경로(filePath)
		String filePath = saveDir + "\\" + saveName;

		galleryVo.setSaveName(saveName);
		galleryVo.setFilePath(filePath);

		galleryVo.setOrgName(file.getOriginalFilename());
		galleryVo.setFileSize(file.getSize());

		System.out.println(galleryVo);
		System.out.println(file.getOriginalFilename());

		// DB에 갤러리 게시글 정보를 저장
		int dbResult = galleryDao.insertFileInfo(galleryVo);
		// 저장 성공/실패에 따라 이미지 저장할 수 있는 개선을 고려하여 변수에 결과정보를 담아놓음

		// 서버에 첨부파일(이미지) 저장
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

	// 갤러리글 삭제
	public int remove(int no) {
		System.out.println("[GalleryService] remove");

		return galleryDao.deleteFileInfo(no);
	}

	//페이징 메소드
	public Map<String, Object> setPaging(int crtPage, int postCnt, int pageBtnCnt, int totalPostCnt) {
		// postCnt => 페이지 당 글 갯수, pageBtnCnt => 페이지 버튼 갯수
		
		// **************************게시글 리스트 시작, 끝번호 연산
		// crtPage 음수 오류 방지
		crtPage = crtPage > 0 ? crtPage : 1;

		// 시작글 번호
		int startPostNo = postCnt * (crtPage - 1) + 1;

		// 끝 글 번호
		int endPostNo = startPostNo + postCnt - 1;

		// **************************페이징
		// 끝 페이지 번호
		int endPageNo = (int) Math.ceil(crtPage / (double) pageBtnCnt) * pageBtnCnt;

		// 시작 페이지 번호
		int startPageNo = endPageNo - pageBtnCnt + 1;
		
		// 이전, 다음버튼
		boolean prev, next;

		if (startPageNo != 1) {
			prev = true;
		} else {
			prev = false;
		}

		if (endPageNo * postCnt < totalPostCnt) {
			next = true;
		} else {
			next = false;
			endPageNo = (int) Math.ceil(totalPostCnt / (double) postCnt);
		}

		// 맵으로 데이터 묶음
		Map<String, Object> pageMap = new HashMap<String, Object>();
		pageMap.put("startPostNo", startPostNo);
		pageMap.put("endPostNo", endPostNo);
		pageMap.put("startPageNo", startPageNo);
		pageMap.put("endPageNo", endPageNo);
		pageMap.put("prev", prev);
		pageMap.put("next", next);

		return pageMap;
	}
}
