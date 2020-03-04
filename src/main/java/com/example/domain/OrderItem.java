package com.example.domain;

import java.util.List;

/**
 * 注文商品情報を表すドメインクラス.
 * 
 * @author nonaka
 *
 */
public class OrderItem {
	/** ID */
	private Integer id;
	/** 商品ID */
	private Integer itemId;
	/** 注文ID */
	private Integer orderId;
	/** 数量 */
	private Integer quantity;
	/** サイズ */
	private Character size;
	/** 商品 */
	private Item item;
	/** トッピングリスト */
	private List<OrderTopping> orderToppingList;
	/** 小計 */
	private Integer subTotal;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Character getSize() {
		return size;
	}

	public void setSize(Character size) {
		this.size = size;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public List<OrderTopping> getOrderToppingList() {
		return orderToppingList;
	}

	public void setOrderToppingList(List<OrderTopping> orderToppingList) {
		this.orderToppingList = orderToppingList;
	}

//	public Integer getSubTotal() {
//		return subTotal;
//	}
	public void setSubTotal(Integer subTotal) {
		this.subTotal = subTotal;
	}

	@Override
	public String toString() {
		return "OrderItem [id=" + id + ", itemId=" + itemId + ", orderId=" + orderId + ", quantity=" + quantity
				+ ", size=" + size + ", item=" + item + ", orderToppingList=" + orderToppingList + ", subTotal="
				+ subTotal + "]";
	}

	/**
	 * 注文商品の小計を計算します.(税抜)
	 * 
	 * @return 注文商品小計
	 */
	public int getSubTotal() {
		int itemPrice = 0;
		int toppingPrice = 0;
		int subTotal = 0;
		List<OrderTopping> toppingList = getOrderToppingList();

		if (getSize() == 'M') {
			itemPrice = item.getPriceM();
		} else if (getSize() == 'L') {
			itemPrice = item.getPriceL();
		}

		// サイズ毎の価格を取得
		for (OrderTopping orderTopping : toppingList) {
			if (getSize() == 'M') {
				toppingPrice = orderTopping.getTopping().getPriceM();
			} else if (getSize() == 'L') {
				toppingPrice = orderTopping.getTopping().getPriceL();

				subTotal = (itemPrice + toppingPrice * toppingList.size()) * getQuantity();
			} else {
				//toppingの選択が一つもなかった時の処理
				subTotal = (itemPrice + toppingPrice * getQuantity());
			}
		}
//		System.out.println("商品の値段"+itemPrice);
//		System.out.println("商品の数"+getQuantity());
//		System.out.println("トッピングの値段"+toppingPrice);
//		System.out.println("トッピングの数"+toppingList.size());
//		System.out.println("小計の値段"+subTotal);
		return subTotal;
	}
}
