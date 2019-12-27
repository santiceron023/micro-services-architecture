package com.sfc.model;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


//para usarla en defnicion de otra cosa
@Embeddable 
public class ConsultaExamenPk implements Serializable {
//searilizable <- se agrega auto en @entities, pero en clase normal NO
	
	@JoinColumn(name="idExamen", nullable = false,foreignKey = @ForeignKey(name="conEx_examen") )
	@ManyToOne
	private Examen examen;
	
	@JoinColumn(name="idConsulta", nullable = false,foreignKey = @ForeignKey(name="conEx_consulta") )
	@ManyToOne
	private Consulta consulta;

	

	// al comparar objetos, compara es ref en memoria
	// se necesita evaluar el valor de los id
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((consulta == null) ? 0 : consulta.hashCode());
		result = prime * result + ((examen == null) ? 0 : examen.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ConsultaExamenPk other = (ConsultaExamenPk) obj;
		if (consulta == null) {
			if (other.consulta != null)
				return false;
		} else if (!consulta.equals(other.consulta))
			return false;
		if (examen == null) {
			if (other.examen != null)
				return false;
		} else if (!examen.equals(other.examen))
			return false;
		return true;
	}
	
	
	}
