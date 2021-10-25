<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css" rel="stylesheet">
<style>
	.ck-editor__editable_inline{
		min-height: 250px;
	}
</style>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-3.6.0.min.js"></script>    
<script type="text/javascript" src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/ckeditor.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/uploadAdapter.js"></script>
<script type="text/javascript">
	$(function(){
		var fname ="";
		var today =new Date();
		var hh = today.getHours();
		
		if(hh>=4 && hh<9){			//새벽 4시부터 낮 9시
			fname="${pageContext.request.contextPath}/resources/images/main_bg1.jpg";
		}else if(hh>=9 && hh<18){	//낮 9시부터 오후 6시
			fname="${pageContext.request.contextPath}/resources/images/main_bg2.jpg";
		}else if(hh>=18 && hh<24){	//오후 6시부터 밤 12시
			fname="${pageContext.request.contextPath}/resources/images/main_bg3.jpg";
		}else{						//밤 12시 부터 새벽 4시
			fname="${pageContext.request.contextPath}/resources/images/main_bg0.jpg";
		}
		
		$('.main_bg').css('background-image','url('+fname+')');
	});
</script>    
<div class="page-main">
	
	<div id="main-content">
		<div class="main_bg">
			<div class="search_container">
	    		<h4 class="search_title" style="font-size:24px;">
			        공지사항
			    </h4>
			</div>
		</div>
	</div>	
	
	<form:form id="noticeUpdate" action="noticeUpdate.do" modelAttribute="noticeVO" enctype="multipart/form-data">
		<form:hidden path="notice_num"/>
		<form:errors element="div" cssClass="error-color"/>
		<ul>
			<li>
				<input type="hidden" value="${user_num}" id="mem_num" name="mem_num">
			</li>
			<li>
				<form:input path="title" class="form-control mt-6" style="width:420px; margin-bottom:10px; margin-top:10px;" placeholder="제목을 입력해 주세요."/>
				<form:errors path="title" cssClass="error-color"/>
			</li>
			<li>
				<form:textarea path="content"/>
				<form:errors path="content" cssClass="error-color"/>
				<script type="text/javascript">
					function MyCustomUploadAdapterPlugin(editor) {
						editor.plugins.get('FileRepository').createUploadAdapter = (loader) => {
							return new UploadAdapter(loader);
						}
					}
					
					ClassicEditor.create(document.querySelector('#content'),{
						extraPlugins:[MyCustomUploadAdapterPlugin]
					}).then(editor => {
						window.editor = editor;
					}).catch(error => {
						console.error(error);
					});
				</script>
			</li>
			<li>
				<label for="passwd" style="margin-top:7px;">비밀번호 확인</label>
				<form:password path="passwd" class="form-control mt-4" style="margin-top:10px;"/>
				<form:errors path="passwd" cssClass="error-color"/>
			</li>
		</ul>
		<p>
		<div class="align-center">
			<input type="submit" value="수정" class="btn btn-dark" style="width:120px; margin-top:20px;">
			<input type="button" value="목록" onclick="location.href='${pageContext.request.contextPath}/notice/noticeList.do'" class="btn btn-dark" style="width:120px; margin-top:20px;">
		</div>
	</form:form>
</div>