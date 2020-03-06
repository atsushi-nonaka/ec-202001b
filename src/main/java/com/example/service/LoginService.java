package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.User;
import com.example.repository.UserRepository;


/**
 * ログイン情報をリポジトリに渡す役割のサービスクラスです.
 * 
 * @author yamaseki
 *
 */
@Service
@Transactional
public class LoginService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	/**
	 * ログインメソッドです.
	 * 
	 * @param email 
	 * @param password
	 * @return ユーザー情報を返します
	 */
	public User login(String email, String password) {
		User user = userRepository.findByEmailAndPassword(email, password);
		// パスワード一致チェック
		  if(passwordEncoder.matches(password, user.getPassword())) {
		    return user;
	}
			return null;
	}
}
