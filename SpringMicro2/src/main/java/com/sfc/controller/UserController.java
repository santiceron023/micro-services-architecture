package com.sfc.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuarios")
public class UserController {

	//desde seucrity config
	@Resource(name = "tokenServices")
//	private ConsumerTokenServices tokenServices;
	
	//para poder tomar caracteres especiales como . / etc
	@GetMapping(value = "/anular/{tokenId:.*}") 
	public void revokeToken(@PathVariable("tokenId") String token) {
//		tokenServices.revokeToken(token);				
	}
}
