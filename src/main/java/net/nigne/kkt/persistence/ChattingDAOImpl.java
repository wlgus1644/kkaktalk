package net.nigne.kkt.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import net.nigne.kkt.domain.ChattingVO;
import net.nigne.kkt.domain.ProductImageVO;

@Repository
public class ChattingDAOImpl implements ChattingDAO {

	@Inject
	private SqlSession sqlSession;
	private static final String namespace = "net.nigne.kkt.mappers.chattingMapper";
	
	@Override
	public List<ChattingVO> chattingList(String email) {
		return sqlSession.selectList(namespace + ".chattingList", email);
	}

	@Override
	public ChattingVO chattingCheck(String email, String friend_email) {
		Map<String, Object> map = new HashMap<>();
		
		map.put("email", email);
		map.put("friend_email", friend_email);
		
		return sqlSession.selectOne(namespace + ".chattingCheck", map);
	}
	
	@Override
	public void chattingInsert() {
		sqlSession.insert(namespace + ".chattingInsert");
	}
	@Override
	public void chattingUpdate(ChattingVO vo) {
		sqlSession.update(namespace + ".chattingupdate", vo);
		
	}

	@Override
	public void userListInsert(int chatting_room_no, String email) {
		Map<String, Object> map = new HashMap<>();
		
		map.put("chatting_room_no", chatting_room_no);
		map.put("email", email);
		sqlSession.insert(namespace + ".userListInsert", map);
	}

	@Override
	public int chattingRoomNO() {
		return sqlSession.selectOne(namespace + ".chattingRoomNO");
	}

	@Override
	public void msgInsert(ChattingVO vo) {
		sqlSession.insert(namespace + ".msgInsert", vo);
	}

	@Override
	public List<ChattingVO> msgList(String email, String friend_email, String date) {
		Map<String, Object> map = new HashMap<>();
		
		map.put("email", email);
		map.put("friend_email", friend_email);
		map.put("date", date);
		
		return sqlSession.selectList(namespace + ".msgList", map);
	}

	@Override
	public ChattingVO chattingRoomInfo(int no) {
		return sqlSession.selectOne(namespace + ".chattingRoomInfo", no);
	}



}
