package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ShowCartController")
public class ShowCartController {
	
	@RequestMapping("/")
	public String showCart() {
		
		
		
		return "cart_list";
	}

}
