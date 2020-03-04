package com.example.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.User;
import com.example.form.UserForm;
import com.example.service.RegisterUserService;

/**
 * ユーザー登録を操作するコントローラー.
 * 
 * @author tanaami
 *
 */
@Controller
@RequestMapping("/insert")
public class RegisterUserController {

	@Autowired
	private RegisterUserService registerUserService;

	@ModelAttribute
	private UserForm setUpUserForm() {
		return new UserForm();
	}

	/**
	 * ユーザー登録画面に遷移
	 * 
	 * @return ユーザー登録画面
	 */
	@RequestMapping("")
	public String index() {
		return "register_user";
	}

	@RequestMapping("insertUser")
	public String insertUser(@Validated UserForm userform, BindingResult result) {
		
		if(registerUserService.findByEmail(userform.getEmail())!=null) {
			result.rejectValue("email", null, "このメールアドレスは既に登録されています。");
		}
		if(!userform.getPassword().equals(userform.getPassword2())) {
			result.rejectValue("password", null, "パスワードと確認用パスワードが一致していません。");
		}
		if(result.hasErrors()) {
			return index();
		}
		
		User user = new User();
		BeanUtils.copyProperties(userform, user);
		registerUserService.insert(user);
		return "login";
	}
	
}
