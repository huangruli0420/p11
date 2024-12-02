package com.example.practice11.vo;

public class DepositWithdrawReq {

	private String account;

	private String pwd;

	private int amount;

	public DepositWithdrawReq() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DepositWithdrawReq(String account, String pwd, int amount) {
		super();
		this.account = account;
		this.pwd = pwd;
		this.amount = amount;
	}

	public String getAccount() {
		return account;
	}

	public String getPwd() {
		return pwd;
	}

	public int getAmount() {
		return amount;
	}

}
