package uy.com.amensg.logistica.bean;

import java.util.Collection;

import jakarta.ejb.Remote;

import uy.com.amensg.logistica.entities.ContratoRoutingHistory;
import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;

@Remote
public interface IContratoRoutingHistoryBean {

	public MetadataConsultaResultado list(MetadataConsulta metadataConsulta, Long usuarioId);

	public Collection<ContratoRoutingHistory> listByContratoId(Long contratoId);
	
	public Collection<ContratoRoutingHistory> listByNumeroTramite(Long numeroTramite);
}