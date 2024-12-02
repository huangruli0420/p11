package com.example.practice11.dto;

public class BasicInfo {

	private String account;
	
	private int balance;

	public BasicInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BasicInfo(String account, int balance) {
		super();
		this.account = account;
		this.balance = balance;
	}

	public String getAccount() {
		return account;
	}

	public int getBalance() {
		return balance;
	}
	
}
