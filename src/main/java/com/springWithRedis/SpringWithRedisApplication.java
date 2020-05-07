package com.springWithRedis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class SpringWithRedisApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringWithRedisApplication.class, args);
	}

}
