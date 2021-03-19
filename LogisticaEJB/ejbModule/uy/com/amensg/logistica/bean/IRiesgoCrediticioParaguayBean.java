package uy.com.amensg.logistica.bean;

import java.util.Date;

import javax.ejb.Remote;

import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.RiesgoCrediticioParaguay;

@Remote
public interface IRiesgoCrediticioParaguayBean {

	public MetadataConsultaResultado list(MetadataConsulta metadataConsulta, Long usuarioId);
	
	public Long count(MetadataConsulta metadataConsulta, Long usuarioId);
	
	public RiesgoCrediticioParaguay getById(Long id);
	
	public RiesgoCrediticioParaguay getLastByDocumentoFechaNacimientoSituacionRiesgoCrediticioParaguayId(
		String documento, Date fechaNacimiento, Long situacionRiesgoCrediticioParaguayId
	);
	
	public RiesgoCrediticioParaguay getSiguienteDocumentoParaControlar();
	
	public RiesgoCrediticioParaguay controlarRiesgo(
		String documento, Date fechaNacimiento, Long situacionRiesgoCrediticioId
	);
	
	public RiesgoCrediticioParaguay actualizarDatosRiesgoCrediticioParaguay(RiesgoCrediticioParaguay riesgoCrediticioParaguay);

	public RiesgoCrediticioParaguay save(RiesgoCrediticioParaguay riesgoCrediticioParaguay);
	
	public String exportarAExcel(MetadataConsulta metadataConsulta, Long loggedUsuarioId);
	
	public String preprocesarArchivoEmpresa(String fileName, Long empresaId);
	
	public String procesarArchivoEmpresa(String fileName, Long empresaId, Long loggedUsuarioId);
}