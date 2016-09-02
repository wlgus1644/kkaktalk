package net.nigne.kkt.service;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.nigne.kkt.domain.ProductImageVO;
import net.nigne.kkt.domain.EscrowVO;
import net.nigne.kkt.persistence.ProductImageDAO;
import net.nigne.kkt.persistence.EscrowDAO;

@Service
public class EscrowServiceImpl implements EscrowService{
	
	@Inject
	private EscrowDAO dao;
	
	@Inject
	private ProductImageDAO imagedao;

	@Transactional
	@Override
	public EscrowVO get(int trade_no) {
		return dao.get(trade_no);
	}
	
	@Transactional
	@Override
	public List<EscrowVO> getSearchList(Map<String, String> map) {
		System.out.println(map);
		return dao.getSearchList(map);
	}
	
	@Transactional
	@Override
	public List<EscrowVO> getList() {
		return dao.getList();
	}
	
	@Transactional
	@Override
	public void insertEscrow(EscrowVO vo) {
		dao.insertEscrow(vo);
		
	}
	
	@Transactional
	@Override
	public void insertAmount(EscrowVO vo) {
		dao.insertAmount(vo);
		
	}
	
	@Transactional
	@Override
	public void updateAmount(EscrowVO vo) {
		dao.updateAmount(vo);
		
	}
	
	@Transactional
	@Override
	public int selectAmountCount(int trade_no) {
		return dao.selectAmountCount(trade_no);	
	}
	@Transactional
	@Override
	public void deleteEscrow(int no) {

		dao.deleteEscrow(no);
		
	}
	@Transactional
	@Override
	public void updateEscrow(EscrowVO vo) {
		dao.updateEscrow(vo);
		
	}
	@Override
	public void updateState(EscrowVO vo) {
		dao.updateState(vo);
		
	}


}
