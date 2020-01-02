package com.sfc.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sfc.dao.IConsultaExamenDao;
import com.sfc.model.ConsultaExamen;
import com.sfc.service.IConsultaExamenService;


@Service
public class ConsultaExamenExamenServiceImpl implements IConsultaExamenService {

	@Autowired
	IConsultaExamenDao dao;

	@Override
	public List<ConsultaExamen> listarExamenesPorConsulta(Integer idConsulta) {
		return dao.listarExamenesPorConsulta(idConsulta);
	}

	

}
