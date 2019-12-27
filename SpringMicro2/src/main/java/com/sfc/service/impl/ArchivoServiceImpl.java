package com.sfc.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sfc.dao.IArchivoDao;
import com.sfc.model.Archivo;
import com.sfc.service.IArchivoService;

@Service
public class ArchivoServiceImpl implements IArchivoService{

	@Autowired
	private IArchivoDao dao;

	@Override
	public int guardar(Archivo archivo) {
		Archivo ar = dao.save(archivo);
		return ar.getIdArchivo() > 0 ? 1 : 0;
	}

	@Override
	public byte[] leerArchivo(Integer idArchivo) {
		Optional<Archivo> opt =  dao.findById(idArchivo);
		return opt.isPresent() ? opt.get().getValue() : new Archivo().getValue();
	}


}
