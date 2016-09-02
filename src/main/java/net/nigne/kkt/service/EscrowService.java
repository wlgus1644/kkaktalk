package net.nigne.kkt.service;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import net.nigne.kkt.domain.EscrowVO;

public interface EscrowService {
	
	public EscrowVO get(int trade_no);
	public List<EscrowVO> getList();
	public List<EscrowVO> getSearchList(Map<String, String> map);
	public void insertEscrow(EscrowVO vo);
	public int selectAmountCount(int trade_no);
	public void insertAmount(EscrowVO vo);
	public void updateAmount(EscrowVO vo);
	public void deleteEscrow(int no);
	public void updateEscrow(EscrowVO vo);
	public void updateState(EscrowVO vo);
}
