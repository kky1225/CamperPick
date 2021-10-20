<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-3.6.0.min.js"></script>  
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css">
<style type="text/css">

tr{
	border-bottom: 1px solid gray;
}
</style>
<script type="text/javascript">
	$(function() {
		$('#search_form').submit(function() {
			if($('#keyword').val().trim() == ''){
				alert('검색어를 입력하세요!');
				$('#keyword').val('').focus();
				return false;
			}
		})
	})
</script>
<!-- 공지사항 메인 시작 -->
<div class="page-main">
	<h4 class="align-center" style="margin-bottom:10px;"><b>공지사항</b></h4>
	<form action="noticeList.do" id="search_form" method="get">
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
				<input type="search" size="30" id="keyword" name="keyword">
			</li>
			<li>
				<input type="submit" value="검색" class="button" style="font-size:14px;">
				<input type="button" value="목록" class="button" style="font-size:14px;" onclick="location.href='noticeList.do'">
			</li>
		</ul>
	</form>
	<div class="align-right">
		<c:if test="${user_auth == 4 }">
		<input type="button" value="글쓰기" onclick="location.href='noticeWrite.do'" class="btn btn-dark">
		</c:if>
	</div>
	<c:if test="${count == 0}">
	<div class="result-display">출력할 내용이 없습니다.</div>
	</c:if>
	<c:if test="${count > 0}">
	<table class="table table-borderless" style="text-align:center;">
		<thead class="thead-dark">
		<tr>
			<th>번호</th>
			<th>제목</th>
			<th>작성자</th>
			<th>작성일</th>
			<th>조회수</th>
		</tr>
		</thead>
		<c:forEach var="notice" items="${list}">
		<tr>
			<td>${notice.notice_num}</td>
			<td><a href="noticeDetail.do?notice_num=${notice.notice_num}">${notice.title}</a></td>
			<td>${notice.name}</td>
			<td>${notice.reg_date}</td>
			<td>${notice.hit}</td>
		</tr>
		</c:forEach>
	</table>
	<div class="align-center">${pagingHtml}</div>
	</c:if>
</div>
<!-- 공지사항 메인 끝 --> 