package com.example.practice11.service.ifs;

import com.example.practice11.vo.BasicRes;
import com.example.practice11.vo.GetAllInfoRes;
import com.example.practice11.vo.GetAllInfoRes2;
import com.example.practice11.vo.GetBalanceRes;
import com.example.practice11.vo.GetInfoRes;

public interface AtmService {

	public BasicRes addInfo(String account, String pwd, int balance);
	
	public GetBalanceRes getBalance(String account, String pwd);
	
	public GetInfoRes getInfo(String account, String pwd);
	
	//
	public GetAllInfoRes getAllInfo();
	
	public GetAllInfoRes2 getAllInfo2();
	
	//練習， 存款 deposit(account, pwd, amount), 提款 withdraw(account, pwd, amount)
	public GetInfoRes deposit(String account,String pwd,int amount);
	
	public GetInfoRes withdraw(String account,String pwd,int amount);
	// 上課講解
	public GetInfoRes deposit1(String account, String pwd, int amount);
	
	public GetInfoRes depositOrWithdraw(String account, String pwd, int amount, boolean isDeposit);
}
