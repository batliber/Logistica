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
<<<<<<< HEAD
import uy.com.amensg.logistica.entities.Modelo;
import uy.com.amensg.logistica.entities.Moneda;
=======
>>>>>>> branch 'master' of https://github.com/batliber/Logistica.git
import uy.com.amensg.logistica.entities.MotivoCambioPlan;
import uy.com.amensg.logistica.entities.Plan;
import uy.com.amensg.logistica.entities.Producto;
import uy.com.amensg.logistica.entities.ResultadoEntregaDistribucion;
import uy.com.amensg.logistica.entities.Rol;
import uy.com.amensg.logistica.entities.Sexo;
<<<<<<< HEAD
import uy.com.amensg.logistica.entities.TarjetaCredito;
import uy.com.amensg.logistica.entities.TipoContrato;
import uy.com.amensg.logistica.entities.TipoContratoTO;
=======
>>>>>>> branch 'master' of https://github.com/batliber/Logistica.git
import uy.com.amensg.logistica.entities.TipoDocumento;
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
					registrosMuestra.add(ContratoDWR.transform((Contrato) contrato));
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
					registrosMuestra.add(ContratoDWR.transform((Contrato) contrato));
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
			
			result = transform(iContratoBean.getById(id));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public ContratoTO getByNumeroTramite(Long numeroTramite) {
		ContratoTO result = null;
		
		try {
			IContratoBean iContratoBean = lookupBean();
			
			result = transform(iContratoBean.getByNumeroTramite(numeroTramite));
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
	
	public String exportarAExcel(MetadataConsultaTO metadataConsultaTO) {
		String result = "";
		
		try {
			HttpSession httpSession = WebContextFactory.get().getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				IContratoBean iContratoBean = lookupBean();
				
				result = iContratoBean.exportarAExcel(MetadataConsultaDWR.transform(metadataConsultaTO), usuarioId);
			}
<<<<<<< HEAD
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
=======
>>>>>>> branch 'master' of https://github.com/batliber/Logistica.git
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
	
	public static ContratoTO transform(Contrato contrato) {
		ContratoTO contratoTO = new ContratoTO();
		
		contratoTO.setAgente(contrato.getAgente());
		contratoTO.setApellido(contrato.getApellido());
		contratoTO.setCodigoPostal(contrato.getCodigoPostal());
		contratoTO.setCuotas(contrato.getCuotas());
		contratoTO.setDireccion(contrato.getDireccion());
		contratoTO.setDireccionEntrega(contrato.getDireccionEntrega());
		contratoTO.setDireccionEntregaApto(contrato.getDireccionEntregaApto());
		contratoTO.setDireccionEntregaBis(contrato.getDireccionEntregaBis());
		contratoTO.setDireccionEntregaBlock(contrato.getDireccionEntregaBlock());
		contratoTO.setDireccionEntregaCalle(contrato.getDireccionEntregaCalle());
		contratoTO.setDireccionEntregaCodigoPostal(contrato.getDireccionEntregaCodigoPostal());
		contratoTO.setDireccionEntregaLocalidad(contrato.getDireccionEntregaLocalidad());
		contratoTO.setDireccionEntregaManzana(contrato.getDireccionEntregaManzana());
		contratoTO.setDireccionEntregaNumero(contrato.getDireccionEntregaNumero());
		contratoTO.setDireccionEntregaObservaciones(contrato.getDireccionEntregaObservaciones());
		contratoTO.setDireccionEntregaSolar(contrato.getDireccionEntregaSolar());
		contratoTO.setDireccionFactura(contrato.getDireccionFactura());
		contratoTO.setDireccionFacturaApto(contrato.getDireccionFacturaApto());
		contratoTO.setDireccionFacturaBis(contrato.getDireccionFacturaBis());
		contratoTO.setDireccionFacturaBlock(contrato.getDireccionFacturaBlock());
		contratoTO.setDireccionFacturaCalle(contrato.getDireccionFacturaCalle());
		contratoTO.setDireccionFacturaCodigoPostal(contrato.getDireccionFacturaCodigoPostal());
		contratoTO.setDireccionFacturaLocalidad(contrato.getDireccionFacturaLocalidad());
		contratoTO.setDireccionFacturaManzana(contrato.getDireccionFacturaManzana());
		contratoTO.setDireccionFacturaNumero(contrato.getDireccionFacturaNumero());
		contratoTO.setDireccionFacturaObservaciones(contrato.getDireccionFacturaObservaciones());
		contratoTO.setDireccionFacturaSolar(contrato.getDireccionFacturaSolar());
		contratoTO.setDocumentoTipo(contrato.getDocumentoTipo());
		contratoTO.setDocumento(contrato.getDocumento());
		contratoTO.setEmail(contrato.getEmail());
		contratoTO.setEquipo(contrato.getEquipo());
		contratoTO.setFechaActivacion(contrato.getFechaActivacion());
		contratoTO.setFechaActivarEn(contrato.getFechaActivarEn());
		contratoTO.setFechaBackoffice(contrato.getFechaBackoffice());
		contratoTO.setFechaCoordinacion(contrato.getFechaCoordinacion());
		contratoTO.setFechaDevolucionDistribuidor(contrato.getFechaDevolucionDistribuidor());
		contratoTO.setFechaEnvioAntel(contrato.getFechaEnvioAntel());
		contratoTO.setFechaEntregaDistribuidor(contrato.getFechaEntregaDistribuidor());
		contratoTO.setFechaEntrega(contrato.getFechaEntrega());
		contratoTO.setFechaFinContrato(contrato.getFechaFinContrato());
		contratoTO.setFechaNacimiento(contrato.getFechaNacimiento());
		contratoTO.setFechaRechazo(contrato.getFechaRechazo());
		contratoTO.setFechaVenta(contrato.getFechaVenta());
		contratoTO.setGastosAdministrativos(contrato.getGastosAdministrativos());
		contratoTO.setGastosAdministrativosTotales(contrato.getGastosAdministrativosTotales());
		contratoTO.setGastosConcesion(contrato.getGastosConcesion());
		contratoTO.setIntereses(contrato.getIntereses());
		contratoTO.setLocalidad(contrato.getLocalidad());
		contratoTO.setMid(contrato.getMid());
		contratoTO.setNombre(contrato.getNombre());
		contratoTO.setNuevoPlanString(contrato.getNuevoPlanString());
		contratoTO.setNumeroBloqueo(contrato.getNumeroBloqueo());
		contratoTO.setNumeroChip(contrato.getNumeroChip());
		contratoTO.setNumeroCliente(contrato.getNumeroCliente());
		contratoTO.setNumeroContrato(contrato.getNumeroContrato());
		contratoTO.setNumeroFactura(contrato.getNumeroFactura());
		contratoTO.setNumeroFacturaRiverGreen(contrato.getNumeroFacturaRiverGreen());
		contratoTO.setNumeroSerie(contrato.getNumeroSerie());
		contratoTO.setNumeroTramite(contrato.getNumeroTramite());
		contratoTO.setObservaciones(contrato.getObservaciones());
		contratoTO.setPrecio(contrato.getPrecio());
		contratoTO.setResultadoEntregaDistribucionLatitud(contrato.getResultadoEntregaDistribucionLatitud());
		contratoTO.setResultadoEntregaDistribucionLongitud(contrato.getResultadoEntregaDistribucionLongitud());
		contratoTO.setResultadoEntregaDistribucionObservaciones(contrato.getResultadoEntregaDistribucionObservaciones());
		contratoTO.setResultadoEntregaDistribucionPrecision(contrato.getResultadoEntregaDistribucionPrecision());
		contratoTO.setResultadoEntregaDistribucionURLAnverso(contrato.getResultadoEntregaDistribucionURLAnverso());
		contratoTO.setResultadoEntregaDistribucionURLReverso(contrato.getResultadoEntregaDistribucionURLReverso());
		contratoTO.setTelefonoContacto(contrato.getTelefonoContacto());
		contratoTO.setTipoContratoCodigo(contrato.getTipoContratoCodigo());
		contratoTO.setTipoContratoDescripcion(contrato.getTipoContratoDescripcion());
		contratoTO.setValorCuota(contrato.getValorCuota());
		contratoTO.setValorUnidadIndexada(contrato.getValorUnidadIndexada());
		contratoTO.setValorTasaInteresEfectivaAnual(contrato.getValorTasaInteresEfectivaAnual());
		
		if (contrato.getBarrio() != null) {
			contratoTO.setBarrio(BarrioDWR.transform(contrato.getBarrio()));
		}
		if (contrato.getDireccionEntregaDepartamento() != null) {
			contratoTO.setDireccionEntregaDepartamento(DepartamentoDWR.transform(contrato.getDireccionEntregaDepartamento()));
		}
		if (contrato.getDireccionFacturaDepartamento() != null) {
			contratoTO.setDireccionFacturaDepartamento(DepartamentoDWR.transform(contrato.getDireccionFacturaDepartamento()));
		}
		if (contrato.getEstado() != null) {
			contratoTO.setEstado(EstadoDWR.transform(contrato.getEstado()));
		}
		if (contrato.getEmpresa() != null) {
			contratoTO.setEmpresa(EmpresaDWR.transform(contrato.getEmpresa()));
		}
<<<<<<< HEAD
		if (contrato.getFormaPago() != null) {
			contratoTO.setFormaPago(FormaPagoDWR.transform(contrato.getFormaPago()));
		}
		if (contrato.getMoneda() != null) {
			contratoTO.setMoneda(MonedaDWR.transform(contrato.getMoneda()));
		}
		if (contrato.getNuevoPlan() != null) {
			contratoTO.setNuevoPlan(PlanDWR.transform(contrato.getNuevoPlan()));
		}
		if (contrato.getMotivoCambioPlan() != null) {
			contratoTO.setMotivoCambioPlan(MotivoCambioPlanDWR.transform(contrato.getMotivoCambioPlan()));
		}
		if (contrato.getMarca() != null) {
			contratoTO.setMarca(MarcaDWR.transform(contrato.getMarca()));
		}
		if (contrato.getModelo() != null) {
			contratoTO.setModelo(ModeloDWR.transform(contrato.getModelo()));
=======
		if (contrato.getNuevoPlan() != null) {
			contratoTO.setNuevoPlan(PlanDWR.transform(contrato.getNuevoPlan()));
		}
		if (contrato.getMotivoCambioPlan() != null) {
			contratoTO.setMotivoCambioPlan(MotivoCambioPlanDWR.transform(contrato.getMotivoCambioPlan()));
>>>>>>> branch 'master' of https://github.com/batliber/Logistica.git
		}
		if (contrato.getProducto() != null) {
			contratoTO.setProducto(ProductoDWR.transform(contrato.getProducto()));
		}
		if (contrato.getResultadoEntregaDistribucion() != null) {
			contratoTO.setResultadoEntregaDistribucion(ResultadoEntregaDistribucionDWR.transform(contrato.getResultadoEntregaDistribucion()));
		}
		if (contrato.getRol() != null) {
			contratoTO.setRol(RolDWR.transform(contrato.getRol()));
		}
<<<<<<< HEAD
		if (contrato.getTarjetaCredito() != null) {
			contratoTO.setTarjetaCredito(TarjetaCreditoDWR.transform(contrato.getTarjetaCredito()));
		}
=======
>>>>>>> branch 'master' of https://github.com/batliber/Logistica.git
		if (contrato.getTipoDocumento() != null) {
			contratoTO.setTipoDocumento(TipoDocumentoDWR.transform(contrato.getTipoDocumento()));
		}
		if (contrato.getSexo() != null) {
			contratoTO.setSexo(SexoDWR.transform(contrato.getSexo()));
		}
		if (contrato.getTurno() != null) {
			contratoTO.setTurno(TurnoDWR.transform(contrato.getTurno()));
		}
		if (contrato.getUsuario() != null) {
			contratoTO.setUsuario(UsuarioDWR.transform(contrato.getUsuario(), false));
		}
		if (contrato.getZona() != null) {
			contratoTO.setZona(ZonaDWR.transform(contrato.getZona()));
		}
		
		if (contrato.getActivador() != null) {
			contratoTO.setActivador(UsuarioDWR.transform(contrato.getActivador(), false));
		}
		if (contrato.getBackoffice() != null) {
			contratoTO.setBackoffice(UsuarioDWR.transform(contrato.getBackoffice(), false));
		}
		if (contrato.getCoordinador() != null) {
			contratoTO.setCoordinador(UsuarioDWR.transform(contrato.getCoordinador(), false));
		}
		if (contrato.getDistribuidor() != null) {
			contratoTO.setDistribuidor(UsuarioDWR.transform(contrato.getDistribuidor(), false));
		}
		if (contrato.getVendedor() != null) {
			contratoTO.setVendedor(UsuarioDWR.transform(contrato.getVendedor(), false));
		}
		
		if (contrato.getArchivosAdjuntos() != null) {
			Collection<ContratoArchivoAdjuntoTO> archivosAdjuntos = new LinkedList<ContratoArchivoAdjuntoTO>();
			
			for (ContratoArchivoAdjunto archivoAdjunto : contrato.getArchivosAdjuntos()) {
				archivosAdjuntos.add(ContratoArchivoAdjuntoDWR.transform(archivoAdjunto));
			}
			
			contratoTO.setArchivosAdjuntos(archivosAdjuntos);
		}
		
		contratoTO.setFact(contrato.getFact());
		contratoTO.setId(contrato.getId());
		contratoTO.setUact(contrato.getUact());
		contratoTO.setTerm(contrato.getTerm());
		
		return contratoTO;
	}

	public static Contrato transform(ContratoTO contratoTO) {
		Contrato contrato = new Contrato();
		
		contrato.setAgente(contratoTO.getAgente());
		contrato.setApellido(contratoTO.getApellido());
		contrato.setCodigoPostal(contratoTO.getCodigoPostal());
		contrato.setCuotas(contratoTO.getCuotas());
		contrato.setDireccion(contratoTO.getDireccion());
		contrato.setDireccionEntrega(contratoTO.getDireccionEntrega());
		contrato.setDireccionEntregaApto(contratoTO.getDireccionEntregaApto());
		contrato.setDireccionEntregaBis(contratoTO.getDireccionEntregaBis());
		contrato.setDireccionEntregaBlock(contratoTO.getDireccionEntregaBlock());
		contrato.setDireccionEntregaCalle(contratoTO.getDireccionEntregaCalle());
		contrato.setDireccionEntregaCodigoPostal(contratoTO.getDireccionEntregaCodigoPostal());
		contrato.setDireccionEntregaLocalidad(contratoTO.getDireccionEntregaLocalidad());
		contrato.setDireccionEntregaManzana(contratoTO.getDireccionEntregaManzana());
		contrato.setDireccionEntregaNumero(contratoTO.getDireccionEntregaNumero());
		contrato.setDireccionEntregaObservaciones(contratoTO.getDireccionEntregaObservaciones());
		contrato.setDireccionEntregaSolar(contratoTO.getDireccionEntregaSolar());
		contrato.setDireccionFactura(contratoTO.getDireccionFactura());
		contrato.setDireccionFacturaApto(contratoTO.getDireccionFacturaApto());
		contrato.setDireccionFacturaBis(contratoTO.getDireccionFacturaBis());
		contrato.setDireccionFacturaBlock(contratoTO.getDireccionFacturaBlock());
		contrato.setDireccionFacturaCalle(contratoTO.getDireccionFacturaCalle());
		contrato.setDireccionFacturaCodigoPostal(contratoTO.getDireccionFacturaCodigoPostal());
		contrato.setDireccionFacturaLocalidad(contratoTO.getDireccionFacturaLocalidad());
		contrato.setDireccionFacturaManzana(contratoTO.getDireccionFacturaManzana());
		contrato.setDireccionFacturaNumero(contratoTO.getDireccionFacturaNumero());
		contrato.setDireccionFacturaObservaciones(contratoTO.getDireccionFacturaObservaciones());
		contrato.setDireccionFacturaSolar(contratoTO.getDireccionFacturaSolar());
		contrato.setDocumentoTipo(contratoTO.getDocumentoTipo());
		contrato.setDocumento(contratoTO.getDocumento());
		contrato.setEmail(contratoTO.getEmail());
		contrato.setEquipo(contratoTO.getEquipo());
		contrato.setFechaActivacion(contratoTO.getFechaActivacion());
		contrato.setFechaActivarEn(contratoTO.getFechaActivarEn());
		contrato.setFechaBackoffice(contratoTO.getFechaBackoffice());
		contrato.setFechaCoordinacion(contratoTO.getFechaCoordinacion());
		contrato.setFechaDevolucionDistribuidor(contratoTO.getFechaDevolucionDistribuidor());
		contrato.setFechaEntregaDistribuidor(contratoTO.getFechaEntregaDistribuidor());
		contrato.setFechaEntrega(contratoTO.getFechaEntrega());
		contrato.setFechaEnvioAntel(contratoTO.getFechaEnvioAntel());
		contrato.setFechaFinContrato(contratoTO.getFechaFinContrato());
		contrato.setFechaNacimiento(contratoTO.getFechaNacimiento());
		contrato.setFechaRechazo(contratoTO.getFechaRechazo());
		contrato.setFechaVenta(contratoTO.getFechaVenta());
		contrato.setGastosAdministrativos(contratoTO.getGastosAdministrativos());
		contrato.setGastosAdministrativosTotales(contratoTO.getGastosAdministrativosTotales());
		contrato.setGastosConcesion(contratoTO.getGastosConcesion());
		contrato.setIntereses(contratoTO.getIntereses());
		contrato.setLocalidad(contratoTO.getLocalidad());
		contrato.setMid(contratoTO.getMid());
		contrato.setNombre(contratoTO.getNombre());
		contrato.setNuevoPlanString(contratoTO.getNuevoPlanString());
		contrato.setNumeroBloqueo(contratoTO.getNumeroBloqueo());
		contrato.setNumeroChip(contratoTO.getNumeroChip());
		contrato.setNumeroCliente(contratoTO.getNumeroCliente());
		contrato.setNumeroContrato(contratoTO.getNumeroContrato());
		contrato.setNumeroFactura(contratoTO.getNumeroFactura());
		contrato.setNumeroFacturaRiverGreen(contratoTO.getNumeroFacturaRiverGreen());
		contrato.setNumeroSerie(contratoTO.getNumeroSerie());
		contrato.setNumeroTramite(contratoTO.getNumeroTramite());
		contrato.setObservaciones(contratoTO.getObservaciones());
		contrato.setPrecio(contratoTO.getPrecio());
		contrato.setResultadoEntregaDistribucionLatitud(contratoTO.getResultadoEntregaDistribucionLatitud());
		contrato.setResultadoEntregaDistribucionLongitud(contratoTO.getResultadoEntregaDistribucionLongitud());
		contrato.setResultadoEntregaDistribucionObservaciones(contratoTO.getResultadoEntregaDistribucionObservaciones());
		contrato.setResultadoEntregaDistribucionPrecision(contratoTO.getResultadoEntregaDistribucionPrecision());
		contrato.setResultadoEntregaDistribucionURLAnverso(contratoTO.getResultadoEntregaDistribucionURLAnverso());
		contrato.setResultadoEntregaDistribucionURLReverso(contratoTO.getResultadoEntregaDistribucionURLReverso());
		contrato.setTelefonoContacto(contratoTO.getTelefonoContacto());
		contrato.setTipoContratoCodigo(contratoTO.getTipoContratoCodigo());
		contrato.setTipoContratoDescripcion(contratoTO.getTipoContratoDescripcion());
		contrato.setValorCuota(contratoTO.getValorCuota());
		contrato.setValorUnidadIndexada(contratoTO.getValorUnidadIndexada());
		contrato.setValorTasaInteresEfectivaAnual(contratoTO.getValorTasaInteresEfectivaAnual());
		
		if (contratoTO.getDireccionEntregaDepartamento() != null) {
			Departamento direccionEntregaDepartamento = new Departamento();
			direccionEntregaDepartamento.setId(contratoTO.getDireccionEntregaDepartamento().getId());
			
			contrato.setDireccionEntregaDepartamento(direccionEntregaDepartamento);
		}
		if (contratoTO.getDireccionFacturaDepartamento() != null) {
			Departamento direccionFacturaDepartamento = new Departamento();
			direccionFacturaDepartamento.setId(contratoTO.getDireccionFacturaDepartamento().getId());
			
			contrato.setDireccionFacturaDepartamento(direccionFacturaDepartamento);
		}
<<<<<<< HEAD
		if (contratoTO.getFormaPago() != null) {
			FormaPago formaPago = new FormaPago();
			formaPago.setId(contratoTO.getFormaPago().getId());
			
			contrato.setFormaPago(formaPago);
		}
		if (contratoTO.getMoneda() != null) {
			Moneda moneda = new Moneda();
			moneda.setId(contratoTO.getMoneda().getId());
			
			contrato.setMoneda(moneda);
		}
		if (contratoTO.getTarjetaCredito() != null) {
			TarjetaCredito tarjetaCredito = new TarjetaCredito();
			tarjetaCredito.setId(contratoTO.getTarjetaCredito().getId());
			
			contrato.setTarjetaCredito(tarjetaCredito);
		}
		if (contratoTO.getMarca() != null) {
			Marca marca = new Marca();
			marca.setId(contratoTO.getMarca().getId());
			
			contrato.setMarca(marca);
		}
		if (contratoTO.getModelo() != null) {
			Modelo modelo = new Modelo();
			modelo.setId(contratoTO.getModelo().getId());
			
			contrato.setModelo(modelo);
		}
=======
>>>>>>> branch 'master' of https://github.com/batliber/Logistica.git
		if (contratoTO.getProducto() != null) {
			Producto producto = new Producto();
			producto.setId(contratoTO.getProducto().getId());
			
			contrato.setProducto(producto);
		}
		if (contratoTO.getNuevoPlan() != null) {
			Plan nuevoPlan = new Plan();
			nuevoPlan.setId(contratoTO.getNuevoPlan().getId());
			
			contrato.setNuevoPlan(nuevoPlan);
		}
		if (contratoTO.getMotivoCambioPlan() != null) {
			MotivoCambioPlan motivoCambioPlan = new MotivoCambioPlan();
			motivoCambioPlan.setId(contratoTO.getMotivoCambioPlan().getId());
			
			contrato.setMotivoCambioPlan(motivoCambioPlan);
		}
		if (contratoTO.getResultadoEntregaDistribucion() != null) {
			ResultadoEntregaDistribucion resultadoEntregaDistribucion = new ResultadoEntregaDistribucion();
			resultadoEntregaDistribucion.setId(contratoTO.getResultadoEntregaDistribucion().getId());
			
			contrato.setResultadoEntregaDistribucion(resultadoEntregaDistribucion);
		}
		if (contratoTO.getTipoDocumento() != null) {
			TipoDocumento tipoDocumento = new TipoDocumento();
			tipoDocumento.setId(contratoTO.getTipoDocumento().getId());
			
			contrato.setTipoDocumento(tipoDocumento);
		}
		if (contratoTO.getSexo() != null) {
			Sexo sexo = new Sexo();
			sexo.setId(contratoTO.getSexo().getId());
			
			contrato.setSexo(sexo);
		}
		if (contratoTO.getTurno() != null) {
			Turno turno = new Turno();
			turno.setId(contratoTO.getTurno().getId());
			
			contrato.setTurno(turno);
		}
		if (contratoTO.getBarrio() != null) {
			Barrio barrio = new Barrio();
			barrio.setId(contratoTO.getBarrio().getId());
			
			contrato.setBarrio(barrio);
		}
		if (contratoTO.getZona() != null) {
			Zona zona = new Zona();
			zona.setId(contratoTO.getZona().getId());
			
			contrato.setZona(zona);
		}
		if (contratoTO.getEstado() != null) {
			Estado estado = new Estado();
			estado.setId(contratoTO.getEstado().getId());
			
			contrato.setEstado(estado);
		}
		if (contratoTO.getEmpresa() != null) {
			Empresa empresa = new Empresa();
			empresa.setId(contratoTO.getEmpresa().getId());
			
			contrato.setEmpresa(empresa);
		}
		if (contratoTO.getRol() != null) {
			Rol rol = new Rol();
			rol.setId(contratoTO.getRol().getId());
			
			contrato.setRol(rol);
		}
		if (contratoTO.getUsuario() != null) {
			Usuario usuario = new Usuario();
			usuario.setId(contratoTO.getUsuario().getId());
			
			contrato.setUsuario(usuario);
		}
		if (contratoTO.getActivador() != null) {
			Usuario activador = new Usuario();
			activador.setId(contratoTO.getActivador().getId());
			
			contrato.setActivador(activador);
		}
		if (contratoTO.getBackoffice() != null) {
			Usuario backoffice = new Usuario();
			backoffice.setId(contratoTO.getBackoffice().getId());
			
			contrato.setBackoffice(backoffice);
		}
		if (contratoTO.getCoordinador() != null) {
			Usuario coordinador = new Usuario();
			coordinador.setId(contratoTO.getCoordinador().getId());
			
			contrato.setCoordinador(coordinador);
		}
		if (contratoTO.getDistribuidor() != null) {
			Usuario distribuidor = new Usuario();
			distribuidor.setId(contratoTO.getDistribuidor().getId());
			
			contrato.setDistribuidor(distribuidor);
		}
		if (contratoTO.getVendedor() != null) {
			Usuario vendedor = new Usuario();
			vendedor.setId(contratoTO.getVendedor().getId());
			
			contrato.setVendedor(vendedor);
		}
		
		Date date = GregorianCalendar.getInstance().getTime();
		
		HttpSession httpSession = WebContextFactory.get().getSession(false);
		Long usuarioId = (Long) httpSession.getAttribute("sesion");
		
		contrato.setFact(date);
		contrato.setId(contratoTO.getId());
		contrato.setTerm(new Long(1));
		
		contrato.setUact(usuarioId);
		
		return contrato;
	}
}