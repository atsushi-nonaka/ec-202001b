package com.example.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.Form.AddItemToCartForm;
import com.example.domain.Order;
import com.example.domain.OrderItem;
import com.example.domain.OrderTopping;
import com.example.repository.ItemRepository;
import com.example.repository.OrderItemRepository;
import com.example.repository.OrderRepository;
import com.example.repository.OrderToppingRepository;
import com.example.repository.ToppingRepository;

/**
 * カートに商品を追加するためのサービスクラス.
 * 
 * @author yamaseki
 *
 */
@Service
@Transactional
public class AddItemToCartService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private OrderItemRepository orderItemRepository;

	@Autowired
	private OrderToppingRepository orderToppingRepository;

	@Autowired
	private ItemRepository itemRepository;

	@Autowired
	private ToppingRepository toppingRepository;

	/**
	 * 注文情報をリポジトリに渡しデータベースに挿入するためのメソッドです.
	 * 
	 * @param form   商品一覧画面からのパラメーターを受け取るフォーム
	 * @param userId ユーザーID
	 */
	public void insertOrder(AddItemToCartForm form, Integer userId) {
		Order order = new Order();

		if (orderRepository.checkByUserIdAndStatus(userId) == null) {
			order.setUserId(userId);
			order = orderRepository.insert(order);

		} else {
			order.setUserId(userId);
			order = orderRepository.checkByUserIdAndStatus(userId);
		}

		OrderItem orderItem = new OrderItem();
		BeanUtils.copyProperties(form, orderItem);
		orderItem.setOrderId(order.getId());
		orderItem = orderItemRepository.insert(orderItem);
		orderItem.setItem(itemRepository.findByItemId(orderItem.getItemId()));

		if (form.getToppingIdList() != null) {
			List<OrderTopping> orderToppingList = new ArrayList<>();
			for (Integer toppingId : form.getToppingIdList()) {
				OrderTopping orderTopping = new OrderTopping();
				orderTopping.setToppingId(toppingId);
				orderTopping.setOrderItemId(orderItem.getId());
				orderTopping.setTopping(toppingRepository.findByToppingId(toppingId));
				orderToppingRepository.insert(orderTopping);
				orderToppingList.add(orderTopping);
			}
			orderItem.setOrderToppingList(orderToppingList);

		}
		int subTotal = orderItem.getSubTotal();

		// orderドメインのtotalPriceにsubTotalを追加して再度update
		int fomerTotalPrice = 0;
		if (order.getTotalPrice() != null) {
			fomerTotalPrice = order.getTotalPrice();
		}
		int newTotalPrice = fomerTotalPrice + subTotal;
		order.setTotalPrice(newTotalPrice);
		orderRepository.update2(order);

	}

}
