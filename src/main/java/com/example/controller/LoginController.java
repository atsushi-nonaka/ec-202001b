package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.User;
import com.example.form.LoginForm;
import com.example.service.LoginService;

/**
 * ログイン・ログアウトを操作するコントローラー.
 * 
 * @author tanaami
 *
 */
@Controller
@RequestMapping("/login")
public class LoginController {

	@Autowired
	private LoginService loginService;

	@ModelAttribute
	private LoginForm setUpForm() {
		return new LoginForm();
	}

	/**
	 * ログイン画面を表示する
	 * 
	 * @return ログイン画面へ遷移
	 */
	@RequestMapping("")
	public String index() {
		return "login";
	}

	/**
	 * ログインをするメソッド
	 * 
	 * @param loginForm メールアドレスとパスワードのリクエストパラメータ
	 * @param model ログイン不可だった場合のエラーメッセージ
	 * @return 商品一覧画面へ遷移
	 */
	@RequestMapping("/loginCheck")
	public String loginCheck(LoginForm loginForm, Model model) {
		User user = loginService.loginCheck(loginForm.getEmail(), loginForm.getPassword());
		if (user == null) {
			model.addAttribute("loginerrors", "メールアドレス、またはパスワードが間違っています");
			return index();
		}
		return "item_list_pizza";
	}

	/**
	 * ショッピングカートへ遷移する用のメソッド
	 * 
	 * @return ショッピングカート画面
	 */
	@RequestMapping("/cartlist")
	public String cartList() {
		return "cart_list";
	}

	/**
	 * ログアウトをする
	 * 
	 * @return ログイン画面へ遷移
	 */
	@RequestMapping("/logout")
	public String logout() {
		return index();
	}
}
