package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Order;
import com.example.repository.OrderRepository;

/**
 * 注文情報を操作するサービス.
 * 
 * @author nonaa
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
	public void orderFinish(Order order) {
		orderRepository.update(order);
	}
}
