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
	 * @param id sessionスコープに入ったorderのuserID情報
	 * @param model 消費税、合計金額、orderオブジェクトを格納.
	 * @return ショッピングカートリスト
	 */
	@RequestMapping("/showCart")
	public String showCart() {
//		Integer userId, Model model
//		上記は引数
//		Order order = service.showCart(userId);
//		
//		int tax = order.getTax();
//		
//		int totalPrice = tax + order.getTotalPrice();
//
//		model.addAttribute("tax", tax);
//		model.addAttribute("totalPrice", totalPrice);
//		model.addAttribute("order", order);

		return "cart_list";
	}

}
