<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>

<%@include file="./include/header.jsp" %>

<script type="text/javascript">
   function friendAdd() {
      location.href = "/friendAdd/${friend_email}";
   }
</script>

<div class="container" style="width: 100%; height: 30px; line-height: 30px; text-align: center">
   <img src="/resources/profile/${thumbnail }" style="width: 100%; max-width: 760px; vertical-align: middle"/>
   친구이름 :  "${friend_name }" <br/>
   <c:if test="${isFriend == 0}">
         <input type="button" value="친구추가"  class="w3-btn w3-white w3-border" onclick="friendAdd()"/>
   </c:if>
   <c:if test="${isFriend == 1}">
         <input class="w3-btn w3-white w3-border" type="button" value ="뒤로" onclick="window.close()">
   </c:if>
</div>


<%@include file="./include/footer.jsp" %>