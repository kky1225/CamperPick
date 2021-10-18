<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- 중앙 내용 시작 -->
<div class="page-main">
	<h2>${room.room_name }</h2>
	<ul>
		<li>객실번호 : ${room.room_num }</li>
		<li>정원 : ${room.people }</li>	
		<li>객실면적 : ${room.area }</li>
		<li>가격 : ${room.price }</li>
		<li>체크인 : ${room.checkIn }</li>
		<li>체크아웃 : ${room.checkOut }</li>
	</ul>
	<hr size="1" width="100%" >
	<c:if test="${!empty room.filename}">
	<div class="align-center">
		<img src="imageView.do?room_num=${room.room_num }" style="max-width:500px">
	</div>
	</c:if>
	<p>${room.info }</p>
	<hr size="1" width="100%">
	<div class="align-right">
		<c:if test="${!empty user_num && user_auth==3}">
			<input type="button" value="수정" onclick="location.href='updateRoom.do?room_num=${room.room_num}'">
			<input type="button" value="삭제" id="delete_btn">
			<script type="text/javascript">
				var delete_btn = document.getElementById('delete_btn');
				delete_btn.onclick=function(){
					var choice=confirm('삭제하시겠습니까?');
					if(choice){
						location.replace('deleteRoom.do?room_num=${room.room_num}');
					}
				};
			</script>
		</c:if>
		<input type="button" value="예약하기" onclick="location.href='${pageContext.request.contextPath }/reservation/reserve.do?camping_num=${room.camping_num }&room_num=${room.room_num }&mem_num=${user_num }'">
		<input type="button" value="목록" onclick="location.href='${pageContext.request.contextPath }/camping/detail.do?camping_num=${room.camping_num }'">
	</div>
</div>
<!-- 중앙 내용 끝 -->