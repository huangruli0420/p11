package com.example.practice11;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.practice11.service.ifs.AtmService;

@SpringBootTest
public class AtmServiceTest {

	@Autowired
	private AtmService atmService;

	@Test
	public void addInfoTest() {
		atmService.addInfo("A01", "AA12345678", 0);
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		// SALT，同樣的字串，每次加密出來的結果都不同
//		for (int i = 0; i <= 2; i++) {
//			System.out.println("AA123");
//		}
	}
	
	@Test
	public void getBalance() {
		atmService.getBalance("A01", "AA12345678");
	}
	

}
