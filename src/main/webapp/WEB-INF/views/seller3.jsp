<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page session="false" %>
<script type="text/JavaScript" src="http://code.jquery.com/jquery-1.7.min.js"></script>
<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>
<%@include file="./include/header.jsp" %>
 <script language = "javascript">
    /*주소찾기 API  */
    function closeWindow() {
		window.close();
	}
    function openDaumZipAddress() {
   
        new daum.Postcode({
            
            oncomplete: function(data) {
   
                jQuery('#postcode1').val(data.postcode1);
                jQuery('#postcode2').val(data.postcode2);
                jQuery('#address').val(data.address);
                jQuery('#address2').focus();
                console.log(data);
            }
        }).open();
    }
    function approve(){
       var postcode1=document.getElementById("postcode1").value;
       var address=document.getElementById("address").value;
       var address2=document.getElementById("address2").value;
       if(postcode1==""){
          alert("우편번호를 입력해주세요")
       }else if(address==""){
          alert("주소를 입력해주세요")
       }else if(address2==""){
          alert("상세주소를 입력해주세요")
       }else{
          url = "/trade/approve?check=y";
         
          window.open(url, "post",
                "toolbar = no, status = yes, menubar = no," + 
                "scrollbars = yes, directories = no," +
                "width = 400, height = 300");
       }
    }
    function zipCheck(){
          
          url = "/trade/zipCheck?check=y";
         
          window.open(url, "post",
                "toolbar = no, status = yes, menubar = no," + 
                "scrollbars = yes, directories = no," +
                "width = 600, height = 400");
          
          
    }
</script>
   
 <c:forEach items="${member}" var="member"> <!-- 판매자 -->
<c:if test="${member.email==trade.member_email}">  
<form action="/trade/returnapprove" method="GET" id="zipfrm" name="zipfrm" class="form-inline">
<div class="well">
   <h4>반품상품 배송지 정보</h4>
				<div class="table-responsive">
					<div class="form-group">
						<table class="w3-table w3-border" >
							<tr>
								<td>판매자</td>
								<td>${trade.member_email}</td>
							</tr>
							<tr>
								<td>상품명</td>
								<td>${escrow.producttitle}</td>
							</tr>
							<tr>
								<th>판매가</th>
								<th><span style="color: red">${escrow.amount}</span>원</th>
							</tr>
							<tr>
								<td>거래번호</td>
								<td>${escrow.no}</td>
							</tr>
							<tr>
								<td>수령자</td>
								<td><input type="text" name="addressee" id="addressee" /></td>
							</tr>
							<tr>
								<td>우편번호</td>
								<td><input name="postcode1" id="postcode1" type="text" style="width: 50px;" readonly="readonly" /> - 
								<input name="postcode2" id="postcode2" type="text" style="width: 50px;" readonly="readonly" />&nbsp; 
								<input type="button" class="w3-btn w3-white w3-border" onClick="openDaumZipAddress()" value="주소 찾기" /></td>
							</tr>
							<tr>
								<td></td>
								
								<td><input type="text" id="address" name="address"readonly="readonly" /><br/> 
									<input type="text" id="address2" name="address2" placeholder="상세주소를 입력하세요." size="23"/></td>
							</tr>
						</table>
					</div>
				</div>
				
</div>

            <input type="hidden" id="friend_email" name="friend_email" value="${friend_email}"/>
            <input type="hidden"  id="chat_room" name="chat_room" value="${room_No}">
            <input type="hidden" id="trade_no" name="trade_no" value="${trade.no}"/>
            <input type ="button" style= "float: left; margin-left:23px" class="w3-btn w3-white w3-border" value = "반품승인" onClick = "approve();">
          
         <input type="button" class="w3-btn w3-white w3-border"  onclick="closeWindow()" value="취소" style="margin-left:3px;"/>
         </form>
       
</c:if>
</c:forEach>

<c:forEach items="${member}" var="member">
<c:if test="${member.email!=trade.member_email}"> <!-- 구매자 -->  
   <div class="well">
   <h4>반품 신청 내역</h4>
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
            <th>가격</th>
         <th><span style="color: red">${escrow.amount}</span>원</th>
      </tr>
       <tr>
            <td>거래번호</td>
         <td>${escrow.no}</td>
      </tr>
      
   </table>
</div>
</div>
     <input type="button" class="w3-btn w3-white w3-border" onclick="closeWindow()" value="확인" style="margin-left:23px "/>

</c:if>
</c:forEach>
<%@include file="./include/footer.jsp" %>