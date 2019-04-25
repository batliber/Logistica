package uy.com.amensg.logistica.bean;

import java.util.Map;

import javax.ejb.Remote;

import uy.com.amensg.logistica.entities.Control;
import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.ProcesoImportacion;

@Remote
public interface IControlBean {

	public MetadataConsultaResultado list(MetadataConsulta metadataConsulta, Long usuarioId);

	public Long count(MetadataConsulta metadataConsulta, Long usuarioId);

	public String preprocesarArchivoEmpresa(String fileName, Long empresaId, Long loggedUsuarioId);
	
	public Map<Long, Integer> preprocesarConjunto(ProcesoImportacion procesoImportacion, Long empresaId);
	
	public String procesarArchivoEmpresa(String fileName, Long empresaId, Long tipoControlId, Long loggedUsuarioId);
	
	public Control getSiguienteMidParaControlar();
	
	public void actualizarDatosControl(Control control);
	
	public Control getById(Long id);
	
	public void update(Control control);
	
	public String exportarAExcel(MetadataConsulta metadataConsulta, Long loggedUsuarioId);
}