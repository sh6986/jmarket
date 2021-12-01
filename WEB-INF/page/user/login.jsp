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
<link rel="stylesheet" href="<%=cp%>/resource/css/login.css" type="text/css">
<link href="https://fonts.googleapis.com/css2?family=Cute+Font&family=Jua&display=swap" rel="stylesheet">
<script type="text/javascript">
function sendlogin2() {
	var f = document.loginform2;
	f.action = "<%=cp%>/user/login_ok.do";
	f.submit();
}
</script>
</head>
<body>
	<div id="mainframe">
	 <jsp:include page="/WEB-INF/page/layout/header.jsp"></jsp:include>
	 <form name="loginform2" method="post">
	 <section class="container">
			<article>
				<div class="login-main">
					<ul>
						<li><input type="text" name="id" placeholder="id"></li>
						<li><input type="password" name="pwd" placeholder="password"></li>
						<li><button type="button" onclick="sendlogin2();">LOGIN</button></li>
					</ul>
				</div>
				<div class="login-menu">
					<ul>
						<li><a href="#">아이디찾기</a></li>
						<li>|</li>
						<li><a href="#">비밀번호찾기</a></li>
						<li>|</li>
						<li><a href="<%=cp%>/user/newuser.do">회원가입</a></li>
					</ul>
				</div>
			</article>
		</section>
	</form>
	</div>
	<jsp:include page="/WEB-INF/page/layout/footer.jsp"></jsp:include>
</body>
</html>