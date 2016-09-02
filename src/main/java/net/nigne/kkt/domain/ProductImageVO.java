package net.nigne.kkt.domain;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class ProductImageVO {
	int file_size;

	public int getFile_size() {
		return file_size;
	}
	public void setFile_size(int file_size) {
		this.file_size = file_size;
	}
	
	String image;
	String up_dir;
	int image_no;
	int trade_no;
	
	public String getUp_dir() {
		return up_dir;
	}
	public void setUp_dir(String up_dir) {
		this.up_dir = up_dir;
	}
	
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public int getImage_no() {
		return image_no;
	}
	public void setImage_no(int image_no) {
		this.image_no = image_no;
	}
	public int getTrade_no() {
		return trade_no;
	}
	public void setTrade_no(int trade_no) {
		this.trade_no = trade_no;
	}

}
