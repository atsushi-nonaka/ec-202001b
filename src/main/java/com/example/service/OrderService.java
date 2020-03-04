package com.example.service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Order;
import com.example.form.OrderForm;
import com.example.repository.OrderRepository;

/**
 * 注文情報を操作するサービス.
 * 
 * @author nonaka
 *
 */
@Service
@Transactional
public class OrderService {
	
	@Autowired
	private OrderRepository orderRepository;
	
	/**
	 * 注文情報の更新を行う.
	 * 
	 * @param order 注文情報
	 */
	public void orderFinish(OrderForm form) {
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

		orderRepository.update(order);
	}
}
