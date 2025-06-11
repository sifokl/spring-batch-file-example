package com.batch.foobarquix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan("com.batch.foobarquix.config")

public class FoobarquixApplication {

	public static void main(String[] args) {
		SpringApplication.run(FoobarquixApplication.class, args);
	}

}
