package net.nigne.kkt.service;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import net.nigne.kkt.domain.ProductImageVO;
import net.nigne.kkt.persistence.ProductImageDAO;

@Service
public class ProductImageServiceImpl implements ProductImageService {
	@Inject
	private ProductImageDAO dao;
	
	@Override
	public ProductImageVO get(int no) {
		return dao.get(no);
	}

	@Override
	public List<ProductImageVO> getList() {
		return dao.getList();
	}

	@Transactional
	@Override
	public void update(ProductImageVO vo) {
		dao.update(vo);
		
	}

	@Transactional
	@Override
	public void delete(int no) {
		System.out.println("ProductImageService의 delete()로 접속됨"+no);
		dao.delete(no);
		
	}
	
	@Transactional
	@Override
	public void delete_image(int image_no) {
		System.out.println("ProductImageService의 delete_image()로 접속됨"+image_no);
		dao.delete_image(image_no);
		
	}

	@Transactional
	@Override
	public void insert(ProductImageVO vo) {
		dao.insert(vo);
		
	}
	
	@Transactional
	@Override
	public void uploadFile(int no, String originalfileName, String saveFileName) {
	    HashMap<String, Object> hm = new HashMap<>();
	    hm.put("originalfileName", originalfileName);
	    hm.put("saveFileName", saveFileName);
	    hm.put("no", no);
	    dao.uploadFile(hm);
	}
	
	@Transactional
	@Override
	public void update_uploadFile(int trade_no, String originalfileName, String saveFileName) {
	    HashMap<String, Object> hm = new HashMap<>();
	    hm.put("originalfileName", originalfileName);
	    hm.put("saveFileName", saveFileName);
	    hm.put("trade_no", trade_no);
	    System.out.println("ProductImageService의 update_uploadFile()로 접속됨"+originalfileName+"오리지널파일네임이고"+saveFileName+"저장되는파일이름이고");
	    dao.update_uploadFile(hm);
	}

}
