package uy.com.amensg.logistica.dwr;

import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpSession;

import org.directwebremoting.WebContextFactory;
import org.directwebremoting.annotations.RemoteProxy;

import uy.com.amensg.logistica.bean.ContratoBean;
import uy.com.amensg.logistica.bean.IContratoBean;
import uy.com.amensg.logistica.entities.Barrio;
import uy.com.amensg.logistica.entities.Contrato;
import uy.com.amensg.logistica.entities.ContratoArchivoAdjunto;
import uy.com.amensg.logistica.entities.ContratoArchivoAdjuntoTO;
import uy.com.amensg.logistica.entities.ContratoTO;
import uy.com.amensg.logistica.entities.Departamento;
import uy.com.amensg.logistica.entities.Empresa;
import uy.com.amensg.logistica.entities.Estado;
import uy.com.amensg.logistica.entities.FormaPago;
import uy.com.amensg.logistica.entities.Marca;
import uy.com.amensg.logistica.entities.MetadataCondicionTO;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.MetadataConsultaResultadoTO;
import uy.com.amensg.logistica.entities.MetadataConsultaTO;
import uy.com.amensg.logistica.entities.ModalidadVenta;
import uy.com.amensg.logistica.entities.Modelo;
import uy.com.amensg.logistica.entities.Moneda;
import uy.com.amensg.logistica.entities.MotivoCambioPlan;
import uy.com.amensg.logistica.entities.Plan;
import uy.com.amensg.logistica.entities.Producto;
import uy.com.amensg.logistica.entities.ResultadoEntregaDistribucion;
import uy.com.amensg.logistica.entities.Rol;
import uy.com.amensg.logistica.entities.Sexo;
import uy.com.amensg.logistica.entities.TarjetaCredito;
import uy.com.amensg.logistica.entities.TipoContrato;
import uy.com.amensg.logistica.entities.TipoContratoTO;
import uy.com.amensg.logistica.entities.TipoDocumento;
import uy.com.amensg.logistica.entities.TipoProducto;
import uy.com.amensg.logistica.entities.TipoTasaInteresEfectivaAnual;
import uy.com.amensg.logistica.entities.Turno;
import uy.com.amensg.logistica.entities.Usuario;
import uy.com.amensg.logistica.entities.UsuarioTO;
import uy.com.amensg.logistica.entities.Zona;
import uy.com.amensg.logistica.util.Configuration;
import uy.com.amensg.logistica.util.Constants;

@RemoteProxy
public class ContratoDWR {

	private IContratoBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = ContratoBean.class.getSimpleName();
		String remoteInterfaceName = IContratoBean.class.getName();
		String lookupName = prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		Context context = new InitialContext();
		
		return (IContratoBean) context.lookup(lookupName);
	}
	
	public MetadataConsultaResultadoTO listContextAware(MetadataConsultaTO metadataConsultaTO) {
		MetadataConsultaResultadoTO result = new MetadataConsultaResultadoTO();
		
		try {
			HttpSession httpSession = WebContextFactory.get().getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				IContratoBean iContratoBean = lookupBean();
				
				MetadataConsultaResultado metadataConsultaResultado = 
					iContratoBean.list(
						MetadataConsultaDWR.transform(
							metadataConsultaTO
						),
						usuarioId
					);
				
				Collection<Object> registrosMuestra = new LinkedList<Object>();
				
				for (Object contrato : metadataConsultaResultado.getRegistrosMuestra()) {
					registrosMuestra.add(ContratoDWR.transform((Contrato) contrato, false));
				}
				
				result.setRegistrosMuestra(registrosMuestra);
				result.setCantidadRegistros(metadataConsultaResultado.getCantidadRegistros());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Long countContextAware(MetadataConsultaTO metadataConsultaTO) {
		Long result = null;
		
		try {
			HttpSession httpSession = WebContextFactory.get().getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				IContratoBean iContratoBean = lookupBean();
				
				result = 
					iContratoBean.count(
						MetadataConsultaDWR.transform(
							metadataConsultaTO
						),
						usuarioId
					);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public MetadataConsultaResultadoTO listNuestroCreditoContextAware(MetadataConsultaTO metadataConsultaTO) {
		MetadataConsultaResultadoTO result = new MetadataConsultaResultadoTO();
		
		try {
			HttpSession httpSession = WebContextFactory.get().getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				IContratoBean iContratoBean = lookupBean();
				
				MetadataCondicionTO metadataCondicionTO = new MetadataCondicionTO();
				metadataCondicionTO.setCampo("formaPago.id");
				metadataCondicionTO.setOperador(Constants.__METADATA_CONDICION_OPERADOR_IGUAL);
				
				Collection<String> valores = new LinkedList<String>();
				valores.add(Configuration.getInstance().getProperty("formaPago.NuestroCredito"));
				
				metadataCondicionTO.setValores(valores);
				
				metadataConsultaTO.getMetadataCondiciones().add(metadataCondicionTO);
				
				MetadataConsultaResultado metadataConsultaResultado = 
					iContratoBean.list(
						MetadataConsultaDWR.transform(
							metadataConsultaTO
						),
						usuarioId
					);
				
				Collection<Object> registrosMuestra = new LinkedList<Object>();
				
				for (Object contrato : metadataConsultaResultado.getRegistrosMuestra()) {
					registrosMuestra.add(ContratoDWR.transform((Contrato) contrato, false));
				}
				
				result.setRegistrosMuestra(registrosMuestra);
				result.setCantidadRegistros(metadataConsultaResultado.getCantidadRegistros());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Long countNuestroCreditoContextAware(MetadataConsultaTO metadataConsultaTO) {
		Long result = null;
		
		try {
			HttpSession httpSession = WebContextFactory.get().getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				MetadataCondicionTO metadataCondicionTO = new MetadataCondicionTO();
				metadataCondicionTO.setCampo("formaPago.id");
				metadataCondicionTO.setOperador(Constants.__METADATA_CONDICION_OPERADOR_IGUAL);
				
				Collection<String> valores = new LinkedList<String>();
				valores.add(Configuration.getInstance().getProperty("formaPago.NuestroCredito"));
				
				metadataCondicionTO.setValores(valores);
				
				metadataConsultaTO.getMetadataCondiciones().add(metadataCondicionTO);
				
				IContratoBean iContratoBean = lookupBean();
				
				result = 
					iContratoBean.count(
						MetadataConsultaDWR.transform(
							metadataConsultaTO
						),
						usuarioId
					);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public ContratoTO getById(Long id) {
		ContratoTO result = null;
		
		try {
			IContratoBean iContratoBean = lookupBean();
			
			result = transform(iContratoBean.getById(id, true), true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public ContratoTO getByNumeroTramite(Long numeroTramite) {
		ContratoTO result = null;
		
		try {
			IContratoBean iContratoBean = lookupBean();
			
			result = transform(iContratoBean.getByNumeroTramite(numeroTramite, false), false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public String addAsignacionManual(Long empresaId, ContratoTO contratoTO) {
		String result = null;
		
		try {
			IContratoBean iContratoBean = lookupBean();
			
			HttpSession httpSession = WebContextFactory.get().getSession(false);
			Long usuarioId = (Long) httpSession.getAttribute("sesion");
			
			result = iContratoBean.addAsignacionManual(empresaId, ContratoDWR.transform(contratoTO), usuarioId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public String procesarArchivoEmpresa(String fileName, Long empresaId) {
		String result = null;
		
		try {
			IContratoBean iContratoBean = lookupBean();
			
			HttpSession httpSession = WebContextFactory.get().getSession(false);
			Long usuarioId = (Long) httpSession.getAttribute("sesion");
			
			result = iContratoBean.procesarArchivoEmpresa(fileName, empresaId, usuarioId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public String procesarArchivoVentasANTELEmpresa(String fileName, Long empresaId) {
		String result = null;
		
		try {
			IContratoBean iContratoBean = lookupBean();
			
			HttpSession httpSession = WebContextFactory.get().getSession(false);
			Long usuarioId = (Long) httpSession.getAttribute("sesion");
			
			result = iContratoBean.procesarArchivoVentasANTELEmpresa(fileName, empresaId, usuarioId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public boolean chequearAsignacion(MetadataConsultaTO metadataConsultaTO) {
		boolean result = false;
		
		try {
			HttpSession httpSession = WebContextFactory.get().getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				IContratoBean iContratoBean = lookupBean();
			
				result = 
					iContratoBean.chequearAsignacion(
						MetadataConsultaDWR.transform(metadataConsultaTO), 
						usuarioId
					);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public boolean chequearRelacionesAsignacion(MetadataConsultaTO metadataConsultaTO) {
		boolean result = false;
		
		try {
			HttpSession httpSession = WebContextFactory.get().getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				IContratoBean iContratoBean = lookupBean();
			
				result = 
					iContratoBean.chequearRelacionesAsignacion(
						MetadataConsultaDWR.transform(metadataConsultaTO), 
						usuarioId
					);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public void asignarVentas(UsuarioTO usuarioTO, MetadataConsultaTO metadataConsultaTO) {
		try {
			HttpSession httpSession = WebContextFactory.get().getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				IContratoBean iContratoBean = lookupBean();
				
				iContratoBean.asignarVentas(
					UsuarioDWR.transform(usuarioTO),
					MetadataConsultaDWR.transform(metadataConsultaTO),
					usuarioId
				);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean validarVenta(ContratoTO contratoTO) {
		boolean result = false;
		
		try {
			HttpSession httpSession = WebContextFactory.get().getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				IContratoBean iContratoBean = lookupBean();
			
				result = iContratoBean.validarVenta(transform(contratoTO));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public void agendar(ContratoTO contratoTO) {
		try {
			IContratoBean iContratoBean = lookupBean();
			
			iContratoBean.agendar(transform(contratoTO));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void rechazar(ContratoTO contratoTO) {
		try {
			IContratoBean iContratoBean = lookupBean();
			
			iContratoBean.rechazar(transform(contratoTO));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void posponer(ContratoTO contratoTO) {
		try {
			IContratoBean iContratoBean = lookupBean();
			
			iContratoBean.posponer(transform(contratoTO));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void asignarBackoffice(UsuarioTO usuarioTO, MetadataConsultaTO metadataConsultaTO) {
		try {
			HttpSession httpSession = WebContextFactory.get().getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				IContratoBean iContratoBean = lookupBean();
				
				iContratoBean.asignarBackoffice(
					UsuarioDWR.transform(usuarioTO),
					MetadataConsultaDWR.transform(metadataConsultaTO),
					usuarioId
				);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void distribuir(ContratoTO contratoTO) {
		try {
			IContratoBean iContratoBean = lookupBean();
			
			iContratoBean.distribuir(transform(contratoTO));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void redistribuir(ContratoTO contratoTO) {
		try {
			IContratoBean iContratoBean = lookupBean();
			
			iContratoBean.redistribuir(transform(contratoTO));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void telelink(ContratoTO contratoTO) {
		try {
			IContratoBean iContratoBean = lookupBean();
			
			iContratoBean.telelink(transform(contratoTO));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void renovo(ContratoTO contratoTO) {
		try {
			IContratoBean iContratoBean = lookupBean();
			
			iContratoBean.renovo(transform(contratoTO));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void asignarDistribuidor(UsuarioTO usuarioTO, MetadataConsultaTO metadataConsultaTO) {
		try {
			HttpSession httpSession = WebContextFactory.get().getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				IContratoBean iContratoBean = lookupBean();
				
				iContratoBean.asignarDistribuidor(
					UsuarioDWR.transform(usuarioTO),
					MetadataConsultaDWR.transform(metadataConsultaTO),
					usuarioId
				);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void reagendar(ContratoTO contratoTO) {
		try {
			IContratoBean iContratoBean = lookupBean();
			
			iContratoBean.reagendar(transform(contratoTO));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void activar(ContratoTO contratoTO) {
		try {
			IContratoBean iContratoBean = lookupBean();
			
			iContratoBean.activar(transform(contratoTO));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void noFirma(ContratoTO contratoTO) {
		try {
			IContratoBean iContratoBean = lookupBean();
			
			iContratoBean.noFirma(transform(contratoTO));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void recoordinar(ContratoTO contratoTO) {
		try {
			IContratoBean iContratoBean = lookupBean();
			
			iContratoBean.recoordinar(transform(contratoTO));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void asignarActivador(UsuarioTO usuarioTO, MetadataConsultaTO metadataConsultaTO) {
		try {
			HttpSession httpSession = WebContextFactory.get().getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				IContratoBean iContratoBean = lookupBean();
				
				iContratoBean.asignarActivador(
					UsuarioDWR.transform(usuarioTO),
					MetadataConsultaDWR.transform(metadataConsultaTO),
					usuarioId
				);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void agendarActivacion(ContratoTO contratoTO) {
		try {
			IContratoBean iContratoBean = lookupBean();
			
			iContratoBean.agendarActivacion(transform(contratoTO));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void enviarAAntel(ContratoTO contratoTO) {
		try {
			IContratoBean iContratoBean = lookupBean();
			
			iContratoBean.enviarAAntel(transform(contratoTO));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void terminar(ContratoTO contratoTO) {
		try {
			IContratoBean iContratoBean = lookupBean();
			
			iContratoBean.terminar(transform(contratoTO));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void faltaDocumentacion(ContratoTO contratoTO) {
		try {
			IContratoBean iContratoBean = lookupBean();
			
			iContratoBean.faltaDocumentacion(transform(contratoTO));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void reActivar(ContratoTO contratoTO) {
		try {
			IContratoBean iContratoBean = lookupBean();
			
			iContratoBean.reActivar(transform(contratoTO));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void noRecoordina(ContratoTO contratoTO) {
		try {
			IContratoBean iContratoBean = lookupBean();
			
			iContratoBean.noRecoordina(transform(contratoTO));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void cerrar(ContratoTO contratoTO) {
		try {
			IContratoBean iContratoBean = lookupBean();
			
			iContratoBean.cerrar(transform(contratoTO));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void gestionInterna(ContratoTO contratoTO) {
		try {
			IContratoBean iContratoBean = lookupBean();
			
			iContratoBean.gestionInterna(transform(contratoTO));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void gestionDistribucion(ContratoTO contratoTO) {
		try {
			IContratoBean iContratoBean = lookupBean();
			
			iContratoBean.gestionDistribucion(transform(contratoTO));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void equipoPerdido(ContratoTO contratoTO) {
		try {
			IContratoBean iContratoBean = lookupBean();
			
			iContratoBean.equipoPerdido(transform(contratoTO));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void facturaImpaga(ContratoTO contratoTO) {
		try {
			IContratoBean iContratoBean = lookupBean();
			
			iContratoBean.facturaImpaga(transform(contratoTO));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void enviadoANucleo(ContratoTO contratoTO) {
		try {
			IContratoBean iContratoBean = lookupBean();
			
			iContratoBean.enviadoANucleo(transform(contratoTO));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void canceladoPorCliente(ContratoTO contratoTO) {
		try {
			IContratoBean iContratoBean = lookupBean();
			
			iContratoBean.canceladoPorCliente(transform(contratoTO));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void equiposPagos(ContratoTO contratoTO) {
		try {
			IContratoBean iContratoBean = lookupBean();
			
			iContratoBean.equiposPagos(transform(contratoTO));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void equipoDevuelto(ContratoTO contratoTO) {
		try {
			IContratoBean iContratoBean = lookupBean();
			
			iContratoBean.equipoDevuelto(transform(contratoTO));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void noRecuperado(ContratoTO contratoTO) {
		try {
			IContratoBean iContratoBean = lookupBean();
			
			iContratoBean.noRecuperado(transform(contratoTO));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String exportarAExcel(MetadataConsultaTO metadataConsultaTO) {
		String result = "";
		
		try {
			HttpSession httpSession = WebContextFactory.get().getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				IContratoBean iContratoBean = lookupBean();
				
				result = iContratoBean.exportarAExcel(MetadataConsultaDWR.transform(metadataConsultaTO), usuarioId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public String exportarAExcelNucleo(MetadataConsultaTO metadataConsultaTO) {
		String result = "";
		
		try {
			HttpSession httpSession = WebContextFactory.get().getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				IContratoBean iContratoBean = lookupBean();
				
				result = iContratoBean.exportarAExcelNucleo(MetadataConsultaDWR.transform(metadataConsultaTO), usuarioId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public String exportarAExcelVentasNuestroCredito(MetadataConsultaTO metadataConsultaTO) {
		String result = "";
		
		try {
			HttpSession httpSession = WebContextFactory.get().getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				MetadataCondicionTO metadataCondicionTO = new MetadataCondicionTO();
				metadataCondicionTO.setCampo("formaPago.id");
				metadataCondicionTO.setOperador(Constants.__METADATA_CONDICION_OPERADOR_IGUAL);
				
				Collection<String> valores = new LinkedList<String>();
				valores.add(Configuration.getInstance().getProperty("formaPago.NuestroCredito"));
				
				metadataCondicionTO.setValores(valores);
				
				metadataConsultaTO.getMetadataCondiciones().add(metadataCondicionTO);
				
				IContratoBean iContratoBean = lookupBean();
				
				result = iContratoBean.exportarAExcelVentasNuestroCredito(MetadataConsultaDWR.transform(metadataConsultaTO), usuarioId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public String exportarAExcelVentasCuentaAjena(MetadataConsultaTO metadataConsultaTO) {
		String result = "";
		
		try {
			HttpSession httpSession = WebContextFactory.get().getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				IContratoBean iContratoBean = lookupBean();
				
				result = iContratoBean.exportarAExcelVentasCuentaAjena(MetadataConsultaDWR.transform(metadataConsultaTO), usuarioId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public void update(ContratoTO contratoTO) {
		try {
			IContratoBean iContratoBean = lookupBean();
			
			iContratoBean.update(transform(contratoTO));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Collection<TipoContratoTO> listTipoContratos(MetadataConsultaTO metadataConsultaTO) {
		Collection<TipoContratoTO> result = new LinkedList<TipoContratoTO>();
		
		try {
			HttpSession httpSession = WebContextFactory.get().getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				IContratoBean iContratoBean = lookupBean();
				
				for (TipoContrato tipoContrato : 
					iContratoBean.listTipoContratos(
						MetadataConsultaDWR.transform(
							metadataConsultaTO
						),
						usuarioId
					)) {
					
					result.add(ACMInterfaceContratoDWR.transform(tipoContrato));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static ContratoTO transform(Contrato contrato, boolean transformCollections) {
		ContratoTO result = new ContratoTO();
		
		result.setAgente(contrato.getAgente());
		result.setAntelFormaPago(contrato.getAntelFormaPago());
		result.setAntelImporte(contrato.getAntelImporte());
		result.setAntelNroServicioCuenta(contrato.getAntelNroServicioCuenta());
		result.setAntelNroTrn(contrato.getAntelNroTrn());
		result.setApellido(contrato.getApellido());
		result.setCodigoPostal(contrato.getCodigoPostal());
		result.setCostoEnvio(contrato.getCostoEnvio());
		result.setCuotas(contrato.getCuotas());
		result.setDireccion(contrato.getDireccion());
		result.setDireccionEntrega(contrato.getDireccionEntrega());
		result.setDireccionEntregaApto(contrato.getDireccionEntregaApto());
		result.setDireccionEntregaBis(contrato.getDireccionEntregaBis());
		result.setDireccionEntregaBlock(contrato.getDireccionEntregaBlock());
		result.setDireccionEntregaCalle(contrato.getDireccionEntregaCalle());
		result.setDireccionEntregaCodigoPostal(contrato.getDireccionEntregaCodigoPostal());
		result.setDireccionEntregaLocalidad(contrato.getDireccionEntregaLocalidad());
		result.setDireccionEntregaManzana(contrato.getDireccionEntregaManzana());
		result.setDireccionEntregaNumero(contrato.getDireccionEntregaNumero());
		result.setDireccionEntregaObservaciones(contrato.getDireccionEntregaObservaciones());
		result.setDireccionEntregaSolar(contrato.getDireccionEntregaSolar());
		result.setDireccionFactura(contrato.getDireccionFactura());
		result.setDireccionFacturaApto(contrato.getDireccionFacturaApto());
		result.setDireccionFacturaBis(contrato.getDireccionFacturaBis());
		result.setDireccionFacturaBlock(contrato.getDireccionFacturaBlock());
		result.setDireccionFacturaCalle(contrato.getDireccionFacturaCalle());
		result.setDireccionFacturaCodigoPostal(contrato.getDireccionFacturaCodigoPostal());
		result.setDireccionFacturaLocalidad(contrato.getDireccionFacturaLocalidad());
		result.setDireccionFacturaManzana(contrato.getDireccionFacturaManzana());
		result.setDireccionFacturaNumero(contrato.getDireccionFacturaNumero());
		result.setDireccionFacturaObservaciones(contrato.getDireccionFacturaObservaciones());
		result.setDireccionFacturaSolar(contrato.getDireccionFacturaSolar());
		result.setDocumentoTipo(contrato.getDocumentoTipo());
		result.setDocumento(contrato.getDocumento());
		result.setEmail(contrato.getEmail());
		result.setEquipo(contrato.getEquipo());
		result.setFechaActivacion(contrato.getFechaActivacion());
		result.setFechaActivarEn(contrato.getFechaActivarEn());
		result.setFechaBackoffice(contrato.getFechaBackoffice());
		result.setFechaCoordinacion(contrato.getFechaCoordinacion());
		result.setFechaDevolucionDistribuidor(contrato.getFechaDevolucionDistribuidor());
		result.setFechaEnvioAntel(contrato.getFechaEnvioAntel());
		result.setFechaEnvioANucleo(contrato.getFechaEnvioANucleo());
		result.setFechaEntregaDistribuidor(contrato.getFechaEntregaDistribuidor());
		result.setFechaEntrega(contrato.getFechaEntrega());
		result.setFechaFinContrato(contrato.getFechaFinContrato());
		result.setFechaNacimiento(contrato.getFechaNacimiento());
		result.setFechaRechazo(contrato.getFechaRechazo());
		result.setFechaVenta(contrato.getFechaVenta());
		result.setGastosAdministrativos(contrato.getGastosAdministrativos());
		result.setGastosAdministrativosTotales(contrato.getGastosAdministrativosTotales());
		result.setGastosConcesion(contrato.getGastosConcesion());
		result.setIncluirChip(contrato.getIncluirChip());
		result.setIntereses(contrato.getIntereses());
		result.setLocalidad(contrato.getLocalidad());
		result.setMid(contrato.getMid());
		result.setNombre(contrato.getNombre());
		result.setNuevoPlanString(contrato.getNuevoPlanString());
		result.setNumeroBloqueo(contrato.getNumeroBloqueo());
		result.setNumeroChip(contrato.getNumeroChip());
		result.setNumeroCliente(contrato.getNumeroCliente());
		result.setNumeroContrato(contrato.getNumeroContrato());
		result.setNumeroFactura(contrato.getNumeroFactura());
		result.setNumeroFacturaRiverGreen(contrato.getNumeroFacturaRiverGreen());
		result.setNumeroSerie(contrato.getNumeroSerie());
		result.setNumeroTramite(contrato.getNumeroTramite());
		result.setNumeroVale(contrato.getNumeroVale());
		result.setObservaciones(contrato.getObservaciones());
		result.setPrecio(contrato.getPrecio());
		result.setRandom(contrato.getRandom());
		result.setResultadoEntregaDistribucionLatitud(contrato.getResultadoEntregaDistribucionLatitud());
		result.setResultadoEntregaDistribucionLongitud(contrato.getResultadoEntregaDistribucionLongitud());
		result.setResultadoEntregaDistribucionObservaciones(contrato.getResultadoEntregaDistribucionObservaciones());
		result.setResultadoEntregaDistribucionPrecision(contrato.getResultadoEntregaDistribucionPrecision());
		result.setResultadoEntregaDistribucionURLAnverso(contrato.getResultadoEntregaDistribucionURLAnverso());
		result.setResultadoEntregaDistribucionURLReverso(contrato.getResultadoEntregaDistribucionURLReverso());
		result.setTelefonoContacto(contrato.getTelefonoContacto());
		result.setTipoContratoCodigo(contrato.getTipoContratoCodigo());
		result.setTipoContratoDescripcion(contrato.getTipoContratoDescripcion());
		result.setValorCuota(contrato.getValorCuota());
		result.setValorUnidadIndexada(contrato.getValorUnidadIndexada());
		result.setValorTasaInteresEfectivaAnual(contrato.getValorTasaInteresEfectivaAnual());
		
		if (contrato.getBarrio() != null) {
			result.setBarrio(BarrioDWR.transform(contrato.getBarrio()));
		}
		if (contrato.getDireccionEntregaDepartamento() != null) {
			result.setDireccionEntregaDepartamento(DepartamentoDWR.transform(contrato.getDireccionEntregaDepartamento()));
		}
		if (contrato.getDireccionFacturaDepartamento() != null) {
			result.setDireccionFacturaDepartamento(DepartamentoDWR.transform(contrato.getDireccionFacturaDepartamento()));
		}
		if (contrato.getEstado() != null) {
			result.setEstado(EstadoDWR.transform(contrato.getEstado()));
		}
		if (contrato.getEmpresa() != null) {
			result.setEmpresa(EmpresaDWR.transform(contrato.getEmpresa(), false));
		}
		if (contrato.getFormaPago() != null) {
			result.setFormaPago(FormaPagoDWR.transform(contrato.getFormaPago()));
		}
		if (contrato.getTipoTasaInteresEfectivaAnual() != null) {
			result.setTipoTasaInteresEfectivaAnual(TipoTasaInteresEfectivaAnualDWR.transform(contrato.getTipoTasaInteresEfectivaAnual()));
		}
		if (contrato.getMoneda() != null) {
			result.setMoneda(MonedaDWR.transform(contrato.getMoneda()));
		}
		if (contrato.getNuevoPlan() != null) {
			result.setNuevoPlan(PlanDWR.transform(contrato.getNuevoPlan()));
		}
		if (contrato.getMotivoCambioPlan() != null) {
			result.setMotivoCambioPlan(MotivoCambioPlanDWR.transform(contrato.getMotivoCambioPlan()));
		}
		if (contrato.getTipoProducto() != null) {
			result.setTipoProducto(TipoProductoDWR.transform(contrato.getTipoProducto()));
		}
		if (contrato.getMarca() != null) {
			result.setMarca(MarcaDWR.transform(contrato.getMarca()));
		}
		if (contrato.getModalidadVenta() != null) {
			result.setModalidadVenta(ModalidadVentaDWR.transform(contrato.getModalidadVenta()));
		}
		if (contrato.getModelo() != null) {
			result.setModelo(ModeloDWR.transform(contrato.getModelo()));
		}
		if (contrato.getProducto() != null) {
			result.setProducto(ProductoDWR.transform(contrato.getProducto()));
		}
		if (contrato.getResultadoEntregaDistribucion() != null) {
			result.setResultadoEntregaDistribucion(ResultadoEntregaDistribucionDWR.transform(contrato.getResultadoEntregaDistribucion()));
		}
		if (contrato.getRol() != null) {
			result.setRol(RolDWR.transform(contrato.getRol(), false));
		}
		if (contrato.getSexo() != null) {
			result.setSexo(SexoDWR.transform(contrato.getSexo()));
		}
		if (contrato.getTarjetaCredito() != null) {
			result.setTarjetaCredito(TarjetaCreditoDWR.transform(contrato.getTarjetaCredito()));
		}
		if (contrato.getTipoDocumento() != null) {
			result.setTipoDocumento(TipoDocumentoDWR.transform(contrato.getTipoDocumento()));
		}
		if (contrato.getTurno() != null) {
			result.setTurno(TurnoDWR.transform(contrato.getTurno()));
		}
		if (contrato.getUsuario() != null) {
			result.setUsuario(UsuarioDWR.transform(contrato.getUsuario(), false));
		}
		if (contrato.getZona() != null) {
			result.setZona(ZonaDWR.transform(contrato.getZona()));
		}
		
		if (contrato.getActivador() != null) {
			result.setActivador(UsuarioDWR.transform(contrato.getActivador(), false));
		}
		if (contrato.getBackoffice() != null) {
			result.setBackoffice(UsuarioDWR.transform(contrato.getBackoffice(), false));
		}
		if (contrato.getCoordinador() != null) {
			result.setCoordinador(UsuarioDWR.transform(contrato.getCoordinador(), false));
		}
		if (contrato.getDistribuidor() != null) {
			result.setDistribuidor(UsuarioDWR.transform(contrato.getDistribuidor(), false));
		}
		if (contrato.getVendedor() != null) {
			result.setVendedor(UsuarioDWR.transform(contrato.getVendedor(), false));
		}
		
		if (transformCollections) {
			if (contrato.getArchivosAdjuntos() != null) {
				Collection<ContratoArchivoAdjuntoTO> archivosAdjuntos = new LinkedList<ContratoArchivoAdjuntoTO>();
				
				for (ContratoArchivoAdjunto archivoAdjunto : contrato.getArchivosAdjuntos()) {
					archivosAdjuntos.add(ContratoArchivoAdjuntoDWR.transform(archivoAdjunto));
				}
				
				result.setArchivosAdjuntos(archivosAdjuntos);
			}
		}
		
		result.setFcre(contrato.getFcre());
		result.setFact(contrato.getFact());
		result.setId(contrato.getId());
		result.setTerm(contrato.getTerm());
		result.setUact(contrato.getUact());
		result.setUcre(contrato.getUcre());
		
		return result;
	}

	public static Contrato transform(ContratoTO contratoTO) {
		Contrato result = new Contrato();
		
		result.setAgente(contratoTO.getAgente());
		result.setAntelFormaPago(contratoTO.getAntelFormaPago());
		result.setAntelImporte(contratoTO.getAntelImporte());
		result.setAntelNroServicioCuenta(contratoTO.getAntelNroServicioCuenta());
		result.setAntelNroTrn(contratoTO.getAntelNroTrn());
		result.setApellido(contratoTO.getApellido());
		result.setCodigoPostal(contratoTO.getCodigoPostal());
		result.setCostoEnvio(contratoTO.getCostoEnvio());
		result.setCuotas(contratoTO.getCuotas());
		result.setDireccion(contratoTO.getDireccion());
		result.setDireccionEntrega(contratoTO.getDireccionEntrega());
		result.setDireccionEntregaApto(contratoTO.getDireccionEntregaApto());
		result.setDireccionEntregaBis(contratoTO.getDireccionEntregaBis());
		result.setDireccionEntregaBlock(contratoTO.getDireccionEntregaBlock());
		result.setDireccionEntregaCalle(contratoTO.getDireccionEntregaCalle());
		result.setDireccionEntregaCodigoPostal(contratoTO.getDireccionEntregaCodigoPostal());
		result.setDireccionEntregaLocalidad(contratoTO.getDireccionEntregaLocalidad());
		result.setDireccionEntregaManzana(contratoTO.getDireccionEntregaManzana());
		result.setDireccionEntregaNumero(contratoTO.getDireccionEntregaNumero());
		result.setDireccionEntregaObservaciones(contratoTO.getDireccionEntregaObservaciones());
		result.setDireccionEntregaSolar(contratoTO.getDireccionEntregaSolar());
		result.setDireccionFactura(contratoTO.getDireccionFactura());
		result.setDireccionFacturaApto(contratoTO.getDireccionFacturaApto());
		result.setDireccionFacturaBis(contratoTO.getDireccionFacturaBis());
		result.setDireccionFacturaBlock(contratoTO.getDireccionFacturaBlock());
		result.setDireccionFacturaCalle(contratoTO.getDireccionFacturaCalle());
		result.setDireccionFacturaCodigoPostal(contratoTO.getDireccionFacturaCodigoPostal());
		result.setDireccionFacturaLocalidad(contratoTO.getDireccionFacturaLocalidad());
		result.setDireccionFacturaManzana(contratoTO.getDireccionFacturaManzana());
		result.setDireccionFacturaNumero(contratoTO.getDireccionFacturaNumero());
		result.setDireccionFacturaObservaciones(contratoTO.getDireccionFacturaObservaciones());
		result.setDireccionFacturaSolar(contratoTO.getDireccionFacturaSolar());
		result.setDocumentoTipo(contratoTO.getDocumentoTipo());
		result.setDocumento(contratoTO.getDocumento());
		result.setEmail(contratoTO.getEmail());
		result.setEquipo(contratoTO.getEquipo());
		result.setFechaActivacion(contratoTO.getFechaActivacion());
		result.setFechaActivarEn(contratoTO.getFechaActivarEn());
		result.setFechaBackoffice(contratoTO.getFechaBackoffice());
		result.setFechaCoordinacion(contratoTO.getFechaCoordinacion());
		result.setFechaDevolucionDistribuidor(contratoTO.getFechaDevolucionDistribuidor());
		result.setFechaEntregaDistribuidor(contratoTO.getFechaEntregaDistribuidor());
		result.setFechaEntrega(contratoTO.getFechaEntrega());
		result.setFechaEnvioAntel(contratoTO.getFechaEnvioAntel());
		result.setFechaEnvioANucleo(contratoTO.getFechaEnvioANucleo());
		result.setFechaFinContrato(contratoTO.getFechaFinContrato());
		result.setFechaNacimiento(contratoTO.getFechaNacimiento());
		result.setFechaRechazo(contratoTO.getFechaRechazo());
		result.setFechaVenta(contratoTO.getFechaVenta());
		result.setGastosAdministrativos(contratoTO.getGastosAdministrativos());
		result.setGastosAdministrativosTotales(contratoTO.getGastosAdministrativosTotales());
		result.setGastosConcesion(contratoTO.getGastosConcesion());
		result.setIncluirChip(contratoTO.getIncluirChip());
		result.setIntereses(contratoTO.getIntereses());
		result.setLocalidad(contratoTO.getLocalidad());
		result.setMid(contratoTO.getMid());
		result.setNombre(contratoTO.getNombre());
		result.setNuevoPlanString(contratoTO.getNuevoPlanString());
		result.setNumeroBloqueo(contratoTO.getNumeroBloqueo());
		result.setNumeroChip(contratoTO.getNumeroChip());
		result.setNumeroCliente(contratoTO.getNumeroCliente());
		result.setNumeroContrato(contratoTO.getNumeroContrato());
		result.setNumeroFactura(contratoTO.getNumeroFactura());
		result.setNumeroFacturaRiverGreen(contratoTO.getNumeroFacturaRiverGreen());
		result.setNumeroSerie(contratoTO.getNumeroSerie());
		result.setNumeroTramite(contratoTO.getNumeroTramite());
		result.setNumeroVale(contratoTO.getNumeroVale());
		result.setObservaciones(contratoTO.getObservaciones());
		result.setPrecio(contratoTO.getPrecio());
		result.setRandom(contratoTO.getRandom());
		result.setResultadoEntregaDistribucionLatitud(contratoTO.getResultadoEntregaDistribucionLatitud());
		result.setResultadoEntregaDistribucionLongitud(contratoTO.getResultadoEntregaDistribucionLongitud());
		result.setResultadoEntregaDistribucionObservaciones(contratoTO.getResultadoEntregaDistribucionObservaciones());
		result.setResultadoEntregaDistribucionPrecision(contratoTO.getResultadoEntregaDistribucionPrecision());
		result.setResultadoEntregaDistribucionURLAnverso(contratoTO.getResultadoEntregaDistribucionURLAnverso());
		result.setResultadoEntregaDistribucionURLReverso(contratoTO.getResultadoEntregaDistribucionURLReverso());
		result.setTelefonoContacto(contratoTO.getTelefonoContacto());
		result.setTipoContratoCodigo(contratoTO.getTipoContratoCodigo());
		result.setTipoContratoDescripcion(contratoTO.getTipoContratoDescripcion());
		result.setValorCuota(contratoTO.getValorCuota());
		result.setValorUnidadIndexada(contratoTO.getValorUnidadIndexada());
		result.setValorTasaInteresEfectivaAnual(contratoTO.getValorTasaInteresEfectivaAnual());
		
		if (contratoTO.getDireccionEntregaDepartamento() != null) {
			Departamento direccionEntregaDepartamento = new Departamento();
			direccionEntregaDepartamento.setId(contratoTO.getDireccionEntregaDepartamento().getId());
			
			result.setDireccionEntregaDepartamento(direccionEntregaDepartamento);
		}
		if (contratoTO.getDireccionFacturaDepartamento() != null) {
			Departamento direccionFacturaDepartamento = new Departamento();
			direccionFacturaDepartamento.setId(contratoTO.getDireccionFacturaDepartamento().getId());
			
			result.setDireccionFacturaDepartamento(direccionFacturaDepartamento);
		}
		if (contratoTO.getFormaPago() != null) {
			FormaPago formaPago = new FormaPago();
			formaPago.setId(contratoTO.getFormaPago().getId());
			
			result.setFormaPago(formaPago);
		}
		if (contratoTO.getTipoTasaInteresEfectivaAnual() != null) {
			TipoTasaInteresEfectivaAnual tipoTasaInteresEfectivaAnual = new TipoTasaInteresEfectivaAnual();
			tipoTasaInteresEfectivaAnual.setId(contratoTO.getTipoTasaInteresEfectivaAnual().getId());
			
			result.setTipoTasaInteresEfectivaAnual(tipoTasaInteresEfectivaAnual);
		}
		if (contratoTO.getMoneda() != null) {
			Moneda moneda = new Moneda();
			moneda.setId(contratoTO.getMoneda().getId());
			
			result.setMoneda(moneda);
		}
		if (contratoTO.getTarjetaCredito() != null) {
			TarjetaCredito tarjetaCredito = new TarjetaCredito();
			tarjetaCredito.setId(contratoTO.getTarjetaCredito().getId());
			
			result.setTarjetaCredito(tarjetaCredito);
		}
		if (contratoTO.getTipoProducto() != null) {
			TipoProducto tipoProducto = new TipoProducto();
			tipoProducto.setId(contratoTO.getTipoProducto().getId());
			
			result.setTipoProducto(tipoProducto);
		}
		if (contratoTO.getMarca() != null) {
			Marca marca = new Marca();
			marca.setId(contratoTO.getMarca().getId());
			
			result.setMarca(marca);
		}
		if (contratoTO.getModalidadVenta() != null) {
			ModalidadVenta modalidadVenta = new ModalidadVenta();
			modalidadVenta.setId(contratoTO.getModalidadVenta().getId());
			
			result.setModalidadVenta(modalidadVenta);
		}
		if (contratoTO.getModelo() != null) {
			Modelo modelo = new Modelo();
			modelo.setId(contratoTO.getModelo().getId());
			
			result.setModelo(modelo);
		}
		if (contratoTO.getProducto() != null) {
			Producto producto = new Producto();
			producto.setId(contratoTO.getProducto().getId());
			
			result.setProducto(producto);
		}
		if (contratoTO.getNuevoPlan() != null) {
			Plan nuevoPlan = new Plan();
			nuevoPlan.setId(contratoTO.getNuevoPlan().getId());
			
			result.setNuevoPlan(nuevoPlan);
		}
		if (contratoTO.getMotivoCambioPlan() != null) {
			MotivoCambioPlan motivoCambioPlan = new MotivoCambioPlan();
			motivoCambioPlan.setId(contratoTO.getMotivoCambioPlan().getId());
			
			result.setMotivoCambioPlan(motivoCambioPlan);
		}
		if (contratoTO.getResultadoEntregaDistribucion() != null) {
			ResultadoEntregaDistribucion resultadoEntregaDistribucion = new ResultadoEntregaDistribucion();
			resultadoEntregaDistribucion.setId(contratoTO.getResultadoEntregaDistribucion().getId());
			
			result.setResultadoEntregaDistribucion(resultadoEntregaDistribucion);
		}
		if (contratoTO.getTipoDocumento() != null) {
			TipoDocumento tipoDocumento = new TipoDocumento();
			tipoDocumento.setId(contratoTO.getTipoDocumento().getId());
			
			result.setTipoDocumento(tipoDocumento);
		}
		if (contratoTO.getSexo() != null) {
			Sexo sexo = new Sexo();
			sexo.setId(contratoTO.getSexo().getId());
			
			result.setSexo(sexo);
		}
		if (contratoTO.getTurno() != null) {
			Turno turno = new Turno();
			turno.setId(contratoTO.getTurno().getId());
			
			result.setTurno(turno);
		}
		if (contratoTO.getBarrio() != null) {
			Barrio barrio = new Barrio();
			barrio.setId(contratoTO.getBarrio().getId());
			
			result.setBarrio(barrio);
		}
		if (contratoTO.getZona() != null) {
			Zona zona = new Zona();
			zona.setId(contratoTO.getZona().getId());
			
			result.setZona(zona);
		}
		if (contratoTO.getEstado() != null) {
			Estado estado = new Estado();
			estado.setId(contratoTO.getEstado().getId());
			
			result.setEstado(estado);
		}
		if (contratoTO.getEmpresa() != null) {
			Empresa empresa = new Empresa();
			empresa.setId(contratoTO.getEmpresa().getId());
			
			result.setEmpresa(empresa);
		}
		if (contratoTO.getRol() != null) {
			Rol rol = new Rol();
			rol.setId(contratoTO.getRol().getId());
			
			result.setRol(rol);
		}
		if (contratoTO.getUsuario() != null) {
			Usuario usuario = new Usuario();
			usuario.setId(contratoTO.getUsuario().getId());
			
			result.setUsuario(usuario);
		}
		if (contratoTO.getActivador() != null) {
			Usuario activador = new Usuario();
			activador.setId(contratoTO.getActivador().getId());
			
			result.setActivador(activador);
		}
		if (contratoTO.getBackoffice() != null) {
			Usuario backoffice = new Usuario();
			backoffice.setId(contratoTO.getBackoffice().getId());
			
			result.setBackoffice(backoffice);
		}
		if (contratoTO.getCoordinador() != null) {
			Usuario coordinador = new Usuario();
			coordinador.setId(contratoTO.getCoordinador().getId());
			
			result.setCoordinador(coordinador);
		}
		if (contratoTO.getDistribuidor() != null) {
			Usuario distribuidor = new Usuario();
			distribuidor.setId(contratoTO.getDistribuidor().getId());
			
			result.setDistribuidor(distribuidor);
		}
		if (contratoTO.getVendedor() != null) {
			Usuario vendedor = new Usuario();
			vendedor.setId(contratoTO.getVendedor().getId());
			
			result.setVendedor(vendedor);
		}
		
		Date date = GregorianCalendar.getInstance().getTime();
		
		HttpSession httpSession = WebContextFactory.get().getSession(false);
		Long usuarioId = (Long) httpSession.getAttribute("sesion");
		
		result.setFcre(contratoTO.getFcre());
		result.setFact(date);
		result.setId(contratoTO.getId());
		result.setTerm(new Long(1));
		
		result.setUact(usuarioId);
		result.setUcre(contratoTO.getUcre());
		
		return result;
	}
}