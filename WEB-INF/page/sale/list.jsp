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
<link rel="stylesheet" href="<%=cp%>/resource/css/home.css" type="text/css">
<link rel="stylesheet" href="<%=cp%>/resource/css/menu.css" type="text/css">
<link rel="stylesheet" href="<%=cp%>/resource/css/sale-list.css" type="text/css">
<link rel="stylesheet" href="<%=cp%>/resource/css/sidemenu.css" type="text/css">

<link href="https://fonts.googleapis.com/css2?family=Cute+Font&family=Jua&display=swap" rel="stylesheet">
<script type="text/javascript">

function searchList() {
	var f = document.searchForm;
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
				<div class="bb">
		<div id="box" style="width:700px; ">
		<h2>| 판매중</h2><br>
<ul class="row" style="border-top: 1px solid #999; color:black;"> 
<li style="line-height: 95px;font-weight: bold;">사진</li>
<li style= "width:320px; text-align: center;line-height: 95px;font-weight: bold;">상품명</li>
<li style="width: 140px;line-height: 95px;font-weight: bold;">작성자</li>
<li style="line-height: 95px;font-weight: bold;">작성일</li>
<li style="line-height: 95px;font-weight: bold;">조회수</li>
</ul>

<c:forEach var="dto" items="${list}">
<ul class="row" onclick="javascript:location.href='${articleUrl}&num=${dto.num}&now=${now}';">
<li> <img class="photo" src="<%=cp%>/photo/sale/${dto.fileName1}" style="width: 70px; height: 78px;"></li>
<li style= "width:320px; text-align: left; line-height: 95px; text-align: center;"> <a href="${articleUrl}&num=${dto.num}&now=${now}">${dto.subject}</a></li>
<li style="width: 140px;line-height: 95px;">${dto.name}</li>
<li style="line-height: 95px;">${dto.created}</li>
<li style="line-height: 95px;">${dto.hitCount}</li>
</ul>
</c:forEach> 

	<table style="width: 100%; margin: 0px auto; border-spacing: 0px;">
			   <tr height="35">
				<td align="center">
			        ${dataCount!=0?paging:"등록된 게시물이 없습니다."}
				</td>
			   </tr>
	</table>
	
	 <form name="searchForm" action="<%=cp%>/sale/list.do" method="post">
	 <input type="hidden" name="keyword" value="${keyword}">
	 <input type="hidden" name="condition" value="${condition}">
	  <table style="width: 100%; margin: 10px auto; border-spacing: 0px;">
			   <tr height="40">
			       
			      <td align="center" style="padding-left: 120px;">
			         
			              <select name="condition" class="selectField" >
			                  <option value="subject"     ${condition=="subject"?"selected='selected'":"" }>제목</option>
			                  <option value="name"    ${condition=="name"?"selected='selected'":"" }>작성자</option>
			                  <option value="content"     ${condition=="content"?"selected='selected'":"" }>내용</option>
			                  <option value="created"     ${condition=="created"?"selected='selected'":"" }>등록일</option>
			            </select>
			            <input type="text" name="keyword" value="${keyword}">
			            <button type="button" class="btn" onclick="searchList()">검색</button>
			       
			      </td>
			      <td align="left" style="width: 70px;">
			          <button type="button" class="btn" onclick="javascript:location.href='<%=cp%>/sale/list.do';">새로고침</button>
			      </td>
			     <td align="right" style="width: 70px;">
			          <button type="button" class="btn" onclick="javascript:location.href='<%=cp%>/sale/write.do';">글올리기</button>
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
			<li><a href="<%=cp%>/sale/list.do">-판매중</a></li>
			<li><a href="<%=cp%>/sale/list2.do">-판매완료</a></li>
		</ul>
	</div>
</aside>
		</section>
	
</div>
		
	<jsp:include page="/WEB-INF/page/layout/footer.jsp"></jsp:include>

</body>
</html>