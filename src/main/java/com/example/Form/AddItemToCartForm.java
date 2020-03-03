package com.example.Form;

import java.util.List;

import com.example.domain.OrderTopping;

public class AddItemToCartForm {
	private Integer itemId;
	private Integer quantity;
	private Character size;
	private List<OrderTopping> orderToppingList;
	
	@Override
	public String toString() {
		return "AddItemToCartForm [itemId=" + itemId + ", quantity=" + quantity + ", size=" + size
				+ ", orderToppingList=" + orderToppingList + "]";
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
	public List<OrderTopping> getOrderToppingList() {
		return orderToppingList;
	}
	public void setOrderToppingList(List<OrderTopping> orderToppingList) {
		this.orderToppingList = orderToppingList;
	}
	
	
	
	
	

}
