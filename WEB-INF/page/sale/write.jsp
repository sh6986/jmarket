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
<link rel="stylesheet" href="<%=cp%>/resource/css/home.css"
	type="text/css">
<link rel="stylesheet" href="<%=cp%>/resource/css/sbwrite.css"
	type="text/css">
<link rel="stylesheet" href="<%=cp%>/resource/css/sidemenu.css"
	type="text/css">
<link
	href="https://fonts.googleapis.com/css2?family=Cute+Font&family=Jua&display=swap"
	rel="stylesheet">
<script type="text/javascript">

function sendOk() {
    var f = document.writeForm;
	var str = f.subject.value;
    if(!str) {
        alert("제목을 입력하세요. ");
        f.subject.focus();
        return;
    }

	str = f.content.value;
    if(!str) {
        alert("내용을 입력하세요.");
        f.content.focus();
        return;
    }
    
    
	str = f.sprice.value;
    if(!str) {
        alert("가격을 입력하세요.");
        f.sprice.focus();
        return;
    }

    
	
    var mode="${mode}";
	  if(mode=="created"||mode=="update" && f.upload1.value!="" && f.upload2.value!="" && f.upload3.value!="") {
		if(! /(\.gif|\.jpg|\.png|\.jpeg)$/i.test(f.upload1.value)) {
			alert('이미지 파일만 가능합니다. !!!');
			f.upload1.focus();
			return;
		}
		if(! /(\.gif|\.jpg|\.png|\.jpeg)$/i.test(f.upload2.value)) {
			alert('이미지 파일만 가능합니다. !!!');
			f.upload2.focus();
			return;
		}
		if(! /(\.gif|\.jpg|\.png|\.jpeg)$/i.test(f.upload3.value)) {
			alert('이미지 파일만 가능합니다. !!!');
			f.upload3.focus();
			return;
		}
	  }

		
		
	f.action="<%=cp%>/sale/${mode}_ok.do";
    f.submit();
}


<c:if test="${mode=='update'}">
function deleteFile(num) {
	  var url="<%=cp%>/sale/deleteFile.do?num=" + num
				+ "&page=${page}&rows=${rows}";
		location.href = url;
	}
	</c:if>
</script>
</head>
<body>
	<div id="mainframe">
		<jsp:include page="/WEB-INF/page/layout/header.jsp"></jsp:include>

		<form name="writeForm" method="post" enctype="multipart/form-data">
			<section class="container">
				<article>
					<div class="content">
						<div class="all">
							<br>
							<br>
							<div class="name1">
								<input name="subject"
									style="width: 550px; height: 25px; border-radius: 4px;">
							</div>
							<div class="img-upload">
								<input type="file" name="upload1">
							</div>


							<ul class="view">
								<li class="product">상품명 : <input type="text" name="pname" value="${dto.pname}">
								<li>
								<li class="price">가격: &nbsp;&nbsp;<input name="sprice" value="${dto.sprice}"
									style="width: 150px; height: 20px; border-radius: 4px;" >
								원<br></li>
								<li class="list"><br>거래방법 : 직접거래 &nbsp;&nbsp; <span
									class="safe">안전거래 신청</span></li>
								<li class="list">배송방법 : 판매자와 직접 연락하세요</li>
								<li>&nbsp;</li>
								<li class="send"><span class="jmpay"> &nbsp;
										&nbsp;Pay</span> 수수료 없이 송금하기</li>
								<li>&nbsp;</li>
								<li class="ju">자몽페이 송금은 에스크로 기능이 제공되지 않으며 판매자에게 결제금액이 바로
									전달되는 '직접거래'입니다.</li>
								<li class="warning">자몽마켓에 등록된 판매 물품과 내용을 개별 판매자가 등록한 것으로서,
									자몽마켓은 등록을 위한 시스템만 제공하며 내용에 대하여 일체의 책임을 지지 않습니다.</li>
							</ul>

							<ul class="before">
								<li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;거래전 필독!
									주의하세요!</li>
								<li>&nbsp;&nbsp;* 연락처가 없이 외부링크, 카카오톡, 댓글로만 거래할 때</li>
								<li>&nbsp;&nbsp;* 연락처 및 계좌번호를 사이버캅과 더치트로 꼭 조회해보기</li>
								<li>&nbsp;&nbsp;* 업체인 척 위장하여 신분증과 사업자 등록증을 보내는 경우</li>
								<li>&nbsp;&nbsp;* 해외직구로 면세받은 물품을 판매하는 행위는 불법입니다.</li>
							</ul>

							<br> <br>

							<div>
								<span class="bigimg-upload"><input type="file" name="upload2"></span> <span
									class="bigimg-upload"><input type="file" name="upload3"> </span>
							</div>
							<div class="write2">
								<textarea name="content" rows="12"
									style="width: 95%; resize: none; display: inline-block;">${content}</textarea>
							</div>
						</div>
					</div>
					<table>
						<c:if test="${mode=='update' }">
							<tr align="left" height="40"
								style="border-bottom: 1px solid #cccccc;">
								<td width="100" bgcolor="#eeeeee" style="text-align: center;">첨부된파일</td>
								<td style="padding-left: 10px;"></td>
							</tr>
						</c:if>

					</table>

					<table style="width: 100%; border-spacing: 0px;">
						<tr height="45">
							<td align="center">
								<button type="button" class="btn" onclick="sendOk();">${mode=='update'?'수정완료':'등록하기'}</button>
								<button type="reset" class="btn">다시입력</button>
								<button type="button" class="btn"
									onclick="javascript:location.href='<%=cp%>/sale/list.do';">${mode=='update'?'수정취소':'등록취소'}</button>
								<c:if test="${mode=='update'}">
									<input type="hidden" name="num" value="${dto.num}">
									<input type="hidden" name="page" value="${page}">
									<input type="hidden" name="fileName1" value="${dto.fileName1}">
									<input type="hidden" name="fileName2" value="${dto.fileName2}">
									<input type="hidden" name="fileName3" value="${dto.fileName3}">
								</c:if> <input type="hidden" name="rows" value="${rows}">

							</td>
						</tr>
					</table>
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
		</form>
	</div>
	<jsp:include page="/WEB-INF/page/layout/footer.jsp"></jsp:include>
</body>
</html>