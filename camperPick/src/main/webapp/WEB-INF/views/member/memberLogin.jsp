<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css">  
<!-- 중앙 내용 시작 -->
<div class="page-main">
	<h2>회원 로그인</h2>
	<form:form id="login_form" action="login.do" modelAttribute="memberVO">
		<ul>
			<li>
				<label for="email" class="form-label mt-4">이메일</label>
				<form:input path="email" class="form-control" placeholder="Enter email" style="width:300px;"/>
			</li>
			<li>
				<label for="passwd" class="form-label mt-4">비밀번호</label>
				<form:password path="passwd" class="form-control" placeholder="Password" style="width:300px;"/>
			</li>
			<li>
				<form:errors element="div" cssClass="error-color" style="margin-top:20px;"/>
			</li>
		</ul>
		<div class="align-center">
			<form:button class="btn btn-dark" style="width:300px; margin-right:80px; margin-top:10px;">로그인</form:button>
			<input type="button" value="회원가입" class="btn btn-dark" onclick="location.href='${pageContext.request.contextPath}/member/registerUser.do'" style="width:300px; margin-top:10px; margin-right:80px;">
		</div>
	</form:form>
	<hr size="1" width="40%">
	<div id="naver_id_login" style="text-align:center">
		<a href="${url}"> <img src="https://developers.naver.com/doc/review_201802/CK_bEFnWMeEBjXpQ5o8N_20180202_7aot50.png" style="height:40px; margin-right:40px;"/></a>
	</div>
</div>
<!-- 중앙 내용 끝 -->