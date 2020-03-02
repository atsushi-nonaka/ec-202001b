package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.example.domain.Item;
import com.example.service.ShowItemListService;

/**
 * 商品情報を操作するコントローラー.
 * 
 * @author tsuchiyahiromu
 *
 */
@Controller
public class ShowItemListController {
	@Autowired
	private ShowItemListService showItemListService;

	/**
	 * 全件検索または条件検索結果を表示します.
	 * 
	 * @param model リクエストスコープ
	 * @param       name リクエストパラメーター
	 * @return 商品一覧画面
	 */
	public String showItemList(Model model, String name) {
		List<Item> itemList = showItemListService.showItemList(name);
		if (itemList.size() == 0) {
			itemList = showItemListService.showItemList("");
		}
		model.addAttribute("itemList", itemList);
		return "item_list_pizza";
	}
}
