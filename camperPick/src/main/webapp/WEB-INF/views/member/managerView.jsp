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
	<h4 class="align-center" style="margin-bottom:10px;"><b>회원정보</b></h4>
	<form action="managerPage.do" id="search_form" method="get">
		<ul class="search">
			<li>
				<select name="keyfield" id="keyfield">
					<option value="1">이메일</option>
					<option value="2">전화번호</option>
					<option value="3">이름</option>
				</select>
			</li>
			<li>
				<input type="search" id="keyword" name="keyword">
			</li>
			<li>
				<input type="submit" value="검색" class="btn btn-outline-dark" style="font-size:14px;">
				<input type="button" value="목록" class="btn btn-outline-dark" style="font-size:14px;" onclick="location.href='marketList.do'">
			</li>
		</ul>
	</form>
	<c:if test="${count == 0}">
	<div class="result-display">출력할 내용이 없습니다.</div>
	</c:if>
	<c:if test="${count > 0}">
	<table class="table table-borderless" style="text-align:center;">
		<thead class="thead-dark">
		<tr>
			<th>회원번호</th>
			<th>이메일</th>
			<th>이름</th>
			<th>계정생성일</th>
			<th>회원등급</th>
			<th>등급변경</th>
		</tr>
		</thead>
		<c:forEach var="member" items="${list}">
		<tr>
			<td>${member.mem_num}</td>
			<td>${member.email}</td>
			<td>${member.name}</td>
			<td>${member.reg_date}</td>
			<c:if test="${member.auth == 0}">
				<td>탈퇴회원</td>
			</c:if>
			<c:if test="${member.auth == 1}">
				<td>정지회원</td>
			</c:if>
			<c:if test="${member.auth == 2}">
				<td>일반회원</td>
			</c:if>
			<c:if test="${member.auth == 3}">
				<td>소셜회원</td>
			</c:if>
			<c:if test="${member.auth == 4}">
				<td>관리자</td>
			</c:if>
			<th><input type="button" class="btn btn-outline-dark" value="변경" onclick="location.href='memberAuthUpdate.do?mem_num=${member.mem_num}'"></th>
		</tr>
		</c:forEach>
	</table>
	<div class="align-center">${pagingHtml}</div>
	</c:if>
</div>
<!-- 거래게시판 메인 끝 --> 