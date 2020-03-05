package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.User;

/**
 * ユーザー登録を操作するリポジトリ.
 * 
 * @author tanaami
 *
 */
@Repository
public class UserRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	/**
	 * Userオブジェクト生成するローマッパー.
	 */
	private static final RowMapper<User> User_ROW_MAPPER = (rs, i) -> {
		User use = new User();
		use.setId(rs.getInt("id"));
		use.setName(rs.getString("name"));
		use.setEmail(rs.getString("email"));
		use.setPassword(rs.getString("password"));
		use.setZipcode(rs.getString("zipcode"));
		use.setAddress(rs.getString("address"));
		use.setTelephone(rs.getString("telephone"));
		return use;
	};

	/**
	 * ユーザー情報を登録します
	 * 
	 * @param user ユーザー情報
	 */
	public void insert(User user) {
		String sql = "INSERT INTO users(name,email,zipcode,address,telephone,password) VALUES (:name,:email,:zipcode,:address,:telephone,:password)";
		SqlParameterSource param = new BeanPropertySqlParameterSource(user);
		template.update(sql, param);
	}

	/**
	 * 登録済みのメールアドレスを検索します
	 * 
	 * @param email 登録済のメールアドレス
	 * @return 登録していない場合はnullを返す
	 */
	public User findByEmail(String email) {
		String sql = "SELECT id,name,email,zipcode,address,telephone,password FROM users WHERE email = :email";
		SqlParameterSource param = new MapSqlParameterSource().addValue("email", email);
		List<User> userList = template.query(sql, param, User_ROW_MAPPER);
		if (userList.size() == 0) {
			return null;
		} else {
			return userList.get(0);
		}
	}
	
	
	/**
	 * メールアドレスとパスワードで登録済みのユーザーを検索します
	 * 
	 * @param email 登録済のメールアドレス
	 * @return 登録していない場合はnullを返す
	 */
	public User findByEmailAndPassword(String email,String password) {
		String sql = "SELECT id,name,email,zipcode,address,telephone,password FROM users WHERE email = :email and password = :password";
		SqlParameterSource param = new MapSqlParameterSource().addValue("email", email).addValue("password", password);
		List<User> userList = template.query(sql, param, User_ROW_MAPPER);
		if (userList.size() == 0) {
			return null;
		} else {

			return userList.get(0);
		}
	}
}
