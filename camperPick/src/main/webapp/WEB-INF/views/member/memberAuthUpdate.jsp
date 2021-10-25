<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %> 
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css">  
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
	$(function(){
		$('#authUpdate_form').submit(function(){
			if($('#auth').val().trim() > 0 && $('#auth').val().trim() < 5){
			}else{
				alert('회원등급을 잘못 입력하셨습니다.');
				return false;
			}
		});
	})
</script>
<!-- 중앙 내용 시작 -->
<div class="page-main">
	<h2>회원가입</h2>
	<form:form id="authUpdate_form" action="memberAuthUpdate.do" modelAttribute="memberVO">
		<ul>
			<li>
				<label for="mem_num" style="margin-top:10px;">회원번호</label>
				<form:input path="mem_num" class="form-control form-label mt-4" value="${memberVO.mem_num}" readonly="true"/>
			</li>
			<li>
				<label for="email" style="margin-top:10px;">이메일</label>
				<form:input path="email" class="form-control form-label mt-4" value="${memberVO.email}" readonly="true"/>
			</li>
			<li>
				<label for="auth" style="margin-top:10px;">회원등급</label>
				<form:input path="auth" class="form-control form-label mt-4" value="${memberVO.auth}"/>
				<br>
				<p> 0 : 탈퇴회원, 1 : 정지회원, 2 : 일반회원, 3 : 소셜회원, 4 : 관리자</p>
			</li>
		</ul>
		<div class="align-center">
			<form:button class="btn btn-dark mt-4" style="width:335px; margin-right:250px;">회원등급 변경</form:button>
		</div>
	</form:form>
</div>