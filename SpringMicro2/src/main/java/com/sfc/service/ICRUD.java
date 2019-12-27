package com.sfc.service;

import java.util.List;


//operaciones comunes
public interface ICRUD <T> {
	T registrar(T t);
	T modificar(T t);
	void eliminar(Integer id);
	List<T> listar();
	T listarPorId(Integer id);
}
