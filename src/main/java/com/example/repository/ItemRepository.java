package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Item;

/**
 * itemsテーブルを操作するレポジトリ.
 * 
 * @author tsuchiyahiromu
 *
 */
@Repository
public class ItemRepository {
	@Autowired
	private NamedParameterJdbcTemplate template;

	/**
	 * Itemオブジェクト生成するローマッパー.
	 */
	private static final RowMapper<Item> ITEM_ROW_MAPPER = (rs, i) -> {
		Item item = new Item();
		item.setId(rs.getInt("id"));
		item.setName(rs.getString("name"));
		item.setDescription(rs.getString("description"));
		item.setPriceM(rs.getInt("price_m"));
		item.setPriceL(rs.getInt("price_l"));
		item.setImagePath(rs.getString("image_path"));
		item.setDeleted(rs.getBoolean("deleted"));
		return item;
	};

	

	/**
	 * 商品名から商品情報を取得します.
	 * 
	 * @param name 商品名
	 * @return 商品情報を返します
	 */
	public List<Item> findByLikeName(String name) {
		String sql = "SELECT id,name,description,price_m,price_l,image_path,deleted FROM items where name LIKE :name ORDER BY price_m";
		SqlParameterSource param = new MapSqlParameterSource().addValue("name", "%" + name + "%");
		List<Item> itemList = template.query(sql, param, ITEM_ROW_MAPPER);
		return itemList;
	}

	/**
	 * 商品IDから商品を検索するためのメソッドです.
	 * (3.5 8:30 メソッド名をfindByItemId→loadに修正しました)
	 *(確認できたらこのコメントは削除します)
	 * 
	 * @param itemId 商品ID
	 * @return 商品情報を返します
	 */
	public Item load(Integer itemId) {
		String sql = "SELECT id,name,description,price_m,price_l,image_path,deleted FROM items where id = :id ORDER BY price_m";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", itemId);
		Item item = template.queryForObject(sql, param, ITEM_ROW_MAPPER);
		return item;
	}
	
	/**
	 * 安い商品順に並び替え
	 * @param priceM　Mサイズ商品
	 * @return　商品情報を返す
	 */
	public List<Item> findByPriceOrederBy(Integer priceM) {
		String sql = "SELECT id,name,description,price_m,price_l,image_path,deleted FROM items ORDER BY price_m";
		List<Item> itemList = template.query(sql, ITEM_ROW_MAPPER);
		return itemList;
	}
	
	/**
	 * 高い商品順に並び替え
	 * @param priceM　Mサイズ商品
	 * @return　商品情報を返す
	 */
	public List<Item> findByPriceOrederByDeac(Integer priceM) {
		String sql = "SELECT id,name,description,price_m,price_l,image_path,deleted FROM items ORDER BY price_m DESC";
		List<Item> itemList = template.query(sql, ITEM_ROW_MAPPER);
		return itemList;
	}
	

	

}
