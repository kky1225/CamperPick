<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript" src="${pageContext.request.contextPath }/resources/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
$(function(){
	//검색 유효성 체크
	$('#search_form').submit(function(){
		if($('#res_phone').val().trim()==''){
			alert('전화번호를 입력하세요!');
			$('#res_phone').val('').focus();
			return false;
		}
		if($('#res_name').val().trim()==''){
			alert('예약자명을 입력하세요!');
			$('#res_name').val('').focus();
			return false;
		}
		
	});
});
</script>
<div class="page-main">
<h2 class="align-center">예약 확인</h2>
<form id="search_form" action="getReservationList.do" method="get">
	<ul>
			<li>
				<label for="res_name">예약자명</label>
				<input type="text" id="res_name" name="res_name">
			</li>
			<li>
				<label for="res_phone">전화번호</label>
				<input type="text" id="res_phone" name="res_phone" placeholder="'-' 필수">
			</li>
	</ul>
	<div class="align-center">
			<input type="submit" value="확인">
			<input type="button" value="홈으로" onclick="location.href='${pageContext.request.contextPath}/main/main.do'">
	</div>
</form>
</div>