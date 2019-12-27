package com.sfc.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;


@Entity
@Table(name = "consulta")
public class Consulta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idConsulta;

	//1 paciente * consultas
	//1 consulta 1 paceiente

	//* consultas 1 paciente, llave en el que es Many=]]
	@ManyToOne
	@JoinColumn(name = "id_paciente",nullable= false, foreignKey = @ForeignKey(name = "consulta_paciente") )
	private Paciente paciente;

	@ManyToOne
	//nombre de la columna
	@JoinColumn(name = "id_medico",nullable= false, foreignKey = @ForeignKey(name = "consulta_medico") )
	private Medico medico;

	@ManyToOne
	//foreignKey -> nombre constrain
	@JoinColumn(name = "id_especialidad",nullable= false, foreignKey = @ForeignKey(name = "consulta_especialidad"))
	private Especialidad especialidad;



	//dar formato a la fecha  ISODate 2019-10-01T05:00:00.000
	@JsonSerialize(using = ToStringSerializer.class)
	private LocalDateTime fecha;




	//Lista detalle consulta en un mismo JSON
	@OneToMany(mappedBy="consulta",cascade = {CascadeType.PERSIST,
			CascadeType.MERGE,CascadeType.REMOVE},fetch=FetchType.LAZY
			,orphanRemoval = false)

	private List<DetalleConsulta> detalleConsulta;
	//MAPEO BIDIRECCIONAL -> solo en maestro detalle
	//mapped by -> llave (nombre de atr Java) que refiere a este modelo en el detalle, NO agrega campo en DB
	//fetch -> lazy, en consultas JDBC no tare la lista poblada, para más velocidad
	//cascade -> Padre crea al hijo
	//orphan -> permite que los hijos se eliminen



	public List<DetalleConsulta> getDetalleConsulta() {
		return detalleConsulta;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idConsulta == null) ? 0 : idConsulta.hashCode());
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
		Consulta other = (Consulta) obj;
		if (idConsulta == null) {
			if (other.idConsulta != null)
				return false;
		} else if (!idConsulta.equals(other.idConsulta))
			return false;
		return true;
	}



	public void setDetalleConsulta(List<DetalleConsulta> detalleConsulta) {
		this.detalleConsulta = detalleConsulta;
	}

	public Integer getIdConsulta() {
		return idConsulta;
	}

	public void setIdConsulta(Integer idConsulta) {
		this.idConsulta = idConsulta;
	}

	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	public Medico getMedico() {
		return medico;
	}

	public void setMedico(Medico medico) {
		this.medico = medico;
	}

	public Especialidad getEspecialidad() {
		return especialidad;
	}

	public void setEspecialidad(Especialidad especialidad) {
		this.especialidad = especialidad;
	}

	public LocalDateTime getFecha() {
		return fecha;
	}

	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}

}
