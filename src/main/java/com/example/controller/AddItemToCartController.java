package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.Form.AddItemToCartForm;
import com.example.service.AddItemToCartService;

/**
 * ショッピングカートに商品を追加する処理を行うコントローラー.
 * 
 * @author yamaseki
 *
 */
@Controller
@RequestMapping("/AddItemToCartController")
public class AddItemToCartController {

	@Autowired
	private AddItemToCartService service;

	/**
	 * 商品詳細ページに遷移します.
	 * 
	 * @return 商品詳細ページ
	 */
	@RequestMapping("/toItemDetail")
	public String toItemDetail() {
		return "item_detail";
	}

	/**
	 * 商品をカートに追加します.
	 * 
	 * @param form   商品詳細ページからのパラメータを受け取るフォームクラス
	 * @param userId ユーザーID
	 * @return toCartメソッドにリダイレクトします
	 */
	@RequestMapping("/addCart")
	public String addItemToCart(AddItemToCartForm form, Integer userId) {
		service.insertOrder(form, userId);
		return "redirect:/AddItemToCartController/toCartList";
	}

	/**
	 * cart一覧画面に遷移します.
	 * 
	 * @return cart一覧画面
	 */
	@RequestMapping("/toCartList")
	public String toCartList() {
		return "cart_list";
	}

}
