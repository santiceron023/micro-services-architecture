package com.sfc.service.impl;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.sfc.dao.IConsultaDao;
import com.sfc.dao.IConsultaExamenDao;
import com.sfc.dto.ConsultaListaExamenDto;
import com.sfc.dto.ConsultaResumenDto;
import com.sfc.dto.FiltroConsultaDto;
import com.sfc.model.Consulta;
import com.sfc.service.IConsultaService;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class ConsultaServiceImpl implements IConsultaService {

	@Autowired
	IConsultaExamenDao conExaDao;

	@Autowired
	IConsultaDao conDao;
	

	//consultaDetalle
	@Override
	public Consulta registrar(Consulta con) { 
		con.getDetalleConsulta().forEach(
				detalle -> detalle.setConsulta(con) 
				);
		return conDao.save(con);
	}


	@Override
	@Transactional 
	//Transaccional -> si hay un error en alguna entidad, hace roolback de todo
	public Consulta registrarTransaccional(ConsultaListaExamenDto dto) { 

		dto.getConsulta().getDetalleConsulta().forEach(
				detalle -> detalle.setConsulta(dto.getConsulta()) 
				);
		conDao.save(dto.getConsulta());
		
		//insertar cada examen de la consulta(Ya creada)
		dto.getExamenList().forEach(
				exam -> conExaDao.registrar(
				dto.getConsulta().getIdConsulta(),exam.getIdExamen() )
				);	

		return dto.getConsulta();
	}


	@Override
	public Consulta modificar(Consulta t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void eliminar(Integer id) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Consulta> listar() {
		return conDao.findAll();
	}

	@Override
	public Consulta listarPorId(Integer id) {
		Optional<Consulta> opt = conDao.findById(id);
		return opt.isPresent() ? opt.get() : new Consulta();
	}


	@Override
	public List<Consulta> buscar(FiltroConsultaDto filtro) {
		return conDao.buscar(filtro.getDni(), filtro.getNombreCompleto());
	}


	@Override
	public List<Consulta> buscarFecha(FiltroConsultaDto filtro) {
		LocalDateTime fechaSgte = filtro.getFechaConsulta().plusDays(1);
		return conDao.buscar(filtro.getFechaConsulta(), fechaSgte);
	}




	@Override
	public List<ConsultaResumenDto> listarResumen() {
		List<ConsultaResumenDto> consulta = new ArrayList<ConsultaResumenDto>();
		
		conDao.listarResumen().forEach( x -> {
			ConsultaResumenDto item = new ConsultaResumenDto();
			item.setCantidad((int) x[0] );
			item.setFecha((String) x[1]);
			consulta.add(item);
		});
		return consulta;
	}


	@Override
	public byte[] generarReporte() {
		byte[] data= null;
		try {
			File file = new ClassPathResource("/reports/consultas.jasper").getFile();
			JasperPrint print = JasperFillManager.fillReport(file.getPath(),
					null, new JRBeanCollectionDataSource(this.listarResumen()));
			data = JasperExportManager.exportReportToPdf(print);
					
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}


	

}
