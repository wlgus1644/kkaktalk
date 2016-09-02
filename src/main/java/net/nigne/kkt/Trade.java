package net.nigne.kkt;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.JsonObject;

import net.nigne.kkt.Trade;
import net.nigne.kkt.chat.WSHandler;
import net.nigne.kkt.domain.ChattingVO;
import net.nigne.kkt.domain.EscrowVO;
import net.nigne.kkt.domain.FriendsVO;
import net.nigne.kkt.domain.MemberVO;
import net.nigne.kkt.domain.Msg_listVO;
import net.nigne.kkt.domain.ProductImageVO;
import net.nigne.kkt.domain.TradeVO;
import net.nigne.kkt.service.TradeService;
import net.nigne.kkt.service.User_listService;
import net.nigne.kkt.service.ChattingService;
import net.nigne.kkt.service.EscrowService;
import net.nigne.kkt.service.MemberService;
import net.nigne.kkt.service.Msg_listService;
import net.nigne.kkt.service.ProductImageService;

@RestController
@RequestMapping("/trade")
public class Trade {

	@Resource(name = "productUploadPath")
	private String productUploadPath;

	@Inject
	private EscrowService escrowService;
	@Inject
	private ChattingService chatservice;
	@Inject
	private TradeService tradeservice;
	@Inject
	private MemberService memberService;
	@Inject
	private ProductImageService productImageservice;
	@Inject
	private Msg_listService msgservice;
	@Inject
	private User_listService uservice;

	// /유닛/ 목록
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView list(Locale locale, Model model, HttpSession session) {

		ModelAndView aaa = new ModelAndView();
		
		if (session.getAttribute("login_email") == null || session.getAttribute("login_email").equals("")) {
			
			model.addAttribute("message", "다시 로그인해주세요.");
			model.addAttribute("returnUrl", "/");
			aaa.setViewName("alertAndRedirect");

			return aaa;
			
		} else {
			
			String login_email = (String) session.getAttribute("login_email");
			List<MemberVO> member_list = memberService.info(login_email);
			aaa.addObject("memberinfo", member_list);

			MemberVO mvo = new MemberVO();
			mvo.setEmail(login_email);

			// 계좌 있는지 체크
			int checkAccount = memberService.checkAccount(mvo);
			System.out.println("checkAccount :" + checkAccount);
			aaa.addObject("checkAccount", checkAccount);

			List<TradeVO> trade_list = tradeservice.getList();
			aaa.addObject("trade_list", trade_list);

			List<ProductImageVO> uploadFileList = productImageservice.getList();
			aaa.addObject("uploadFileList", uploadFileList);

			chatinfo(session, login_email);

			aaa.setViewName("trade_list");
			
			return aaa;
			
		}

	}

	public void chatinfo(HttpSession session, String login_email) {

		int totalmsg = 0;

		List<MemberVO> volist = new ArrayList<MemberVO>();
		List<Msg_listVO> msglist = new ArrayList<Msg_listVO>();
		List cntmsg = new ArrayList();
		List roomlist = uservice.roomlist(login_email);

		HashMap<String, Object> chatinfo = new HashMap<>();
		chatinfo.put("user_email", login_email);

		Iterator iterator = roomlist.iterator();

		while (iterator.hasNext()) {

			String room = iterator.next().toString();
			int length = room.length();
			String roomNo = room.substring(18, length - 1);

			System.out.println("방번호 : " + roomNo);

			chatinfo.put("roomNo", roomNo);

			System.out.println("상대방이메일 : " + msgservice.getFriend(chatinfo));

			chatinfo.put("friend_email", msgservice.getFriend(chatinfo));

			if (msgservice.getFriend(chatinfo) == null || msgservice.getFriend(chatinfo).equals("")) {
				cntmsg.add(0);
			} else {

				totalmsg += msgservice.cntmsg(chatinfo);
				System.out.println("읽지 않은 메세지 : " + totalmsg);
				cntmsg.add(msgservice.cntmsg(chatinfo));

			}

			volist.add(memberService.chatlist(chatinfo));
			msglist.add(msgservice.lastmsg(chatinfo));

		}

		session.setAttribute("chatlist", volist);
		session.setAttribute("msglist", msglist);
		session.setAttribute("cntmsg", cntmsg);
		session.setAttribute("totalmsg", totalmsg);

	}

	// 계좌번호 등록
	@RequestMapping(value = "/updateAccount", method = RequestMethod.POST)
	public ModelAndView checkAccount(HttpSession session, @RequestParam("bank_type") String bank_type,
			@RequestParam("account_num") String account_num) {

		ModelAndView mv = new ModelAndView();
		mv.setViewName("trade_list");

		String login_email = (String) session.getAttribute("login_email");

		MemberVO mvo = new MemberVO();

		mvo.setEmail(login_email);
		mvo.setBank_type(bank_type);
		mvo.setAccount_num(account_num);

		memberService.updateAccount(mvo);
		mv.addObject("checkAccount", "up"); // null값 아닌 값 넣어주려고

		return new ModelAndView("redirect:/trade");

	}

	// /유닛/글번호 상세
	   @RequestMapping(value = "/{no}", method = RequestMethod.GET)
	   public ModelAndView List(@PathVariable("no") Integer no, HttpSession session) {

	      ModelAndView aaa = new ModelAndView();
	      aaa.setViewName("detail");

	      String login_email = (String) session.getAttribute("login_email");
	      List<MemberVO> member_list = memberService.info(login_email);
	      aaa.addObject("memberinfo", member_list);

	      List<ProductImageVO> vo2 = productImageservice.getList();
	      aaa.addObject("vo2", vo2);

	      TradeVO vo = tradeservice.get(no);
	      aaa.addObject("vo", vo);
	      
	      //System.out.println("게시판 번호는?"+no);
	      String thumbnail=tradeservice.thumbnail(no);
	      //System.out.println("썸네일은>>>>>>>>>>>>>>>>???"+thumbnail);
	      aaa.addObject("thumbnail", thumbnail);
	      return aaa;
	   }

	// /유닛/글번호 수정 -> update 페이지 호출
	@RequestMapping(value = "/{no}/updateList", method = RequestMethod.GET)
	public ModelAndView update(@PathVariable("no") Integer no) {
		ModelAndView aaa = new ModelAndView();
		aaa.setViewName("update");

		TradeVO vo = tradeservice.get(no);
		aaa.addObject("vo", vo);

		List<ProductImageVO> vo2 = productImageservice.getList();
		aaa.addObject("vo2", vo2);
		return aaa;
	}

	// 사진수정
	@Transactional
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ModelAndView update_fileUpload(@RequestParam("no") int no, HttpServletRequest req,
			MultipartHttpServletRequest mhsq, TradeVO vo) throws IllegalStateException, IOException {

		tradeservice.updateProduct(vo);
		List<MultipartFile> mf = mhsq.getFiles("file");
		System.out.println("mf=" + mf);
		File dir = new File(productUploadPath);
		if (!dir.isDirectory()) {
			dir.mkdirs();
		}
		if (mf.size() == 1 && mf.get(0).getOriginalFilename().equals("")) {
		} else {
			for (int i = 0; i < mf.size(); i++) {
				// 파일 중복명 처리
				String genId = UUID.randomUUID().toString();
				// 본래 파일명
				String originalfileName = mf.get(i).getOriginalFilename();
				System.out.println("11111" + originalfileName);

				String saveFileName = genId + "." + originalfileName;
				// 저장되는 파일 이름
				System.out.println("2222222" + saveFileName);
				String savePath = productUploadPath + saveFileName; // 저장 될 파일														// 경로
				System.out.println("333333" + savePath);

				// int fileSize = mf.get(i).getSize(); // 파일 사이즈
				mf.get(i).transferTo(new File(savePath)); // 파일 저장
				productImageservice.uploadFile(no, originalfileName, saveFileName);
			}
		}
		return new ModelAndView("redirect:/trade");
	}

	// 검색결과
	@RequestMapping(value = "/searchList", method = RequestMethod.GET)
	public ModelAndView searchList(@RequestParam(required = false) String keyfield,
			@RequestParam(required = false) String keyword, HttpSession session) {
		ModelAndView bbb = new ModelAndView();
		bbb.setViewName("trade_list");

		System.out.println("keyfield=" + keyfield + "이고, keyword=" + keyword);
		Map<String, String> map = new HashMap<String, String>();
		map.put("keyfield", keyfield);
		map.put("keyword", keyword);
		List<TradeVO> search_list = tradeservice.getSearchList(map);
		bbb.addObject("trade_list", search_list);
		
		String login_email = (String)session.getAttribute("login_email");
		List<MemberVO> memberinfo = memberService.info(login_email);
		bbb.addObject("memberinfo", memberinfo);
		
		List<ProductImageVO> uploadFileList = productImageservice.getList();
		bbb.addObject("uploadFileList", uploadFileList);

		return bbb;
	}

	// /유닛/new 글쓰기 페이지
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public ModelAndView write() {
		ModelAndView aaa = new ModelAndView();
		aaa.setViewName("write");

		return aaa;
	}

	// 사진등록
	@Transactional
	@RequestMapping(value = "", method = RequestMethod.POST)
	public ModelAndView fileUpload(HttpServletRequest req, MultipartHttpServletRequest mhsq, TradeVO vo,
			HttpSession session) throws IllegalStateException, IOException {

		String login_email = (String) session.getAttribute("login_email");
		vo.setMember_email(login_email);
		System.out.println("login_email=" + login_email);

		tradeservice.insertProduct(vo);
		int no = tradeservice.getNo();
		List<MultipartFile> mf = mhsq.getFiles("file");
		File dir = new File(productUploadPath);
		if (!dir.isDirectory()) {
			dir.mkdirs();
		}
		if (mf.size() == 1 && mf.get(0).getOriginalFilename().equals("")) {
		} else {
			for (int i = 0; i < mf.size(); i++) {
				// 파일 중복명 처리
				String genId = UUID.randomUUID().toString();
				// 본래 파일명
				String originalfileName = mf.get(i).getOriginalFilename();
				System.out.println("11111" + originalfileName);

				String saveFileName = genId + "." + originalfileName;
				// 저장되는 파일 이름
				System.out.println("2222222" + saveFileName);

				String savePath = productUploadPath + saveFileName; // 저장 될 파일
																	// 경로
				System.out.println("333333" + savePath);

				// int fileSize = mf.get(i).getSize(); // 파일 사이즈
				mf.get(i).transferTo(new File(savePath)); // 파일 저장
				productImageservice.uploadFile(no, originalfileName, saveFileName);
			}
		}
		return new ModelAndView("redirect:/trade");
	}

	// /유닛/번호 삭제
	@RequestMapping(value = "/{no}", method = RequestMethod.DELETE)
	public ModelAndView delete(@PathVariable("no") Integer no) {
		System.out.println("no=?" + no);
		 productImageservice.delete(no); //외래키의 속성으로 이걸 먼저 지워줘야 삭제가 됨!!!
		tradeservice.deleteProduct(no);
		return new ModelAndView("redirect:/trade");

	}

	// /유닛/사진 삭제
	@RequestMapping(value = "/{no}/{image_no}", method = RequestMethod.POST)
	public ModelAndView delete_image(@PathVariable("no") Integer trade_no, @PathVariable("image_no") Integer image_no) {
		System.out.println("no=?" + image_no);
		// productImageservice.delete(no); //외래키의 속성으로 이걸 먼저 지워줘야 삭제가 됨!!!
		productImageservice.delete_image(image_no);

		return new ModelAndView("redirect:/trade/{no}/updateList");

	}

	// 친구추가후 채팅
	@RequestMapping(value = "/add_chat/{friend_email:.+}", method = RequestMethod.POST)
	public ModelAndView chat(MemberVO mvo, FriendsVO fvo, ChattingVO chatvo,
			@PathVariable("friend_email") String friend_email, HttpSession session, HttpServletRequest request) {

		ModelAndView view = new ModelAndView();
		System.out.println(chatvo.getTrade_no());

		String login_email = (String) session.getAttribute("login_email");

		fvo.setFriend_email(friend_email);
		fvo.setMember_email(login_email);

		List<FriendsVO> alreadyFriend = memberService.alreadyFriend(fvo.getMember_email(), fvo.getFriend_email());

		if (alreadyFriend.isEmpty()) {// 친구로 등록이 안되있을경우 친구추가
			memberService.friendAdd(fvo);
		}

		chatvo.setEmail(login_email);
		chatvo.setBuyer_email(login_email);
		ChattingVO chattingCheck = chatservice.chattingCheck(login_email, friend_email);

		if (chattingCheck == null) {
			System.out.println("채팅방생성");
			chatservice.chattingInsert();

			int chattingRoomNO = chatservice.chattingRoomNO();
			chatvo.setNo(chattingRoomNO);

			chatservice.chattingUpdate(chatvo);
			System.out.println("chattingRoomNO:" + chattingRoomNO);

			chatservice.userListInsert(chattingRoomNO, login_email);
			chatservice.userListInsert(chattingRoomNO, friend_email);
		} else {
			chatvo.setNo(chatservice.chattingCheck(login_email, friend_email).getChatting_room_no());
			chatservice.chattingUpdate(chatvo);
		}
		chattingCheck = chatservice.chattingCheck(login_email, friend_email);
		view.addObject("chattingCheck", chattingCheck);

		chatvo.setChatting_room_no(chattingCheck.getChatting_room_no());
		if (uservice.myUserListDate(chatvo) == null) {
			uservice.updateMyDate(chatvo);
		}
		String date = uservice.myUserListDate(chatvo).substring(0, 19);

		List<ChattingVO> msgList = chatservice.msgList(login_email, friend_email, date);
		view.addObject("msgList", msgList);

		List<MemberVO> memberinfo = memberService.info(login_email);
		view.addObject("memberinfo", memberinfo);

		mvo = memberService.oneInfo(friend_email);
		view.addObject("friend_email", friend_email);
		view.addObject("friend_name", mvo.getName());
		view.addObject("thumbnail", mvo.getThumbnail());
		ChattingVO chat_room = chatservice
				.chattingRoomInfo(chatservice.chattingCheck(login_email, friend_email).getChatting_room_no());
		view.addObject("chat_room", chat_room);

		HashMap<String, Object> checkmsg = new HashMap<String, Object>();

		checkmsg.put("chatting_room_no", chattingCheck.getChatting_room_no());
		checkmsg.put("user_email", friend_email);
		System.out.println("no :" + chattingCheck.getChatting_room_no() + ", friend :" + friend_email);
		msgservice.checkmsg(checkmsg);

		view.addObject("trade", tradeservice.get(chat_room.getTrade_no()));

		view.setViewName("chat");

		return view;
	}

	// 최종금액 입력
	@RequestMapping(value = "/chat/insertAmount", method = RequestMethod.POST)
	public ModelAndView insertAmount(HttpSession session, @RequestParam("chat_room") int chat_room,
			@RequestParam("friend_email") String friend_email, EscrowVO evo, MemberVO mvo)
			throws IllegalStateException, IOException {
		

		System.out.println("seller_account_num=" + mvo.getAccount_num());
		String seller_account_num = mvo.getAccount_num();
		evo.setSeller_account_num(seller_account_num);

		System.out.println("상품명=" + evo.getProducttitle());
		System.out.println("거래번호=" + evo.getTrade_no());
		System.out.println("amt=" + evo.getAmount());
		System.out.println("계좌번호=" + evo.getSeller_account_num());
		System.out.println("은행타입=" + evo.getBank_type());

		// 계좌 있는지 체크
		String login_email = (String) session.getAttribute("login_email");
		mvo.setEmail(login_email);
		System.out.println("login한 사람 =" + mvo.getEmail());
		int checkAccount = memberService.checkAccount(mvo);
		int count = escrowService.selectAmountCount(evo.getTrade_no());
		String msg = "최종 판매금액의 구매를 결정하시겠습니까?";

		System.out.println("checkAccount=" + checkAccount);

		mvo.setAccount_num(evo.getSeller_account_num());
		mvo.setBank_type(evo.getBank_type());
		mvo.setEmail(login_email);

		// 계좌 팝업관련
		if (checkAccount == 0) {
			memberService.updateAccount(mvo);
			escrowService.insertEscrow(evo);
			evo.setState("최종금액");
			escrowService.updateState(evo);
			ChattingVO vo = new ChattingVO();
			vo.setEmail(login_email);
			vo.setMsg(msg);
			vo.setNotice("1");
			vo.setChatting_room_no(chat_room);
			chatservice.msgInsert(vo);

		} else {

			System.out.println("count=" + count);

			// 돈 입력 후, 공지띠워주기
			if (count == 0) {
				// 없으면 새로 만들어주고
				ChattingVO vo = new ChattingVO();
				vo.setEmail(login_email);
				vo.setMsg(msg);
				vo.setNotice("1");
				vo.setChatting_room_no(chat_room);
				chatservice.msgInsert(vo);
				escrowService.insertEscrow(evo);
				evo.setState("최종금액");
				escrowService.updateState(evo);

			} else {
				// msgInsert: no, msg, user_email, check_user, chatting_room_no,
				// notice
				ChattingVO vo = new ChattingVO();
				vo.setEmail(login_email);
				vo.setMsg(msg);
				vo.setNotice("1");
				vo.setChatting_room_no(chat_room);
				chatservice.msgInsert(vo);
				escrowService.updateAmount(evo);
				evo.setState("최종금액");
				escrowService.updateState(evo);
			}
		}
		return new ModelAndView("redirect:/chat/" + friend_email);
	}

	// 카톡에서 중고나라 글 상세보기
	@RequestMapping(value = "/katalk_detail/{trade_no}", method = RequestMethod.GET)
	public ModelAndView katalkDetail(@RequestParam("chat_room") int chat_room, @PathVariable("trade_no") int trade_no,
			@RequestParam("friend_email") String friend_email, HttpSession session) {

		System.out.println("@@@@@chat_room : " + chat_room);
		ModelAndView view = new ModelAndView();
		view.setViewName("katalk_detail");
		// 회원
		String login_email = (String) session.getAttribute("login_email");
		List<MemberVO> member_list = memberService.info(login_email);
		view.addObject("member", member_list);

		// 게시판
		TradeVO trade_vo = tradeservice.get(trade_no);
		view.addObject("trade", trade_vo);

		// 이미지
		List<ProductImageVO> imgvo = productImageservice.getList();
		view.addObject("imgvo", imgvo);

		// 에스크로
		EscrowVO escrow_vo = escrowService.get(trade_no);
		System.out.println("escrow_vo.no :" + escrow_vo.getNo());
		view.addObject("escrow", escrow_vo);
		view.addObject("friend_email", friend_email);
		view.addObject("room_No", chat_room);

		String thumbnail=tradeservice.thumbnail(trade_no);
		view.addObject("thumbnail", thumbnail);
		
		return view;
	}

	// 카톡방으로 다시
	@RequestMapping(value = "/chat/rekatalk", method = RequestMethod.GET)
	public ModelAndView reKatalk(@RequestParam("chat_room") int chat_room,
			@RequestParam("friend_email") String friend_email, HttpSession session)
			throws IllegalStateException, IOException {

		String login_email = (String) session.getAttribute("login_email");
		ChattingVO vo = new ChattingVO();
		vo.setEmail(login_email);
		vo.setChatting_room_no(chat_room);

		return new ModelAndView("redirect:/chat/" + friend_email);
	}

	// 배송지정보입력 페이지 이동
	@RequestMapping(value = "/buyer", method = RequestMethod.POST)
	public ModelAndView buyer(@RequestParam("friend_email") String friend_email,
			@RequestParam("chat_room") int chat_room, @RequestParam("trade_no") int trade_no, HttpSession session) {

		ModelAndView view = new ModelAndView();
		// 회원
		String login_email = (String) session.getAttribute("login_email");
		List<MemberVO> member_list = memberService.info(login_email);
		view.addObject("member", member_list);

		// 게시판
		TradeVO trade_vo = tradeservice.get(trade_no);
		view.addObject("trade", trade_vo);

		// 이미지
		List<ProductImageVO> imgvo = productImageservice.getList();
		view.addObject("imgvo", imgvo);

		// 에스크로
		EscrowVO escrow_vo = escrowService.get(trade_no);
		view.addObject("escrow", escrow_vo);
		view.addObject("friend_email", friend_email);
		view.addObject("room_No", chat_room);

		view.setViewName("buyer1");
		return view;
	}

	// 우편번호검색 페이지 이동
	@RequestMapping(value = "/zipcode", method = RequestMethod.GET)
	public ModelAndView zipcode(HttpSession session) {

		ModelAndView view = new ModelAndView();
		view.setViewName("zipcode");
		return view;
	}

	// 배송지저장
	@Transactional
	@RequestMapping(value = "/updateAddress", method = RequestMethod.POST)
	public ModelAndView updateAddress(EscrowVO vo, @RequestParam("friend_email") String friend_email,
			@RequestParam("chat_room") int chat_room, @RequestParam("trade_no") int trade_no, HttpSession session)
			throws IllegalStateException, IOException {
		vo.setAddress(vo.getAddress() + vo.getAddress2());
		vo.setPostcode(vo.getPostcode1()+vo.getPostcode2());
		escrowService.updateEscrow(vo);
		vo.setState("배송지저장");
		escrowService.updateState(vo);
		ModelAndView view = new ModelAndView();
		// 회원
		String login_email = (String) session.getAttribute("login_email");
		List<MemberVO> member_list = memberService.info(login_email);
		view.addObject("member", member_list);

		// 게시판
		TradeVO trade_vo = tradeservice.get(trade_no);
		view.addObject("trade", trade_vo);

		// 이미지
		List<ProductImageVO> imgvo = productImageservice.getList();
		view.addObject("imgvo", imgvo);

		// 에스크로
		EscrowVO escrow = escrowService.get(trade_no);
		System.out.println("trade_no=" + trade_no);
		view.addObject("escrow", escrow);
		System.out.println("escrowNO = " + escrow.getNo());
		view.addObject("friend_email", friend_email);
		view.addObject("room_No", chat_room);

		view.setViewName("escrow_detail");
		return view;
	}

	// 우편번호검색
	@RequestMapping(value = "/zipCheck", method = RequestMethod.GET)
	public ModelAndView zipCheck() {
		ModelAndView view = new ModelAndView();
		System.out.println("컨트롤러는 넘어옴");
		view.setViewName("zipCheck");

		return view;
	}

	// 카톡방으로 다시2
	@RequestMapping(value = "/chat/rekatalk2", method = RequestMethod.GET)
	public ModelAndView reKatalk2(@RequestParam("chat_room") int chat_room,
			@RequestParam("friend_email") String friend_email, HttpSession session)
			throws IllegalStateException, IOException {

		String login_email = (String) session.getAttribute("login_email");
		ChattingVO vo = new ChattingVO();
		EscrowVO evo = new EscrowVO();
		evo.setState("입금확인");
		escrowService.updateState(evo);
		vo.setEmail(login_email);
		vo.setChatting_room_no(chat_room);
		ChattingVO cvo = new ChattingVO();
		String msg = "입금이 확인되었습니다.";
		cvo.setEmail(login_email);
		cvo.setChatting_room_no(chat_room);
		cvo.setMsg(msg);
		cvo.setNotice("2");
		chatservice.msgInsert(cvo);
		
		ModelAndView temp = new ModelAndView();
		temp.addObject("noticeNum", 2);
		temp.setViewName("popUp");
		
		return temp;
	}

	// 팬매자 배송페이지
	@RequestMapping(value = "/seller/{trade_no}", method = RequestMethod.GET)
	public ModelAndView seller(@RequestParam("chat_room") int chat_room, @PathVariable("trade_no") int trade_no,
			@RequestParam("friend_email") String friend_email, HttpSession session) {

		System.out.println("@@@@@chat_room : " + chat_room);
		ModelAndView view = new ModelAndView();
		view.setViewName("seller");
		// 회원
		String login_email = (String) session.getAttribute("login_email");
		List<MemberVO> member_list = memberService.info(login_email);
		view.addObject("member", member_list);

		// 게시판
		TradeVO trade_vo = tradeservice.get(trade_no);
		view.addObject("trade", trade_vo);

		// 에스크로
		EscrowVO escrow_vo = escrowService.get(trade_no);
		System.out.println("escrow_vo.no :" + escrow_vo.getNo());
		view.addObject("escrow", escrow_vo);
		view.addObject("friend_email", friend_email);
		view.addObject("room_No", chat_room);

		return view;
	}

	// 배송정보입력 페이지 이동
	@RequestMapping(value = "/seller2", method = RequestMethod.POST)
	public ModelAndView seller2(@RequestParam("friend_email") String friend_email,
			@RequestParam("chat_room") int chat_room, @RequestParam("trade_no") int trade_no, HttpSession session) {

		ModelAndView view = new ModelAndView();
		// 회원
		String login_email = (String) session.getAttribute("login_email");
		List<MemberVO> member_list = memberService.info(login_email);
		view.addObject("member", member_list);

		// 게시판
		TradeVO trade_vo = tradeservice.get(trade_no);
		view.addObject("trade", trade_vo);

		// 이미지
		List<ProductImageVO> imgvo = productImageservice.getList();
		view.addObject("imgvo", imgvo);

		// 에스크로
		EscrowVO escrow_vo = escrowService.get(trade_no);
		view.addObject("escrow", escrow_vo);
		view.addObject("friend_email", friend_email);
		view.addObject("room_No", chat_room);

		view.setViewName("seller2");
		return view;
	}

	// 카톡방으로 다시3
	@RequestMapping(value = "/chat/rekatalk3", method = RequestMethod.GET)
	public ModelAndView reKatalk3(@RequestParam("chat_room") int chat_room, @RequestParam("trade_no") int trade_no,
			@RequestParam("friend_email") String friend_email, HttpSession session)
			throws IllegalStateException, IOException {

		String login_email = (String) session.getAttribute("login_email");
		ChattingVO vo = new ChattingVO();
		EscrowVO evo = new EscrowVO();
		evo.setState("배송중");
		evo.setTrade_no(trade_no);
		escrowService.updateState(evo);
		vo.setEmail(login_email);
		vo.setChatting_room_no(chat_room);
		ChattingVO cvo = new ChattingVO();
		String msg = "상품이 배송중입니다.";
		cvo.setEmail(login_email);
		cvo.setChatting_room_no(chat_room);
		cvo.setMsg(msg);
		cvo.setNotice("3");
		chatservice.msgInsert(cvo);
		
		ModelAndView temp = new ModelAndView();
		temp.addObject("noticeNum", 3);
		temp.setViewName("popUp");
		
		return temp;
	}

	// 팬매자 배송페이지
	@RequestMapping(value = "/buyer2/{trade_no}", method = RequestMethod.GET)
	public ModelAndView buyer2(@RequestParam("chat_room") int chat_room, @PathVariable("trade_no") int trade_no,
			@RequestParam("friend_email") String friend_email, HttpSession session) {

		System.out.println("@@@@@chat_room : " + chat_room);
		ModelAndView view = new ModelAndView();
		view.setViewName("buyer2");
		// 회원
		String login_email = (String) session.getAttribute("login_email");
		List<MemberVO> member_list = memberService.info(login_email);
		view.addObject("member", member_list);

		// 게시판
		TradeVO trade_vo = tradeservice.get(trade_no);
		view.addObject("trade", trade_vo);

		// 에스크로
		EscrowVO escrow_vo = escrowService.get(trade_no);
		System.out.println("escrow_vo.no :" + escrow_vo.getNo());
		view.addObject("escrow", escrow_vo);
		view.addObject("friend_email", friend_email);
		view.addObject("room_No", chat_room);

		return view;
	}

	// 수령확인
	@Transactional
	@RequestMapping(value = "/buyer3", method = RequestMethod.POST)
	public ModelAndView buyer3(EscrowVO vo, @RequestParam("friend_email") String friend_email,
			@RequestParam("chat_room") int chat_room, @RequestParam("trade_no") int trade_no, HttpSession session)
			throws IllegalStateException, IOException {
		ModelAndView view = new ModelAndView();
		// 회원
		String login_email = (String) session.getAttribute("login_email");
		List<MemberVO> member_list = memberService.info(login_email);
		view.addObject("member", member_list);

		// 게시판
		TradeVO trade_vo = tradeservice.get(trade_no);
		view.addObject("trade", trade_vo);

		// 이미지
		List<ProductImageVO> imgvo = productImageservice.getList();
		view.addObject("imgvo", imgvo);

		// 에스크로
		EscrowVO escrow = escrowService.get(trade_no);
		System.out.println("trade_no=" + trade_no);
		view.addObject("escrow", escrow);
		System.out.println("escrowNO = " + escrow.getNo());
		view.addObject("friend_email", friend_email);
		view.addObject("room_No", chat_room);
		System.out.println("체크체크" + vo.getBuyer_received_check());
		String check = vo.getBuyer_received_check();
		if (check.equals("수령확인")) {
			EscrowVO evo = new EscrowVO();
			evo.setState("수령확인");
			evo.setTrade_no(trade_no);
			escrowService.updateState(evo);
			System.out.println("notice44444444444444444444444444444444");
			ChattingVO cvo = new ChattingVO();
			String msg = "구매자의 상품수령이 확인되었습니다. 판매자에게돈이 입금될 예정입니다.";

			cvo.setEmail(login_email);
			cvo.setChatting_room_no(chat_room);
			cvo.setMsg(msg);
			cvo.setNotice("4");
			chatservice.msgInsert(cvo);

		}
		ModelAndView temp = new ModelAndView();
		temp.addObject("noticeNum", 4);
		temp.setViewName("popUp");
		return temp;
	}

	// 상품수령체크
	@RequestMapping(value = "/buyerReceivedCheck", method = RequestMethod.GET)
	public ModelAndView buyerReceivedCheck() {
		ModelAndView view = new ModelAndView();
		view.setViewName("buyerReceivedCheck");

		return view;
	} // 반품요청

	@RequestMapping(value = "/return1", method = RequestMethod.GET)
	public ModelAndView return1() {
		ModelAndView view = new ModelAndView();
		view.setViewName("return1");

		return view;
	}

	// 반품요청 공지
	@Transactional
	@RequestMapping(value = "/return2", method = RequestMethod.POST)
	public ModelAndView return2(EscrowVO vo, @RequestParam("friend_email") String friend_email,
			@RequestParam("chat_room") int chat_room, @RequestParam("trade_no") int trade_no, HttpSession session)
			throws IllegalStateException, IOException {
		ModelAndView view = new ModelAndView();
		// 회원
		String login_email = (String) session.getAttribute("login_email");
		List<MemberVO> member_list = memberService.info(login_email);
		view.addObject("member", member_list);

		// 게시판
		TradeVO trade_vo = tradeservice.get(trade_no);
		view.addObject("trade", trade_vo);

		// 이미지
		List<ProductImageVO> imgvo = productImageservice.getList();
		view.addObject("imgvo", imgvo);

		// 에스크로
		EscrowVO escrow = escrowService.get(trade_no);
		System.out.println("trade_no=" + trade_no);
		view.addObject("escrow", escrow);
		System.out.println("escrowNO = " + escrow.getNo());
		view.addObject("friend_email", friend_email);
		view.addObject("room_No", chat_room);
		vo.setState("반품요청");
		vo.setTrade_no(trade_no);
		System.out.println("accountnumbuyer" + vo.getBuyer_account_num());
		escrowService.updateEscrow(vo);

		ChattingVO cvo = new ChattingVO();
		String msg = "구매자의 반품요청이 있습니다.";

		cvo.setEmail(login_email);
		cvo.setChatting_room_no(chat_room);
		cvo.setMsg(msg);
		cvo.setNotice("5");
		chatservice.msgInsert(cvo);
		
		ModelAndView temp = new ModelAndView();
		temp.addObject("noticeNum", 5);
		temp.setViewName("popUp");

		return temp;
	}

	// 판매자 반품승인 페이지
	@RequestMapping(value = "/seller3/{trade_no}", method = RequestMethod.GET)
	public ModelAndView return3(@RequestParam("chat_room") int chat_room, @PathVariable("trade_no") int trade_no,
			@RequestParam("friend_email") String friend_email, HttpSession session) {
		System.out.println("seller333333333333333333333333333");
		System.out.println("@@@@@chat_room : " + chat_room);
		ModelAndView view = new ModelAndView();
		view.setViewName("seller3");
		// 회원
		String login_email = (String) session.getAttribute("login_email");
		List<MemberVO> member_list = memberService.info(login_email);
		view.addObject("member", member_list);

		// 게시판
		TradeVO trade_vo = tradeservice.get(trade_no);
		view.addObject("trade", trade_vo);

		// 에스크로
		EscrowVO escrow_vo = escrowService.get(trade_no);
		System.out.println("escrow_vo.no :" + escrow_vo.getNo());
		view.addObject("escrow", escrow_vo);
		view.addObject("friend_email", friend_email);
		view.addObject("room_No", chat_room);

		return view;
	}

	// 반품승인
	@RequestMapping(value = "/approve", method = RequestMethod.GET)
	public ModelAndView approve() {
		ModelAndView view = new ModelAndView();
		view.setViewName("approve");

		return view;
	}

	// 반품승인 공지
	@Transactional
	@RequestMapping(value = "/returnapprove", method = RequestMethod.GET)
	public ModelAndView returnapprove(EscrowVO vo, @RequestParam("friend_email") String friend_email,
			@RequestParam("chat_room") int chat_room, @RequestParam("trade_no") int trade_no, HttpSession session)
			throws IllegalStateException, IOException {
		ModelAndView view = new ModelAndView();
		// 회원
		String login_email = (String) session.getAttribute("login_email");
		List<MemberVO> member_list = memberService.info(login_email);
		view.addObject("member", member_list);

		// 게시판
		TradeVO trade_vo = tradeservice.get(trade_no);
		view.addObject("trade", trade_vo);

		// 이미지
		List<ProductImageVO> imgvo = productImageservice.getList();
		view.addObject("imgvo", imgvo);

		// 에스크로
		EscrowVO escrow = escrowService.get(trade_no);
		System.out.println("trade_no=" + trade_no);
		view.addObject("escrow", escrow);
		System.out.println("escrowNO = " + escrow.getNo());
		view.addObject("friend_email", friend_email);
		view.addObject("room_No", chat_room);
		vo.setState("반품승인");
		vo.setTrade_no(trade_no);
		vo.setSeller_address(vo.getAddress() + "   " + vo.getAddress2());
		vo.setSeller_postcode(vo.getPostcode1()+vo.getPostcode2());
		escrowService.updateEscrow(vo);
		ChattingVO cvo = new ChattingVO();
		String msg = "반품요청이 승인되었습니다.";

		cvo.setEmail(login_email);
		cvo.setChatting_room_no(chat_room);
		cvo.setMsg(msg);
		cvo.setNotice("6");
		chatservice.msgInsert(cvo);
		
		ModelAndView temp = new ModelAndView();
		temp.addObject("noticeNum", 6);
		temp.setViewName("popUp");

		return temp;
	}

	// 구매자 반품배송 페이지
	@RequestMapping(value = "/buyer5/{trade_no}", method = RequestMethod.GET)
	public ModelAndView buyer5(@RequestParam("chat_room") int chat_room, @PathVariable("trade_no") int trade_no,
			@RequestParam("friend_email") String friend_email, HttpSession session) {
		System.out.println("buyer5555555555");
		System.out.println("@@@@@chat_room : " + chat_room);
		ModelAndView view = new ModelAndView();
		view.setViewName("buyer3");
		// 회원
		String login_email = (String) session.getAttribute("login_email");
		List<MemberVO> member_list = memberService.info(login_email);
		view.addObject("member", member_list);

		// 게시판
		TradeVO trade_vo = tradeservice.get(trade_no);
		view.addObject("trade", trade_vo);

		// 에스크로
		EscrowVO escrow_vo = escrowService.get(trade_no);
		System.out.println("escrow_vo.no :" + escrow_vo.getNo());
		view.addObject("escrow", escrow_vo);
		view.addObject("friend_email", friend_email);
		view.addObject("room_No", chat_room);

		return view;
	}

	// 구매자 반품배송
	@RequestMapping(value = "/chat/rekatalk4", method = RequestMethod.GET)
	public ModelAndView reKatalk4(@RequestParam("chat_room") int chat_room, @RequestParam("trade_no") int trade_no,
			@RequestParam("friend_email") String friend_email, HttpSession session)
			throws IllegalStateException, IOException {

		String login_email = (String) session.getAttribute("login_email");
		ChattingVO vo = new ChattingVO();
		EscrowVO evo = new EscrowVO();
		evo.setState("반품배송중");
		evo.setTrade_no(trade_no);
		escrowService.updateState(evo);
		vo.setEmail(login_email);
		vo.setChatting_room_no(chat_room);
		ChattingVO cvo = new ChattingVO();
		String msg = "반품상품이 배송중입니다.";
		cvo.setEmail(login_email);
		cvo.setChatting_room_no(chat_room);
		cvo.setMsg(msg);
		cvo.setNotice("7");
		chatservice.msgInsert(cvo);
		
		ModelAndView temp = new ModelAndView();
		temp.addObject("noticeNum", 7);
		temp.setViewName("popUp");
		return temp;
	}

	// 구매자 반품배송 페이지
	@RequestMapping(value = "/src/{trade_no}", method = RequestMethod.GET)
	public ModelAndView src(@RequestParam("chat_room") int chat_room, @PathVariable("trade_no") int trade_no,
			@RequestParam("friend_email") String friend_email, HttpSession session) {

		System.out.println("@@@@@chat_room : " + chat_room);
		ModelAndView view = new ModelAndView();
		view.setViewName("sellerreceived");
		// 회원
		String login_email = (String) session.getAttribute("login_email");
		List<MemberVO> member_list = memberService.info(login_email);
		view.addObject("member", member_list);

		// 게시판
		TradeVO trade_vo = tradeservice.get(trade_no);
		view.addObject("trade", trade_vo);

		// 에스크로
		EscrowVO escrow_vo = escrowService.get(trade_no);
		System.out.println("escrow_vo.no :" + escrow_vo.getNo());
		view.addObject("escrow", escrow_vo);
		view.addObject("friend_email", friend_email);
		view.addObject("room_No", chat_room);

		return view;
	}

	// 판매자 반품수령
	@RequestMapping(value = "/chat/rekatalk5", method = RequestMethod.POST)
	public ModelAndView reKatalk5(@RequestParam("chat_room") int chat_room, @RequestParam("trade_no") int trade_no,
			@RequestParam("friend_email") String friend_email, HttpSession session)
			throws IllegalStateException, IOException {

		String login_email = (String) session.getAttribute("login_email");
		ChattingVO vo = new ChattingVO();
		EscrowVO evo = new EscrowVO();
		evo.setState("반품수령");
		evo.setTrade_no(trade_no);
		escrowService.updateState(evo);
		vo.setEmail(login_email);
		vo.setChatting_room_no(chat_room);
		ChattingVO cvo = new ChattingVO();
		String msg = "반품상품이 수령되었습니다. 구매자에게 돈이 입금될 예정입니다.";
		cvo.setEmail(login_email);
		cvo.setChatting_room_no(chat_room);
		cvo.setMsg(msg);
		cvo.setNotice("8");
		chatservice.msgInsert(cvo);
		
		ModelAndView temp = new ModelAndView();
		temp.addObject("noticeNum", 8);
		temp.setViewName("popUp");
		return temp;
	}

	// 반품요청
	@RequestMapping(value = "/returncc", method = RequestMethod.GET)
	public ModelAndView returncc() {
		ModelAndView view = new ModelAndView();
		view.setViewName("returncc");

		return view;
	}

}