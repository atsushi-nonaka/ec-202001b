package com.example.controller;



import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Order;
import com.example.form.AddItemToCartForm;
import com.example.service.AddItemToCartService;
import com.example.service.ShowCartService;

/**
 * ショッピングカートに商品を追加する処理を行うコントローラー.
 * 商品追加、カートの中身の表示も担当。
 * 
 * @author yamaseki
 *
 */
@Controller
@RequestMapping("")
public class AddItemToCartController {

	@Autowired
	private AddItemToCartService service;
	
	@Autowired
	private ShowCartService showCartService;
	
	@Autowired
	private HttpSession session;

	/**
	 * 商品をカートに追加します.
	 * 
	 * @param form   商品詳細ページからのパラメータを受け取るフォームクラス
	 * @return showCartメソッドにリダイレクトします
	 */
	@RequestMapping("/addCart")
	public String addItemToCart(AddItemToCartForm form) {

		service.insertOrder(form);	
		
		return "redirect:/showCart";
	}	
	
	/**								
	 * ショッピングカートのリンクから中身を表示させます.								
	 * @param userId								
	 * @return ショッピングカート								
	 */								
	@RequestMapping("/showCart")								
	public String showCart(Model model) {								
									
		Integer userId=(Integer)session.getAttribute("userId");							
									
		//未ログインの時sessionのIDを取得							
		if (userId== null) {							
			userId=session.getId().hashCode();						
		}							
		Order order =showCartService.showCart(userId);							
									
		//データベース上で注文がない場合							
		if(order==null) {							
			return "cart_list";						
		}							
									
		int tax = order.getTax();							
									
		int totalPrice = tax + order.CalcTotalPrice();							
									
		model.addAttribute("tax", tax);							
		model.addAttribute("totalPrice", totalPrice);							
		model.addAttribute("order", order);							
		session.setAttribute("order", order);							
									
									
//		List<OrderItem>list = order.getOrderItemList();							
//		for(OrderItem oi:list) {							
//			System.out.println(oi.getId());						
//		}							
									
		return "cart_list";							
	}								

	
	

}
