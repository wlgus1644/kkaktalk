package net.nigne.kkt.persistence;

import java.util.HashMap;
import java.util.List;

import net.nigne.kkt.domain.ChattingVO;
import net.nigne.kkt.domain.FriendsVO;
import net.nigne.kkt.domain.User_listVO;

public interface User_listDAO {
	
	public String usercheck(HashMap<String, Object> user_list);
	public void insertUser(HashMap<String, Object> user_list);
	public List roomlist(String email);
	
	public void exit_room(User_listVO vo);
	public void deleteRoom(int no);
	public String myUserListDate(ChattingVO vo);
	public String oppositeUserListDate(ChattingVO vo);
	public void updateMyDate(ChattingVO vo);
	public void updateOppDate(ChattingVO vo);
}

