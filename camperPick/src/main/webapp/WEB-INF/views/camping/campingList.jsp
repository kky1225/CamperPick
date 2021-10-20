<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css">
<script type="text/javascript" src="${pageContext.request.contextPath }/resources/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
$(function(){
	//검색 유효성 체크
	$('#search_form').submit(function(){
		if($('#keyword').val().trim()==''){
			alert('검색어를 입력하세요!');
			$('#keyword').val('').focus();
			return false;
		}
	});
});
</script>
<!-- 중앙 내용 시작 -->
<div class="page-main">
<h4 class="align-center" style="margin-bottom:10px;"><b>캠핑장 목록</b></h4>
<c:if test="${!empty user_num && user_auth==3}">
<div class="align-right">
	<input type="button" value="등록" onclick="location.href='write.do'">
	<input type="button" value="데이터 받기" onclick="location.href='insertData.do'">
</div>
</c:if>

<c:if test="${count==0 }">
	<div class="result-display">
		등록된 캠핑장이 없습니다.
	</div>
</c:if>
<c:if test="${count>0 }">
	<table class="table table-borderless" style="text-align:center;">
		<thead class="thead-dark">
			<tr>
				<th>캠핑장 번호</th>
				<th>캠핑장명</th>
				<th>주소</th>
				<th>전화번호</th>
				<th>객실수</th>
			</tr>
		</thead>
		<c:forEach var="camping" items="${list }">
			<tr>
				<td>${camping.camping_num }</td>
				<td><a href="detail.do?camping_num=${camping.camping_num }">${camping.camp_name}</a></td>
				<td>${camping.camp_address }</td>
				<td>${camping.camp_phone }</td>
				<td>${camping.rcount }</td>
			</tr>
		</c:forEach>
	</table>
	<div class="align-center">${pagingHtml }</div>
</c:if>
<br>
<div class="align-center">
<form id="search_form" action="list.do" method="get">
	<ul class="search">
		<li>
			<select name="keyfield" id="keyfield">
				<option value="1">캠핑장명</option>
				<option value="2">지역</option>
			</select>
		</li>
		<li>
			<input type="search" name="keyword" id="keyword">
		</li>
		<li>
			<input type="submit" value="검색">
			<input type="button" value="목록" onclick="location.href='list.do'">
		</li>
	</ul>
</form>
</div>
</div>

<!-- 중앙 내용 끝 -->