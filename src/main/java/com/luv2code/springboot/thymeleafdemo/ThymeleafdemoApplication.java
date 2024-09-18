package com.luv2code.springboot.thymeleafdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class ThymeleafdemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ThymeleafdemoApplication.class, args);
	}

}
