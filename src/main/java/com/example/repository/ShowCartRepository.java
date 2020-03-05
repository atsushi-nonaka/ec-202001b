package com.example.repository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.domain.Order;


@Repository
public class ShowCartRepository {
	
	private static final ResultSetExtractor<Order> ORDER_RESULT_SET_EXTRACTOR = (rs) -> {
		return null;
	};
	
	@Autowired
	private NamedParameterJdbcTemplate template;
	
	
	
	
	public Order findByUserIdAndStatus(Integer id) {
		String sql = "SELECT total_price from orders where id = :id";
		String sql2 = "SELECT quantity,size from order_items where order_id = orders.id";
		String sql3 = "SELECT name,image_path from items where id = order_items.item_id";
		
		String sql4 = "SELECT order_item_id from order_toppings where order_item_id = order_items.id";
		String sql5 = "SELECT name from toppings where id = order_toppings.id";
		
		return null;
	}
}
