package com.dealengine.weatherreport;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.dealengine.weatherreport.repository")
@EntityScan(basePackages = "com.dealengine.weatherreport.model")
public class WeatherReport {

	public static void main(String[] args) {
		SpringApplication.run(WeatherReport.class, args);
	}

}
