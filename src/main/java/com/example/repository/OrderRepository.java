package com.example.repository;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Item;
import com.example.domain.Order;
import com.example.domain.OrderItem;
import com.example.domain.OrderTopping;
import com.example.domain.Topping;

@Repository
public class OrderRepository {
	
	@Autowired
	NamedParameterJdbcTemplate template;
	
	/**
	 * Order,OrderItem,Item,OrderTopping,Toppingの5つのテーブルを結合したものからorderリストを作成する.
	 *orderオブジェクト内にはorderItemリストを格納する。
	 *orderItemオブジェクト内にはorderToppingListを格納する。 
	 */
	private static final ResultSetExtractor<List<Order>> ORDER_RESULT_SET_EXTRACTOR
	=(rs)->{
		List<Order> orderList=new LinkedList<>();
		List<OrderItem> orderItemList=null;
		List<OrderTopping> orderToppingList=null;
		int beforeOrderId=0;
		int beforeOrderItemId=0;
		while(rs.next()) {
			int nowOrderId=rs.getInt("o_id");
			int nowOrderItemId=rs.getInt("oi_item_id");
			if(nowOrderId!=beforeOrderId) {
				Order order=new Order();
				orderItemList=new ArrayList<>();
				order.setOrderList(orderItemList);
				orderList.add(order);
			}
			if(nowOrderItemId!=beforeOrderItemId) {
				OrderItem orderItem=new OrderItem();
				orderItem.setQuantity(rs.getInt("oi_quantity"));
				
				//sizeはドメインがchar型のため、String型→Char型に変換
				String size=rs.getString("oi_size");
				char [] sizeChar=size.toCharArray();
				orderItem.setSize(sizeChar[0]);		
				
				//orderItemオブジェクト内のitemにItemクラスの情報を格納
				Item item=new Item();
				item.setName(rs.getString("i_name"));
				item.setPriceM(rs.getInt("i_price_m"));
				item.setPriceL(rs.getInt("i_price_l"));
				item.setImagePath(rs.getString("i_image_path"));
				orderItem.setItem(item);
			
				orderToppingList=new ArrayList<>();
				orderItem.setOrderToppingList(orderToppingList);
				orderItemList.add(orderItem);
			}
			if (rs.getInt("ot_id") != 0) {
				OrderTopping orderTopping=new OrderTopping();
				
				//orderToppingオブジェクト内のtoppingにToppingクラスの情報を格納
				Topping topping=new Topping();
				topping.setName(rs.getString("t_name"));
				topping.setPriceM(rs.getInt("t_price_m"));
				topping.setPriceM(rs.getInt("t_price_l"));
				orderTopping.setTopping(topping);
				orderToppingList.add(orderTopping);
			}
			beforeOrderId=nowOrderId;
			beforeOrderItemId=nowOrderItemId;
			}
			return orderList;
		};
		
		/**
		 * ユーザIDと状態から注文情報を取得します.
		 * 注文情報に含まれている、注文商品リスト、注文トッピングリストも取得します.
		 * @param userId ユーザID
		 * @param status 状態
		 * @return　注文リスト
		 */
		public List<Order> findByUserIdAndStatus(Integer userId){
			String sql="SELECT o.id AS o_id,oi.item_id AS oi_item_id,ot.id AS ot_id,i.image_path AS i_image_path," + 
					"i.name AS i_name,i.price_m AS i_price_m,i.price_l AS i_price_l,oi.size AS oi_size,"+
					"oi.quantity AS oi_quantity,t.name AS t_name,t.price_m AS t_price_m,t.price_l AS t_price_l" + 
					"FROM orders o INNER JOIN order_items oi ON o.id=oi.order_id"+
					"INNER JOIN items i ON oi.item_id=i.id"+
					"INNER JOIN order_toppings ot ON oi.id=ot.order_item_id"+
					"INNER JOIN toppings t ON ot.topping_id=t.id"+
					"WHERE o.user_id=:userId AND o.status=:status ORDER BY o_id";
			
			SqlParameterSource param=new MapSqlParameterSource().addValue("userId", userId).addValue("status", 0);
			
			List<Order> orderList=template.query(sql, param, ORDER_RESULT_SET_EXTRACTOR);
			
			return orderList;
			
			
		}
		
		
	}
