<%@ page language="java" contentType="text/html; charset=utf-8"
   pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="true"%>
<%@include file="./include/header.jsp"%>
<div class="container">
	<nav class="navbar navbar-default navbar-static-top">
		<div class="navbar-header">
			<c:forEach items="${memberinfo}" var="vo">
				<tr>
					<td><img class="img-circle" width="40" height="40" src="/resources/profile/${vo.thumbnail}" /></td>
				</tr>
				<tr>
					<td>&nbsp&nbsp${vo.name}</td>
				</tr>
			</c:forEach>
		</div> 
		<ul class="nav navbar-top-links navbar-right">
			<li class="dropdown" id="menu">
				<a class="dropdown-toggle" data-toggle="dropdown" href="#">
					<i class="fa fa-ellipsis-h fa-fw" style="font-size: 18px"></i>
				</a>
		<ul class="dropdown-menu dropdown-user">
			<li style="width: 50px"><a href="/memberUpdate"><i class="fa fa-user fa-fw"></i>프로필수정</a></li>
			<li class="divider"></li>
			<li><a href="/pwchange"><i class="fa fa-sign-out fa-fw"></i>비밀번호변경</a></li>
			<li><a href="/logout"><i class="fa fa-sign-out fa-fw"></i>로그아웃</a></li></ul></li>
			<li class="dropdown"><a href="/friend_list"> <i class="fa fa-user fa-fw" style="font-size:18px"></i></a></li>
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
			<!-- <li class="dropdown"><a href="/chat_list"> <i class="fa fa-comments fa-fw" style="font-size:18px"></i></a></li> -->
			<li class="dropdown"><a href="/trade"><i class="glyphicon glyphicon-shopping-cart glyphicon-fw" style="font-size:18px"></i></a></li>
		</ul>   
	</nav>
<div class="panel panel-default">
	<div class="panel-heading" style="text-align: center;">채팅</div>
		<div class="panel-body">
			<div class="list-group">
				<c:forEach items="${chatlist}" var="chatlist" varStatus="status">
					<c:if test="${!empty chatlist}">
						<p class="list-group-item">
							<img class="img-circle" width="50" height="50" src="/resources/profile/${chatlist.thumbnail}" />&nbsp&nbsp&nbsp${chatlist.name}
							<span class="pull-right text-muted medium" style="margin-top: 8px;"> 
								<a href="/chat/${chatlist.email}">${msglist[status.index].msg}</a>
								<c:if test="${cntmsg[status.index] ne 0}">
									<span class="badge bg-theme" style="background-color: red">${cntmsg[status.index]} </span><br>
								</c:if>&nbsp&nbsp&nbsp
							</span>
						</p>
					</c:if>
				</c:forEach>
			</div>
		</div>
	</div>
</div>
<%@include file="./include/footer.jsp"%>