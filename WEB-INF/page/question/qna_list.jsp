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
function subclick(num){
	var answer=document.getElementById("answer"+num);
	if(answer.style.visibility=="collapse"){
		answer.style.visibility="visible";
	}else{
		answer.style.visibility="collapse";
	}
}

function sendOk(){
	var f=document.listForm;
	f.submit();
}


//날짜선택박스 초기값 오늘날짜로 준다.
window.onload = function()
{
	resetday();
}

function resetday(){
	var f=document.listForm;
    f.year.value='${year}';
    f.month.value='${month}';
    f.day.value='${day}';
    f.year2.value='${year2}';
    f.month2.value='${month2}';
    f.day2.value='${day2}';

}

function resetday2(){
	var f=document.listForm;
	f.year2.value=f.year.value;
	f.month2.value=f.month.value;
	f.day2.value=f.day.value;
}


function monthch(){
	var f=document.listForm;
	var m=f.month.value<10?"0"+f.month.value:f.month.value;
	var minmonth=${minmonth}<10?"0"+${minmonth}:${minmonth};
	var calmonth=${calmonth}<10?"0"+${calmonth}:${calmonth};
	if(""+f.year.value+m<""+${minyear}+${minmonth}){
		alert("문의내역조회는 6개월전까지만 가능합니다.");
		resetday();
	}else if(""+f.year.value+m>""+${calyear}+calmonth)	{
		alert("시작날짜를 다시 설정해 주세요");
		resetday();
	}
}

function daych(){
	//선택한달이 현재달과 같을시->현재날짜 이후 불가능
	//선택한달이 최소달과 같을시->최소날짜이전 불가능
	var f=document.listForm;
	if(f.month.value==${calmonth}){
		if(f.day.value>${calday}){
			alert("시작날짜를 다시 설정해주세요");
			resetday();
		}
	}else if(f.month.value==${minmonth}){
		if(f.day.value<${minday}){
			alert("문의내역조회는 6개월전까지만 가능합니다.");
			resetday();
		}
	}
	resetday2();	
}


function yearch2(){
	//시작날짜로 설정한 년도보다 작으면x
	var f=document.listForm;
	if(f.year.value>f.year2.value){
		alert("시작날짜를 다시 설정해주세요");
		resetday2();	
	}
}

function monthch2(){
	//년, 월 합쳤을때 시작날짜 년, 월 합친것보다 작으면x
	var f=document.listForm;
	var m=f.month.value<10?"0"+f.month.value:f.month.value;
	var m2=f.month2.value<10?"0"+f.month2.value:f.month2.value;
	var calmonth=${calmonth}<10?"0"+${calmonth}:${calmonth};
	if(""+f.year.value+m>""+f.year2.value+m2){
		alert("시작날짜를 다시 설정해주세요");
		resetday2();	
	}
	//현재년, 월 합친것보다 많으면 x
	if(""+${calyear}+calmonth<""+f.year2.value+m2){
		alert("오늘날짜까지 조회 가능합니다.");
		resetday2();	
	}
}

function daych2(){
	//년, 월, 일 합쳤을대 시작날자 년, 월, 일보다 작으면 x, 오늘을 넘기면 x
	var f=document.listForm;
	var m=f.month.value<10?"0"+f.month.value:f.month.value;
	var m2=f.month2.value<10?"0"+f.month2.value:f.month2.value; 
	var calmonth=${calmonth}<10?"0"+${calmonth}:${calmonth};
	var d=f.day.value<10?"0"+f.day.value:f.day.value;
	var d2=f.day2.value<10?"0"+f.day2.value:f.day2.value;
	var calday=${calday}<10?"0"+${calday}:${calday};
	if(""+f.year.value+m+d>""+f.year2.value+m2+d2){
		alert("시작날짜를 다시 설정해주세요");
		resetday2();	
	}
	//현재년, 월 합친것보다 많으면 x
	if(""+${calyear}+calmonth+calday<""+f.year2.value+m2+d2){
		alert("오늘날짜까지 조회 가능합니다.");
		resetday2();	
	}
}

function month1(){
	var f=document.listForm;
	f.year.value='${month1year}';
	f.month.value='${month1month}';
	f.day.value='${month1day}';
	f.year2.value='${calyear}';
	f.month2.value='${calmonth}';
	f.day2.value='${calday}';
}

function totalday(){
	var f=document.listForm;
	f.year.value='${minyear}';
	f.month.value='${minmonth}';
	f.day.value='${minday}';
	f.year2.value='${calyear}';
	f.month2.value='${calmonth}';
	f.day2.value='${calday}';
}

</script>
<style type="text/css">
.subject:hover{
	text-decoration: underline;
}

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
			<h2>|&nbsp;&nbsp;나의 문의내역</h2>
			<form name="listForm" method="post" action="<%=cp%>/qna/${mode}_list.do">
				<div class="main" style="width: 700px; margin: 30px auto;">
					<p align="right">총 문의건:${totalData}건&nbsp;|&nbsp;답변완료:${answerData}건&nbsp;|&nbsp;접수완료:${questionData}건</p>
					<table style="width: 100%; border-spacing: 0; border-collapse: collapse;">
					  <tr align="center" bgcolor="#ffffff"  height="35" style="border-top: 1px solid #cccccc; border-bottom: 1px solid #cccccc;"> 
					      <th width="100" bgcolor="#eeeeee" style="color: #787878;">문의 접수일</th>
					      <td>
					      	<select id="year" name="year">
					      		<c:forEach var="n" begin="${minyear}" end="${calyear}">
					      			<option value="${n}">${n}</option>
					      		</c:forEach>
					      	</select>
					      	년
					      	<select id="month" onchange="monthch();" name="month">
					      		<c:forEach var="n" begin="1" end="12">
					      			<option value="${n}">${n}</option>
					      		</c:forEach>
					      	</select>
					      	월
					      	<select id="day" onchange="daych();" name="day">
					      		<c:forEach var="n" begin="1" end="31">
					      			<option value="${n}">${n}</option>
					      		</c:forEach>
					      	</select>
					      	일~
					      	<select id="year2" onchange="yearch2();" name="year2">
					      		<c:forEach var="n" begin="${minyear}" end="${calyear}">
					      			<option value="${n}">${n}</option>
					      		</c:forEach>
					      	</select>
					      	년
					      	<select id="month2" onchange="monthch2();" name="month2"> 
					      		<c:forEach var="n" begin="1" end="12">
					      			<option value="${n}">${n}</option>
					      		</c:forEach>
					      	</select>
					      	월
					      	<select id="day2" onchange="daych2();" name="day2">
					      		<c:forEach var="n" begin="1" end="31">
					      			<option value="${n}">${n}</option>
					      		</c:forEach>
					      	</select>
					      	일
					      </td>
					      <td width="150">
					      	<button type="button" onclick="month1();">1개월</button>
					      	<button type="button" onclick="totalday();">전체</button>
					      	<button type="button" style="background: silver;" onclick="sendOk();" >조회</button>
					      </td>
					  </tr>
					  
					</table>
					<br>
					
					<table style="width: 100%; border-spacing: 0; border-collapse: collapse;">
					  <tr align="center" bgcolor="#eeeeee" height="35" style="border-top: 1px solid #cccccc; border-bottom: 1px solid #cccccc;"> 
					      <th width="60" style="color: #787878;">번호</th>
					      <th width="80" style="color: #787878;">카테고리</th>
					      <th style="color: #787878;">문의제목</th>
					      <c:if test="${mode=='answer'}">
					      	<th width="60" style="color: #787878;">아이디</th>
					      </c:if>
					      <th width="80" style="color: #787878;">문의일</th>
					      <th width="80" style="color: #787878;">답변일</th>
					      <th width="80" style="color: #787878;">
						      <select name="status" style="border: none; background: #eeeeee;" onchange="sendOk();">
						      		<option value="-1">처리상태</option>
						      		<option value="2" ${status=="2"?"selected='selected'":"" }>전체</option>
						      		<option value="0" ${status=="0"?"selected='selected'":"" }>접수완료</option>
						      		<option value="1" ${status=="1"?"selected='selected'":"" }>답변완료</option>
						      </select>
					      </th>
					  </tr>
					  <c:forEach var="dto" items="${list}">
						  <tr class="listtr" align="center" bgcolor="#ffffff" height="35" style="border-bottom: 1px solid #cccccc;"> 
						      <td>${dto.listNum}</td>
						      <td>${dto.category=='goods'?('상품문의'):(dto.category=='delivery'?'배송문의':'이벤트문의')}</td>
						      <td align="left" style="padding-left: 10px;" class="subject" onclick="subclick(${dto.num});">${dto.subject}</td>
						      <c:if test="${mode=='answer'}">
						      	<td>${dto.id}</td>
						      </c:if>
						      <td>${dto.created}</td>
						      <td>${dto.an_created}</td>
						      <td>${dto.status==0?"접수완료":"답변완료"}</td>
						  </tr>
						  <tr id="answer${dto.num}" height="35" style="visibility:collapse; border-bottom: 1px solid #cccccc;" >
							  <td colspan="${mode=='answer'?7:6}" style="background: #F6F6F6; padding:0px 20px 20px 20px; ">
								<input type="hidden" name="num" value="${dto.num}">
								<p ><b style="color: gray;">Q.</b><b>${dto.subject}</b>&nbsp;${dto.created}	</p>
								<p style="padding:0px 0px 0px 10px;">${dto.content}</p>	
								<c:if test="${dto.status==0&&sessionScope.member.id!='admin'}">
									<button type="button" onclick="javascript:location.href='<%=cp%>/qna/qna_update.do?num=${dto.num}';">수정</button>
								</c:if>
								<c:if test="${dto.status==1}">
									<hr style="border: 1px dashed gray;" >
									<p ><b style="color: blue;">A.</b><b>[자몽마켓]고객님 문의에 답변드립니다.</b>&nbsp;2020-05-21	</p>
									<p style="padding:0px 0px 0px 10px;">${dto.an_content}</p>
								</c:if>
								<c:if test="${dto.status==0&&sessionScope.member.id=='admin'}">
									<button type="button" onclick="javascript:location.href='<%=cp%>/qna/answer_created.do?num=${dto.num}';">답변작성</button>
								</c:if>
								<c:if test="${sessionScope.member.id=='admin'}">
									<button type="button" onclick="javascript:location.href='<%=cp%>/qna/answer_delete_ok.do?num=${dto.num}';" >삭제</button>
								</c:if>
							  </td>
						  </tr>
					  </c:forEach>
					</table>
					
					<table style="width: 100%; margin: 0px auto; border-spacing: 0px;">
					   <tr height="35">
						<td align="center">
					        ${dataCount==0?"등록된 게시물이 없습니다.":paging}
						</td>
					   </tr>
					</table>
				</div>
				
				</form>
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