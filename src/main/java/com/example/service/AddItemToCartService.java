package com.example.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.form.AddItemToCartForm;
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

	@Autowired
	private HttpSession session;
	
	/**
	 * 注文情報をリポジトリに渡しデータベースに挿入するためのメソッドです.
	 * 
	 * @param form   商品一覧画面からのパラメーターを受け取るフォーム
	 * @param userId ユーザーID
	 */
	public void insertOrder(AddItemToCartForm form, Integer userId) {
		Order order=new Order();
		userId=(Integer)session.getAttribute("userId");
		System.out.println("insertorder内の"+userId);
		//データベース上にないuserIdだったらその値をセットしてデータベースにインサート
		if (userId== null) {
			order.setUserId(0);
			//insertでorder.setIdが自動採番される
			order = orderRepository.insert(order);

		} else if(orderRepository.checkByUserIdAndStatus(userId) == null){
			//データベース上にあれば注文情報を取得
			order.setUserId(userId);
			order = orderRepository.insert(order);
		} else if(orderRepository.checkByUserIdAndStatus(userId) != null){
			//データベース上にあれば注文情報を取得
			order = orderRepository.checkByUserIdAndStatus(userId);
		}

		System.out.println("insertOrderのメソッド内"+order);
		
		OrderItem orderItem = new OrderItem();
		BeanUtils.copyProperties(form, orderItem);
		orderItem.setOrderId(order.getId());
		orderItem = orderItemRepository.insert(orderItem);
		orderItem.setItem(itemRepository.load(orderItem.getItemId()));

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
//		int subTotal = orderItem.getSubTotal();
//
//		// orderドメインのtotalPriceにsubTotalを追加して再度update
//		int fomerTotalPrice = 0;
//		if (order.getTotalPrice() != null) {
//			fomerTotalPrice = order.getTotalPrice();
//		}
//		int newTotalPrice = fomerTotalPrice + subTotal;
//		order.setTotalPrice(newTotalPrice);
		order.setTotalPrice(0);
		
		orderRepository.update2(order);
		System.out.println("update後の"+order);

	}
	
	
	
//	/**
//	 * 注文情報をリポジトリに渡しデータベースに挿入するためのメソッドです.
//	 * 
//	 * @param form   商品一覧画面からのパラメーターを受け取るフォーム
//	 * @param userId ユーザーID
//	 */
//	public void insertOrder(AddItemToCartForm form, Integer userId) {
//		Order order = new Order();
//
//		//データベース上にないuserIdだったらその値をセットしてデータベースにインサート
//		if (orderRepository.checkByUserIdAndStatus(userId) == null) {
//			order.setUserId(userId);
//			//insertでorder.setIdが自動採番される
//			order = orderRepository.insert(order);
//
//		} else {
//			//データベース上にあれば注文情報を取得
//			order.setUserId(userId);
//			order = orderRepository.checkByUserIdAndStatus(userId);
//		}
//
//		OrderItem orderItem = new OrderItem();
//		BeanUtils.copyProperties(form, orderItem);
//		orderItem.setOrderId(order.getId());
//		orderItem = orderItemRepository.insert(orderItem);
//		orderItem.setItem(itemRepository.load(orderItem.getItemId()));
//
//		if (form.getToppingIdList() != null) {
//			List<OrderTopping> orderToppingList = new ArrayList<>();
//			for (Integer toppingId : form.getToppingIdList()) {
//				OrderTopping orderTopping = new OrderTopping();
//				orderTopping.setToppingId(toppingId);
//				orderTopping.setOrderItemId(orderItem.getId());
//				orderTopping.setTopping(toppingRepository.findByToppingId(toppingId));
//				orderToppingRepository.insert(orderTopping);
//				orderToppingList.add(orderTopping);
//			}
//			orderItem.setOrderToppingList(orderToppingList);
//
//		}
////		int subTotal = orderItem.getSubTotal();
////
////		// orderドメインのtotalPriceにsubTotalを追加して再度update
////		int fomerTotalPrice = 0;
////		if (order.getTotalPrice() != null) {
////			fomerTotalPrice = order.getTotalPrice();
////		}
////		int newTotalPrice = fomerTotalPrice + subTotal;
////		order.setTotalPrice(newTotalPrice);
//		order.setTotalPrice(0);
//		
//		orderRepository.update2(order);
//
//	}

}