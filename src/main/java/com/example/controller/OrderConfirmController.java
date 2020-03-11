package com.example.controller;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.CreditCard;
import com.example.domain.LoginUser;
import com.example.domain.Order;
import com.example.domain.User;
import com.example.form.BuyOrderForm;
import com.example.form.CreditCardForm;
import com.example.service.BuyOrderService;
import com.example.service.CreditCardService;
import com.example.service.OrderConfirmService;

/**
 * 注文確認画面に関するコントローラーです. 注文確認画面の表示、注文の操作、注文履歴をこのコントローラにまとめています。
 * 
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

	@Autowired
	private HttpSession session;

	@Autowired
	private CreditCardService creditCardService;

	@ModelAttribute
	public BuyOrderForm setUpForm() {
		return new BuyOrderForm();
	}

	/**
	 * 注文確認画面を表示します.
	 * 
	 * @return 注文確認画面
	 */
	@RequestMapping("/toOrderConfirm")
	public String toOrderConfirm(Model model, @AuthenticationPrincipal LoginUser loginUser) {
		User user = loginUser.getUser();

		Order order = service.findByUserIdAndStatus(user.getId());

		int tax = order.getTax();
		int totalPrice = tax + order.CalcTotalPrice();

		model.addAttribute("tax", tax);
		model.addAttribute("totalPrice", totalPrice);

		model.addAttribute("order", order);
		model.addAttribute("user", user);
		session.setAttribute("order", order);

		return "order_confirm";

	}

	/**
	 * 注文情報を更新する.
	 * 
	 * @param form オーダーフォーム
	 * @return リダイレクト
	 */
	@RequestMapping("/toComplete")
	public String orderFinish(@Validated BuyOrderForm form, BindingResult result, Model model,
			@AuthenticationPrincipal LoginUser loginUser, CreditCardForm creditCardForm) {

		if (form.getDeliveryDate().matches("[0-9]{4}-[0-9]{2}-[0-9]{2}")
				&& LocalDateTime.now().isAfter(buyOrderService.toLocalDateTime(form))) {
			result.rejectValue("deliveryDate", null, "配達時間が過去に設定されています");
		}

		CreditCard creditCard = null;
		System.out.println(creditCardForm);
		if ("credit".equals(form.getPaymentMethod())) {
			creditCard = creditCardService.getCreditCard(creditCardForm);
			if ("error".equals(creditCard.getStatus())) {
				model.addAttribute("error", "入力されたカード情報は不正です。");
				System.out.println(creditCard);
				return toOrderConfirm(model, loginUser);
			}
		}

		buyOrderService.orderFinish(form);

		return "redirect:/finish";
	}

	/**
	 * 完了画面へ遷移する.
	 * 
	 * @return 完了画面
	 */
	@RequestMapping("/finish")
	public String finish() {

		return "order_finished";
	}

	/**
	 * 注文履歴の情報を表示します
	 * 
	 * @param loginUser ログイン者のユーザ情報
	 * @return 注文確認画面
	 */
	@RequestMapping("/showOrderHistory")
	public String showOrderHistory(Model model, @AuthenticationPrincipal LoginUser loginUser) {
		User user = loginUser.getUser();

		Integer userId = user.getId();

		List<Order> orderHistoryList = buyOrderService.findOrderHistory(userId);
		if (orderHistoryList == null) {
			return "order_history";
		}

		model.addAttribute("orderHistoryList", orderHistoryList);

		return "order_history";

	}

}
