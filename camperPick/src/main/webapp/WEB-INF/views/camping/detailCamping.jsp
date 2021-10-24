<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css">  
<script type="text/javascript" src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<script type="text/javascript">
//타이틀 배경이미지
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
<div class="page-main" id="container">

	<div id="main-content">
		<div class="main_bg">
			<div class="search_container">
	    		<h4 class="search_title" style="font-size: 23px;">
			        캠핑장
			    </h4>
			</div>
		</div>
	</div>
		
	<h4 class="align-center" style="margin: 20px 0 30px 0;"><b>${camping.camp_name }</b></h4>
	
	<div class="camping-top">
		<table summary="">
			<tr>
				<th>번호</th>
				<td>${camping.camping_num}</td>
			</tr>
			<tr>
				<th>주소</th>
				<td>${camping.camp_address}</td>
			</tr>
			<tr>
				<th>전화번호</th>
				<td>${camping.camp_phone}</td>
			</tr>
			<tr>
				<th>객실수</th>
				<td>${camping.rcount}</td>
			</tr>
		</table>
	</div>
	
	<hr size="1" width="100%" >
	
	<c:if test="${!empty camping.filename}">
	<div class="align-center">
		<img src="imageView.do?camping_num=${camping.camping_num }" style="max-width:500px">
	</div>
	</c:if>
	<div class="align-right" style="margin-top:20px;">
	<c:if test="${!empty user_num && user_auth==4}">
		
			<input type="button" value="수정" class="button-large" onclick="location.href='update.do?camping_num=${camping.camping_num}'">
			<input type="button" value="삭제" class="button-large" id="delete_btn">
			<script type="text/javascript">
				var delete_btn = document.getElementById('delete_btn');
				delete_btn.onclick=function(){
					var choice=confirm('삭제하시겠습니까?');
					if(choice){
						location.replace('delete.do?camping_num=${camping.camping_num}');
					}
				};
			</script>
		
	</c:if>
	
		<input type="button" value="목록" class="button-large" onclick="location.href='list.do'">
	</div>
	<c:if test="${!empty user_num && user_auth==4}">
	<div class="align-right" style="margin-top:30px;">
		<input type="button" value="객실 추가" class="button" onclick="location.href='${pageContext.request.contextPath}/room/writeRoom.do?camping_num=${camping.camping_num}'">
	</div>
	</c:if>
	
	<div>
		<c:if test="${roomCount==0 }">
			<div class="result-display">
				등록된 객실이 없습니다.
			</div>
		</c:if>
		<c:if test="${roomCount>0 }">
			<table class="table table-hover" style="width:100%; text-align:center;">
				<tr class="thead-dark">
					<th>사진</th>
					<th>객실이름</th>
					<th>정원</th>
					<th>면적</th>
					<th>가격</th>
				</tr>
				<c:forEach var="room" items="${roomList }">
					<tr onClick="location.href='${pageContext.request.contextPath }/room/detailRoom.do?room_num=${room.room_num }'" style="border-bottom:1px solid #000;">
						<td>
							<c:if test="${!empty room.filename }">
								<img src="${pageContext.request.contextPath }/room/imageView.do?room_num=${room.room_num}" border="0" width="120" height="120">
							</c:if>
							<c:if test="${empty room.filename }">
								<img src="${pageContext.request.contextPath }/resources/images/noImage.gif" border="0" width="120" height="120" >
							</c:if>
						</td>
						<td style="vertical-align:middle;">${room.room_name }</td>
						<td style="vertical-align:middle;">${room.people }</td>
						<td style="vertical-align:middle;">${room.area }</td>
						<td style="vertical-align:middle;">${room.price }</td>
					</tr>
				</c:forEach>
			</table>
			<div class="align-center" style="margin-top:30px;">${pagingHtml }</div>
		</c:if>
				
	</div>
	<div>	
		<!-- 리뷰 -->
		<%@include file="../review/reviewList.jsp" %>
	</div>
</div>
<!-- 중앙 내용 끝 -->