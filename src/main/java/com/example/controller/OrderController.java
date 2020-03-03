package com.example.controller;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Order;
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
		
		Order order = new Order();
		BeanUtils.copyProperties(form, order);
		String shippingDate = form.getDeliveryDate();
		int shippingHour = Integer.parseInt(form.getDeliveryTime());
		LocalDate localDate = LocalDate.parse(shippingDate);
		LocalTime localTime = LocalTime.of(shippingHour, 0, 0);
		LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);
		Timestamp timestamp = Timestamp.valueOf(localDateTime);
		order.setDeliveryTime(timestamp);
		
		if(form.getPaymentMethod().equals("credit")) {
			order.setPaymentMethod(1);	
			order.setStatus(2);
		}else {
			order.setPaymentMethod(2);
			order.setStatus(1);
		}
		orderService.orderFinish(order);
		return "order_finished";
	}
}
