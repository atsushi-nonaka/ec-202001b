package com.example.repository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.example.domain.OrderItem;

@Repository
public class OrderItemRepository {
	
	@Autowired
	private  NamedParameterJdbcTemplate template;
	
	private SimpleJdbcInsert insert;
	
	@PostConstruct
	public void init() {
		SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert((DataSource)template.getJdbcOperations());
		SimpleJdbcInsert withTableName = simpleJdbcInsert.withTableName("order_items");
		insert = withTableName.usingGeneratedKeyColumns("id");
	}
	
	public OrderItem insert(OrderItem orderItem) {
		String sql = "insert order_items(item_id,order_id,quantity,size)values(:itemId,:orderId,:quantity,:size)";
		SqlParameterSource param = new BeanPropertySqlParameterSource(orderItem);
		template.update(sql, param);
		
		Number key = insert.executeAndReturnKey(param);
		orderItem.setId((Integer) key);
		
		return orderItem;
	}

}
