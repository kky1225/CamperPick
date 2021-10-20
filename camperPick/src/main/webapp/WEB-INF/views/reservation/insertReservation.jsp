<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>    
<script type="text/javascript" src="${pageContext.request.contextPath }/resources/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	
	$('#res_end').change(function(){
		if($('#res_start').val()==''){
			alert('입실일을 먼저 선택해 주세요.');
			$('#res_end').val('');
			return;
		}
		$.ajax({
			url:'checkDate.do',
			type:'post',
			data:{room_num:$('#room_num').val(),res_start:$('#res_start').val(),res_end:$('#res_end').val()},
			dataType:'json',
			cache:false,
			timeout:30000,
			success:function(param){
				if(param.result=='StartAlreadyReserved'){
					alert('입실일은 이미 예약된 날짜입니다.');
					$('#res_start').val('');
					$('#res_end').val('');
				}else if(param.result=='EndAlreadyReserved'){
					alert('퇴실일은 이미 예약된 날짜입니다.');
					$('#res_end').val('');
				}else if(param.result=='BetweenAlreadyReserved'){
					alert('이미 예약된 기간입니다.');
					$('#res_start').val('');
					$('#res_end').val('');
				}
			},
			error:function(){
				alert('네트워크 오류 발생');
			
			}
		});		//end if ajax
	});
	
});
</script>
<!-- 중앙 내용 시작 -->
<div class="page-main">
	<h2 class="align-center">예약하기</h2>
	<form:form id="reservation_form" action="reserve.do" modelAttribute="reservationVO">
		<form:hidden path="camping_num" value="${camping_num }"/>
		<form:hidden path="room_num" value="${room_num}"/>
		<form:errors element="div" cssClass="error-color"/>
		<ul>
			
			<li>
				<label for="res_name">예약자명</label>
				<form:input path="res_name" />
				<form:errors path="res_name" cssClass="error-color"/>
			</li>
			<li>
				<label for="res_phone">전화번호</label>
				<form:input path="res_phone"/>
				<form:errors path="res_phone" cssClass="error-color"/>
			</li>
			<li>
				<label for="res_email">이메일</label>
				<form:input path="res_email" />
				<form:errors path="res_email" cssClass="error-color"/>
			</li>
			<li>
				<label for="headcount">인원</label>
				<input type="number" id="headcount" name="headcount" value="${reservationVO.headcount }">
				<form:errors path="headcount" cssClass="error-color"/>
			</li>
			<li>
				<label for="res_start">입실 날짜</label>
				<input type="date" id="res_start" name="res_start" value="${reservationVO.res_start }">
				<form:errors path="res_start" cssClass="error-color"/>
			</li>
			<li>
				<label for="res_end">퇴실 날짜</label>
				<input type="date" id="res_end" name="res_end" value="${reservationVO.res_end }">
				<form:errors path="res_end" cssClass="error-color"/>
			</li>
		</ul>
		<div class="align-center">
			<form:button>예약</form:button> 
			<input type="button" value="홈으로" onclick="location.href='${pageContext.request.contextPath}/main/main.do'">
		</div>
	</form:form>
</div>
<!-- 중앙 내용 끝 -->