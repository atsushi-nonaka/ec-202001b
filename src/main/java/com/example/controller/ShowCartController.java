package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Order;
import com.example.service.ShowCartService;

@Controller
@RequestMapping("")
public class ShowCartController {

	@Autowired
	private ShowCartService service;

	
	/**
	 * テスト用の表示メソッドです.
	 * 
	 * @return 商品一覧
	 */
	@RequestMapping("/showList")
	public String showList() {
		return "item_list_pizza";
	}
	
	/**
	 * ショッピングカートの中身を表示します.
	 * @return ショッピングカートリスト
	 */
	@RequestMapping("/showCart")
	public String showCart(Integer userId, Model model) {

//		System.out.println("idの表示"+userId);
//		Order order = service.showCart(userId);
//		
//		System.out.println("orderの中身"+order);
//		
//		System.out.println(order.getTax());
//		int tax = order.getTax();
//		
//		int totalPrice = tax + order.getTotalPrice();
//
//		model.addAttribute("tax", tax);
//		model.addAttribute("totalPrice", totalPrice);
//		model.addAttribute("order", order);

		return "cart_list";
	}
	
	
//	@RequestMapping("/")
//	public String showCart(Integer userId, Model model) {
//
//		Order order = service.showCart(userId);
//
//		int tax = order.getTax();
//		int totalPrice = tax + order.getTotalPrice();
//
//		model.addAttribute("tax", tax);
//		model.addAttribute("totalPrice", totalPrice);
//		model.addAttribute("order", order);
//
//		return "cart_list";
//	}

}
