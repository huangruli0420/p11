package com.example.practice11.vo;

import java.util.List;

import com.example.practice11.entity.Atm;

public class GetAllInfoRes extends BasicRes {

	private List<Atm> infoList;

	public GetAllInfoRes() {
		super();
	}

	public GetAllInfoRes(int code, String message) {
		super(code, message);
	}

	public GetAllInfoRes(int code, String message,List<Atm> infoList) {
		super(code, message);
		this.infoList = infoList;
	}

	
	public List<Atm> getInfoList() {
		return infoList;
	}

}
