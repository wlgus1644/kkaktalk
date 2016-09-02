
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>

<%@include file="./include/header.jsp" %>
 
<script type="text/javascript">
   function onSearch(){
      var friendsearch = document.getElementById("friendsearch").value;
      
      if(friendsearch!=""){
         location.href="/friend_search/" + friendsearch;   
      }
   }
</script>
 
<div class="container">
   <div class="panel panel-default">
		<div class="panel-heading" style="text-align: center;">
			<a href="/friend_list"><i class="fa fa-chevron-left fa-5x" style="float: left; font-size:15px" ></i></a>친구찾기
		</div>
		<div class="panel-body">
			<div class="list-group">
				<input type="text" id="friendsearch" name="friendsearch"onkeypress="if(event.keyCode==13) {onSearch();}" 
				class="form-control" placeholder="친구찾기" style="text-align: center;">
				<span class="input-group-btn"></span>
				
				<c:forEach items="${friendSearch}" var="friendSearch" varStatus="status">
				<br/>
				<c:if test="${!empty friendSearch}">
					<p class="list-group-item">
						<img class="img-circle" width="50" height="50"
							src="/resources/profile/${friendSearch.thumbnail}" />&nbsp&nbsp&nbsp${friendSearch.name} 
							<span class="pull-right text-muted small">
								<a class="fa fa-plus-circle" href="/friendAdd/${friendSearch.email}" style="font-size:20px; margin-top: 12px"></a>
							</span>
						</p>
				</c:if>
				</c:forEach>
			</div>
		</div>
	</div>
	
</div>

<%@include file="./include/footer.jsp" %>


