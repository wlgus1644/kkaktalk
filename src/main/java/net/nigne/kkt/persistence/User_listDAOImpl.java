package net.nigne.kkt.persistence;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import net.nigne.kkt.domain.ChattingVO;
import net.nigne.kkt.domain.User_listVO;

@Repository
public class User_listDAOImpl implements User_listDAO {
	
	@Inject
	private SqlSession sqlSession;
	private static final String namespace = "net.nigne.kkt.mappers.chattingMapper";
	
	@Override
	public String usercheck(HashMap<String, Object> user_list) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(namespace + ".usercheck", user_list);
	}

	@Override
	public void insertUser(HashMap<String, Object> user_list) {
		// TODO Auto-generated method stub
		sqlSession.insert(namespace + ".insertUser", user_list);
	}

	@Override
	public List roomlist(String email) {
		// TODO Auto-generated method stub
		return sqlSession.selectList(namespace + ".roomlist", email);
	}
	
	@Override
	public void exit_room(User_listVO vo) {
		sqlSession.update(namespace + ".exit", vo);
		
	}

	@Override
	public void deleteRoom(int no) {
		sqlSession.delete(namespace + ".deleteRoom", no);
		
	}

	@Override
	public String myUserListDate(ChattingVO vo) {
		return sqlSession.selectOne(namespace + ".myUserListDate", vo);
	}

	@Override
	public String oppositeUserListDate(ChattingVO vo) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(namespace + ".oppositeUserListDate", vo);
	}

	@Override
	public void updateMyDate(ChattingVO vo) {
		sqlSession.update(namespace + ".updateMyDate", vo);
	}

	@Override
	public void updateOppDate(ChattingVO vo) {
		sqlSession.update(namespace + ".updateOppDate", vo);
	}

}
