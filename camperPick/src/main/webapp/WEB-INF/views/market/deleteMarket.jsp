<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css">        
<div class="page-main">
	<h4 class="align-center" style="margin-bottom:10px;"><b>거래게시판 삭제</b></h4>
	<form:form action="marketDelete.do" modelAttribute="marketVO">
		<form:hidden path="market_num"/>
		<form:errors element="div" cssClass="error-color"/>
		<ul>
			<li>
				<label for="passwd">비밀번호</label>
				<form:password path="passwd"/>
				<form:errors path="passwd" cssClass="error-color"/>
			</li>
			<li>
				<input type="hidden" value="${user_num}" id="mem_num" name="mem_num">
			</li>
		</ul>
		<div class="align-center">
			<input type="submit" value="삭제" class="btn btn-dark" style="width:120px; margin-top:20px;">
			<input type="button" value="목록" onclick="location.href='${pageContext.request.contextPath}/market/marketList.do'" class="btn btn-dark" style="width:120px; margin-top:20px;">	
		</div>
	</form:form>
</div>
