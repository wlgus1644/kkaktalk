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
		message = "�Ա��� Ȯ�εǾ����ϴ�.";
		break;
	case 3:
		message = "��ǰ�� ������Դϴ�.";
		break;
	case 4:
		message = "�������� ��ǰ������ Ȯ�εǾ����ϴ�. �Ǹ��ڿ��� ���� �Աݵ� �����Դϴ�.";
		break;
	case 5:
		message = "�������� ��ǰ��û�� �ֽ��ϴ�.";
		break;
	case 6:
		message = "��ǰ��û�� ���εǾ����ϴ�.";
		break;
	case 7:
		message = "��ǰ��ǰ�� ������Դϴ�.";
		break;
	case 8:
		message = "��ǰ��ǰ�� ���ɵǾ����ϴ�. �����ڿ��� ���� �Աݵ� �����Դϴ�.";
		break;
	}
	
	opener.parent.sendNotice(noticeNum, message);
	window.close();
</script>
</body>
</html>