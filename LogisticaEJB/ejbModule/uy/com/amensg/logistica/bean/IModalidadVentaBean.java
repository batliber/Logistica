package uy.com.amensg.logistica.bean;

import java.util.Collection;

import jakarta.ejb.Remote;

import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.ModalidadVenta;

@Remote
public interface IModalidadVentaBean {

	public Collection<ModalidadVenta> list();
	
	public MetadataConsultaResultado list(MetadataConsulta metadataConsulta, Long usuarioId);
	
	public Long count(MetadataConsulta metadataConsulta, Long usuarioId);
	
	public ModalidadVenta getById(Long id);
	
	public ModalidadVenta save(ModalidadVenta atencionClienteConcepto);
	
	public void remove(ModalidadVenta atencionClienteConcepto);
	
	public void update(ModalidadVenta atencionClienteConcepto);
}