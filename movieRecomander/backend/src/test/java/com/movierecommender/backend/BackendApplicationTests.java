package com.movierecommender.backend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
class BackendApplicationTests {

	@Test
	void contextLoads() {
		BackendApplication backendApplication = new BackendApplication();
		assertNotEquals(null, backendApplication);
	}

}
