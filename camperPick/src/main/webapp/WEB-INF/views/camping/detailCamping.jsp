<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- 중앙 내용 시작 -->
<div class="page-main">
	<h2 class="align-center">${camping.camp_name }</h2>
	<ul>
		<li>번호 : ${camping.camping_num }</li>
		<li>주소 : ${camping.camp_address }</li>	
		<li>전화번호 : ${camping.camp_phone }</li>
		<li>객실수 : ${camping.rcount }</li>
	</ul>
	<hr size="1" width="100%" >
	<div class="align-right">
	<c:if test="${!empty user_num && user_auth==3}">
		
			<input type="button" value="수정" onclick="location.href='update.do?camping_num=${camping.camping_num}'">
			<input type="button" value="삭제" id="delete_btn">
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
	
		<input type="button" value="목록" onclick="location.href='list.do'">
	</div>
	<c:if test="${!empty user_num && user_auth==3}">
	<div class="align-right">
		<input type="button" value="객실 추가" onclick="location.href='${pageContext.request.contextPath}/room/writeRoom.do?camping_num=${camping.camping_num}'">
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
			<table>
				<tr>
					<th>객실번호</th>
					<th>객실이름</th>
					<th>정원</th>
					<th>면적</th>
					<th>가격</th>
					
				</tr>
				<c:forEach var="room" items="${roomList }">
					<tr>
						<td>${room.room_num }</td>
						<td><a href="${pageContext.request.contextPath }/room/detailRoom.do?room_num=${room.room_num }">${room.room_name }</a></td>
						<td>${room.people }</td>
						<td>${room.area }</td>
						<td>${room.price }</td>
					</tr>
				</c:forEach>
			</table>
			<div class="align-center">${pagingHtml }</div>
		</c:if>
				
	</div>
</div>
<!-- 중앙 내용 끝 -->