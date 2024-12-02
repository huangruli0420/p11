package com.example.practice11.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.practice11.entity.Meal;


@Repository // 將此界面交由 SpringBoot 託管成 Repository 類
public interface MealDao extends JpaRepository<Meal, String> {
	// Meal: MealDao 要操作的 class
	// String: 在類別 Meal 的所有屬性中，有加上 @Id 的屬性之資料型態(在這只有單一屬性有加上 @Id)

	// 根據餐點名稱(name, PK)來修改價格
	// 回傳值型態是 int，主要用於可以回傳的筆數:
	// 0表示沒有資料更新成功，1表示成功(因為 name 是 PK，至多只會有一筆更新成功)
	// @Query: JPA 中撰寫 SQL 語法用
	// SQL 語法中的?表示定位符號，?後面的數字是方法 updatePrice()中的參數位置，從1開始
	// ?1 表示把方法 updatePrice 的 第一個參數 name 的值帶入到 SQL語法中
	// nativeQuery = true 時，語法中的表和欄位名稱是使用
	//  									資料庫中資料表(@Table 後面的名字)和欄位(@Column 中的名字)的名稱
	// JPQL: insert/update/delete 要求要加上 @Transactional(事務，只有全成功或全失敗) 和 @Modifying
	// @Transactional import 的 library， javax 和 springframework 都可以使用
	//      兩者差異可參照 PPT spring boot_02-2 @Transactional 部分

	@Modifying
	@Transactional
	@Query(value = "update meal set price = ?2 where name = ?1", nativeQuery = true)
	public int updatePrice(String name, int price);

	// JPQL，nativeQuery 預設是 false(不寫也等同於預設)
	// nativeQuery = false 時，語法中的表和欄位名稱各是操作 Entity class 名稱和屬性變數名稱
	// 在此 SQL 語法中，表的名稱是用 Entity 名稱 Meal
	// 欄位名稱是屬性變數名稱 name 和 price(剛好 Meal 中的屬性變數名稱與欄位名稱相同)
	@Modifying
	@Transactional
	@Query(value = "update Meal set price = ?2 where name = ?1", nativeQuery = false)
	public int updatePrice1(String name, int price);
	
	//上課練習，查詢特定餐點價格，和findAll()
	@Query(value = "select price from meal where name = ?1", nativeQuery = true)
	public Integer findByName(String name);
	@Query(value = "select * from meal where name = ?1", nativeQuery = true)
	public Meal findByName1(String name);
	@Query(value = "select * from meal", nativeQuery = true)
	public List<Meal> getAll();
	}

