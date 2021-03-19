package uy.com.amensg.logistica.bean;

import java.util.Collection;
import java.util.Date;

import javax.ejb.Remote;

import uy.com.amensg.logistica.entities.ContratoANTEL;
import uy.com.amensg.logistica.entities.DatosEstadisticasResultadoEntregasANTEL;
import uy.com.amensg.logistica.entities.DatosEstadisticasVentasANTEL;
import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;

@Remote
public interface IContratoANTELBean {

	public MetadataConsultaResultado list(MetadataConsulta metadataConsulta, Long usuarioId);
	
	public Long count(MetadataConsulta metadataConsulta, Long usuarioId);
	
	public ContratoANTEL getByNumeroTramite(Long numeroTramite);
	
	public ContratoANTEL getByNumeroOrden(String numeroOrden);
	
	public Collection<DatosEstadisticasVentasANTEL> listEstadisticasVentasANTEL(
		Collection<Long> empresas, Date fechaDesde, Date fechaHasta
	);
	
	public Collection<DatosEstadisticasResultadoEntregasANTEL> listEstadisticasResultadoEntregasANTEL(
		Collection<Long> empresas, Date fechaDesde, Date fechaHasta
	);
}