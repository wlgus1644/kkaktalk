package net.nigne.kkt.service;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import net.nigne.kkt.domain.ProductImageVO;
import net.nigne.kkt.domain.TradeVO;
import net.nigne.kkt.persistence.ProductImageDAO;
import net.nigne.kkt.persistence.TradeDAO;

@Service
public class TradeServiceImpl implements TradeService{
	
	@Inject
	private TradeDAO dao;
	
	@Inject
	private ProductImageDAO imagedao;

	
	@Override
	public int getNo() {
		return dao.getNo();
	}

	@Override
	public TradeVO get(int no) {
		return dao.get(no);
	}
	

	@Override
	public List<TradeVO> getSearchList(Map<String, String> map) {
		System.out.println("TradeService狼 getSearchList()肺 立加凳");
		System.out.println(map);
		return dao.getSearchList(map);

	}
	

	@Override
	public List<TradeVO> getList() {
		return dao.getList();
	}
	
	
	@Override
	public void insertProduct(TradeVO vo) {
		dao.insertProduct(vo);
		
	}

	@Override
	public void deleteProduct(int no) {
		System.out.println("TradeService狼 deleteProduct()肺 立加凳"+no);
		dao.deleteProduct(no);
		
	}

	@Override
	public void updateProduct(TradeVO vo) {
		dao.updateProduct(vo);
	}
	@Override
	   public String thumbnail(int no) {
	      return dao.thumbnail(no);
	 }



}
