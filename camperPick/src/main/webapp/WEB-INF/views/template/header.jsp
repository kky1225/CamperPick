<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-3.6.0.min.js"></script>
<c:if test="${!empty user_num}">
<script type="text/javascript">
	$(document).ready(function(){
		$.ajax({
			url:'../main/notificationCount.do',
			type:'post',
			dataType:'json',
			cache:false,
			timeout:30000,
			success:function(param){
				if(param.result != "0"){
					$('#notification_count').css('color','red').text(param.result);
				}
			},
			error:function(){
				alert("오류 발생");
			}
		}); //end if ajax
	});
</script>
</c:if>
<!-- 상단 시작 -->
<div class="logo">
	<a href="${pageContext.request.contextPath}/main/main.do" class="align-center"><img alt="로고" src="${pageContext.request.contextPath}/resources/images/logo_small.png"></a>
</div>

<div class="menu">
	<ul>
		<li><a href="${pageContext.request.contextPath}/camping/list.do">캠핑장</a></li>
		<li><a href="${pageContext.request.contextPath}/reservation/checkReservation.do">예약확인</a></li>
		<li><a href="${pageContext.request.contextPath}/notice/noticeList.do">공지사항</a></li>
		<li><a href="${pageContext.request.contextPath}/market/marketList.do">거래게시판</a></li>
	</ul>
</div>

<div class="align-right register">
	<c:if test="${!empty user_num}">
	    [<span>${user_email}</span>]
	    <c:if test="${user_auth != 4}">
	   		<a href="${pageContext.request.contextPath}/member/notification.do"><img src="${pageContext.request.contextPath}/resources/images/notification.png" width="22" height="22"></a>
	   		<a id="notification_count"></a>
	    </c:if>
		<a href="${pageContext.request.contextPath}/member/logout.do">로그아웃</a>
	</c:if>
	<c:if test="${empty user_num}">
		<a href="${pageContext.request.contextPath}/member/registerUser.do">회원가입</a>
		<a href="${pageContext.request.contextPath}/member/login.do">로그인</a>
	</c:if>
	<c:if test="${!empty user_num && (user_auth == 2 || user_auth == 3)}">
		<a href="${pageContext.request.contextPath}/member/myPage.do">MY페이지</a>
	</c:if>
	<c:if test="${!empty user_num && (user_auth == 4)}">
		<a href="${pageContext.request.contextPath}/member/managerPage.do">관리자페이지</a>
	</c:if>
</div>
<!-- 상단 끝 -->