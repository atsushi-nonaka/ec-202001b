package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.Topping;
import com.example.repository.ToppingRepository;

/**
 * トッピング情報を操作するサービス.
 * 
 * @author tsuchiyahiromu
 *
 */
@Service
public class GetAllToppingsService {
	@Autowired
	private ToppingRepository toppingRepository;

	/**
	 * テーブルに存在する全てのデータを取得する.
	 * 
	 * @return トッピング情報をリストに入れて返す
	 */
	public List<Topping> getAllToppings() {
		List<Topping> toppingList = toppingRepository.findAllToppings();
		return toppingList;
	}
}
