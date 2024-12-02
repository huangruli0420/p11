package com.example.practice11.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "person_info")
public class PersonInfo {

	@Id
	@Column(name = "id")
	private String id;
	
	@Column(name = "user_name")
	private String userName;
	
	@Column(name = "user_age")
	private int userAge;
	
	@Column(name = "city")
	private String city;

	
	public PersonInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PersonInfo(String id, String userName, int userAge, String city) {
		super();
		this.id = id;
		this.userName = userName;
		this.userAge = userAge;
		this.city = city;
	}

	public String getId() {
		return id;
	}

	public String getUserName() {
		return userName;
	}

	public int getUserAge() {
		return userAge;
	}

	public String getCity() {
		return city;
	}
	
}
