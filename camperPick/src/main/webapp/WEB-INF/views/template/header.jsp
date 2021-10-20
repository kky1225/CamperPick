<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!-- 상단 시작 -->
<a href="${pageContext.request.contextPath}/main/main.do" class="align-center"><h2>Spring Project</h2></a>
<div class="align-right">
	<c:if test="${!empty user_num}">
	    [<span>${user_email}</span>]
	    <a href="${pageContext.request.contextPath}/member/notification.do">알림</a>
		<a href="${pageContext.request.contextPath}/member/logout.do">로그아웃</a>
	</c:if>
	<c:if test="${empty user_num}">
		<a href="${pageContext.request.contextPath}/member/registerUser.do">회원가입</a>
		<a href="${pageContext.request.contextPath}/member/login.do">로그인</a>
	</c:if>
	<c:if test="${!empty user_num && (user_auth == 2 || user_auth == 3)}">
		<a href="${pageContext.request.contextPath}/member/myPage.do">MY페이지</a>
	</c:if>
	<c:if test="${!empty user_num && (user_auth == 4)}">
		<a href="${pageContext.request.contextPath}/member/managerPage.do">관리자페이지</a>
	</c:if>
</div>
<!-- 상단 끝 -->