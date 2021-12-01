<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
<link rel="stylesheet" href="<%=cp%>/resource/css/menu.css" type="text/css">
<link rel="stylesheet" href="<%=cp%>/resource/css/sale-list.css" type="text/css">
<link rel="stylesheet" href="<%=cp%>/resource/css/sidemenu.css" type="text/css">
<link href="https://fonts.googleapis.com/css2?family=Cute+Font&family=Jua&display=swap" rel="stylesheet">

<script type="text/javascript">
function sendOk() {
    var f = document.boardForm;

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

		f.action="<%=cp%>/notice/${mode}_ok.do";

    f.submit();
}

<c:if test="${mode=='update'}">
function deleteFile(num) {
	  var url="<%=cp%>/notice/deleteFile.do?num="+num+"&page=${page}&rows=${rows}";
	  location.href=url;
}
</c:if>
</script>
</head>
<body>

<div id="mainframe">
	<jsp:include page="/WEB-INF/page/layout/header.jsp"/>
	
	<section class="container">
		<article>
			<div class="content" >
				<div class="all">
					<div style="width: 600px;">
						<h2>| 공지사항</h2><br>
					</div>
					<div style="width: 600px;">
					
						<form name="boardForm" method="post" enctype="multipart/form-data">
						  <table style="width: 100%; margin: 20px auto 0px; border-spacing: 0px; border-collapse: collapse;">
						  <tr align="left" height="40" style="border-top: 3px solid #FF6C6C; border-bottom: 1px solid #FF6C6C;"> 
						      <td width="100" bgcolor="#FFDDDD" style="text-align: center;">제&nbsp;&nbsp;&nbsp;&nbsp;목</td>
						      <td style="padding-left:10px;"> 
						        <input type="text" name="title" maxlength="100" class="boxTF" style="width: 95%;" value="${dto.title}">
						      </td>
						  </tr>
						
						  <tr align="left" height="40" style="border-bottom: 1px solid #FF6C6C;"> 
						      <td width="100" bgcolor="#FFDDDD" style="text-align: center;">공지여부</td>
						      <td style="padding-left:10px;"> 
						          <input type="checkbox" name="notice" value="1" ${dto.notice==1 ? "checked='checked' ":"" }> 공지
						      </td>
						  </tr>
			
						  <tr align="left" height="40" style="border-bottom: 1px solid #FF6C6C;"> 
						      <td width="100" bgcolor="#FFDDDD" style="text-align: center;">작성자</td>
						      <td style="padding-left:10px;"> 
						          ${sessionScope.member.name}
						      </td>
						  </tr>
						
						  <tr align="left" style="border-bottom: 1px solid #FF6C6C;"> 
						      <td width="100" bgcolor="#FFDDDD" style="text-align: center; padding-top:5px;" valign="top">내&nbsp;&nbsp;&nbsp;&nbsp;용</td>
						      <td valign="top" style="padding:5px 0px 5px 10px;"> 
						        <textarea name="content" rows="12" class="boxTA" style="width: 95%;">${dto.content}</textarea>
						      </td>
						  </tr>
						  
						  <tr align="left" height="40" style="border-bottom: 2px solid #FF6C6C;"> 
						      <td width="100" bgcolor="#FFDDDD" style="text-align: center;">첨부</td>
						      <td style="padding-left:10px;"> 
						          <input type="file" name="upload" class="boxTF" size="53" style="height: 25px;">
						      </td>
						  </tr>
						  
						  <c:if test="${mode=='update' }">
							  <tr align="left" height="40" style="border-bottom: 1px solid #FF6C6C;"> 
							      <td width="100" bgcolor="#FFDDDD" style="text-align: center;">첨부된파일</td>
							      <td style="padding-left:10px;"> 
							         <c:if test="${not empty dto.afilename}">
							             ${dto.bfilename}
							             | <a href="javascript:deleteFile('${dto.num}');" >삭제</a>
							         </c:if>     
							      </td>
							  </tr>
						  </c:if>
						  
						  </table>
						
						  <table style="width: 100%; border-spacing: 0px;">
						     <tr height="45"> 
						      <td align="center" >
						        <button type="button" class="btn" onclick="sendOk();" style="background: #FFDDDD">${mode=='update'?'수정완료':'등록하기'}</button>
						        <button type="reset" class="btn" style="background: #FFDDDD">다시입력</button>
						        <button type="button" class="btn" onclick="javascript:location.href='<%=cp%>/notice/list.do';" style="background: #FFDDDD">${mode=='update'?'수정취소':'등록취소'}</button>
						         <c:if test="${mode=='update'}">
						         	 <input type="hidden" name="num" value="${dto.num}">
						        	 <input type="hidden" name="page" value="${page}">
						        	 <input type="hidden" name="filesize" value="${dto.filesize}">
						        	 <input type="hidden" name="afilename" value="${dto.afilename}">
						        	 <input type="hidden" name="bfilename" value="${dto.bfilename}">
						        </c:if>
						        <input type="hidden" name="rows" value="${rows}">
						        
						      </td>
						    </tr>
						  </table>
						</form>
					
					</div>
				</div>
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