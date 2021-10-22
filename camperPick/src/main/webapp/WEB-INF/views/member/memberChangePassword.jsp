<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %> 
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css">  
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
	$(function(){
		$('#passwd').keyup(function(){
			if($('#passwdCheck').val() != '' && $('#passwdCheck').val() != $(this).val()){
				$('#message_id').text('비밀번호 불일치').css('color','red');
			}else if($('#passwdCheck').val() != '' && $('#passwdCheck').val() == $(this).val()){
				$('#message_id').text('비밀번호 일치').css('color','#000');				
			}
		});
		
		$('#passwdCheck').keyup(function(){
			if($('#passwd').val() != '' && $('#passwd').val() != $(this).val()){
				$('#message_id').text('비밀번호 불일치').css('color','red');
			}else if($('#passwd').val() != '' && $('#passwd').val() == $(this).val()){
				$('#message_id').text('비밀번호 일치').css('color','#000');				
			}
		});
		
		$('#change_form').submit(function(){
			if($('#now_passwd').val().trim() == ''){
				alert('기존 비밀번호를 입력하세요.');
				$('#now_passwd').val().focus();
				return false;
			}
			if($('#passwd').val().trim() == ''){
				alert('새 비밀번호를 입력하세요.');
				$('#passwd').val().focus();
				return false;
			}
			if($('#passwdCheck').val().trim() == ''){
				alert('새 비밀번호 확인을 입력하세요.');
				$('#passwdCheck').val().focus();
				return false;
			}
			if($('#passwd').val() != $('#passwdCheck').val()){
				$('#message_id').text('비밀번호 불일치').css('color','red');
				return false;
			}
		});
	});
</script>
<!-- 중앙 내용 시작 -->
<div class="page-main">
	<h2>비밀번호 변경</h2>
	<form:form id="change_form" action="changePassword.do" modelAttribute="memberVO">
		<ul>
			<li>
				<label for="now_passwd" style="margin-top:10px;">기존 비밀번호</label>
				<form:password path="now_passwd" class="form-control form-label mt-4"/>
				<form:errors path="now_passwd" cssClass="error-color"/>
			</li>
			<li>
				<label for="passwd" style="margin-top:10px;">새 비밀번호</label>
				<form:password path="passwd" class="form-control form-label mt-4" placeholder="4~12자 영문, 숫자 허용"/>
				<form:errors path="passwd" cssClass="error-color"/>
			</li>
			<li>
				<label for="passwdCheck" style="margin-top:10px;">새 비밀번호 확인</label>
				<input type="password" id="passwdCheck" class="form-control form-label mt-4" placeholder="4~12자 영문, 숫자 허용"/>
				<span id="message_id" class="error-color"></span>
			</li>
		</ul>
		<div class="align-center">
			<form:button class="button-large">수정</form:button>
			<input type="button" class="button-large" value="취소" onclick="location.href='${pageContext.request.contextPath}/member/myPage.do'">
			<input type="button" class="button-large" value="홈으로" onclick="location.href='${pageContext.request.contextPath}/main/main.do'">
		</div>
	</form:form>
</div>
<!-- 중앙 내용 끝 -->