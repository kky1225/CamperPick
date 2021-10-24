<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-3.6.0.min.js"></script>      
<script type="text/javascript">
	$(function() {
		// 검색 유효성 체크
		$('#search_form').submit(function() {
			if($('#keyword').val().trim()==''){
				alert('검색어를 입력하세요!');
				$('#keyword').val('').focus();
				return false;
			}
		})
	})
	
	$(function(){
		var fname ="";
		var today =new Date();
		var hh = today.getHours();
		
		if(hh>=4 && hh<9){			//새벽 4시부터 낮 9시
			fname="${pageContext.request.contextPath}/resources/images/main_bg1.jpg";
		}else if(hh>=9 && hh<18){	//낮 9시부터 오후 6시
			fname="${pageContext.request.contextPath}/resources/images/main_bg2.jpg";
		}else if(hh>=18 && hh<24){	//오후 6시부터 밤 12시
			fname="${pageContext.request.contextPath}/resources/images/main_bg3.jpg";
		}else{						//밤 12시 부터 새벽 4시
			fname="${pageContext.request.contextPath}/resources/images/main_bg0.jpg";
		}
		
		$('.main_bg').css('background-image','url('+fname+')');
	});
</script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css">
<style type="text/css">

tr{
	border-bottom: 1px solid gray;
}
</style>
<!-- 거래게시판 메인 시작 -->
<div class="page-main">
	
	<div id="main-content">
		<div class="main_bg">
			<div class="search_container">
	    		<h4 class="search_title">
			        거래게시판
			    </h4>
			</div>
		</div>
	</div>
	
	<form action="marketList.do" id="search_form" method="get">
		<ul class="search">
			<li>
				<select name="keyfield" id="keyfield">
					<option value="1">제목</option>
					<option value="2">작성자</option>
					<option value="3">내용</option>
					<option value="4">제목+내용</option>
				</select>
			</li>
			<li>
				<input type="search" size="30" id="keyword" name="keyword">
			</li>
			<li>
				<input type="submit" value="검색" class="button" style="font-size:14px;">
				<input type="button" value="목록" class="button" style="font-size:14px;" onclick="location.href='marketList.do'">
			</li>
		</ul>
	</form>
	<div class="align-right" style="width: 90%">
		<c:if test="${user_auth == 2 || user_auth == 3}">
			<input type="button" value="글쓰기" class="button-large" onclick="location.href='marketWrite.do'">
		</c:if>
	</div>
	<c:if test="${count == 0}">
	<div class="result-display">출력할 내용이 없습니다</div>
	</c:if>
	<c:if test="${count > 0}">
	<table class="table table-borderless" style="text-align:center;">
		<thead class="thead-dark">
		<tr>
			<th>상태</th>
			<th>제목</th>
			<th>작성자</th>
			<th>날짜</th>
			<th>조회수</th>
		</tr>
		</thead>
		<c:forEach var="market" items="${list}">
		<tr>
			<c:if test="${market.choice==0 && market.state==0}"><td>판매</td></c:if>
			<c:if test="${market.choice==0 && market.state==1}"><td>판매완료</td></c:if>
			<c:if test="${market.choice==1 && market.state==0}"><td>구매</td></c:if>
			<c:if test="${market.choice==1 && market.state==1}"><td>구매완료</td></c:if>
			<td><a href="marketDetail.do?market_num=${market.market_num}">${market.title}</a></td>
			<td>${market.name}</td>
			<td>${market.reg_date}</td>
			<td>${market.hit}</td>
		</tr>
		</c:forEach>
	</table>
	<div class="align-center">${pagingHtml}</div>
	</c:if>
</div>
<!-- 거래게시판 메인 끝 --> 