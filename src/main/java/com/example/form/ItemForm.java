package com.example.form;

/**
 * 商品一覧表示時に使用するフォームです.
 * @author tsuchiyahiromu
 *
 */
public class ItemForm {
	/**
	 * 商品名
	 */
	private String name;

	@Override
	public String toString() {
		return "ItemForm [name=" + name + "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
