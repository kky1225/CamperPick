<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
				alert("환불 성공 : " + result);
				window.location.href = "http://localhost:8081/camperPick/reservation/deleteReservation.do?res_num=${reservation.res_num}";
			}).fail(function(error){
				alert("환불 실패 : " + error);
			});//ajax
	}
}); //doc.ready
</script>
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
			<c:if test="${reservation.res_state=='결제대기' }">
				<input type="button" value="결제" id="payment">
			</c:if>
			
		
	</c:if>
		<input type="button" value="홈으로" onclick="location.href='${pageContext.request.contextPath}/main/main.do'">
	</div>
</div>
<!-- 중앙 내용 끝 -->