package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.Form.LoginForm;
import com.example.domain.User;
import com.example.service.LoginService;


/**
 * ログイン用のコントローラーです.
 * 
 * @author yamaseki
 *
 */
@Controller
@RequestMapping("/loginController")
public class LoginController {
	
	@ModelAttribute
	public LoginForm setUpLoginForm() {
		return new LoginForm();
	}

	@Autowired
	private LoginService service;

	/**
	 * ログイン画面に遷移します.
	 * 
	 * @return ログイン画面
	 */
	@RequestMapping("")
	public String toLogin() {
		return "login";
	}

	/**
	 * ログイン処理です.
	 * 
	 * @param form ログイン画面からのパラーメターを受け取るフォーム
	 * @param result エラーメッセージを表示するためのオブジェクト
	 * @return ログインが成功すると商品一覧画面に遷移します
	 */
	@RequestMapping("/login")
	public String login(@Validated LoginForm form,BindingResult result) {
		if(result.hasErrors()) {
			return toLogin();
		}
		User user = service.login(form.getEmail(), form.getPassword());
		if(user==null) {
			result.rejectValue("email", null, "メールアドレスかパスワードが間違っています。");
			return toLogin();
		}
		return "forward:/show_item_list";
	}

}
