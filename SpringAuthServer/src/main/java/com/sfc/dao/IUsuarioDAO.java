package com.sfc.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sfc.model.Usuario;

public interface IUsuarioDAO extends JpaRepository<Usuario, Integer> {
		
	//sacado desde Nombre de atributo de la clase
	Usuario findOneByUsername(String username);	
	//@query
	//bucarUsuarioPorNombre
}