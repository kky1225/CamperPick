<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css">
<script type="text/javascript" src="${pageContext.request.contextPath }/resources/js/jquery-3.6.0.min.js"></script>
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
<!-- 중앙 내용 시작 -->
<div class="page-main">
	
	<div id="main-content">
		<div class="main_bg">
			<div class="search_container">
	    		<h4 class="search_title">
			        연락처 수정
			    </h4>
			</div>
		</div>
	</div>

	<form:form id="modify_form" action="update.do" modelAttribute="memberVO">
		<ul>
			<li>
				<label for="name" style="margin-top:10px;">이름</label>
				<form:input path="name" class="form-control form-label mt-4"/>
				<form:errors path="name" cssClass="error-color"/>
			</li>
			<li>
				<label for="phone" style="margin-top:10px;">전화번호</label>
				<form:input path="phone" class="form-control form-label mt-4"/>
				<form:errors path="phone" cssClass="error-color"/>
				<span id="message_phone"></span>
			</li>
		</ul>
		<div class="align-center">
			<form:button class="button-large">수정</form:button>
			<input type="button" class="button-large" value="취소" onclick="location.href='${pageContext.request.contextPath}/member/myPage.do'">
		</div>
	</form:form>
</div>
<!-- 중앙 내용 끝 -->