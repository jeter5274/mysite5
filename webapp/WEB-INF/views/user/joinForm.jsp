<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<script type="text/javascript"
	src="${pageContext.request.contextPath }/assets/js/jquery/jquery-1.12.4.js"></script>

<link href="${pageContext.request.contextPath }/assets/css/mysite.css"
	rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath }/assets/css/user.css"
	rel="stylesheet" type="text/css">

</head>

<body>
	<div id="wrap">

		<!-- //header -->
		<!-- //nav -->
		<c:import url="/WEB-INF/views/include/header.jsp"></c:import>

		<div id="aside">
			<h2>회원</h2>
			<ul>
				<li>회원정보</li>
				<li>로그인</li>
				<li>회원가입</li>
			</ul>
		</div>
		<!-- //aside -->

		<div id="content">

			<div id="content-head">
				<h3>회원가입</h3>
				<div id="location">
					<ul>
						<li>홈</li>
						<li>회원</li>
						<li class="last">회원가입</li>
					</ul>
				</div>
				<div class="clear"></div>
			</div>
			<!-- //content-head -->

			<div id="user">
				<div id="joinForm">
					<form action="${pageContext.request.contextPath }/user/join"
						method="post">

						<!-- 아이디 -->
						<div class="form-group">
							<label class="form-text" for="input-uid">아이디</label> <input
								type="text" id="input-uid" name="id" value=""
								placeholder="아이디를 입력하세요">
							<button type="button" id="btnChk">중복체크</button>
						</div>

						<p id="msg">
							<!-- 아이디 사용가능여부 메세지 -->
						</p>


						<!-- 비밀번호 -->
						<div class="form-group">
							<label class="form-text" for="input-pass">패스워드</label> <input
								type="password" id="input-pass" name="password" value=""
								placeholder="비밀번호를 입력하세요">
						</div>

						<!-- 이메일 -->
						<div class="form-group">
							<label class="form-text" for="input-name">이름</label> <input
								type="text" id="input-name" name="name" value=""
								placeholder="이름을 입력하세요">
						</div>

						<!-- //나이 -->
						<div class="form-group">
							<span class="form-text">성별</span> <label for="rdo-male">남</label>
							<input type="radio" id="rdo-male" name="gender" value="male">
							<label for="rdo-female">여</label> <input type="radio"
								id="rdo-female" name="gender" value="female">

						</div>

						<!-- 약관동의 -->
						<div class="form-group">
							<span class="form-text">약관동의</span> <input type="checkbox"
								id="chk-agree" value="" name=""> <label for="chk-agree">서비스
								약관에 동의합니다.</label>
						</div>

						<!-- 버튼영역 -->
						<div class="button-area">
							<button type="submit" id="btn-submit">회원가입</button>
						</div>

					</form>
				</div>
				<!-- //joinForm -->
			</div>
			<!-- //user -->
		</div>
		<!-- //content  -->
		<div class="clear"></div>

		<!-- //footer -->
		<c:import url="/WEB-INF/views/include/footer.jsp"></c:import>

	</div>
	<!-- //wrap -->

</body>

<script type="text/javascript">

	$("#btnChk").on("click", function(){
		
		var uid = $("#input-uid").val();	
		var pw = $("#input-pass").val();
		console.log(uid+ ", " +pw);
		
		//ajax 데이터만 받을래
		$.ajax({	//서버와 통신하는 기술
			
			url : "${pageContext.request.contextPath }/user/idcheck",		
			type : "post",
			/* contentType : "application/json", */
			data : {id: uid, password: pw},				//url에 파라미터값을 입력하는 다른 방법

			dataType : "text",
			success : function(result){
				/*성공시 처리해야될 코드 작성*/
				if(result == 'can'){
					console.log("can");
					$("#msg").html("사용할 수 있는 아이디 입니다.");
				}else{
					console.log("cant");
					$("#msg").html("사용할 수 없는 아이디 입니다.");
				}
			},
			/* 성공도 실패도 아닌 에러 */
			error : function(XHR, status, error) { 
				console.error(status + " : " + error);
			}
		});

	});

	//form submit 클릭시, submit되기 전에 
	$("#joinForm").on("submit", function(){
		
		//id중복체크 확인
		var idChkResult = $("#msg").text();
		console.log(idChkResult);
		
		if(idChkResult != "사용할 수 있는 아이디 입니다."){
			
			if(idChkResult == "사용할 수 없는 아이디 입니다."){
				alert(idChkResult);	
			}else{
				alert("id 중복체크를 해주세요.");	
			}
			
			return false;
		}
		//id 중복확인후에 입력값을 바꾸면 회원가입되는 현상이 있음 - 입력된 값을 바꿀때 인증초기화 방법이 필요함

		
		//패스워드 체크 
		var pw = $("#input-pass").val();
		console.log(pw);
		
		if(pw.length < 8){	//8글자 미만 alert(): 패스워드를 8글자 이상 입력해주세요. -> submit 취소
			alert("패스워드를 8글자 이상 입력해주세요.");
			return false;
		}
		/* 
		//패스워드 체크 
		var pw = $("#input-pass").val();
		if(pw.length < 8){	//8글자 이상 통과
			return true;
		}else{				//나머지 alert(): 패스워드를 8글자 이상 입력해주세요. -> submit 취소
			alert("패스워드를 8글자 이상 입력해주세요.");
			return false;
		}
		*/
		
		//이름 입력 체크
		var name = $("#input-name").val();
		console.log(name);
		
		if(name.length < 2){	//외자일 수도 있으니 2글자 이상으로
			alert("이름을 2글자 이상 입력해주세요.");
			return false;
		}
		
		//성별 입력 체크
		var gender = $("input[name='gender']:checked").val();
		console.log(gender);
		
		if(gender == null) {	//!(gender =='male' || gender == 'female')
			alert("성별을 선택해 주세요");
			return false;
		}
		
		//약관 동의 여부 체크
		var check = $("#chk-agree").is(":checked");
		console.log(check);
			
		if(!check){			//동의하기 checkbox 체크 안되어 있을 시 -> alert() : 약관에 동의해 주세요. -> submit 취소
			alert("약관에 동의해 주세요.");
			return false;
		}
		
		return true;
		
		/* 
		//약관 동의 여부 체크
		var check = $("#chk-agree").is(":checked");
		console.log(check);
			
		if(check){		//동의하기 checkbox 체크 시 -> submit
			return true;
		}else{			//동의하기 checkbox 체크 안되어 있을 시 -> alert() : 약관에 동의해 주세요. -> submit 취소
			alert("약관에 동의해 주세요.");
			return false;
		} */
		
		
	});

	//입력된 값이 바뀌면 중복확인 결과 메세지를 지움
	$("#input-uid").change(function(){
		$("#msg").html(null);
	});
	
</script>
</html>