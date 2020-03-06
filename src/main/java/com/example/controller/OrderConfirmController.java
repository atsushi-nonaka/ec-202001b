package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.LoginUser;
import com.example.domain.Order;
import com.example.domain.User;
import com.example.form.BuyOrderForm;
import com.example.service.BuyOrderService;
import com.example.service.OrderConfirmService;

/**
 * 注文確認画面に関するコントローラーです.
 * 注文確認画面の表示、注文の操作をこのコントローラにまとめています。
 * @author yuri.okada
 *
 */
@Controller
@RequestMapping("")
public class OrderConfirmController {
	
	@Autowired
	private OrderConfirmService service;
	
	@Autowired
	private BuyOrderService buyOrderService;
	
//	@Autowired
//	private ShowCartService showCartService;

	@ModelAttribute
	public BuyOrderForm setUpForm() {
		return new BuyOrderForm();
	}	
		
	/**
	 * 注文確認画面を表示します.
	 * @return　注文確認画面
	 */
	@RequestMapping("/toOrderConfirm")
	public String toOrderConfirm(Model model,@AuthenticationPrincipal LoginUser loginUser) {
		User user = loginUser.getUser();
		Order order = service.findByUserIdAndStatus(user.getId());
//		if(userId==null) {
//			return "login";
//		}

//		int tax=order.getTax();
//		int totalPrice=tax+order.getTotalPrice();
//				
//		model.addAttribute("tax",tax);
//		model.addAttribute("totalPrice",totalPrice);
//		
//		model.addAttribute("order",order);
		model.addAttribute("user", user);
		
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
								   @AuthenticationPrincipal LoginUser loginUser
								   ) {
		
		if(result.hasErrors()) {
			return toOrderConfirm(model,loginUser);
					
		}
		
		buyOrderService.orderFinish(form);
		return "order_finished";
	}

}
