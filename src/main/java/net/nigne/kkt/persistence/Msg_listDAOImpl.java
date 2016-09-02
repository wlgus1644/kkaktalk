package net.nigne.kkt.persistence;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import net.nigne.kkt.domain.Msg_listVO;

@Repository
public class Msg_listDAOImpl implements Msg_listDAO {

	@Inject
	private SqlSession sqlSession;
	private static final String namespace = "net.nigne.kkt.mappers.chattingMapper";
	
	@Override
	public void insert(Msg_listVO vo) {
		// TODO Auto-generated method stub
		sqlSession.insert(namespace + ".insert", vo);
	}

	@Override
	public List<Msg_listVO> getMsg_list(int roomNo) {
		// TODO Auto-generated method stub
		return sqlSession.selectList(namespace + ".getMsg_list", roomNo);
	}

	@Override
	public Msg_listVO lastmsg(HashMap<String, Object> chatinfo) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(namespace + ".lastmsg", chatinfo);
	}

	@Override
	public void checkmsg(HashMap<String, Object> checkmsg) {
		sqlSession.update(namespace + ".checkmsg", checkmsg);
		
	}

	@Override
	public int cntmsg(HashMap<String, Object> chatinfo) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(namespace + ".cntmsg", chatinfo);
	}

	@Override
	public String getFriend(HashMap<String, Object> chatinfo) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(namespace + ".getFriend", chatinfo);
	}

}
