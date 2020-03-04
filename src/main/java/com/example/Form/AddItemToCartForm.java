package com.example.Form;

import java.util.List;

/**
 * 商品詳細画面からカートに追加するためのパラメーターを受け取るフォーム.
 * 
 * @author yamaseki
 *
 */
public class AddItemToCartForm {
	/** ピザの種類毎のID */
	private Integer itemId;
	/** ピザの枚数 */
	private Integer quantity;
	/** ピザの種類毎のID */
	private Character size;
	/** トッピングIDを格納するリスト */
	private List<Integer> toppingIdList;

	@Override
	public String toString() {
		return "AddItemToCartForm [itemId=" + itemId + ", quantity=" + quantity + ", size=" + size + ", toppingIdList="
				+ toppingIdList + "]";
	}

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
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

	public List<Integer> getToppingIdList() {
		return toppingIdList;
	}

	public void setToppingIdList(List<Integer> toppingIdList) {
		this.toppingIdList = toppingIdList;
	}

}
