package net.nigne.kkt.persistence;

import java.util.List;
import java.util.Map;

import net.nigne.kkt.domain.TradeVO;

public interface TradeDAO {
	
	public int getNo();
	public TradeVO get(int no);
	public List<TradeVO> getList();
	public List<TradeVO> getSearchList(Map<String, String> map);
	public void insertProduct(TradeVO vo);
	public void deleteProduct(int no);
	public void updateProduct(TradeVO vo);
	public String thumbnail(int no);

}



