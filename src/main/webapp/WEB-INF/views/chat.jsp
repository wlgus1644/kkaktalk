<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>
<%@include file="./include/chatheader.jsp" %>
<style>
.dropdown {
    position: relative;
    display: inline-block;
}
 
.dropdown-content {
    display: none;
    position: absolute;
    right: 0;
    background-color: #f9f9f9;
    min-width: 160px;
    box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
}
 
.dropdown-content a {
    color: black;
    padding: 12px 16px;
    text-decoration: none;
    display: block;
}
 
.dropdown-content a:hover {background-color: #f1f1f1}
 
.dropdown:hover .dropdown-content {
    display: block;
}
 
.dropdown:hover .dropbtn {
    background-color: #3e8e41;
}
 
#dropbtn {
	outline: 0; 
	border: 0; 
	margin-top: 7px; 
	margin-right: 10px; 
	background-color: Transparent;
}
</style>
<script type="text/javascript">
	var wsUrl = "ws://52.198.118.55/chatting/${chattingCheck.chatting_room_no}/${login_email}";
	var ws;
	var email = "<%=(String)session.getAttribute("login_email")%>";
	var myTime;
	var friendTime;
	var chatDay;
	var toggle = 0;	//0은 왼쪽 1 오른쪽 
	  
	var table_start = "<table class='notice'>";
	var friend_email = "<input type='hidden' id='friend_email' name='friend_email' value='${friend_email}'>";
	var chatting_room_no = "<input type='hidden'  id='chat_room' name='chat_room' value='${chattingCheck.chatting_room_no}'>";
	var notice = "<tr style='border: 1px solid black;'><th>공지가 등록되었습니다.</th></tr>";

	var check = "<tr style='border: 1px solid black;'><td><i style='font-size:24px' class='fa'>&#xf0f6;</i>글 확인하기<input type='button' onclick='Escrow(";
	
	
	
	var check2 = ")' class='w3-btn w3-light-grey w3-hover-Indigo w3-round-xxlarge' value='>'></td></tr>";
	var table_end = "</table>";
	var form_end = "</form>";
	
	function sendNotice(arg1, arg2) {
		showMessageRight(arg2, getTimeStamp(), 0, arg1);
		scroll();
 		var message = {
				type : "escrow",
				noti : arg1,
				sender : "${login_email}"
		};
		ws.send(JSON.stringify(message));
	}
	
 	function Escrow(arg) {
 		var Win = window.open('', 'escrow');
		var frm = document.getElementById("sendEscrow"+arg);
		frm.target = "escrow";
		frm.submit();
	} 

	$(document).ready(function() {
		//alert(wsUrl);
		ws = new WebSocket(wsUrl);

		ws.onopen = function(evt) {
			//alert(evt);
			onOpen(evt);
		};
		ws.onmessage = function(evt) {
			onMessage(evt);
		};
		ws.onerror = function(evt) {
			onError(evt);
		};
	});
	function onOpen(evt) {
		var messageWindow = document.getElementById("messageWindow");
		<c:forEach items="${msgList }" var="vo">
		if ("${vo.user_email}" == email) {
			showMessageRight("${vo.msg}", "${vo.date}", "${vo.check_user}", "${vo.notice}", "${vo.trade_no}");
		} else {
			showMessageLeft("${vo.msg}", "${vo.date}", "${vo.notice}");
		}

		</c:forEach>

		scroll();

		setInterval(function() {
			var message = {
					type : "connection",
					sender : "${login_email}"
			};
			ws.send(JSON.stringify(message));
		}, 5000);
	}
	function onMessage(evt) {
		if (evt.data == "check1616!!!!!") {
			$(".unChecked").remove();
			return 0;
		}
		var message = JSON.parse(evt.data);
		if(message.type == "message") {
			showMessageLeft(message.text, getTimeStamp(), message.notice);
		} else if(message.type == "notice") {
			showMessageLeft(message.text, getTimeStamp(), message.notice);
		}
		scroll();
	}
	function onError(evt) {
		showMessageRight("에러", getTimeStamp());
	}
	function scroll() {
		var scrollheight = document.body.scrollHeight;
		scrollheight = scrollheight + 20;
		document.body.scrollTop = scrollheight;
	}
	

	function showMessageLeft(msg, date, noti) {
		var time = date.substring(11, 16);
		var day = date.substring(0, 11);

		var messageWindow = document.getElementById("messageWindow");
		var Box = document.createElement("div");
		var thumb = document.createElement("img");
		var contents = document.createElement("div");

		var name = document.createElement("div");
		var msgBox = document.createElement("li");
		var detail = document.createElement("div");
		
		switch (Number(noti)) {
		case 1:
			msg = "<form id='sendEscrow1" + noti + "' action='/trade/katalk_detail/${trade.no}' method='GET'>"
							+ table_start
							+ friend_email
							+ chatting_room_no
							+ notice
							+ "<tr style='border: 1px solid black;'><td>"+ msg +"</td></tr>"
							+ check + "1" + noti + check2
							+ table_end + form_end;
			break;
		case 2:
			msg = "<form id='sendEscrow1" + noti + "' action='/trade/seller/${trade.no}' method='GET'>"
							+ table_start
							+ friend_email
							+ chatting_room_no
							+ notice
							+ "<tr style='border: 1px solid black;'><td>"+ msg +"</td></tr>"
							+ check + "1" + noti + check2
							+ table_end + form_end;
			break;
		case 3:
			msg = "<form id='sendEscrow1" + noti + "' action='/trade/buyer2/${trade.no}' method='GET'>"
							+ table_start
							+ friend_email
							+ chatting_room_no
							+ notice
							+ "<tr style='border: 1px solid black;'><td>"+ msg +"</td></tr>"
							+ check + "1" + noti + check2
							+ table_end + form_end;
			break;
		case 4:
			msg = "<form id='sendEscrow1" + noti + "' action='/trade/${trade.no}' method='GET'>"
							+ table_start
							+ friend_email
							+ chatting_room_no
							+ notice
							+ "<tr style='border: 1px solid black;'><td>"+ msg +"</td></tr>"
							+ table_end + form_end;
			break;
		case 5:
			msg = "<form id='sendEscrow1" + noti + "' action='/trade/seller3/${trade.no}' method='GET'>"
							+ table_start
							+ friend_email
							+ chatting_room_no
							+ notice
							+ "<tr style='border: 1px solid black;'><td>"+ msg +"</td></tr>"
							+ check + "1" + noti + check2
							+ table_end + form_end;
			break;
		case 6:
			msg = "<form id='sendEscrow1" + noti + "' action='/trade/buyer5/${trade.no}' method='GET'>"
							+ table_start
							+ friend_email
							+ chatting_room_no
							+ notice
							+ "<tr style='border: 1px solid black;'><td>"+ msg +"</td></tr>"
							+ check + "1" + noti + check2
							+ table_end + form_end;
			break;
		case 7:
			msg = "<form id='sendEscrow1" + noti + "' action='/trade/src/${trade.no}' method='GET'>"
							+ table_start
							+ friend_email
							+ chatting_room_no
							+ notice
							+ "<tr style='border: 1px solid black;'><td>"+ msg +"</td></tr>"
							+ check + "1" + noti + check2
							+ table_end + form_end;
			break;
		case 8:
			msg = "<form id='sendEscrow1" + noti + "' action='/trade/${trade.no}' method='GET'>"
							+ table_start
							+ friend_email
							+ chatting_room_no
							+ notice
							+ "<tr style='border: 1px solid black;'><td>"+ msg +"</td></tr>"
							+ "<tr style='border: 1px solid black;'></tr>"
							+ table_end + form_end;
			break;
		default:
			break;
		}
		if (chatDay != day) {
			var dayBox = document.createElement("div");
			dayBox.innerHTML = day;
			dayBox.className = "day";
			messageWindow.appendChild(dayBox);
		}
		chatDay = day;
		contents.appendChild(msgBox);
		detail.style.float = "left";
		detail.className = "detail";
		if (friendTime != time || toggle == 1) {
			thumb.src = "/resources/profile/${thumbnail}";
			thumb.className = "img-circle thumb";
			thumb.setAttribute("onclick", "thumb()");
			Box.appendChild(thumb);

			name.innerHTML = "${friend_name}";
			contents.insertBefore(name, contents.childNodes[0]);

			detail.innerHTML = time;

			msgBox.className = "odd";
			contents.appendChild(detail);
		} else {
			contents.style.clear = "both";
			contents.style.marginLeft = "50";
			contents.appendChild(detail);
			msgBox.className = "odd2";
			detail.innerHTML = time;
			var prev = document.getElementById("messageWindow").lastChild.lastChild;
			prev.removeChild(prev.lastChild);
		}
		friendTime = time;

		contents.className = "contents";
		msgBox.innerHTML = msg;

		Box.appendChild(contents);
		messageWindow.appendChild(Box);

		toggle = 0;
	}
	function showMessageRight(msg, date, check_user, noti) {
		var time = date.substring(11, 16);
		var day = date.substring(0, 11);

		var messageWindow = document.getElementById("messageWindow");
		var msgBox = document.createElement("li");
		var Box = document.createElement("div");
		var detail = document.createElement("div");
		var msgTime = document.createElement("div");
		
		switch (Number(noti)) {
		case 1:
			msg = "<form id='sendEscrow2" + noti + "' action='/trade/katalk_detail/${trade.no}' method='GET'>"
							+ table_start
							+ friend_email
							+ chatting_room_no
							+ notice
							+ "<tr style='border: 1px solid black;'><td>" + msg + "</td></tr>"
							+ check + "2" + noti + check2
							+ table_end + form_end;
			break;
		case 2:
			msg = "<form id='sendEscrow2" + noti + "' action='/trade/seller/${trade.no}' method='GET'>"
							+ table_start
							+ friend_email
							+ chatting_room_no
							+ notice
							+ "<tr style='border: 1px solid black;'><td>" + msg + "</td></tr>"
							+ check + "2" + noti + check2
							+ table_end + form_end;
			break;
		case 3:
			msg = "<form id='sendEscrow2" + noti + "' action='/trade/buyer2/${trade.no}' method='GET'>"
							+ table_start
							+ friend_email
							+ chatting_room_no
							+ notice
							+ "<tr style='border: 1px solid black;'><td>" + msg + "</td></tr>"
							+ check + "2" + noti + check2
							+ table_end + form_end;
			break;
		case 4:
			msg = "<form id='sendEscrow2" + noti + "' action='/trade/${trade.no}' method='GET'>"
							+ table_start
							+ friend_email
							+ chatting_room_no
							+ notice
							+ "<tr style='border: 1px solid black;'><td>" + msg + "</td></tr>"
							+ table_end + form_end;
			break;
		case 5:
			msg = "<form id='sendEscrow2" + noti + "' action='/trade/seller3/${trade.no}' method='GET'>"
							+ table_start
							+ friend_email
							+ chatting_room_no
							+ notice
							+ "<tr style='border: 1px solid black;'><td>" + msg + "</td></tr>"
							+ check + "2" + noti + check2
							+ table_end + form_end;
			break;
		case 6:
			msg = "<form id='sendEscrow2" + noti + "' action='/trade/buyer5/${trade.no}' method='GET'>"
							+ table_start
							+ friend_email
							+ chatting_room_no
							+ notice
							+ "<tr style='border: 1px solid black;'><td>" + msg + "</td></tr>"
							+ check + "2" + noti + check2
							+ table_end + form_end;
			break;
		case 7:
			msg = "<form id='sendEscrow2" + noti + "' action='/trade/src/${trade.no}' method='GET'>"
							+ table_start
							+ friend_email
							+ chatting_room_no
							+ notice
							+ "<tr style='border: 1px solid black;'><td>" + msg + "</td></tr>"
							+ check + "2" + noti + check2
							+ table_end + form_end;
			break;
		case 8:
			msg = "<form id='sendEscrow2" + noti + "' action='/trade/${trade.no}' method='GET'>"
							+ table_start
							+ friend_email
							+ chatting_room_no
							+ notice
							+ "<tr style='border: 1px solid black;'><td>" + msg + "</td></tr>"
							+ "<tr style='border: 1px solid black;'></tr>"
							+ table_end + form_end;
			break;
		default:
			break;
		}

		if (chatDay != day) {
			var dayBox = document.createElement("div");
			dayBox.innerHTML = day;
			dayBox.className = "day";
			messageWindow.appendChild(dayBox);
		}
		chatDay = day;

		if (check_user == 0) {
			var checkMsg = document.createElement("div");
			detail.appendChild(checkMsg);
			checkMsg.style.textAlign = "right";
			checkMsg.innerHTML = 1;
			checkMsg.style.color = "red";
			checkMsg.className = "unChecked";
		}
		Box.appendChild(detail);
		detail.appendChild(msgTime);
		msgTime.innerHTML = time;
		detail.style.display = "inline-block";
		Box.className = "BoxRight";
		if (myTime == time && toggle == 1) {
			var prev = document.getElementById("messageWindow").lastChild;
			var prevCheck = prev.firstChild.firstChild.innerHTML;
			prev.removeChild(prev.firstChild);
			if (prevCheck == 1) {
				var checkBox = document.createElement("div");
				var detail = document.createElement("div");
				checkBox.innerHTML = "1";
				checkBox.className = "unChecked";
				detail.style.display = "inline-block";
				checkBox.style.color = "red";
				prev.insertBefore(detail, prev.childNodes[0]);
				detail.appendChild(checkBox);
			}
			msgBox.className = "even2";
		} else {
			msgBox.className = "even";
		}
		myTime = time;

		Box.appendChild(msgBox);
		msgBox.innerHTML = msg;

		messageWindow.appendChild(Box);
		toggle = 1;
	}
	function sendMessage() {
		var msg = document.getElementById("msg");
		if (msg.value != "") {
			<c:forEach items="${memberinfo }" var="vo">
			showMessageRight(msg.value, getTimeStamp(), 0, 0);
			</c:forEach>
			
			var message = {
					type : "message",
					text : msg.value,
					sender : "${login_email}"
				};
			ws.send(JSON.stringify(message));
			scroll();
			msg.value = "";
			msg.focus();
		}
	}
	function exit() {
		ws.send("exit");
		document.getElementById("exit").submit();
	}
	function getTimeStamp() {
		var d = new Date();
		var s = leadingZeros(d.getFullYear(), 4) + '-'
				+ leadingZeros(d.getMonth() + 1, 2) + '-'
				+ leadingZeros(d.getDate(), 2) + ' ' +

				leadingZeros(d.getHours(), 2) + ':'
				+ leadingZeros(d.getMinutes(), 2) + ':'
				+ leadingZeros(d.getSeconds(), 2);

		return s;
	}
	function leadingZeros(n, digits) {
		var zero = '';
		n = n.toString();

		if (n.length < digits) {
			for (i = 0; i < digits - n.length; i++)
				zero += '0';
		}
		return zero + n;
	}

	function thumb() {
		window.open("friendAdd/${friend_email}", 'newWindow',
				'width =300, height=350');
	}
</script>
<div class="panel panel-default">
<div class="panel panel-heading" style="text-align: center; position:fixed; top:0; left:0; z-index:10; width:100%; height: 50px; padding : 3px;" >${friend_name}
  <a href="/chat_list"><i class="fa fa-chevron-left fa-5x" style="float: left; font-size:15px; margin-top: 10px" >&nbsp뒤로가기</i></a>

  <div class="dropdown" style="float: right;">

   <button class="fa fa-ellipsis-h fa-fw" id="dropbtn"></button>

   <div class="dropdown-content">
    <a href="/chat/exit/${chattingCheck.chatting_room_no}"><i class="fa fa-user fa-fw"></i>나가기</a> 
    <a id="money" href="#"><i class="glyphicon glyphicon-transfer"></i>에스크로</a>
   </div>
  </div>
  <%--     <ul class="nav navbar-top-links navbar-right" style="float:right;">
   <li class="dropdown">
<button class="dropbtn fa fa-ellipsis-h fa-fw"
     style="outline: 0; border: 0; margin-top: 7px; margin-right: 10px; background-color: Transparent;"></button>
    </a>

    <ul class="dropdown-menu dropdow-user" >
     <li><a href="/chat/exit/${chattingCheck.chatting_room_no}"><i class="fa fa-user fa-fw"></i>나가기</a></li>
     <li><a id = "money" href="#"><i class="glyphicon glyphicon-transfer"></i>에스크로</a></li>
    </ul>
   </li>
  </ul> --%>
 </div>
</div>
 

	<div id="msgpanel" style="margin-top: 45px; height: 100%;">
		<div class="container" style="margin-bottom: 40px">
			<ul id="messageWindow" class="chat-box"></ul>
		</div>
		<div style="position:fixed; bottom:0;">
			<div class="input-group">
				<c:choose>
	    			<c:when test="${friend_email ne '알수없는 사람' }">
						<input type="text" id="msg" name="msg" class="form-control input-sm" style="height: 35px" onkeyup="if(event.keyCode == 13){sendMessage();}" >
						<span class="input-group-btn">
							<button class="btn btn-warning btn-sm" style="height: 35px" onclick="sendMessage()" >전송</button>
						</span>
					</c:when>
				</c:choose>
			
			</div>
		</div>	
	</div>


		
		<!-- Modal -->
		<div class="modal fade" id="myModal" role="dialog">
			<div class="modal-dialog">

				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header" style="padding: 35px 50px;">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4>
							<span class="glyphicon glyphicon-lock"></span> 에스크로 거래
						</h4>
					</div>

					<form name="clear_form" id="clear_form"
						action="/trade/chat/insertAmount" method="post">
						<div class="modal-body" style="padding: 40px 50px;">

							<div class="form-group">
								<label for="usrname"><span
									class="glyphicon glyphicon-user"></span> 품목명</label> <input
									type="hidden" id="friend_email" name="friend_email"
									value="${friend_email}" /> <input type="hidden" id="trade_no"
									name="trade_no" value="${trade.no}" /> <input type="text"
									class="form-control" id="producttitle" name="producttitle"
									value="${trade.title}" readonly>
							</div>
							<div class="form-group">
								<label for="psw"><span
									class="glyphicon glyphicon-eye-open"></span> 금액</label> <input
									type="text" class="form-control" id="amount" name="amount"
									placeholder="최종 금액을 입력하세요">
							</div>



							<c:set var="checkAccount" value="${checkAccount}" />
							<c:choose>
								<c:when test="${checkAccount !=0}">
									<div>
										<label for="seller_account_num"><span
											class="glyphicon glyphicon-eye-open"></span> 계좌번호</label>

										<c:forEach items="${memberinfo }" var="vo">
											<select id="bank_type" name="bank_type"
												value="${vo.bank_type}">

												<c:if test="${vo.bank_type=='국민은행'}">
													<option value="${vo.bank_type}" selected="selected">국민은행</option>
												</c:if>
												<c:if test="${vo.bank_type=='농협은행'}">
													<option value="${vo.bank_type}" selected="selected">농협은행</option>
												</c:if>
												<c:if test="${vo.bank_type=='우리은행'}">
													<option value="${vo.bank_type}" selected="selected">우리은행</option>
												</c:if>
												<c:if test="${vo.bank_type=='하나은행'}">
													<option value="${vo.bank_type}" selected="selected">하나은행</option>
												</c:if>
												<c:if test="${vo.bank_type=='신한은행'}">
													<option value="${vo.bank_type}" selected="selected">신한은행</option>
												</c:if>
												<c:if test="${vo.bank_type=='기업은행'}">
													<option value="${vo.bank_type}" selected="selected">기업은행</option>
												</c:if>
											</select>
											<input type="text" class="form-control" id="account_num"
												name="account_num" placeholder="계좌번호를 입력하세요"
												value="${vo.account_num}" readonly>
          	 		*다른 계좌 이용 시, 내 정보에서 수정해주세요
      			 </c:forEach>
									</div>
								</c:when>
								<c:otherwise>
									<div>
										<label for="seller_account_num"><span
											class="glyphicon glyphicon-eye-open"></span> 계좌번호</label> <select
											id="bank_type" name="bank_type">
											<option value="국민은행">국민은행</option>
											<option value="농협은행">농협은행</option>
											<option value="우리은행">우리은행</option>
											<option value="하나은행">하나은행</option>
											<option value="신한은행">신한은행</option>
											<option value="기업은행">기업은행</option>
										</select> <input type="text" class="form-control" id="account_num"
											name="account_num" placeholder="계좌번호를 입력하세요"> *다른
										계좌 이용 시, 내 정보에서 수정해주세요

									</div>

								</c:otherwise>
							</c:choose>

						</div>
						<input type='hidden'  id='chat_room' name='chat_room' value='${chattingCheck.chatting_room_no}'>
					</form>
					<input type="button" onclick="register()" id="register"
						
						class="btn btn-default btn-block" value="등록완료">
					<!--  <span class="glyphicon glyphicon-off" ></span> -->
				</div>
			</div>
		</div>




<%@include file="./include/footer.jsp" %>
