package com.sfc.service.impl;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class RestAuthService {

	public boolean hasAccess(String path) {
		boolean rta = false;

		String metodoRol = "";

		// /listar
		switch (path) {
		case "listar":
			metodoRol = "ADMIN";
			break;

		case "listarId":
			metodoRol = "ADMIN,USER,DBA";
			break;
		}

		String metodoRoles[] = metodoRol.split(",");

		//Acceso al contexto!!!!!!
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (!(authentication instanceof AnonymousAuthenticationToken)) {

			//información de quien inicio
			System.out.println(authentication.getName());

			for (GrantedAuthority auth : authentication.getAuthorities()) {
				String rolUser = auth.getAuthority();

				System.out.println(rolUser);

				//si algún rol está en el switch
				for (String rolMet : metodoRoles) {
					if (rolUser.equalsIgnoreCase(rolMet)) {
						rta = true;
					}
				}
			}
		}

		return rta;

	}

}
