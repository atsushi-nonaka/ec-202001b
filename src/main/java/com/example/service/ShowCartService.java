package com.example.service;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Order;
import com.example.repository.OrderRepository;

/**
 * ショッピングカートを表示するサービスクラスです.
 * @author yuri.okada
 *
 */
@Service
@Transactional
public class ShowCartService {
	
	@Autowired
	private OrderRepository repository;
	
	@Autowired
	private HttpSession session;
	
	/**
	 * ショッピングカートの中身の商品情報を検索する.
	 * @param userId	 ユーザーID
	 * @return　検索した注文情報
	 */
	public Order showCart(Integer userId) {
		
		userId=(Integer)session.getAttribute("userId");
		
		//未ログインの時sessionのIDを取得
		if (userId== null) {
			userId=session.getId().hashCode();
		}
		
		return repository.findByUserIdAndStatus(userId);
				
	}
}
