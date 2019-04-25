package uy.com.amensg.logistica.bean;

import java.util.Collection;
import java.util.Map;

import javax.ejb.Remote;

import uy.com.amensg.logistica.entities.Contrato;
import uy.com.amensg.logistica.entities.Empresa;
import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.TipoContrato;
import uy.com.amensg.logistica.entities.Usuario;

@Remote
public interface IContratoBean {

	public MetadataConsultaResultado list(MetadataConsulta metadataConsulta, Long usuarioId);
	
	public Long count(MetadataConsulta metadataConsulta, Long usuarioId);
	
	public Contrato getById(Long id, boolean initializeCollections);
	
	public Contrato getByMidEmpresa(Long mid, Empresa empresa, boolean initializeCollections);
	
	public Contrato getByNumeroTramite(Long numeroTramite, boolean initializeCollections);
	
	public Collection<TipoContrato> listTipoContratos();
	
	public Collection<TipoContrato> listTipoContratos(MetadataConsulta metadataConsulta, Long loggedUsuarioId);
	
	public String preprocesarArchivoEmpresa(String fileName, Long empresaId);
	
	public String preprocesarArchivoVentasANTELEmpresa(String fileName, Long empresaId);
	
	public Map<Long, Integer> preprocesarConjunto(Collection<Long> mids, Long empresaId);
	
	public Map<String, Integer> preprocesarConjuntoANTEL(Collection<String> nros, Long empresaId);
	
	public String procesarArchivoEmpresa(String fileName, Long empresaId, Long loggedUsuarioId);
	
	public String procesarArchivoVentasANTELEmpresa(String fileName, Long empresaId, Long loggedUsuarioId);
	
	public String addAsignacionManual(Long empresaId, Contrato contrato, Long loggedUsuarioId);
	
	public boolean chequearAsignacion(MetadataConsulta metadataConsulta, Long loggedUsuarioId);
	
	public boolean chequearRelacionesAsignacion(MetadataConsulta metadataConsulta, Long loggedUsuarioId);
	
	public void asignarVentas(Usuario usuario, MetadataConsulta metadataConsulta, Long loggedUsuarioId);
	
	public boolean validarVenta(Contrato contrato);
	
	public void agendar(Contrato contrato);
	
	public void rechazar(Contrato contrato);
	
	public void posponer(Contrato contrato);
	
	public void asignarBackoffice(Usuario usuario, MetadataConsulta metadataConsulta, Long loggedUsuarioId);
	
	public void distribuir(Contrato contrato);
	
	public void redistribuir(Contrato contrato);

	public void telelink(Contrato contrato);
	
	public void renovo(Contrato contrato);
	
	public void asignarDistribuidor(Usuario usuario, MetadataConsulta metadataConsulta, Long loggedUsuarioId);
	
	public void reagendar(Contrato contrato);
	
	public void activar(Contrato contrato);
	
	public void noFirma(Contrato contrato);
	
	public void recoordinar(Contrato contrato);
	
	public void asignarActivador(Usuario usuario, MetadataConsulta metadataConsulta, Long loggedUsuarioId);
	
	public void agendarActivacion(Contrato contrato);
	
	public void enviarAAntel(Contrato contrato);
	
	public void terminar(Contrato contrato);
	
	public void faltaDocumentacion(Contrato contrato);
	
	public void reActivar(Contrato contrato);
	
	public void noRecoordina(Contrato contrato);
	
	public void cerrar(Contrato contrato);
	
	public void gestionInterna(Contrato contrato);
	
	public void gestionDistribucion(Contrato contrato);
	
	public void equipoPerdido(Contrato contrato);
	
	public void facturaImpaga(Contrato contrato);
	
	public void enviadoANucleo(Contrato contrato);
	
	public void canceladoPorCliente(Contrato contrato);
	
	public void equiposPagos(Contrato contrato);
	
	public void equipoDevuelto(Contrato contrato);
	
	public void noRecuperado(Contrato contrato);
	
	public String exportarAExcel(MetadataConsulta metadataConsulta, Long loggedUsuarioId);
	
	public String exportarAExcelNucleo(MetadataConsulta metadataConsulta, Long loggedUsuarioId);
	
	public String exportarAExcelVentasNuestroCredito(MetadataConsulta metadataConsulta, Long loggedUsuarioId);
	
	public String exportarAExcelVentasCuentaAjena(MetadataConsulta metadataConsulta, Long loggedUsuarioId);
	
	public Contrato update(Contrato contrato);
}