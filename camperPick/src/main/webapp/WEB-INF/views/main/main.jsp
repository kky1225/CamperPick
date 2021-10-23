<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
	$(function(){
		var fname ="";
		var today =new Date();
		var hh = today.getHours();
		
		if(hh>=4 && hh<9){			//새벽 4시부터 낮 9시
			fname="${pageContext.request.contextPath}/resources/images/main_bg1.jpg";
		}else if(hh>=9 && hh<6){	//낮 9시부터 오후 6시
			fname="${pageContext.request.contextPath}/resources/images/main_bg2.jpg";
		}else if(hh>=6 && hh<24){	//오후 6시부터 밤 12시
			fname="${pageContext.request.contextPath}/resources/images/main_bg3.jpg";
		}else{						//밤 12시 부터 새벽 4시
			fname="${pageContext.request.contextPath}/resources/images/main_bg0.jpg";
		}
		
		$('.main_bg').css('background-image','url('+fname+')');
	});
</script>
<!-- 메인 시작 -->
<div id="main-content">
	<div class="main_bg">
		<div class="search_container">
    		<h1 class="search_title">
		        CAMPERPICK
		    </h1>
		    <div class="msearch_content">
		    	<form id="search_form" action="../camping/list.do">
		    		<input class="search_input" type="search" size="30" id="keyword" name="keyword" placeholder="지역 검색">
		    		<input class="search_btn" type="submit" value="검색하기">
		    	</form>
		    </div>
		</div>
	</div>
</div>
<!-- 메인 끝 -->