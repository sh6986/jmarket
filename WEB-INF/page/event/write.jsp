<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
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
<link rel="icon" href="data:;base64,iVBORw0KGgo=">
<link rel="stylesheet" href="<%=cp%>/resource/css/home.css" type="text/css">
<link rel="stylesheet" href="<%=cp%>/resource/css/sidemenu.css" type="text/css">
<link rel="stylesheet" href="<%=cp%>/resource/css/event.css" type="text/css">
<link href="https://fonts.googleapis.com/css2?family=Cute+Font&family=Jua&display=swap" rel="stylesheet">

<script type="text/javascript">
    function sendOk() {
        var f = document.eventForm;

    	var str = f.title.value;
        if(!str) {
            alert("제목을 입력하세요. ");
            f.title.focus();
            return;
        }

    	str = f.content.value;
        if(!str) {
            alert("내용을 입력하세요. ");
            f.content.focus();
            return;
        }

   		f.action="<%=cp%>/event/${mode}_ok.do";

        f.submit();
    }
</script>

</head>
<body>
<div id="mainframe">
	 <jsp:include page="/WEB-INF/page/layout/header.jsp"></jsp:include>
	 
	 <section class="container">
			<article>
				<div class="content">
						<article>
		<div class="content">
			<h3><span style="font-family: Webdings">2</span> 이벤트게시판 </h3>
		</div>
		
		<form name="eventForm" method="post">
			  <table style="width: 100%; margin: 20px auto 0px; border-spacing: 0px; border-collapse: collapse;">
			  <tr align="left" height="40" style="border-top: 1px solid #FF9090; border-bottom: 1px solid #FF9090;"> 
			      <td width="100" bgcolor="#FF9090" style="text-align: center;">제&nbsp;&nbsp;&nbsp;&nbsp;목</td>
			      <td style="padding-left:10px;"> 
			        <input type="text" name="title" maxlength="100" style="width: 95%;" value="${dto.title}">
			      </td>
			  </tr>
			
			  <tr align="left" height="40" style="border-bottom: 1px solid #FF9090;"> 
			      <td width="100" bgcolor="#FF9090" style="text-align: center;">작성자</td>
			      <td style="padding-left:10px;"> 
			          ${sessionScope.member.name}
			      </td>
			  </tr>
			
			  <tr align="left" style="border-bottom: 1px solid #FF9090;"> 
			      <td width="100" bgcolor="#FF9090" style="text-align: center; padding-top:5px;" valign="top">내&nbsp;&nbsp;&nbsp;&nbsp;용</td>
			      <td valign="top" style="padding:5px 0px 5px 10px;"> 
			        <textarea name="content" rows="12" style="width: 95%;">${dto.content}</textarea>
			      </td>
			  </tr>
			  </table>
			
			  <table style="width: 100%; border-spacing: 0px;">
			     <tr height="45"> 
			      <td align="center" >
			      <c:if test="${mode=='update'}">
			      	<input type="hidden" name="num" value="${dto.num}">
			      	<input type="hidden" name="page" value="${page}">
			      </c:if>
			        <button type="button" class="btn" onclick="sendOk();">${mode=='update'?'수정완료':'등록하기'}</button>
			        <button type="reset" class="btn">다시입력</button>
			        <button type="button" class="btn" onclick="javascript:location.href='<%=cp%>/event/list.do';">${mode=='update'?'수정취소':'등록취소'}</button>
			      </td>
			    </tr>
			  </table>
			</form>
		
	</article>
				</div>
			</article>
<aside>
	<div class="sidebox">
		<div id="sidetitle">
			<h2 style="font-family: 'Do Hyeon', sans-serif;">|&nbsp;&nbsp;메뉴</h2>
		</div>
		<ul>
			<li><a href="<%=cp%>/notice/list.do">-공지</a></li>
			<li><a href="<%=cp%>/event/list.do">-이벤트</a></li>
			<li><a href="<%=cp%>/report/list.do">-신고하기</a></li>
			<li><a href="<%=cp%>/faq/faq_list.do">-문의</a></li>
		</ul>
	</div>
</aside>
		</section>
	</div>
	<jsp:include page="/WEB-INF/page/layout/footer.jsp"></jsp:include>

</body>
</html>