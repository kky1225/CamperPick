<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript" src="${pageContext.request.contextPath }/resources/js/jquery-3.6.0.min.js"></script>
<!-- 중앙 내용 시작 -->
<div class="page-main">
<h2 class="align-center">예약 목록</h2>
<c:if test="${count==0 }">
	<div class="result-display">
		예약 내역이 없습니다.
	</div>
</c:if>
<c:if test="${count>0 }">
	<table>
		<tr>
			<th>예약 번호</th>
			<th>예약자명</th>
			<th>캠핑장명</th>
			<th>날짜</th>
			<th>예약상태</th>
		</tr>
		<c:forEach var="reservation" items="${list }">
			<tr>
				<td>${reservation.res_num }</td>
				<td><a href="detailReservation.do?res_num=${reservation.res_num }">${reservation.res_name}</a></td>
				<td>${reservation.camp_name }</td>
				<td>${reservation.res_start }~${reservation.res_end }</td>
				<td>${reservation.res_state }</td>
			</tr>
		</c:forEach>
	</table>
</c:if>
<br>
<div class="align-center">
	<input type="button" value="홈으로" onclick="location.href='${pageContext.request.contextPath}/main/main.do'">
</div>
</div>


<!-- 중앙 내용 끝 -->