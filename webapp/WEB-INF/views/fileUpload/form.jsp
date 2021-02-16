<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<link href="${pageContext.request.contextPath }/assets/css/mysite.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath }/assets/css/gallery.css" rel="stylesheet" type="text/css">

<script type="text/javascript" src="${pageContext.request.contextPath }/assets/js/jquery/jquery-1.12.4.js"></script>

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
				<h3>첨부파일연습</h3>
				<div id="location">
					<ul>
						<li>홈</li>
						<li>갤러리</li>
						<li class="last">첨부파일연습</li>
					</ul>
				</div>
				<div class="clear"></div>
			</div>
			<!-- //content-head -->
		
			<div id="file">
				<form method="post" action="${pageContext.request.contextPath }/fileUp/upload" enctype="multipart/form-data">
					<table>
						<colgroup>
							<col style="width: 600px;">
							<col style="width: 220px;">
						</colgroup>
						<tr>
							<td class="text-left"><input type="file" name="file"></td>
							<td class="text-right"><button type="submit">파일업로드</button></td>
						</tr>
					</table>
				</form>
			</div>
		</div>
		<!-- //content  -->
		<div class="clear"></div>

		<c:import url="/WEB-INF/views/include/footer.jsp"></c:import>
		<!-- //footer -->

	</div>
	<!-- //wrap -->

</body>

<script type="text/javascript">


</script>

</html>