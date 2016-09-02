<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import = "java.util.*" %>
<%@ page import = "net.nigne.kkt.Trade"%>

<html>
<head><title>수령확인</title></head>
<script>
		
	function wclose(){	
		window.close();
	}		
	
	function receivedCheck(){
		window.opener.document.cfrm1.submit();
		window.close();
	}		

</script>
<body>
<center>
	<b>수령 확인</b>
<table>

<tr>
      <td align = "center"><br>
           수령확인 후, 취소 불가.<br/>
           반드시 물품 수령후, 수령확인해주세요
      </td>
</tr>
<tr>
      <td align = "center"><br>
		<input type = "button" value = "수령확인" onclick = "receivedCheck();">
      </td>
</tr>


<tr>	
		 <td align = "center"><br>
      <input type = "button" value = "취소" onclick = "wclose();">
</tr>
</table>
</center>
</body>
</html>



