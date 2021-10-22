<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css">  
<!-- 중앙 내용 시작 -->
<div class="page-main">
	<h4 class="align-center" style="margin-bottom:10px;"><b>로그인</b></h4>
	<form:form id="login_form" action="login.do" modelAttribute="memberVO">
		<ul>
			<li>
				<label for="email" class="form-label mt-4" style="margin-left:35px;">이메일</label>
				<form:input path="email" class="form-control" placeholder="Enter email" style="width:300px; margin-left:35px;"/>
			</li>
			<li>
				<label for="passwd" class="form-label mt-4" style="margin-left:35px;">비밀번호</label>
				<form:password path="passwd" class="form-control" placeholder="Password" style="width:300px; margin-left:35px;"/>
			</li>
			<li>
				<form:errors element="div" cssClass="error-color" style="margin-top:20px;"/>
			</li>
		</ul>
		<div class="align-center">
			<form:button class="btn btn-dark" style="width:300px; margin-top:10px; margin-right:15px;">로그인</form:button>
			<input type="button" value="회원가입" class="btn btn-dark" onclick="location.href='${pageContext.request.contextPath}/member/registerUser.do'" style="width:300px; margin-top:10px; margin-right:15px;">
		</div>
	</form:form>
	<hr size="1" width="30%">
	<div id="naver_id_login" style="text-align:center">
		<a href="${url}"> <img src="https://developers.naver.com/doc/review_201802/CK_bEFnWMeEBjXpQ5o8N_20180202_7aot50.png" style="height:40px;"/></a>
	</div>
</div>
<!-- 중앙 내용 끝 -->