<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css">  
<script type="text/javascript" src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<!-- 중앙 내용 시작 -->
<div class="page-main">
	<h2 class="align-center" style="margin-bottom:30px;"><b>${camping.camp_name }</b></h2>
	<ul>
		<li>번호 : ${camping.camping_num }</li>
		<li>주소 : ${camping.camp_address }</li>	
		<li>전화번호 : ${camping.camp_phone }</li>
		<li>객실수 : ${camping.rcount }</li>
	</ul>
	<hr size="1" width="100%" >
	<c:if test="${!empty camping.filename}">
	<div class="align-center">
		<img src="imageView.do?camping_num=${camping.camping_num }" style="max-width:500px">
	</div>
	</c:if>
	<div class="align-right" style="margin-top:20px;">
	<c:if test="${!empty user_num && user_auth==4}">
		
			<input type="button" value="수정" class="button" onclick="location.href='update.do?camping_num=${camping.camping_num}'">
			<input type="button" value="삭제" id="delete_btn" class="button">
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
	
		<input type="button" value="목록" class="button" onclick="location.href='list.do'">
	</div>
	<c:if test="${!empty user_num && user_auth==4}">
	<div class="align-right" style="margin-top:30px;">
		<input type="button" value="객실 추가" class="button" onclick="location.href='${pageContext.request.contextPath}/room/writeRoom.do?camping_num=${camping.camping_num}'">
	</div>
	</c:if>
	<br>
	<div>
		<c:if test="${roomCount==0 }">
			<div class="result-display">
				등록된 객실이 없습니다.
			</div>
		</c:if>
		<c:if test="${roomCount>0 }">
			<table style="text-align:center;">
				<tr>
					<th></th>
					<th>객실번호</th>
					<th>객실이름</th>
					<th>정원</th>
					<th>면적</th>
					<th>가격</th>
					
				</tr>
				<c:forEach var="room" items="${roomList }">
					<tr>
						<td>
							<c:if test="${!empty room.filename }">
								<img src="${pageContext.request.contextPath }/room/imageView.do?room_num=${room.room_num}" border="0" width="90" height="90">
							</c:if>
							<c:if test="${empty room.filename }">
								<img src="${pageContext.request.contextPath }/resources/images/noImage.gif" border="0" width="90" height="90" >
							</c:if>
						</td>
						<td>${room.room_num }</td>
						<td><a href="${pageContext.request.contextPath }/room/detailRoom.do?room_num=${room.room_num }">${room.room_name }</a></td>
						<td>${room.people }</td>
						<td>${room.area }</td>
						<td>${room.price }</td>
					</tr>
				</c:forEach>
			</table>
			<div class="align-center" style="margin-top:30px;">${pagingHtml }</div>
		</c:if>
				
	</div>
		<%@include file="../review/reviewList.jsp" %>
</div>
<!-- 중앙 내용 끝 -->