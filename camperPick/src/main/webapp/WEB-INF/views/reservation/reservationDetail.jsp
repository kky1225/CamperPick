<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css">  
<script type="text/javascript" src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

<script type="text/javascript" src="https://cdn.iamport.kr/js/iamport.payment-1.1.8.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	IMP.init('imp33526773');
		$("#payment").click(function(e){
			//결제요청
			IMP.request_pay({
				pg : 'inicis',
				pay_method: 'card',
				merchant_uid : 'merchant_' + new Date().getTime(),
				name : '${reservation.room_name}', // 상품명
				amount : ${reservation.res_price}, // 가격
				buyer_email : '${reservation.res_email}', // 이메일
				buyer_name : '${reservation.res_name}', //구매자 이름
				buyer_tel : '${reservation.res_phone}',  //전화번호
			}, function(rsp){
				if(rsp.success){//결제 성공시
					var msg = '결제가 완료되었습니다';
					var result = {
					"imp_uid" : rsp.imp_uid,
					"merchant_uid" : rsp.merchant_uid,
					"biz_email" : '${reservation.res_email}',
					"pay_date" : new Date().getTime(),
					"amount" : rsp.paid_amount,
					"card_no" : rsp.apply_num,
					"refund" : 'payed',
					"res_num" : ${reservation.res_num},
					"mem_num" : ${reservation.mem_num},
					"camp_name" : '${reservation.camp_name}'
					}
					$.ajax({
						url : 'payment.do', 
				        type :'post',
				        data : JSON.stringify(result,
				        		['imp_uid', 'merchant_uid', 'biz_email', 
				        			'pay_date', 'amount', 'res_num', 'mem_num', 'camp_name']),
				        contentType:'application/json;charset=utf-8',
				        dataType: 'json', //서버에서 보내줄 데이터 타입
				        success: function(param){
				        	if(param.result == 'success'){
				        		alert("결제 성공");
				        	}else if(param.result == 'fail'){
				        		alert("결제 실패");
				        	}else{
				        		alert('결제 오류');
				        	}
				        	window.location.href = "http://localhost:8081/camperPick/reservation/detailReservation.do?res_num=${reservation.res_num}";
				        },
				        error:function(){
				          console.log("ajax 통신 실패!!!");
				        }
					}) //ajax
					
				}
				else{//결제 실패시
					var msg = '결제에 실패했습니다';
					msg += '에러 : ' + rsp.error_msg
				}
				console.log(msg);
			});//pay
		}); //payment 클릭 이벤트
		
		var delete_btn = document.getElementById('delete_btn');
		delete_btn.onclick=function(){
			var choice=confirm('취소하시겠습니까?');
			if(choice){
				cancel_pay();
			}
		};
		
		var cancel_pay = function(){
			$.ajax({
					url:'cancelPay.do',
					type:'post',
					//dataType:'json',
					contentType:'application/x-www-form-urlencoded;charset=utf-8',
					data : {
						"biz_email" : '${reservation.res_email}',
						"res_num" : ${reservation.res_num}
						//"merchant_uid" : 'merchant_1634110794018', // 주문번호
						//"cancel_request_amount" : 100, //환불금액
						//"reason" : '테스트 결제 환불', //환불사유
						//"refund_holder" : '', //[가상계좌 환불시 필수입력] 환불 가상계좌 예금주
						//"refund_bank" : '', //[가상계좌 환불시 필수입력] 환불 가상계좌 은행코드(ex Kg이니시스의 경우 신한은행 88)
						//"refund_account" : '' // [가상계좌 환불시 필수입력] 환불 가상계좌 번호
					},
			}).done(function(result){ //환불 성공
				if(result == "0"){
					
				}else{
					alert("환불 금액 : " + result);
				}
				window.location.href = "http://localhost:8081/camperPick/reservation/deleteReservation.do?res_num=${reservation.res_num}&camp_name=${reservation.camp_name}";
			}).fail(function(error){
				alert("환불 실패 : " + error);
			});//ajax
	}
}); //doc.ready
</script>
<!-- 중앙 내용 시작 -->
<div class="page-main middle-content">
	<h4 class="align-center" style="margin-bottom:50px;"><b>${reservation.res_name }님의 예약</b></h4>
	
	<h5><b>예약 정보</b></h5>
	<br>
	<ul class="content-center">
		<li class="form-group">
			<fieldset disabled>
			    <label class="form-label" for="disabledInput">캠핑장</label>
			    <input class="form-control" id="disabledInput" type="text" placeholder="${reservation.camp_name}" disabled>
			</fieldset>
		</li>
		<li class="form-group">
			<fieldset disabled>
			    <label class="form-label" for="disabledInput">객실</label>
			    <input class="form-control" id="disabledInput" type="text" placeholder="${reservation.room_name}" disabled>
			</fieldset>		  
		</li>
		<li class="form-group">
			<fieldset disabled>
			    <label class="form-label" for="disabledInput">예약번호</label>
			    <input class="form-control" id="disabledInput" type="text" placeholder="${reservation.res_num}" disabled>
			</fieldset>	
		</li>
		<li class="form-group">
			<fieldset disabled>
				<label class="form-label" for="disabledInput">예약자</label>
				<input class="form-control" id="disabledInput" type="text" placeholder="${reservation.res_name}" disabled>
			</fieldset>
		</li>	
		<li class="form-group">
			<fieldset disabled>
				<label class="form-label" for="disabledInput">전화번호</label>
				<input class="form-control" id="disabledInput" type="text" placeholder="${reservation.res_phone}" disabled>
			</fieldset>
		</li>
		<li class="form-group">
			<fieldset disabled>
				<label class="form-label" for="disabledInput">이메일</label>
				<input class="form-control" id="disabledInput" type="text" placeholder="${reservation.res_email}" disabled>
			</fieldset>
		</li>
		<li class="form-group">
			<fieldset disabled>
				<label class="form-label" for="disabledInput">인원</label>
				<input class="form-control" id="disabledInput" type="text" placeholder="${reservation.headcount}" disabled>
			</fieldset>
		</li>
		<li class="form-group">
			<fieldset disabled>
				<label class="form-label" for="disabledInput">날짜</label>
				<input class="form-control" id="disabledInput" type="text" placeholder="${reservation.res_start}" disabled>
			</fieldset> 
		</li>
		<li class="form-group">
			<fieldset disabled>
				<label class="form-label" for="disabledInput">가격</label>
				<input class="form-control" id="disabledInput" type="text" placeholder="${reservation.res_price}" disabled>
			</fieldset>
		</li>
		<li class="form-group" id="r_state">
			<fieldset disabled>
				<label class="form-label" for="disabledInput">예약상태</label>
				<input class="form-control" id="disabledInput" type="text" placeholder="${reservation.res_state}" disabled>
			</fieldset>
		</li>
	</ul>
	
	<div class="align-right">
	<c:if test="${!empty user_num && user_num == reservation.mem_num}">
		<input type="button" value="예약 수정" class="button" style="margin-top:10px;" onclick="location.href='updateReservation.do?res_num=${reservation.res_num}'">
		<input type="button" value="예약 취소" class="button" style="margin-top:10px;" id="delete_btn">
		<c:if test="${reservation.res_state=='결제대기' }">
			<input type="button" class="button" style="margin-top:10px;" value="결제" id="payment">
		</c:if>	
	</c:if>
	</div>
</div>
<!-- 중앙 내용 끝 -->