<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

    	<div id="aside">
			<h2>방명록</h2>
			<ul>
				<li><a href="${pageContext.request.contextPath }/guestbook/addList">일반방명록</a></li>
				<li><a href="${pageContext.request.contextPath }/guestbook/ajaxList">ajax방명록</a></li>
			</ul>
		</div>
