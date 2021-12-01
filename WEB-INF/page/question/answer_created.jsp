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
function sendOk(){
	var f=document.qnaForm;
	f.action='<%=cp%>/qna/answer_created_ok.do';
	f.submit();
}


</script>
</head>
<body>
<div id="mainframe">
<jsp:include page="/WEB-INF/page/layout/header.jsp"></jsp:include>
	 <section class="container">
		<article>
			<div id = "content" >
			<h2>|&nbsp;&nbsp;문의하기</h2>
				<div class="main" style="width: 700px; margin: 30px auto;">
				<form name="qnaForm" method="post">
					<table style="width: 100%; border-spacing: 0; border-collapse: collapse;">
					  <tbody id="tbody">
					  </tbody>
					  <tr align="left" height="35" style="border-top: 1px solid #cccccc; border-bottom: 1px solid #cccccc;"> 
					      <th align="center" bgcolor="#eeeeee" style="color: #787878;">문의날짜</th>
					      <td style="padding: 0px 10px 0px 10px;">
					      	${dto.created}
						  </td>
						  <th align="center" bgcolor="#eeeeee" style="color: #787878;">아이디</th>
						  <td style="padding: 0px 10px 0px 10px;">
						  	${dto.id}
						  </td>
					  </tr>
					  <tr align="left" height="35" style="border-top: 1px solid #cccccc; border-bottom: 1px solid #cccccc;"> 
					      <th align="center" bgcolor="#eeeeee" style="color: #787878;">문의제목</th>
					      <td colspan="3" style="padding: 0px 10px 0px 10px;">
					      	[${dto.category=='goods'?('상품문의'):(dto.category=='delivery'?'배송문의':'이벤트문의')}]${dto.subject}
						  </td>
					  </tr>
					  <tr align="left" height="35" style="border-top: 1px solid #cccccc; border-bottom: 1px solid #cccccc;"> 
					      <th align="center" bgcolor="#eeeeee" style="color: #787878;">문의내용</th>
					      <td colspan="3" style="padding: 0px 10px 0px 10px;">
					      	${dto.content}
					      </td>
					  </tr>
					  <tr align="left" height="35" style="border-top: 1px solid #cccccc; border-bottom: 1px solid #cccccc;"> 
					      <th align="center" bgcolor="#eeeeee" style="color: #787878;">답변내용</th>
					      <td colspan="3" style="padding: 0px 10px 0px 10px;">
					      	<textarea name="an_content" rows="12" class="boxTA" style="width: 95%;"></textarea>
					      </td>
					  </tr>
					</table>
					<input type="hidden" name="dtonum" value="${dto.num}">
					<p align="center">
						<button type="button" onclick="sendOk();">문의하기 등록</button>
						<button type="button" onclick="javascript:location.href='<%=cp%>/qna/answer_list.do';">입력취소</button>
					</p>
				</form>
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