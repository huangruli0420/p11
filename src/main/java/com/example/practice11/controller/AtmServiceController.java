package com.example.practice11.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.practice11.service.ifs.AtmService;
import com.example.practice11.vo.AddInfoReq;
import com.example.practice11.vo.BasicRes;
import com.example.practice11.vo.DepositWithdrawReq;
import com.example.practice11.vo.GetAllInfoRes;
import com.example.practice11.vo.GetAllInfoRes2;
import com.example.practice11.vo.GetBalanceRes;
import com.example.practice11.vo.GetInfoReq;
import com.example.practice11.vo.GetInfoRes;

//三層式架構 MVC， Model (也就是Entity)，View (前端畫面)， Controller (提供外部和內部的連接)

//@RestController 包含了 @Controller 和 @ResponseBody
//@Controller 是指將此類別交由 Spring boot 託管成 Controller 物件
//@ResponseBody: 可以將自定義的物件(response)轉換成 JSON 格式傳輸給外部
//加了 @RestController 後，就不需要在 AddInfoRes(或其他的 xxxRes)前面加上 @ResponseBody

//@RequestMapping("atm/")，代表這個 controller 所有方法的 value 的共通前綴，可以把 atm/ 拿掉 
@RestController
public class AtmServiceController {

	@Autowired
	private AtmService atmService;

	/**
	 * 1. @PostMapping: 表示請求方法的 Http method 是 POST<br>
	 * 2. value = "atm/add_info": 表示該請求的路徑(URL)，自定義<br>
	 * 3. @RequestBody: 可以把外部請求中的 JSON 物件(key-value) 對應到自定義的 AddInfoReq 中的屬性名稱中
	 * ，並把值賦予到對應的變數裡 4. 外部請求的 key 的字串要和 AddInfoReq 中的屬性變數名稱完全一樣，這樣才會把外部的 value 設定到
	 * AddInfoReq 中對應的屬性變數中
	 */
//	下一行的另一種寫法: @RequestMapping(value = "atm/add_info", method = RequestMethod.POST)
	@PostMapping(value = "atm/add_info")
	public BasicRes addInfo(@RequestBody AddInfoReq req) { // 因為上面是 @RestController 所以 BasicRes 前面不用加 @ResponseBody
		return atmService.addInfo(req.getAccount(), req.getPwd(), req.getBalance());
	}

	// 另一種寫法
	@PostMapping(value = "atm/add_info1")
	public BasicRes addInfo1(@RequestParam String account, @RequestParam String pwd, @RequestParam int balance) {
		return atmService.addInfo(account, pwd, balance);
	}

	@PostMapping(value = "atm/get_info")
	public GetInfoRes getInfo(@RequestBody GetInfoReq req) {
		return atmService.getInfo(req.getAccount(), req.getPwd());
	}

	//
	@GetMapping(value = "atm/get_all_info")
	public GetAllInfoRes getAllInfo() {
		return atmService.getAllInfo();
	}
	
	@GetMapping(value = "atm/get_all_info2")
	public GetAllInfoRes2 getAllInfo2() {
		return atmService.getAllInfo2();
	}
	
	//練習，存款提款，請求的物件是 DepositWithdrawReq
	@PostMapping(value = "atm/deposit")
	public GetInfoRes deposit(@RequestBody DepositWithdrawReq req) {
		return atmService.deposit(req.getAccount(), req.getPwd(), req.getAmount());
	}
	
	@PostMapping(value = "atm/withdraw")
	public GetInfoRes withdraw(@RequestBody DepositWithdrawReq req) {
		return atmService.withdraw(req.getAccount(), req.getPwd(), req.getAmount());
	}
	
	@PostMapping(value = "atm/deposit1")
	public GetInfoRes deposit1(@RequestBody DepositWithdrawReq req) {
		return atmService.deposit1(req.getAccount(), req.getPwd(), req.getAmount());
	}
}
