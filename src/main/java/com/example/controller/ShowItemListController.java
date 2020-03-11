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
	 * @param model    リクエストスコープ
	 * @param itemForm リクエストパラメーター
	 * @return 商品一覧画面
	 */
	@RequestMapping("")
	public String showItemList(Model model, ItemForm itemForm,Integer page) {
		// 全件検索で取得したItemオブジェクト群が入ったリスト（要素数１８）
		List<Item> itemList = showItemListService.findAll();
		// itemオブジェクトを三つずつリストに格納しそれをリストに入れる（要素数６）
		List<List<Item>> itemListList = putThreeItemsListInList(itemList);
		//
		List<List<Item>> bigThreeList = new ArrayList<>();
		//上記itemListListを二つずつ入れたリストをさらにリストに格納する（要素数３）
		List<List<List<Item>>> superItemList = new ArrayList<>();
		for (int i = 0; i < itemListList.size(); i++) {
			bigThreeList.add(itemListList.get(i));
			if (bigThreeList.size() == 2) {
				superItemList.add(bigThreeList);
				bigThreeList = new ArrayList<>();
			}
		}
		if (bigThreeList.size() != 0) {
			superItemList.add(bigThreeList);
		}
		StringBuilder itemListForAutocomplete = showItemListService.getItemListForAutocomplete(itemList);
		model.addAttribute("itemListForAutocomplete", itemListForAutocomplete);
		
		Integer index = 0;
		if(page != null) {
			index = page -1;
		}
		model.addAttribute("superItemList", superItemList);
		//従来のItemオブジェクトを３つずつ格納したリスト
		//model.addAttribute("itemListList", itemListList);
		model.addAttribute("bigThreeList", superItemList.get(index));
		model.addAttribute("pattern1", 1);
		return "item_list_pizza";
	}

	@RequestMapping("/search")
	public String search(Model model, ItemForm itemForm,Integer page) {
		List<Item> itemList = new ArrayList<>();
		if (itemForm.getHighLow().equals("1")) {
			itemList = showItemListService.showItemListByName(itemForm.getName());
		} else if (itemForm.getHighLow().equals("2")) {
			itemList = showItemListService.showItemListByNameDesc(itemForm.getName());
		}
		if (itemList.size() == 0) {
			itemList = showItemListService.showItemListByName("");
			model.addAttribute("message", "該当する商品がございません");
		}
		//条件検索したItemオブジェクトが3つずつ格納されたリスト
		List<List<Item>> itemListList = putThreeItemsListInList(itemList);
		//
		List<List<Item>> bigThreeList = new ArrayList<>();
		//
		List<List<List<Item>>> superItemList = new ArrayList<>();
		for (int i = 0; i < itemListList.size(); i++) {
			bigThreeList.add(itemListList.get(i));
			if (bigThreeList.size() == 2) {
				superItemList.add(bigThreeList);
				bigThreeList = new ArrayList<>();
			}
		}
		if (bigThreeList.size() != 0) {
			superItemList.add(bigThreeList);
		}
		StringBuilder itemListForAutocomplete = showItemListService.getItemListForAutocomplete(itemList);
		model.addAttribute("itemListForAutocomplete", itemListForAutocomplete);
		Integer index = 0;
		if(page != null) {
			index = page -1;
		}
		model.addAttribute("superItemList", superItemList);
		model.addAttribute("bigThreeList", superItemList.get(index));
		//model.addAttribute("itemListList", itemListList);
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

//	@RequestMapping("/cheap_items")
//	private String showCheapItem(Model model, String priceM) {
//		List<Item> itemList = showItemListService.showCheapItem(Integer.parseInt(priceM));
//		List<List<Item>> itemListList = putThreeItemsListInList(itemList);
//		model.addAttribute("itemListList", itemListList);
//		return "item_list_pizza";
//	}
//
//	@RequestMapping("/expensive_items")
//	private String showExpensiveItem(Model model, String priceM) {
//		List<Item> itemList = showItemListService.showExpensiveItem(Integer.parseInt(priceM));
//		List<List<Item>> itemListList = putThreeItemsListInList(itemList);
//		model.addAttribute("itemListList", itemListList);
//		return "item_list_pizza";
//	}

}
