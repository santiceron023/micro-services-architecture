package com.sfc.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sfc.model.Archivo;
import com.sfc.service.IArchivoService;

@RestController
@RequestMapping("/archivo")
public class ArchivoController {

	@Autowired
	IArchivoService servicio;


	@GetMapping()
	@ResponseStatus(HttpStatus.OK)
	public byte[] leerArchivo() {
		return this.servicio.leerArchivo(1);
	}


	@PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	@ResponseStatus(HttpStatus.CREATED)
	public int crear (@RequestParam("file") MultipartFile file) throws IOException{
		int rta=0;
		Archivo ar =  new Archivo();
		ar.setFilename(file.getOriginalFilename());
		ar.setValue(file.getBytes());
		ar.setFiletype(file.getContentType());
		rta = servicio.guardar(ar);
		return rta;
	}

}

