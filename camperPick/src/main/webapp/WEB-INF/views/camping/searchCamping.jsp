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
<div class="page-main">
<h4 class="align-center" style="margin-bottom:10px;"><b>캠핑장 검색</b></h4>
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
			<input type="button" class="button" value="전체목록" onclick="location.href='list.do'">
		</li>
	</ul>
</form>
</div>