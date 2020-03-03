package com.example.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.Form.AddItemToCartForm;
import com.example.domain.Order;
import com.example.domain.OrderItem;
import com.example.domain.OrderTopping;
import com.example.repository.OrderItemRepository;
import com.example.repository.OrderRepository;
import com.example.repository.OrderToppingRepository;

@Service
@Transactional
public class AddItemToCartService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private OrderItemRepository orderItemRepository;

	@Autowired
	private OrderToppingRepository orderToppingRepository;

	public void insertOrder(AddItemToCartForm form, Integer id) {
		Order order = new Order();

		if (orderRepository.findByUserIdAndStatus(id) == null) {
			order.setUserId(id);
			order = orderRepository.insert(order);
			
		} else {
			order.setUserId(id);
			order = orderRepository.findByUserIdAndStatus(id);
		}

		OrderItem orderItem = new OrderItem();
		BeanUtils.copyProperties(form, orderItem);
		orderItem.setOrderId(order.getId());
		orderItem = orderItemRepository.insert(orderItem);

		for (Integer toppingId : form.getToppingIdList()) {
			OrderTopping orderTopping = new OrderTopping();
			orderTopping.setToppingId(toppingId);
			orderTopping.setOrderItemId(orderItem.getId());
			orderToppingRepository.insert(orderTopping);
		}

	}

}
