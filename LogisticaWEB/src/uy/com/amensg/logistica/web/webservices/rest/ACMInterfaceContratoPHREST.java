package uy.com.amensg.logistica.web.webservices.rest;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;

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
import uy.com.amensg.logistica.bean.ACMInterfaceContratoPHBean;
import uy.com.amensg.logistica.bean.IACMInterfaceContratoPHBean;
import uy.com.amensg.logistica.entities.ACMInterfaceContrato;
import uy.com.amensg.logistica.entities.FormaPago;
import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.TipoContrato;
import uy.com.amensg.logistica.entities.Usuario;
import uy.com.amensg.logistica.web.entities.AsignacionTO;
import uy.com.amensg.logistica.web.entities.PreprocesarAsignacionTO;
import uy.com.amensg.logistica.web.entities.ReprocesarTO;
import uy.com.amensg.logistica.web.entities.ResultadoExportacionArchivoTO;
import uy.com.amensg.logistica.web.entities.ResultadoPreprocesarAsignacionTO;

@Path("/ACMInterfaceContratoPHREST")
public class ACMInterfaceContratoPHREST {

	@POST
	@Path("/listTipoContratosContextAware")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Collection<TipoContrato> listTipoContratos(
		MetadataConsulta metadataConsulta, @Context HttpServletRequest request
	) {
		Collection<TipoContrato> result = new LinkedList<TipoContrato>();
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				IACMInterfaceContratoPHBean iACMInterfaceContratoPHBean = lookupBean();
				
				result = iACMInterfaceContratoPHBean.listTipoContratos(metadataConsulta);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
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
				IACMInterfaceContratoPHBean iACMInterfaceContratoPHBean = lookupBean();
				
				result = iACMInterfaceContratoPHBean.list(metadataConsulta);
				
				for (Object object : result.getRegistrosMuestra()) {
					ACMInterfaceContrato acmInterfaceContratoPH = (ACMInterfaceContrato) object;
					
					if (acmInterfaceContratoPH.getAcmInterfacePersona() != null) {
						if (acmInterfaceContratoPH.getAcmInterfacePersona().getRiesgoCrediticio() != null) {
							acmInterfaceContratoPH
								.getAcmInterfacePersona()
								.getRiesgoCrediticio()
								.getEmpresa()
								.setFormaPagos(new HashSet<FormaPago>());
							acmInterfaceContratoPH
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
				IACMInterfaceContratoPHBean iACMInterfaceContratoPHBean = lookupBean();
				
				result = iACMInterfaceContratoPHBean.count(metadataConsulta);
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
				
				IACMInterfaceContratoPHBean iACMInterfaceContratoPHBean = lookupBean();
				
				String nombreArchivo = 
					iACMInterfaceContratoPHBean.exportarAExcel(metadataConsulta, usuarioId);
				
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
				IACMInterfaceContratoPHBean iACMInterfaceContratoPHBean = lookupBean();
				
				result = new ResultadoPreprocesarAsignacionTO();
				result.setDatos(
					iACMInterfaceContratoPHBean.preprocesarAsignacion(
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
				IACMInterfaceContratoPHBean iACMInterfaceContratoPHBean = lookupBean();
				
				result = new ResultadoExportacionArchivoTO();
				result.setNombreArchivo(
					iACMInterfaceContratoPHBean.asignar(
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
				IACMInterfaceContratoPHBean iACMInterfaceContratoPHBean = lookupBean();
				
				iACMInterfaceContratoPHBean.deshacerAsignacion(metadataConsulta);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@POST
	@Path("/reprocesarPorMID")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public void reprocesarPorMID(ReprocesarTO reprocesarTO, @Context HttpServletRequest request) {
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				IACMInterfaceContratoPHBean iACMInterfaceContratoPHBean = lookupBean();
				
				iACMInterfaceContratoPHBean.reprocesarPorMID(
					reprocesarTO.getMetadataConsulta(),
					reprocesarTO.getObservaciones()
				);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@POST
	@Path("/reprocesarPorNumeroContrato")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public void reprocesarPorNumeroContrato(ReprocesarTO reprocesarTO, @Context HttpServletRequest request) {
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				IACMInterfaceContratoPHBean iACMInterfaceContratoPHBean = lookupBean();
				
				iACMInterfaceContratoPHBean.reprocesarPorNumeroContrato(
					reprocesarTO.getMetadataConsulta(),
					reprocesarTO.getObservaciones()
				);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private IACMInterfaceContratoPHBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = ACMInterfaceContratoPHBean.class.getSimpleName();
		String remoteInterfaceName = IACMInterfaceContratoPHBean.class.getName();
		String lookupName = 
			prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;

		javax.naming.Context context = new InitialContext();
		
		return (IACMInterfaceContratoPHBean) context.lookup(lookupName);
	}
}