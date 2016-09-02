package net.nigne.kkt.persistence;

import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.inject.Inject;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import net.nigne.kkt.domain.EscrowVO;
import net.nigne.kkt.domain.TradeVO;

@Repository
public class EscrowDAOImpl implements EscrowDAO{
	
	@Inject
	private SqlSession sqlSession;
	private static final String namespace= "net.nigne.kkt.mappers.escrowMapper";

	@Override
	public EscrowVO get(int trade_no) {
		return sqlSession.selectOne(namespace+".get", trade_no);
	}
	@Override
	public List<EscrowVO> getList() {
		return sqlSession.selectList(namespace+".getList");
	
	}
	@Override
	public List<EscrowVO> getSearchList(Map<String, String> map) {
		return sqlSession.selectList(namespace+".getSearchList", map);
	}
	@Override
	public void insertEscrow(EscrowVO vo) {
		System.out.println("daoImpl.insertEscrow"+vo);
		sqlSession.insert(namespace+".insertEscrow", vo);
		System.out.println("daoImpl.insertEscrowءʥ222"+vo);
	}
	
	@Override
	public void insertAmount(EscrowVO vo) {
		System.out.println("daoImpl.insertAmount()��ءʥ222"+vo);
		sqlSession.update(namespace+".insertAmount", vo);		
	}
	
	@Override
	public void updateAmount(EscrowVO vo) {
		sqlSession.update(namespace+".updateAmount", vo);		
	}
	
	
	
	@Override
	public void deleteEscrow(int no) {
		sqlSession.delete(namespace+".deleteProduct", no);		
	}
	
	@Override
	public void updateEscrow(EscrowVO vo) {
		sqlSession.update(namespace+".updateEscrow", vo);
		
	}
	@Override
	public int selectAmountCount(int trade_no) {
		return sqlSession.selectOne(namespace+".selectAmountCount",trade_no);
	}

	@Override
	public void updateState(EscrowVO vo) {
		sqlSession.update(namespace+".updateState", vo);
		
	}


}
