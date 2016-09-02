package net.nigne.kkt.service;

import java.util.List;

import net.nigne.kkt.domain.ChattingVO;

public interface ChattingService {

	public List<ChattingVO> chattingList(String email);
	public ChattingVO chattingCheck(String email, String friend_email);
	public void chattingInsert();
	public void userListInsert(int chatting_room_no, String email);
	public int chattingRoomNO();
	public void msgInsert(ChattingVO vo);
	public List<ChattingVO> msgList(String email, String friend_email, String date);
	public void chattingUpdate(ChattingVO vo);
	public ChattingVO chattingRoomInfo(int no);
}
