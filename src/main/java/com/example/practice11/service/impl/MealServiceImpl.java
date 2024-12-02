package com.example.practice11.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.practice11.entity.Meal;
import com.example.practice11.repository.MealDao;
import com.example.practice11.service.ifs.MealService;

@Service // 將此 class 交由 spring boot 託管成 service類，加在實作類別而非介面
public class MealServiceImpl implements MealService {

	// @Autowired:可以把本來交由 spring boot 託管的物件注入到變數中
	// 這邊是把 MealDao 整個物件注入到變數 mealDao 中
	@Autowired
	private MealDao mealDao;

	@Override
	public void addMeals(List<Meal> mealList) {
		// 逐筆檢查
		for (Meal item : mealList) {
			// name 的資料型態是String，要檢查:1.null 2.空字串 3.全空白字串
			if (item.getName() == null || item.getName().isBlank()) {
				System.out.println("餐點名稱錯誤!!");
				return; // 程式碼執行到return，就會跳出該方法，不會繼續執行
			}
			// price的資料型態是 int，要檢查負數
			if (item.getPrice() <= 0) {
				System.out.println("餐點價格錯誤!!");
				return;
			}
			// 這樣寫比起saveAll寫在for迴圈外面會呼叫很多次資料庫
//			mealDao.save(item); 
		}
		// 資料檢查都正確，將資料寫進資料庫
		mealDao.saveAll(mealList);
	}

	@Override
	public void updatePrice(String name, int price) {
		// 防呆，參數檢查
		// name 要檢查: 1.null 2.空字串 3.全空白字串
		// StringUtils.hasText(name) == false :表示name 是null或空字串或空白字串
		// 前面多驚嘆號 !StringUtils.hasText(name) 等同於 StringUtils.hasText(name) == false
		if (!StringUtils.hasText(name)) {
			System.out.println("餐點名稱錯誤!!");
			return; // 程式碼執行到return，就會跳出該方法，不會繼續執行
		}
		// price的資料型態是 int，要檢查負數
		if (price <= 0) {
			System.out.println("餐點價格錯誤!!");
			return;
		}
		// 更新資料
		int result = mealDao.updatePrice(name, price);
		if (result == 0) {
			System.out.println("餐點價格更新失敗");
			return;
		} 
		System.out.println("餐點價格更新成功");		
		
	}
	
	// 11/22上課練習，查詢特定餐點價格，若查詢不到餐點則會回傳null，則需要用Integer接回傳值而不是int
	@Override
	public void findByName(String name) {
		if (!StringUtils.hasText(name)) {
			System.out.println("請輸入餐點名稱");
			return; // 程式碼執行到return，就會跳出該方法，不會繼續執行
		}
		Integer price = mealDao.findByName(name);
//		Meal meal = mealDao.findByName1(name);
		if (price == null) {
			System.out.println("查無此餐點");
			return;
		}
		System.out.println(name + ":" +price);
	}
	// 11/22上課練習，findAll
	@Override
	public void findAll() {
		 List<Meal> list = mealDao.getAll();
		 for(Meal item: list) {
			 System.out.println(item.getName() + ": "+ item.getPrice());
		 }
	}
	
	
	

}
