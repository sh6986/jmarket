<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript">

function sendPay() {
	var input = prompt('계좌비밀번호를 입력하세요','6자리 입력');
	alert(input);
	alert("결제가 완료되었습니다.");
	close();
}


</script>
</head>
<body>
<form action="">
<div style="width: 450px; height: 480px; border: 1px solid gray; margin-left: 20px; margin-top: 20px; ">
<div align="center" style="width:400px; height:380px; margin-top:25px; margin-left:20px; border:1px solid gray; ">
	<div style="margin-top: 200px;">
		<p>자몽페이 결제를 원하시면,</p>
		<span style="font-weight: bold;">결제완료</span><span>버튼을 눌러주세요.</span>
	<p style="font-size: 12px; color: #FF7E7E;">결제정보가 등록된 회원만 가능합니다.</p>
	</div>
</div>

<div>
	<input type="button"  value="결제완료" onclick="sendPay();" style="width:250px; height:40px; margin-left: 95px; margin-top: 10px; background: #FF7E7E;">
	
</div>
</div>


</form>
</body>
</html>