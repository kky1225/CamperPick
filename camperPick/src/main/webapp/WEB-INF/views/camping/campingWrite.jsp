<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css">  
<script type="text/javascript" src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<script type="text/javascript">
	$(function(){
		if($('#upload').change(function(){
			$('#filename_text').val($(this)[0].files[0].name);
		}));
	});
</script>
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
				<div class="row">
					<div class="col-auto" style="margin-top:10px;">
						첨부파일
					</div>
					<div class="col-auto" style="margin-left:32px;">
						<input id="filename_text" class="form-control mt-1" value="파일선택" style="width:200px; margin-left:14px;" readonly>
					</div>
					<div class="col-auto" style="margin-top:5px;">
						<label for="upload" class="btn btn-dark" style="width:60px;">파일</label>
					</div>
					<input type="file" id="upload" name="upload" accept="image/gif,image/png,image/jpeg" style="display:none;">
				</div>
			</li>
			
		</ul>
		<div class="align-center">
			<form:button class="button" style="margin-top:30px;">전송</form:button>
			<input type="button" class="button" style="margin-top:30px;" value="목록" onclick="location.href='list.do'">
		</div>
	</form:form>
</div>
<!-- 중앙 내용 끝 -->