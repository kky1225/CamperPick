<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		var checkId = 0;
		
		$('#confirmId').click(function(){
			if($('#id').val().trim()==''){
				$('#message_id').css('color', 'red').text('아이디를 입력하세요');
				$('#id').val('').focus();
				return;
			}
			
			$.ajax({
				url:'confirmId.do',
				type:'post',
				data:{id:$('#id').val()},
				dataType:'json',
				cache:false,
				timeout:30000,
				success:function(param){
					if(param.result == 'idNotFound'){
						$('#message_id').css('color', 'green').text('등록가능 ID');
						checkId = 1;
					}else if(param.result == 'idDuplicated'){
						$('#message_id').css('color', 'red').text('중복된 아이디');
						$('#id').foucs();
						checkId = 0;
					}else if(param.result == 'notMatchPattern'){
						$('#message_id').css('color', 'red').text('영문, 숫자 4~12자리 입력');
						checkId = 0;
					}else{
						checkId = 0;
						alert('아이디 중복 체크 오류');
					}
				},
				error:function(){
					checkId = 0;
					alert('네트워크 오류 발생');
				}
			});
			
			$('#register_form #id').keydown(function(){
				checkId = 0;
				$('#message_id').text('');
			});
			
			$('#register_form').submit(function(){
				if(checkId == 0){
					$('#message_id').css('color', 'red').text('아이디 중복 체크 필수');
					if($('#id').val().trim() == ''){
						$('#id').focus();
					}
					return false;
				}
			});
		});
		
	});
</script>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!-- 중앙 내용 -->
<div class="page-main">
	<h2>회원가입</h2>
	<form:form id="register_form" action="registerUser.do" modelAttribute="memberVO">
		<ul>
			<li>
				<label for="id">아이디</label>
				<form:input path="id" placeholer="4~12자 영문, 숫자만 허용"/>
				<input type="button" id="confirmId" value="ID중복체크">
				<span id="message_id"></span>
				<form:errors path="id" cssClass="error-color"/>
			</li>
			<li>
				<label for="name">이름</label>
				<form:input path="name"/>
				<form:errors path="name" cssClass="error-color"/>
			</li>
			<li>
				<label for="passwd">비밀번호</label>
				<form:password path="passwd" placeholer="4~12자 영문, 숫자만 허용"/>
				<form:errors path="passwd" cssClass="error-color"/>
			</li>
			<li>
				<label for="phone">전화번호</label>
				<form:input path="phone"/>
				<form:errors path="phone" cssClass="error-color"/>
			</li>
			<li>
				<label for="email">E-mail</label>
				<form:input path="email"/>
				<form:errors path="email" cssClass="error-color"/>
			</li>
			<li>
				<label for="zipcode">우편번호</label>
				<form:input path="zipcode"/><input type="button" onclick="execDaumPostcode()" value="우편번호 찾기">
				<form:errors path="zipcode" cssClass="error-color"/>
			</li>
			<li>
				<label for="address1">주소</label>
				<form:input path="address1"/>
				<form:errors path="address1" cssClass="error-color"/>
			</li>
			<li>
				<label for="address2">상세주소</label>
				<form:input path="address2"/>
				<form:errors path="address2" cssClass="error-color"/>
			</li>
		</ul>
		<div class="algin-center">
			<form:button>전송</form:button>
			<input type="button" value="홈으로" onclick="location.href='${PageContext.request.contextPath}/main/main.do'">
		</div>
	</form:form>
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