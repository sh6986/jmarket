<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String cp = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="<%=cp%>/resource/css/home.css" type="text/css">
<link rel="stylesheet" href="<%=cp%>/resource/css/join.css" type="text/css">
<link rel="stylesheet" href="<%=cp%>/resource/css/sidemenu.css" type="text/css">
<link href="https://fonts.googleapis.com/css2?family=Cute+Font&family=Jua&display=swap" rel="stylesheet">
<script type="text/javascript">
function sendjoin() {
	
	var f = document.join;
	var str;
	str = f.userId.value;
	str = str.trim();
	if(!str) {
		alert("아이디를 입력하세요. ");
		f.userId.focus();
		return;
	}
	if(!/^[a-z][a-z0-9_]{4,9}$/i.test(str)) { 
		alert("아이디는 5~10자이며 첫글자는 영문자이어야 합니다.");
		f.userId.focus();
		return;
	}
	f.userId.value = str;

	str = f.userPwd.value;
	str = str.trim();
	if(!str) {
		alert("패스워드를 입력하세요. ");
		f.userPwd.focus();
		return;
	}
	if(!/^(?=.*[a-z])(?=.*[!@#$%^*+=-]|.*[0-9]).{5,10}$/i.test(str)) { 
		alert("패스워드는 5~10자이며 하나 이상의 숫자나 특수문자가 포함되어야 합니다.");
		f.userPwd.focus();
		return;
	}
	f.userPwd.value = str;

	if(str!= f.userPwdCheck.value) {
        alert("패스워드가 일치하지 않습니다. ");
        f.userPwdCheck.focus();
        return;
	}
	
    str = f.userName.value;
	str = str.trim();
    if(!str) {
        alert("이름을 입력하세요. ");
        f.userName.focus();
        return;
    }
    f.userName.value = str;

    str = f.birth.value;
	str = str.trim();
    if(!str || !isValidDateFormat(str)) {
        alert("생년월일를 입력하세요[YYYY-MM-DD]. ");
        f.birth.focus();
        return;
    }
    
    str = f.tel1.value;
	str = str.trim();
    if(!str) {
        alert("전화번호를 입력하세요. ");
        f.tel1.focus();
        return;
    }

    str = f.tel2.value;
	str = str.trim();
    if(!str) {
        alert("전화번호를 입력하세요. ");
        f.tel2.focus();
        return;
    }
    if(!/^(\d+)$/.test(str)) {
        alert("숫자만 가능합니다. ");
        f.tel2.focus();
        return;
    }

    str = f.tel3.value;
	str = str.trim();
    if(!str) {
        alert("전화번호를 입력하세요. ");
        f.tel3.focus();
        return;
    }
    if(!/^(\d+)$/.test(str)) {
        alert("숫자만 가능합니다. ");
        f.tel3.focus();
        return;
    }
    
    str = f.email1.value;
	str = str.trim();
    if(!str) {
        alert("이메일을 입력하세요. ");
        f.email1.focus();
        return;
    }

    str = f.email2.value;
	str = str.trim();
    if(!str) {
        alert("이메일을 입력하세요. ");
        f.email2.focus();
        return;
    }

    var mode="${mode}";
    if(mode=="created") {
    	f.action = "<%=cp%>/user/newuser_ok.do";
    } else if(mode=="update") {
    	f.action = "<%=cp%>/user/updateuser_ok.do";
    }
    f.submit();
}

function changeEmail() {
    var f = document.join;
	    
    var str = f.selectEmail.value;
    if(str!="direct") {
        f.email2.value=str; 
        f.email2.readOnly = true;
        f.email1.focus(); 
    }
    else {
        f.email2.value="";
        f.email2.readOnly = false;
        f.email1.focus();
    }
}

function isValidDateFormat(data){
    var regexp = /[12][0-9]{3}[\.|\-|\/]?[0-9]{2}[\.|\-|\/]?[0-9]{2}/;
    if(! regexp.test(data))
        return false;

    regexp=/(\.)|(\-)|(\/)/g;
    data=data.replace(regexp, "");
    
	var y=parseInt(data.substr(0, 4));
    var m=parseInt(data.substr(4, 2));
    if(m<1||m>12) 
    	return false;
    var d=parseInt(data.substr(6));
    var lastDay = (new Date(y, m, 0)).getDate();
    if(d<1||d>lastDay)
    	return false;
		
	return true;
}
</script>
</head>
<body>
	<div id="mainframe">
	 <jsp:include page="/WEB-INF/page/layout/header.jsp"></jsp:include>
	 
	 <form name="join" method="post">
	 		<section class="container">
			<article>
				<div id = "content">
					<h2>|&nbsp;&nbsp;${title}</h2>
					<div id = "newaccount">
						<ul>
							<li class="infotxt">아이디</li>
							<li class="inputtxt"><input type="text" name="userId" value="${dto.id}" ${mode=="update" ? "readonly='readonly' ":""}></li>
						</ul>
						<ul>
							<li class="infotxt">패스워드</li>
							<li class="inputtxt"><input type="password" name="userPwd"></li>
						</ul>
						<ul>
							<li class="infotxt">패스워드 확인</li>
							<li class="inputtxt"><input type="password" name="userPwdCheck"></li>
						</ul>
						<ul>
							<li class="infotxt">이름</li>
							<li class="inputtxt"><input type="text" name="userName" value="${dto.name}" ${mode=="update" ? "readonly='readonly' ":""}></li>
						</ul>
						<ul>
							<li class="infotxt">생년월일</li>
							<li class="inputtxt"><input type="text" name="birth" value="${dto.birth}"></li>
						</ul>
						<ul>
							<li class="infotxt">전화번호</li>
							<li class="inputtxt">
							<select name="tel1" style="width: 72px;height: 31px;">
							<option value="010" ${dto.tel1=="010" ? "selected='selected'" : ""}> 010 </option>
							<option value="011" ${dto.tel1=="011" ? "selected='selected'" : ""}> 011 </option>
							<option value="012" ${dto.tel1=="012" ? "selected='selected'" : ""}> 012 </option>
							<option value="019" ${dto.tel1=="019" ? "selected='selected'" : ""}> 019 </option>
							</select>
							- <input class="tel" name="tel2" value="${dto.tel2}"> - <input class="tel" name="tel3" value="${dto.tel3}"></li>
						</ul>
						<ul>
							<li class="infotxt cityinfotxt">주소</li>
							<li class="inputtxt"> <input type="text" id="zip" name="zip" readonly="readonly" placeholder="우편번호" style="width: 130px" value="${dto.home}">&nbsp;<input class="cityline" id="addr1" name="addr1" placeholder="기본 주소" readonly="readonly" style="width: 255px" value="${dto.addr1}">
							<button type="button" class="btn" onclick="daumPostcode();" style="height: 32px; width: 95px;">우편번호</button></li>
						</ul>
						<ul>
							<li class="infotxt cityinfotxt">&nbsp;</li>
							<li class="inputtxt"><input type="text" class="cityline" id="addr2" name="addr2" placeholder="나머지 주소" value="${dto.addr2}"></li>
						</ul>
						<ul>
							<li class="infotxt">이메일</li>
							<li class="inputtxt">
								<input type="text" name="email1" value="${dto.email1}"> @ 
								<input type="text" name="email2" style="width: 145px;" value="${dto.email2}">
								<select name = "selectEmail" onchange="changeEmail();">
									<option value="direct"> 직접입력 </option>
									<option value="naver.com" ${dto.email2=="naver.com" ? "selected='selected'" : ""}> NAVER </option>
									<option value="daum.com" ${dto.email2=="daum.com" ? "selected='selected'" : ""}> DAUM </option>
									<option value="gmail.com" ${dto.email2=="gmail.com" ? "selected='selected'" : ""}> GMAIL </option>
								</select>
							</li>
						</ul>
					</div>
						<div class="btnbox"><button class="btn1" type="button" onclick="sendjoin();">제 출</button>&nbsp;&nbsp;<button class="btn1">취 소</button></div>
				</div>
			</article>
			<aside>
			<div class="sidebox">
				<div id="sidetitle">
					<h2 style="font-family: 'Do Hyeon', sans-serif;">|&nbsp;&nbsp;메뉴</h2>
				</div>
				<ul>
					<li><a href="<%=cp%>/user/login.do">-로그인</a></li>
					<li><a href="<%=cp%>/user/newuser.do">-회원가입</a></li>
					<c:if test="${not empty dto}">
						<li><a href="<%=cp%>/user/updateuser.do">-회원정보수정</a></li>
					</c:if>
				</ul>
			</div>
			</aside>
		</section>
	 </form>
<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>
<script>
    function daumPostcode() {
        new daum.Postcode({
            oncomplete: function(data) {
                // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

                // 각 주소의 노출 규칙에 따라 주소를 조합한다.
                // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                var fullAddr = ''; // 최종 주소 변수
                var extraAddr = ''; // 조합형 주소 변수

                // 사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
                if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                    fullAddr = data.roadAddress;

                } else { // 사용자가 지번 주소를 선택했을 경우(J)
                    fullAddr = data.jibunAddress;
                }

                // 사용자가 선택한 주소가 도로명 타입일때 조합한다.
                if(data.userSelectedType === 'R'){
                    //법정동명이 있을 경우 추가한다.
                    if(data.bname !== ''){
                        extraAddr += data.bname;
                    }
                    // 건물명이 있을 경우 추가한다.
                    if(data.buildingName !== ''){
                        extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                    }
                    // 조합형주소의 유무에 따라 양쪽에 괄호를 추가하여 최종 주소를 만든다.
                    fullAddr += (extraAddr !== '' ? ' ('+ extraAddr +')' : '');
                }

                // 우편번호와 주소 정보를 해당 필드에 넣는다.
                document.getElementById('zip').value = data.zonecode; //5자리 새우편번호 사용
                document.getElementById('addr1').value = fullAddr;

                // 커서를 상세주소 필드로 이동한다.
                document.getElementById('addr2').focus();
            }
        }).open();
    }
</script>   
	</div>
	<jsp:include page="/WEB-INF/page/layout/footer.jsp"></jsp:include>
</body>
</html>