package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.domain.User;
import com.example.repository.UserRepository;

/**
 * ユーザー登録を操作するサービス.
 * 
 * @author tanaami
 *
 */
@Service
public class RegisterUserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	 /**
	 * 管理者情報を登録します.
	 * パスワードはここでハッシュ化されます
	 * 
	 * @param administrator　管理者情報
	 */
	public void insert(User user) {
	  
	  // パスワードをハッシュ化
	  user.setPassword(passwordEncoder.encode(user.getPassword()));
	  
	  userRepository.insert(user);
	}	

	/**
	 * 登録済みのメールアドレスを検索する
	 * 
	 * @param email メールアドレス
	 * @return 登録していない場合はnullを返す
	 */
	public User findByEmail(String email) {
		User user = userRepository.findByEmail(email);
		return user;
	}
}
