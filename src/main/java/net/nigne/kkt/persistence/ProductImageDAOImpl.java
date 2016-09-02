package net.nigne.kkt.persistence;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import net.nigne.kkt.domain.ProductImageVO;

@Repository
public class ProductImageDAOImpl implements ProductImageDAO{
	
	
	@Inject
	private SqlSession sqlSession;
	
	private static final String namespace ="net.nigne.kkt.mappers.productImageMapper";
	@Override
	public ProductImageVO get(int no) {
		return sqlSession.selectOne(namespace + ".get", no); 
	}

	@Override
	public List<ProductImageVO> getList() {
		return sqlSession.selectList(namespace + ".getList"); 
	}

	@Override
	public void insert(ProductImageVO vo) {
		sqlSession.insert(namespace+ ".insert", vo);
		
	}

	@Override
	public void update(ProductImageVO vo) {
		sqlSession.update(namespace + ".update", vo);
		
	}

	@Override
	public void delete(int no) {
		sqlSession.delete(namespace + ".delete", no);
		
	}
	
	@Override
	public void delete_image(int image_no) {
		sqlSession.delete(namespace + ".delete_image", image_no);
		
	}


	public void uploadFile(HashMap<String, Object> hm){
		sqlSession.insert(namespace+".uploadFile", hm);
	}

	@Override
	public void update_uploadFile(HashMap<String, Object> hm) {
		System.out.println("hm???"+hm);
		sqlSession.update(namespace+".update_uploadFile", hm);
	}





}
