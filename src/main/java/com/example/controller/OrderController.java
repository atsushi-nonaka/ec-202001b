package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.form.OrderForm;
import com.example.service.OrderService;

/**
 * 注文情報を操作するコントローラー.
 * 
 * @author nonaka
 *
 */
@Controller
@RequestMapping("")
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	@RequestMapping("")
	public String index() {
		return "order_confirm";
	}
	
	@ModelAttribute
	public OrderForm setUpForm() {
		return new OrderForm();
	}
	
	/**
	 * 注文情報を更新する
	 * 
	 * @param form オーダーフォーム
	 * @return 完了画面
	 */
	@RequestMapping("to_complete")
	public String orderFinish(@Validated OrderForm form,
								   BindingResult result
								   ) {
		
		if(result.hasErrors()) {
			return index();
		}
		orderService.orderFinish(form);
		return "order_finished";
	}
}
