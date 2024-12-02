package com.example.practice11.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.practice11.constants.ResMessage;
import com.example.practice11.dto.BasicInfo;
import com.example.practice11.entity.Atm;
import com.example.practice11.repository.AtmDao;
import com.example.practice11.service.ifs.AtmService;
import com.example.practice11.vo.BasicRes;
import com.example.practice11.vo.GetAllInfoRes;
import com.example.practice11.vo.GetAllInfoRes2;
import com.example.practice11.vo.GetBalanceRes;
import com.example.practice11.vo.GetInfoRes;

import ch.qos.logback.core.recovery.ResilientSyslogOutputStream;

@Service
public class AtmServiceImpl implements AtmService {

	@Autowired
	private AtmDao atmDao;

	@Override
	public BasicRes addInfo(String account, String pwd, int balance) {
		// 檢查 account、pwd 不能為 null、空字串 或 空白字串
		if (!StringUtils.hasText(account) || account.length() < 3 || account.length() > 8) {
//			System.out.println("帳號錯誤!!!!!");
			return new BasicRes(ResMessage.ACCOUNT_ERROR.getCode(), //
					ResMessage.ACCOUNT_ERROR.getMessage());
		}
		if (!StringUtils.hasText(pwd) || pwd.length() < 8 || pwd.length() > 16) {
//			System.out.println("密碼錯誤!!!!!");
			return new BasicRes(ResMessage.PASSWORD_ERROR.getCode(), //
					ResMessage.PASSWORD_ERROR.getMessage());
		}
		if (balance < 0) {
//			System.out.println("餘額不得為負數!!!!!");
			return new BasicRes(ResMessage.BALANCE_ERROR.getCode(), //
					ResMessage.BALANCE_ERROR.getMessage());
		}
		// save
		// 沒有 try catch，連續執行兩次，若已有同樣PK則會報錯
//		if(atmDao.insert(account, pwd, balance) != 1) {
//			System.out.println("新增資訊失敗!!!!!");
//			return;
//		}
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		// 有 try catch，可以讓程式繼續執行下去，表達當有錯誤時想表達的訊息
		try {
			atmDao.insert(account, encoder.encode(pwd), balance);
		} catch (Exception e) {
//			System.out.println("新增資訊失敗!!!!!");
			return new BasicRes(ResMessage.ADD_INFO_FAILED.getCode(), //
					ResMessage.ADD_INFO_FAILED.getMessage());
		}
//		System.out.println("新增資訊成功!!!!!");
		return new BasicRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage());
	}

	@Override
	public GetBalanceRes getBalance(String account, String pwd) {
		// 盡量事先檢查資料，減少跟資料庫的連接次數 (降低跟資料庫之間的cost)
		if (!StringUtils.hasText(account) || !StringUtils.hasText(pwd)) {
//			System.out.println("帳號或密碼錯誤!!!!!");
			return new GetBalanceRes(ResMessage.ACCOUNT_OR_PASSWORD_ERROR.getCode(), //
					ResMessage.ACCOUNT_OR_PASSWORD_ERROR.getMessage());
		}

		Atm atm = atmDao.getByAccount(account);
		BasicRes checkResult = check(atm, pwd);
		if (checkResult != null) {
			return new GetBalanceRes(checkResult.getCode(), checkResult.getMessage());
		}

//		// 檢查資料是否存在
//		if (atm == null) {
//			return new GetBalanceRes(ResMessage.NOT_FOUND.getCode(), ResMessage.NOT_FOUND.getMessage());
//		}
//		// 比對密碼
//		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//		if (!encoder.matches(pwd, atm.getPwd())) { // 匿名，等同於 encoder.matches(pwd, atm.getPwd()) == false
////			System.out.println("密碼錯誤!!!!!");
//			return new GetBalanceRes(ResMessage.PASSWORD_ERROR.getCode(), //
//					ResMessage.PASSWORD_ERROR.getMessage());
//		}

//		System.out.println(atm.getBalance());
		return new GetBalanceRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage()//
				, atm.getBalance());
	}

	// 將共通的檢查拉出來成為一個私有方法，只在這個介面的實作裡使用
	// 因為 atm 最後回傳會用到，所以帳密防呆跟 atm 不放在私有方法裡
	// 因為 兩個方法的資料回傳型態不同，只能先用父類別去接
	private BasicRes check(Atm atm, String pwd) {
		// 檢查資料是否存在
		if (atm == null) {
			return new BasicRes(ResMessage.NOT_FOUND.getCode(), ResMessage.NOT_FOUND.getMessage());
		}
		// 比對密碼
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		if (!encoder.matches(pwd, atm.getPwd())) { // 匿名，等同於 encoder.matches(pwd, atm.getPwd()) == false
			// System.out.println("密碼錯誤!!!!!");
			return new BasicRes(ResMessage.PASSWORD_ERROR.getCode(), //
					ResMessage.PASSWORD_ERROR.getMessage());
		}
		// 檢查通過
		return null;
	}

	@Override
	public GetInfoRes getInfo(String account, String pwd) {
		// 參數檢查
		if (!StringUtils.hasText(account) || !StringUtils.hasText(pwd)) {
			// System.out.println("帳號或密碼錯誤!!!!!");
			return new GetInfoRes(ResMessage.ACCOUNT_OR_PASSWORD_ERROR.getCode(), //
					ResMessage.ACCOUNT_OR_PASSWORD_ERROR.getMessage());
		}

		Atm atm = atmDao.getByAccount(account);
		//
		BasicRes res = check(atm, pwd);
		if (res != null) {
			return new GetInfoRes(res.getCode(), res.getMessage());
		}

//		// 檢查資料是否存在
//		if (atm == null) {
//			return new GetInfoRes(ResMessage.NOT_FOUND.getCode(), ResMessage.NOT_FOUND.getMessage());
//		}
//		// 比對密碼
//		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//		if (!encoder.matches(pwd, atm.getPwd())) { // 匿名，等同於 encoder.matches(pwd, atm.getPwd()) == false
//			// System.out.println("密碼錯誤!!!!!");
//			return new GetInfoRes(ResMessage.PASSWORD_ERROR.getCode(), //
//					ResMessage.PASSWORD_ERROR.getMessage());
//		}

		// 回傳資料
		return new GetInfoRes(ResMessage.SUCCESS.getCode(), //
				ResMessage.SUCCESS.getMessage(), account, atm.getBalance());
	}

	@Override
	public GetAllInfoRes getAllInfo() {
		List<Atm> res = atmDao.findAll(); // 如果用 findAll 會把以加密後的密碼塞回 Atm，用不到
		// lambda 語法: List 本身定義的 forEach 語法，無法回傳任何值
		res.forEach(item -> {
			item.setPwd(null);
		});
		// 一般forEach
		for (Atm item : res) {
			item.setPwd(null);
		}
		return new GetAllInfoRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage(), res);
	}

	@Override
	public GetAllInfoRes2 getAllInfo2() {
		List<BasicInfo> res = atmDao.getAll();
		return new GetAllInfoRes2(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage(), res);
	}

	// 練習，存款
	@Override
	public GetInfoRes deposit(String account, String pwd, int amount) {
		// 參數檢查，帳密防呆
		if (!StringUtils.hasText(account) || !StringUtils.hasText(pwd)) {
			return new GetInfoRes(ResMessage.ACCOUNT_OR_PASSWORD_ERROR.getCode(), // System.out.println("帳號或密碼錯誤!!!!!");
					ResMessage.ACCOUNT_OR_PASSWORD_ERROR.getMessage());
		}
		// 要存款的金額不得小於0
		if (amount <= 0) {
			return new GetInfoRes(ResMessage.AMOUNT_ERROR.getCode(), //
					ResMessage.AMOUNT_ERROR.getMessage());
		}
		Atm atm = atmDao.getByAccount(account);
		// 檢查資料是否存在
		if (atm == null) {
			return new GetInfoRes(ResMessage.NOT_FOUND.getCode(), ResMessage.NOT_FOUND.getMessage());
		}
		// 比對密碼
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		if (!encoder.matches(pwd, atm.getPwd())) { // 匿名，等同於 encoder.matches(pwd, atm.getPwd()) == false
			return new GetInfoRes(ResMessage.PASSWORD_ERROR.getCode(), // System.out.println("密碼錯誤!!!!!");
					ResMessage.PASSWORD_ERROR.getMessage());
		}
		// 更改資料庫
		int newBalance = atm.getBalance() + amount;
		atmDao.updateBalance(atm.getAccount(), newBalance);
		return new GetInfoRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage(), account, newBalance);
	}

	// 練習，提款
	@Override
	public GetInfoRes withdraw(String account, String pwd, int amount) {
		// 參數檢查，帳密防呆
		if (!StringUtils.hasText(account) || !StringUtils.hasText(pwd)) {
			// System.out.println("帳號或密碼錯誤!!!!!");
			return new GetInfoRes(ResMessage.ACCOUNT_OR_PASSWORD_ERROR.getCode(), // System.out.println("帳號或密碼錯誤!!!!!");
					ResMessage.ACCOUNT_OR_PASSWORD_ERROR.getMessage());
		}
		// 要提款的金額不得小於0
		if (amount <= 0) {
			return new GetInfoRes(ResMessage.AMOUNT_ERROR.getCode(), //
					ResMessage.AMOUNT_ERROR.getMessage());
		}
		// 檢查帳號是否存在(使用 JPA 的 findById);
		// findById 是 JPA 原有的方法中，唯一一個回傳值型態是被 Optional 包住的
		// 用 Optional 主要是提醒撰寫者要檢查回傳值是否為 null
		// 自定義的 Dao 方法其回傳值也可以使用 Optional
		// Optional import 的 library 是 java.util.Optional
		Optional<Atm> op = atmDao.findById(account);
		// 檢查回傳值

		Atm atm = atmDao.getByAccount(account);
		// 檢查資料是否存在
		if (atm == null) {
			return new GetInfoRes(ResMessage.NOT_FOUND.getCode(), ResMessage.NOT_FOUND.getMessage());
		}
		// 比對密碼
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		if (!encoder.matches(pwd, atm.getPwd())) { // 匿名，等同於 encoder.matches(pwd, atm.getPwd()) == false
			return new GetInfoRes(ResMessage.PASSWORD_ERROR.getCode(), // System.out.println("密碼錯誤!!!!!");
					ResMessage.PASSWORD_ERROR.getMessage());
		}
		// 跟存款檢查不同，要多檢查一個 提款金額是否大於餘額
		if (amount > atm.getBalance()) {
			return new GetInfoRes(ResMessage.AMOUNT_ERROR.getCode(), //
					ResMessage.AMOUNT_ERROR.getMessage());
		}
		// 更改資料庫
		int newBalance = atm.getBalance() - amount;
		atmDao.updateBalance(atm.getAccount(), newBalance);
		return new GetInfoRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage(), account, newBalance);
	}

	// 上課講解 存款提款
	@Override
	public GetInfoRes deposit1(String account, String pwd, int amount) {
		// 參數檢查，帳密防呆
		if (!StringUtils.hasText(account) || !StringUtils.hasText(pwd)) {
			// System.out.println("帳號或密碼錯誤!!!!!");
			return new GetInfoRes(ResMessage.ACCOUNT_OR_PASSWORD_ERROR.getCode(), // System.out.println("帳號或密碼錯誤!!!!!");
					ResMessage.ACCOUNT_OR_PASSWORD_ERROR.getMessage());
		}
		// 要提款的金額不得小於0
		if (amount <= 0) {
			return new GetInfoRes(ResMessage.AMOUNT_ERROR.getCode(), //
					ResMessage.AMOUNT_ERROR.getMessage());
		}
		// 檢查帳號是否存在(使用 JPA 的 findById);
		// findById 是 JPA 原有的方法中，唯一一個回傳值型態是被 Optional 包住的
		// 用 Optional 主要是提醒撰寫者要檢查回傳值是否為 null
		// 自定義的 Dao 方法其回傳值也可以使用 Optional
		// Optional import 的 library 是 java.util.Optional
		Optional<Atm> op = atmDao.findById(account);
		// 檢查回傳值
		if (op.isEmpty()) { // isEmpty = true 時，表示被 Optional 包住的 Atm 是 null
			return new GetInfoRes(ResMessage.NOT_FOUND.getCode(), ResMessage.NOT_FOUND.getMessage());
		}
		// 確定 被 Optional 包住的 Atm 不是 null，從 Optional<Atm> 取出 Atm
		Atm atm = op.get();
		// 確認密碼
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		if (!encoder.matches(pwd, atm.getPwd())) { // 匿名，等同於 encoder.matches(pwd, atm.getPwd()) == false
			return new GetInfoRes(ResMessage.PASSWORD_ERROR.getCode(), // System.out.println("密碼錯誤!!!!!");
					ResMessage.PASSWORD_ERROR.getMessage());
		}
		// 因為上面已經檢查過帳密，只剩 更新餘額時 有可能會出錯
		int newBalance = atm.getBalance() + amount;
		try {
			atmDao.updateBalance(account, newBalance);
		} catch (Exception e) {
			return new GetInfoRes(ResMessage.UPDATE_BALANCE_ERROR.getCode(), //
					ResMessage.UPDATE_BALANCE_ERROR.getMessage());
		}
		return new GetInfoRes(ResMessage.SUCCESS.getCode(), //
				ResMessage.SUCCESS.getMessage(), account, newBalance);
	}

	// 上課講解，合併存款提款
	@Override
	public GetInfoRes depositOrWithdraw(String account, String pwd, int amount, boolean isDeposit) {
		// 參數檢查，帳密防呆
		if (!StringUtils.hasText(account) || !StringUtils.hasText(pwd)) {
			// System.out.println("帳號或密碼錯誤!!!!!");
			return new GetInfoRes(ResMessage.ACCOUNT_OR_PASSWORD_ERROR.getCode(), // System.out.println("帳號或密碼錯誤!!!!!");
					ResMessage.ACCOUNT_OR_PASSWORD_ERROR.getMessage());
		}
		// 要提款的金額不得小於0
		if (amount <= 0) {
			return new GetInfoRes(ResMessage.AMOUNT_ERROR.getCode(), //
					ResMessage.AMOUNT_ERROR.getMessage());
		}
		// 檢查帳號是否存在(使用 JPA 的 findById);
		// findById 是 JPA 原有的方法中，唯一一個回傳值型態是被 Optional 包住的
		// 用 Optional 主要是提醒撰寫者要檢查回傳值是否為 null
		// 自定義的 Dao 方法其回傳值也可以使用 Optional
		// Optional import 的 library 是 java.util.Optional
		Optional<Atm> op = atmDao.findById(account);
		// 檢查回傳值
		if (op.isEmpty()) { // isEmpty = true 時，表示被 Optional 包住的 Atm 是 null
			return new GetInfoRes(ResMessage.NOT_FOUND.getCode(), ResMessage.NOT_FOUND.getMessage());
		}
		// 確定 被 Optional 包住的 Atm 不是 null，從 Optional<Atm> 取出 Atm
		Atm atm = op.get();
		// 確認密碼
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		if (!encoder.matches(pwd, atm.getPwd())) { // 匿名，等同於 encoder.matches(pwd, atm.getPwd()) == false
			return new GetInfoRes(ResMessage.PASSWORD_ERROR.getCode(), // System.out.println("密碼錯誤!!!!!");
					ResMessage.PASSWORD_ERROR.getMessage());
		}
		// =======================================
		// 存款或是提款的差異檢查
//		int newBalance = 0;
//		if (!isDeposit) { // 提款
//			if (atm.getBalance() < amount) {
//				return new GetInfoRes(ResMessage.BALANCE_INSUFFICIENT.getCode(), //
//						ResMessage.BALANCE_INSUFFICIENT.getMessage());
//			}
//			newBalance = atm.getBalance() - amount;
//		} else {
//			newBalance = atm.getBalance() + amount;
//		}
		// =====================
		if (!isDeposit && atm.getBalance() < amount)
			return new GetInfoRes(ResMessage.BALANCE_INSUFFICIENT.getCode(), //
					ResMessage.BALANCE_INSUFFICIENT.getMessage());
		// 三元式寫法，當 isDeposit 為 true 做?後面的存款，否則做冒號後面的提款
		int newBalance = isDeposit ? atm.getBalance() + amount : atm.getBalance() - amount;

		try {
			atmDao.updateBalance(account, !isDeposit ? atm.getBalance() - amount : atm.getBalance() + amount);
		} catch (Exception e) {
			return new GetInfoRes(ResMessage.UPDATE_BALANCE_ERROR.getCode(), //
					ResMessage.UPDATE_BALANCE_ERROR.getMessage());
		}
		return new GetInfoRes(ResMessage.SUCCESS.getCode(), //
				ResMessage.SUCCESS.getMessage(), account, newBalance);
	}

}
