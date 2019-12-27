package com.sfc.controller;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.sfc.model.Medico;
import com.sfc.service.IMedicoService;

@RestController
@RequestMapping("/medicos")
public class MedicoController {

	@Autowired
	IMedicoService servicio;
	
	//lógica programación
	@GetMapping
	public ResponseEntity<List<Medico>> listar(){		
		return new ResponseEntity< List<Medico> >(servicio.listar(),HttpStatus.OK);
	}


//	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
	public Resource<Medico> listarPorId(@PathVariable("id") Integer id){

		Medico pac = servicio.listarPorId(id);

		if (pac == null) {
			//tipo de error personalizado
			throw new ModeloNotFoundException("Id no encontrado : " + id);
		}

		Resource<Medico> resource = new Resource<Medico>(pac);
		//  /pacientes/{id}   <- va a ese recurso y le pone el id
		ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).listarPorId(id));
		//agregar el link al recurso
		resource.add(linkTo.withRel("paciente-resource"));
		//https://www.adictosaltrabajo.com/2013/12/02/spring-hateoas/
		return resource;
	}

	//	@PostMapping
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Medico> registrar(@Valid @RequestBody Medico pac) {

		Medico pacSaved = servicio.registrar(pac);
		//devuelve id
		URI uriLocation = ServletUriComponentsBuilder.fromCurrentRequest().
				path("/{id}").buildAndExpand(pacSaved.getIdMedico()).toUri();

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
	public Medico modificar(@RequestBody Medico pac) {
		return servicio.modificar(pac);
	}





}
