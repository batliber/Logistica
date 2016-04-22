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
import uy.com.amensg.logistica.entities.ContratoTO;
import uy.com.amensg.logistica.entities.Empresa;
import uy.com.amensg.logistica.entities.Estado;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.MetadataConsultaResultadoTO;
import uy.com.amensg.logistica.entities.MetadataConsultaTO;
import uy.com.amensg.logistica.entities.Producto;
import uy.com.amensg.logistica.entities.ResultadoEntregaDistribucion;
import uy.com.amensg.logistica.entities.Rol;
import uy.com.amensg.logistica.entities.Turno;
import uy.com.amensg.logistica.entities.Usuario;
import uy.com.amensg.logistica.entities.UsuarioTO;
import uy.com.amensg.logistica.entities.Zona;

@RemoteProxy
public class ContratoDWR {

	private IContratoBean lookupBean() throws NamingException {
		String EARName = "Logistica";
		String beanName = ContratoBean.class.getSimpleName();
		String remoteInterfaceName = IContratoBean.class.getName();
		String lookupName = EARName + "/" + beanName + "/remote-" + remoteInterfaceName;
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
	
	public void update(ContratoTO contratoTO) {
		try {
			IContratoBean iContratoBean = lookupBean();
			
			iContratoBean.update(transform(contratoTO));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static ContratoTO transform(Contrato contrato) {
		ContratoTO contratoTO = new ContratoTO();
		
		contratoTO.setAgente(contrato.getAgente());
		contratoTO.setCodigoPostal(contrato.getCodigoPostal());
		contratoTO.setDireccion(contrato.getDireccion());
		contratoTO.setDireccionEntrega(contrato.getDireccionEntrega());
		contratoTO.setDireccionFactura(contrato.getDireccionFactura());
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
		contratoTO.setLocalidad(contrato.getLocalidad());
		contratoTO.setMid(contrato.getMid());
		contratoTO.setNombre(contrato.getNombre());
		contratoTO.setNuevoPlan(contrato.getNuevoPlan());
		contratoTO.setNumeroCliente(contrato.getNumeroCliente());
		contratoTO.setNumeroContrato(contrato.getNumeroContrato());
		contratoTO.setNumeroFactura(contrato.getNumeroFactura());
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
		
		if (contrato.getProducto() != null) {
			contratoTO.setProducto(ProductoDWR.transform(contrato.getProducto()));
		}
		if (contrato.getResultadoEntregaDistribucion() != null) {
			contratoTO.setResultadoEntregaDistribucion(ResultadoEntregaDistribucionDWR.transform(contrato.getResultadoEntregaDistribucion()));
		}
		if (contrato.getTurno() != null) {
			contratoTO.setTurno(TurnoDWR.transform(contrato.getTurno()));
		}
		if (contrato.getBarrio() != null) {
			contratoTO.setBarrio(BarrioDWR.transform(contrato.getBarrio()));
		}
		if (contrato.getZona() != null) {
			contratoTO.setZona(ZonaDWR.transform(contrato.getZona()));
		}
		if (contrato.getEstado() != null) {
			contratoTO.setEstado(EstadoDWR.transform(contrato.getEstado()));
		}
		if (contrato.getEmpresa() != null) {
			contratoTO.setEmpresa(EmpresaDWR.transform(contrato.getEmpresa()));
		}
		if (contrato.getRol() != null) {
			contratoTO.setRol(RolDWR.transform(contrato.getRol()));
		}
		if (contrato.getUsuario() != null) {
			contratoTO.setUsuario(UsuarioDWR.transform(contrato.getUsuario(), false));
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
		
		contratoTO.setFact(contrato.getFact());
		contratoTO.setId(contrato.getId());
		contratoTO.setUact(contrato.getUact());
		contratoTO.setTerm(contrato.getTerm());
		
		return contratoTO;
	}

	public static Contrato transform(ContratoTO contratoTO) {
		Contrato contrato = new Contrato();
		
		contrato.setAgente(contratoTO.getAgente());
		contrato.setCodigoPostal(contratoTO.getCodigoPostal());
		contrato.setDireccion(contratoTO.getDireccion());
		contrato.setDireccionEntrega(contratoTO.getDireccionEntrega());
		contrato.setDireccionFactura(contratoTO.getDireccionFactura());
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
		contrato.setLocalidad(contratoTO.getLocalidad());
		contrato.setMid(contratoTO.getMid());
		contrato.setNombre(contratoTO.getNombre());
		contrato.setNuevoPlan(contratoTO.getNuevoPlan());
		contrato.setNumeroCliente(contratoTO.getNumeroCliente());
		contrato.setNumeroContrato(contratoTO.getNumeroContrato());
		contrato.setNumeroFactura(contratoTO.getNumeroFactura());
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
		
		if (contratoTO.getProducto() != null) {
			Producto producto = new Producto();
			producto.setId(contratoTO.getProducto().getId());
			
			contrato.setProducto(producto);
		}
		if (contratoTO.getResultadoEntregaDistribucion() != null) {
			ResultadoEntregaDistribucion resultadoEntregaDistribucion = new ResultadoEntregaDistribucion();
			resultadoEntregaDistribucion.setId(contratoTO.getResultadoEntregaDistribucion().getId());
			
			contrato.setResultadoEntregaDistribucion(resultadoEntregaDistribucion);
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