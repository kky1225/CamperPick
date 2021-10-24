<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %> 
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css">  
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		var checkId = 0;
		var checkPasswd = 0;
		var checkPasswd2 = 0;
		var checkPhone = 0;
		
		//아이디 중복 안내 메시지 초기화 및 아이디 중복 값 초기화
		$('#register_form #email').keyup(function(){
			checkId = 0;
			$('#message_email').text('');
			
			$.ajax({
				url:'confirmEmail.do',
				type:'post',
				data:{email:$('#email').val()},
				dataType:'json',
				cache:false,
				timeout:30000,
				success:function(param){
					if(param.result == 'idNotFound'){
						$('#message_email').css('color','green').text('사용 가능합니다');
						checkId = 1;
					}else if(param.result == 'idDuplicated'){
						$('#message_email').css('color','red').text('중복된 이메일입니다');
						checkId = 0;
					}else if(param.result == 'notMatchPattern'){
						$('#message_email').css('color','red').text('이메일이 아닙니다');
						checkId = 0;
					}else{
						checkId = 0;
						alert('이메일 중복체크 오류');
					}
				},
				error:function(){
					checkId = 0;
					alert('네트워크 오류 발생');
				}
			}); //end if ajax
		});
		
		$('#register_form #passwd').keyup(function(){
			checkPasswd = 0;
			$('#message_passwd').text('');
			
			$.ajax({
				url:'confirmPasswd.do',
				type:'post',
				data:{passwd:$('#passwd').val()},
				dataType:'json',
				cache:false,
				timeout:30000,
				success:function(param){
					if(param.result == 'good'){
						$('#message_passwd').css('color','green').text('사용 가능합니다');
						checkPasswd = 1;
					}else if(param.result == 'notMatchPattern'){
						$('#message_passwd').css('color','red').text('4~12자리 영문, 숫자만 가능합니다');
						checkPasswd = 0;
					}else{
						alert('비밀번호 체크 오류');
						checkPasswd = 0;
					}
				},
				error:function(){
					alert('네트워크 오류 발생');
				}
			}); //end if ajax
		});
		
		$('#register_form #passwdCheck').keyup(function(){
			checkPasswd2 = 0;
			if($('#passwdCheck').val().trim() == $('#passwd').val().trim()){
				$('#message_passwd2').css('color','green').text('비밀번호가 동일합니다');
				checkPasswd2 = 1;
			}else{
				$('#message_passwd2').css('color','red').text('비밀번호가 서로 다릅니다');
				checkPasswd2 = 0;
			}
		});
		
		$('#register_form #phone').keyup(function(){
			checkPhone = 0;
			$('#message_phone').text('');
			
			$.ajax({
				url:'confirmPhone.do',
				type:'post',
				data:{phone:$('#phone').val()},
				dataType:'json',
				cache:false,
				timeout:30000,
				success:function(param){
					if(param.result == 'good'){
						$('#message_phone').css('color','green').text('사용 가능합니다');
						checkPhone = 1;
					}else if(param.result == 'phoneDuplicated'){
						$('#message_phone').css('color','red').text('중복된 전화번호입니다');
						checkPhone = 0;
					}else if(param.result == 'notMatchPattern'){
						$('#message_phone').css('color','red').text('전화번호 형식이 아닙니다');
						checkPhone = 0;
					}else{
						alert('전화번호 체크 오류');
						checkPhone = 0;
					}
				},
				error:function(){
					alert('네트워크 오류 발생');
				}
			}); //end if ajax
		});
		
		//submit 이벤트 발생시 비밀번호 체크 여부 확인
		$('#register_form').submit(function(){
			if(checkId==0){
				$('#email').focus();
				return false;
			}else if(checkPasswd == 0){
				$('#passwd').focus();
				return false;
			}else if(checkPasswd2 == 0){
				$('#passwdCheck').focus();
				return false;
			}else if(checkPhone == 0){
				$('#phone').focus();
				return false;
			}
		});
	});
	
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
<!-- 중앙 내용 시작 -->
<div class="page-main" align="left">
	
	<div id="main-content">
		<div class="main_bg">
			<div class="search_container">
	    		<h4 class="search_title">
			        회원가입
			    </h4>
			</div>
		</div>
	</div>

	<form:form id="register_form" action="registerUser.do" modelAttribute="memberVO">
		<ul class="align-center" style="margin-left:67px;">
			<li>
				<label for="email" style="margin-top:10px;">이메일</label>
				<form:input path="email" class="form-control form-label mt-4"/>
				<form:errors path="email" cssClass="error-color"/>
				<span id="message_email"></span>
			</li>
			<li>
				<label for="passwd" style="margin-top:10px;">비밀번호</label>
				<form:password path="passwd" class="form-control form-label mt-4"/>
				<form:errors path="passwd" cssClass="error-color"/>
				<span id="message_passwd"></span>
			</li>
			<li>
				<label for="passwdCheck" style="margin-top:10px;">비밀번호 확인</label>
				<input type="password" id="passwdCheck" class="form-control form-label mt-4"/>
				<span id="message_passwd2"></span>
			</li>
			<li>
				<label for="name" style="margin-top:10px;">이름</label>
				<form:input path="name" class="form-control form-label mt-4"/>
				<form:errors path="name" cssClass="error-color"/>
			</li>
			<li>
				<label for="phone" style="margin-top:10px;">전화번호</label>
				<form:input path="phone" class="form-control form-label mt-4"/>
				<form:errors path="phone" cssClass="error-color"/>
				<span id="message_phone"></span>
			</li>
			<li>
				<label for="zipcode" style="margin-top:32px;">우편번호</label>
				<div class="row">
					<div class="col-auto" style="padding: 0;">
						<form:input path="zipcode" class="form-control form-label mt-4"/>
						<form:errors path="zipcode" cssClass="error-color"/>
					</div>
					<div class="col-auto" style="margin-top:25px; margin-right:26px;">
						<input type="button" value="찾기" class="btn btn-dark" onclick="execDaumPostcode()">
					</div>
				</div>
			</li>
			<li>
				<label for="address1" style="margin-top:10px;">주소</label>
				<form:input path="address1" class="form-control form-label mt-4" style="width:275px"/>
				<form:errors path="address1" cssClass="error-color"/>
			</li>
			<li>
				<label for="address2" style="margin-top:10px;">나머지 주소</label>
				<form:input path="address2" class="form-control form-label mt-4" style="width:275px"/>
				<form:errors path="address2" cssClass="error-color"/>
			</li>
		</ul>
		<div class="align-center">
			<form:button class="btn btn-dark mt-5" style="width:300px;">회원가입</form:button>
		</div>
		<div id="layer" style="display:none;position:fixed;overflow:hidden;z-index:1;-webkit-overflow-scrolling:touch;">
			<img src="//t1.daumcdn.net/postcode/resource/images/close.png" id="btnCloseLayer" style="cursor:pointer;position:absolute;right:-3px;top:-3px;z-index:1" onclick="closeDaumPostcode()" alt="닫기 버튼">
		</div>
		<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
		<script>
 		   // 우편번호 찾기 화면을 넣을 element
  		  var element_layer = document.getElementById('layer');

  		  function closeDaumPostcode() {
  		      // iframe을 넣은 element를 안보이게 한다.
  		      element_layer.style.display = 'none';
 		   }

  		  function execDaumPostcode() {
   		     new daum.Postcode({
    		        oncomplete: function(data) {
     		           // 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

      		          // 각 주소의 노출 규칙에 따라 주소를 조합한다.
       		         // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
        		        var addr = ''; // 주소 변수
         		       var extraAddr = ''; // 참고항목 변수
		
         		       //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
          		      if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
           		         addr = data.roadAddress;
           		     } else { // 사용자가 지번 주소를 선택했을 경우(J)
            		        addr = data.jibunAddress;
            		    }

             		   // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
             		   if(data.userSelectedType === 'R'){
              		      // 법정동명이 있을 경우 추가한다. (법정리는 제외)
              		      // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
              		      if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
              		          extraAddr += data.bname;
               		     }
                		    // 건물명이 있고, 공동주택일 경우 추가한다.
                		    if(data.buildingName !== '' && data.apartment === 'Y'){
                		        extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                		    }
                		    // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                		    if(extraAddr !== ''){
                		        extraAddr = ' (' + extraAddr + ')';
                		    }
                 		   // 조합된 참고항목을 해당 필드에 넣는다.
                		    document.getElementById("address1").value = extraAddr;
                
             		   } else {
                		    document.getElementById("address1").value = '';
              		  }

               		 // 우편번호와 주소 정보를 해당 필드에 넣는다.
               		 document.getElementById('zipcode').value = data.zonecode;
               		 document.getElementById("address1").value = addr;
               		 // 커서를 상세주소 필드로 이동한다.
               		 document.getElementById("address2").focus();

              		  // iframe을 넣은 element를 안보이게 한다.
              		  // (autoClose:false 기능을 이용한다면, 아래 코드를 제거해야 화면에서 사라지지 않는다.)
               		 element_layer.style.display = 'none';
          		  },
          		  width : '100%',
          		  height : '100%',
          		  maxSuggestItems : 5
      		  }).embed(element_layer);

      		  // iframe을 넣은 element를 보이게 한다.
      		  element_layer.style.display = 'block';

        		// iframe을 넣은 element의 위치를 화면의 가운데로 이동시킨다.
       		 initLayerPosition();
  		  }

    		// 브라우저의 크기 변경에 따라 레이어를 가운데로 이동시키고자 하실때에는
    		// resize이벤트나, orientationchange이벤트를 이용하여 값이 변경될때마다 아래 함수를 실행 시켜 주시거나,
    		// 직접 element_layer의 top,left값을 수정해 주시면 됩니다.
    		function initLayerPosition(){
    		    var width = 300; //우편번호서비스가 들어갈 element의 width
    		    var height = 400; //우편번호서비스가 들어갈 element의 height
    		    var borderWidth = 5; //샘플에서 사용하는 border의 두께

     		   // 위에서 선언한 값들을 실제 element에 넣는다.
    		    element_layer.style.width = width + 'px';
     		   element_layer.style.height = height + 'px';
     		   element_layer.style.border = borderWidth + 'px solid';
    		    // 실행되는 순간의 화면 너비와 높이 값을 가져와서 중앙에 뜰 수 있도록 위치를 계산한다.
       		 element_layer.style.left = (((window.innerWidth || document.documentElement.clientWidth) - width)/2 - borderWidth) + 'px';
       		 element_layer.style.top = (((window.innerHeight || document.documentElement.clientHeight) - height)/2 - borderWidth) + 'px';
   		 }
		</script>
	</form:form>
</div>
<!-- 중앙 내용 끝 -->