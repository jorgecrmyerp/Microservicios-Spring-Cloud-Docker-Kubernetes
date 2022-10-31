package com.jgr.micro.cursos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class AbServicioCursosApplication {

	public static void main(String[] args) {
		SpringApplication.run(AbServicioCursosApplication.class, args);
	}

}
