package com.soulcode.Servicos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching //comente caso o redis não esteja ok ainda
@SpringBootApplication
public class ServicosApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServicosApplication.class, args);
//		System.out.println(new BCryptPasswordEncoder().encode("primavera"));
	}
}
