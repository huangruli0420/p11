package com.example.practice11.service.ifs;

import java.util.List;

import com.example.practice11.entity.PersonInfo;

public interface PersonInfoService {

	public void createInfo(List<PersonInfo> personInfoList);
	
	public void createInfo1(List<PersonInfo> personInfoList);
	
	public void findByAgeGreaterThan(int age);
	
	//回家作業，專題3的5、6、7、8、10
	public void getByAgeLessThanEqual(int age);
	
	public void findByUseAgeLessThanOrGreaterThan(int age1, int age2);
	
	public void findByUserAgeBetweenOrderBy(int age1,int age2);
	
	public void findByCityLike(String str);
	
	public void findByCityIn(List<String> strList);
}
