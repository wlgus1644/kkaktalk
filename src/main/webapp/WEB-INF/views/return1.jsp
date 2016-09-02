<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import = "java.util.*" %>
<%@ page import = "net.nigne.kkt.Trade"%>
<%@include file="./include/header.jsp" %>


<html>
<head><title>반품요청</title></head>
<script>
      
   function wclose(){   
      window.close();
   }      
   
   function return1(){
      var va= document.getElementById('buyer_account_num').value;
      var bank= document.getElementById('bank_type').value;
      window.opener.document.cfrm2.buyer_account_num.value = va;
      window.opener.document.cfrm2.buyer_bank_type.value = bank;
      window.opener.document.cfrm2.submit();
      window.close();
   }      

</script>
	<div class="well">
		<h4 style="text-align: center;">반품요청</h4>
	
		<form class="form-horizontal" >
		<label>은행명</label>
			<div class="form-group">
			<div class="col-xs-5">
					<select id="bank_type" name="bank_type" class="form-control">
						<option value="국민은행">국민은행</option>
						<option value="농협은행">농협은행</option>
						<option value="우리은행">우리은행</option>
						<option value="하나은행">하나은행</option>
						<option value="신한은행">신한은행</option>
						<option value="기업은행">기업은행</option>
					</select>
			</div><br/>
			</div>
				<label>계좌번호</label>
				<div class="form-group">
					<div class="col-xs-7">
					
						<input type="text" id="buyer_account_num" name="buyer_account_num" placeholder="-없이 입력하세요." class="form-control"/>
					</div>
				</div>
			판매자에게 반품요청합니다.
		
		</form>
	</div>
	<input type = "button" class="w3-btn w3-white w3-border" style= "float: left;  margin-left:23px" value = "반품요청" onclick = "return1();">
    <input type = "button" class="w3-btn w3-white w3-border" style= "float: left; margin-left:3px" value = "취소" onclick = "wclose();">
</html>


