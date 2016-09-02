<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
</head>
<body>
<script type="text/javascript">
	var noticeNum = "${noticeNum}";
	var message = "";
	switch(Number(noticeNum)) {
	case 2:
		message = "입금이 확인되었습니다.";
		break;
	case 3:
		message = "상품이 배송중입니다.";
		break;
	case 4:
		message = "구매자의 상품수령이 확인되었습니다. 판매자에게 돈이 입금될 예정입니다.";
		break;
	case 5:
		message = "구매자의 반품요청이 있습니다.";
		break;
	case 6:
		message = "반품요청이 승인되었습니다.";
		break;
	case 7:
		message = "반품상품이 배송중입니다.";
		break;
	case 8:
		message = "반품상품이 수령되었습니다. 구매자에게 돈이 입금될 예정입니다.";
		break;
	}
	
	opener.parent.sendNotice(noticeNum, message);
	window.close();
</script>
</body>
</html>