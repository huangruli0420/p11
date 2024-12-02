package com.example.practice11.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.practice11.entity.NewMeal;
import com.example.practice11.entity.NewMealId;

@Repository
public interface NewMealDao extends JpaRepository<NewMeal, NewMealId>{
	// 因為 NewMeal 有多個PK(屬性上有加上@Id)，所以要透過一個 class 來管理這些PK
	// NewMealId 就是管理 newMeal 中多個PK屬性的 class，所以JpaRepository 角括號中的資料型態就變成是 NewMealId

}
