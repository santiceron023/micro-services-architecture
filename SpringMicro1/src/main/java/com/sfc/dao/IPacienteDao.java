package com.sfc.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sfc.model.Paciente;

@Repository
public interface IPacienteDao extends JpaRepository<Paciente,Integer>{

}
