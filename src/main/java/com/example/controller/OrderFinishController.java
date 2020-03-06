package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 注文完了画面で使用されるコントローラー.
 * 
 * @author nonaka
 *
 */
@Controller
@RequestMapping("")
public class OrderFinishController {
	
	/**
	 * Top画面に遷移する.
	 * 
	 * @return オーダー確認画面
	 */
	@RequestMapping("/toItemList")
	public String toItemList() {
		return "forward:/show_item_list";
	}
}
