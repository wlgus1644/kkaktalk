<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page session="false" %>

<%@include file="./include/header.jsp" %>


<div class="container">

   <table class="table table-stripped" style="text-align: center;">
  	 <c:forEach items="${imgvo}" var="imgvo" >
         <c:if test="${escrow.trade_no==imgvo.trade_no}">
            <td><img src="/resources/product/${imgvo.image}" width="350" /></td>   
         </c:if>
      </c:forEach>
      <tr>
         <td>거래번호: ${escrow.no}</td>
      </tr>
      <tr>
         <td>구매 상품: ${escrow.producttitle}</td>
      </tr>
      <tr>
         <td>입금은행: 신한은행 <br/> 입금계좌: 000-000-000000</td>
      </tr>
      <tr>
         <td>입금해야할 금액: ${escrow.amount}원</td>
      </tr>


   </table>

   </div>
  
   <div id="wrapper1">
   <form action="/trade/chat/rekatalk2" method="GET">
		<input type="hidden" id="friend_email" name="friend_email" value="${friend_email}"/>
		<input type="hidden"  id="chat_room" name="chat_room" value="${room_No}">
		<input type="submit" class="w3-btn w3-white w3-border" value="확인"/>
	</form>
	</div>


<%@include file="./include/footer.jsp" %>