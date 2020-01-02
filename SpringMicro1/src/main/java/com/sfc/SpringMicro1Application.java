package com.sfc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;


//para que reciba tokens
@EnableResourceServer

@EnableEurekaClient
@SpringBootApplication
public class SpringMicro1Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringMicro1Application.class, args);
	}

}
