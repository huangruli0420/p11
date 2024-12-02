package com.example.practice11.vo;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AddInfoReq {

	private String account;

	// @JsonProperty("password"): 可以將外部請求中 key 和括號中的字串 mapping，並將值賦予到變數中
	// ，但只能有一個(一對一)，關鍵字 value 可省略
	// @JsonAlias({"password", "pwd"}): 效果同@JsonProperty，可以有多個 key 的 mapping
	// 上述兩個注釋擇一使用，通常會使用 JsonAlias ，因為可以多對一
//	@JsonProperty(value = "password") 
	@JsonAlias(value = {"password","pwd"})
	private String pwd;

	private int balance;

	public AddInfoReq() {
		super();
	}

	public AddInfoReq(String account, String pwd, int balance) {
		super();
		this.account = account;
		this.pwd = pwd;
		this.balance = balance;
	}

	public String getAccount() {
		return account;
	}

	public String getPwd() {
		return pwd;
	}

	public int getBalance() {
		return balance;
	}

}
