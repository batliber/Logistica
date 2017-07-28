package uy.com.amensg.logistica.bean;

import java.util.Collection;

import javax.ejb.Remote;

import uy.com.amensg.logistica.entities.ActivacionSublote;
import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.PuntoVenta;

@Remote
public interface IActivacionSubloteBean {

	public Collection<ActivacionSublote> list();
	
	public MetadataConsultaResultado list(MetadataConsulta metadataConsulta);
	
	public MetadataConsultaResultado listMisSublotes(MetadataConsulta metadataConsulta, Long usuarioId);

	public Long count(MetadataConsulta metadataConsulta);
	
	public Long countMisSublotes(MetadataConsulta metadataConsulta, Long usuarioId);
	
	public ActivacionSublote getById(Long id);
	
	public ActivacionSublote getByNumero(Long numero);
	
	public Long save(ActivacionSublote activacionSublote);
	
	public void update(ActivacionSublote activacionSublote);
	
	public void asignarAPuntoVenta(ActivacionSublote activacionSublote, PuntoVenta puntoVenta);
}