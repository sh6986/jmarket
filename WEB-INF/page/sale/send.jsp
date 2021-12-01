<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript">

function send() {
	alert("쪽지가 전송되었습니다.");
	close();
}


</script>
</head>
<body>
<form action="">

<div style="width: 450px; height: 480px; border: 1px solid gray; margin-left: 20px; margin-top: 20px; ">
<input type="text" style="width:400px; height:380px; margin-top:25px; margin-left:20px; border:1px solid gray;" placeholder="판매자에게 보낼 메세지를 입력하세요."> 
<input type="button" value="쪽지보내기" onclick="send();" style="text-align:center; width:250px; height:40px; margin-left: 95px; margin-top: 10px; background: #FF7E7E; text-align: left;">
</div>

</form>
</body>
</html>