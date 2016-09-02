<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page session="false" %>
<%@include file="./include/header.jsp" %>
<script>
	function closeWindow() {
		window.close();
	}
</script>

<c:forEach items="${member}" var="member">
	<c:if test="${member.email==trade.member_email}"><!-- 판매자 -->
		<div class="well">
			<h4>반품 배송지 정보</h4>
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
						<th>거래가격</th>
						<th><span style="color: red">${escrow.amount}</span>원</th>
					</tr>
					<tr>
						<td>받는 분 성함</td>
						<td>${escrow.addressee}</td>
					</tr>
					<tr>
						<td>받는 분 연락처</td>
						<td>${escrow.addresseephonenumber}</td>
					</tr>
					<tr>
						<td>받는 분 우편번호</td>
						<td>${escrow.seller_postcode}</td>
					</tr>
					<tr>
						<td>받는분 주소</td>
						<td>${escrow.seller_address}</td>
					</tr>
				</table>
			</div>
		</div>

			<input type="button" class="w3-btn w3-white w3-border" onclick="closeWindow()" value="확인" style="margin-left: 23px"/>


	</c:if>
</c:forEach>
<c:forEach items="${member}" var="member">
	<c:if test="${member.email!=trade.member_email}"> <!-- 구매자 -->
		<div class="well">
			<h4>반품 배송지 정보</h4>
			<div class="table-responsive">
				<table class="w3-table w3-border">
					<tr>
					<td>거래번호</td>
						<td>거래번호: ${escrow.no}</td>
					</tr>
					<tr>
					<td>상품명</td>
						<td>${escrow.producttitle}</td>
					</tr>
					<tr>
					<th>거래가격</th>
						<th><span style="color: red">${escrow.amount}</span>원</th>
					</tr>
					<tr>
						<td>받는 분 성함</td>
						<td>${escrow.addressee}</td>
					</tr>
					<tr>
						<td>받는 분 연락처</td>
						<td>${escrow.addresseephonenumber}</td>
					</tr>
					<tr>
						<td>받는 분 우편번호</td>
						<td>${escrow.seller_postcode}</td>
					</tr>
					<tr>
						<td>받는 분 주소</td>
						<td>${escrow.seller_address}</td>
					</tr>
				</table>
			</div>
		</div>
		<form action="/trade/chat/rekatalk4" method="GET" style="float: left; margin-right: 3px">
					<input type="hidden" id="friend_email" name="friend_email" value="${friend_email}"/>
					<input type="hidden"  id="chat_room" name="chat_room" value="${room_No}">
					<input type="hidden" id="trade_no" name="trade_no" value="${trade.no}"/>
		    		<input type="submit" class="w3-btn w3-white w3-border" value="배송" style="margin-left: 23px"/>
		 		</form>
		   			
		   		<input type="button" class="w3-btn w3-white w3-border"  onclick="closeWindow()" value="취소"  />
		</div>
	</c:if>
</c:forEach>
<%@include file="./include/footer.jsp" %>