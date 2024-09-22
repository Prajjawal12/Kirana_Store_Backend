package com.kirana.register.kirana_store_register;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;

@SpringBootApplication(exclude = {
		org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class

})
public class KiranaStoreRegisterApplication {

	public static void main(String[] args) {
		SpringApplication.run(KiranaStoreRegisterApplication.class, args);
	}

	@Bean
	public CommandLineRunner checkMongoDb(MongoTemplate mongoTemplate) {
		return args -> {
			if (mongoTemplate.getDb().getName() != null) {
				System.out.println("MongoDB is up and running: " + mongoTemplate);
			} else {
				System.out.println("Failed to connect to MongoDB.");
			}
		};
	}

}
