package com.pingpong.jlab.pingpong;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@EnableScheduling
@SpringBootApplication
public class PingpongApplication {

	public static void main(String[] args) {
		SpringApplication.run(PingpongApplication.class, args);
	}

}