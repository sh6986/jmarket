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
<link rel="icon" href="data:;base64,iVBORw0KGgo=">
<link rel="stylesheet" href="<%=cp%>/resource/css/home.css"
	type="text/css">
<link rel="stylesheet" href="<%=cp%>/resource/css/menu.css"
	type="text/css">
<link rel="stylesheet" href="<%=cp%>/resource/css/sale-list.css"
	type="text/css">
<link rel="stylesheet" href="<%=cp%>/resource/css/sidemenu.css"
	type="text/css">
<link
	href="https://fonts.googleapis.com/css2?family=Cute+Font&family=Jua&display=swap"
	rel="stylesheet">
<script type="text/javascript">
<c:if test="${sessionScope.member.id=='admin'}">
	function deleteNotice(num) {
		if(confirm("게시물을 삭제 하시겠습니까?")){
		var url="<%=cp%>/notice/delete.do?num="+num+"&${query}";
		location.href=url;		
	}
	
}
	</c:if>
</script>
</head>
<body>

	<div id="mainframe">
		<jsp:include page="/WEB-INF/page/layout/header.jsp" />

		<section class="container">
			<article>
				<div class="content">
					<div class="all">
						<div style="width: 700px;">
							<h2>| 공지사항</h2>
							<br>
						</div>
						<div style="width: 700px;">

							<table
								style="width: 100%; margin: 20px auto 0px; border-spacing: 0px; border-collapse: collapse;">
								<tr height="35"
									style="border-top: 3px solid #FF6C6C; border-bottom: 1px solid #FF6C6C; background:#FFDDDD">
									<td colspan="2" align="center">${dto.title}</td>
								</tr>

								<tr height="35" style="border-bottom: 2px solid #FF6C6C;">
									<td width="50%" align="left" style="padding-left: 5px;">
										이름 : ${dto.name}</td>
									<td width="50%" align="right" style="padding-right: 5px;">
										${dto.created} | 조회 ${dto.hitcount}</td>
								</tr>

								<tr style="border-bottom: 2px solid #FF6C6C;">
									<td colspan="2" align="left" style="padding: 10px 5px;"
										valign="top" height="200">${dto.content}</td>
								</tr>

								<tr height="35" style="border-bottom: 1px solid #FF6C6C;background:#FFDDDD">
									<td colspan="2" align="left" style="padding-left: 5px;">
										첨&nbsp;&nbsp;부 : <c:if test="${not empty dto.afilename}">
											<a href="<%=cp%>/notice/download.do?num=${dto.num}">${dto.bfilename}</a>
		                    (<fmt:formatNumber value="${dto.filesize/1024}"
												pattern="0.00" /> Kbyte)
		                                    </c:if>
									</td>
								</tr>

								<tr height="35" style="border-bottom: 1px solid #FF6C6C;">
									<td colspan="2" align="left" style="padding-left: 5px;">
										이전글 : <c:if test="${not empty preReadDto}">
											<a
												href="<%=cp%>/notice/article.do?${query}&num=${preReadDto.num}">${preReadDto.title}</a>
										</c:if>
									</td>
								</tr>

								<tr height="35" style="border-bottom: 3px solid #FF6C6C;background:#FFDDDD">
									<td colspan="2" align="left" style="padding-left: 5px;">
										다음글 : <c:if test="${not empty nextReadDto}">
											<a
												href="<%=cp%>/notice/article.do?${query}&num=${nextReadDto.num}">${nextReadDto.title}</a>
										</c:if>
									</td>
								</tr>

							</table>

							<table
								style="width: 100%; margin: 0px auto 20px; border-spacing: 0px;">
								<tr height="45">
									<td width="300" align="left"><c:if
											test="${sessionScope.member.id==dto.id}">
											<button type="button" class="btn"
												onclick="javascript:location.href='<%=cp%>/notice/update.do?num=${dto.num}&page=${page}&rows=${rows}';" style="background: #FFDDDD">수정</button>
										</c:if> <c:if
											test="${sessionScope.member.id==dto.id || sessionScope.member.id=='admin'}">
											<button type="button" class="btn"
												onclick="deleteNotice('${dto.num}'); " style="background: #FFDDDD">삭제</button>
										</c:if></td>

									<td align="right">
										<button type="button" class="btn"
											onclick="javascript:location.href='<%=cp%>/notice/list.do?${query}';" style="background: #FFDDDD">리스트</button>
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