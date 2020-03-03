package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.service.OrderConfirmService;

@Controller
@RequestMapping("/order")
public class OrderConfirmController {
	
	@Autowired
	private OrderConfirmService service;
	
	

}
