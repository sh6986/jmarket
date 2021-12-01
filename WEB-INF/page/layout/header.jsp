<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String cp = request.getContextPath();
%>
<script type="text/javascript">
function sendlogin() {
	var f = document.loginform;
	f.action = "<%=cp%>/user/login_ok.do";
	f.submit();
}
</script>
<form name="loginform" method="post">
<header>
			<div id="menuline" class="container" >
				<ul class="nav">
					<li><a href="<%=cp%>/home/home.do">HOME</a>
					
					<li><a href="<%=cp%>/sale/list.do">팝니다</a>
						<ul>
							<li><a href="<%=cp%>/sale/list.do">판매중</a></li>
							<li><a href="<%=cp%>/sale/list2.do">판매완료</a></li>
						</ul>

					<li><a href="<%=cp%>/buy/list1.do">삽니다</a>
						<ul>
							<li><a href="<%=cp%>/buy/list1.do">구매중</a></li>
							<li><a href="<%=cp%>/buy/list2.do">구매완료</a></li>

						</ul>

					<li><a href="">고객센터</a>
						<ul>
							<li><a href="<%=cp%>/notice/list.do">공지사항</a></li>
							<li><a href="<%=cp%>/event/list.do">이벤트</a></li>
							<li><a href="<%=cp%>/report/list.do">신고게시판</a>
							<li><a href="<%=cp%>/faq/faq_list.do">문의</a></li>

						</ul>
				<c:if test="${empty sessionScope.member}">
					<li><a href="<%=cp%>/user/login.do">로그인</a>
						<ul>
							<li><a href="<%=cp%>/user/newuser.do">회원가입</a></li>
							<li><a>ID&nbsp;<input class="login" type="text" maxlength="10" name="id">&nbsp;&nbsp;&nbsp;&nbsp;Password&nbsp;<input class="login" type="password" maxlength="10" name="pwd">&nbsp;&nbsp;<button class="loginbtn" type="button" onclick="sendlogin();">로그인</button></a></li>
						</ul>
				</c:if>
				<c:if test="${not empty sessionScope.member}">
					<li><a href="">회원</a>
						<ul>
							<li><a href="<%=cp%>/user/updateuser.do">회원정보수정</a></li>
							<li><a href="<%=cp%>/user/logout.do">로그아웃</a></li>
						</ul>
				</c:if>
				</ul>
				
			</div>


			<div id="logo">
				<div id="logoline" class="container">
					<div id="logotxt">
						<h1>
						<img class="img box"src="<%=cp%>/resource/image/jm.jpg">
							<a 	style="font-family: 'Jua', sans-serif;"  href="<%=cp%>/home/home.do">자몽마켓</a>							
						</h1>
						<p>자몽 마켓에 오신것을 환영합니다.</p>
					</div>
				</div>
			</div>
			<!-- section 끝 -->
</header>
</form>