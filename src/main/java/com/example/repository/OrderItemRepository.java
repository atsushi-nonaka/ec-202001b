package com.example.repository;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.example.domain.Order;
import com.example.domain.OrderItem;

/**
 * OrderItemsテーブルを操作するためのリポジトリ.
 * 
 * @author yamaseki
 *
 */
@Repository
public class OrderItemRepository {
	
	public static final RowMapper<OrderItem> ORDER_ITEM_ROW_MAPPER = (rs, i) -> {
		OrderItem orderItem = new OrderItem();
		orderItem.setId(rs.getInt("id"));
		orderItem.setItemId(rs.getInt("item_id"));
		orderItem.setOrderId(rs.getInt("order_id"));
		orderItem.setQuantity(rs.getInt("quantity"));
		
		// sizeはドメインがchar型のため、String型→Char型に変換
		String size = rs.getString("size");
		char[] sizeChar = size.toCharArray();
		orderItem.setSize(sizeChar[0]);
		
		return orderItem;
	};

	@Autowired
	private NamedParameterJdbcTemplate template;

	/** insertで自動採番されるidを取ってくるためのオブジェクト */
	private SimpleJdbcInsert insert;

	@PostConstruct
	public void init() {
		SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert((JdbcTemplate) template.getJdbcOperations());
		SimpleJdbcInsert withTableName = simpleJdbcInsert.withTableName("order_items");
		insert = withTableName.usingGeneratedKeyColumns("id");
	}

	/**
	 * OrderItemテーブルに商品情報を挿入するためのメソッド.
	 * 
	 * @param orderItem 挿入する注文商品情報
	 * @return 注文商品情報を返します
	 */
	public OrderItem insert(OrderItem orderItem) {
		String sql = "insert into order_items(item_id,order_id,quantity,size)values(:itemId,:orderId,:quantity,:size)";
		SqlParameterSource param = new BeanPropertySqlParameterSource(orderItem);
		Number key = insert.executeAndReturnKey(param);
		orderItem.setId(key.intValue());

		return orderItem;
	}
	
	public void updateOrderIdByOrderId(Integer hashedId,Integer loginUsersOrderId) {
		String sql = "update order_items set order_id=:loginUsersOrderId where order_id = :hashedId";
		SqlParameterSource param = new MapSqlParameterSource().addValue("loginUsersOrderId", loginUsersOrderId).addValue("hashedId", hashedId);
		template.update(sql, param);
	}
	
	public OrderItem findById(Integer orderItemId) {
		String sql = "SELECT id,item_id,order_id,quantity,size from order_items where id = :orderItemId";
		SqlParameterSource param = new MapSqlParameterSource().addValue("orderItemId", orderItemId);
		OrderItem orderItem = template.queryForObject(sql, param, ORDER_ITEM_ROW_MAPPER);
		return orderItem;
	}
	
	public void deleteById(Integer id) {
		String sql = "DELETE FROM order_items where id = :orderItemId";
		SqlParameterSource param = new MapSqlParameterSource().addValue("orderItemId", id);
		template.update(sql, param);
	}

}
