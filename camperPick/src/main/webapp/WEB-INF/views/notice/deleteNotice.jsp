<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css">        
<div class="page-main">
	<h4 class="align-center" style="margin-bottom:10px;"><b>공지사항 삭제</b></h4>
	<form:form action="noticeDelete.do" modelAttribute="noticeVO">
		<form:hidden path="notice_num"/>
		<form:errors element="div" cssClass="error-color"/>
		<ul>
			<li>
				<label for="passwd">비밀번호</label>
				<form:password path="passwd" class="form-control mt-4"/>
				<form:errors path="passwd" cssClass="error-color"/>
			</li>
			<li>
				<input type="hidden" value="${user_num}" id="mem_num" name="mem_num">
			</li>
		</ul>
		<div class="align-center">
			<input type="submit" value="삭제" class="button-large" style="width:120px; margin-top:20px;">
			<input type="button" value="목록" class="button-large" onclick="location.href='${pageContext.request.contextPath}/notice/noticeList.do'" style="width:120px; margin-top:20px;">	
		</div>
	</form:form>
</div>
