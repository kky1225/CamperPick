<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-3.6.0.min.js"></script>      
<style type="text/css">

tr{
	border-bottom: 1px solid gray;
}
</style>
<script type="text/javascript">
	$(function() {
		// 검색 유효성 체크
		$('#search_form').submit(function() {
			if($('#keyword').val().trim()==''){
				alert('검색어를 입력하세요!');
				$('#keyword').val('').focus();
				return false;
			}
		})
	})
</script>
<!-- 거래게시판 메인 시작 -->
<div class="page-main">
	<h4 class="align-center" style="margin-bottom:10px;"><b>거래게시판</b></h4>
	<form action="marketList.do" id="search_form" method="get">
		<ul class="search">
			<li>
				<select name="keyfield" id="keyfield">
					<option value="1">제목</option>
					<option value="2">작성자</option>
					<option value="3">내용</option>
					<option value="4">제목+내용</option>
				</select>
			</li>
			<li>
				<input type="search" id="keyword" name="keyword">
			</li>
			<li>
				<input type="submit" value="검색" class="button" style="font-size:14px;">
				<input type="button" value="목록" class="button" style="font-size:14px;" onclick="location.href='marketList.do'">
			</li>
		</ul>
	</form>
	<div class="align-right">
		<c:if test="${user_auth > 1}">
		<input type="button" value="글쓰기" class="button-large" onclick="location.href='marketWrite.do'">
		</c:if>
	</div>
	<c:if test="${count == 0}">
	<div class="result-display">출력할 내용이 없습니다.</div>
	</c:if>
	<c:if test="${count > 0}">
	<table class="table table-borderless" style="text-align:center;">
		<thead class="thead-dark">
		<tr>
			<th>상태</th>
			<th>제목</th>
			<th>작성자</th>
			<th>날짜</th>
			<th>조회수</th>
		</tr>
		</thead>
		<c:forEach var="market" items="${list}">
		<tr>
			<c:if test="${market.choice==0 && market.state==0}"><td>판매</td></c:if>
			<c:if test="${market.choice==0 && market.state==1}"><td>판매완료</td></c:if>
			<c:if test="${market.choice==1 && market.state==0}"><td>구매</td></c:if>
			<c:if test="${market.choice==1 && market.state==1}"><td>구매완료</td></c:if>
			<td><a href="marketDetail.do?market_num=${market.market_num}">${market.title}</a></td>
			<td>${market.name}</td>
			<td>${market.reg_date}</td>
			<td>${market.hit}</td>
		</tr>
		</c:forEach>
	</table>
	<div class="align-center">${pagingHtml}</div>
	</c:if>
</div>
<!-- 거래게시판 메인 끝 --> 