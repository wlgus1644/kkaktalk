<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page session="false" %>

<%@include file="./include/header.jsp" %>

<script type="text/javascript">
	function closeWindow() {
		window.close();
	}
</script>

<div class="container">
    <div class="jumbotron">
      <table >
      <c:forEach items="${member}" var="me">
         <tr>
            <td  rowspan="2"><img class="img-circle" width="65px" height="65px" src="/resources/profile/${thumbnail}" /></td>
            <td>${trade.member_email}</td>
         </tr>
         <tr>
            <td style="color:gray;">${trade.date }</td>
         </tr>
        </c:forEach>
   </table>
   </div>
   
   <div class="table-responsive">
      <table class="w3-table w3-border" style="text-align: center;">
         <tr>
            <td>거래번호</td>
            <td>${escrow.no}</td>
         </tr>
         <tr>
            <td>상품명</td>
            <td>${escrow.producttitle}</td>
         </tr>
         <tr>
            <th>판매가</th>
            <th><span style="color: red">${escrow.amount}원</span></th>
         </tr>
      </table>
   </div>
   <hr/>
<label>상품상세</label>
<hr/>
   <c:forEach items="${imgvo}" var="imgvo">
      <c:if test="${trade.no==imgvo.trade_no}">
         <td><img src="/resources/product/${imgvo.image}"  class="img-responsive" width="250" height="250"/></td>
         <br/>
      </c:if>
   </c:forEach>
      <br/>
      <c:forEach items="${member}" var="member">
         <c:if test="${member.email==trade.member_email}">
			<input type="button" class="w3-btn w3-white w3-border" onclick="closeWindow()" value="확인" />
         </c:if>
      </c:forEach>

      <c:forEach items="${member}" var="member">
         <c:if test="${member.email!=trade.member_email}">
               <form action="/trade/buyer" method="post" style="float: left; margin-right: 3px">
                  <input type="hidden" id="trade_no" name="trade_no" value="${trade.no}" /> 
                  <input type="hidden" id="chat_room"name="chat_room" value="${room_No}">
                  <input type="hidden" id="friend_email" name="friend_email" value="${friend_email}" /> 
                  <input type="submit" class="w3-btn" value="구매하기" />
               </form>
         
               <input type="button"class="w3-btn w3-white w3-border" onclick="closeWindow()" value="취소" />
            
         </c:if>
      </c:forEach>
</div>

   <%@include file="./include/footer.jsp"%>