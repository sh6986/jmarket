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
function btn(num){
	var answer=document.getElementById("answer"+num);
	if(answer.style.visibility=="collapse"){
		answer.style.visibility="visible";
		document.getElementById("btn"+num).innerHTML="▲";
	}else{
		answer.style.visibility="collapse";
		
		document.getElementById("btn"+num).innerHTML="▼";
	}
	
}


</script>
<style type="text/css">
.listtr:hover{
	background: #EAEAEA;
}
</style>
</head>
<body>
<div id="mainframe">
<jsp:include page="/WEB-INF/page/layout/header.jsp"></jsp:include>
	 <section class="container">
		<article>
			<div id = "content">
			<h2>|&nbsp;&nbsp;자주묻는 질문</h2>
				<div class="main" style="width: 700px; margin: 30px auto;">
					<table style="width: 700px; margin: 0px auto; border-spacing: 0; border-collapse: collapse;">
						<tr>
							<td colspan="3" style="text-align: center;">
								<button style="height:30px; width: 33%; background: white; " type="button" onclick="javascript:location.href='<%=cp%>/faq/faq_list.do?category=goods';">상품문의</button>
								<button style="height:30px; width: 32%; background: white; " type="button" onclick="javascript:location.href='<%=cp%>/faq/faq_list.do?category=delivery';" >배송문의</button>
								<button style="height:30px; width: 33%; background: white; " type="button" onclick="javascript:location.href='<%=cp%>/faq/faq_list.do?category=event';" >이벤트문의</button>
							</td>
						</tr>
						<c:forEach var="dto" items="${list}">
						<tr class="listtr" align="center" height="35" style="border-bottom: 1px solid #cccccc;"> 
							<td width="80px;"><img src="<%=cp%>/resource/image/qna.JPG" border="0" style="margin-top: 1px; width: 30px; height: 30px;"></td>
							<td width="500px;" align="left" style="padding-left: 10px;">
								${dto.subject}
							</td>
							<td><button id="btn${dto.num}" onclick="btn(${dto.num});" style="font-size: 20px; border: none; background: white;" >▼</button></td>
						</tr>
						<tr id="answer${dto.num}" align="left" height="35" style="visibility:collapse; border-bottom: 1px solid #cccccc;">
							<td colspan="3" style="padding:10px 80px 10px 80px; background: #F6F6F6;">${dto.content}
							<c:if test="${sessionScope.member.id=='admin'}">
								<p align="right">
									<button type="button" onclick="javascript:location.href='<%=cp%>/faq/faq_update.do?num=${dto.num}';">수정</button>
									<button type="button" onclick="javascript:location.href='<%=cp%>/faq/faq_delete_ok.do?num=${dto.num}';">삭제</button>
								</p>
							</c:if>
							</td>
						</tr>
						</c:forEach>
					</table>
					<c:if test="${sessionScope.member.id=='admin'}" >
						<p align="right">
							<button style="text-align:right; margin:0px 80px 10px 80px; " type="button" onclick="javacript:location.href='<%=cp%>/faq/faq_created.do'">글올리기</button>
						</p>
					</c:if>
				</div>
			</div>
		</article>
		<div class="sidebox">
			<div id="sidetitle">
				<h2 style="font-family: 'Do Hyeon', sans-serif;">|&nbsp;&nbsp;메뉴</h2>
			</div>
			<ul>
				<li><a href="<%=cp%>/faq/faq_list.do">-FAQ</a></li>
				<c:if test="${sessionScope.member.id!='admin'}">
					<li><a href="<%=cp%>/qna/qna_created.do">-문의하기</a></li>
					<li><a href="<%=cp%>/qna/qna_list.do">-문의내역</a></li>
				</c:if>
				<c:if test="${sessionScope.member.id=='admin'}">
					<li><a href="<%=cp%>/qna/answer_list.do">-고객문의내역</a></li>
				</c:if>
			</ul>
		</div>
	</section>
</div>
<jsp:include page="/WEB-INF/page/layout/footer.jsp"></jsp:include>
</body>
</html>