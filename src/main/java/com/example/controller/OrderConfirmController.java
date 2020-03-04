package com.example.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Order;
import com.example.domain.OrderItem;
import com.example.service.OrderConfirmService;

@Controller
@RequestMapping("")
public class OrderConfirmController {
	
	@Autowired
	private OrderConfirmService service;
	
	
	/**テスト用の表示メソッドです.
	 * @return 詳細リスト
	 */
	@RequestMapping("/shoppingCart")
	public String showShoppingCart() {
		return "cart_list";
	}
	
		
	/**
	 * 注文確認画面を表示します.
	 * @return　注文確認画面
	 */
	@RequestMapping("/toOrderConfirm")
	public String toOrderConfirm(Integer userId,Model model) {
		Order order=service.findByUserIdAndStatus(userId);
		if(userId==null) {
			return "login";
		}
		
		int tax=order.getTax();
		int totalPrice=order.getTotalPrice();
		
		model.addAttribute("tax",tax);
		model.addAttribute("totalPrice",totalPrice);
		
		model.addAttribute("order",order);
		
		return "order_confirm";
				
	}
	
	

}
