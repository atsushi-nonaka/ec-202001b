package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.LoginUser;
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

	/**
	 * 商品をカートに追加します.
	 * 
	 * @param form   商品詳細ページからのパラメータを受け取るフォームクラス
	 * @param userId ユーザーID
	 * @return showCartメソッドにリダイレクトします
	 */
	@RequestMapping("/addCart")
	public String addItemToCart(AddItemToCartForm form,  @AuthenticationPrincipal LoginUser loginUser) {
		if(loginUser==null) {
			service.insertOrder(form, 0);
			System.out.println("userID=0");
		}else {
			service.insertOrder(form, loginUser.getUser().getId());
			System.out.println("userID=userID");
		}
		return "redirect:/showCart";
	}
	
	/**
	 * ショッピングカートの中身を表示します.
	 * @param id sessionスコープに入ったorderのuserID情報
	 * @param model 消費税、合計金額、orderオブジェクトを格納.
	 * @return ショッピングカートリスト
	 */
	@RequestMapping("/showCart")
	public String showCart() {
		
//		Integer userId, Model model
//		上記は引数です。
//		System.out.println("showCartメソッドの引数"+userId);
//		
//		Order order =showCartService.showCart(userId);
//		
//		int tax = order.getTax();
//		
//		int totalPrice = tax + order.getTotalPrice();
//
//		model.addAttribute("tax", tax);
//		model.addAttribute("totalPrice", totalPrice);
//		model.addAttribute("order", order);

		return "cart_list";
	}

}
