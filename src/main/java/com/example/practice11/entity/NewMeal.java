package com.example.practice11.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "new_meal")
@IdClass(value = NewMealId.class) 
//@IdClass: 一個 Entity 中若有多個 PK 屬性，就必須要生成一個class來管理那些PK，此 Annotation 用來關聯該class
public class NewMeal {

	@Id
	@Column(name = "name")
	private String name;

	@Id
	@Column(name = "cooking_style")
	private String cookingStyle;

	@Column(name = "price")
	private int price;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCookingStyle() {
		return cookingStyle;
	}

	public void setCookingStyle(String cookingStyle) {
		this.cookingStyle = cookingStyle;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}


}
