<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- 중앙 내용 시작 -->
<div class="page-main">
	<h2>리뷰 수정</h2>
	<form:form id="register_form" action="reviewupdate.do" modelAttribute="reviewVO"
	           enctype="multipart/form-data">
	    <form:hidden path="review_num"/> 
	    <form:hidden path="res_num"/> 
	    <form:errors element="div" cssClass="error-color"/>      
		<ul>
			<li>
				<label for="title">제목</label>
				<form:input path="title"/>
				<form:errors path="title" cssClass="error-color"/>
			</li>
			<li>
				<label for="content">내용</label>
				<form:textarea path="content"/>
				<form:errors path="content" cssClass="error-color"/>
			</li>
			<li>
				<label for="upload">이미지 파일</label>
				<input type="file" name="upload" id="upload" accept="image/gif,image/png,image/jpeg">
				<c:if test="${!empty reviewVO.filename}">
				<br>
				<span id="file_detail">(${reviewVO.filename})파일이 등록되어 있습니다.
				다시 업로드하면 기존 파일은 삭제됩니다.</span>
				<input type="button" class="button" value="파일삭제" id="file_del">
				<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-3.6.0.min.js"></script>
				<script type="text/javascript">
				$(function(){
					$('#file_del').click(function(){
						var choice = confirm('삭제하시겠습니까?');
						if(choice){
							$.ajax({
								data:{review_num:${reviewVO.review_num}},
								type:'post',
								url:'deleteFile.do',
								dataType:'json',
								cache:false,
								timeout:30000,
								success:function(param){
									if(param.result == 'logout'){
										alert('로그인 후 사용하세요!');
									}else if(param.result == 'success'){
										$('#file_detail').hide();
									}else{
										alert('파일 삭제 오류 발생');
									}
								},
								error:function(){
									alert('네트워크 오류 발생');
								}
							});
						}
					});
				});
				</script>
				</c:if>
			</li>
		</ul>
		<div class="align-center">
			<form:button>수정</form:button>
			<input type="button" class="button" value="목록" onclick="location.href='reviewlist.do'">
		</div>	
	</form:form>
</div>
<!-- 중앙 내용 끝 -->



