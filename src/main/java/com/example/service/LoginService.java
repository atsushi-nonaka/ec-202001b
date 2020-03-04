package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.User;
import com.example.repository.UserRepository;

/**
 * ログイン画面を操作するサービス
 * 
 * @author tana
 *
 */
@Service
public class LoginService {

	@Autowired
	private UserRepository userRepository;

	/**
	 * 登録済みのユーザー情報を検索する
	 * @param email　メールアドレス
	 * @param password　パスワード
	 * @return　ユーザー情報
	 */
	public User loginCheck(String email, String password) {
		User user = userRepository.findByEmailAndPassword(email, password);
		return user;
	}
}
