<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page session="false" %>

<%@include file="./include/header.jsp" %>

<script>
	function closeWindow() {
		window.close();
	}
</script>


<div class="well">
	<h4>입금확인내역</h4>
   <div class="table-responsive">
      <table class="w3-table w3-border">
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
      <th><span style="color: red">${escrow.amount}</span>원</th>
   </tr>
   </table>

      </div>
</div>
<c:forEach items="${member}" var="member">
<c:if test="${member.email!=trade.member_email}"> <!-- 구매자 -->
      <input type="button" class="w3-btn w3-white w3-border" onclick="closeWindow()" value="확인" style=" margin-left:23px"/>

</c:if>
</c:forEach>

 <c:forEach items="${member}" var="member">
<c:if test="${member.email==trade.member_email}"> <!-- 판매자 -->

         <form action="/trade/seller2" method="POST" style= "float: left;  margin-left:23px; ">
               <input type="hidden" id="friend_email" name="friend_email" value="${friend_email}"/>
               <input type="hidden"  id="chat_room" name="chat_room" value="${room_No}">
               <input type="hidden" id="trade_no" name="trade_no" value="${trade.no}"/>
               <input type="submit" class="w3-btn w3-white w3-border" value="다음" />
         </form>
   
         <input type="button" class="w3-btn w3-white w3-border"  onclick="closeWindow()" value="취소" style="margin-left: 3px;"/>

</c:if>
</c:forEach>

<%@include file="./include/footer.jsp" %>