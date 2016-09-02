package net.nigne.kkt.domain;

public class ChattingVO {
	private int no;
	private String buyer_email;
	private String email;
	private String user_email;
	private String name;
	private String thumbnail;
	private String msg;
	private int chatting_room_no;
	private int trade_no;
	private String notice;
	private String date;
	private int check_user;
	
	public String getNotice() {
		return notice;
	}
	public void setNotice(String notice) {
		this.notice = notice;
	}
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public int getTrade_no() {
		return trade_no;
	}
	public void setTrade_no(int trade_no) {
		this.trade_no = trade_no;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public int getChatting_room_no() {
		return chatting_room_no;
	}
	public void setChatting_room_no(int chatting_room_no) {
		this.chatting_room_no = chatting_room_no;
	}
	public String getUser_email() {
		return user_email;
	}
	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}
	public String getBuyer_email() {
		return buyer_email;
	}
	public void setBuyer_email(String buyer_email) {
		this.buyer_email = buyer_email;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getCheck_user() {
		return check_user;
	}
	public void setCheck_user(int check_user) {
		this.check_user = check_user;
	}
	
	
}
