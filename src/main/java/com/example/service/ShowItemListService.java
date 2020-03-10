package com.example.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.example.domain.Item;
import com.example.repository.ItemRepository;

/**
 * 商品情報を操作するサービス.
 * 
 * @author tsuchiyahiromu
 *
 */
@Service
public class ShowItemListService {
	@Autowired
	private ItemRepository itemRepository;

	/**
	 * 全件検索及び、曖昧検索をします.
	 * 
	 * @param name 商品名
	 * @return 商品一覧を返します
	 */
	public List<Item> showItemList(String name) {
		List<Item> itemList = itemRepository.findByLikeName(name);
		return itemList;
	}

	/**
	 * 商品安い順に並び変えます
	 * 
	 * @param priceM Mサイズ商品
	 * @return 商品一覧
	 */
	public List<Item> showCheapItem(Integer priceM) {
		List<Item> itemList = itemRepository.findByPriceOrederBy(priceM);
		return itemList;
	}

	/**
	 * 商品高い順に並び変えます
	 * 
	 * @param priceM Mサイズ商品
	 * @return 商品一覧
	 */
	public List<Item> showExpensiveItem(Integer priceM) {
		List<Item> itemList = itemRepository.findByPriceOrederByDeac(priceM);
		return itemList;
	}

	/**
	 * オートコンプリート用にJavaScriptの配列の中身を作ります.
	 * 
	 * @param itemList 商品一覧
	 * @return 配列の文字列
	 */
	public StringBuilder getItemListForAutocomplete(List<Item> itemList) {
		StringBuilder itemListForAutocomplete = new StringBuilder();
		for (int i = 0; i < itemList.size(); i++) {
			if (i != 0) {
				itemListForAutocomplete.append(",");
			}
			Item item = itemList.get(i);
			itemListForAutocomplete.append("\"");
			itemListForAutocomplete.append(item.getName());
			itemListForAutocomplete.append("\"");
		}
		return itemListForAutocomplete;
	}

	

}
