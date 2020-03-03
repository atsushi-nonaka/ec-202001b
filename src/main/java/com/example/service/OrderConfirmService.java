package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Order;
import com.example.repository.OrderRepository;

/**
 * 注文確認画面を表示するサービスクラスです.
 * @author yuri.okada
 *
 */
@Service
@Transactional
public class OrderConfirmService {

	@Autowired
	private OrderRepository repository;
	
	/**
	 * ユーザIDと状態から注文情報を取得します.
	 * 注文情報に含まれている、注文商品リスト、注文トッピングリストも取得します.
	 * @param userId　ユーザID
	 * @return　注文リスト
	 */
	public List<Order> findByUserIdAndStatus(Integer userId){
		return repository.findByUserIdAndStatus(userId);
	}
	
}
