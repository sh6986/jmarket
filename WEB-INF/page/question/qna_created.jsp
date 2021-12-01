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
	f.action='<%=cp%>/qna/qna_${mode}_ok.do';
	f.submit();
}

window.onload = function()
{
	var f=document.listForm;
   	f.category.value='${dto.category}';
  // 	f.content_up.innerHTML='${dto.content}';
}


function catechange(){
	var objs=document.getElementById("tbody");

	var s="";
	var n=0;
	<c:forEach var="dto" items="${list}">
		if("${dto.category}"==document.getElementById("category").value&&n<5){  //연관faq문의사항은 5개까지만 표시
			if(n!=0){
				s+="<tr align='center'  height='35' style='border-top: 1px solid #cccccc; border-bottom: 1px solid #cccccc;'>"
			}
			s+="<td class='listtr' style='padding: 0px 10px 0px 10px;' align='left'><a href='<%=cp%>/faq/faq_list.do?category="+document.getElementById("category").value+"'>${dto.subject}</a></td>";
			s+="</tr>"; 
			n++;
		}
	</c:forEach>
	
	s="<tr  align='center'  height='35' style='border-top: 1px solid #cccccc; border-bottom: 1px solid #cccccc;'>"+
	"<th rowspan='"+n+"' bgcolor='#eeeeee' style='color: #787878;' >연관 FAQ</th>"+s
	
	//선택한카테고리에 맞는 질문이 없을시
	if(n==0){
		s="";
	}
	
	objs.innerHTML=s;
	
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
			<div id = "content" >
			<h2>|&nbsp;&nbsp;문의하기</h2>
				<div class="main" style="width: 700px; margin: 30px auto;">
				<form name="qnaForm" method="post">
					<table style="width: 100%; border-spacing: 0; border-collapse: collapse;">
					  <tr align="left" height="35" style="border-top: 1px solid #cccccc; border-bottom: 1px solid #cccccc;"> 
					      <th width="100" align="center" bgcolor="#eeeeee" style="color: #787878;" >카테고리</th>
					      <td style="padding: 0px 10px 0px 10px;" >
					      	<select style="width: 200px;" id="category" name="category" onchange="catechange();">
					      		<option>선택</option>
					      		<option value="goods" ${dto.category=='goods'?"selected='selected' " : ""  }>상품문의</option>
					      		<option value="delivery"${dto.category=='delivery'?"selected='selected' " : ""  }>배송문의</option>
					      		<option value="event" ${dto.category=='event'?"selected='selected' " : ""  }>이벤트문의</option>
					      	</select>
					      </td>
					  </tr>
					  <tbody id="tbody" align="left">
					  </tbody>
					  <tr align="left" height="35" style="border-top: 1px solid #cccccc; border-bottom: 1px solid #cccccc;"> 
					      <th align="center" bgcolor="#eeeeee" style="color: #787878;">문의제목</th>
					      <td style="padding: 0px 10px 0px 10px;">
					      	<input style="width: 300px;" type="text" name="subject" value="${dto.subject}" >
						  </td>
					  </tr>
					  <tr align="left" height="35" style="border-top: 1px solid #cccccc; border-bottom: 1px solid #cccccc;"> 
					      <th align="center" bgcolor="#eeeeee" style="color: #787878;">문의내용</th>
					      <td style="padding: 0px 10px 0px 10px;">
					      	<textarea id="content_up" name="content" rows="12" class="boxTA" style=" width: 95%;">${dto.content}</textarea>
					      </td>
					  </tr>
					</table>
					<c:if test="${mode=='update'}">
						<input type="hidden" name="num" value="${dto.num}">
					</c:if>
					<p align="center">
						<button type="button" onclick="sendOk();">${mode=='update'?'수정완료':'문의하기 등록'}</button>
						<button type="button" onclick="javascript:location.href='<%=cp%>/qna/qna_list.do';">입력취소</button>
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