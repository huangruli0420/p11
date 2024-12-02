package com.example.practice11.vo;

public class GetBalanceRes extends BasicRes {

	private int balance;

	public GetBalanceRes() {
		super();
	}

	public GetBalanceRes(int code, String message) {
		// 父類別的要寫在第一行
		super(code, message);
	}
	
	public GetBalanceRes(int code, String message, int balance) {
		// 父類別的要寫在第一行
		super(code, message);
		this.balance = balance;
	
	}

	public int getBalance() {
		return balance;
	}

}
