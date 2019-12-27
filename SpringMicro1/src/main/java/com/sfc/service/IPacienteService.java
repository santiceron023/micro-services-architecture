package com.sfc.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sfc.model.Paciente;

public interface IPacienteService extends ICRUD<Paciente> {

	Page<Paciente> listarPageable(Pageable pageable);
	
}
