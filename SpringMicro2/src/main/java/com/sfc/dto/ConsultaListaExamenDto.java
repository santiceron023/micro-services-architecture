package com.sfc.dto;

import java.util.List;

import com.sfc.model.Consulta;
import com.sfc.model.Examen;

public class ConsultaListaExamenDto {

	private Consulta consulta;
	private List<Examen> examenList;
	
	public Consulta getConsulta() {
		return consulta;
	}
	public void setConsulta(Consulta consulta) {
		this.consulta = consulta;
	}
	public List<Examen> getExamenList() {
		return examenList;
	}
	public void setExamenList(List<Examen> examenList) {
		this.examenList = examenList;
	}
	
	
	
	
}
