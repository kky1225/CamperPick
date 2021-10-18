<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- 중앙 내용 시작 -->
<div class="page-main">
	<h2>${reservation.res_name }님의 예약</h2>
	<ul>
		<li><b>캠핑지 정보</b></li>
		<li>캠핑장명 : ${reservation.camp_name }</li>
		<li>객실 : ${reservation.room_name }</li>
	</ul>
	<ul>
		<li><b>예약 정보</b></li>
		<li>예약번호 : ${reservation.res_num }</li>
		<li>예약자명 : ${reservation.res_name }</li>	
		<li>전화번호 : ${reservation.res_phone }</li>
		<li>이메일 : ${reservation.res_email }</li>
		<li>인원 : ${reservation.headcount }명</li>
		<li>날짜 : ${reservation.res_start } ~ ${reservation.res_end }</li>
		<li>가격 : ${reservation.res_price}원</li>
		<li>예약상태 : ${reservation.res_state }</li>
	</ul>
	<hr size="1" width="100%" >
	<div class="align-right">
	<c:if test="${!empty user_num && user_num == reservation.mem_num}">
		
			<input type="button" value="예약 수정" onclick="location.href='updateReservation.do?res_num=${reservation.res_num}'">
			<input type="button" value="예약 취소" id="delete_btn">
			<script type="text/javascript">
				var delete_btn = document.getElementById('delete_btn');
				delete_btn.onclick=function(){
					var choice=confirm('취소하시겠습니까?');
					if(choice){
						location.replace('deleteReservation.do?res_num=${reservation.res_num}');
					}
				};
			</script>
			<c:if test="${reservation.res_state=='결제대기' }">
				<input type="button" value="결제" onclick="location.href='#'">
			</c:if>
			
		
	</c:if>
		<input type="button" value="홈으로" onclick="location.href='${pageContext.request.contextPath}/main/main.do'">
	</div>
</div>
<!-- 중앙 내용 끝 -->