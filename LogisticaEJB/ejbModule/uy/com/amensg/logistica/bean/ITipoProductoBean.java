package uy.com.amensg.logistica.bean;

import java.util.Collection;

import jakarta.ejb.Remote;

import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.TipoProducto;

@Remote
public interface ITipoProductoBean {

	public Collection<TipoProducto> list();
	
	public MetadataConsultaResultado list(MetadataConsulta metadataConsulta, Long usuarioId);
	
	public Long count(MetadataConsulta metadataConsulta, Long usuarioId);
	
	public TipoProducto getById(Long id);
	
	public TipoProducto save(TipoProducto tipoProducto);
	
	public void remove(TipoProducto tipoProducto);
	
	public void update(TipoProducto tipoProducto);
}