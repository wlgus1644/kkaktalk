package net.nigne.kkt.chat;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import net.nigne.kkt.domain.ChattingVO;
import net.nigne.kkt.domain.EscrowVO;
import net.nigne.kkt.domain.MemberVO;
import net.nigne.kkt.service.ChattingService;
import net.nigne.kkt.service.EscrowService;
import net.nigne.kkt.service.MemberService;
import net.nigne.kkt.service.Msg_listService;
import net.nigne.kkt.service.User_listService;

public class WSHandler extends TextWebSocketHandler {

	private static final Logger logger = LoggerFactory.getLogger(WSHandler.class);
	
	private Set<WebSocketSession> wsSession = new HashSet<WebSocketSession>();
	
	public WSHandler(){
		logger.info("웹소켓 생성자입니다");
	}
	@Inject
	private ChattingService chatservice;
	@Inject
	private User_listService uservice;
	@Inject
	private Msg_listService msgservice;
	@Inject
	private MemberService memberService;
	@Inject
	private EscrowService escrowService;

	//1.연결됬을때
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		
		System.out.println("연결");
		wsSession.add(session);
		super.afterConnectionEstablished(session);
		
		String[] uri = session.getUri().toString().split("/");
		for(WebSocketSession s : wsSession){
			if(s.isOpen() && !s.getId().equals(session.getId()) && uri[2].equals(s.getUri().toString().split("/")[2])){
				try {
					s.sendMessage(new TextMessage("check1616!!!!!"));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
	//2.연결이 끊어졌을때
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		
		System.out.println("연결X");
		wsSession.remove(session);
		super.afterConnectionClosed(session, status);
		
	}

	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
		super.handleMessage(session, message);
		String msg = message.getPayload().toString();
		
		try{
			JsonParser parser = new JsonParser();
			JsonObject jsonObject = (JsonObject) parser.parse(msg);
			
			if("message".equals(jsonObject.get("type").getAsString())) {
				sendMessage(session, jsonObject);
			} else if("escrow".equals(jsonObject.get("type").getAsString())) {
				sendEscrow(session, jsonObject);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	private void sendMessage(WebSocketSession session, JsonObject jsonObject) {
		String[] uri = session.getUri().toString().split("/");
		ChattingVO vo = new ChattingVO();
		vo.setEmail(uri[3]);
		vo.setMsg(jsonObject.get("text").getAsString());
		vo.setChatting_room_no(Integer.parseInt(uri[2]));
		
		if(uservice.oppositeUserListDate(vo) == null) {
			uservice.updateOppDate(vo);
		}
		if(uservice.myUserListDate(vo) == null) {
			uservice.updateMyDate(vo);
		}
		vo.setNotice("0");
		chatservice.msgInsert(vo);
		for(WebSocketSession s : wsSession){
			if(s.isOpen() && !s.getId().equals(session.getId()) && uri[2].equals(s.getUri().toString().split("/")[2])){
				try {
					HashMap<String, Object> checkmsg = new HashMap<>();
					checkmsg.put("chatting_room_no", uri[2]);
					checkmsg.put("user_email", uri[3]);
					msgservice.checkmsg(checkmsg);
					
					jsonObject.addProperty("notice", "0");
					s.sendMessage(new TextMessage(jsonObject.toString()));
					session.sendMessage(new TextMessage("check1616!!!!!"));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
	private void sendEscrow(WebSocketSession session, JsonObject jsonObject) {
		
		String[] uri = session.getUri().toString().split("/");
		String msg = "";
		String notice = "";
		
		int noti = jsonObject.get("noti").getAsInt();
		////////////////////
		switch(noti) {
		case 1:
			int trade_no = jsonObject.get("trade_no").getAsInt();
			String friend_email = jsonObject.get("friend_email").getAsString();
			int amount = jsonObject.get("amount").getAsInt();
			String seller_account_num = jsonObject.get("account_num").getAsString();
			String bank_type = jsonObject.get("bank_type").getAsString();
			String producttitle = jsonObject.get("producttitle").getAsString();
			
			EscrowVO evo = new EscrowVO();
			evo.setSeller_account_num(seller_account_num);
			evo.setTrade_no(trade_no);
			evo.setAmount(amount);
			evo.setBank_type(bank_type);
			evo.setProducttitle(producttitle);
			
			MemberVO mvo = new MemberVO();
			// 계좌 있는지 체크
			mvo.setEmail(uri[3]);
			int checkAccount = memberService.checkAccount(mvo);
			int count = escrowService.selectAmountCount(evo.getTrade_no());
			msg = "최종 판매금액의 구매를 결정하시겠습니까?";
			
			mvo.setAccount_num(seller_account_num);
			mvo.setBank_type(bank_type);
	
			// 계좌 팝업관련
			if (checkAccount == 0) {
				memberService.updateAccount(mvo);
				escrowService.insertEscrow(evo);
				evo.setState("최종금액");
				escrowService.updateState(evo);
				notice = "1";
			} else {
	
				// 돈 입력 후, 공지띠워주기
				if (count == 0) {
					escrowService.insertEscrow(evo);
					evo.setState("최종금액");
					escrowService.updateState(evo);
					notice = "1";
				} else {
					escrowService.updateAmount(evo);
					evo.setState("최종금액");
					escrowService.updateState(evo);
					notice = "1";
				}
			}
			ChattingVO vo = new ChattingVO();
			vo.setEmail(uri[3]);
			vo.setMsg(msg);
			vo.setChatting_room_no(Integer.parseInt(uri[2]));
			vo.setNotice(notice);
		
			if(uservice.oppositeUserListDate(vo) == null) {
				uservice.updateOppDate(vo);
			}
			if(uservice.myUserListDate(vo) == null) {
				uservice.updateMyDate(vo);
			}
			chatservice.msgInsert(vo);
			break;
		case 2:
			msg = "입금이 확인되었습니다.";
			notice = "2";
			break;
		case 3:
			msg = "상품이 배송중입니다.";
			notice = "3";
			break;
		case 4:
			msg = "구매자의 상품수령이 확인되었습니다. 판매자에게 돈이 입금될 예정입니다.";
			notice = "4";
			break;
		case 5:
			msg = "구매자의 반품요청이 있습니다.";
			notice = "5";
			break;
		case 6:
			msg = "반품요청이 승인되었습니다.";
			notice = "6";
			break;
		case 7:
			msg = "반품상품이 배송중입니다.";
			notice = "7";
			break;
		case 8:
			msg = "반품상품이 수령되었습니다. 구매자에게 돈이 입금될 예정입니다.";
			notice = "8";
			break;
		}
		//////////////////////
		
		
		jsonObject = new JsonObject();
		jsonObject.addProperty("type", "notice");
		jsonObject.addProperty("text", msg);
		jsonObject.addProperty("notice", notice);
		
		for(WebSocketSession s : wsSession){
			if(s.isOpen() && !s.getId().equals(session.getId()) && uri[2].equals(s.getUri().toString().split("/")[2])){
				try {
					HashMap<String, Object> checkmsg = new HashMap<>();
					checkmsg.put("chatting_room_no", uri[2]);
					checkmsg.put("user_email", uri[3]);
					msgservice.checkmsg(checkmsg);
					
					s.sendMessage(new TextMessage(jsonObject.toString()));
					session.sendMessage(new TextMessage("check1616!!!!!"));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}

	//전송에러
	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		super.handleTransportError(session, exception);
	}

	@Override
	public boolean supportsPartialMessages() {
		return super.supportsPartialMessages();
	}
	
	
}
