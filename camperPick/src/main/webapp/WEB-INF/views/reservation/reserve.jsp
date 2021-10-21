<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
				name : '${reservationVO.room_name}', // 상품명
				amount : ${reservationVO.res_price}, // 가격
				buyer_email : '${reservationVO.res_email}', // 이메일
				buyer_name : '${reservationVO.res_name}', //구매자 이름
				buyer_tel : '${reservationVO.res_phone}',  //전화번호
			}, function(rsp){
				if(rsp.success){//결제 성공시
					var msg = '결제가 완료되었습니다';
					var result = {
					"imp_uid" : rsp.imp_uid,
					"merchant_uid" : rsp.merchant_uid,
					"biz_email" : '${reservationVO.res_email}',
					"pay_date" : new Date().getTime(),
					"amount" : rsp.paid_amount,
					"card_no" : rsp.apply_num,
					"refund" : 'payed',
					"res_num" : ${reservationVO.res_num},
					"mem_num" : ${reservationVO.mem_num},
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
				        	window.location.href = "http://localhost:8081/camperPick/reservation/detailReservation.do?res_num=${reservationVO.res_num}";
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

}); //doc.ready
</script>
<div class="page-main align-center" >
<h2 class="align-center">예약이 완료되었습니다.</h2>
	<div >
	<ul>
		<li>예약자명 : ${reservationVO.res_name }</li>
		<li>휴대번호 : ${reservationVO.res_phone }</li>
		<li>예약이메일 : ${reservationVO.res_email }</li>
		<li>인원 : ${reservationVO.headcount }명</li>
		<li>날짜 : ${reservationVO.res_start } ~ ${reservationVO.res_end }</li>
		<li>가격 : ${reservationVO.res_price}원</li>
	</ul>
		<div >
			<input type="button" class="button" style="margin-top:30px;" value="결제" id="payment">
			<input type="button" class="button" style="margin-top:30px;" value="홈으로" onclick="location.href='${pageContext.request.contextPath}/main/main.do'">
		</div>
	</div>
	
</div>