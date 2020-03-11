package com.example.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.OrderTopping;

/**
 * OrderToppingテーブルを操作するためのリポジトリ.
 * 
 * @author yamaseki
 *
 */
@Repository
public class OrderToppingRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	/**
	 * 注文トッピング情報を挿入するためのメソッドです.
	 * 
	 * @param orderTopping 注文トッピング情報
	 */
	public void insert(OrderTopping orderTopping) {
		String sql = "insert into order_toppings(topping_id,order_item_id)values(:toppingId,:orderItemId)";
		SqlParameterSource param = new MapSqlParameterSource().addValue("toppingId", orderTopping.getToppingId())
				.addValue("orderItemId", orderTopping.getOrderItemId());
		template.update(sql, param);
	}
	
	public void deleteByOrderId(Integer orderItemId) {
		String sql = "DELETE from order_toppings where order_item_id = :orderId";
		SqlParameterSource param = new MapSqlParameterSource().addValue("orderId", orderItemId);
		template.update(sql, param);
	}

}
