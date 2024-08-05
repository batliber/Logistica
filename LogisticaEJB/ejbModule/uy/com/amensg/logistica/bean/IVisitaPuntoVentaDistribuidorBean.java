package uy.com.amensg.logistica.bean;

import jakarta.ejb.Remote;

import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.PuntoVenta;
import uy.com.amensg.logistica.entities.Usuario;
import uy.com.amensg.logistica.entities.VisitaPuntoVentaDistribuidor;

@Remote
public interface IVisitaPuntoVentaDistribuidorBean {

	public MetadataConsultaResultado list(MetadataConsulta metadataConsulta);

	public Long count(MetadataConsulta metadataConsulta);

	public VisitaPuntoVentaDistribuidor getById(Long id);
	
	public VisitaPuntoVentaDistribuidor getLastByPuntoVentaDistribuidor(Usuario distribuidor, PuntoVenta puntoVenta);
	
	public VisitaPuntoVentaDistribuidor save(VisitaPuntoVentaDistribuidor visitaPuntoVentaDistribuidor);
	
	public void remove(VisitaPuntoVentaDistribuidor visitaPuntoVentaDistribuidor);
	
	public void update(VisitaPuntoVentaDistribuidor visitaPuntoVentaDistribuidor);
	
	public void crearVisita(Usuario distribuidor, PuntoVenta puntoVenta, Long loggedUsuarioId);
	
	public void crearVisitas(
		Usuario distribuidor, String observaciones, MetadataConsulta metadataConsulta, Long loggedUsuarioId
	);
	
	public void crearVisitasPermanentes(
		Usuario distribuidor, String observaciones, MetadataConsulta metadataConsulta, Long loggedUsuarioId
	);
	
	public void crearVisitasPorSubLotes(
		Usuario distribuidor, String observaciones, MetadataConsulta metadataConsulta, Long loggedUsuarioId
	);

	public String exportarAExcel(MetadataConsulta metadataConsulta, Long loggedUsuarioId);
}