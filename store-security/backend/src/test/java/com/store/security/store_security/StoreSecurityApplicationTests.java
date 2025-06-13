package com.store.security.store_security;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties =
		{
				"spring.jpa.hibernate.ddl-auto=create-drop"
		})
public class StoreSecurityApplicationTests {



}
