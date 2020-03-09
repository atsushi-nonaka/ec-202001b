package com.example.form;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Range;

import com.example.domain.OrderItem;


/**
 * オーダーフォーム.
 * 
 * @author nonaka
 *
 */
public class BuyOrderForm {
	/** ID */
	private String userId;
	/** 宛先氏名 */
	@NotBlank(message = "お名前を入力して下さい")
	private String destinationName;
	/** 宛先Eメール */
	@NotBlank(message = "メールアドレスを入力して下さい")
	private String destinationEmail;
	/** 宛先郵便番号 */
	@NotBlank(message = "郵便番号を入力してください")
	@Pattern(regexp = "^[0-9]{7}$", message = "7桁で入力してください")
	private String destinationZipcode;
	/** 宛先住所 */
	@NotBlank(message = "住所を入力して下さい")
	private String destinationAddress;
	/** 宛先TEL */
	@NotBlank(message = "電話番号を入力して下さい")
	@Range(message = "数字を入力してください")
	private String destinationTel;
	/** 配達日 */
	@NotBlank(message = "配達日時を入力して下さい")
	private String deliveryDate;
	/** 配達時間 */
	private String deliveryTime;
	/** 支払方法 */
	private String paymentMethod;
	/** 注文商品リスト */
	private List<OrderItem> orderItemList;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getIntUserId() {
		return Integer.parseInt(userId);
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

	public List<OrderItem> getOrderItemList() {
		return orderItemList;
	}

	public void setOrderItemList(List<OrderItem> orderItemList) {
		this.orderItemList = orderItemList;
	}

	@Override
	public String toString() {
		return "BuyOrderForm [destinationName=" + destinationName + ", destinationEmail=" + destinationEmail
				+ ", destinationZipcode=" + destinationZipcode + ", destinationAddress=" + destinationAddress
				+ ", destinationTel=" + destinationTel + ", deliveryDate=" + deliveryDate + ", deliveryTime="
				+ deliveryTime + ", paymentMethod=" + paymentMethod + ", orderItemList=" + orderItemList + "]";
	}

}
