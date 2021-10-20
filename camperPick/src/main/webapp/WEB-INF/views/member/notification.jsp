<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-3.6.0.min.js"></script> 
<!-- 알림페이지 시작 -->
<div class="page-main">
	<h4 class="align-center" style="margin-bottom:10px;"><b>알림</b></h4>
	<table class="table table-borderless" style="text-align:center;">
		<thead class="thead-dark">
		<tr>
			<th>내용</th>
			<th>시간</th>
		</tr>
		</thead>
		<c:forEach var="notification" items="${list}">
			<tr>
				<td><a href="${pageContext.request.contextPath}/reservation/detailReservation.do?res_num=${notification.res_num}">${notification.message}</a></td>
				<td>${notification.date_time}</td>
			</tr>
		</c:forEach>
	</table>
</div>