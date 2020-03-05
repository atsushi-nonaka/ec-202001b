package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Order;
import com.example.repository.ShowCartRepository;

@Service
@Transactional
public class ShowCartService {
	
	@Autowired
	private ShowCartRepository repository;
	
	public Order searchByUserIdAndStatus(Integer id) {
		
		repository.findByUserIdAndStatus(id);
		
		return null;
	}
}
