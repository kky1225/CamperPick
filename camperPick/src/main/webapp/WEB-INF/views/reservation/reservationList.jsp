<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css">  
<script type="text/javascript" src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/resources/js/jquery-3.6.0.min.js"></script>
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
<!-- 중앙 내용 시작 -->
<div class="page-main">

	<div id="main-content">
		<div class="main_bg">
			<div class="search_container">
	    		<h4 class="search_title">
			        예약 목록
			    </h4>
			</div>
		</div>
	</div>
	
	<c:if test="${count==0 }">
		<div class="result-display">
			예약 내역이 없습니다.
		</div>
	</c:if>
	<c:if test="${count>0 }">
		<table class="table table-borderless" style="text-align:center;">
			<thead class="thead-dark">
			<tr>
				<th>예약 번호</th>
				<th>예약자명</th>
				<th>캠핑장명</th>
				<th>날짜</th>
				<th>예약상태</th>
			</tr>
			</thead>
			<c:forEach var="reservation" items="${list }">
				<tr>
					<td>${reservation.res_num }</td>
					<td><a href="detailReservation.do?res_num=${reservation.res_num }">${reservation.res_name}</a></td>
					<td>${reservation.camp_name }</td>
					<td>${reservation.res_start }~${reservation.res_end }</td>
					<td>${reservation.res_state }</td>
				</tr>
			</c:forEach>
		</table>
		<div class="align-center" style="margin-top:30px;">${pagingHtml }</div>
	</c:if>
</div>

<!-- 중앙 내용 끝 -->