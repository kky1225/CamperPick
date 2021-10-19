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
			<th>번호</th>
			<th>내용</th>
			<th>시간</th>
		</tr>
		</thead>
		<c:forEach var="notification" items="${list}">
		<c:if test="${notification.date_time == notification.read_time}">
			<tr>
				<td><b>${notification.not_num}</b></td>
				<td><b><a href="${pageContext.request.contextPath}/reservation/detailReservation.do?res_num=${notification.res_num}">${notification.message}</a></b></td>
				<td><b>${notification.date_time}</b></td>
			</tr>
		</c:if>
		<c:if test="${notification.date_time != notification.read_time}">
			<tr>
				<td>${notification.not_num}</td>
				<td><a href="${pageContext.request.contextPath}/reservation/detailReservation.do?res_num=${notification.res_num}">${notification.message}</a></td>
				<td>${notification.date_time}</td>
			</tr>
		</c:if>
		</c:forEach>
	</table>
</div>