<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!-- 중앙 내용 시작 -->
<div class="page-main">
	<h2 class="align-center">캠핑장 수정</h2>
	<form:form id="update_form" action="update.do" modelAttribute="campingVO">
		<form:hidden path="camping_num"/>
		<form:errors element="div" cssClass="error-color"/>
		<ul>
			<li>
				<label for="camp_name">캠핑장명</label>
				<form:input path="camp_name"/>
				<form:errors path="camp_name" cssClass="error-color"/>
			</li>
			<li>
				<label for="camp_address">주소</label>
				<form:input path="camp_address"/>
				<form:errors path="camp_address" cssClass="error-color"/>
			</li>
			<li>
				<label for="camp_phone">전화번호</label>
				<form:input path="camp_phone"/>
				<form:errors path="camp_phone" cssClass="error-color"/>
			</li>
			<li>
				<label for="rcount">객실 수</label>
				<input type="number" id="rcount" name="rcount" value="${campingVO.rcount }">
				<form:errors path="rcount" cssClass="error-color"/>
			</li>
			
		</ul>
		<div class="align-center">
			<form:button>수정</form:button>
			<input type="button" value="목록" onclick="location.href='list.do'">
		</div>
	</form:form>
</div>
<!-- 중앙 내용 끝 -->