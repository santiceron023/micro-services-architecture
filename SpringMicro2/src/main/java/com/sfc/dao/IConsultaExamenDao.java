package com.sfc.dao;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sfc.model.ConsultaExamen;

public interface IConsultaExamenDao extends JpaRepository<ConsultaExamen, Integer>{


	//	permite modificar la persistencia con native query
	@Modifying
	// hace commit
	//	@Transactional
	//SQL. puedo poner :idExame o ?1, ?2
	@Query(value = "INSERT INTO consulta_examen(id_consulta, id_examen) VALUES (:idConsulta,:idExamen)"
	, nativeQuery = true)
	Integer registrar(@Param("idConsulta") Integer idConsulta,@Param("idExamen") Integer idExamen);
} 