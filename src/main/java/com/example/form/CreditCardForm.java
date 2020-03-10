package com.example.form;

public class CreditCardForm {
	
	private String user_id;
	private String order_number;
	private String amount;
	private String card_number;
	private String card_exp_month;
	private String card_exp_year;
	private String card_name;
	private String card_cvv;
	private String paymentMethod;
	@Override
	public String toString() {
		return "CreditCardForm [user_id=" + user_id + ", order_number=" + order_number + ", amount=" + amount
				+ ", card_number=" + card_number + ", card_exp_month=" + card_exp_month + ", card_exp_year="
				+ card_exp_year + ", card_name=" + card_name + ", card_cvv=" + card_cvv + ", paymentMethod="
				+ paymentMethod + "]";
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getOrder_number() {
		return order_number;
	}
	public void setOrder_number(String order_number) {
		this.order_number = order_number;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getCard_number() {
		return card_number;
	}
	public void setCard_number(String card_number) {
		this.card_number = card_number;
	}
	public String getCard_exp_month() {
		return card_exp_month;
	}
	public void setCard_exp_month(String card_exp_month) {
		this.card_exp_month = card_exp_month;
	}
	public String getCard_exp_year() {
		return card_exp_year;
	}
	public void setCard_exp_year(String card_exp_year) {
		this.card_exp_year = card_exp_year;
	}
	public String getCard_name() {
		return card_name;
	}
	public void setCard_name(String card_name) {
		this.card_name = card_name;
	}
	public String getCard_cvv() {
		return card_cvv;
	}
	public void setCard_cvv(String card_cvv) {
		this.card_cvv = card_cvv;
	}
	public String getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	
	
	
	
	

}
