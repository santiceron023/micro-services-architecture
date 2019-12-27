package com.sfc.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sfc.model.Archivo;

public interface IArchivoDao extends JpaRepository<Archivo, Integer>{

}
