package com.Task_Forge.Microservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.yourpackage")
public class MicroserviceApplication {

	public static void main(String[] args) {

		SpringApplication.run(MicroserviceApplication.class, args);
	}

}
