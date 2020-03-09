package com.example.repository;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.example.domain.Order;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;
import com.example.domain.Item;
import com.example.domain.OrderItem;
import com.example.domain.OrderTopping;
import com.example.domain.Topping;

/**
 * ordersテーブルを操作するレポジトリ.
 * 
 * @author nonaka
 *
 */

@Repository
public class OrderRepository {

	public static final RowMapper<Order> ORDER_ROW_MAPPER = (rs, i) -> {
		Order order = new Order();
		order.setId(rs.getInt("id"));
		order.setUserId(rs.getInt("user_id"));
		order.setStatus(rs.getInt("status"));
		order.setTotalPrice(rs.getInt("total_price"));
		order.setOrderDate(rs.getDate("order_date"));
		order.setDestinationName(rs.getString("destination_name"));
		order.setDestinationEmail(rs.getString("destination_email"));
		order.setDestinationZipcode(rs.getString("destination_zipcode"));
		order.setDestinationAddress(rs.getString("destination_address"));
		order.setDestinationTel(rs.getString("destination_tel"));
		order.setDeliveryTime(rs.getTimestamp("delivery_time"));
		order.setPaymentMethod(rs.getInt("payment_method"));
		return order;
	};

	/**
	 * Order,OrderItem,Item,OrderTopping,Toppingの5つのテーブルを結合する.
	 * orderオブジェクト内にはorderItemリストを格納する。 orderItemオブジェクト内にはorderToppingListを格納する。
	 */
	private static final ResultSetExtractor<List<Order>> ORDER_RESULT_SET_EXTRACTOR = (rs) -> {
		List<Order> orderList = new LinkedList<>();
		List<OrderItem> orderItemList = null;
		List<OrderTopping> orderToppingList = null;
		int beforeOrderId = 0;
		int beforeOrderItemId = 0;
		while (rs.next()) {
			int nowOrderId = rs.getInt("o_id");
			int nowOrderItemId = rs.getInt("oi_item_id");
			if (nowOrderId != beforeOrderId) {
				Order order = new Order();
				order.setId(rs.getInt("o_id"));
				order.setUserId(rs.getInt("o_user_id"));
				order.setStatus(rs.getInt("o_status"));
				order.setTotalPrice(rs.getInt("o_total_price"));
				order.setOrderDate(rs.getDate("o_order_date"));
				order.setDestinationName(rs.getString("o_destination_name"));
				order.setDestinationEmail(rs.getString("o_destination_email"));
				order.setDestinationZipcode(rs.getString("o_destination_zipcode"));
				order.setDestinationTel(rs.getString("o_destination_tel"));
				order.setDeliveryTime(rs.getTimestamp("o_delivery_time"));
				order.setPaymentMethod(rs.getInt("o_payment_method"));

				// order.setUserは今回未使用のためなし。
				orderItemList = new ArrayList<>();
				order.setOrderItemList(orderItemList);
				orderList.add(order);
			}
			if (nowOrderItemId != beforeOrderItemId) {
				OrderItem orderItem = new OrderItem();
				orderItem.setId(rs.getInt("oi_id"));
				orderItem.setItemId(rs.getInt("oi_item_id"));
				orderItem.setOrderId(rs.getInt("oi_order_id"));
				orderItem.setQuantity(rs.getInt("oi_quantity"));

				// sizeはドメインがchar型のため、String型→Char型に変換
				String size = rs.getString("oi_size");
				char[] sizeChar = size.toCharArray();
				orderItem.setSize(sizeChar[0]);

				// orderItemオブジェクト内のitemにItemクラスの情報を格納
				Item item = new Item();
				item.setId(rs.getInt("i_id"));
				item.setName(rs.getString("i_name"));
				item.setDescription(rs.getString("i_description"));
				item.setPriceM(rs.getInt("i_price_m"));
				item.setPriceL(rs.getInt("i_price_l"));
				item.setImagePath(rs.getString("i_image_path"));
				item.setDeleted(rs.getBoolean("i_deleted"));
				orderItem.setItem(item);

				orderToppingList = new ArrayList<>();
				orderItem.setOrderToppingList(orderToppingList);
				orderItemList.add(orderItem);
			}
			if (rs.getInt("ot_id") != 0) {
				OrderTopping orderTopping = new OrderTopping();
				orderTopping.setId(rs.getInt("ot_id"));
				orderTopping.setToppingId(rs.getInt("ot_topping_id"));
				orderTopping.setOrderItemId(rs.getInt("ot_order_item_id"));

				// orderToppingオブジェクト内のtoppingにToppingクラスの情報を格納
				Topping topping = new Topping();
				topping.setId(rs.getInt("t_id"));
				topping.setName(rs.getString("t_name"));
				topping.setPriceM(rs.getInt("t_price_m"));
				topping.setPriceL(rs.getInt("t_price_l"));
				orderTopping.setTopping(topping);
				orderToppingList.add(orderTopping);
			}
			beforeOrderId = nowOrderId;
			beforeOrderItemId = nowOrderItemId;
		}
		return orderList;
	};

	@Autowired
	private NamedParameterJdbcTemplate template;
	
	@Autowired
	private HttpSession session;

	/** insertで自動採番されるidを取ってくるためのオブジェクト */
	private SimpleJdbcInsert insert;

	/**
	 * insertで自動採番されるidを取ってくるためのメソッド.
	 */

	@PostConstruct
	public void init() {
		SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert((JdbcTemplate) template.getJdbcOperations());
		SimpleJdbcInsert withTableName = simpleJdbcInsert.withTableName("orders");
		insert = withTableName.usingGeneratedKeyColumns("id");
	}

	/**
	 * 引数のuserIdの注文情報がデータベースに存在するかをチェックするためのメソッド.
	 * 
	 * @param userId ユーザーID
	 * @return 注文情報を返します（注文情報がないときはnullを返します）
	 */
	public Order checkByUserIdAndStatus(Integer userId) {
		try {
			String sql = "select id,user_id,status,total_price,order_date,destination_name,destination_email,destination_zipcode,destination_address,destination_tel,delivery_time,payment_method from orders where user_id = :userId and status = 0";
			SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId).addValue("status", 0);
			Order order = template.queryForObject(sql, param, ORDER_ROW_MAPPER);
			
			return order;
		} catch (Exception e) {
			return null;
			
		}

	}

	/**
	 * Orderテーブルにデータを挿入します.
	 * 
	 * @param order 注文情報
	 * @return 注文情報を返します
	 */
	public Order insert(Order order) {
		String sql = "insert into orders (user_id,status,total_price)values(:userId,:status,:totalPrice)";
		SqlParameterSource param = new MapSqlParameterSource().addValue("userId", order.getUserId())
				.addValue("status", 0).addValue("totalPrice", 0);
		Number key = insert.executeAndReturnKey(param);
		order.setId(key.intValue());

		return order;
	}

	/**
	 * 注文情報を更新するためのメソッドです.
	 * 
	 * @param order 更新したい注文情報
	 */
	public void update2(Order order) {
		String sql = "update orders set total_price = :totalPrice where user_id = :userId and status = :status";
		SqlParameterSource param = new MapSqlParameterSource().addValue("userId", order.getUserId()).addValue("status", 0)
				.addValue("totalPrice", order.getTotalPrice());
		template.update(sql, param);
	}	
	
	/**
	 * ユーザIDと状態から注文情報を取得します. 
	 * 注文情報に含まれている、注文商品リスト、注文トッピングリストも取得します。
	 * statusは0を指定しています。
	 * 
	 * @param userId ユーザID
	 * @return 注文リスト
	 */
	public Order findByUserIdAndStatus(Integer userId) {
		String sql = "SELECT o.id AS o_id,o.user_id AS o_user_id,o.status AS o_status,o.total_price AS o_total_price"
				+ ",o.order_date AS o_order_date,o.destination_name AS o_destination_name,o.destination_email AS o_destination_email"
				+ ",o.destination_zipcode AS o_destination_zipcode,o.destination_tel AS o_destination_tel"
				+ ",o.delivery_time AS o_delivery_time,o.payment_method AS o_payment_method"
				+ ",oi.id AS oi_id,oi.item_id AS oi_item_id,oi.order_id AS oi_order_id,oi.quantity AS oi_quantity,oi.size AS oi_size"
				+ ",i.id AS i_id,i.name AS i_name,i.description AS i_description,i.price_m AS i_price_m,i.price_l AS i_price_l"
				+ ",i.image_path AS i_image_path,i.deleted AS i_deleted"
				+ ",ot.id AS ot_id,ot.topping_id AS ot_topping_id,ot.order_item_id AS ot_order_item_id"
				+ ",t.id AS t_id,t.name AS t_name,t.price_m AS t_price_m,t.price_l AS t_price_l"
				+ " FROM orders o LEFT OUTER JOIN order_items oi ON o.id=oi.order_id"
				+ " LEFT OUTER JOIN items i ON oi.item_id=i.id" + " LEFT OUTER JOIN order_toppings ot ON oi.id=ot.order_item_id"
				+ " LEFT OUTER JOIN toppings t ON ot.topping_id=t.id"
				+ " WHERE o.user_id=:userId AND o.status=:status ORDER BY o_id, oi_id";

		SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId).addValue("status", 0);

		List<Order> orderList = template.query(sql, param, ORDER_RESULT_SET_EXTRACTOR);

		if (orderList.size() == 0) {
			return null;
		}

		return orderList.get(0);

	}

	/**
	 * 注文情報の更新を行う.
	 * 
	 * @param order 注文情報
	 */
	public void update(Order order) {
		String sql = "UPDATE orders SET status = :status, total_price = :totalPrice, order_date = :orderDate, destination_name = :destinationName, destination_email = :destinationEmail, "
				+ "destination_zipcode = :destinationZipcode, destination_address = :destinationAddress, "
				+ "destination_tel = :destinationTel, delivery_time = :deliveryTime, payment_method = :paymentMethod";

		SqlParameterSource param = new BeanPropertySqlParameterSource(order);
		template.update(sql, param);
	}
	
	/**
	 * userのID情報を更新します。
	 * @param userId ユーザーID
	 */
	public void update(Integer userId,Integer sessionId) {
		String sql="UPDATE orders SET user_id=:userId WHERE user_id=:sessionId";
		SqlParameterSource param=new MapSqlParameterSource().addValue("userId", userId).addValue("sessionId", session.getId().hashCode());
		template.update(sql, param);
	}
	
	public void deleteById(Integer userId) {
		String sql="DELETE FROM orders where user_id = :userId";
		SqlParameterSource param=new MapSqlParameterSource().addValue("userId", userId);
		template.update(sql, param);
	}
}
