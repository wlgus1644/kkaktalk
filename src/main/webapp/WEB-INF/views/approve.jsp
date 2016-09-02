<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import = "java.util.*" %>
<%@ page import = "net.nigne.kkt.Trade"%>
<%@include file="./include/header.jsp" %>
<html>
<head><title>반품승인</title></head>
<script>
	function wclose(){	
		window.close();
	}		
	function approve(){
		window.opener.document.zipfrm.submit();
		window.close();
	}		
</script>
<body>
<div class="well">
	
		<h4 style="text-align: center; padding: 30px;">반품승인</h4>
		<p style="text-align: center;">반품 상품을 승인하시겠습니까?</p>
		<hr/>
</div>
			<div>
				<input type = "button" value = "반품승인" onclick = "approve();" style="float:left;margin-left: 128px"/>
				<input type = "button" value = "취소" onclick = "wclose();" style="margin-left:3px"/>
			</div>

</body>
</html>



