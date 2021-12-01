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
<link rel="stylesheet" href="<%=cp%>/resource/css/sidemenu.css" type="text/css">
<link rel="stylesheet" href="<%=cp%>/resource/css/report.css" type="text/css">
<link href="https://fonts.googleapis.com/css2?family=Cute+Font&family=Jua&display=swap" rel="stylesheet">
<script type="text/javascript">
function sendlogin() {
	var f = document.loginform;
	f.action = "<%=cp%>/user/login_ok.do";
	f.submit();
}

function searchList() {
	var f=document.searchForm;
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
					<h2>|&nbsp;&nbsp;신고게시판 </h2>
						<table class="table">
							<tr style="border-bottom: 1px solid silver;">
								<td class="num">번호</td>
								<td class="category">카테고리</td>
								<td class="tilte" style="text-align: left;">제목</td>
								<td class="writer">작성자</td>
								<td class="date">&nbsp;&nbsp;작성일자</td>
								<td class="count">조회수</td>
							</tr>
							<c:forEach var="dto" items="${list}">
								<tr class="tr-row">
								<td class="num"><a href="${articleUrl}&num=${dto.num}">${dto.listNum}</a></td>
								<td><a href="${articleUrl}&num=${dto.num}">[${dto.category}]</a></td>
								<td><a href="${articleUrl}&num=${dto.num}">${dto.title}</a></td>
								<td><a href="${articleUrl}&num=${dto.num}">${dto.name}</a></td>
								<td><a href="${articleUrl}&num=${dto.num}">${dto.created}</a></td>
								<td class="v"><a href="${articleUrl}&num=${dto.num}">${dto.views}</a></td>
								</tr>
							</c:forEach>	
						</table>
						
						<table style="width: 100%; margin: 0px auto; border-spacing: 0px;">
			   			<tr height="35">
							<td align="center">
			       			 	${dataCount==0?"둥록된 게시물이 없습니다." : paging}
							</td>
			   			</tr>
						</table>
						
						<table style="width: 100%; margin: 10px auto; border-spacing: 0px;">
			   				<tr height="40">
			     			 <td align="left" width="100">
			         			<button type="button" class="btn" onclick="javascript:location.href='<%=cp%>/report/list.do';">새로고침</button>
			     			 </td>
			      			<td align="center">
			         		 <form name="searchForm" action="<%=cp%>/report/list.do" method="post">
			              		<select name="condition" class="selectField">
			                  		<option value="title" ${condition=="title" ? " selected='selected' ": ""}>제목</option>
			                 		<option value="name" ${condition=="name" ? " selected='selected' ": ""}>작성자</option>
			                  		<option value="content" ${condition=="content" ? " selected='selected' ": ""}>내용</option>
			                  		<option value="created" ${condition=="created" ? " selected='selected' ": ""}>작성일자</option>
			            		</select>
			            		<input type="text" name="keyword" value="${keyword}">
			            			<button type="button" class="btn" onclick="searchList()">검색</button>
			        		</form>
			      			</td>
			      			<td align="right" width="100">
			          			<button type="button" class="btn" onclick="javascript:location.href='<%=cp%>/report/write.do';">글올리기</button>
			     		    </td>
			   			 </tr>
					 </table>
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