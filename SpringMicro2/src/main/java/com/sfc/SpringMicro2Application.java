package com.sfc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

//para tokens desde gateway zuul
@EnableResourceServer

@EnableEurekaClient
@SpringBootApplication
public class SpringMicro2Application{
	//	extends SpringBootServletInitializer{


	public static void main(String[] args) {
		SpringApplication.run(SpringMicro2Application.class, args);
	}


	//para generar war
	//	@Override
	//	protected SpringApplicationBuilder configure(SpringApplicationBuilder appBuilder) {
	//		return appBuilder.sources(SpringMicro2Application.class);
	//	}


	// -------------------Hash de passWords 
	//Para poder usar Esta función! (AUTOWIRED necesita instancia)
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	//	@Autowired
	//	private BCryptPasswordEncoder bcrypt;
	//-------------------Hash de passWords 
}
