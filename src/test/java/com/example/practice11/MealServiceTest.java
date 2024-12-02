package com.example.practice11;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.practice11.entity.Meal;
import com.example.practice11.service.ifs.MealService;

@SpringBootTest
public class MealServiceTest {

	@Autowired
	private MealService mealService;
	
	@Test
	public void addMealIsTest() {
		List<Meal> list = new ArrayList<>();
		// 使用帶有參數的建構方法就可以不用寫setName()和setPrice()，也可以進一步使用匿名直接將Meal m1等號右側整行寫進mealList.add()裡面
//		Meal m1 = new Meal("Beef",200);
//		m1.setName("Beef");
//		m1.setPrice(200);
//		Meal m2 = new Meal("Pork",180);
//		m1.setName("Pork");
//		m1.setPrice(180);
		
		list.add(new Meal("Beef",200));
		list.add(new Meal("Pork",180));
		
		mealService.addMeals(list);
	}
	
	@Test
	public void updatePriceTest () {
		mealService.updatePrice("beef",250);
	}
	
	// 11/22上課練習，findaByName和findAll
	@Test
	public void findByNameTest (){
//		mealService.findByName("beef");
		mealService.findByName("fish");
	}	
	@Test
	public void findAll() {
		mealService.findAll();
	}
}
