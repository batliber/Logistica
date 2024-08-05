package uy.com.amensg.logistica.web.webservices.rest;

import java.util.HashSet;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import uy.com.amensg.logistica.bean.ACMInterfacePrepagoBean;
import uy.com.amensg.logistica.bean.IACMInterfacePrepagoBean;
import uy.com.amensg.logistica.entities.ACMInterfacePrepago;
import uy.com.amensg.logistica.entities.Empresa;
import uy.com.amensg.logistica.entities.FormaPago;
import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.TipoControlRiesgoCrediticio;
import uy.com.amensg.logistica.entities.Usuario;
import uy.com.amensg.logistica.web.entities.AsignacionTO;
import uy.com.amensg.logistica.web.entities.ControlarRiesgoCrediticioTO;
import uy.com.amensg.logistica.web.entities.PreprocesarAsignacionTO;
import uy.com.amensg.logistica.web.entities.ReprocesarTO;
import uy.com.amensg.logistica.web.entities.ResultadoExportacionArchivoTO;
import uy.com.amensg.logistica.web.entities.ResultadoPreprocesarAsignacionTO;

@Path("/ACMInterfacePrepagoREST")
public class ACMInterfacePrepagoREST {

	@POST
	@Path("/listContextAware")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public MetadataConsultaResultado listContextAware(
		MetadataConsulta metadataConsulta, @Context HttpServletRequest request
	) {
		MetadataConsultaResultado result = new MetadataConsultaResultado();
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				IACMInterfacePrepagoBean iACMInterfacePrepagoBean = lookupBean();
				
				result = iACMInterfacePrepagoBean.list(metadataConsulta);
				
				for (Object object : result.getRegistrosMuestra()) {
					ACMInterfacePrepago acmInterfacePrepago = (ACMInterfacePrepago) object;
					
					if (acmInterfacePrepago.getAcmInterfacePersona() != null) {
						if (acmInterfacePrepago.getAcmInterfacePersona().getRiesgoCrediticio() != null) {
							acmInterfacePrepago
								.getAcmInterfacePersona()
								.getRiesgoCrediticio()
								.getEmpresa()
								.setFormaPagos(new HashSet<FormaPago>());
							acmInterfacePrepago
								.getAcmInterfacePersona()
								.getRiesgoCrediticio()
								.getEmpresa().setEmpresaUsuarioContratos(new HashSet<Usuario>());
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@POST
	@Path("/countContextAware")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Long countContextAware(
		MetadataConsulta metadataConsulta, @Context HttpServletRequest request
	) {
		Long result = null;
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				IACMInterfacePrepagoBean iACMInterfacePrepagoBean = lookupBean();
				
				result = iACMInterfacePrepagoBean.count(metadataConsulta);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@POST
	@Path("/exportarAExcel")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ResultadoExportacionArchivoTO exportarAExcel(
		MetadataConsulta metadataConsulta, @Context HttpServletRequest request
	) {
		ResultadoExportacionArchivoTO result = null;
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				IACMInterfacePrepagoBean iACMInterfacePrepagoBean = lookupBean();
				
				String nombreArchivo = 
					iACMInterfacePrepagoBean.exportarAExcel(metadataConsulta, usuarioId);
				
				if (nombreArchivo != null) {
					result = new ResultadoExportacionArchivoTO();
					result.setNombreArchivo(nombreArchivo);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@POST
	@Path("/preprocesarAsignacionByEmpresa")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ResultadoPreprocesarAsignacionTO preprocesarAsignacionByEmpresa(
		PreprocesarAsignacionTO preprocesarAsignacionTO, @Context HttpServletRequest request
	) {
		ResultadoPreprocesarAsignacionTO result = null;
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				IACMInterfacePrepagoBean iACMInterfacePrepagoBean = lookupBean();
				
				result = new ResultadoPreprocesarAsignacionTO();
				result.setDatos(
					iACMInterfacePrepagoBean.preprocesarAsignacion(
						preprocesarAsignacionTO.getMetadataConsulta(),
						preprocesarAsignacionTO.getEmpresa()
					)
				);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@POST
	@Path("/asignarByEmpresa")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ResultadoExportacionArchivoTO asignarByEmpresa(
		AsignacionTO asignacionTO, @Context HttpServletRequest request
	) {
		ResultadoExportacionArchivoTO result = null;
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				IACMInterfacePrepagoBean iACMInterfacePrepagoBean = lookupBean();
				
				result = new ResultadoExportacionArchivoTO();
				result.setNombreArchivo(
					iACMInterfacePrepagoBean.asignar(
						asignacionTO.getMetadataConsulta(),
						asignacionTO.getEmpresa(),
						asignacionTO.getObservaciones()
					)
				);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@POST
	@Path("/deshacerAsignacion")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public void deshacerAsignacion(MetadataConsulta metadataConsulta, @Context HttpServletRequest request) {
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				IACMInterfacePrepagoBean iACMInterfacePrepagoBean = lookupBean();
				
				iACMInterfacePrepagoBean.deshacerAsignacion(metadataConsulta);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@POST
	@Path("/reprocesar")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public void reprocesar(ReprocesarTO reprocesarTO, @Context HttpServletRequest request) {
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				IACMInterfacePrepagoBean iACMInterfacePrepagoBean = lookupBean();
				
				iACMInterfacePrepagoBean.reprocesar(
					reprocesarTO.getMetadataConsulta(),
					reprocesarTO.getObservaciones()
				);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@POST
	@Path("/controlarRiesgoCrediticio")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public void controlarRiesgoCrediticio(
		ControlarRiesgoCrediticioTO controlarRiesgoCrediticioTO, @Context HttpServletRequest request
	) {
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				IACMInterfacePrepagoBean iACMInterfacePrepagoBean = lookupBean();
				
				Empresa empresa = new Empresa();
				empresa.setId(controlarRiesgoCrediticioTO.getEmpresaId());
				
				TipoControlRiesgoCrediticio tipoControlRiesgoCrediticio = new TipoControlRiesgoCrediticio();
				tipoControlRiesgoCrediticio.setId(
					controlarRiesgoCrediticioTO.getTipoControlRiesgoCrediticioId()
				);
				
				iACMInterfacePrepagoBean.controlarRiesgoCrediticio(
					empresa,
					tipoControlRiesgoCrediticio,
					controlarRiesgoCrediticioTO.getMetadataConsulta()
				);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@POST
	@Path("/agregarAListaNegra")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public void agregarAListaNegra(MetadataConsulta metadataConsulta, @Context HttpServletRequest request) {
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				IACMInterfacePrepagoBean iACMInterfacePrepagoBean = lookupBean();
				
				iACMInterfacePrepagoBean.agregarAListaNegra(metadataConsulta);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private IACMInterfacePrepagoBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String remoteInterfaceName = IACMInterfacePrepagoBean.class.getName();
		String beanName = ACMInterfacePrepagoBean.class.getSimpleName();
		String lookupName = 
			prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		
		javax.naming.Context context = new InitialContext();
		
		return (IACMInterfacePrepagoBean) context.lookup(lookupName);
	}
}