<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page session="false" %>

<%@include file="./include/header.jsp" %>


<div class="container">
 <div class="jumbotron">
    <h4>${vo.title}</h4>
      <hr />
      <table >
      <c:forEach items="${memberinfo}" var="me">
         <c:if test="${vo.member_email==me.email}">
         <tr>
            <td  rowspan="2"><img class="img-circle" width="65px" height="65px" src="/resources/profile/${me.thumbnail}" /></td>
            <td>${vo.member_email }</td>
         </tr>
         <tr>
            <td style="color:gray;">${vo.date }</td>
         </tr>
         </c:if>
         <c:if test="${vo.member_email!=me.email}">
         <tr>
            <td  rowspan="2"><img class="img-circle" width="65px" height="65px" src="/resources/profile/${thumbnail}" /></td>
            <td>${vo.member_email }</td>
         </tr>
         <tr>
            <td style="color:gray;">${vo.date }</td>
         </tr>
         </c:if>
        </c:forEach>
   </table>
   </div>

   
   <div class="table-responsive">
      <table class="w3-table w3-border">
         <tr>
            <th>판매가</th>
            <th><span style="color: red">${vo.price}</span>원</th>
         </tr>
         <tr>
            <td>카테고리</td>
            <td><c:out value="${vo.category}" escapeXml="false" /></td>
         </tr>
         <tr>
            <td>상품설명</td>
            <td><c:out value="${vo.content}" escapeXml="false" /></td>
         </tr>
      </table>
   </div>
   <hr/>
   <label>상품상세</label>
   <hr/>
   <c:forEach items="${vo2}" var="vo2">
      <c:if test="${vo.no==vo2.trade_no}">
         <img src="/resources/product/${vo2.image}"  class="img-responsive"  width="500" height="150"/><br />
      </c:if>
   </c:forEach>
   <br />
   

   <c:forEach items="${memberinfo}" var="me">
   <c:if test="${me.email==vo.member_email}">

         <form action="/trade/${vo.no}" method="post" style="float: left; margin-right: 3px">
            <input type="hidden" id="_method" name="_method" value="delete" />
             <input type="submit"  class="w3-btn w3-white w3-border"  value="삭제" style= "float: left;"/>
         </form>

         <form action="/trade/${vo.no}/updateList" method="GET">
            <input type="hidden" id="update_method" name="update_method" value="update" /> 
            <input type="submit"  class="w3-btn w3-white w3-border" value="수정" />
         </form>

   </c:if>
</c:forEach>

<c:forEach items="${memberinfo}" var="me">
   <c:if test="${me.email != vo.member_email}">
      <div id="w3">
         <form action="/trade/add_chat/${vo.member_email}" method="POST">
            <input type="hidden" id="trade_no" name="trade_no" value="${vo.no} " />
            <input type="submit" class="w3-btn"  value="거래하기" />
         </form>
      </div>
   </c:if>
</c:forEach>
</div>
<%@include file="./include/footer.jsp" %>