package com.sfc.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.repository.query.Param;

import com.sfc.dto.ConsultaListaExamenDto;
import com.sfc.dto.ConsultaResumenDto;
import com.sfc.dto.FiltroConsultaDto;
import com.sfc.model.Consulta;


public interface IConsultaService extends ICRUD<Consulta> {

	Consulta registrarTransaccional(ConsultaListaExamenDto dto);
	
	List<Consulta> buscar(FiltroConsultaDto filtro);
	List<Consulta> buscarFecha(FiltroConsultaDto filtro);
	List<ConsultaResumenDto> listarResumen();

	byte[] generarReporte();
}
