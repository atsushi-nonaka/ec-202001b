package com.example.service;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.domain.LoginUser;
import com.example.domain.User;
import com.example.repository.UserRepository;

/**
 * ログイン後のユーザーに権限情報を付与するサービスクラス.
 * 
 * @author yamaseki
 *
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	/** DBから情報を得るためのリポジトリ */
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private HttpSession session;
	
	@Autowired
	private OrderConfirmService orderConfirmService;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.core.userdetails.UserDetailsService#
	 * loadUserByUsername(java.lang.String) DBから検索をし、ログイン情報を構成して返す。
	 */
	@Override
	public UserDetails loadUserByUsername(String email)
			throws UsernameNotFoundException {
		User user = userRepository.findByEmail(email);
		System.out.println(user);
		
		if (user == null) {
			System.out.println("ログイン失敗");
			throw new UsernameNotFoundException("そのEmailは登録されていません。");
		}
		System.out.println("ログイン成功");
		// 権限付与の例
		Collection<GrantedAuthority> authorityList = new ArrayList<>();
		authorityList.add(new SimpleGrantedAuthority("ROLE_USER")); // ユーザ権限付与
		
		//ログイン成功時にuser情報をsessionスコープに格納する
		session.setAttribute("userId",user.getId());
		//注文情報のIDを仮sessionIDからログイン者のIDに更新
		orderConfirmService.updateUserId(user.getId(),session.getId().hashCode());
		//		if(member.isAdmin()) {
//			authorityList.add(new SimpleGrantedAuthority("ROLE_ADMIN")); // 管理者権限付与
//		}
		return new LoginUser(user,authorityList);
	}
}
