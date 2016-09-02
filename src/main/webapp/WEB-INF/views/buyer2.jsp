<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page session="false" %>
<%@include file="./include/header.jsp" %>
 <script language = "javascript">
    function buyerReceivedCheck(){
      url = "/trade/buyerReceivedCheck?check=y";
      window.open(url, "post",
            "toolbar = no, status = yes, menubar = no," + 
            "scrollbars = yes, directories = no," +
            "width = 400, height = 300");
    }
    function return1(){   
      url = "/trade/return1?check=y";
      window.open(url, "post",
            "toolbar = no, status = yes, menubar = no," + 
            "scrollbars = yes, directories = no," +
            "width = 400, height = 450");
    }
    function closeWindow() {
		window.close();
	}
</script>
<div class="well">
	<c:forEach items="${member}" var="member">
		<c:if test="${member.email==trade.member_email}">
			<h4>상품 정보</h4>
		</c:if>
		<c:if test="${member.email!=trade.member_email}">  
			 <h4>상품 수령 확인</h4>
		</c:if>
	</c:forEach>
	<div class="table-responsive">
      <table class="w3-table w3-border">
			<tr>
				<td>판매자</td>
				<td>${trade.member_email}</td>
			</tr>
			<tr>
				<td>상품명</td>
				<td>${escrow.producttitle}</td>
			</tr>
			<tr>
				<td>거래번호</td>
				<td>${escrow.no}</td>
			</tr>
			<tr>
				<th>가격</th>
				<th><span style="color: red">${escrow.amount}</span>원</th>
			</tr>

		</table>
</div>
</div>
<c:forEach items="${member}" var="member">
<c:if test="${member.email==trade.member_email}"> <!-- 판매자 -->  
   <input type="button" class="w3-btn w3-white w3-border" onclick="closeWindow()" value="확인" style="margin-left: 23px;"/>

</c:if>
</c:forEach>
<c:forEach items="${member}" var="member"> <!-- 구매자 -->
<c:if test="${member.email!=trade.member_email}">  
    
      <form action="/trade/buyer3" method="POST" id="cfrm1" name="cfrm1" >
         <input type="hidden" id="friend_email" name="friend_email" value="${friend_email}"/>
         <input type="hidden"  id="chat_room" name="chat_room" value="${room_No}">
         <input type="hidden" id="trade_no" name="trade_no" value="${trade.no}"/>
         <input type="hidden" id="state" name="state" value="상품수령">
         <input type="hidden" id="buyer_received_check" name="buyer_received_check">
         <input type = "button" class="w3-btn w3-white w3-border" value = "상품수령" onClick = "buyerReceivedCheck()" style= "float: left; margin-left:23px;" />
      </form>
      <form action="/trade/return2" method="POST" id="cfrm2" name="cfrm2" >
         <input type="hidden" id="friend_email" name="friend_email" value="${friend_email}"/>
         <input type="hidden"  id="chat_room" name="chat_room" value="${room_No}">
         <input type="hidden" id="trade_no" name="trade_no" value="${trade.no}"/>
         <input type="hidden" id="state" name="state" value="반품요청">
         <input type="hidden" id="buyer_account_num" name="buyer_account_num"/>
         <input type="hidden" id="buyer_bank_type" name="buyer_bank_type"/>
         <input type = "button" class="w3-btn w3-white w3-border" value = "반품요청" onClick = "return1()" style= "float: left; margin-left: 3px;" />
      </form>
      
      <input type="button" class="w3-btn w3-white w3-border"  onclick="closeWindow()" value="취소" style="margin-left:3px;"/>

</c:if>
</c:forEach>
<%@include file="./include/footer.jsp" %>