package com.nse.option;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class OptionApplication  extends SpringBootServletInitializer
{

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application)
	{
		return application.sources(OptionApplication.class);
	}

	public static void main(String[] args)
	{
		SpringApplication.run(OptionApplication.class, args);
	}

}