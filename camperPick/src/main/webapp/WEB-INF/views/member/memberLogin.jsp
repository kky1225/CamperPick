<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-3.6.0.min.js"></script>  
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
			        로그인
			    </h4>
			</div>
		</div>
	</div>
	
	<form:form id="login_form" action="login.do" modelAttribute="memberVO">
		<ul>
			<li>
				<label for="email" class="form-label mt-4" style="margin-left:35px;">이메일</label>
				<form:input path="email" class="form-control" placeholder="Enter email" style="width:300px; margin-left:35px;"/>
			</li>
			<li>
				<label for="passwd" class="form-label mt-4" style="margin-left:35px;">비밀번호</label>
				<form:password path="passwd" class="form-control" placeholder="Password" style="width:300px; margin-left:35px;"/>
			</li>
			<li>
				<form:errors element="div" cssClass="error-color" style="margin-top:20px;"/>
			</li>
		</ul>
		<div class="align-center">
			<form:button class="btn btn-dark" style="width:300px; margin-top:10px; margin-right:15px;">로그인</form:button>
			<input type="button" value="회원가입" class="btn btn-dark" onclick="location.href='${pageContext.request.contextPath}/member/registerUser.do'" style="width:300px; margin-top:10px; margin-right:15px;">
		</div>
	</form:form>
	<hr size="1" width="30%">
	<div id="naver_id_login" style="text-align:center">
		<a href="${url}"><img src="${pageContext.request.contextPath }/resources/images/naver.png" border="0"></a>
	</div>
</div>
<!-- 중앙 내용 끝 -->