package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Order;
import com.example.domain.OrderItem;
import com.example.repository.OrderItemRepository;
import com.example.repository.OrderRepository;
import com.example.repository.OrderToppingRepository;

@Service
@Transactional
public class DeleteItemService {
	
	@Autowired
	private OrderRepository orderRepository;
	
	
	@Autowired
	private OrderItemRepository orderItemRepository;
	
	
	@Autowired
	private OrderToppingRepository orderToppingRepository;
	
	public OrderItem searchById(Integer orderItemId) {
		OrderItem orderItem = orderItemRepository.findById(orderItemId);
		return orderItem;
	}
	
	public void deleteOrderByUserId(Integer userId) {
		orderRepository.deleteByUserId(userId);

	}
	
	public void deleteOrderItemById(Integer id) {
		orderItemRepository.deleteById(id);
	}
	
	public void deleteOrderToppingById(Integer orderItemId) {
		orderToppingRepository.deleteByOrderId(orderItemId);;
	}
	
	public void updateOrder(Order order) {
		orderRepository.update(order);
	}
	
	

}
