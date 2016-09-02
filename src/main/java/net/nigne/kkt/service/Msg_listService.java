package net.nigne.kkt.service;

import java.util.HashMap;
import java.util.List;

import net.nigne.kkt.domain.Msg_listVO;

public interface Msg_listService {
	
	public List<Msg_listVO> getMsg_list(int roomNo);
	public void insert(Msg_listVO vo);
	public Msg_listVO lastmsg(HashMap<String, Object> chatinfo);
	public void checkmsg(HashMap<String, Object> checkmsg);
	public int cntmsg(HashMap<String, Object> chatinfo);
	public String getFriend(HashMap<String, Object> chatinfo);
	
}
