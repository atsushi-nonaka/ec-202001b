package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Order;
import com.example.form.BuyOrderForm;
import com.example.service.BuyOrderService;
import com.example.service.OrderConfirmService;

@Controller
@RequestMapping("")
public class OrderConfirmController {
	
	@Autowired
	private OrderConfirmService service;
	
	@Autowired
	private BuyOrderService buyOrderService;
	
	@ModelAttribute
	public BuyOrderForm setUpForm() {
		return new BuyOrderForm();
	}
	
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
		int totalPrice=tax+order.getTotalPrice();
				
		model.addAttribute("tax",tax);
		model.addAttribute("totalPrice",totalPrice);
		
		model.addAttribute("order",order);
		
		return "order_confirm";
				
	}
	
	/**
	 * 注文情報を更新する.
	 * 
	 * @param form オーダーフォーム
	 * @return 完了画面
	 */
	@RequestMapping("/toComplete")
	public String orderFinish(@Validated BuyOrderForm form,
								   BindingResult result,
								   Model model,
								   Integer userId
								   ) {
		
		if(result.hasErrors()) {
			return toOrderConfirm(userId, model);
		}
		buyOrderService.orderFinish(form);
		return "order_finished";
	}

}
