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
<c:if test="${!empty user_num && user_auth==4}">
<div class="align-right">
	<input type="button" class="button" value="등록" onclick="location.href='write.do'">
	<input type="button" class="button" value="데이터 받기" onclick="location.href='insertData.do'">
</div>
</c:if>

<c:if test="${count==0 }">
	<div class="result-display">
		등록된 캠핑장이 없습니다.
	</div>
</c:if>
<c:if test="${count>0 }">
	<table class="table table-hover" style="text-align:center; border-style:none;">
		<thead class="thead-dark">
			<tr>
				<th>사진</th>
				<th>캠핑장명</th>
				<th>주소</th>
				<th>객실수</th>
			</tr>
		</thead>
		<c:forEach var="camping" items="${list }">
			<tr onClick="location.href='detail.do?camping_num=${camping.camping_num }'" style="border-bottom:1px solid #000;">
				<td>
				<c:if test="${!empty camping.filename }">
					<img src="${pageContext.request.contextPath }/camping/imageView.do?camping_num=${camping.camping_num}" border="0" width="150" height="150">
				</c:if>
				<c:if test="${empty camping.filename }">
					<img src="${pageContext.request.contextPath }/resources/images/noImage.gif" border="0" width="150" height="150" >
				</c:if>
				</td>
				<td style="vertical-align:middle;">${camping.camp_name}</td>
				<td style="vertical-align:middle;">${camping.camp_address }</td>
				<td style="vertical-align:middle;">${camping.rcount }</td>
			</tr>
		</c:forEach>
	</table>
	<div class="align-center" style="margin-top:30px;">${pagingHtml }</div>
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
			<input type="submit" class="button" value="검색">
			<input type="button" class="button" value="목록" onclick="location.href='list.do'">
		</li>
	</ul>
</form>
</div>
</div>

<!-- 중앙 내용 끝 -->