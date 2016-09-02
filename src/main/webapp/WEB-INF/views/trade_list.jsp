<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page session="true" %>

<%@include file="./include/header.jsp" %>
<div class="w3-overlay w3-hide-large w3-animate-opacity" onclick="w3_close()" style="cursor:pointer" title="close side menu" id="myOverlay"></div>

<div class="w3-main">

<script>
var check="";
$(document).ready(function(){
      //컨트롤러에서 체크해서 계좌 체크-> 없으면 띄우게. 
      <c:forEach items="${memberinfo}" var="vo">
            var noticeCookie=getCookie("${vo.email}"); 
      </c:forEach>
      
      if(noticeCookie != "no"){
          $("#accountModal").modal('toggle');
      }
         
   });   
function getCookie(name) {
   var Found=false;
   var start, end;
   var i=0;
   while(i<=document.cookie.length){
      start=i;
      end=start+name.length;
      if(document.cookie.substring(start, end)==name){
         Found=true;
         break;
      }
      i++;
   }
   if(Found==true){
      start=end+1;
      end=document.cookie.indexOf(";", start);
      if(end<start)
         end=document.cookie.length;
      return document.cookie.substring(start, end);      
   }
   return "";
}

 function setCookie(name, value, expiredays) {
   var todayDate=new Date();
   todayDate.setDate(todayDate.getDate()+ expiredays);
   document.cookie=name+"="+escape(value)+"; path=/; expires="+todayDate.toGMTString()+";";
}
 function closeWin() {
   if(document.account.chkAccount.checked){
      <c:forEach items="${memberinfo}" var="vo">
         setCookie("${vo.email}", "no", 1); //email마다 쿠키 지정해줘야함
      </c:forEach>
      $("#accountModal").modal('hide');
   }
} 
</script>


<header class="w3-container">

<c:if test="${checkAccount eq 0}">

<!-- 계좌등록 first Modal -->
  <div class="modal fade" id="accountModal" role="dialog">
    <div class="modal-dialog modal-sm">
      <!-- Modal content-->
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h4 class="modal-title">거래를 위해 계좌번호를 등록하시겠습니까?</h4>
        </div>
        <div class="modal-body" style="text-align: center">
            <a data-toggle="modal" href="#accountModal2" class="btn btn-default">YES</a>
             <button type="button" data-dismiss="modal"class="btn btn-default">NO</button>
        </div>
        <div class="modal-footer">
           <form name="account">
              <input type="checkbox" name="chkAccount" value="" onclick="closeWin()">오늘하루 팝업창 보지 않기
           </form>
        </div>
      </div>
    </div>
  </div>
  <!-- 계좌등록 second Modal -->
  <div class="modal fade" id="accountModal2" role="dialog">
    <div class="modal-dialog modal-sm">
     
      <!-- Modal content-->
       <form action="/trade/updateAccount" method="post">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h4 class="modal-title">계좌번호를 입력해주세요.</h4>
        </div>
        <div class="modal-body" style="text-align: center">
          <select id="bank_type" name="bank_type">
             <option value="국민은행">국민은행</option>
             <option value="농협은행">농협은행</option>
             <option value="우리은행">우리은행</option>
             <option value="하나은행">하나은행</option>
             <option value="신한은행">신한은행</option>
             <option value="기업은행">기업은행</option>
          </select>
          <input type="text" id="account_num" name="account_num" placeholder="숫자만 입력하세요"/>
          <div class="modal-footer">
          <input type="submit"  data-toggle="modal"  class="w3-btn w3-white w3-border"  value="등록" />
        </div>
        </div>
      </div>
        </form>
    </div>
  </div>
</c:if>
  </header>

	<div class="container">
		<nav class="navbar navbar-default navbar-static-top" >
			<div class="navbar-header">
				<c:forEach items="${memberinfo}" var="vo">
					<tr>
						<td><img class="img-circle" width="40" height="40"
							src="/resources/profile/${vo.thumbnail }" /></td>
					</tr>
					<tr>
						<td>&nbsp&nbsp${vo.name}</td>
					</tr>
				</c:forEach>
			</div>
			<ul class="nav navbar-top-links navbar-right" role="menu">
				<li class="dropdown" id="menu"><a class="dropdown-toggle"
					data-toggle="dropdown" href="#"> <i class="fa fa-plus"
						style="font-size: 18px"></i>
				</a>
					<ul class="dropdown-menu dropdown-user">
						<li><a href="/trade/new"><i class="fa fa-edit"></i>상품등록</a></li>

					</ul></li>
				<li class="dropdown"><a href="/friend_list"><i
						class="fa fa-user fa-fw" style="font-size: 18px"></i></a></li>

				<li class="dropdown"><a href="/chat_list"> <c:choose>
							<c:when test="${totalmsg ne 0}">
								<i class="fa fa-comments fa-fw" style="font-size: 18px"> <span
									class="badge bg-important" style="background-color: red">${totalmsg}</span>
								</i>
							</c:when>
							<c:otherwise>
								<i class="fa fa-comments fa-fw" style="font-size: 18px"></i>
							</c:otherwise>

						</c:choose>
				</a></li>
				<li class="dropdown"><a href="/trade"><i
						class="glyphicon glyphicon-shopping-cart glyphicon-fw"
						style="font-size: 18px"></i></a></li>

			</ul>
		</nav>

<form id="form2" action="/trade/searchList">
	<div class="panel panel-default">
		<div class="panel-heading" style="text-align: center;">중고</div>
		<div class="panel-body">
			<div class="list-group">
				<div class="w3-dropdown-click" style="text-align: center; float: left; margin-right: 10px">
					<select id="keyfield" name="keyfield" size="1">
						<option value="전체" id="all" name="all">전체</option>
						<option value="의류" id="clothes" name="clothes">의류</option>
						<option value=가전제품 id="DAppliance" name="DAppliance">
							가전제품</option>
						<option value="기타" id="etc" name="etc">기타</option>
					</select>
				</div>
				<input type="text" id="keyword" name="keyword" onkeypress="if(event.keyCode==13) {goSearch();}" class="form-control" placeholder="상품검색" style="text-align: center; width: 65%; height: 25px;">
			</div>
		</div>
	</div>
</form>


		<%-- 		<div class="panel-heading" style="text-align: center;">
			<form id="form2" class="w3-form w3-margin-bottom"
				action="/trade/searchList" style="float: left" method="get">
				<div class="w3-dropdown-click" style="text-align: center">
					<select id="keyfield" name="keyfield" size="1">
						<option value="전체" id="all" name="all">전체</option>
						<option value="의류" id="clothes" name="clothes">의류</option>
						<option value=가전제품 id="DAppliance" name="DAppliance">
							가전제품</option>
						<option value="기타" id="etc" name="etc">기타</option>
					</select>
				</div>
				<input type="text" size="16" placeholder="전체" name="keyword"
					value="${keyword}" />
				<button class="w3-btn" onClick="goSearch()" value="검색">
					<i class="fa fa-search"></i>
				</button>
			</form>

		</div> --%>
	
</div>


 <!-- First Photo Grid-->
 
<c:forEach items="${trade_list }" var="trade_list" >

  <div class="w3-row-padding" style="float:left" >
  
    <div class="w3-third w3-container w3-margin-bottom" style="float:left">

      <c:set var="doneLoop" value="false"/>
         <c:forEach items="${uploadFileList}" var="vo1" >
            <c:if test="${not doneLoop }">
             <c:if test="${trade_list.no==vo1.trade_no}"> 
             <a href="/trade/${trade_list.no}"><img src="/resources/product/${vo1.image}" alt="Norway" style="width:330" class="w3-hover-opacity"></a>
         <c:set var="doneLoop" value="true"/>
               </c:if>
               </c:if>
         </c:forEach>
      
      <div class="w3-container w3-white" >
      </br>
        <p><b>${trade_list.title}</b></p>
        <p>${trade_list.price}원</p>
      </div>
    </div>
  </div>
 </c:forEach>
</div>
<%@include file="./include/footer.jsp" %>