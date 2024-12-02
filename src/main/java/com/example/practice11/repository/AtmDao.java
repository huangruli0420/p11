package com.example.practice11.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.practice11.dto.BasicInfo;
import com.example.practice11.entity.Atm;

@Repository
public interface AtmDao extends JpaRepository<Atm, String> {

	// JPQL的 insert 語法只能用於 nativeQuery = true
	// 回傳值是 int ，1表示新增成功，0表示因新增失敗
	// UPDATE 或 INSERT 或 DELETE 操作，則需要使用 @Modifying 告訴 Spring Data JPA，這是一個非查詢操作，基本都會和 Transactional 一起搭配
	@Modifying 
	@Transactional
	@Query(value = "insert into atm (account, password, balance) values (?1, ?2, ?3) ", nativeQuery = true)
	public int insert(String account, String pwd, int balance);
	
	// 等同於 JPA 原有的 findById(String account) 或是 自定義的 findByAccount(String account)
	@Query(value = "select * from atm where account = ?1", nativeQuery = true)
	public Atm getByAccount(String account);
	
	/**
	 * BasicInfo
	 * 1. 沒有被 spring boot 託管，所以要寫完整的路徑
	 * 2. 塞值的方式同使用建構方法，要透過 new 
	 * 3. SQL語法中要取得 Entity 對應的屬性值，是使用 別名.變數名稱， 不是透過getXXX 方法
	 * 4. 必須是 nativeQuery = false
	 */
	@Query(value = "select new com.example.practice11.dto.BasicInfo(a.account, a.balance) from Atm as a", 
			nativeQuery = false)
	public List<BasicInfo> getAll();
	
	//練習，存款提款，有使用@Modifying回傳值是只能是 int 或 void 
	@Modifying 
	@Transactional
	@Query(value = "update atm set balance = ?2 where account = ?1", nativeQuery = true)
	public int updateBalance(String account, int amount);
}
