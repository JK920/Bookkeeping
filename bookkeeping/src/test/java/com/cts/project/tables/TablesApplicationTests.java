package com.cts.project.tables;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.cts.project.bookkeeping.TablesApplication;


@SpringBootTest(classes = TablesApplication.class)
class TablesApplicationTests {

	@Test
	void contextLoads() {
		assertTrue(true);
	}

}
