package com.example.form;

/**
 * ログイン画面でしようするフォーム
 * @author tanaami
 *
 */
public class LoginForm {

	/** Eメールアドレス */
	private String email;
	/** パスワード */
	private String password;
	
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
	@Override
	public String toString() {
		return "LoginForm [email=" + email + ", password=" + password + "]";
	}
	
	
}
