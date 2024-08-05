package uy.com.amensg.logistica.bean;

import java.util.Collection;

import jakarta.ejb.Remote;

import uy.com.amensg.logistica.entities.AtencionClienteConcepto;
import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;

@Remote
public interface IAtencionClienteConceptoBean {

	public Collection<AtencionClienteConcepto> list();
	
	public MetadataConsultaResultado list(MetadataConsulta metadataConsulta, Long usuarioId);
	
	public Long count(MetadataConsulta metadataConsulta, Long usuarioId);
	
	public AtencionClienteConcepto getById(Long id);
	
	public AtencionClienteConcepto save(AtencionClienteConcepto atencionClienteConcepto);
	
	public void remove(AtencionClienteConcepto atencionClienteConcepto);
	
	public void update(AtencionClienteConcepto atencionClienteConcepto);
}