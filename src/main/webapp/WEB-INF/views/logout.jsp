<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>

<%@include file="./include/header.jsp" %>

<% session.invalidate(); %>

<div class="container">
	
	<h3>로그아웃 되었습니다.</h3>
	
	<input class="btn btn-info" type="button" value="로그인" onclick="location.href='/';" />
</div>

<%@include file="./include/footer.jsp" %>
