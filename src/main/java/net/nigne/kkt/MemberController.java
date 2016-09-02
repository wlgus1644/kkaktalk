package net.nigne.kkt;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import net.nigne.kkt.domain.ChattingVO;
import net.nigne.kkt.domain.FriendsVO;
import net.nigne.kkt.domain.MemberVO;
import net.nigne.kkt.domain.Msg_listVO;
import net.nigne.kkt.domain.TradeVO;
import net.nigne.kkt.domain.User_listVO;
import net.nigne.kkt.email.Email;
import net.nigne.kkt.email.EmailSender;
import net.nigne.kkt.email.Mathrandom;
import net.nigne.kkt.service.ChattingService;
import net.nigne.kkt.service.MemberService;
import net.nigne.kkt.service.Msg_listService;
import net.nigne.kkt.service.TradeService;
import net.nigne.kkt.service.User_listService;

@Controller
@RestController
@RequestMapping("")
public class MemberController {
	@Inject
	private TradeService tradeservice;
	@Inject
	private ChattingService chatservice;
	@Inject
	private MemberService memberService;
	@Inject
	private User_listService uservice;
	@Inject
	private Msg_listService msgservice;
	@Autowired
	private EmailSender emailSender;
	@Autowired
	private Email email;
	
	// 로그인페이지
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView login(HttpSession session) {
		ModelAndView view = new ModelAndView();
		String login_email = (String) session.getAttribute("login_email");
		String login_pw = (String) session.getAttribute("login_pw");
		System.out.println("11login_email:" + login_email);
		System.out.println("11login_pw:" + login_pw);
		if (login_email != null || login_pw != null) {
			view.setViewName("redirect:/friend_list");
		} else {
			view.setViewName("login");
		}
		return view;
	}
	
	@RequestMapping(value = "/agree", method = RequestMethod.GET)
	public ModelAndView agree(MemberVO vo, Model model, HttpSession session) {
		ModelAndView view = new ModelAndView();
		view.setViewName("agree");
		return view;
	}
	// 회원가입페이지
	@RequestMapping(value = "/join", method = RequestMethod.GET)
	public ModelAndView join() {
		ModelAndView view = new ModelAndView();
		view.setViewName("join");
		return view;
	}
	@Resource(name = "profileUploadPath")
	private String profileUploadPath;
	// 회원가입 db에 insert
	@RequestMapping(value = "", method = RequestMethod.POST)
	public ModelAndView insert(@RequestParam Map<String, Object> paramMap, MultipartFile file, MemberVO vo, Model model) throws Exception {
		ModelAndView view = new ModelAndView();
		List<MemberVO> memberinfo = memberService.info(vo.getEmail());
		// 회원가입할때 이메일 중복확인
		if (memberinfo.isEmpty()) {
			if (file.getSize() > 0) {
				String fileName = uploadFile(file.getOriginalFilename(), file.getBytes());
				vo.setThumbnail(fileName);
			} else {
				vo.setThumbnail("noImage.jpg");
			}
			String name = (String) paramMap.get("name");
			String e_mail = (String) paramMap.get("email");
			String link = "http://52.198.118.55/kkt/" + e_mail;
			System.out.println("이메일전송 name : " + name);
			System.out.println("이메일전송 e_mail : " + e_mail);
			System.out.println("이메일전송 link : " + link);
			PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			vo.setPw(passwordEncoder.encode(vo.getPw()));
			memberService.insert(vo);
			email.setContent("링크를 누르시면 회원가입이 완료됩니다.<br>" + link);
			email.setReceiver(e_mail);
			email.setSubject("[깎톡] " + name + "님 회원가입 메일입니다.");
			emailSender.SendEmail(email);
			model.addAttribute("message", "회원가입확인메일이 이메일로 전송되었습니다.");
			model.addAttribute("returnUrl", "/");
			view.setViewName("alertAndRedirect");
		} else {
			model.addAttribute("message", "이미 사용중인 이메일입니다.");
			model.addAttribute("returnUrl", "/join");
			view.setViewName("alertAndRedirect");
		}
		return view;
	}
	private String uploadFile(String orgName, byte[] file) throws Exception {
		UUID uid = UUID.randomUUID();
		String fileName = uid.toString() + "_" + orgName;
		File target = new File(profileUploadPath, fileName);
		FileCopyUtils.copy(file, target);
		return fileName;
	}
	//친구목록
	@RequestMapping(value = "/friend_list", method = RequestMethod.GET)
	public ModelAndView loginGet(HttpSession session) {
		ModelAndView view = new ModelAndView();
		MemberVO vo = new MemberVO();
		String login_email = (String) session.getAttribute("login_email");
		// 로그인상태면 친구목록 보여주기
		if (login_email != null || login_email.equals("")) {
			vo.setEmail(login_email);
			List<MemberVO> memberinfo = memberService.info(vo.getEmail());
			System.out.println("memberInfo=" + memberinfo);
			session.setAttribute("memberSession", memberinfo);
			session.setAttribute("memberinfo", memberinfo);
			view.addObject("memberinfo", memberinfo);
			view.setViewName("friend_list");
			List<MemberVO> friendlist = memberService.friendList(vo.getEmail());
			view.addObject("friendlist", friendlist);
			chatinfo(session, login_email);
		} else { // 로그인 상태가 아니면 로그인페이지로 이동
			view.setViewName("redirect:/");
		}
		return view;
	}

	// 로그인페이지에서 로그인버튼 클릭시
	@RequestMapping(value = "/friend_list", method = RequestMethod.POST)
	public ModelAndView loginProcess(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response) {

		ModelAndView view = new ModelAndView();
		MemberVO vo = new MemberVO();

		String email = request.getParameter("login_email");
		String pw = request.getParameter("login_pw");
		vo.setEmail(email);
		vo.setPw(pw);
		// vo.setThumbnail(thumbnail);

		session.setAttribute("login_email", vo.getEmail());
		session.setAttribute("login_pw", vo.getPw());

		System.out.println("@@@@@@@@@@@@@@@@@@@@@@");
		System.out.println(session.getAttribute("login_email"));
		String login = memberService.login(vo.getEmail());

		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

		if (passwordEncoder.matches(vo.getPw(), login)) {// 로그인 email, pw와 db에
															// 있는 email, pw가
															// 일치하면 친구목록페이지로 이동
			List<MemberVO> memberinfo = memberService.info(vo.getEmail());
			view.addObject("memberinfo", memberinfo);
			session.setAttribute("memberinfo", memberinfo);
			view.setViewName("friend_list");

			List<MemberVO> friendlist = memberService.friendList(vo.getEmail());
			view.addObject("friendlist", friendlist);
			chatinfo(session, email);

		} else {// 패스워드가 일치하지 않으면 alert창 띄움
			session.invalidate();

			model.addAttribute("message", "로그인실패");
			model.addAttribute("returnUrl", "/");

			view.setViewName("alertAndRedirect");

		}

		return view;
	}

	// 친구목록에서 친구검색
	@RequestMapping(value = "/friend_list/{search:.+}", method = RequestMethod.GET)
	public ModelAndView listsearch(@PathVariable("search") String search, Model model, MemberVO vo, HttpSession session) {
		
		ModelAndView view = new ModelAndView();
		
		if (session.getAttribute("login_email") == null || session.getAttribute("login_email").equals("")) {
			
			model.addAttribute("message", "다시 로그인해주세요.");
			model.addAttribute("returnUrl", "/");
			view.setViewName("alertAndRedirect");

			return view;
			
		} else {
			vo.setEmail((String) session.getAttribute("login_email"));

			String email = vo.getEmail();

			List<MemberVO> friendListSearch = memberService.friendListSearch(email, search);

			if (friendListSearch.isEmpty()) {
				
				model.addAttribute("message", "일치하는 사용자가 없습니다.");
				model.addAttribute("returnUrl", "/friend_list");

				view.setViewName("alertAndRedirect");
				
			} else {
				
				List<MemberVO> memberinfo = memberService.Search(vo.getEmail());
				view.addObject("memberinfo", memberinfo);

				view.addObject("friendlist", friendListSearch);
				view.setViewName("friend_list");
				
			}
			return view;
		}
		
	}

	// 로그아웃페이지
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public ModelAndView logoutProcess(HttpSession session, Model model) {

		ModelAndView view = new ModelAndView();

		model.addAttribute("message", "로그아웃이 되었습니다.");
		model.addAttribute("returnUrl", "/");

		view.setViewName("alertAndRedirect");
		session.invalidate();

		return view;
	}

	// 친구찾기페이지(자신을 등록한 친구들이 뜸. 단,서로친구인경우 안뜸)
	@RequestMapping(value = "/friend_search", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView friend_search(MemberVO vo, HttpSession session, HttpServletRequest request) {

		ModelAndView view = new ModelAndView();

		vo.setEmail((String) session.getAttribute("login_email"));

		List<MemberVO> friendSearch = memberService.friendSearch(vo.getEmail());

		view.addObject("friendSearch", friendSearch);

		view.setViewName("friend_search");

		return view;
	}

	// 친구찾기페이지에서 친구검색
	@RequestMapping(value = "/friend_search/{search:.+}", method = RequestMethod.GET)
	public ModelAndView search(@PathVariable("search") String search, Model model) {

		ModelAndView view = new ModelAndView();

		List<MemberVO> friendSearch = memberService.Search(search);

		if (friendSearch.isEmpty()) {
			model.addAttribute("message", "일치하는 사용자가 없습니다.");
			model.addAttribute("returnUrl", "/friend_search");

			view.setViewName("alertAndRedirect");
		} else {
			view.addObject("friendSearch", friendSearch);
			view.setViewName("friend_search");
		}

		return view;
	}

	// 친구찾기페이지에서 친구추가 버튼을 눌렀을때
	@RequestMapping(value = "/friendAdd/{friend_email:.+}", method = RequestMethod.GET)
	public ModelAndView friendAdd(FriendsVO vo, @PathVariable("friend_email") String friend_email, HttpSession session, Model model) {

		ModelAndView view = new ModelAndView();

		vo.setFriend_email(friend_email);
		vo.setMember_email((String) session.getAttribute("login_email"));

		if (vo.getFriend_email() == vo.getMember_email() || vo.getFriend_email().equals(vo.getMember_email())) {
			model.addAttribute("message", "자신은 친구로 등록할 수 없습니다.");
			model.addAttribute("returnUrl", "/friend_search");

			view.setViewName("alertAndRedirect");
		} else {
			List<FriendsVO> alreadyFriend = memberService.alreadyFriend(vo.getMember_email(), vo.getFriend_email());

			if (alreadyFriend.isEmpty()) {// 친구로 등록이 안되있을경우 친구추가
				memberService.friendAdd(vo);
				view.setViewName("redirect:/friend_list");
			} else {// 이미 친구로 등록이 되 있을 경우
				model.addAttribute("message", "이미 친구입니다.");
				model.addAttribute("returnUrl", "/friend_search");

				view.setViewName("alertAndRedirect");
			}
		}
		return view;
	}

	// 프로필수정페이지
	@RequestMapping(value = "/memberUpdate", method = RequestMethod.GET)
	public ModelAndView memberUpdate(HttpSession session, MemberVO vo) {

		ModelAndView view = new ModelAndView();

		vo.setEmail((String) session.getAttribute("login_email"));
		List<MemberVO> memberinfo = memberService.info(vo.getEmail());
		view.addObject("memberinfo", memberinfo);

		view.setViewName("memberUpdate");

		return view;
	}

	// 프로필수정
	@RequestMapping(value = "/memberUpdate", method = RequestMethod.POST)
	public ModelAndView memberUpdatePost(MultipartFile file, MemberVO vo, HttpServletRequest request, HttpSession session, Model model) throws Exception {

		ModelAndView view = new ModelAndView();
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String thumbnail = request.getParameter("thumbnail");
		String login_email = (String) session.getAttribute("login_email");
		String login = memberService.login(login_email);
		System.out.println("기존pw=" + login);

		if (file.getSize() > 0) {
			String fileName = uploadFile(file.getOriginalFilename(), file.getBytes());

			vo.setThumbnail(fileName);

		} else {
			if (thumbnail == null || "".equals(thumbnail)) {
				vo.setThumbnail("noImage.jpg");
			} else {
				vo.setThumbnail(thumbnail);
			}
		}

		System.out.println("프로필수정확인용pw=" + vo.getPw());

		if (passwordEncoder.matches(vo.getPw(), login)) {
			vo.setPw(passwordEncoder.encode(vo.getPw()));
			memberService.memberUpdate(vo);
			view.setViewName("friend_list"); // 패스워드가 일치하면 변경창으로
			view.setViewName("redirect:/friend_list");

			return view;

		} else {// 패스워드가 일치하지 않으면 alert창 띄움
			model.addAttribute("message", "비밀번호가 일치하지않습니다.");
			model.addAttribute("returnUrl", "/memberUpdate");

			view.setViewName("alertAndRedirect");
			return view;
		}

	}

	// 채팅방 리스트
	@RequestMapping(value = "/chat_list", method = RequestMethod.GET)
	public ModelAndView chat_list(HttpSession session, Model model) {

		ModelAndView view = new ModelAndView();
		
		if (session.getAttribute("login_email") == null || session.getAttribute("login_email").equals("")) {
			
			model.addAttribute("message", "다시 로그인해주세요.");
			model.addAttribute("returnUrl", "/");
			view.setViewName("alertAndRedirect");

			return view;
			
		} else {
			
			view.setViewName("chat_list");
			String login_email = (String) session.getAttribute("login_email");
			chatinfo(session, login_email);

			return view;
			
		}

	}

	// 읽지 않은 메세지 count
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

			System.out.println("방번호 :" + roomNo);

			chatinfo.put("roomNo", roomNo);

			System.out.println("상대방이메일 : " + msgservice.getFriend(chatinfo));

			chatinfo.put("friend_email", msgservice.getFriend(chatinfo));

			if (msgservice.getFriend(chatinfo) == null || msgservice.getFriend(chatinfo).equals("")) {
				cntmsg.add(0);
			} else {

				totalmsg += msgservice.cntmsg(chatinfo);
				System.out.println("안읽은 메세지 총:" + totalmsg);
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

	// 채팅
	@RequestMapping(value = "/chat/{friend_email:.+}", method = RequestMethod.GET)
	public ModelAndView chat(Model model, MemberVO mvo, ChattingVO vo, @PathVariable("friend_email") String friend_email, HttpSession session, HttpServletRequest request) {

		ModelAndView view = new ModelAndView();
		
		if (session.getAttribute("login_email") == null || session.getAttribute("login_email").equals("")) {
			
			model.addAttribute("message", "다시 로그인해주세요.");
			model.addAttribute("returnUrl", "/");
			view.setViewName("alertAndRedirect");

			return view;
			
		} else {
			String login_email = (String) session.getAttribute("login_email");

			vo.setEmail(login_email);

			// 계좌 있는지 체크
			mvo.setEmail(login_email);
			System.out.println("login한 사람 =" + mvo.getEmail());
			int checkAccount = memberService.checkAccount(mvo);
			System.out.println("checkAccount=" + checkAccount);
			view.addObject("checkAccount", checkAccount);

			System.out.println("login_email:" + login_email);

			System.out.println("friend_email:" + friend_email);

			ChattingVO chattingCheck = chatservice.chattingCheck(login_email, friend_email);

			if (chattingCheck == null) {
				System.out.println("채팅방생성");
				chatservice.chattingInsert();

				int chattingRoomNO = chatservice.chattingRoomNO();
				System.out.println("chattingRoomNO:" + chattingRoomNO);

				chatservice.userListInsert(chattingRoomNO, login_email);
				chatservice.userListInsert(chattingRoomNO, friend_email);
			}

			chattingCheck = chatservice.chattingCheck(login_email, friend_email);
			view.addObject("chattingCheck", chattingCheck);

			List<ChattingVO> msgList = null;
			vo.setChatting_room_no(chattingCheck.getChatting_room_no());
			
			if (uservice.myUserListDate(vo) != null) {
				
				String date = uservice.myUserListDate(vo).substring(0, 19);
				msgList = chatservice.msgList(login_email, friend_email, date);
				
			}

			System.out.println("메시지리스트 1 : " + msgList);
			view.addObject("msgList", msgList);

			List<MemberVO> memberinfo = memberService.info(login_email);
			view.addObject("memberinfo", memberinfo);

			mvo = memberService.oneInfo(friend_email);
			List<MemberVO> aaa = memberService.Search(friend_email);
			if (aaa.isEmpty()) {
				view.addObject("friend_email", "알수없는 사람");
				System.out.println("알수없는사람");
			} else {
				
				view.addObject("friend_email", friend_email);
				view.addObject("friend_name", memberService.oneInfo(friend_email).getName());
				System.out.println("111111111111111111111111111");
				
			}
			view.addObject("thumbnail", mvo.getThumbnail());

			// 이건뭐지??
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
		

	}

	// 친구삭제
	@RequestMapping(value = "/friend_delete/{friend_email:.+}", method = RequestMethod.GET)
	public ModelAndView friend_delete(HttpSession session, @PathVariable("friend_email") String friend_email) {

		ModelAndView view = new ModelAndView();

		String member_email = (String) session.getAttribute("login_email");

		memberService.friendDelete(member_email, friend_email);

		view.setViewName("redirect:/friend_list");

		return view;
	}

	// 방 나가기
	@RequestMapping(value = "/chat/exit/{room_no}", method = RequestMethod.GET)
	public ModelAndView chatExit(@PathVariable("room_no") int room_no, HttpSession session, HttpServletRequest request) {

		ModelAndView view = new ModelAndView();
		User_listVO user_list = new User_listVO();
		user_list.setNo(room_no);
		user_list.setMember_email((String) session.getAttribute("login_email"));

		uservice.exit_room(user_list);
		uservice.deleteRoom(user_list.getNo());

		view.setViewName("redirect:/chat_list");
		return view;

	}

	// 회원탈퇴
	@RequestMapping(value = "/memberDelete", method = RequestMethod.GET)
	public ModelAndView memberDelete(HttpSession session) {

		ModelAndView view = new ModelAndView();

		String member_email = (String) session.getAttribute("login_email");

		memberService.memberDelete(member_email);

		session.invalidate();

		view.setViewName("redirect:/");

		return view;
	}

	// 회원가입 이메일에서 링크클릭시
	@RequestMapping(value = "/kkt/{email:.+}", method = RequestMethod.GET)
	public ModelAndView join11(@PathVariable("email") String email, Model model) {

		ModelAndView view = new ModelAndView();
		model.addAttribute("message", "회원가입이 완료되었습니다.");
		model.addAttribute("returnUrl", "/");

		view.setViewName("alertAndRedirect");
		System.out.println("이메이이이일" + email);
		int joincheck = memberService.joinCheck(email);

		System.out.println("joincheck : " + joincheck);

		if (joincheck == 0) {
			memberService.memberJoin(email);

			model.addAttribute("message", "회원가입이 완료되었습니다.");
			model.addAttribute("returnUrl", "/");

			view.setViewName("alertAndRedirect");
		} else {
			model.addAttribute("message", "이미 가입되었습니다.");
			model.addAttribute("returnUrl", "/");

			view.setViewName("alertAndRedirect");
		}

		return view;
	}

	// 아이디 찾기
	@RequestMapping(value = "/idSearch", method = RequestMethod.GET)
	public ModelAndView idSearch() {

		ModelAndView view = new ModelAndView();

		view.setViewName("idSearch");

		return view;
	}

	@RequestMapping(value = "/idSearch", method = RequestMethod.POST)
	public ModelAndView idsearch(Model model, MemberVO vo) {

		ModelAndView view = new ModelAndView();

		String email = memberService.idSearch(vo.getName(), vo.getPhone_num());

		if (email == null) {
			model.addAttribute("message", "아이디가 존재하지않습니다. 회원가입을 해주세요.");
			model.addAttribute("returnUrl", "/");
			view.setViewName("alertAndRedirect");
		} else {
			model.addAttribute("message", vo.getName() + "님의 아이디는 " + email + " 입니다.");
			model.addAttribute("returnUrl", "/");
			view.setViewName("alertAndRedirect");
		}

		return view;
	}

	// 비밀번호 찾기
	@RequestMapping(value = "/pwSearch", method = RequestMethod.GET)
	public ModelAndView pwSearch() {

		ModelAndView view = new ModelAndView();

		view.setViewName("pwSearch");

		return view;
	}

	@RequestMapping(value = "/pwSearch", method = RequestMethod.POST)
	public ModelAndView sendEmailAction(@RequestParam Map<String, Object> paramMap, ModelMap model, MemberVO vo)
			throws Exception {
		ModelAndView view = new ModelAndView();

		String name = (String) paramMap.get("name");
		String e_mail = (String) paramMap.get("email");
		String pw = Mathrandom.random();

		System.out.println("이메일전송 name : " + name);
		System.out.println("이메일전송 e_mail : " + e_mail);
		System.out.println("이메일전송 pw : " + pw);

		String pwsearch = memberService.pwSearch(vo);

		if (pwsearch != null) {
			email.setContent("임시비밀번호는 " + pw + " 입니다.");
			email.setReceiver(e_mail);
			email.setSubject("[깎톡] " + name + "님 비밀번호 찾기 메일입니다.");
			emailSender.SendEmail(email);

			vo.setEmail(e_mail);

			PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			vo.setPw(passwordEncoder.encode(pw));

			memberService.pwUpdate(vo);

			model.addAttribute("message", "임시비밀번호가 이메일로 전송되었습니다.");
			model.addAttribute("returnUrl", "/");

			view.setViewName("alertAndRedirect");

		} else {
			model.addAttribute("message", "존재하지않는 사용자입니다.");
			model.addAttribute("returnUrl", "/");

			view.setViewName("alertAndRedirect");
		}
		return view;
	}

	// 비밀번호 변경
	@RequestMapping(value = "/pwchange", method = RequestMethod.GET)
	public ModelAndView pwchange() {

		ModelAndView view = new ModelAndView();

		view.setViewName("pwchange");

		return view;
	}

	@RequestMapping(value = "/pwchange", method = RequestMethod.POST)
	public ModelAndView pwChange(MemberVO vo, HttpSession session, Model model) {

		ModelAndView view = new ModelAndView();

		String login_email = (String) session.getAttribute("login_email");

		String login = memberService.login(login_email);

		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

		if (passwordEncoder.matches(vo.getPw(), login)) {

			view.setViewName("pwchange2"); // 패스워드가 일치하면 변경창으로

		} else {// 패스워드가 일치하지 않으면 alert창 띄움
			model.addAttribute("message", "비밀번호가 일치하지않습니다.");
			model.addAttribute("returnUrl", "/pwchange");

			view.setViewName("alertAndRedirect");
		}

		return view;
	}

	@RequestMapping(value = "/pwchange2", method = RequestMethod.POST)
	public ModelAndView pwChange2(MemberVO vo, HttpSession session, Model model) {

		ModelAndView view = new ModelAndView();

		String login_email = (String) session.getAttribute("login_email");

		vo.setEmail(login_email);
		System.out.println("비밀번호 수정 비번 : " + vo.getPw());

		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		vo.setPw(passwordEncoder.encode(vo.getPw()));

		System.out.println("비밀번호 수정 이메일 : " + vo.getEmail());
		System.out.println("비밀번호 수정 비번 : " + vo.getPw());

		memberService.pwUpdate(vo);

		session.invalidate();

		model.addAttribute("message", "비밀번호가 변경되었습니다. 다시 로그인해주세요.");
		model.addAttribute("returnUrl", "/");

		view.setViewName("alertAndRedirect");

		return view;
	}

	   @RequestMapping(value = "/chat/friendAdd/{friend_email:.+}", method = RequestMethod.GET) 
	   public ModelAndView friendAdd(@PathVariable("friend_email") String friend_email, HttpSession session, Model model) {
	      ModelAndView view = new ModelAndView();
	 
	      MemberVO mvo = memberService.oneInfo(friend_email);
	      view.addObject("friend_name", mvo.getName());
	      view.addObject("thumbnail", mvo.getThumbnail());
	      List<FriendsVO> alreadyFriend = memberService.alreadyFriend((String)session.getAttribute("login_email"), friend_email);
	      if(alreadyFriend.isEmpty()) {
	         view.addObject("friend_email", friend_email);
	         view.addObject("isFriend", 0);
	      } else {
	         view.addObject("isFriend", 1);
	      }
	      view.setViewName("friend_detail");

	      return view;
	   }
}
