package uy.com.amensg.logistica.bean;

import javax.ejb.Remote;

import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.RecargaSolicitudAcreditacionSaldo;

@Remote
public interface IRecargaSolicitudAcreditacionSaldoBean {

	public MetadataConsultaResultado list(MetadataConsulta metadataConsulta, Long usuarioId);
	
	public Long count(MetadataConsulta metadataConsulta, Long usuarioId);
	
	public RecargaSolicitudAcreditacionSaldo getById(Long id, boolean initializeCollections);
	
	public RecargaSolicitudAcreditacionSaldo save(RecargaSolicitudAcreditacionSaldo recargaSolicitudAcreditacionSaldo);
	
	public void update(RecargaSolicitudAcreditacionSaldo recargaSolicitudAcreditacionSaldo);
	
	public RecargaSolicitudAcreditacionSaldo solicitar(
		RecargaSolicitudAcreditacionSaldo recargaSolicitudAcreditacionSaldo
	);
	
	public RecargaSolicitudAcreditacionSaldo preaprobar(
		RecargaSolicitudAcreditacionSaldo recargaSolicitudAcreditacionSaldo
	);
	
	public RecargaSolicitudAcreditacionSaldo aprobar(
		RecargaSolicitudAcreditacionSaldo recargaSolicitudAcreditacionSaldo
	);
	
	public RecargaSolicitudAcreditacionSaldo denegar(
		RecargaSolicitudAcreditacionSaldo recargaSolicitudAcreditacionSaldo
	);
	
	public RecargaSolicitudAcreditacionSaldo credito(
		RecargaSolicitudAcreditacionSaldo recargaSolicitudAcreditacionSaldo
	);
	
	public RecargaSolicitudAcreditacionSaldo eliminar(
		RecargaSolicitudAcreditacionSaldo recargaSolicitudAcreditacionSaldo
	);
}