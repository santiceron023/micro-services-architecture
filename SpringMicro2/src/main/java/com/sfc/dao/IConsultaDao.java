package com.sfc.dao;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sfc.model.Consulta;

public interface IConsultaDao extends JpaRepository<Consulta, Integer>{
	
	//JPQL
	@Query("From Consulta con where con.paciente.dni = :dni or LOWER(con.paciente.nombres) like %:nombreCompleto% or LOWER(con.paciente.apellidos) like %:nombreCompleto%")
	List<Consulta> buscar(@Param("dni") String dni,@Param("nombreCompleto") String nombreCompleto);
	
	//JPQL
	@Query("From Consulta con where con.fecha between :fechaConsulta and :fechaSgte")
	List<Consulta> buscar(@Param("fechaConsulta") LocalDateTime fechaConsulta,@Param("fechaSgte") LocalDateTime fechaSgte);
	
	
	//cantidad Fecha --------PROCEDIMIENTO ALMACENADO------------
	//[2, 10/20/1993]
	@Query(value="select * from fn_listarResumen()",nativeQuery = true)
	List<Object[]> listarResumen();


}
