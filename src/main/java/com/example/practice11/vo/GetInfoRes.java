package com.example.practice11.vo;

public class GetInfoRes extends BasicRes {

	private String account;

	private int balance;

	public GetInfoRes() {
		super();
	}

	public GetInfoRes(int code, String message) {
		super(code, message);
	}

	public GetInfoRes(int code, String message, String account, int balance) {
		super(code, message);
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
