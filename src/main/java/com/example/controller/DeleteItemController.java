package com.example.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.LoginUser;
import com.example.domain.Order;
import com.example.domain.OrderItem;
import com.example.service.DeleteItemService;
import com.example.service.OrderConfirmService;


@Controller
@RequestMapping("")
public class DeleteItemController {
	
	@Autowired
	private DeleteItemService deleteItemService;
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	private OrderConfirmService service;
	
	@RequestMapping("/deleteItem")
	public String deleteItem(Integer orderItemId) {
		Order order = (Order) session.getAttribute("order");
		OrderItem orderItem = null;
		for(OrderItem oi:order.getOrderItemList()) {
			if(oi.getId().equals(orderItemId)) {
				orderItem = oi;

			}
		}
		Integer subtotal = orderItem.getSubTotal();
		order.setTotalPrice(order.getTotalPrice()-subtotal);
		deleteItemService.updateOrder(order);
		System.out.println("合計金額は"+order.getTotalPrice());
		session.setAttribute("hashedOrder", order);
//		OrderItem orderItem = deleteItemService.searchById(orderItemId);
		deleteItemService.deleteOrderToppingById(orderItem.getId());
		deleteItemService.deleteOrderItemById(orderItemId);
		
		if(order.getTotalPrice()==0) {
			deleteItemService.deleteOrderById(order.getUserId());
			session.setAttribute("hashedOrder", null);
		}
		
		return "redirect:/showCart";
	}

}
