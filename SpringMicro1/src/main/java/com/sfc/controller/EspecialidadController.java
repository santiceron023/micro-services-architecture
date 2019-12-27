package com.sfc.controller;
import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.sfc.exceptions.ModeloNotFoundException;
import com.sfc.model.Especialidad;
import com.sfc.service.IEspecialidadService;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/especialidades")
public class EspecialidadController {

	@Autowired
	IEspecialidadService servicio;

	@GetMapping
	public ResponseEntity<List<Especialidad>> listar(){		
		return new ResponseEntity< List<Especialidad> >(servicio.listar(),HttpStatus.OK);
	}


	//	@GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(method=RequestMethod.GET,value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
	public Resource<Especialidad> listarPorId(@PathVariable("id") Integer id){

		Especialidad pac = servicio.listarPorId(id);

		if (pac == null) {
			//tipo de error personalizado
			throw new ModeloNotFoundException("Id no encontrado : " + id);
		}

		Resource<Especialidad> resource = new Resource<Especialidad>(pac);
		//  /pacientes/{id}   <- va a ese recurso y le pone el id
		ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).listarPorId(id));
		//agregar el link al recurso
		resource.add(linkTo.withRel("paciente-resource"));
		//https://www.adictosaltrabajo.com/2013/12/02/spring-hateoas/
		return resource;
	}

	//	@PostMapping
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Especialidad> registrar(@Valid @RequestBody Especialidad pac) {

		Especialidad pacSaved = servicio.registrar(pac);
		//devuelve id
		URI uriLocation = ServletUriComponentsBuilder.fromCurrentRequest().
				path("/{id}").buildAndExpand(pacSaved.getIdEspecialidad()).toUri();

		return ResponseEntity.created(uriLocation).build();
	}


	//	@DeleteMapping(value = "/{id}")
	@RequestMapping(method=RequestMethod.DELETE,value = "/{id}")
	public void eliminarPorId(@PathVariable("id") Integer id){

		if(servicio.listarPorId(id) != null) {
			servicio.eliminar(id);
		}else {
			throw new ModeloNotFoundException("ïs no encontrado : " + id);
		}
	}

	//	@PutMapping
	@RequestMapping(method=RequestMethod.PUT)
	public Especialidad modificar(@RequestBody Especialidad pac) {
		return servicio.modificar(pac);
	}





}
