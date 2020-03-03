package com.example.Form;

import java.util.List;

import com.example.domain.OrderTopping;

public class AddItemToCartForm {
	private Integer itemId;
	private Integer quantity;
	private Character size;
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
