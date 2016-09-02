package net.nigne.kkt.service;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import net.nigne.kkt.domain.ChattingVO;
import net.nigne.kkt.domain.User_listVO;
import net.nigne.kkt.persistence.User_listDAO;

@Service
public class User_listServiceImpl implements User_listService {
	
	@Inject
	private User_listDAO dao;
	
	@Override
	public String usercheck(HashMap<String, Object> user_list) {
		// TODO Auto-generated method stub
		return dao.usercheck(user_list);
	}

	@Override
	public void insertUser(HashMap<String, Object> user_list) {
		// TODO Auto-generated method stub
		dao.insertUser(user_list);
	}

	@Override
	public List roomlist(String email) {
		// TODO Auto-generated method stub
		return dao.roomlist(email);
	}
	@Override
	public void exit_room(User_listVO vo) {
		dao.exit_room(vo);
	}
	
	@Override
	public void deleteRoom(int no) {
		dao.deleteRoom(no);
	}

	@Override
	public String myUserListDate(ChattingVO vo) {
		return dao.myUserListDate(vo);
	}

	@Override
	public String oppositeUserListDate(ChattingVO vo) {
		return dao.oppositeUserListDate(vo);
	}

	@Override
	public void updateMyDate(ChattingVO vo) {
		dao.updateMyDate(vo);
	}

	@Override
	public void updateOppDate(ChattingVO vo) {
		dao.updateOppDate(vo);
	}

}
