<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css">  
<script type="text/javascript" src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

<!-- 중앙 내용 시작 -->
<div class="page-main">
	<h2 class="align-center" style="margin-bottom:70px;"><B>캠핑장 등록</B></h2>
	<form:form id="register_form" action="write.do" enctype="multipart/form-data" modelAttribute="campingVO">
		<form:errors element="div" cssClass="error-color"/>
		<ul>
			<li>
				<label for="camp_name">캠핑장명</label>
				<form:input path="camp_name" class="form-control" style="margin-bottom:10px;width:300px;"/>
				<form:errors path="camp_name" cssClass="error-color"/>
			</li>
			<li>
				<label for="camp_address">주소</label>
				<form:input path="camp_address" class="form-control" style="margin-bottom:10px;width:300px;"/>
				<form:errors path="camp_address" cssClass="error-color"/>
			</li>
			<li>
				<label for="camp_phone">전화번호</label>
				<form:input path="camp_phone" class="form-control" style="margin-bottom:10px;width:300px;"/>
				<form:errors path="camp_phone" cssClass="error-color"/>
				
			</li>
			<li>
				<label for="rcount">객실 수</label>
				<input type="number" id="rcount" name="rcount" class="form-control" style="width:300px;margin-bottom:10px;">
				<form:errors path="rcount" cssClass="error-color"/>
			</li>
			<li>
				<label for="upload">이미지 파일</label>
				<input type="file" name="upload" id="upload" class="form-control" style="margin-bottom:10px;width:300px;" accept="image/gif,image/png,image/jpeg"/>
				<c:if test="${!empty campingVO.filename }">
					<br>
					<span>(${campingVO.filename })파일이 등록되어 있습니다. 다시 업로드하면 기존 파일은 삭제됩니다.</span>
				</c:if>
			</li>
			
		</ul>
		<div class="align-center">
			<form:button class="button" style="margin-top:30px;">전송</form:button>
			<input type="button" class="button" style="margin-top:30px;" value="목록" onclick="location.href='list.do'">
		</div>
	</form:form>
</div>
<!-- 중앙 내용 끝 -->