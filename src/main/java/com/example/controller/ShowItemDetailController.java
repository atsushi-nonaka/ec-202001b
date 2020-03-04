package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Item;
import com.example.service.ShowItemDetailService;

/**
 * 商品情報を操作するコントローラー.
 * 
 * @author tsuchiyahiromu
 *
 */
@Controller
@RequestMapping("/show_item_detail")
public class ShowItemDetailController {
	@Autowired
	private ShowItemDetailService showItemDetailService;

	/**
	 * 商品の詳細情報を表示します.
	 * 
	 * @param       id リクエストパラメータ(商品id)
	 * @param model リクエストスコープ
	 * @return
	 */
	@RequestMapping("")
	public String showDetail(Model model,String id) {
		System.out.println(id);
		Item item = showItemDetailService.showDetail(Integer.parseInt(id));
		model.addAttribute("item", item);
		return "item_detail";
	}
}
