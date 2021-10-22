<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- 중앙 내용 시작 -->
<div class="page-main">
	<h2>${review.title}</h2>
	<ul>
		<li>리뷰번호 : ${review.review_num}</li>
		<li>작성자 : ${review.mem_num}</li>
		<li>조회수 : ${review.hit}</li>
		<li>작성일 : ${review.reg_date}</li>
		<li>최근수정일 : ${review.modify_date}</li>
	</ul>
	<hr size="1" width="100%">
	<c:if test="${!empty review.filename}">
	<div class="align-center">
		<img src="imageView.do?review_num=${review.review_num}" style="max-width:500px">
	</div>
	</c:if>
	<p>내용: ${review.content}</p>
	<hr size="1" width="100%">
	<div class="align-right">
		<c:if test="${!empty user_num && user_num == review.mem_num}">
		<input type="button" value="수정" 
		                onclick="location.href='update.do?review_num=${review.review_num}'">
		<input type="button" value="삭제" id="delete_btn">
		<script type="text/javascript">
			var delete_btn = document.getElementById('delete_btn');
			delete_btn.onclick=function(){
				var choice = confirm('삭제하시겠습니까?');
				if(choice){
					location.replace('delete.do?review_num=${review.review_num}');
				}
			};
		</script>
		</c:if>
		<input type="button" value="목록" onclick="location.href='reviewlist.do'">
	</div>
</div>
<!-- 중앙 내용 끝 -->

