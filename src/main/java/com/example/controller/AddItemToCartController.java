package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.Form.AddItemToCartForm;
import com.example.service.AddItemToCartService;

@Controller
@RequestMapping("/AddItemToCartController")
public class AddItemToCartController {
	
	@Autowired
	private AddItemToCartService service;
	
	@RequestMapping("/toItemDetail")
	public String toItemDetail() {
		return "item_detail";
	}
	
	@RequestMapping("/addCart")
	public String addItemToCart(AddItemToCartForm form,Integer id) {
		service.insertOrder(form,id);
		return "redirect:/AddItemToCartController/toCartList";
	}
	
	@RequestMapping("/toCartList")
	public String toCartList() {
		return "cart_list";
	}

}
