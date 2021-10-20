<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css"> 
<!-- 마이페이지 시작 -->
<div id="mypageForm" class="page-main">
	<h4>마이페이지</h4>
	<div class="mypageSection">
		<div class="mypageBox">
			<!-- 연락처 -->
			<div id="mypageform" class="card">
				<div class="card-body">
					<div class="mp_header">
						<h5><b>연락처</b></h5>
					</div>
					<div class="mp_content">
						<dl>
							<dt>이메일</dt>
							<dd>${member.email}</dd>
							<dt>이름</dt>
							<dd>${member.name}</dd>
							<dt>전화번호</dt>
							<dd>${member.phone}</dd>
							<dt>가입일</dt>
							<dd>${member.reg_date}</dd>
							<dt>정보수정일</dt>
							<dd>${member.modify_date}</dd>
						</dl>
					</div>
					<div class="mp_btn">
						<c:if test="${!empty user_num && user_auth == 2}">
							<input type="button" class="btn btn-dark" value="수정" onclick="location.href='update.do'">
						</c:if>
					</div>
				</div>
			</div>

			<!-- 비밀번호 -->
			<div id="mypageform" class="card">
				<div class="card-body">
					<div class="mp_header">
						<h5><b>비밀번호</b></h5>
					</div>
					<div class="mp_content">
						<p>주기적인 비밀번호 변경을 통해<br>개인정보를 안전하게 보호하세요.</p>
					</div>
					<div class="mp_btn">
						<c:if test="${!empty user_num && user_auth == 2}">
						<input type="button" class="btn btn-dark" value="수정" onclick="location.href='changePassword.do'">
						</c:if>
					</div>
				</div>
			</div>

			<!-- 회원탈퇴 -->
			<div id="mypageform" class="card">
				<div class="card-body">
					<div class="mp_header">
						<h5><b>회원탈퇴</b></h5>
					</div>
					<div class="mp_content">
						<p>사용하고 계신 아이디는 탈퇴할 경우<br> 재사용 및 복구가 불가능합니다.</p>
					</div>
					<div class="mp_btn">
						<input type="button" class="btn btn-dark" value="탈퇴" onclick="location.href='delete.do'">
					</div>
				</div>
			</div>
		</div>
		
		<div class="mypageBox">
			<!-- 주소 -->
			<div id="mypageform" class="card">
				<div class="card-body">
					<div class="mp_header">
						<h5><b>주소</b></h5>
					</div>
					<div class="mp_content">
						<p>${member.address1}<br>${member.address2}</p>
					</div>
					<div class="mp_btn">
						<input type="button" class="btn btn-dark" value="수정" onclick="location.href='updateAddress.do'">
					</div>
				</div>
			</div>
			
			<!-- 예약정보 -->
			<div id="mypageform" class="card">
				<div class="card-body">
					<div class="mp_header">
						<h5><b>예약정보</b></h5>
					</div>
					<div class="mp_content">
						<p>예약된 정보를 확인할 수 있습니다.</p>
					</div>
					<div class="mp_btn">
						<input type="button" class="btn btn-dark" value="확인" onclick="location.href='${pageContext.request.contextPath}/reservation/getReservationList.do?email=${user_email}'">
					</div>
				</div>
			</div>
		</div>

	</div>
</div>