package com.nse.option;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class OptionApplication {

	public static void main(String[] args)
	{
		SpringApplication.run(OptionApplication.class, args);
	}

}