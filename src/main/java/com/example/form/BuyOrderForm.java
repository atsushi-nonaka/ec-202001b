package com.example.form;

import java.sql.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * オーダーフォーム.
 * 
 * @author nonaka
 *
 */
public class BuyOrderForm {
	/** ID */
	private String userId;
	/** 注文日 */
	private Date orderDate;
	/** 宛先氏名 */
	@NotBlank(message = "お名前を入力して下さい")
	private String destinationName;
	/** 宛先Eメール */
	@NotBlank(message = "メールアドレスを入力して下さい")
	private String destinationEmail;
	/** 宛先郵便番号 */
	@NotBlank(message = "郵便番号を入力してください")
	@Pattern(regexp="^[0-9]{7}$", message="ハイフンを付けず、半角数字7桁の形式で入力してください (例:3335555)")
	private String destinationZipcode;
	/** 宛先住所 */
	@NotBlank(message = "住所を入力して下さい")
	private String destinationAddress;
	/** 宛先TEL */
	@NotBlank(message = "電話番号を入力して下さい")
	@Pattern(regexp="^[0-9]{10,11}$", message="ハイフンを付けず、半角数字で入力してください (例:09011112222)")
	private String destinationTel;
	/** 配達日 */
	@NotBlank(message = "配達日時を入力してください")
	@Pattern(regexp = "^[0-9]{4}-[0-9]{2}-[0-9]{2}$", message = "yyyy-MM-ddの形で入力してください")
	private String deliveryDate;
	/** 配達時間 */
	private String deliveryTime;
	/** 支払方法 */
	private String paymentMethod;
	/** 合計金額 */
	private String totalPrice;
	/** 注文商品IDリスト */
	private List<Integer> orderItemId;
	/** トッピングIDリスト */
	private List<Integer> orderToppingId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getIntUserId() {
		return Integer.parseInt(userId);
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public String getDestinationName() {
		return destinationName;
	}

	public void setDestinationName(String destinationName) {
		this.destinationName = destinationName;
	}

	public String getDestinationEmail() {
		return destinationEmail;
	}

	public void setDestinationEmail(String destinationEmail) {
		this.destinationEmail = destinationEmail;
	}

	public String getDestinationZipcode() {
		return destinationZipcode;
	}

	public void setDestinationZipcode(String destinationZipcode) {
		this.destinationZipcode = destinationZipcode;
	}

	public String getDestinationAddress() {
		return destinationAddress;
	}

	public void setDestinationAddress(String destinationAddress) {
		this.destinationAddress = destinationAddress;
	}

	public String getDestinationTel() {
		return destinationTel;
	}

	public void setDestinationTel(String destinationTel) {
		this.destinationTel = destinationTel;
	}

	public String getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public String getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(String deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}

	public List<Integer> getOrderItemId() {
		return orderItemId;
	}

	public void setOrderItemId(List<Integer> orderItemId) {
		this.orderItemId = orderItemId;
	}

	public List<Integer> getOrderToppingId() {
		return orderToppingId;
	}

	public void setOrderToppingId(List<Integer> orderToppingId) {
		this.orderToppingId = orderToppingId;
	}

	@Override
	public String toString() {
		return "BuyOrderForm [userId=" + userId + ", orderDate=" + orderDate + ", destinationName=" + destinationName
				+ ", destinationEmail=" + destinationEmail + ", destinationZipcode=" + destinationZipcode
				+ ", destinationAddress=" + destinationAddress + ", destinationTel=" + destinationTel
				+ ", deliveryDate=" + deliveryDate + ", deliveryTime=" + deliveryTime + ", paymentMethod="
				+ paymentMethod + ", totalPrice=" + totalPrice + ", orderItemId=" + orderItemId + ", orderToppingId="
				+ orderToppingId + "]";
	}
}
