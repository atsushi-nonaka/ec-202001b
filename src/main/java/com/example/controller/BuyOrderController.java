package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.form.BuyOrderForm;
import com.example.service.BuyOrderService;

/**
 * 注文情報を操作するコントローラー.
 * 
 * @author nonaka
 *
 */
@Controller
@RequestMapping("")
public class BuyOrderController {
	
//	@Autowired
//	private BuyOrderService orderService;
//	
//	
//	@ModelAttribute
//	public BuyOrderForm setUpForm() {
//		return new BuyOrderForm();
//	}
//	
//	/**
//	 * オーダー確認画面に遷移する.
//	 * 
//	 * @return オーダー確認画面
//	 */
//	@RequestMapping("/toConfirm")
//	public String toConfirm() {
//		return "order_confirm";
//	}
//	
//	/**
//	 * 注文情報を更新する.
//	 * 
//	 * @param form オーダーフォーム
//	 * @return 完了画面
//	 */
//	@RequestMapping("/toComplete")
//	public String orderFinish(@Validated BuyOrderForm form,
//								   BindingResult result,
//								   Integer userId,Model model
//								   ) {
//		
//		if(result.hasErrors()) {
//			return "forward:/toOrderConfirm";
//		}
//		orderService.orderFinish(form);
//		return "order_finished";
//	}
}
