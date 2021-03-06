<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="${pageContext.request.contextPath }/assets/css/mysite.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath }/assets/css/board.css" rel="stylesheet" type="text/css">

</head>


<body>
	<div id="wrap">

		<!-- //header -->
		<!-- //nav -->
		<c:import url="/WEB-INF/views/include/header.jsp"></c:import>

		<!-- //aside -->
		<c:import url="/WEB-INF/views/include/boardAside.jsp"></c:import>

		<div id="content">

			<div id="content-head">
				<h3>게시판</h3>
				<div id="location">
					<ul>
						<li>홈</li>
						<li>게시판</li>
						<li class="last">댓글게시판</li>
					</ul>
				</div>
				<div class="clear"></div>
			</div>
			<!-- //content-head -->

			<div id="board">
				<div id="list">
					<form action="${pageContext.request.contextPath }/rboard/search" method="get">
						<div class="form-group text-right">
							<input type="text" name="keyword">
							<button type="submit" id=btn_search>검색</button>
						</div>
					</form>
					<table >
						<thead>
							<tr>
								<th>번호</th>
								<th>제목</th>
								<th>글쓴이</th>
								<th>조회수</th>
								<th>작성일</th>
								<th>관리</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${rboardList}" var="rboardVo">
								<tr>
									<td>${rboardVo.no }</td>
									<td class="text-left">
										<c:choose>
											<c:when test="${rboardVo.delStatus == 'delete'}">
												${rboardVo.title}
											</c:when>
											<c:otherwise>
												<a href="${pageContext.request.contextPath }/rboard/read?no=${rboardVo.no }&userNo=${rboardVo.userNo }">
													<c:forEach var="i" begin="1" end="${rboardVo.depth}" step="1">
														&nbsp; &nbsp; &nbsp; &nbsp;
													</c:forEach>
													${rboardVo.title}
												</a>
											</c:otherwise>
										</c:choose>
										
									</td>
									<td>
										<c:if test="${rboardVo.delStatus != 'delete'}">
											${rboardVo.writer}
										</c:if>
									</td>
									<td>
										<c:if test="${rboardVo.delStatus != 'delete'}">
											${rboardVo.hit}
										</c:if>
									</td>
									<td>
										<c:if test="${rboardVo.delStatus != 'delete'}">
											${rboardVo.regDate}
										</c:if>
									</td>
									<td>
										<c:if test="${rboardVo.delStatus != 'delete' && rboardVo.userNo == authUser.no }">
											<a href="${pageContext.request.contextPath }/rboard/remove?no=${rboardVo.no }&groupNo=${rboardVo.groupNo}&orderNo=${rboardVo.orderNo}&depth=${rboardVo.depth}">[삭제]</a>
										</c:if>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
		
					<div id="paging">
						<ul>
							<li><a href="">◀</a></li>
							<li><a href="">1</a></li>
							<li><a href="">2</a></li>
							<li><a href="">3</a></li>
							<li><a href="">4</a></li>
							<li class="active"><a href="">5</a></li>
							<li><a href="">6</a></li>
							<li><a href="">7</a></li>
							<li><a href="">8</a></li>
							<li><a href="">9</a></li>
							<li><a href="">10</a></li>
							<li><a href="">▶</a></li>
						</ul>
						
						
						<div class="clear"></div>
					</div>
					<c:if test="${authUser != null}">
						<a id="btn_write" href="${pageContext.request.contextPath }/rboard/writeForm">글쓰기</a>
					</c:if>
				
				</div>
				<!-- //list -->
			</div>
			<!-- //board -->
		</div>
		<!-- //content  -->
		<div class="clear"></div>

		<!-- //footer -->
		<c:import url="/WEB-INF/views/include/footer.jsp"></c:import>

	</div>
	<!-- //wrap -->

</body>

</html>
