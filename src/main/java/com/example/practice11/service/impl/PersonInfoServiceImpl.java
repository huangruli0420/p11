package com.example.practice11.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.example.practice11.entity.PersonInfo;
import com.example.practice11.repository.PersonInfoDao;
import com.example.practice11.service.ifs.PersonInfoService;

// 11/22上課練習
@Service
public class PersonInfoServiceImpl implements PersonInfoService {

	@Autowired
	private PersonInfoDao personInfoDao;

	@Override
	public void createInfo(List<PersonInfo> personInfoList) {
//		if (personInfoList.isEmpty() || personInfoList == null) {
//			System.out.println("新增資訊不得為空");
//			return;
//		}
		// CollectionUtils.isEmpty(personInfoList : 會檢查 list是否為 null 或是 空，檢查結果等同於上面那段
		if (CollectionUtils.isEmpty(personInfoList)) {
			System.out.println("新增資訊不得為空");
			return;
		}
		// 檢查參數
		for (PersonInfo item : personInfoList) {
			// 檢查 id，因為是PK，所以一定要檢查，不得為:1.null 2.空字串 3.全空白字串
			if (!StringUtils.hasText(item.getId())) {
				System.out.println("Id 錯誤!!!!!");
				return;
			}
			// 檢查 userName，不得為:1.null 2.空字串 3.全空白字串
			if (!StringUtils.hasText(item.getUserName())) {
				System.out.println("user name 錯誤!!!!!");
				return;
			}
			// 檢查 userAge，不得 <= 0
			if (item.getUserAge() <= 0) {
				System.out.println("user age 錯誤!!!!!");
				return;
			}
			// 檢查id是否已存在，匿名寫法，boolean res = personInfoDao.existsById(item.getId());
			// personInfoDao.existsById(item.getId())，==true省略
			if (personInfoDao.existsById(item.getId())) {
				System.out.println("id 已存在!!!!!");
				return;
			}
		}
		// 將資料存進資料庫
		// save 或 saveAll 是 JPA 既有的方法，會執行新增或更新
		// ==> PK已存在 : 執行更新， PK不存在 : 執行新增
		personInfoDao.saveAll(personInfoList);
		System.out.println("新增資訊成功!!!!!");
	}

	// 跟上面方法一樣，差在最後蒐集ID，只需要操作資料庫1次，相比於上面5次
	@Override
	public void createInfo1(List<PersonInfo> personInfoList) {
		if (CollectionUtils.isEmpty(personInfoList)) {
			System.out.println("新增資訊不得為空");
			return;
		}
		List<String> idList = new ArrayList<>();
		// 檢查參數
		for (PersonInfo item : personInfoList) {
			// 檢查 id，因為是PK，所以一定要檢查，不得為:1.null 2.空字串 3.全空白字串
			if (!StringUtils.hasText(item.getId())) {
				System.out.println("Id 錯誤!!!!!");
				return;
			}
			// 檢查 userName，不得為:1.null 2.空字串 3.全空白字串
			if (!StringUtils.hasText(item.getUserName())) {
				System.out.println("user name 錯誤!!!!!");
				return;
			}
			// 檢查 userAge，不得 <= 0
			if (item.getUserAge() <= 0) {
				System.out.println("user age 錯誤!!!!!");
				return;
			}
			// 蒐集id: 主要用於後續可以一次檢查所有的 id 是否已存在於資料庫
			idList.add(item.getId());

		}
		// 檢查蒐集的 id 是否已存在
//		boolean res = personInfoDao.existsByIdIn(idList);
		// 只要 idList 中的 任一個 id 存在於資料庫，就會回傳 true
		if (personInfoDao.existsByIdIn(idList)) {
			System.out.println("Id 已存在!!!!!");
			return;
		}
		// 將資料存進資料庫
		personInfoDao.saveAll(personInfoList);
		System.out.println("新增資訊成功!!!!!");
	}

	@Override
	public void findByAgeGreaterThan(int age) {
		if(age < 0) {
			System.out.println("年齡條件不得為負數");
			return;
		}		
		List<PersonInfo> list = personInfoDao.getByAgeGreaterThan(age);
		for(PersonInfo item: list) {
			System.out.println("id: " + item.getId() + "; user_name: " + item.getUserName());
		}
	}
	
	// 回家作業，專題3的5、6、7、8、10 "
	// 5，找出年紀小於等於輸入條件的資訊，由小到大排序
	@Override
	public void getByAgeLessThanEqual(int age) {
		if(age < 0) {
			System.out.println("年齡條件不得為負數");
			return;
		}		
		List<PersonInfo> list = personInfoDao.getByAgeLessThanEqual(age);
		for(PersonInfo item: list) {
			System.out.println("id: " + item.getId() + "; user_name: " + item.getUserName() +
					"; user_age: " + item.getUserAge() + "; city: " + item.getCity());
		}
	}
	
	// 6，找出年紀小於輸入條件1 或大於輸入條件2的資訊
	@Override
	public void findByUseAgeLessThanOrGreaterThan(int age1, int age2) {
		if(age1 < 0 || age2 < 0) {
			System.out.println("年齡條件不得為負數");
			return;
		}		
		List<PersonInfo> list = personInfoDao.findByUseAgeLessThanOrGreaterThan(age1,age2);
		for(PersonInfo item: list) {
			System.out.println("id: " + item.getId() + "; user_name: " + item.getUserName() +
					"; user_age: " + item.getUserAge() + "; city: " + item.getCity());
		}
	}
	
	// 7，找出年紀介於2個數字之間(有包含)的資訊，以年齡由大到小排序，只取前3筆資料
	public void findByUserAgeBetweenOrderBy(int age1,int age2) {
		if(age1 < 0 || age2 < 0) {
			System.out.println("年齡條件不得為負數");
			return;
		}
		List<PersonInfo> list = personInfoDao.findByUserAgeBetweenOrderBy(age1,age2);
		for(PersonInfo item: list) {
			System.out.println("id: " + item.getId() + "; user_name: " + item.getUserName() +
					"; user_age: " + item.getUserAge() + "; city: " + item.getCity());
		}		
	}
	
	// 8，找出年紀大於輸入條件以及 city 包含某個特定字的資訊
	public void findByCityLike(String str) {
		if (!StringUtils.hasText(str)){
			System.out.println("要搜尋的字不得為null或空白!!!!!");
			return;
		}
		List<PersonInfo> list = personInfoDao.findByCityLike(str);
		for(PersonInfo item: list) {
			System.out.println("id: " + item.getId() + "; user_name: " + item.getUserName() +
					"; user_age: " + item.getUserAge() + "; city: " + item.getCity());
		}		
	}
	
	// 10，使用 in 找出特定幾個 city
	public void findByCityIn(List<String> strList) {
		if (CollectionUtils.isEmpty(strList)) {
			System.out.println("搜尋資訊不得為空");
			return;
		}		
		List<PersonInfo> list = personInfoDao.findByCityIn(strList);
		for(PersonInfo item: list) {
			System.out.println("city: " + item.getCity());
		}		
	}
	
	
	
}
