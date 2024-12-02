package com.example.practice11.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

//在 Spring Boot 中，將 Entity 和 Repository 託管的意思是：
//Entity 是一個實體類，代表數據庫中的一個表。
//Repository 是一個數據訪問層，負責處理與數據庫的交互操作（如查詢、插入、刪除等）。

@Entity // 將此類別交由 spring boot 託管成 entity 類
@Table(name = "meal") // 將此 class Meal 關連到指定的資料表(Meal)
public class Meal {

	@Id // 表明此欄位是 PK (Primary Key)
	@Column(name = "name") // 將此屬性關連到指定的欄位，括號中的字串是欄位名稱
	private String name; // 此行的 name 是變數(屬性)名稱

	@Column(name = "price")
	private int price;

	public Meal() {
		super();
	}

//	有了帶有參數的建構方法就不需要setters，但要記得也加入無參數的建構方法，為了與資料庫連接時不出錯
	public Meal(String name, int price) {
		super();
		this.name = name;
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public int getPrice() {
		return price;
	}


}
