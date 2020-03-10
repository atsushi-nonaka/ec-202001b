package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Order;
import com.example.domain.OrderItem;
import com.example.repository.OrderItemRepository;
import com.example.repository.OrderRepository;
import com.example.repository.OrderToppingRepository;

/**
 * DB上のデータ削除の操作を担当するサービス.
 * @author yamasaki
 *
 */
@Service
@Transactional
public class DeleteItemService {
	
	@Autowired
	private OrderRepository orderRepository;
	
	
	@Autowired
	private OrderItemRepository orderItemRepository;
	
	
	@Autowired
	private OrderToppingRepository orderToppingRepository;
	
	/**
	 *注文商品IDの注文商品情報を取得します.
	 * @param orderItemId 注文商品ID
	 * @return 注文商品
	 */
	public OrderItem searchById(Integer orderItemId) {
		OrderItem orderItem = orderItemRepository.findById(orderItemId);
		return orderItem;
	}
	
	/**
	 * ユーザIDの注文情報を削除します.
	 * @param userId ユーザID
	 */
	public void deleteOrderByUserId(Integer userId) {
		orderRepository.deleteByUserId(userId);

	}
	
	/**
	 * 注文商品IDの注文商品情報を削除します.
	 * @param id 注文商品ID
	 */
	public void deleteOrderItemById(Integer id) {
		orderItemRepository.deleteById(id);
	}
	
	/**
	 * 注文トッピングIDの注文トッピング情報を削除します.
	 * @param orderItemId 注文トッピングID
	 */
	public void deleteOrderToppingById(Integer orderItemId) {
		orderToppingRepository.deleteByOrderId(orderItemId);;
	}
	
	/**
	 * 注文情報を更新します.
	 * @param order 注文情報
	 */
	public void updateOrder(Order order) {
		orderRepository.update(order);
	}
	
	

}
