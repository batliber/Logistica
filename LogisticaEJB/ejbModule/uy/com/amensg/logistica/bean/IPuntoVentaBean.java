package uy.com.amensg.logistica.bean;

import java.util.Collection;

import javax.ejb.Remote;

import uy.com.amensg.logistica.entities.Barrio;
import uy.com.amensg.logistica.entities.Departamento;
import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.PuntoVenta;

@Remote
public interface IPuntoVentaBean {

	public Collection<PuntoVenta> list();
	
	public Collection<PuntoVenta> list(Long usuarioId);
	
	public Collection<PuntoVenta> listMinimal();
	
	public Collection<PuntoVenta> listMinimalCreatedByUsuarioId(Long usuarioId);
	
	public Collection<PuntoVenta> listMinimalCreatedORAssignedByUsuarioId(Long usuarioId);
	
	public MetadataConsultaResultado list(MetadataConsulta metadataConsulta);

	public Long count(MetadataConsulta metadataConsulta);
	
	public Collection<PuntoVenta> listByDepartamento(Departamento departamento);
	
	public Collection<PuntoVenta> listByDepartamento(Departamento departamento, Long usuarioId);
	
	public Collection<PuntoVenta> listMinimalByDepartamento(Departamento departamento);
	
	public Collection<PuntoVenta> listByBarrio(Barrio barrio);
	
	public Collection<PuntoVenta> listByBarrio(Barrio barrio, Long usuarioId);
	
	public Collection<PuntoVenta> listMinimalByBarrio(Barrio barrio);
	
	public PuntoVenta getById(Long id);
	
	public void save(PuntoVenta puntoVenta);
	
	public void remove(PuntoVenta puntoVenta);
	
	public void update(PuntoVenta puntoVenta);
}