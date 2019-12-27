package com.sfc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;


@Entity
@Table(name = "paciente")
public class Paciente {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private Integer idPaciente;
	
	@Column(nullable = true, length = 150)
	@ApiModelProperty(notes ="maximo 150 caracteres" )
	@Size(min=3,message = "direccion minimo 3 caracteres")
	private String direccion;
	
	@Column(nullable = false, length = 8)
	@ApiModelProperty(notes ="maximo 8 caracteres" )
	@Size(min=3,max=8,message = "dni 3-8 caracteres")
	private String dni;
	
	@Column(nullable = false, length = 70)
	@ApiModelProperty(notes ="maximo 70 caracteres" )
	@Size(min=3,message = "nombre minimo 3 caracteres")
	private String nombres;
	
	@Column(nullable = false, length = 70)
	@Size(min=3,message = "apellidos minimo 3 caracteres")
	private String apellidos;
	
	@Column(nullable = true, length = 9)
	@Size(min=3,max=9,message = "telefono 3-9 caracteres")
	private String telefono;
	
	@Column(nullable = true, length = 55)
	private String email;
	
	
	
	public Integer getIdPaciente() {
		return idPaciente;
	}
	public void setIdPaciente(Integer idPaciente) {
		this.idPaciente = idPaciente;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getDni() {
		return dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}
	public String getNombres() {
		return nombres;
	}
	public void setNombres(String nombres) {
		this.nombres = nombres;
	}
	public String getApellidos() {
		return apellidos;
	}
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
