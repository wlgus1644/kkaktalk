package net.nigne.kkt.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import net.nigne.kkt.domain.ProductImageVO;

public interface ProductImageService {
	public ProductImageVO get(int no);
	public List<ProductImageVO> getList();
	public void update(ProductImageVO vo);
	public void delete(int no);
	public void delete_image(int image_no);
	public void insert(ProductImageVO vo);
	public void uploadFile(int no, String originalfileName, String saveFileName);
	public void update_uploadFile(int trade_no,String originalfileName, String saveFileName);
	
}
