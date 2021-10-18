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
<!-- 거래게시판 수정 시작 -->
<div class="page-main"> 
	<h4 class="align-center" style="margin-bottom:10px;"><b>거래게시판 수정</b></h4>
	<form:form id="marketUpdate" action="marketUpdate.do" modelAttribute="marketVO" enctype="multipart/form-data">
		<form:hidden path="market_num"/>
		<form:errors element="div" cssClass="error-color"/>
		<ul>
			<li>
				<input type="hidden" value="${user_num}" id="mem_num" name="mem_num">
			</li>
			<li>
				<label for="choice">구분</label>
				<input type="radio" name="choice" id="choice" value="0" <c:if test="${marketVO.choice==0}">checked="checked"</c:if>>팝니다
				<input type="radio" name="choice" id="choice" value="1" <c:if test="${marketVO.choice==1}">checked="checked"</c:if>>삽니다
			</li>
			<br>
			<li>
				<label for="title">제목</label>
				<form:input path="title"/>
				<form:errors path="title" cssClass="error-color"/>
			</li>
			<br>
			<li><b>내용</b></li><br>
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
			<br>
			<li>
				<label for="state">거래 상황</label>
				<input type="radio" name="state" id="state" value="0" <c:if test="${marketVO.state==0}">checked="checked"</c:if>>거래중
				<input type="radio" name="state" id="state" value="1" <c:if test="${marketVO.state==1}">checked="checked"</c:if>>거래완료
			</li>
			<br>
			<li>
				<label for="passwd">비밀번호 확인</label>
				<form:password path="passwd"/>
				<form:errors path="passwd" cssClass="error-color"/>
			</li>
			<br>
			<li>
				<label for="upload">파일</label>
				<input type="file" id="upload" name="upload" accept="image/gif,image/png,image/jpeg">
				<c:if test="${!empty marketVO.filename}">
				<br>
				<span id="file_detail">(${marketVO.filename})파일이 등록되어 있습니다.
				다시 업로드 하면 기존 파일은 삭제가 됩니다.
				<input type="button" value="파일 삭제" id="file_del">
				</span>
				<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-3.6.0.min.js"></script>    
				<script type="text/javascript">
					$(function() {
						$('#file_del').click(function() {
							var choice = confirm('삭제하시겠습니까?');
							if(choice){
								$.ajax({
									data : {market_num:${marketVO.market_num}},
									type : 'post',
									url : 'deleteFile.do',
									dataType : 'json',
									cache : false,
									timeout : 30000,
									success : function(param) {
										if(param.result == 'logout'){
											alert('로그인 후 사용하세요!');
										}else if(param.result == 'success'){
											$('#file_detail').hide();
										}else{
											alert('파일 삭제 오류 발생');
										}
									},
									error : function() {
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
		<p>
		<div class="align-center">
			<input type="submit" value="등록" class="btn btn-dark" style="width:120px; margin-top:20px;">
			<input type="button" value="목록" onclick="location.href='${pageContext.request.contextPath}/market/marketList.do'" class="btn btn-dark" style="width:120px; margin-top:20px;">
		</div>
	</form:form>
</div>
<!-- 거래게시판 수정 끝 -->