package com.sfc.service;


import java.util.List;

import com.sfc.model.ConsultaExamen;


public interface IConsultaExamenService {
	
	public List<ConsultaExamen> listarExamenesPorConsulta(Integer idConsulta);

}
