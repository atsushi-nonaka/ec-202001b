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
import com.example.domain.Order;
import com.example.domain.User;
import com.example.repository.OrderItemRepository;
import com.example.repository.OrderRepository;
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

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private OrderItemRepository orderItemRepository;

	
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
//		orderConfirmService.updateUserId(user.getId(),session.getId().hashCode());
		//		if(member.isAdmin()) {
//			authorityList.add(new SimpleGrantedAuthority("ROLE_ADMIN")); // 管理者権限付与
//		}
		
		if (user != null) {
			if (session.getAttribute("hashedOrder") != null) {
				Order order = (Order) session.getAttribute("hashedOrder");
				Integer hashedOrderId = order.getId();
				Order order2 = orderRepository.checkByUserIdAndStatus(user.getId());
				if (order2 == null) {
					order2 = new Order();
					order2.setUserId(user.getId());
					order2.setStatus(0);
					order2.setTotalPrice(0);
					order2 = orderRepository.insert(order2);
					System.out.println("order2=" + order2);
				}
				Integer loginUsersOrderId = order2.getId();
				System.out.println("hashedOrderId="+hashedOrderId);
				System.out.println("loginUsersOrderId="+loginUsersOrderId);
				orderItemRepository.updateOrderIdByOrderId(hashedOrderId, loginUsersOrderId);
				//ハッシュのDB消す
				orderRepository.deleteById(order.getUserId());
				//金額揃える
				System.out.println("元々の金額"+order2.getTotalPrice());
				order2.setTotalPrice(order2.getTotalPrice()+order.getTotalPrice());
				System.out.println("調整後の金額"+order2.getTotalPrice());
				orderRepository.update2(order2);				
			}
		}
		
		return new LoginUser(user,authorityList);
	}
}
