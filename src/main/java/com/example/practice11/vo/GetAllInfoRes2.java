package com.example.practice11.vo;

import java.util.List;

import com.example.practice11.dto.BasicInfo;
import com.example.practice11.entity.Atm;

public class GetAllInfoRes2 extends BasicRes {

	private List<BasicInfo> infoList;

	public GetAllInfoRes2() {
		super();
	}

	public GetAllInfoRes2(int code, String message) {
		super(code, message);
	}

	public GetAllInfoRes2(int code, String message,List<BasicInfo> infoList) {
		super(code, message);
		this.infoList = infoList;
	}

	
	public List<BasicInfo> getInfoList() {
		return infoList;
	}

}
