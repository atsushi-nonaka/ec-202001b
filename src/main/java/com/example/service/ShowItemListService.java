package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.domain.Item;
import com.example.repository.ItemRepository;

/**
 * 商品情報を操作するサービス.
 * 
 * @author tsuchiyahiromu
 *
 */
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
}
