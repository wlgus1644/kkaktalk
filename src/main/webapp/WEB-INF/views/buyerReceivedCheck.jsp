<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import = "java.util.*" %>
<%@ page import = "net.nigne.kkt.Trade"%>
<%@include file="./include/header.jsp" %>

<html>
<head><title>수령확인</title></head>
<script>   
   function wclose(){   
      window.close();
   }      
   function receivedCheck(){
      window.opener.document.cfrm1.buyer_received_check.value = "수령확인";
      window.opener.document.cfrm1.submit();
      window.close();
   }      
</script>


<div class="well">
<div class="table-responsive">
      <table class="w3-table w3-border">
      <tr> <th aglign="center">수령확인</th></tr>
      <tr>
            <td align = "center"><br>
      수령확인 후, 취소 불가.<br/>
      반드시 물품 수령후, 수령확인해주세요
      </td>
      </tr>
      
      
   </table>
</div>
</div>


<input type = "button"  style= "float: left;  margin-left:23px" class="w3-btn w3-white w3-border" value = "수령확인" onclick = "receivedCheck();">
<input type = "button"  style= "float: left;  margin-left:3px" class="w3-btn w3-white w3-border" value = "취소" onclick = "wclose();">

</html>


