package com.example.practice11.service.ifs;

import java.util.List;

import com.example.practice11.entity.Meal;

public interface MealService {

	public void addMeals(List <Meal> mealList);
	
	public void updatePrice(String name, int price);
	
	// 11/22上課練習，findaByName和findAll
	public void findByName(String name);

	public void findAll();
	
	
}
