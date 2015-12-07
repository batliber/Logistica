package uy.com.amensg.logistica.bean;

import java.util.Collection;

import javax.ejb.Remote;

import uy.com.amensg.logistica.entities.Contrato;
import uy.com.amensg.logistica.entities.ContratoRoutingHistory;
import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.Usuario;

@Remote
public interface IContratoRoutingHistoryBean {

	public MetadataConsultaResultado list(MetadataConsulta metadataConsulta, Long usuarioId);

	public String preprocesarArchivoEmpresa(String fileName, Long empresaId);
	
	public void procesarArchivoEmpresa(String fileName, Long empresaId);
	
	public String addAsignacionManual(Long empresaId, Contrato contrato);
	
	public void asignarVentas(Usuario usuario, MetadataConsulta metadataConsulta, Long loggedUsuarioId);
	
	public void agendar(ContratoRoutingHistory contratoRoutingHistory);
	
	public void rechazar(ContratoRoutingHistory contratoRoutingHistory);
	
	public void posponer(ContratoRoutingHistory contratoRoutingHistory);
	
	public void asignarBackoffice(Usuario usuario, MetadataConsulta metadataConsulta, Long loggedUsuarioId);
	
	public void distribuir(ContratoRoutingHistory contratoRoutingHistory);
	
	public void redistribuir(ContratoRoutingHistory contratoRoutingHistory);

	public void telelink(ContratoRoutingHistory contratoRoutingHistory);
	
	public void renovo(ContratoRoutingHistory contratoRoutingHistory);
	
	public void asignarDistribuidor(Usuario usuario, MetadataConsulta metadataConsulta, Long loggedUsuarioId);
	
	public void reagendar(ContratoRoutingHistory contratoRoutingHistory);
	
	public void activar(ContratoRoutingHistory contratoRoutingHistory);
	
	public void noFirma(ContratoRoutingHistory contratoRoutingHistory);
	
	public void recoordinar(ContratoRoutingHistory contratoRoutingHistory);
	
	public void asignarActivador(Usuario usuario, MetadataConsulta metadataConsulta, Long loggedUsuarioId);
	
	public void agendarActivacion(ContratoRoutingHistory contratoRoutingHistory);
	
	public void terminar(ContratoRoutingHistory contratoRoutingHistory);
	
	public void faltaDocumentacion(ContratoRoutingHistory contratoRoutingHistory);
	
	public Collection<ContratoRoutingHistory> listByContratoId(Long contratoId);
	
	public String exportarAExcel(MetadataConsulta metadataConsulta, Long loggedUsuarioId);
}