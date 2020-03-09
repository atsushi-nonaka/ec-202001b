package com.example.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Item;
import com.example.domain.LoginUser;
import com.example.form.ItemForm;
import com.example.service.ShowItemListService;

/**
 * 商品情報を操作するコントローラー.
 * 
 * @author tsuchiyahiromu
 *
 */
@Controller
@RequestMapping("")
public class ShowItemListController {
	@Autowired
	private ShowItemListService showItemListService;

	/**
	 * 全件検索または条件検索結果を表示します.
	 * 
	 * @param model リクエストスコープ
	 * @param itemForm  リクエストパラメーター
	 * @return 商品一覧画面
	 */
	@RequestMapping("")
	public String showItemList(Model model, ItemForm itemForm, @AuthenticationPrincipal LoginUser loginUser) {
		List<Item> itemList = showItemListService.showItemList(itemForm.getName());
		if(itemForm.getName() == null) {
			itemList =showItemListService.showItemList("");
		}
		if (itemList.size() == 0) {
			itemList = showItemListService.showItemList("");
			model.addAttribute("message", "該当する商品がございません");
		}
		List<List<Item>> itemListList = putThreeItemsListInList(itemList);
		model.addAttribute("itemListList", itemListList);
		StringBuilder itemListForAutocomplete = showItemListService.getItemListForAutocomplete(itemList);
		model.addAttribute("itemListForAutocomplete", itemListForAutocomplete);
		return "item_list_pizza";
	}

	/**
	 * Itemオブジェクトを3つずつリストに入れそのリストオブジェクト群を大枠のリスト格納する.
	 * 
	 * @param itemList 検索して取得したItemオブジェクト群
	 * @return リストの中にアイテムオブジェクトを入れたリストを格納し返します
	 */
	private List<List<Item>> putThreeItemsListInList(List<Item> itemList) {
		List<List<Item>> itemListList = new ArrayList<>();
		List<Item> smallitemList = new ArrayList<>();
		for (int i = 1; i <= itemList.size(); i++) {
			smallitemList.add(itemList.get(i - 1));
			if (smallitemList.size() % 3 == 0 || itemList.size() == i) {
				itemListList.add(smallitemList);
				smallitemList = new ArrayList<>();
			}
		}
		return itemListList;
	}

	@RequestMapping("/cheap_items")
	private String showCheapItem(Model model, String priceM) {
		List<Item> itemList = showItemListService.showCheapItem(Integer.parseInt(priceM));
		List<List<Item>> itemListList = putThreeItemsListInList(itemList);
		model.addAttribute("itemListList", itemListList);
		return "item_list_pizza";
	}

	@RequestMapping("/expensive_items")
	private String showExpensiveItem(Model model, String priceM) {
		List<Item> itemList = showItemListService.showExpensiveItem(Integer.parseInt(priceM));
		List<List<Item>> itemListList = putThreeItemsListInList(itemList);
		model.addAttribute("itemListList", itemListList);
		return "item_list_pizza";
	}

}
