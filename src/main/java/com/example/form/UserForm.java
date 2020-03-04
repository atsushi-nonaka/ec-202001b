package com.example.Form;

import javax.validation.constraints.NotBlank;

/**
 * ユーザー登録時に使用するフォーム.
 * @author tanaami
 *
 */
public class UserForm {
    @NotBlank(message="名前を入力してください")
	private String name;
	/** Eメールアドレス */
    @NotBlank(message="メールアドレスを入力してください")
	private String email;
		/** 郵便番号 */
    @NotBlank(message="郵便番号を入力してください")
	private String zipcode;
	/** 住所 */
    @NotBlank(message="住所を入力してください")
	private String address;
	/** 電話番号 */
    @NotBlank(message="電話番号を入力してください")
	private String telephone;
	/** パスワード */
    @NotBlank(message="パスワードを入力してください")
	private String password;
	/** 確認用パスワード */
    @NotBlank(message="確認用パスワードを入力してください")
	private String password2;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPassword2() {
		return password2;
	}
	public void setPassword2(String password2) {
		this.password2 = password2;
	}
	@Override
	public String toString() {
		return "UserForm [name=" + name + ", email=" + email + ", zipcode=" + zipcode + ", address=" + address
				+ ", telephone=" + telephone + ", password=" + password + ", password2=" + password2 + "]";
	}
	
	  
	
	
}
