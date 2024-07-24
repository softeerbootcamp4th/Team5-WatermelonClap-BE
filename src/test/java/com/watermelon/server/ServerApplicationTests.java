package com.watermelon.server;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;


@EnableScheduling
@SpringBootTest
class ServerApplicationTests {


	@Autowired
	private ApplicationContext applicationContext;
//	@Test
//	@DisplayName("ApplyTokenProvider 정상 등록 확인")
//	public void applyTokenProvider() {
//		Assertions.assertTrue(applicationContext.containsBean("applyTokenProvider"));
//
//	}

}
