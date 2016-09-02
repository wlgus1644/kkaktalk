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
	
	// �α���������
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
	// ȸ������������
	@RequestMapping(value = "/join", method = RequestMethod.GET)
	public ModelAndView join() {
		ModelAndView view = new ModelAndView();
		view.setViewName("join");
		return view;
	}
	@Resource(name = "profileUploadPath")
	private String profileUploadPath;
	// ȸ������ db�� insert
	@RequestMapping(value = "", method = RequestMethod.POST)
	public ModelAndView insert(@RequestParam Map<String, Object> paramMap, MultipartFile file, MemberVO vo, Model model) throws Exception {
		ModelAndView view = new ModelAndView();
		List<MemberVO> memberinfo = memberService.info(vo.getEmail());
		// ȸ�������Ҷ� �̸��� �ߺ�Ȯ��
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
			System.out.println("�̸������� name : " + name);
			System.out.println("�̸������� e_mail : " + e_mail);
			System.out.println("�̸������� link : " + link);
			PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			vo.setPw(passwordEncoder.encode(vo.getPw()));
			memberService.insert(vo);
			email.setContent("��ũ�� �����ø� ȸ�������� �Ϸ�˴ϴ�.<br>" + link);
			email.setReceiver(e_mail);
			email.setSubject("[����] " + name + "�� ȸ������ �����Դϴ�.");
			emailSender.SendEmail(email);
			model.addAttribute("message", "ȸ������Ȯ�θ����� �̸��Ϸ� ���۵Ǿ����ϴ�.");
			model.addAttribute("returnUrl", "/");
			view.setViewName("alertAndRedirect");
		} else {
			model.addAttribute("message", "�̹� ������� �̸����Դϴ�.");
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
	//ģ�����
	@RequestMapping(value = "/friend_list", method = RequestMethod.GET)
	public ModelAndView loginGet(HttpSession session) {
		ModelAndView view = new ModelAndView();
		MemberVO vo = new MemberVO();
		String login_email = (String) session.getAttribute("login_email");
		// �α��λ��¸� ģ����� �����ֱ�
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
		} else { // �α��� ���°� �ƴϸ� �α����������� �̵�
			view.setViewName("redirect:/");
		}
		return view;
	}

	// �α������������� �α��ι�ư Ŭ����
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

		if (passwordEncoder.matches(vo.getPw(), login)) {// �α��� email, pw�� db��
															// �ִ� email, pw��
															// ��ġ�ϸ� ģ������������� �̵�
			List<MemberVO> memberinfo = memberService.info(vo.getEmail());
			view.addObject("memberinfo", memberinfo);
			session.setAttribute("memberinfo", memberinfo);
			view.setViewName("friend_list");

			List<MemberVO> friendlist = memberService.friendList(vo.getEmail());
			view.addObject("friendlist", friendlist);
			chatinfo(session, email);

		} else {// �н����尡 ��ġ���� ������ alertâ ���
			session.invalidate();

			model.addAttribute("message", "�α��ν���");
			model.addAttribute("returnUrl", "/");

			view.setViewName("alertAndRedirect");

		}

		return view;
	}

	// ģ����Ͽ��� ģ���˻�
	@RequestMapping(value = "/friend_list/{search:.+}", method = RequestMethod.GET)
	public ModelAndView listsearch(@PathVariable("search") String search, Model model, MemberVO vo, HttpSession session) {
		
		ModelAndView view = new ModelAndView();
		
		if (session.getAttribute("login_email") == null || session.getAttribute("login_email").equals("")) {
			
			model.addAttribute("message", "�ٽ� �α������ּ���.");
			model.addAttribute("returnUrl", "/");
			view.setViewName("alertAndRedirect");

			return view;
			
		} else {
			vo.setEmail((String) session.getAttribute("login_email"));

			String email = vo.getEmail();

			List<MemberVO> friendListSearch = memberService.friendListSearch(email, search);

			if (friendListSearch.isEmpty()) {
				
				model.addAttribute("message", "��ġ�ϴ� ����ڰ� �����ϴ�.");
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

	// �α׾ƿ�������
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public ModelAndView logoutProcess(HttpSession session, Model model) {

		ModelAndView view = new ModelAndView();

		model.addAttribute("message", "�α׾ƿ��� �Ǿ����ϴ�.");
		model.addAttribute("returnUrl", "/");

		view.setViewName("alertAndRedirect");
		session.invalidate();

		return view;
	}

	// ģ��ã��������(�ڽ��� ����� ģ������ ��. ��,����ģ���ΰ�� �ȶ�)
	@RequestMapping(value = "/friend_search", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView friend_search(MemberVO vo, HttpSession session, HttpServletRequest request) {

		ModelAndView view = new ModelAndView();

		vo.setEmail((String) session.getAttribute("login_email"));

		List<MemberVO> friendSearch = memberService.friendSearch(vo.getEmail());

		view.addObject("friendSearch", friendSearch);

		view.setViewName("friend_search");

		return view;
	}

	// ģ��ã������������ ģ���˻�
	@RequestMapping(value = "/friend_search/{search:.+}", method = RequestMethod.GET)
	public ModelAndView search(@PathVariable("search") String search, Model model) {

		ModelAndView view = new ModelAndView();

		List<MemberVO> friendSearch = memberService.Search(search);

		if (friendSearch.isEmpty()) {
			model.addAttribute("message", "��ġ�ϴ� ����ڰ� �����ϴ�.");
			model.addAttribute("returnUrl", "/friend_search");

			view.setViewName("alertAndRedirect");
		} else {
			view.addObject("friendSearch", friendSearch);
			view.setViewName("friend_search");
		}

		return view;
	}

	// ģ��ã������������ ģ���߰� ��ư�� ��������
	@RequestMapping(value = "/friendAdd/{friend_email:.+}", method = RequestMethod.GET)
	public ModelAndView friendAdd(FriendsVO vo, @PathVariable("friend_email") String friend_email, HttpSession session, Model model) {

		ModelAndView view = new ModelAndView();

		vo.setFriend_email(friend_email);
		vo.setMember_email((String) session.getAttribute("login_email"));

		if (vo.getFriend_email() == vo.getMember_email() || vo.getFriend_email().equals(vo.getMember_email())) {
			model.addAttribute("message", "�ڽ��� ģ���� ����� �� �����ϴ�.");
			model.addAttribute("returnUrl", "/friend_search");

			view.setViewName("alertAndRedirect");
		} else {
			List<FriendsVO> alreadyFriend = memberService.alreadyFriend(vo.getMember_email(), vo.getFriend_email());

			if (alreadyFriend.isEmpty()) {// ģ���� ����� �ȵ�������� ģ���߰�
				memberService.friendAdd(vo);
				view.setViewName("redirect:/friend_list");
			} else {// �̹� ģ���� ����� �� ���� ���
				model.addAttribute("message", "�̹� ģ���Դϴ�.");
				model.addAttribute("returnUrl", "/friend_search");

				view.setViewName("alertAndRedirect");
			}
		}
		return view;
	}

	// �����ʼ���������
	@RequestMapping(value = "/memberUpdate", method = RequestMethod.GET)
	public ModelAndView memberUpdate(HttpSession session, MemberVO vo) {

		ModelAndView view = new ModelAndView();

		vo.setEmail((String) session.getAttribute("login_email"));
		List<MemberVO> memberinfo = memberService.info(vo.getEmail());
		view.addObject("memberinfo", memberinfo);

		view.setViewName("memberUpdate");

		return view;
	}

	// �����ʼ���
	@RequestMapping(value = "/memberUpdate", method = RequestMethod.POST)
	public ModelAndView memberUpdatePost(MultipartFile file, MemberVO vo, HttpServletRequest request, HttpSession session, Model model) throws Exception {

		ModelAndView view = new ModelAndView();
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String thumbnail = request.getParameter("thumbnail");
		String login_email = (String) session.getAttribute("login_email");
		String login = memberService.login(login_email);
		System.out.println("����pw=" + login);

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

		System.out.println("�����ʼ���Ȯ�ο�pw=" + vo.getPw());

		if (passwordEncoder.matches(vo.getPw(), login)) {
			vo.setPw(passwordEncoder.encode(vo.getPw()));
			memberService.memberUpdate(vo);
			view.setViewName("friend_list"); // �н����尡 ��ġ�ϸ� ����â����
			view.setViewName("redirect:/friend_list");

			return view;

		} else {// �н����尡 ��ġ���� ������ alertâ ���
			model.addAttribute("message", "��й�ȣ�� ��ġ�����ʽ��ϴ�.");
			model.addAttribute("returnUrl", "/memberUpdate");

			view.setViewName("alertAndRedirect");
			return view;
		}

	}

	// ä�ù� ����Ʈ
	@RequestMapping(value = "/chat_list", method = RequestMethod.GET)
	public ModelAndView chat_list(HttpSession session, Model model) {

		ModelAndView view = new ModelAndView();
		
		if (session.getAttribute("login_email") == null || session.getAttribute("login_email").equals("")) {
			
			model.addAttribute("message", "�ٽ� �α������ּ���.");
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

	// ���� ���� �޼��� count
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

			System.out.println("���ȣ :" + roomNo);

			chatinfo.put("roomNo", roomNo);

			System.out.println("�����̸��� : " + msgservice.getFriend(chatinfo));

			chatinfo.put("friend_email", msgservice.getFriend(chatinfo));

			if (msgservice.getFriend(chatinfo) == null || msgservice.getFriend(chatinfo).equals("")) {
				cntmsg.add(0);
			} else {

				totalmsg += msgservice.cntmsg(chatinfo);
				System.out.println("������ �޼��� ��:" + totalmsg);
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

	// ä��
	@RequestMapping(value = "/chat/{friend_email:.+}", method = RequestMethod.GET)
	public ModelAndView chat(Model model, MemberVO mvo, ChattingVO vo, @PathVariable("friend_email") String friend_email, HttpSession session, HttpServletRequest request) {

		ModelAndView view = new ModelAndView();
		
		if (session.getAttribute("login_email") == null || session.getAttribute("login_email").equals("")) {
			
			model.addAttribute("message", "�ٽ� �α������ּ���.");
			model.addAttribute("returnUrl", "/");
			view.setViewName("alertAndRedirect");

			return view;
			
		} else {
			String login_email = (String) session.getAttribute("login_email");

			vo.setEmail(login_email);

			// ���� �ִ��� üũ
			mvo.setEmail(login_email);
			System.out.println("login�� ��� =" + mvo.getEmail());
			int checkAccount = memberService.checkAccount(mvo);
			System.out.println("checkAccount=" + checkAccount);
			view.addObject("checkAccount", checkAccount);

			System.out.println("login_email:" + login_email);

			System.out.println("friend_email:" + friend_email);

			ChattingVO chattingCheck = chatservice.chattingCheck(login_email, friend_email);

			if (chattingCheck == null) {
				System.out.println("ä�ù����");
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

			System.out.println("�޽�������Ʈ 1 : " + msgList);
			view.addObject("msgList", msgList);

			List<MemberVO> memberinfo = memberService.info(login_email);
			view.addObject("memberinfo", memberinfo);

			mvo = memberService.oneInfo(friend_email);
			List<MemberVO> aaa = memberService.Search(friend_email);
			if (aaa.isEmpty()) {
				view.addObject("friend_email", "�˼����� ���");
				System.out.println("�˼����»��");
			} else {
				
				view.addObject("friend_email", friend_email);
				view.addObject("friend_name", memberService.oneInfo(friend_email).getName());
				System.out.println("111111111111111111111111111");
				
			}
			view.addObject("thumbnail", mvo.getThumbnail());

			// �̰ǹ���??
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

	// ģ������
	@RequestMapping(value = "/friend_delete/{friend_email:.+}", method = RequestMethod.GET)
	public ModelAndView friend_delete(HttpSession session, @PathVariable("friend_email") String friend_email) {

		ModelAndView view = new ModelAndView();

		String member_email = (String) session.getAttribute("login_email");

		memberService.friendDelete(member_email, friend_email);

		view.setViewName("redirect:/friend_list");

		return view;
	}

	// �� ������
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

	// ȸ��Ż��
	@RequestMapping(value = "/memberDelete", method = RequestMethod.GET)
	public ModelAndView memberDelete(HttpSession session) {

		ModelAndView view = new ModelAndView();

		String member_email = (String) session.getAttribute("login_email");

		memberService.memberDelete(member_email);

		session.invalidate();

		view.setViewName("redirect:/");

		return view;
	}

	// ȸ������ �̸��Ͽ��� ��ũŬ����
	@RequestMapping(value = "/kkt/{email:.+}", method = RequestMethod.GET)
	public ModelAndView join11(@PathVariable("email") String email, Model model) {

		ModelAndView view = new ModelAndView();
		model.addAttribute("message", "ȸ�������� �Ϸ�Ǿ����ϴ�.");
		model.addAttribute("returnUrl", "/");

		view.setViewName("alertAndRedirect");
		System.out.println("�̸���������" + email);
		int joincheck = memberService.joinCheck(email);

		System.out.println("joincheck : " + joincheck);

		if (joincheck == 0) {
			memberService.memberJoin(email);

			model.addAttribute("message", "ȸ�������� �Ϸ�Ǿ����ϴ�.");
			model.addAttribute("returnUrl", "/");

			view.setViewName("alertAndRedirect");
		} else {
			model.addAttribute("message", "�̹� ���ԵǾ����ϴ�.");
			model.addAttribute("returnUrl", "/");

			view.setViewName("alertAndRedirect");
		}

		return view;
	}

	// ���̵� ã��
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
			model.addAttribute("message", "���̵� ���������ʽ��ϴ�. ȸ�������� ���ּ���.");
			model.addAttribute("returnUrl", "/");
			view.setViewName("alertAndRedirect");
		} else {
			model.addAttribute("message", vo.getName() + "���� ���̵�� " + email + " �Դϴ�.");
			model.addAttribute("returnUrl", "/");
			view.setViewName("alertAndRedirect");
		}

		return view;
	}

	// ��й�ȣ ã��
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

		System.out.println("�̸������� name : " + name);
		System.out.println("�̸������� e_mail : " + e_mail);
		System.out.println("�̸������� pw : " + pw);

		String pwsearch = memberService.pwSearch(vo);

		if (pwsearch != null) {
			email.setContent("�ӽú�й�ȣ�� " + pw + " �Դϴ�.");
			email.setReceiver(e_mail);
			email.setSubject("[����] " + name + "�� ��й�ȣ ã�� �����Դϴ�.");
			emailSender.SendEmail(email);

			vo.setEmail(e_mail);

			PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			vo.setPw(passwordEncoder.encode(pw));

			memberService.pwUpdate(vo);

			model.addAttribute("message", "�ӽú�й�ȣ�� �̸��Ϸ� ���۵Ǿ����ϴ�.");
			model.addAttribute("returnUrl", "/");

			view.setViewName("alertAndRedirect");

		} else {
			model.addAttribute("message", "���������ʴ� ������Դϴ�.");
			model.addAttribute("returnUrl", "/");

			view.setViewName("alertAndRedirect");
		}
		return view;
	}

	// ��й�ȣ ����
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

			view.setViewName("pwchange2"); // �н����尡 ��ġ�ϸ� ����â����

		} else {// �н����尡 ��ġ���� ������ alertâ ���
			model.addAttribute("message", "��й�ȣ�� ��ġ�����ʽ��ϴ�.");
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
		System.out.println("��й�ȣ ���� ��� : " + vo.getPw());

		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		vo.setPw(passwordEncoder.encode(vo.getPw()));

		System.out.println("��й�ȣ ���� �̸��� : " + vo.getEmail());
		System.out.println("��й�ȣ ���� ��� : " + vo.getPw());

		memberService.pwUpdate(vo);

		session.invalidate();

		model.addAttribute("message", "��й�ȣ�� ����Ǿ����ϴ�. �ٽ� �α������ּ���.");
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
