package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Item;
import com.example.service.ShowItemListService;

/**
 * 商品情報を操作するコントローラー.
 * 
 * @author tsuchiyahiromu
 *
 */
@Controller
@RequestMapping("/show_item_list")
public class ShowItemListController {
	@Autowired
	private ShowItemListService showItemListService;

	/**
	 * 全件検索または条件検索結果を表示します.
	 * 
	 * @param model リクエストスコープ
	 * @param name  リクエストパラメーター
	 * @return 商品一覧画面
	 */
	@RequestMapping("")
	public String showItemList(Model model, String code) {
		List<Item> itemList = showItemListService.showItemList(code);
		if (itemList.size() == 0) {
			itemList = showItemListService.showItemList("");
		}
		model.addAttribute("itemList", itemList);

		System.out.println(itemList.size());

		return "item_list_pizza";
	}
}
