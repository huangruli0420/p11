package com.example.practice11.constants;

public enum ResMessage {

	// 有多個 enum 用逗號分隔，寫在class的最上層，分號只會有一個
	SUCCESS(200, "Success!!!!!"), //
	ACCOUNT_ERROR(400, "Account error!!!!!"), //
	PASSWORD_ERROR(400, "Password error!!!!!"), //
	BALANCE_ERROR(400, "Balance error!!!!!"), //
	ADD_INFO_FAILED(400, "Add info failed!!!!!"), //
	ACCOUNT_OR_PASSWORD_ERROR(400, "Account or password error!!!!!"),//
	NOT_FOUND(404, "Not found!!!!!"),//
	//練習存款、提款
	AMOUNT_ERROR(400, "Amount error!!!!!"),//
	UPDATE_BALANCE_ERROR(400, "Update balance error error!!!!!"),//
	BALANCE_INSUFFICIENT(400, "Balance insufficient"),//
	;

	private int code;

	private String message;

	private ResMessage(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

}
