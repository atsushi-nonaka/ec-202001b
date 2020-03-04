package com.example.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Topping;

@Repository
public class ToppingRepository {
	
	@Autowired
	private NamedParameterJdbcTemplate template;
	
	private static final RowMapper<Topping> TOPPING_ROW_MAPPER = (rs, i) -> {
		Topping topping = new Topping();
		topping.setId(rs.getInt("id"));
		topping.setName(rs.getString("name"));
		topping.setPriceM(rs.getInt("price_m"));
		topping.setPriceL(rs.getInt("price_l"));
		
		return topping;
	};
	
	public Topping findByToppingId(Integer toppingId) {
		String sql = "SELECT id,name,price_m,price_l from toppings where id = :id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", toppingId);
		Topping topping = template.queryForObject(sql, param, TOPPING_ROW_MAPPER);
		return topping;
	}

}