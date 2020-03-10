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
	
	/**
	 * 商品の順番
	 */
	private String highLow;

	@Override
	public String toString() {
		return "ItemForm [name=" + name + ", highLow=" + highLow + "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHighLow() {
		return highLow;
	}

	public void setHighLow(String highLow) {
		this.highLow = highLow;
	}
	
	
	
	
}
