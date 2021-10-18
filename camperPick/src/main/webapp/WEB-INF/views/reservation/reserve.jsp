<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="page-main align-center" >
<h2>예약이 완료되었습니다.</h2>
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
			<input type="button" value="결제" onclick="location.href='pay.do?res_num=${reservationVO.res_num}&mem_num=${reservationVO.mem_num }'">
			<input type="button" value="홈으로" onclick="location.href='${pageContext.request.contextPath}/main/main.do'">
		</div>
	</div>
	
</div>