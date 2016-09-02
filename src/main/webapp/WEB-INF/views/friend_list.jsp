<%@ page language="java" contentType="text/html; charset=utf-8"
   pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="true"%>

<%@include file="./include/header.jsp"%>


<script type="text/javascript">
   if(window.name == "newWindow") {
	   window.close();
   }
   function onSearch() {
      var friend_name = document.getElementById("friendname").value;

      if (friend_name != "") {
         location.href = "/friend_list/" + friend_name;
      } else {
         location.href = "/friend_list";
      }
   }
   
   function ondelete(friend_email){
	   if (confirm("정말 삭제하시겠습니까??") == true){    //확인
		   location.href= "/friend_delete/" + friend_email;
		}else{   //취소
		    return;
		}
   }
</script>

<div class="container">
   <nav class="navbar navbar-default navbar-static-top">
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
         <li class="dropdown" id="menu">
            <a class="dropdown-toggle" data-toggle="dropdown" href="#">
               <i class="fa fa-ellipsis-h fa-fw" style="font-size:18px"></i>
            </a>
            <ul class="dropdown-menu dropdown-user">
               <li><a href="/memberUpdate"><i class="fa fa-user fa-fw"></i>프로필수정</a></li>
               <li><a href="/pwchange"><i class="fa fa-sign-out fa-fw"></i>비밀번호 변경</a></li>
               <li><a href="/logout"><i class="fa fa-sign-out fa-fw"></i>로그아웃</a></li>
            </ul>
         </li>
         <li class="dropdown">
            <a href="/friend_list"><i class="fa fa-user fa-fw" style="font-size:18px"></i></a>
         </li>
         
         <li class="dropdown">
         <a href="/chat_list">
            <c:choose>
               <c:when test="${totalmsg ne 0}">
                  <i class="fa fa-comments fa-fw" style="font-size:18px">
                     <span class="badge bg-important" style="background-color: red">${totalmsg}</span>
                  </i>
               </c:when>
               <c:otherwise>
                  <i class="fa fa-comments fa-fw" style="font-size:18px"></i>
               </c:otherwise>

            </c:choose>
         </a>
         </li>

         <li class="dropdown">
            <a href="/trade"><i class="glyphicon glyphicon-shopping-cart glyphicon-fw" style="font-size:18px"></i></a>
         </li>
         
      
      </ul>
   </nav>

   <div class="panel panel-default">
      <div class="panel-heading" style="text-align: center;">
         친구
         <a href="/friend_search"><img style="width:20px; height: 20px; float:right; margin-top: 3px" src="/resources/profile/friend+.png" /></a>
      </div>
      
      <div class="panel panel-body">
         <div class="list-group">
            <input type="text" id="friendname" name="friendname" onkeypress="if(event.keyCode==13) {onSearch();}" 
            class="form-control" placeholder="친구검색" style="text-align: center;"> 
            <span class="input-group-btn"></span>
            <c:forEach items="${friendlist}" var="vo">
               <p class="list-group-item">
                  <img class="img-circle" width="50" height="50" src="/resources/profile/${vo.thumbnail }" />&nbsp&nbsp
                  <a href="/chat/${vo.email }">${vo.name}</a> <input type="hidden" id="friend_email" name="friend_email" value="${vo.email }" />
                  <a style="float: right" href="#" onclick="ondelete('${vo.email }');"><img style="width:23px; height: 23px; margin-top: 10px" src="/resources/profile/friend-.png" /></a>
               </p>
            </c:forEach>
         </div>
      </div>
   </div>
</div>

<%@include file="./include/footer.jsp"%>