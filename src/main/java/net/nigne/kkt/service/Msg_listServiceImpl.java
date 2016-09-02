package net.nigne.kkt.service;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import net.nigne.kkt.domain.Msg_listVO;
import net.nigne.kkt.persistence.Msg_listDAO;

@Service
public class Msg_listServiceImpl implements Msg_listService {
	
	@Inject
	private Msg_listDAO dao;
	
	@Override
	public void insert(Msg_listVO vo) {
		// TODO Auto-generated method stub
		dao.insert(vo);
	}
	
	@Override
	public List<Msg_listVO> getMsg_list(int roomNo) {
		// TODO Auto-generated method stub
		return dao.getMsg_list(roomNo);
	}

	@Override
	public Msg_listVO lastmsg(HashMap<String, Object> chatinfo) {
		// TODO Auto-generated method stub
		return dao.lastmsg(chatinfo);
	}

	@Override
	public void checkmsg(HashMap<String, Object> checkmsg) {
		// TODO Auto-generated method stub
		dao.checkmsg(checkmsg);
	}

	@Override
	public int cntmsg(HashMap<String, Object> chatinfo) {
		// TODO Auto-generated method stub
		return dao.cntmsg(chatinfo);
	}

	@Override
	public String getFriend(HashMap<String, Object> chatinfo) {
		// TODO Auto-generated method stub
		return dao.getFriend(chatinfo);
	}

}
