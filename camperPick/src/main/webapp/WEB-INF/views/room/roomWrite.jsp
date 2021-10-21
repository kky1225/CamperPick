<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css">  
<script type="text/javascript" src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

<!-- 중앙 내용 시작 -->
<div class="page-main">
	<h2 class="align-center" style="margin-bottom:40px;"><b>객실 등록</b></h2>
	<form:form id="register_form" action="writeRoom.do" modelAttribute="roomVO" enctype="multipart/form-data">
		<form:hidden path="camping_num" value="${camping_num }"/>
		<form:errors element="div" cssClass="error-color"/>
		<ul>
			<li>
				<label for="room_name">객실명</label>
				<form:input path="room_name" class="form-control" style="width:300px;margin-bottom:10px;"/>
				<form:errors path="room_name" cssClass="error-color"/>
			</li>
			<li>
				<label for="people">정원</label>
				<input type="number" id="people" name="people" class="form-control" style="width:300px;margin-bottom:10px;">
				<form:errors path="people" cssClass="error-color"/>
			</li>
			<li>
				<label for="area">객실 면적</label>
				<input type="number" id="area" name="area" class="form-control" style="width:300px;margin-bottom:10px;">
				<form:errors path="area" cssClass="error-color"/>
			</li>
			<li>
				<label for="price">가격</label>
				<input type="number" id="price" name="price" class="form-control" style="width:300px;margin-bottom:10px;">
				<form:errors path="price" cssClass="error-color"/>
			</li>
			<li>
				<label for="checkIn">체크인</label>
				<form:input path="checkIn" class="form-control" style="width:300px;margin-bottom:10px;"/>
				<form:errors path="checkIn" cssClass="error-color"/>
			</li>
			<li>
				<label for="checkIn">체크아웃</label>
				<form:input path="checkOut" class="form-control" style="width:300px;margin-bottom:10px;"/>
				<form:errors path="checkOut" cssClass="error-color"/>
			</li>
			<li>
				<label for="info">설명</label>
				<form:textarea path="info" class="form-control" style="width:300px;margin-bottom:10px;"/>
				<form:errors path="info" cssClass="error-color"/>
			</li>
			<li>
				<label for="upload">이미지 파일</label>
				<input type="file" name="upload" id="upload" class="form-control" style="width:300px;margin-bottom:10px;" accept="image/gif,image/png,image/jpeg"/>
				<c:if test="${!empty roomVO.filename }">
					<br>
					<span>(${roomVO.filename })파일이 등록되어 있습니다. 다시 업로드하면 기존 파일은 삭제됩니다.</span>
				</c:if>
			</li>
			
		</ul>
		<div class="align-center">
			<form:button class="button" style="margin-top:30px;">등록</form:button>
			<input type="button" class="button" style="margin-top:30px;" value="목록" onclick="location.href='${pageContext.request.contextPath }/camping/list.do'">
		</div>
	</form:form>
</div>
<!-- 중앙 내용 끝 -->