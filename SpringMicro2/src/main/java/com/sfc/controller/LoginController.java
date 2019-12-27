package com.sfc.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sfc.model.ResetToken;
import com.sfc.model.Usuario;
import com.sfc.service.ILoginService;
import com.sfc.service.IResetTokenService;
import com.sfc.util.EmailService;
import com.sfc.util.Mail;


@RestController
@RequestMapping("/login")
public class LoginController {

	@Autowired
	private ILoginService service;

	@Autowired
	private IResetTokenService tokenService;

	@Autowired
	private EmailService emailService;

	@Autowired
	private BCryptPasswordEncoder bcrypt;

	
	//ya que el servicio no recibe JSon!!
	@PostMapping(value = "/enviarCorreo", consumes = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> enviarCorreo(@RequestBody String correo) {
		String rpta = "0";
		try {
			Usuario us = service.verificarNombreUsuario(correo);
			if(us != null && us.getIdUsuario() > 0) {
				// se crea el token
				ResetToken token = new ResetToken();
				token.setToken(UUID.randomUUID().toString());//56465454
				token.setUsuario(us);
				token.setExpiracion(10);
				tokenService.guardar(token);

				Mail mail = new Mail();				
				mail.setFrom("MedicaApp@spring.com");
				mail.setTo("santiceron023@gmail.com");
				mail.setSubject("Recuperar Contraseña MedicaApp");
				
				////JAVA COLLECTION AND MAP!!!!!!!!!
				Map<String,Object> model = new HashMap<>();
				String url = "http://localhost:4200/recuperar/" + token.getToken();
				model.put("user", token.getUsuario().getUsername());
				model.put("resetUrl", url);
				mail.setModel(model);
				try {
					this.emailService.sendEmail(mail);				
					rpta="1";								
				} catch (Exception e) {
					rpta = token.getToken();
				}
			}
			//de más especifica a más general
		} catch (Exception e) {
			return new ResponseEntity<String>(rpta, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<String>(rpta, HttpStatus.OK);

	}

	@GetMapping(value = "/restablecer/verificar/{token:.*}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Integer> restablecerClave(@PathVariable("token") String token) {
		int rpta = 0;
		try {
			if (token != null && !token.isEmpty()) {
				ResetToken reseytToken = tokenService.findByToken(token);
				if (reseytToken != null && reseytToken.getId() > 0) {
					if (!reseytToken.isExpirado()) {
						rpta = 1;
					}
				}
			}
		} catch (Exception e) {
			return new ResponseEntity<Integer>(rpta, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Integer>(rpta, HttpStatus.OK);
	}

	@PostMapping(value = "/restablecer/{token:.*}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<Integer> restablecerClave(@PathVariable("token") String token, @RequestBody String clave) {
		int rpta = 0;
		try {
			ResetToken rt = tokenService.findByToken(token);
			String claveHash = bcrypt.encode(clave);
			rpta = service.CambiarClave(claveHash, rt.getUsuario().getUsername());
			tokenService.eliminar(rt);
		} catch (Exception e) {
			return new ResponseEntity<Integer>(rpta, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Integer>(rpta, HttpStatus.OK);
	}
}
