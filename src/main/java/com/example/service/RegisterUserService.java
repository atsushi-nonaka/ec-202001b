package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
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

	/**
	 * ユーザー情報を登録する
	 * 
	 * @param user ユーザー情報
	 */
	public void insert(User user) {
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
