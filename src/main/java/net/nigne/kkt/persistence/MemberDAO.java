package net.nigne.kkt.persistence;

import java.util.HashMap;
import java.util.List;

import net.nigne.kkt.domain.FriendsVO;
import net.nigne.kkt.domain.MemberVO;

public interface MemberDAO {

	public List<MemberVO> getList();
	public List<MemberVO> info(String email);
	public void insert(MemberVO vo);
	public String login(String email);
	public List<MemberVO> friendList(String email);
	public List<MemberVO> friendListSearch(String email, String search);
	public List<MemberVO> friendSearch(String email);
	public void friendAdd(FriendsVO vo);
	public List<FriendsVO> alreadyFriend(String member_email, String friend_email);
	public void memberUpdate(MemberVO vo);
	public int checkAccount(MemberVO vo);
	public void updateAccount(MemberVO vo);
	public void friendDelete(String member_email, String friend_email);
	public List<MemberVO> Search(String email);
	public void memberDelete(String email);
	public String idSearch(String name, String phone_num);
	public String pwSearch(MemberVO vo);
	public void pwUpdate(MemberVO vo);
	public void memberJoin(String email);
	public int joinCheck(String email);
	
	public MemberVO oneInfo(String email);
	public MemberVO chatlist(HashMap<String, Object> chatinfo);
}
