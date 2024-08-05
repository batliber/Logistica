package uy.com.amensg.logistica.bean;

import java.util.Collection;
import java.util.Map;

import jakarta.ejb.Remote;

import uy.com.amensg.logistica.entities.Activacion;
import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;

@Remote
public interface IActivacionBean {

	public MetadataConsultaResultado list(MetadataConsulta metadataConsulta, Long usuarioId);

	public Long count(MetadataConsulta metadataConsulta, Long usuarioId);

	public String preprocesarArchivoEmpresa(String fileName, Long empresaId);
	
	public Map<Long, Integer> preprocesarConjunto(Collection<Long> mids, Long empresaId);
	
	public String procesarArchivoEmpresa(String fileName, Long empresaId, Long tipoActivacionId, Long loggedUsuarioId);
	
	public Activacion getSiguienteMidParaActivar();
	
	public void actualizarDatosActivacion(Long mid, String chip, Long estadoActivacionId);
	
	public Activacion getById(Long id);
	
	public Activacion getLastByChip(String chip);
	
	public Activacion getLastByEmpresaIdMid(Long empresaId, Long mid);
	
	public void update(Activacion activacion);
	
	public String exportarAExcel(MetadataConsulta metadataConsulta, Long loggedUsuarioId);
	
	public String exportarAExcelSupervisorDistribucionChips(MetadataConsulta metadataConsulta, Long loggedUsuarioId);
	
	public String exportarAExcelEncargadoActivaciones(MetadataConsulta metadataConsulta, Long loggedUsuarioId);
	
	public String exportarAExcelEncargadoActivacionesSinDistribucion(MetadataConsulta metadataConsulta, Long loggedUsuarioId);
}