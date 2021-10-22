<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css">  
<!-- 중앙 내용 시작 -->
<div class="page-main">
	<h2>회원탈퇴</h2>
	<form:form id="delete_form" action="delete.do" modelAttribute="memberVO">
		<ul>
			<li>
				<label for="email" style="margin-top:10px;">이메일</label>
				<form:input path="email" class="form-control form-label mt-4"/>
				<form:errors path="email" cssClass="error-color"/>
				<span id="message_email"></span>
			</li>
			<li>
				<label for="passwd" style="margin-top:10px;">비밀번호</label>
				<form:password path="passwd" class="form-control form-label mt-4"/>
				<form:errors path="passwd" cssClass="error-color"/>
				<span id="message_passwd"></span>
			</li>
		</ul>
		<div class="align-center">
			<form:button class="button-large">탈퇴</form:button>
			<input type="button" class="button-large" value="취소" onclick="location.href='${pageContext.request.contextPath}/member/myPage.do'">
			<input type="button" class="button-large" value="홈으로" onclick="location.href='${pageContext.request.contextPath}/main/main.do'">
		</div>
	</form:form>
</div>
<!-- 중앙 내용 끝 -->