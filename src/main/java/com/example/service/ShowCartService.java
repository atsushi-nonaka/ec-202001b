package com.example.service;

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
	
	public Order showCart(Integer userId) {
		
		return repository.findByUserIdAndStatus(userId);
				
	}
}
