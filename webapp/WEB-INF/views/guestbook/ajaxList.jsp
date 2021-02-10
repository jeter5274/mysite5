<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<link href="${pageContext.request.contextPath }/assets/bootstrap/css/bootstrap.css" rel="stylesheet" type="text/css">

<link href="${pageContext.request.contextPath }/assets/css/mysite.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath }/assets/css/guestbook.css" rel="stylesheet" type="text/css">


<script type="text/javascript" src="${pageContext.request.contextPath }/assets/js/jquery/jquery-1.12.4.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/assets/bootstrap/js/bootstrap.js"></script>


</head>

<body>
	<div id="wrap">

		<!-- //header -->
		<!-- //nav -->
		<c:import url="/WEB-INF/views/include/header.jsp"></c:import>

		<!-- //aside -->
		<c:import url="/WEB-INF/views/include/guestbookAside.jsp"></c:import>

		<div id="content">

			<div id="content-head">
				<h3>일반방명록</h3>
				<div id="location">
					<ul>
						<li>홈</li>
						<li>방명록</li>
						<li class="last">일반방명록</li>
					</ul>
				</div>
				<div class="clear"></div>
			</div>
			<!-- //content-head -->

			<div id="guestbook">
				<!-- <form action="" method=""> -->
				<table id="guestAdd">
					<colgroup>
						<col style="width: 70px;">
						<col>
						<col style="width: 70px;">
						<col>
					</colgroup>
					<tbody>
						<tr>
							<th><label class="form-text" for="input-uname">이름</label></th>
							<td><input id="input-uname" type="text" name="name"></td>
							<th><label class="form-text" for="input-pass">패스워드</label></th>
							<td><input id="input-pass" type="password" name="pass"></td>
						</tr>
						<tr>
							<td colspan="4"><textarea name="content" cols="72" rows="5"></textarea></td>
						</tr>
						<tr class="button-area">
							<td colspan="4"><button id="btnSubmit" type="submit">등록</button></td>
						</tr>
					</tbody>

				</table>
				<!-- //guestWrite -->
				<input type="hidden" name="action" value="add">

				<!-- </form> -->

				<div id="guestbookListArea">
					<!-- 방명록 글 리스트 출력 영역 -->
				</div>

			</div>
			<!-- //guestbook -->
		</div>
		<!-- //content  -->
		<div class="clear"></div>

		<!-- //footer -->
		<c:import url="/WEB-INF/views/include/footer.jsp"></c:import>

	</div>
	<!-- //wrap -->

	<!-- 모달창 영역 -->
	<div id="delModal" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content">
			
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">방명록 삭제</h4>
				</div>
				
				<div class="modal-body">
				
					<label>비밀번호</label>
					<input id="modalPassword" type="text" name="password" value=""><br>
					
					<input id="modalNo" type="text" name="no" value="">
					
				</div>
				
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">취소</button>
					<button id="modalBtnDel" type="button" class="btn btn-primary">삭제</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!-- /.modal -->
	
	
</body>

<script type="text/javascript">
	//DOM이 생성되면
	$("document").ready(function() {
		console.log("ready");

		//방명록 리스트 출력
		fetchList();

	});

	//모달창의 삭제버튼을 클릭 할때
	$("#modalBtnDel").on("click", function(){
		
		console.log("모달 창 삭제버튼 클릭");
		
		//입력된 비밀번호, 게시글 번호 수집
		//var password = $("#modalPassword").val();
		//var no = $("#modalNo").val();

		//객체를 활용해서 필요한 정보를 수집
		var guestbookVo={
				password: $("#modalPassword").val(),
				no: $("#modalNo").val()
		};
		
		console.log(guestbookVo);
		
		//ajax로 삭제 요청
		$.ajax({

			url : "${pageContext.request.contextPath }/api/guestbook/remove",
			type : "post",
			//contentType : "application/json",
			data : guestbookVo,

			dataType : "json",
			success : function(count) {
				/*성공시 처리해야될 코드 작성*/
				console.log(count);
				
				if(count){	//count == 1 (삭제성공)
					//모달창 닫기
					$("#delModal").modal("hide");
				
					//no 테이블(글)을 화면에서 삭제
					$("#t-" +guestbookVo.no).remove();
					
				}else{	//count == 0 (삭제실패)
					
					alert("비밀번호를 잘 못 입력하셨습니다.");
					$("#modalPassword").val("");
					//모달창 닫기
					//$("#delModal").modal("hide");
				}
			},
			error : function(XHR, status, error) {
				console.error(status + " : " + error);
			}
			
		}); 
		
	});
	
	//삭제버튼 클릭할때 -> 비밀번호 입력창 호출
	$("#guestbookListArea").on("click", "a", function(){
		
		event.preventDefault();
		console.log("모달 창 호출");
		
		$("#modalPassword").val("");	//비밀번호 input을 초기화
		
		//게시글의 no 값
		var no = $(this).data("no");
		$("#modalNo").val(no);
		
		//모달창 호출
		$('#delModal').modal();
		
	});
	
	//방명록 등록버튼 클릭할 때
	$("#btnSubmit").on("click", function() {

		console.log("방명록 등록 버튼 클릭")

		/* var name = $("[name ='name']").val();
		var password = $("[name ='pass']").val();
		var content = $("[name ='content']").val();

		console.log(name);
		console.log(password);
		console.log(content); 
		//아래와 같이 객체로 묶음 */
		
		var guestbookVo = {
			name: $("[name ='name']").val(),
			password: $("[name ='pass']").val(),
			content: $("[name ='content']").val()
		}
		
		console.log(guestbookVo);
		
		//ajax방식으로 요청
		$.ajax({

			//객체 자료형태로 데이터를 보내기
			url : "${pageContext.request.contextPath }/api/guestbook/write",
			type : "post",
			//contentType : "application/json",
			data : guestbookVo,
			
			/* //json 자료형태로 데이터를 보내기
			url : "${pageContext.request.contextPath }/api/guestbook/write2",
			type : "post",
			contentType : "application/json",
			data : JSON.stringify(guestbookVo), */
			
			dataType : "json",
			success : function(guestbookVo) {
				/*성공시 처리해야될 코드 작성*/
				console.log(guestbookVo);
				console.log(guestbookVo.no);
				console.log(guestbookVo.name);
				rander(guestbookVo, "up"); //게스트북 정보 출력
			},
			error : function(XHR, status, error) {
				console.error(status + " : " + error);
			}
		});
		
		//입력 폼 비우기
		$("[name ='name']").val("");
		$("[name ='pass']").val("");
		$("[name ='content']").val("");
	});

	//방명록 리스트 출력
	function fetchList(){
		
		$.ajax({

			url : "${pageContext.request.contextPath }/api/guestbook/list",
			type : "post",
			//contentType : "application/json",
			//data : {name : name},

			dataType : "json",
			success : function(guestbookList) {
				/*성공시 처리해야될 코드 작성*/
				console.log(guestbookList);
				for(var i=0; i<guestbookList.length; i++){
					rander(guestbookList[i], "up");
				}
			},
			error : function(XHR, status, error) {
				console.error(status + " : " + error);
			}
		});
		
	}
	
	//방명록 글정보 + html 조합하여 화면에 출력
	function rander(guestbookVo, updown) {

		var str = "";
		str += '<table id="t-' +guestbookVo.no+ '" class="guestRead">';
		str += '	<colgroup>';
		str += '		<col style="width: 10%;">';
		str += '		<col style="width: 40%;">';
		str += '		<col style="width: 40%;">';
		str += '		<col style="width: 10%;">';
		str += '	</colgroup>';
		str += '	<tr>';
		str += '		<td>' + guestbookVo.no + '</td>';
		str += '		<td>' + guestbookVo.name + '</td>';
		str += '		<td>' + guestbookVo.regDate + '</td>';
		str += '		<td><a href="" data-no="' +guestbookVo.no+ '">[삭제]</a></td>';
		str += '	<tr>';
		str += '	<tr>';
		str += '		<td colspan=4 class="text-left">' + guestbookVo.content + '</td>';
		str += '	<tr>';
		str += '</table>';

		if(updown == "up"){
			$("#guestbookListArea").prepend(str);
		}else if(updown == "down"){
			$("#guestbookListArea").append(str);
		}else{
			console.log("방향 미지정");
		}
		
	}
</script>


</html>