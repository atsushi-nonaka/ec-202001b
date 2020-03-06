package com.example.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * ログイン画面からのパラメーターを受け取るフォームです.
 * 
 * @author yamaseki
 *
 */
public class LoginForm {

	/**メールアドレス*/
	@NotBlank(message="入力必須項目です")
	@Email(message="不正なメールアドレスです")
	private String email;
	/**パスワード*/
	@NotBlank(message="入力必須項目です")
	private String password;

	@Override
	public String toString() {
		return "LoginForm [name=" + email + ", password=" + password + "]";
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
