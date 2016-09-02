package net.nigne.kkt.persistence;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import net.nigne.kkt.domain.TradeVO;

@Repository
public class TradeDAOImpl implements TradeDAO{
	
	@Inject
	private SqlSession sqlSession;
	private static final String namespace= "net.nigne.kkt.mappers.tradeMapper";
	
	@Override
	public int getNo() {
		return sqlSession.selectOne(namespace+".getNo");
	}
	@Override
	public TradeVO get(int no) {
		return sqlSession.selectOne(namespace+".get", no);
	}
	
	@Override
	public List<TradeVO> getSearchList(Map<String, String> map) {
		return sqlSession.selectList(namespace+".getSearchList", map);
	}
	
	
	@Override
	public List<TradeVO> getList() {
		return sqlSession.selectList(namespace+".getList");
	}
	
	@Override
	public void insertProduct(TradeVO vo) {
		sqlSession.insert(namespace+".insertProduct", vo);	
	}
	
	@Override
	public void deleteProduct(int no) {
		sqlSession.delete(namespace+".deleteProduct", no);	
	}
	
	@Override
	public void updateProduct(TradeVO vo) {
		sqlSession.update(namespace+".updateProduct", vo);	
	}
	 @Override
	   public String thumbnail(int no) {
	      return sqlSession.selectOne(namespace+".thumbnail", no);
	}


}
