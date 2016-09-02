<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page session="false" %>
<%@include file="./include/header.jsp" %>

<html>

<head>
   <title>배송 정보</title>

</head>
<body>

<div class="container">

		<h4>배송정보 입력</h4>

		<form action="/trade/chat/rekatalk3" method="GET" id="zipfrm" name="zipfrm">
			<table class="w3-table w3-bordered w3-striped w3-card-4">
				<tbody>
					<tr>
						<th>수령인 이름</th>
						<td>${escrow.addressee}</td>
					</tr>
					<tr>
						<th>수령인 연락처</th>
						<td>${escrow.addresseephonenumber}</td>
					</tr>
					<tr>
						<th>수령인 우편번호</th>
						<td>${escrow.postcode}</td>
					</tr>
					<tr>
						<th>수령인 주소</th>
						<td>${escrow.address}</td>
					</tr>

				</tbody>
			</table>
			<br/>
			<input type="hidden" name="trade_no" id="trade_no"value="${trade.no}" />
			 <input type="hidden" id="friend_email"name="friend_email" value="${friend_email}" />
			  <input type="hidden"id="chat_room" name="chat_room" value="${room_No}">
			   <input type="submit" class="w3-btn w3-white w3-border" value="배송" />

		</form>
	</div>
<%@include file="./include/footer.jsp" %>
</body>

</html>