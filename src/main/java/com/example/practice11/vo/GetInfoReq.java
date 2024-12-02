package com.example.practice11.vo;

public class GetInfoReq {

	private String account;

	private String pwd;

	public GetInfoReq() {
		super();
	}

	public GetInfoReq(String account, String pwd) {
		super();
		this.account = account;
		this.pwd = pwd;
	}

	public String getAccount() {
		return account;
	}

	public String getPwd() {
		return pwd;
	}

}
