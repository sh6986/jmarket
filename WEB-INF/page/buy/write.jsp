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
<link rel="stylesheet" href="<%=cp%>/resource/css/sbwrite.css" type="text/css">
<link rel="stylesheet" href="<%=cp%>/resource/css/sidemenu.css" type="text/css">
<link href="https://fonts.googleapis.com/css2?family=Cute+Font&family=Jua&display=swap" rel="stylesheet">
<script type="text/javascript">
function sendbuy() {
    var f = document.writeForm;
    var str = f.subject.value;
    if(!str) {
        alert("제목을 입력하세요. ");
        f.subject.focus();
        return;
    }
    
    str = f.content.value;
    if(!str) {
        alert("내용을 입력하세요. ");
        f.content.focus();
        return;
    }
    
    str = f.price.value;
    if(!str) {
        alert("가격을 입력하세요. ");
        f.content.focus();
        return;
    }
    
    
    str = f.productname.value;
    if(!str) {
        alert("물품명을 입력하세요. ");
        f.content.focus();
        return;
    }
    
    
    f.action="<%=cp%>/buy/${state=='write'?'write':'update'}_ok.do";
    f.submit();
}
</script>
</head>
<body>
	<div id="mainframe">
	 <jsp:include page="/WEB-INF/page/layout/header.jsp"></jsp:include>
	 <form name="writeForm" method="post" enctype="multipart/form-data" >
	 <c:if test="${state=='update'}">
		 <input type="hidden" name="id" value="${dto.id}">
		 <input type="hidden" name="page"  value="${page}">
		 <input type="hidden" name="num" value="${dto.num}">
	 </c:if>
		<section class="container">
			<article>
				<div class="content">
					<div class="all">
						<br><br>
						<div class="name1">글 제목 <input name="subject" style="width: 550px; height: 25px; border-radius: 4px;" value="${dto.subject}"></div>
						<div class="img-upload">
							<c:if test="${state=='write'}">
								<input class="button" type="file" name="upload">
							</c:if>	
							<c:if test="${state=='update'}">
								<img class="pimg imgbox1" src="<%=cp%>/photo/buy/${dto.imageName}" style="margin: 0px;">
								<input class="button" type="file" name="upload" style="margin: 5px 0px 0px 5px; ">
							</c:if>	
						</div>
					
						<ul class="view">
							<li class="product">상품명 <input name="productname" style="width: 150px; height: 20px; border-radius: 4px;" value="${dto.productName}"> <li>
							<li class="price">가격 &nbsp;&nbsp;<input name="price" style="width: 150px; height: 20px; border-radius: 4px;" value="${dto.price}"> 원<br></li>
							<li class="list"><br>거래방법 : <select name="how" style="border-radius: 4px; height: 22px;">
									                 		<option value="post"    ${how=="post"?"selected='selected'":"" }>택배거래</option>
									                  		<option value="direct" 	${how=="direct"?"selected='selected'":"" }>직접거래</option>
									                  	    <option value="safety"  ${how=="safety"?"selected='selected'":"" }>안전거래</option>
			            								</select>
							 &nbsp;&nbsp;
							<span class="safe">안전거래 신청</span> 
							</li>
							<li class="list">배송방법 : 판매자와 직접 연락하세요</li>
							<li>&nbsp;</li>
							<li class="send"><span class="jmpay"> &nbsp; &nbsp;Pay</span> 수수료 없이 송금하기</li>
							<li>&nbsp;</li>
							<li class="ju">자몽페이 송금은 에스크로 기능이 제공되지 않으며 판매자에게 결제금액이 바로 전달되는 '직접거래'입니다.</li>
							<li class="warning">자몽마켓에 등록된 판매 물품과 내용을 개별 판매자가 등록한 것으로서, 
								자몽마켓은 등록을 위한 시스템만 제공하며 내용에 대하여 일체의 책임을 지지 않습니다.</li>
						</ul>
						
						<ul class="before">
							<li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;거래전 필독! 주의하세요!</li>
							<li>&nbsp;&nbsp;* 연락처가 없이 외부링크, 카카오톡, 댓글로만 거래할 때</li>
							<li>&nbsp;&nbsp;* 연락처 및 계좌번호를 사이버캅과 더치트로 꼭 조회해보기</li>
							<li>&nbsp;&nbsp;* 업체인 척 위장하여 신분증과 사업자 등록증을 보내는 경우</li>
							<li>&nbsp;&nbsp;* 해외직구로 면세받은 물품을 판매하는 행위는 불법입니다.</li>
						</ul>
						
						<br>
						<br>
						<div class="write2"><textarea name="content" rows="12"  style="width: 95%; resize: none; border-radius: 4px;">${dto.content}</textarea></div>
					</div>
					<div style="text-align:right; width: 866px;">
					<button type="button" class="btn" onclick="sendbuy();">${state=='update'?'수정완료':'등록하기'}</button>
			        <button type="reset" class="btn">다시입력</button>
			        <button type="button" class="btn" onclick="">${state=='update'?'수정취소':'등록취소'}</button>
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
	 </form>
	</div>
	<jsp:include page="/WEB-INF/page/layout/footer.jsp"></jsp:include>
</body>
</html>