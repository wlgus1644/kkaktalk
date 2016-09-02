package net.nigne.kkt.domain;

import java.util.List;

import net.nigne.kkt.domain.ProductImageVO;

public class EscrowVO {
	
	int no;
	int amount;
	String producttitle;
	String address;
	String address1;
	String address2;
	String postcode;
	String date;
	String buyer_received_check;
	String seller_received_check;
	String deposit_check;
	int trade_no;
	String seller_account_num;
	String buyer_account_num;
	String bank_type;
	String addressee;
	String addresseephonenumber;
	String state;
	String seller_address;
	String seller_postcode;
	String buyer_bank_type;
	String postcode1;
	String postcode2;
	
	
	public String getBuyer_bank_type() {
		return buyer_bank_type;
	}
	public String getPostcode1() {
		return postcode1;
	}
	public void setPostcode1(String postcode1) {
		this.postcode1 = postcode1;
	}
	public String getPostcode2() {
		return postcode2;
	}
	public void setPostcode2(String postcode2) {
		this.postcode2 = postcode2;
	}
	public void setBuyer_bank_type(String buyer_bank_type) {
		this.buyer_bank_type = buyer_bank_type;
	}
	public String getSeller_address() {
		return seller_address;
	}
	public void setSeller_address(String seller_address) {
		this.seller_address = seller_address;
	}
	public String getSeller_postcode() {
		return seller_postcode;
	}
	public void setSeller_postcode(String seller_postcode) {
		this.seller_postcode = seller_postcode;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getBuyer_received_check() {
		return buyer_received_check;
	}
	public void setBuyer_received_check(String buyer_received_check) {
		this.buyer_received_check = buyer_received_check;
	}
	public String getSeller_received_check() {
		return seller_received_check;
	}
	public void setSeller_received_check(String seller_received_check) {
		this.seller_received_check = seller_received_check;
	}
	public String getDeposit_check() {
		return deposit_check;
	}
	public void setDeposit_check(String deposit_check) {
		this.deposit_check = deposit_check;
	}
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	public String getAddressee() {
		return addressee;
	}
	public void setAddressee(String addressee) {
		this.addressee = addressee;
	}
	public String getAddresseephonenumber() {
		return addresseephonenumber;
	}
	public void setAddresseephonenumber(String addresseephonenumber) {
		this.addresseephonenumber = addresseephonenumber;
	}
	public String getBank_type() {
		return bank_type;
	}
	public void setBank_type(String bank_type) {
		this.bank_type = bank_type;
	}
	
	public String getSeller_account_num() {
		return seller_account_num;
	}
	public void setSeller_account_num(String seller_account_num) {
		this.seller_account_num = seller_account_num;
	}
	public String getBuyer_account_num() {
		return buyer_account_num;
	}
	public void setBuyer_account_num(String buyer_account_num) {
		this.buyer_account_num = buyer_account_num;
	}
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public String getProducttitle() {
		return producttitle;
	}
	public void setProducttitle(String producttitle) {
		this.producttitle = producttitle;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPostcode() {
		return postcode;
	}
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}

	public int getTrade_no() {
		return trade_no;
	}
	public void setTrade_no(int trade_no) {
		this.trade_no = trade_no;
	}

}
