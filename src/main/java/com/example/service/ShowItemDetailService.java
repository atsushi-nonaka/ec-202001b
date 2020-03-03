package com.example.service;

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
public class ShowItemDetailService {

	@Autowired
	private ItemRepository itemRepository;

	/**
	 * 受け取ったidから特定の商品情報を検索します.
	 * 
	 * @param id リクエストパラメータ(商品id)
	 * @return 商品情報を返します
	 */
	public Item showDetail(int id) {
		Item item = itemRepository.load(id);
		return item;
	}
}
