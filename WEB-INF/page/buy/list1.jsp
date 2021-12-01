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
<link rel="stylesheet" href="<%=cp%>/resource/css/sblist.css" type="text/css">
<link rel="stylesheet" href="<%=cp%>/resource/css/sidemenu.css" type="text/css">
<link href="https://fonts.googleapis.com/css2?family=Cute+Font&family=Jua&display=swap" rel="stylesheet">
<script type="text/javascript">
function searchnow() {
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
				<div class="content" >
						<div class="bb">
							<div id="box" style="width:700px; ">
							<h2>| 구입중</h2><br>
						 	<form name="listForm" method="post">
						 	<input type="hidden" name="page" value="${page}">
							<input type="hidden" name="condition" value="${condition}">
							<input type="hidden" name="keyword" value="${keyword}">
							<ul class="row" style="border-top: 1px solid #999; color:black;"> 
							<li style="line-height: 95px;font-weight: bold;">사진</li>
							<li style= "width:320px; text-align: center; line-height: 95px; font-weight: bold;">상품명</li>
							<li style="width: 140px; line-height: 95px;font-weight: bold;">작성자</li>
							<li style="line-height: 95px;font-weight: bold;">작성일</li>
							<li style="line-height: 95px;font-weight: bold;">조회수</li>
							</ul>
<c:forEach var="dto" items="${list}">				
							<ul class="row" onclick="javascript:location.href='${articleUrl}&num=${dto.num}&now=${now}';">
							<li> <img class="photo" src="<%=cp%>/photo/buy/${dto.imageName}" onerror="this.src='<%=cp%>/resource/image/imgnull.jpg'" style="width: 70px; height: 78px;"></li>
							<li style= "width:320px; text-align: left;line-height: 95px;"><a>&nbsp;&nbsp;${dto.subject}</a></li>
							<li style="width: 140px;line-height: 95px;">${dto.id}</li>
							<li style="line-height: 95px;">${dto.created}</li>
							<li style="line-height: 95px;">${dto.views}</li>
							</ul>
</c:forEach>	
							<div align="center">
								<h4 style="text-align: center; width: 340px; margin: 0 50px; ">${dataCount==0?"등록된 게시물이 없습니다.":paging}</h4>
							</div>
							</form>
							<div style="margin-top: 10px; margin-left: 190px;">
								<form name="goWriteForm" method="post">
								<div style="float: right;">
									<c:if test="${sessionScope.member.id!=null}">
									<input type="button" value="글쓰기 " onclick="javascript:location.href='<%=cp%>/buy/write.do';">
									</c:if>
								</div>
								</form>
							<form name="searchForm" action="<%=cp%>/buy/list1.do" method="post">
			                  	<select name="condition" style="width: 85px; height: 23px; border-radius: 4px;">
			                  		<option value="subject"  ${condition=="subject"?"selected='selected'":"" }>제목</option>
			                  		<option value="id" 	 ${condition=="id"?"selected='selected'":"" }>작성자</option>
			                 		<option value="content"  ${condition=="content"?"selected='selected'":"" }>내용</option>
			               		   <option value="created"   ${condition=="created"?"selected='selected'":"" }>등록일</option>
			            		</select>
			            		<input type="text" name="keyword" value="${keyword}" style="width: 200px; height: 17px; border-radius: 4px;">
			           			<button type="button" class="btn" onclick="searchnow();" style="height: 24px; width: 62px; border: none; background: pink; border-radius: 5px;">검색</button>
			            	</form>
							</div>
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
					<li><a href="<%=cp%>/buy/list1.do">-구매중</a></li>
					<li><a href="<%=cp%>/buy/list2.do">-구매완료</a></li>
				</ul>
			</div>
			</aside>
		</section>
	</div>
	<jsp:include page="/WEB-INF/page/layout/footer.jsp"></jsp:include>
</body>
</html>