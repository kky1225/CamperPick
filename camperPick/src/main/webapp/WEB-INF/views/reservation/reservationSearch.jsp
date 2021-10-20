<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css">
<script type="text/javascript" src="${pageContext.request.contextPath }/resources/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
$(function(){
	//검색 유효성 체크
	$('#search_form').submit(function(){
		if($('#email').val().trim()==''){
			alert('이메일을 입력하세요!');
			$('#email').val('').focus();
			return false;
		}
	});
});
</script>
<div class="page-main">
	<h4 class="align-center" style="margin-bottom:10px;"><b>예약 확인</b></h4>
	<form id="search_form" action="getReservationList.do" method="get">
			
		<div class="align-center">
				<input type="email" id="email" name="email" placeholder="가입 이메일을 입력하세요.">
				<input type="submit" class="button" value="확인">
		</div>
	</form>
</div>