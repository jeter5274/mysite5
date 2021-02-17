package com.javaex.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javaex.vo.GalleryVo;

@Repository
public class GalleryDao {

	@Autowired
	private SqlSession sqlSession;
	
	//리스트 셀렉트
	public List<GalleryVo> selectList(){
		System.out.println("[GalleryDao] selectList");
		
		return sqlSession.selectList("gallery.selectList");
	}
	
	//페이지 당 갯수에 맞춘 리스트 셀렉트
	public List<GalleryVo> selectList(int startPostNo, int endPostNo){
		System.out.println("[GalleryDao] selectList(startPostNo, endPostNo)");
		
		Map<String, Object> pageMap = new HashMap<String, Object>();
		pageMap.put("startPostNo", startPostNo);
		pageMap.put("endPostNo", endPostNo);
		
		return sqlSession.selectList("gallery.selectOnePage", pageMap);
	}
	
	
	//파일정보 저장
	public int insertFileInfo(GalleryVo galleryVo) {
		System.out.println("[GalleryDao] insertFileInfo");
		
		return sqlSession.insert("gallery.insertFileInfo", galleryVo);
	}
	
	//파일정보 삭제
	public int deleteFileInfo(int no) {
		System.out.println("[GalleryDao] deleteFileInfo");

		return sqlSession.delete("gallery.deleteFileInfo", no);
	}
	
	//글 갯수
	public int selectPostCnt() {
		System.out.println("[GalleryDao] selectPostCnt");

		return sqlSession.selectOne("gallery.selectPostCnt");
	}
	
	
	
}
