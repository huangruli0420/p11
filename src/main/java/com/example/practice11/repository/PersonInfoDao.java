package com.example.practice11.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.practice11.entity.PersonInfo;

@Repository
public interface PersonInfoDao extends JpaRepository<PersonInfo, String> {

	// 自定義 JPA 方法
	// 關鍵字 In 表示方法中的參數是個集合(Collection、List、Set、Iterator)
	public boolean existsByIdIn(List<String> idList);

	// JPA 自訂方法規則
	// 1. 小駝峰命名方式
	// 2. By後面接續的是屬性變數名稱
	// 3. 關鍵字: exists(查詢資料是否已存在)，find (等同於 SQL 的 select)，其餘的可參考JPA文件
	public boolean existsByUserName(String name);

	// SQL 語法中的 * 只適用於 nativeQuery = true
	@Query(value = "select * from person_info where user_age > ?1", nativeQuery = true)
	public List<PersonInfo> getByAgeGreaterThan(int age);

	// nativeQuery = false，Table名稱改為Entity名稱，欄位名稱改為屬性變數名稱，可以透過 * 改為別名撈取整個Entity
	@Query(value = "select P from PersonInfo as P where userAge > ?1", nativeQuery = false)
	public List<PersonInfo> getByAgeGreaterThan1(int age);

	// 自定義JPA方法，find 後面的 userAge 是屬性變數名稱
	public List<PersonInfo> findByuserAgeGreaterThan(int age);

	// 回家作業5 6 7 8 10
	// 5.
	// I. JPA寫法: By後面的UserAge是屬性變數名稱
	public List<PersonInfo> findByUserAgeLessThanEqualOrderByUserAgeAsc(int age);

	// II. SQL使用位置符號 (asc 是預設，可省略不寫)
	@Query(value = "select * from person_info where user_age <= ?1 order by user_age asc", nativeQuery = true)
	public List<PersonInfo> getByAgeLessThanEqual(int age);

	// III. SQL使用@Param (ASC 是預設，可省略不寫)
	// 使用 @Param 時，SQL語法要使用冒號(:)來指定參數，冒號後面的變數名稱是 @Param 中的字串
	@Query(value = "select * from person_info where user_age <= :inputAge order by user_age asc", nativeQuery = true)
	public List<PersonInfo> getByAgeLessThanEqual1(@Param("inputAge") int age);

	// 6.
	// II. SQL使用位置符號 (asc 是預設，可省略不寫)
	@Query(value = "select * from person_info where user_age <= ?1 or user_age >= ?2", nativeQuery = true)
	public List<PersonInfo> findByUseAgeLessThanOrGreaterThan(int age1, int age2);

	// III. SQL使用@Param (ASC 是預設，可省略不寫)， 用註解符號可以強制斷行，不會被自動排版(ctrl shft F)改掉
	// Query語法有斷行要記得每行之間加上空格才不會報錯
	@Query(value = "select id, user_name, user_age,city from person_info" + " where user_age <= :inputAge1"
			+ " or user_age >= :inputAge2", nativeQuery = true)
	public List<PersonInfo> getByAgeGreaterThanEqual(//
			@Param("inputAge1") int age1, //
			@Param("inputAge2") int age2);

	// 7. Limit 只能用在 nativeQuery = true 的時候
	@Query(value = "select * from person_info where user_age between ?1 and ?2 order by user_age desc limit 3", nativeQuery = true)
	public List<PersonInfo> findByUserAgeBetweenOrderBy(int age1, int age2);

	// 8. 有特殊符號 % (百分比，萬用字元)，可以用單引號框起來，用正式表達式就不需要百分比符號
	@Query(value = "select * from person_info where city like '%?1%' ", nativeQuery = true)
	public List<PersonInfo> findByCityLike(String str);
	
	@Query(value = "select * from person_info where city like '%?1%' or city like '%?2%' ", nativeQuery = true)
	public List<PersonInfo> findByCityLike1(String str1, String str2);
	// 因為邏輯運算符號(| 或 &)不屬於原本 SQL 語法中認定的字符，所以要用 concat 將之與參數串接
	// concat 是將方法中的所有參數串接成字串，所以邏輯符號記得要加上單引號(SQL 語法中的字串是用單引號)
	@Query(value = "select * from person_info where city regexp concat (?1, '|', ?2) ", nativeQuery = true)
	public List<PersonInfo> findByCityRegexp(String str1, String str2);

	// 10. 因為不知道是特定幾個的所謂幾個數量是多少，所以用List，在SQL語法中要用()把多個條件裝起來
	@Query(value = "select * from person_info where city in (?1)", nativeQuery = true)
	public List<PersonInfo> findByCityIn(List<String> strList);
}
