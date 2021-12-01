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
function searchList() {
	var f=document.searchForm;
	f.submit();
}
</script>

</head>
<body>

<div id="mainframe">
	<jsp:include page="/WEB-INF/page/layout/header.jsp"/>
	
	<section class="container">
		<article>
			<div class="content" >
				<div class="all">
					<div style="width: 730px;">
						<h2>| 공지사항</h2><br>
					</div>
					<div style="width: 730px;">
					
						<table style="width: 100%; border-spacing: 0; border-collapse: collapse;">
						  <tr align="center" bgcolor="#FFDDDD" height="35" style="border-top: 3px solid #FF6C6C; border-bottom: 1px solid #FF6C6C;"> 
						      <th width="60" style="color: #787878;">번호</th>
						      <th style="color: #787878;">제목</th>
						      <th width="100" style="color: #787878;">작성자</th>
						      <th width="80" style="color: #787878;">작성일</th>
						      <th width="60" style="color: #787878;">조회수</th>
						      <th width="50" style="color: #787878;">다운</th>
						  </tr>
						 
						 <c:forEach var="dto" items="${listNotice}">
						  <tr align="center" bgcolor="#ffffff" height="35" style="border-bottom: 1px solid #FF6C6C;"> 
						      <td>
						           <span style="display: inline-block;padding:1px 3px; background: #ED4C00;color: #FFFFFF; ">공지</span>
						      </td>
						      <td align="left" style="padding-left: 10px;">
						           <a href="${articleUrl}&num=${dto.num}">${dto.title}</a>
						      </td>
						      <td>${dto.name}</td>
						      <td>${dto.created}</td>
						      <td>${dto.hitcount}</td>
						      <td>
									<c:if test="${not empty dto.afilename}">
									      <a href="<%=cp%>/notice/download.do?num=${dto.num}"><img src="<%=cp%>/resource/image/disk.gif" border="0" style="margin-top: 1px;"></a>
									</c:if>
						      </td>
						  </tr>
						</c:forEach> 
			
						 <c:forEach var="dto" items="${list}">
						  <tr align="center" bgcolor="#ffffff" height="35" style="border-bottom: 1px solid #FF6C6C;">
						      <td>${dto.listnum}</td>
						      <td align="left" style="padding-left: 10px;">
						           <a href="${articleUrl}&num=${dto.num}">${dto.title}</a>
						           <c:if test="${dto.gap<1}"><img src="<%=cp%>/resource/image/new.gif"></c:if>
						      </td>
						      <td>${dto.name}</td>
						      <td>${dto.created}</td>
						      <td>${dto.hitcount}</td>
						      <td>
									<c:if test="${not empty dto.afilename}">
									      <a href="<%=cp%>/notice/download.do?num=${dto.num}"><img src="<%=cp%>/resource/image/disk.gif" border="0" style="margin-top: 1px;"></a>
									</c:if>
						      </td>
						  </tr>
						</c:forEach> 
			
						</table>
						 
						<table style="width: 100%; margin: 0px auto; border-spacing: 0px;">
						   <tr height="35">
							<td align="center">
						        ${datacount!=0?paging:"등록된 게시물이 없습니다."}
							</td>
						   </tr>
						</table>
						
						<table style="width: 100%; margin: 10px auto; border-spacing: 0px;">
						   <tr height="40">
						      <td align="left" width="100">
						          <button style="background: #FFDDDD" type="button" class="btn" onclick="javascript:location.href='<%=cp%>/notice/list.do';">새로고침</button>
						      </td>
						      <td align="center">
						          <form name="searchForm" action="<%=cp%>/notice/list.do" method="post">
						              <select name="condition" class="selectField">
						                  <option value="title" ${condition=="title"?"selected='selected'":"" }>제목</option>
						                  <option value="name" ${condition=="name"?"selected='selected'":"" }>작성자</option>
						                  <option value="content" ${condition=="content"?"selected='selected'":"" }>내용</option>
						                  <option value="created" ${condition=="created"?"selected='selected'":"" }>등록일</option>
						            </select>
						            <input type="text" name="keyword" class="boxTF" value="${keyword}">
						            <input type="hidden" name="rows" value="${rows}">
						            <button type="button" class="btn" onclick="searchList()" style="background: #FFDDDD">검색</button>
						        </form>
						      </td>
						      <td align="right" width="100">
								<c:if test="${sessionScope.member.id == 'admin' }">			      
						          <button type="button" class="btn" onclick="javascript:location.href='<%=cp%>/notice/created.do?rows=${rows}'; " style="background: #FFDDDD">글올리기</button>
						        </c:if>
						      </td>
						   </tr>
						</table>
					
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