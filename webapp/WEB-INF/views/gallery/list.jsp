<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<link href="${pageContext.request.contextPath }/assets/css/mysite.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath }/assets/css/gallery.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath }/assets/bootstrap/css/bootstrap.css" rel="stylesheet" type="text/css">

<script type="text/javascript"
	src="${pageContext.request.contextPath }/assets/js/jquery/jquery-1.12.4.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/assets/bootstrap/js/bootstrap.js"></script>

</head>


<body>
	<div id="wrap">

		<c:import url="/WEB-INF/views/include/header.jsp"></c:import>
		<!-- //header -->
		<!-- //nav -->

		<c:import url="/WEB-INF/views/include/galleryAside.jsp"></c:import>
		<!-- //aside -->


		<div id="content">

			<div id="content-head">
				<h3>갤러리</h3>
				<div id="location">
					<ul>
						<li>홈</li>
						<li>갤러리</li>
						<li class="last">갤러리</li>
					</ul>
				</div>
				<div class="clear"></div>
			</div>
			<!-- //content-head -->


			<div id="gallery">
				<div id="list">

					<c:if test="${authUser != null }">
						<button id="btnImgUpload">이미지올리기</button>
						<div class="clear"></div>
					</c:if>

					<ul id="viewArea">

						<c:forEach items="${pageMap.galleryList }" var="galleryVo">
							<!-- 이미지반복영역 -->
							<li data-no="${galleryVo.no}" 
								data-content="${galleryVo.content}" 
								data-savename="${galleryVo.saveName}"
								data-chkwriter="${galleryVo.userNo == authUser.no}" 
								id="galleryNo${galleryVo.no}">
								<div class="view">
									<img class="imgItem" src="${pageContext.request.contextPath }/upload/${galleryVo.saveName}">
									<div class="imgWriter">
										작성자: <strong>${galleryVo.writer } </strong>
									</div>
								</div>
							</li>
							<!-- 이미지반복영역 -->
						</c:forEach>

					</ul>
					
					<div class="clear"></div>
				
					<div id="paging">
						<ul>
							<c:if test="${pageMap.prev == true }">
								<li><a href="${pageContext.request.contextPath }/gallery/list?crtPage=${pageMap.startPageNo-1}">◀</a></li>
							</c:if>
							
							<c:forEach begin="${pageMap.startPageNo }" end="${pageMap.endPageNo }" step="1" var="page">
								
								<c:choose>
									<c:when test="${(empty param.crtPage && page==1) || param.crtPage == page}">
										<li class="active"><a href="${pageContext.request.contextPath }/gallery/list?crtPage=${page }">${page }</a></li>
									</c:when>
									<c:otherwise>
										<li><a href="${pageContext.request.contextPath }/gallery/list?crtPage=${page }">${page }</a></li>
									</c:otherwise>
								</c:choose>
									
							</c:forEach>
							
							<c:if test="${pageMap.next == true }">
								<li><a href="${pageContext.request.contextPath }/gallery/list?crtPage=${pageMap.endPageNo+1}">▶</a></li>
							</c:if>
						</ul>
					</div>
					<!-- //paging -->
				
				</div>
				<!-- //list -->
				
		
			</div>
			<!-- //board -->
		</div>
		<!-- //content  -->
		
		<div class="clear"></div>
		
		

		<c:import url="/WEB-INF/views/include/footer.jsp"></c:import>
		<!-- //footer -->

	</div>
	<!-- //wrap -->



	<!-- 이미지등록 팝업(모달)창 -->
	<div class="modal fade" id="addModal">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">이미지등록</h4>
				</div>

				<form method="post" action="${pageContext.request.contextPath }/gallery/upload"
					enctype="multipart/form-data">
					<div class="modal-body">
						<div class="form-group">
							<label class="form-text">글작성</label> <input id="addModalContent" type="text" name="content"
								value="">
						</div>
						<div class="form-group">
							<label class="form-text">이미지선택</label> <input id="file" type="file" name="file" value="">
						</div>
					</div>
					<div class="modal-footer">
						<button type="submit" class="btn" id="btnUpload">등록</button>
					</div>
				</form>


			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!-- /.modal -->



	<!-- 이미지보기 팝업(모달)창 -->
	<div class="modal fade" id="viewModal">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">이미지보기</h4>
				</div>
				<div class="modal-body">

					<div class="formgroup">
						<img id="viewModelImg" src="">
						<!-- ajax로 처리 : 이미지출력 위치-->
					</div>

					<div class="formgroup">
						<p id="viewModelContent"></p>
						<!-- ajax로 처리 : 내용 출력 위치-->
					</div>

				</div>
				
				<div class="modal-footer">
				</div>	

				<input type="hidden" name="gallery-no" value="">
				

			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!-- /.modal -->


</body>

<script type="text/javascript">
	//이미지올리기 버튼 클릭
	$("#btnImgUpload").on("click", function() {
		console.log("이미지 올리기 버튼 클릭");

		//모달창 입력 초기화
		$("#addModalContent").val("");
		$("#file").val("");
		//모달창 띄우기
		$("#addModal").modal();

	});

	//게시글 클릭
	$("#viewArea").on("click", "li", function() {

		console.log("게시글 버튼 클릭");

		//이미지 경로 변경
		$("#viewModelImg").attr("src", "${pageContext.request.contextPath }/upload/"+$(this).data("savename"));
		
		//콘텐트 내용 변경
		$("#viewModelContent").html($(this).data("content"));
		
		//글번호 표시
		$("input[name='gallery-no']").val($(this).data("no"));
		
		//모달창 띄우기
		$("#viewModal").modal();
		
		//로그인한사람이 작성한 사람이면 true / 아니면 false
		modalFooterCreate($(this).data("chkwriter"));

	});
	
	//삭제버튼 클릭
	$(".modal-footer").on("click", "#btnDel", function(){
		
		console.log("삭제 버튼 클릭");
		
		var no = $("input[name='gallery-no']").val();

		//ajax로 삭제 요청
		$.ajax({

			url : "${pageContext.request.contextPath }/api/gallery/remove",
			type : "post",
			//contentType : "application/json",
			data : {no : no},

			dataType : "json",
			success : function(count) {
				/*성공시 처리해야될 코드 작성*/
				console.log(count);
				
				//창닫기
				$("#viewModal").modal("hide");
				
				//리스트에서 지우기
				$("#galleryNo" +no).remove();
				
			},
			error : function(XHR, status, error) {
				console.error(status + " : " + error);
			}
			
		}); 
		
	});
	
	function modalFooterCreate(chkWriter){
		
		//uppend로 삭제버튼만 추가했더니, 닫기 후 다른 글을 읽을때 삭제버튼이 2이상 생기는 현상이 있어 html전체를 수정하는 것으로 변경
		
		closeBtn = '<button type="button" class="btn btn-default" data-dismiss="modal">닫기</button>';
		delBtn = '<button type="button" class="btn btn-danger" id="btnDel">삭제</button>';
		
		if(chkWriter){
			$(".modal-footer").html(closeBtn+delBtn);
		}else{
			$(".modal-footer").html(closeBtn);
		}		
		
	}
	
</script>




</html>

