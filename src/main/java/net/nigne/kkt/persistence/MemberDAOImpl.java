package net.nigne.kkt.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import net.nigne.kkt.domain.FriendsVO;
import net.nigne.kkt.domain.MemberVO;

@Repository
public class MemberDAOImpl implements MemberDAO {

	@Inject
	private SqlSession sqlSession;
	private static final String namespace = "net.nigne.kkt.mappers.memberMapper";
	private static final String namespace2 = "net.nigne.kkt.mappers.chattingMapper";
	
	@Override
	public List<MemberVO> getList() {
		return sqlSession.selectList(namespace + ".memberList");
	}

	@Override
	public void insert(MemberVO vo) {
		sqlSession.insert(namespace + ".memberInsert", vo);
	}

	@Override
	public String login(String email) {
		return sqlSession.selectOne(namespace+".memberLogin", email);
	}

	@Override
	public List<MemberVO> friendList(String email) {
		return sqlSession.selectList(namespace + ".friendList", email);
	}

	@Override
	public List<MemberVO> info(String email) {
		return sqlSession.selectList(namespace + ".memberInfo", email);
	}

	@Override
	public List<MemberVO> friendSearch(String email) {
		return sqlSession.selectList(namespace + ".friendSearch", email);
	}

	@Override
	public void friendAdd(FriendsVO vo) {
		sqlSession.insert(namespace + ".friendAdd", vo);
		
	}

	@Override
	public List<MemberVO> friendListSearch(String email, String search) {

		Map<String, Object> map = new HashMap<>();
		
		map.put("email", email);
		map.put("search", search);
		
		return sqlSession.selectList(namespace + ".friendListSearch", map);
	}

	@Override
	public List<FriendsVO> alreadyFriend(String member_email, String friend_email) {
		Map<String, Object> map = new HashMap<>();
	
		map.put("member_email", member_email);
		map.put("friend_email", friend_email);
		
		return sqlSession.selectList(namespace + ".alreadyFriend", map);
	}

	@Override
	public void memberUpdate(MemberVO vo) {
		sqlSession.update(namespace + ".memberUpdate", vo);
	}

	@Override
	public MemberVO oneInfo(String email) {
		return sqlSession.selectOne(namespace + ".oneInfo", email);
	}
	
	@Override
	public MemberVO chatlist(HashMap<String, Object> chatinfo) {
		return sqlSession.selectOne(namespace2 + ".chatlist", chatinfo);
	}
	
	@Override
	   public int checkAccount(MemberVO vo) {
	      return sqlSession.selectOne(namespace+".checkAccount", vo);
	   }

	@Override
   public void updateAccount(MemberVO vo) {
		sqlSession.update(namespace+".updateAccount", vo);
	}
	   
	@Override
	public void friendDelete(String member_email, String friend_email) {
		Map<String, Object> map = new HashMap<>();
			
		map.put("member_email", member_email);
		map.put("friend_email", friend_email);
			
		sqlSession.delete(namespace + ".friendDelete", map);
	}
	
	@Override
	public List<MemberVO> Search(String email) {
		return sqlSession.selectList(namespace + ".Search", email);
	}

	@Override
	public void memberDelete(String email) {
		sqlSession.update(namespace + ".memberDelete", email);
	}

	@Override
	public String idSearch(String name, String phone_num) {
		Map<String, Object> map = new HashMap<>();
		
		map.put("name", name);
		map.put("phone_num", phone_num);
		
		return sqlSession.selectOne(namespace + ".idSearch", map);
	}

	@Override
	public String pwSearch(MemberVO vo) {
		return sqlSession.selectOne(namespace + ".pwSearch", vo);
	}

	@Override
	public void pwUpdate(MemberVO vo) {
		sqlSession.update(namespace + ".pwUpdate", vo);
	}

	@Override
	public void memberJoin(String email) {
		sqlSession.update(namespace + ".memberJoin", email);
	}

	@Override
	public int joinCheck(String email) {
		return sqlSession.selectOne(namespace + ".joinCheck", email);
	}
	
}
