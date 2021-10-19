<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<%-- <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css"> --%>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript" src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/videoAdapter.js"></script>    
<!DOCTYPE html>
<!-- 거래게시판 디테일 시작 -->
<div class="page-main">
	<h4 class="align-center" style="margin-bottom:10px;"><b>거래게시판 상세</b></h4>
	<p>
		제목 : ${market.title}<br>
		작성자 : ${market.name}<br>
		조회수 : ${market.hit}
	</p>
	<hr width="100%" size="1" noshade="noshade">
	${market.content}<br>
	<c:if test="${!empty market.filename}">
		<div class="align-center">
			<img src="imageView.do?market_num=${market.market_num}" style="max-width: 500px">
		</div>
		<hr width="100%" size="1" noshade="noshade">
	</c:if>
	<c:if test="${empty market.filename}">
		<hr width="100%" size="1" noshade="noshade">
	</c:if>
	<div class="align-right">
		<c:if test="${user_num == market.mem_num}">
		<input type="button" value="수정" onclick="location.href='marketUpdate.do?market_num=${market.market_num}'" class="btn btn-outline-dark" style="font-size:14px;">
		<input type="button" value="삭제" onclick="location.href='marketDelete.do?market_num=${market.market_num}'" class="btn btn-outline-dark" style="font-size:14px;">
		</c:if>
		<input type="button" value="목록" onclick="location.href='${pageContext.request.contextPath}/market/marketList.do'" class="btn btn-outline-dark" style="font-size:14px;">
	</div>
</div>
<!-- 거래게시판 디테일 끝 -->