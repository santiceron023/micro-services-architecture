package com.sfc.controller;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.sfc.exceptions.ModeloNotFoundException;
import com.sfc.model.Paciente;
import com.sfc.service.IPacienteService;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

	@Autowired
	IPacienteService servicio;

	@GetMapping
	public ResponseEntity<List<Paciente>> listar(){		
		return new ResponseEntity< List<Paciente> >(servicio.listar(),HttpStatus.OK);
	}
	
	
	@GetMapping(value="/pageable")
	public ResponseEntity<Page<Paciente>> listarPageable(Pageable pageable){		
		
		return 
				new ResponseEntity<Page<Paciente>> (servicio.listarPageable(pageable),HttpStatus.OK);
	}


	//	@GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(method=RequestMethod.GET,value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
	public Resource<Paciente> listarPorId(@PathVariable("id") Integer id){

		Paciente pac = servicio.listarPorId(id);

		if (pac == null) {
			//tipo de error personalizado
			throw new ModeloNotFoundException("Id no encontrado : " + id);
		}

		Resource<Paciente> resource = new Resource<Paciente>(pac);
		//  /pacientes/{id}   <- va a ese recurso y le pone el id
		ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).listarPorId(id));
		//agregar el link al recurso
		resource.add(linkTo.withRel("paciente-resource"));
		//https://www.adictosaltrabajo.com/2013/12/02/spring-hateoas/
		return resource;
	}

	//	@PostMapping
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Paciente> registrar(@Valid @RequestBody Paciente pac) {

		Paciente pacSaved = servicio.registrar(pac);
		//@RequestMapping("/pacientes")
		URI uriLocation = ServletUriComponentsBuilder.fromCurrentRequest().
				//devuelve id
				path("/{id}").buildAndExpand(pacSaved.getIdPaciente()).toUri();

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
	@RequestMapping(method=RequestMethod.PUT,consumes = MediaType.APPLICATION_JSON_VALUE ,produces = MediaType.APPLICATION_JSON_VALUE)
	public Paciente modificar(@RequestBody Paciente pac) {
		return servicio.modificar(pac);
	}





}
