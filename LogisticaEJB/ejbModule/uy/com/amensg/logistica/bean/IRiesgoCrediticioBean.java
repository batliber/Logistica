package uy.com.amensg.logistica.bean;

import javax.ejb.Remote;

import uy.com.amensg.logistica.entities.ACMInterfaceRiesgoCrediticio;
import uy.com.amensg.logistica.entities.BCUInterfaceRiesgoCrediticio;
import uy.com.amensg.logistica.entities.BCUInterfaceRiesgoCrediticioInstitucionFinanciera;
import uy.com.amensg.logistica.entities.Empresa;
import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.RiesgoCrediticio;

@Remote
public interface IRiesgoCrediticioBean {

	public MetadataConsultaResultado list(MetadataConsulta metadataConsulta, Long usuarioId);

	public Long count(MetadataConsulta metadataConsulta, Long usuarioId);

	public RiesgoCrediticio getSiguienteDocumentoParaControlar();
	
	public RiesgoCrediticio getLastByEmpresaDocumento(Empresa empresa, String documento);
	
	public String preprocesarArchivoEmpresa(String fileName, Long empresaId);
	
	public String procesarArchivoEmpresa(String fileName, Long empresaId, Long tipoControlRiesgoCrediticioId, Long loggedUsuarioId);
	
	public void save(RiesgoCrediticio riesgoCrediticio);
	
	public void actualizarDatosRiesgoCrediticioACM(ACMInterfaceRiesgoCrediticio acmInterfaceRiesgoCrediticio);
	
	public void actualizarDatosRiesgoCrediticioBCU(BCUInterfaceRiesgoCrediticio bcuInterfaceRiesgoCrediticio);
	
	public void actualizarDatosRiesgoCrediticioBCUInstitucionFinanciera(
		BCUInterfaceRiesgoCrediticioInstitucionFinanciera bcuInterfaceRiesgoCrediticioInstitucionFinanciera
	);
}