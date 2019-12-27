package com.sfc.service;

import com.sfc.model.Usuario;

public interface ILoginService {
	
	Usuario verificarNombreUsuario(String Username) throws Exception;
	int CambiarClave(String clave, String nombre) throws Exception;

}
