<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript" src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/videoAdapter.js"></script>    
<c:if test="${empty user_auth}">
	<c:set var="user_auth" value="0"></c:set>
	<c:set var="user_num" value="0"></c:set>
</c:if>
<script type="text/javascript">
	$(function() {
		var currentPage;
		var count;
		var rowCount;
		var view_button = 0;//댓댓글이 안 보여짐 (초기값)
		
		// 댓글 목록
		function selectData(pageNum,notice_num) {
			currentPage=pageNum;
			
			if(pageNum == 1){
				// 처음 호출에는 해당 ID의 div의 내부 내용물을 제거
				$('#output').empty();
			}
			
			// 로딩 이미지 노출
			$('#loading').show();
			
			$.ajax({
				type:'post',
				data:{pageNum:pageNum,notice_num:notice_num},
				url:'listReply.do',
				dataType:'json',
				cache:false,
				timeout:30000,
				success:function(param){
					// 로딩 이미지 감추기
					$('#loading').hide();
					count = param.count;
					rowCount = param.rowCount;
					
					$(param.list).each(function(index,item) {
						var output = '<div class="item">';
						output += '<h4>' + item.name + '</h4>';
						output += '<div class="sub-item">';
						output += '   <p>' + item.re_content.replace(/</gi,'&lt;').replace(/>/gi,'&gt;') + '</p>';
						output += item.re_date;
						if($('#mem_num').val() != ''){
							output += ' <input type="button" data-notice="'+item.notice_num+'" data-num="'+item.nre_num+'" data-mem="'+item.mem_num+'" value="답글 작성" class="write-btn btn btn-outline-dark" style="font-size:14px;"">';
							output += '&nbsp;'
						}
						output +='<input type="button" data-notice="'+item.notice_num+'" data-num="'+item.nre_num+'" value="답글 보기" class="view-btn btn btn-outline-dark" style="font-size:14px;"">';
						if($('#mem_num').val() == item.mem_num){
							// 로그인 한 회원 번호가 댓글 작성자 회원 번호와 같으면
							output += '	<input type="button" data-num="'+item.nre_num+'" data-mem="'+item.mem_num+'" value="수정" class="modify-btn btn btn-outline-dark" style="font-size:14px;">';
							output += '	<input type="button" data-num="'+item.nre_num+'" data-mem="'+item.mem_num+'" value="삭제" class="delete-btn btn btn-outline-dark" style="font-size:14px;">';
						}
						output += '	<hr size="1" noshade>';
						output += '</div>';
						output += '</div>';
						
						// 문서 객체에 추가
						$('#output').append(output);
					});	// end of each
					
					// paging button 처리
					if(currentPage >= Math.ceil(count/rowCount)){
						// 다음 페이지가 없음
						$('.paging-button').hide();
					}else{
						// 다음 페이지가 존재
						$('.paging-button').show();
					}
					
				},
				error:function(){
					alert('네트워크 오류 발생');
				}
			});
			
		}
		// 다음 댓글 보기 버튼 클릭 시 데이터 추가
		$('.paging-button input').click(function() {
			var pageNum = currentPage + 1;
			selectData(pageNum,$('#notice_num').val());
		});
		
		// 댓글 등록
		$('#re_form').submit(function(event) {
			if($('#re_content').val().trim()==''){
				alert('내용을 입력하세요!');
				$('#re_content').val('').focus();
				return false;
			}
			
			var data = $(this).serialize();
			// 등록
			$.ajax({
				type:'post',
				data:data,
				url:'writeReply.do',
				dataType:'json',
				cache:false,
				timeout:30000,
				success:function(param){
					if(param.result == 'logout'){
						alert('로그인해야 작성할 수 있습니다.');
					}else if(param.result == 'success'){
						// 폼 초기화
						initForm();
						// 댓글 작성이 성공하면 새로 삽입한 글을 포함해서 첫번째 페이지의
						// 게시글들을 다시 호출
						selectData(1,$('#notice_num').val());
					}else{
						alert('댓글 등록시 오류 발생!');
					}
				},
				error:function(){
					alert('네트워크 오류 발생');
				}
			});
			// 기본 이벤트 제거
			event.preventDefault();
		});
		
		// 댓글 작성 폼 초기화
		function initForm() {
			$('textarea').val('');
			$('#re_first .letter-count').text('300/300');
		}
		
		// textarea에 내용 입력 시 글자 수 체크
		$(document).on('keyup','textarea',function(){
			// 남은 글자수를 구함
			var inputLength = $(this).val().length;
			
			if(inputLength>300){	// 300자를 넘는 경우
				$(this).val($(this).val().substring(0,300));
			}else{	// 300자 이하의 경우
				var remain = 300 - inputLength;
				remain += '/300';
				if($(this).attr('id') == 're_content'){
					// 등록폼 글자수
					$('#re_first .letter-count').text(remain);
				}else{
					// 수정폼 글자수
					$('#mre_first .letter-count').text(remain);
				}
			}
		});
		
		//댓글 수정 버튼 클릭시 수정폼 노출
		$(document).on('click','.modify-btn',function(){
			//댓글 글번호
			var nre_num = $(this).attr('data-num');
			//작성자 회원 번호
			var mem_num = $(this).attr('data-mem');
			//댓글 내용
			var content = $(this).parent().find('p').html().replace(/<br>/gi,'\n');
			
			//댓글 수정폼 UI
			var modifyUI = '<form id="mre_form">';
			   modifyUI += '  <input type="hidden" name="nre_num" id="mnre_num" value="'+nre_num+'">';
			   modifyUI += '  <input type="hidden" name="mem_num" id="mmem_num" value="'+mem_num+'">';
			   modifyUI += '  <textarea rows="3" cols="50" name="re_content" id="mre_content" class="form-control" >'+content+'</textarea>';
			   modifyUI += '  <div id="mre_first"><span class="letter-count">300/300</span></div>';	
			   modifyUI += '  <div id="mre_second" class="align-right">';
			   modifyUI += '     <input type="submit" value="수정" class="btn btn-outline-dark" style="font-size:14px;">';
			   modifyUI += '     <input type="button" value="취소" class="re-reset btn btn-outline-dark" style="font-size:14px;">';
			   modifyUI += '  </div>';
			   modifyUI += '  <hr size="1" noshade width="90%">';
			   modifyUI += '</form>';
			   
			//이전에 이미 수정하는 댓글이 있을 경우 수정버튼을 클릭하면
			//숨김 sub-item을 환원시키고 수정폼을 최기화함
			initModifyForm();
			
			//지금 클릭해서 수정하고자 하는 데이터는 감추기
			//수정버튼을 감싸고 있는 div
			$(this).parent().hide();
			
			//수정폼을 수정하고자하는 데이터가 있는 div에 노출
			$(this).parents('.item').append(modifyUI);
			
			//입력한 글자수 셋팅
			var inputLength = $('#mre_content').val().length;
			var remain = 300 - inputLength;
			remain += '/300';
			
			//문서 객체에 반영
			$('#mre_first .letter-count').text(remain);		
		});
		
		// 수정 폼에서 취소 버튼 클릭 시 수정 폼 초기화
		$(document).on('click','.re-reset',function(){
			initModifyForm();
		});
		
		// 댓글 수정 폼 초기화
		function initModifyForm() {
			$('.sub-item').show();
			$('#mre_form').remove();
		}
		
		// 댓글 수정
		$(document).on('submit','#mre_form',function(event){
			if($('#mre_content').val().trim() == ''){
				alert('내용을 입력하세요!');
				$('#mre_content').val('').focus();
				return false;
			}
			
			// 폼에 입력한 데이터를 반환
			var data = $(this).serialize();
			
			// 수정
			$.ajax({
				url : 'updateReply.do',
				type : 'post',
				data : data,
				dataType : 'json',
				cache : false,
				timeout : 30000,
				success : function(param) {
					if(param.result == 'logout'){
						alert('로그인 후 수정할 수 있습니다.');
					}else if(param.result == 'success'){
						$('#mre_form').parent().find('p').html($('#mre_content').val().replace(/</g,'&lt;').replace(/>/g,'&gt;'));
						// 수정폼 초기화
						initModifyForm();
						alert('댓글 수정 완료!');
					}else if(param.result == 'wrongAccess'){
						alert('타인의 글을 수정할 수 없습니다.');
					}else{
						alert('댓글 수정시 오류 발생');
					}
				},
				error : function() {
					alert('네트워크 오류 발생');
				}
			});
			// 기본 이벤트 제거
			event.preventDefault();
		});
		
		// 댓글 삭제
		$(document).on('click','.delete-btn',function(){
			// 댓글 번호
			var nre_num = $(this).attr('data-num');
			// 작성자 회원번호
			var mem_num = $(this).attr('data-mem');
			
			var choice = confirm('댓글을 삭제하시겠습니까?');
			if(choice){
				$.ajax({
					type:'post',
					url:'deleteReply.do',
					data:{nre_num:nre_num,mem_num:mem_num},
					dataType:'json',
					cache:false,
					timeout:30000,
					success:function(param){
						if(param.result == 'logout'){
							alert('로그인 후 삭제 가능');
						}else if(param.result == 'success'){
							alert('삭제 완료!');
							selectData(1,$('#notice_num').val());
						}else if(param.result == 'wrongAcccess'){
							alert('타인의 글은 삭제할 수 없음');
						}else{
							alert('댓글 삭제 시 오류 발생');
						}
					},
					error:function(){
						alert('네트워크 오류 발생');
					}
				});
			}
	
		});
		
		// 초기 데이터(목록) 호출
		selectData(1,$('#notice_num').val());
		
		// 대댓글
		
		//대댓글 작성 버튼 클릭시 대댓글 작성폼 노출------------
		$(document).on('click','.write-btn',function(){
			//댓글 글번호
			var nre_num = $(this).attr('data-num');
			//게시판 번호
			var notice_num = $(this).attr('data-notice');
			//대댓글 작성자 회원 번호
			mem_num = ${user_auth};
			
			//대댓글 작성폼 UI
			var rmodifyUI = '<form id="wre_form">';
			   rmodifyUI += '  <input type="hidden" name="nre_num" id="cnre_num" value="'+nre_num+'">';
			   rmodifyUI += '  <input type="hidden" name="mem_num" id="cmem_num" value="'+mem_num+'">';
			   rmodifyUI += '  <input type="hidden" name="notice_num" id="cnotice_num" value="'+notice_num+'">';
			   rmodifyUI += '  <textarea rows="3" cols="50" name="re_content" id="cre_content" class="form-control" ></textarea>';
			   rmodifyUI += '  <div id="cre_first"><span class="letter-count">300/300</span></div>';	
			   rmodifyUI += '  <div id="cre_second" class="align-right">';
			   rmodifyUI += '     <input type="submit" value="등록" class="btn btn-outline-dark" style="font-size:14px;">';
			   rmodifyUI += '     <input type="button" value="취소" class="wre-reset btn btn-outline-dark" style="font-size:14px;"">';
			   rmodifyUI += '  </div>';
			   rmodifyUI += '  <hr size="1" noshade width="90%">';
			   rmodifyUI += '</form>';
			   
			//이전에 이미 작성하는 댓글이 있을 경우 작성버튼을 클릭하면
			//숨김 sub-item을 환원시키고 작성폼을 초기화함
			initWriteForm();
			
			//지금 클릭해서 작성하고자 하는 데이터는 감추기
			//작성버튼을 감싸고 있는 div
			$(this).parent().hide();
			
			//작성폼을 수정하고자하는 데이터가 있는 div에 노출
			$(this).parents('.item').append(rmodifyUI);
		});
		
		// ########## 대댓글 등록
		$(document).on('submit','#wre_form',function(event){
			if($('#cre_content').val().trim()==''){
				alert('내용을 입력하세요!');
				$('#cre_content').val('').focus();
				return false;
			}
			var data = $(this).serialize();
			//등록
			$.ajax({
				data:data,
				type:'post',
				url:'writeReReply.do',
				dataType:'json',
				cache:false,
				timeout:30000,
				success:function(param){
					if(param.result == 'logout'){
						alert('로그인해야 작성할 수 있습니다.');
					}else if(param.result == 'success'){
						//폼초기화
						initWriteForm();
						view_button = 0; //대댓글 안 보여짐
						//댓글 작성이 성공하면 새로 삽입한 글을 포함해서 첫번째 페이지의
						//게시글들을 다시 호출
						selectData(1,$('#notice_num').val());
					}else{
						alert('댓글 등록시 오류 발생!');
					}
				},
				error:function(){
					alert('네트워크 오류 발생');
				}
			});
			//기본 이벤트 제거
			event.preventDefault();
		});
		
		//textarea에 내용 입력시 글자수 체크
		$(document).on('keyup','textarea',function(){
			//남은 글자수를 구함
			var rinputLength = $(this).val().length;
			
			if(rinputLength>300){//300자를 넘어선 경우
				$(this).val($(this).val().substring(0,300));
			}else{//300자 이하인 경우
				var rremain = 300 - rinputLength;
				rremain += '/300';
				if($(this).attr('id') == 'cre_content'){
					//등록폼 글자수
					$('#cre_first .letter-count').text(rremain);
				}else{
					//수정폼 글자수
					$('#rcre_first .letter-count').text(rremain);
				}
			}
		});	
		
		//작성폼에서 취소 버튼 클릭시 작성폼 초기화
		$(document).on('click','.wre-reset',function(){
			initWriteForm();
		});
		//댓글 작성 폼 초기화
		function initWriteForm(){
			$('.sub-item').show();
			$('#wre_form').remove();
		}
		
		
		//------------------------대댓글 리스트-----
		//대댓글 리스트 버튼 클릭시 대댓글 리스트 노출
		$(document).on('click','.view-btn',function(){
			var view_btn = $(this);
			var nre_num = $(this).attr('data-num');
			if(view_button == 0){
				$.ajax({
					type:'post',
					data:{nre_num:nre_num},
					url:'rereplyList.do',
					dataType:'json',
					cache:false,
					timeout:30000,
					success:function(param){
						 //로딩 이미지 감추기
						var output = '';
						$(param.list).each(function(index,item){
							output += '<div class="rritem" style="margin:20px 30px 40px 50px; ">';
							output += '<div class="rrsub-item">';
							output +='   <p>' + item.re_content.replace(/</gi,'&lt;').replace(/>/gi,'&gt;') + '</p>';
							output += item.re_date; 
							if($('#mem_num').val()==item.mem_num){
								//로그인한 회원 번호가 댓글 작성자 회원 번호와 같으면
								output += ' <input type="button" data-notice="'+item.notice_num+'" data-num="'+item.nrre_num+'" data-mem="'+item.mem_num+'" value="수정" class="rmodify-btn btn btn-outline-dark" style="font-size:14px;"">';
								output += ' <input type="button" data-notice="'+item.notice_num+'" data-num="'+item.nrre_num+'" data-mem="'+item.mem_num+'" value="삭제" class="rdelete-btn btn btn-outline-dark" style="font-size:14px;"">';
							 }
							output += '</div>';
							output += '</div>';
						});//end of each
						if(param.list.length > 0){
							output += '  <hr size="1" noshade class="view-hr">';
							//문서 객체에 추가
							view_btn.parents('.sub-item').append(output);
						}
						view_button = 1; //댓댓글이 보여짐 
						},
					error:function(){
						alert('네트워크 오류 발생');
					}
				}); 
			}else{
				$('.rritem').hide();
				$('.view-hr').hide();
				view_button = 0; //댓댓글이 안 보여지게 함
			}
		}); 
		
		//대댓글 수정 버튼 클릭시 수정폼 노출
		$(document).on('click','.rmodify-btn',function(){
			//대댓글 글번호
			var nrre_num = $(this).attr('data-num');
			//작성자 회원 번호
			var mem_num = $(this).attr('data-mem');
			//대댓글 내용
			var re_content = $(this).parent().find('p').html().replace(/<br>/gi,'\n');
			//대댓글 글번호
			var notice_num = $(this).attr('data-notice');
			
			//댓글 수정폼 UI
			var modifyUI = '<form id="remre_form">';
			   modifyUI += '  <input type="hidden" name="nrre_num" id="rcre_num" value="'+nrre_num+'">';
			   modifyUI += '  <input type="hidden" name="mem_num" id="rcmem_num" value="'+mem_num+'">';
			   modifyUI += '  <input type="hidden" name="notice_num" id="rcnotice_num" value="'+notice_num+'">';
			   modifyUI += '  <textarea rows="3" cols="50" name="re_content" id="rcre_content" class="form-control">'+re_content+'</textarea>';
			   modifyUI += '  <div id="rcre_first"><span class="letter-count">300/300</span></div>';	
			   modifyUI += '  <div id="rcre_second" class="align-right">';
			   modifyUI += '     <input type="submit" value="수정" class="btn btn-outline-dark" style="font-size:14px;">';
			   modifyUI += '     <input type="button" value="취소" class="rre-reset btn btn-outline-dark" style="font-size:14px;"">';
			   modifyUI += '  </div>';
			   modifyUI += '  <hr size="1" noshade width="90%">';
			   modifyUI += '</form>';
			   
			//이전에 이미 수정하는 댓글이 있을 경우 수정버튼을 클릭하면
			//숨김 sub-item을 환원시키고 수정폼을 초기화함
			rinitModifyForm();
			
			//지금 클릭해서 수정하고자 하는 데이터는 감추기
			//수정버튼을 감싸고 있는 div
			$(this).parent().hide();
			
			//수정폼을 수정하고자하는 데이터가 있는 div에 노출
			$(this).parents('.rritem').append(modifyUI);
			
		});
		//수정폼에서 취소 버튼 클릭시 수정폼 초기화
		$(document).on('click','.rre-reset',function(){
			rinitModifyForm();
		});
		// 대댓글 수정 폼 초기화
		function rinitModifyForm(){
			$('.rrsub-item').show();
			$('#remre_form').remove();
		}
		//대댓글 수정
		$(document).on('submit','#remre_form',function(event){
			if($('#rcre_content').val().trim()==''){
				alert('내용을 입력하세요');
				$('#rcre_content').val('').focus();
				return false;
			}
			
			//폼에 입력한 데이터 반환
			var data=$(this).serialize();
			
			//수정
			$.ajax({
				url:'updateReReply.do',
				type:'post',
				data:data,
				dataType:'json',
				cache:false,
				timeout:30000,
				success:function(param){
					if(param.result =='logout'){
						alert('로그인해야 수정할 수 있습니다.')
					}else if(param.result == 'success'){
						$('#remre_form').parent().find('p').html($('#rcre_content').val().replace(/</g,'&lt;').replace(/>/g,'&gt;'));
						//수정폼 초기화
						rinitModifyForm();
					}else if(param.result == 'wrongAccess'){
						alert('타인의 글을 수정할 수 없습니다.');
					}else{
						alert('댓글 수정시 오류 발생');
					}
				},
				error:function(){
					alert('네트워크 오류발생');
				}
			});
			
			//기본 이벤트 제거
			event.preventDefault();
			
		});
		
		//대댓글 삭제
		$(document).on('click','.rdelete-btn',function(){
			//대댓글 번호
			var nrre_num = $(this).attr('data-num');
			//작성자 회원 번호
			var mem_num = $(this).attr('data-mem');
			//공지사항 번호
			var notice_num = $(this).attr('data-notice');
			
			$.ajax({
				type:'post',
				url:'deleteReReply.do',
				data:{nrre_num:nrre_num,mem_num:mem_num},
				dataType:'json',
				cache:false,
				timeout:30000,
				success:function(param){
					if(param.result == 'logout'){
						alert('로그인해야 삭제할 수 있습니다.');
					}else if(param.result == 'success'){
						alert('삭제가 완료되었습니다');
						selectData(1,$('#notice_num').val());
					}else if(param.result == 'wrongAccess'){
						alert('타인의 글을 삭제할 수 없습니다.');
					}else{
						alert('댓글 삭제시 오류 발생');
					}
				},
				error:function(){
					alert('네트워크 오류 발생');
				}
			});
			
		});
		//초기 데이터(목록) 호출
		//selectData(1,$('#market_num').val()); 
		
	});
	
	// 타이틀 배경이미지
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
<!DOCTYPE html>
<!-- 공지사항 디테일 시작 -->
<div class="page-main" id="container">

	<div id="main-content">
		<div class="main_bg">
			<div class="search_container">
	    		<h4 class="search_title" style="font-size: 23px;">
			        공지사항
			    </h4>
			</div>
		</div>
	</div>
	
	<br>
	<div class="content-top">
		<table summary="">
			<tr>
				<th>제목</th>
				<td>${notice.title}</td>
			</tr>
			<tr>
				<th>작성자</th>
				<td>${notice.name}
					<div class="content-date">
						<div class="reg">
							작성일 : 
							<span class="txt">${notice.reg_date}</span>
						</div>
						<div class="modify">
							수정일 : 
							<span class="txt">${notice.modify_date}</span>
						</div>
					</div>
				</td>
			</tr>
		</table>
	</div>
	
	<div class="content-datail" style="padding-left:20px;">
		${notice.content}
	</div>
	
	<br>
	
	<c:if test="${!empty notice.filename}">
		<div class="align-center">
			<img src="imageView.do?notice_num=${notice.notice_num}" style="max-width: 500px">
		</div>
		<hr width="100%" size="1" noshade="noshade">
	</c:if>
	<c:if test="${empty notice.filename}">
		<hr width="100%" size="1" noshade="noshade">
	</c:if>
	<div class="align-right">
		<c:if test="${user_num == notice.mem_num}">
			<input type="button" value="수정" onclick="location.href='noticeUpdate.do?notice_num=${notice.notice_num}'" class="button-large" style="font-size:14px;">
			<input type="button" value="삭제" onclick="location.href='noticeDelete.do?notice_num=${notice.notice_num}'" class="button-large" style="font-size:14px;">
		</c:if>
		<input type="button" value="목록" onclick="location.href='${pageContext.request.contextPath}/notice/noticeList.do'" class="button-large" style="font-size:14px;">
	</div>
	
	<hr size="1" width="100%" noshade="noshade">
	
	<div id="reply_div">
		<span class="reply-title">댓글</span>
		<form id="re_form">
			<input type="hidden" name="notice_num" value="${notice.notice_num}" id="notice_num">
			<input type="hidden" name="mem_num" value="${user_num}" id="mem_num">
			<textarea rows="3" cols="50" name="re_content"
			  id="re_content" class="form-control" 
			  <c:if test="${empty user_num || user_num==0}">disabled="disabled"</c:if>
			  ><c:if test="${empty user_num || user_num==0}">로그인 해야 작성할 수 있습니다.</c:if></textarea>
			  <c:if test="${!empty user_num}">
			<div id="re_first">
				<span class="letter-count">300/300</span>
			</div>
			<div id="re_second" class="align-right">
				<input type="submit" class="button-large mt-3" value="전송" style="margin-left:100px;">
			</div>
			</c:if>
		</form>
	</div>
	<!-- 댓글 목록 출력 -->
	<div id="output"></div>
	<div class="paging-button" style="display: none;">
		<input type="button"  class="button" value="다음 글 보기">
	</div>
	<div id="loading" style="display: none;">
		<img src="${pageContext.request.contextPath}/resources/images/ajax-loader.gif">
	</div>
</div>
<!-- 공지사항 디테일 끝 -->