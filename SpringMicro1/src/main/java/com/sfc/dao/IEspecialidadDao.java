package com.sfc.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sfc.model.Especialidad;
import com.sfc.model.Medico;
import com.sfc.model.Paciente;

@Repository
public interface IEspecialidadDao extends JpaRepository<Especialidad,Integer>{

}
