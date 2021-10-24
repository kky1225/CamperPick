<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css">  
<!-- 중앙 내용 시작 -->
<div class="page-main">
	<h2>회원정보 수정</h2>
	<form:form id="modify_form" action="update.do" modelAttribute="memberVO">
		<ul>
			<li>
				<label for="name" style="margin-top:10px;">이름</label>
				<form:input path="name" class="form-control form-label mt-4"/>
				<form:errors path="name" cssClass="error-color"/>
			</li>
			<li>
				<label for="phone" style="margin-top:10px;">전화번호</label>
				<form:input path="phone" class="form-control form-label mt-4"/>
				<form:errors path="phone" cssClass="error-color"/>
				<span id="message_phone"></span>
			</li>
		</ul>
		<div class="align-center">
			<form:button class="button-large">수정</form:button>
			<input type="button" class="button-large" value="취소" onclick="location.href='${pageContext.request.contextPath}/member/myPage.do'">
		</div>
	</form:form>
</div>
<!-- 중앙 내용 끝 -->