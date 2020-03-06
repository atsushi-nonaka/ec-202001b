package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.domain.Order;
import com.example.form.AddItemToCartForm;
import com.example.service.AddItemToCartService;
import com.example.service.ShowCartService;

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
	
	@Autowired
	private ShowCartService showCartService;

	/**
	 * 商品詳細ページに遷移します.
	 * 
	 * @return 商品詳細ページ
	 */
	@RequestMapping("/toItemDetail")
	public String toItemDetail() {
		return "forward:/show_item_detail";
	}

	/**
	 * 商品をカートに追加します.
	 * 
	 * @param form   商品詳細ページからのパラメータを受け取るフォームクラス
	 * @param userId ユーザーID
	 * @return toCartメソッドにリダイレクトします
	 */
	@RequestMapping("/addCart")
	public String addItemToCart(AddItemToCartForm form, Integer userId,RedirectAttributes rediAttributes) {
		service.insertOrder(form, userId);
		rediAttributes.addFlashAttribute("userId",userId);
		System.out.println("userIdの表示"+userId);
		return "redirect:/AddItemToCartController/showCart";
	}
	
	
	/**
	 * ショッピングカートの中身を表示します.
	 * @param id sessionスコープに入ったorderのuserID情報
	 * @param model 消費税、合計金額、orderオブジェクトを格納.
	 * @return ショッピングカートリスト
	 */
	@RequestMapping("/showCart")
	public String showCart(Integer userId, Model model) {
		System.out.println("showCartメソッドの引数"+userId);
		
		Order order =showCartService.showCart(userId);
		
		int tax = order.getTax();
		
		int totalPrice = tax + order.getTotalPrice();

		model.addAttribute("tax", tax);
		model.addAttribute("totalPrice", totalPrice);
		model.addAttribute("order", order);

		return "cart_list";
	}
	
//	/**
//	 * cart一覧画面に遷移します.
//	 * 
//	 * @return cart一覧画面
//	 */
//	@RequestMapping("/toCartList")
//	public String toCartList() {
//		
//		System.out.println("ショッピングカートの表示");
//		return "forward:/showCart";
////		return "forward:/toOrderConfirm";
//	}

}
