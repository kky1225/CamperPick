<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>  
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css">  
<script type="text/javascript" src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
   
<script type="text/javascript" src="${pageContext.request.contextPath }/resources/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	const start = $('#res_start').val();
	const end = $('#res_end').val();
	
	$('#res_start').change(function(){
		$('#res_end').val('');
	});
	
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
					$('#res_start').val(start);
					$('#res_end').val(end);
				}else if(param.result=='EndAlreadyReserved'){
					alert('퇴실일은 이미 예약된 날짜입니다.');
					$('#res_start').val(start);
					$('#res_end').val(end);
				}else if(param.result=='BetweenAlreadyReserved'){
					alert('이미 예약된 기간입니다.');
					$('#res_start').val(start);
					$('#res_end').val(end);
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
	<h2 class="align-center" style="margin-bottom:30px;"><B>예약수정</B></h2>
	<form:form id="updateReservation_form" action="updateReservation.do" modelAttribute="reservationVO">
		<form:hidden path="camping_num" />
		<form:hidden path="room_num"/>
		<form:hidden path="mem_num"/>
		<form:hidden path="res_num"/>
		<form:errors element="div" cssClass="error-color"/>
		<ul>
			<li>
				<label for="res_name">예약자명</label>
				<form:input path="res_name" class="form-control" style="margin-bottom:10px;"/>
				<form:errors path="res_name" cssClass="error-color"/>
			</li>
			<li>
				<label for="res_phone">전화번호</label>
				<form:input path="res_phone" class="form-control" style="margin-bottom:10px;"/>
				<form:errors path="res_phone" cssClass="error-color"/>
			</li>
			<li>
				<label for="res_email">예약 이메일</label>
				<form:input path="res_email" class="form-control" style="margin-bottom:10px;"/>
				<form:errors path="res_email" cssClass="error-color"/>
			</li>
			<li>
				<label for="headcount">인원</label>
				<input type="number" id="headcount" name="headcount" class="form-control" style="width:200px;margin-bottom:10px;" value="${reservationVO.headcount }">
				<form:errors path="headcount" cssClass="error-color"/>
			</li>
			<li>
				<label for="res_start">입실 날짜</label>
				<input type="date" id="res_start" name="res_start" class="form-control" style="width:200px;margin-bottom:10px;" value="${reservationVO.res_start }">
				<form:errors path="res_start" cssClass="error-color"/>
			</li>
			<li>
				<label for="res_end">퇴실 날짜</label>
				<input type="date" id="res_end" name="res_end" class="form-control" style="width:200px;margin-bottom:10px;" value="${reservationVO.res_end }">
				<form:errors path="res_end" cssClass="error-color"/>
			</li>
		</ul>
		<div class="align-center">
			<form:button class="button" style="margin-top:30px;">수정</form:button> 
			<input type="button" class="button" style="margin-top:30px;" value="돌아가기" onclick="location.href='${pageContext.request.contextPath}/reservation/detailReservation.do?res_num=${reservationVO.res_num}&&phone=${phone}'">
		</div>
	</form:form>
</div>
<!-- 중앙 내용 끝 -->