package com.sfc.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//tipo d error personalizado, 
//tiene codigo estado porque se devuelve en los rest
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ModeloNotFoundException extends RuntimeException {

	public ModeloNotFoundException(String mensaje){
		//para mensaje de exception
		super(mensaje);
	}
	
}
