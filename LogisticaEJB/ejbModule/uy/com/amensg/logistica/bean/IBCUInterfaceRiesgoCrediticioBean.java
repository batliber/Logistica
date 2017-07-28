package uy.com.amensg.logistica.bean;

import javax.ejb.Remote;

import uy.com.amensg.logistica.entities.BCUInterfaceRiesgoCrediticio;
import uy.com.amensg.logistica.entities.Empresa;
import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;

@Remote
public interface IBCUInterfaceRiesgoCrediticioBean {

	public MetadataConsultaResultado list(MetadataConsulta metadataConsulta, Long usuarioId);

	public Long count(MetadataConsulta metadataConsulta, Long usuarioId);

	public BCUInterfaceRiesgoCrediticio getLastByEmpresaDocumento(Empresa empresa, String documento);
	
	public void save(BCUInterfaceRiesgoCrediticio bcuInterfaceRiesgoCrediticio);

	public String exportarAExcel(MetadataConsulta metadataConsulta, Long loggedUsuarioId);
}