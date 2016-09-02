<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page session="false" %>
<%@include file="./include/header.jsp" %>
<script type="text/JavaScript" src="http://code.jquery.com/jquery-1.7.min.js"></script>
<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>

<html>
<head>
   <title>배송지 정보</title>
 <script>
    /*주소찾기 API  */
    
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
    function zipCheck(){
       url = "/trade/zipCheck?check=y";
       window.open(url, "post",
             "toolbar = no, status = yes, menubar = no," + 
             "scrollbars = yes, directories = no," +
             "width = 600, height = 400");
    }
    
    function deliveryClear(){
       var addressee=document.getElementById("addressee").value;
       var addresseephonenumber=document.getElementById("addresseephonenumber").value;
       var postcode1=document.getElementById("postcode1").value;
       var address=document.getElementById("address").value;
       var address2=document.getElementById("address2").value;
       
       if(addressee==""){
          alert("받는 분 이름을 입력해주세요");
       }else if(addresseephonenumber==""){
          alert("받는 분을 연락처를 입력해주세요");
       }else if(postcode1==""){
          alert("우편번호를 입력해주세요")
       }else if(address==""){
          alert("주소를 입력해주세요")
       }else if(address2==""){
          alert("상세주소를 입력해주세요")
       }else
          document.zipfrm.submit();
    }

</script>
</head>
<body>
<div class="container">
<h1>배송지 정보입력</h1>
<div class="well">
      <form action="/trade/updateAddress" method="post" id="zipfrm" name="zipfrm" class="form-horizontal">
    
       <div class="form-group">
            <label class="control-label col-sm-2" >받는 사람</label>
            <div class="col-sm-10">
           <input type="text" class="form-control" id="addressee" name="addressee" placeholder="받는 분 성함을 입력하세요.">
            </div>
       </div>
       <div class="form-group">
            <label class="control-label col-sm-2" >연락처</label>
            <div class="col-sm-10">
           <input type="text" class="form-control" id="addresseephonenumber" name="addresseephonenumber" placeholder="받는 분 연락처를 입력하세요.">
            </div>
       </div>
         
        <div class="form-group">
            <label class="control-label col-sm-2" >주소</label>
                <div class="form-inline">
                   <div class="col-xs-10">
                      <input  name="postcode1" id ="postcode1" type ="text" style="width:50px;" readonly="readonly" /> -
                        <input name="postcode2"  id = "postcode2"  type = "text" style="width:50px;" readonly="readonly"/>&nbsp;
                      <input type = "button"  class="w3-btn w3-white w3-border" onClick = "openDaumZipAddress()" value = "주소 찾기" />
                     </div>
     
                   <div class="col-xs-10">
                      <input type="text"  id = "address" name = "address"  readonly="readonly" style="width: 300px; "/>
                      <input type="text"  id = "address2" name = "address2"  placeholder="상세주소를 입력하세요." style="width: 300px; "/>
                  </div>
               </div>
               </div>
        
      <input type="hidden" name="trade_no" id="trade_no" value="${trade.no}"/>
      <input type="hidden" id="friend_email" name="friend_email" value="${friend_email}"/>
     <input type="hidden"  id="chat_room" name="chat_room" value="${room_No}">
     <input type="button" class="w3-btn w3-white w3-border" value="다음" onclick="deliveryClear()" />
     </form>
     </div>
   </div>
<%@include file="./include/footer.jsp" %>