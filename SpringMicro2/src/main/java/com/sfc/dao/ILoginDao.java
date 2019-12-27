package com.sfc.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.sfc.model.Usuario;

public interface ILoginDao extends JpaRepository<Usuario, Integer>{
	
	@Query("FROM Usuario u where u.username = :nombre")
	Usuario verificarUsarioPorNombre(@Param(value = "nombre") String userName);
	
	@Transactional
	@Modifying//porque es mod SQL DML
	@Query("UPDATE Usuario us SET us.password = :clave WHERE us.username = :nombre")
	void cambiarClave(@Param("clave") String clave, @Param("nombre") String nombre) throws Exception;
	

}
