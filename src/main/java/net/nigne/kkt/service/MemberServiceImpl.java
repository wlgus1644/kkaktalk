package net.nigne.kkt.service;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import net.nigne.kkt.domain.FriendsVO;
import net.nigne.kkt.domain.MemberVO;
import net.nigne.kkt.persistence.MemberDAO;

@Service
public class MemberServiceImpl implements MemberService {

	@Inject
	private MemberDAO dao;
	
	@Override
	public List<MemberVO> getList() {
		return dao.getList();
	}

	@Override
	public void insert(MemberVO vo) {
		dao.insert(vo);
	}

	@Override
	public String login(String email) {
		return dao.login(email);
	}

	@Override
	public List<MemberVO> friendList(String email) {
		return dao.friendList(email);
	}

	@Override
	public List<MemberVO> info(String email) {
		return dao.info(email);
	}

	@Override
	public List<MemberVO> friendSearch(String email) {
		return dao.friendSearch(email);
	}

	@Override
	public void friendAdd(FriendsVO vo) {
		dao.friendAdd(vo);
	}

	@Override
	public List<MemberVO> friendListSearch(String email, String search) {
		return dao.friendListSearch(email, search);
	}

	@Override
	public List<FriendsVO> alreadyFriend(String member_email, String friend_email) {
		return dao.alreadyFriend(member_email, friend_email);
	}

	@Override
	public void memberUpdate(MemberVO vo) {
		dao.memberUpdate(vo);
	}

	@Override
	public MemberVO oneInfo(String email) {
		return dao.oneInfo(email);
	}

	@Override
	public MemberVO chatlist(HashMap<String, Object> chatinfo) {
		return dao.chatlist(chatinfo);
	}
	
	@Override
	   public int checkAccount(MemberVO vo) {
	      return dao.checkAccount(vo);
	   }

	   @Override
	   public void updateAccount(MemberVO vo) {
	      dao.updateAccount(vo);
	   }
	   

	@Override
	public void friendDelete(String member_email, String friend_email) {
		dao.friendDelete(member_email, friend_email);
	}
	
	@Override
	public List<MemberVO> Search(String email) {
		return dao.Search(email);
	}

	@Override
	public void memberDelete(String email) {
		dao.memberDelete(email);
	}

	@Override
	public String idSearch(String name, String phone_num) {
		return dao.idSearch(name, phone_num);
	}

	@Override
	public String pwSearch(MemberVO vo) {
		return dao.pwSearch(vo);
	}

	@Override
	public void pwUpdate(MemberVO vo) {
		dao.pwUpdate(vo);
	}

	@Override
	public void memberJoin(String email) {
		dao.memberJoin(email);
	}

	@Override
	public int joinCheck(String email) {
		return dao.joinCheck(email);
	}

}
