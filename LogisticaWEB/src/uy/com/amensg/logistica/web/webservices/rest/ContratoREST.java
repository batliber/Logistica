package uy.com.amensg.logistica.webservices.rest;

import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.LinkedList;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import uy.com.amensg.logistica.bean.ContratoBean;
import uy.com.amensg.logistica.bean.IContratoBean;
import uy.com.amensg.logistica.entities.AsignacionContratosUsuarioTO;
import uy.com.amensg.logistica.entities.ChequearAsignacionTO;
import uy.com.amensg.logistica.entities.ChequearRelacionesAsignacionTO;
import uy.com.amensg.logistica.entities.Contrato;
import uy.com.amensg.logistica.entities.ContratoArchivoAdjunto;
import uy.com.amensg.logistica.entities.FormaPago;
import uy.com.amensg.logistica.entities.ImportacionArchivoVentasANTELTO;
import uy.com.amensg.logistica.entities.ImportacionArchivoVentasTO;
import uy.com.amensg.logistica.entities.Menu;
import uy.com.amensg.logistica.entities.MetadataCondicion;
import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.PickUpTO;
import uy.com.amensg.logistica.entities.ResultadoAsignacionManualTO;
import uy.com.amensg.logistica.entities.ResultadoExportacionArchivoTO;
import uy.com.amensg.logistica.entities.ResultadoImportacionArchivoTO;
import uy.com.amensg.logistica.entities.ResultadoValidarVentaTO;
import uy.com.amensg.logistica.entities.Rol;
import uy.com.amensg.logistica.entities.TipoContrato;
import uy.com.amensg.logistica.entities.Usuario;
import uy.com.amensg.logistica.entities.UsuarioRolEmpresa;
import uy.com.amensg.logistica.util.Configuration;
import uy.com.amensg.logistica.util.Constants;

@Path("/ContratoREST")
public class ContratoREST {
	
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
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				IContratoBean iContratoBean = lookupBean();
				
				result = iContratoBean.listTipoContratos(metadataConsulta, usuarioId);
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
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				IContratoBean iContratoBean = lookupBean();
				
				result = iContratoBean.listDetached(metadataConsulta, usuarioId);
				
				for (Object object : result.getRegistrosMuestra()) {
					Contrato contrato = (Contrato) object;
					
					contrato.getEmpresa().setEmpresaUsuarioContratos(new HashSet<Usuario>());
					contrato.getEmpresa().setFormaPagos(new HashSet<FormaPago>());
					
					contrato.setArchivosAdjuntos(new HashSet<ContratoArchivoAdjunto>());
					
					if (contrato.getActivador() != null) {
						contrato.getActivador().setUsuarioRolEmpresas(new LinkedList<UsuarioRolEmpresa>());
					}
					
					if (contrato.getBackoffice() != null) {
						contrato.getBackoffice().setUsuarioRolEmpresas(new LinkedList<UsuarioRolEmpresa>());
					}
					
					if (contrato.getCoordinador() != null) {
						contrato.getCoordinador().setUsuarioRolEmpresas(new LinkedList<UsuarioRolEmpresa>());
					}
					
					if (contrato.getDistribuidor() != null) {
						contrato.getDistribuidor().setUsuarioRolEmpresas(new LinkedList<UsuarioRolEmpresa>());
					}
						
					if (contrato.getVendedor() != null) {
						contrato.getVendedor().setUsuarioRolEmpresas(new LinkedList<UsuarioRolEmpresa>());
					}
					
					if (contrato.getUsuario() != null) {
						contrato.getUsuario().setUsuarioRolEmpresas(new LinkedList<UsuarioRolEmpresa>());
					}
					
					if (contrato.getRol() != null) {
						contrato.getRol().setSubordinados(new HashSet<Rol>());
						contrato.getRol().setMenus(new HashSet<Menu>());
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
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				IContratoBean iContratoBean = lookupBean();
				
				result = iContratoBean.count(metadataConsulta, usuarioId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@POST
	@Path("/listNuestroCreditoContextAware")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public MetadataConsultaResultado listNuestroCreditoContextAware(
		MetadataConsulta metadataConsulta, @Context HttpServletRequest request
	) {
		MetadataConsultaResultado result = new MetadataConsultaResultado();
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				IContratoBean iContratoBean = lookupBean();
				
				MetadataCondicion metadataCondicion = new MetadataCondicion();
				metadataCondicion.setCampo("formaPago.id");
				metadataCondicion.setOperador(Constants.__METADATA_CONDICION_OPERADOR_IGUAL);
				
				Collection<String> valores = new LinkedList<String>();
				valores.add(Configuration.getInstance().getProperty("formaPago.NuestroCredito"));
				
				metadataCondicion.setValores(valores);
				
				metadataConsulta.getMetadataCondiciones().add(metadataCondicion);
				
				result = iContratoBean.list(metadataConsulta, usuarioId);
				
				for (Object object : result.getRegistrosMuestra()) {
					Contrato contrato = (Contrato) object;
					
					contrato.getEmpresa().setEmpresaUsuarioContratos(new HashSet<Usuario>());
					contrato.getEmpresa().setFormaPagos(new HashSet<FormaPago>());
					
					contrato.setArchivosAdjuntos(new HashSet<ContratoArchivoAdjunto>());
					
					if (contrato.getActivador() != null) {
						contrato.getActivador().setUsuarioRolEmpresas(new LinkedList<UsuarioRolEmpresa>());
					}
					
					if (contrato.getBackoffice() != null) {
						contrato.getBackoffice().setUsuarioRolEmpresas(new LinkedList<UsuarioRolEmpresa>());
					}
					
					if (contrato.getCoordinador() != null) {
						contrato.getCoordinador().setUsuarioRolEmpresas(new LinkedList<UsuarioRolEmpresa>());
					}
					
					if (contrato.getDistribuidor() != null) {
						contrato.getDistribuidor().setUsuarioRolEmpresas(new LinkedList<UsuarioRolEmpresa>());
					}
						
					if (contrato.getVendedor() != null) {
						contrato.getVendedor().setUsuarioRolEmpresas(new LinkedList<UsuarioRolEmpresa>());
					}
					
					if (contrato.getUsuario() != null) {
						contrato.getUsuario().setUsuarioRolEmpresas(new LinkedList<UsuarioRolEmpresa>());
					}
					
					if (contrato.getRol() != null) {
						contrato.getRol().setSubordinados(new HashSet<Rol>());
						contrato.getRol().setMenus(new HashSet<Menu>());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@POST
	@Path("/countNuestroCreditoContextAware")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Long countNuestroCreditoContextAware(
		MetadataConsulta metadataConsulta, @Context HttpServletRequest request
	) {
		Long result = null;
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				IContratoBean iContratoBean = lookupBean();
				
				MetadataCondicion metadataCondicion = new MetadataCondicion();
				metadataCondicion.setCampo("formaPago.id");
				metadataCondicion.setOperador(Constants.__METADATA_CONDICION_OPERADOR_IGUAL);
				
				Collection<String> valores = new LinkedList<String>();
				valores.add(Configuration.getInstance().getProperty("formaPago.NuestroCredito"));
				
				metadataCondicion.setValores(valores);
				
				metadataConsulta.getMetadataCondiciones().add(metadataCondicion);
				
				result = iContratoBean.count(metadataConsulta, usuarioId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@GET
	@Path("/getById/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Contrato getById(@PathParam("id") Long id, @Context HttpServletRequest request) {
		Contrato result = null;
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				IContratoBean iContratoBean = lookupBean();
				
				result = iContratoBean.getById(id, true);
				
				if (result.getArchivosAdjuntos() != null) {
					result.setArchivosAdjuntos(new HashSet<ContratoArchivoAdjunto>());
				}
				
				if (result.getActivador() != null) {
					result.getActivador().setUsuarioRolEmpresas(new LinkedList<UsuarioRolEmpresa>());
				}
				if (result.getBackoffice() != null)	{
					result.getBackoffice().setUsuarioRolEmpresas(new LinkedList<UsuarioRolEmpresa>());
				}
				
				if (result.getCoordinador() != null) {	
					result.getCoordinador().setUsuarioRolEmpresas(new LinkedList<UsuarioRolEmpresa>());
				}
				
				if (result.getDistribuidor() != null) {
					result.getDistribuidor().setUsuarioRolEmpresas(new LinkedList<UsuarioRolEmpresa>());
				}
				
				if (result.getEmpresa() != null) {
					result.getEmpresa().setEmpresaUsuarioContratos(new HashSet<Usuario>());
					result.getEmpresa().setFormaPagos(new HashSet<FormaPago>());
				}
				
				if (result.getRol() != null) {
					result.getRol().setMenus(new HashSet<Menu>());
					result.getRol().setSubordinados(new HashSet<Rol>());
				}
				
				if (result.getUsuario() != null) {
					result.getUsuario().setUsuarioRolEmpresas(new LinkedList<UsuarioRolEmpresa>());
				}
				
				if (result.getVendedor() != null) {
					result.getVendedor().setUsuarioRolEmpresas(new LinkedList<UsuarioRolEmpresa>());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@GET
	@Path("/getByNumeroTramite/{numeroTramite}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Contrato getByNumeroTramite(
		@PathParam("numeroTramite") Long numeroTramite, @Context HttpServletRequest request
	) {
		Contrato result = null;
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				IContratoBean iContratoBean = lookupBean();
				
				result = iContratoBean.getByNumeroTramite(numeroTramite, true);
				
				if (result.getActivador() != null) {
					result.getActivador().setUsuarioRolEmpresas(new LinkedList<UsuarioRolEmpresa>());
				}
				if (result.getBackoffice() != null)	{
					result.getBackoffice().setUsuarioRolEmpresas(new LinkedList<UsuarioRolEmpresa>());
				}
				
				if (result.getCoordinador() != null) {	
					result.getCoordinador().setUsuarioRolEmpresas(new LinkedList<UsuarioRolEmpresa>());
				}
				
				if (result.getDistribuidor() != null) {
					result.getDistribuidor().setUsuarioRolEmpresas(new LinkedList<UsuarioRolEmpresa>());
				}
				
				if (result.getEmpresa() != null) {
					result.getEmpresa().setEmpresaUsuarioContratos(new HashSet<Usuario>());
					result.getEmpresa().setFormaPagos(new HashSet<FormaPago>());
				}
				
				if (result.getRol() != null) {
					result.getRol().setMenus(new HashSet<Menu>());
					result.getRol().setSubordinados(new HashSet<Rol>());
				}
				
				if (result.getUsuario() != null) {
					result.getUsuario().setUsuarioRolEmpresas(new LinkedList<UsuarioRolEmpresa>());
				}
				
				if (result.getVendedor() != null) {
					result.getVendedor().setUsuarioRolEmpresas(new LinkedList<UsuarioRolEmpresa>());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@POST
	@Path("/procesarArchivo")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ResultadoImportacionArchivoTO procesarArchivo(
		ImportacionArchivoVentasTO importacionArchivoVentasTO, 
		@Context HttpServletRequest request
	) {
		ResultadoImportacionArchivoTO result = null;
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				IContratoBean iContratoBean = lookupBean();
				
				result = new ResultadoImportacionArchivoTO();
				result.setMensaje(
					iContratoBean.procesarArchivoEmpresa(
						importacionArchivoVentasTO.getNombre(),
						importacionArchivoVentasTO.getEmpresaId(),
						usuarioId
					)
				);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@POST
	@Path("/procesarArchivoVentasANTELEmpresa")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ResultadoImportacionArchivoTO procesarArchivoVentasANTELEmpresa(
		ImportacionArchivoVentasANTELTO importacionArchivoVentasANTELTO, 
		@Context HttpServletRequest request
	) {
		ResultadoImportacionArchivoTO result = null;
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				IContratoBean iContratoBean = lookupBean();
				
				result = new ResultadoImportacionArchivoTO();
				result.setMensaje(
					iContratoBean.procesarArchivoVentasANTELEmpresa(
						importacionArchivoVentasANTELTO.getNombre(),
						importacionArchivoVentasANTELTO.getEmpresaId(),
						usuarioId
					)
				);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@POST
	@Path("/chequearAsignacion")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ChequearAsignacionTO chequearAsignacion(
		MetadataConsulta metadataConsulta, @Context HttpServletRequest request
	) {
		ChequearAsignacionTO result = null;
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				IContratoBean iContratoBean = lookupBean();
				
				result = new ChequearAsignacionTO();
				
				result.setOk(
					iContratoBean.chequearAsignacion(metadataConsulta, usuarioId)
				);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@POST
	@Path("/chequearRelacionesAsignacion")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ChequearRelacionesAsignacionTO chequearRelacionesAsignacion(
		MetadataConsulta metadataConsulta, @Context HttpServletRequest request
	) {
		ChequearRelacionesAsignacionTO result = null;
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				IContratoBean iContratoBean = lookupBean();
				
				result = new ChequearRelacionesAsignacionTO();
				
				result.setOk(
					iContratoBean.chequearRelacionesAsignacion(metadataConsulta, usuarioId)
				);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@POST
	@Path("/validarVenta")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ResultadoValidarVentaTO validarVenta(
		Contrato contrato, @Context HttpServletRequest request
	) {
		ResultadoValidarVentaTO result = null;
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				IContratoBean iContratoBean = lookupBean();
				
				result = new ResultadoValidarVentaTO();
				result.setOk(
					iContratoBean.validarVenta(contrato)
				);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@POST
	@Path("/update")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public void update(Contrato contrato, @Context HttpServletRequest request) {
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long loggedUsuarioId = (Long) httpSession.getAttribute("sesion");
				
				IContratoBean iContratoBean = lookupBean();
				
				Date hoy = GregorianCalendar.getInstance().getTime();
				
				contrato.setFact(hoy);
				contrato.setTerm(Long.valueOf(1));
				contrato.setUact(loggedUsuarioId);
				
				iContratoBean.update(contrato);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@POST
	@Path("/addAsignacionManual")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ResultadoAsignacionManualTO addAsignacionManual(
		Contrato contrato, @Context HttpServletRequest request
	) {
		ResultadoAsignacionManualTO result = null;
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long loggedUsuarioId = (Long) httpSession.getAttribute("sesion");
				
				IContratoBean iContratoBean = lookupBean();
				
				Date hoy = GregorianCalendar.getInstance().getTime();
				
				contrato.setFact(hoy);
				contrato.setTerm(Long.valueOf(1));
				contrato.setUact(loggedUsuarioId);
				
				String resultado = 
					iContratoBean.addAsignacionManual(
						contrato.getEmpresa().getId(), 
						contrato, 
						loggedUsuarioId
					);
				result = new ResultadoAsignacionManualTO();
				
				String id = resultado.split(";")[0];
				if (id != null && !id.isEmpty()) {
					result.setId(Long.decode(id));
				}
				
				result.setResultado( 
					resultado.split(";")[1]
				);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@POST
	@Path("/asignarVendedor")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public void asignarVendedor(
		AsignacionContratosUsuarioTO asignacionContratosUsuarioTO, @Context HttpServletRequest request
	) {
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				IContratoBean iContratoBean = lookupBean();
				
				iContratoBean.asignarVentas(
					asignacionContratosUsuarioTO.getUsuario(), 
					asignacionContratosUsuarioTO.getMetadataConsulta(), 
					usuarioId
				);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@POST
	@Path("/asignarBackoffice")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public void asignarBackoffice(
		AsignacionContratosUsuarioTO asignacionContratosUsuarioTO, @Context HttpServletRequest request
	) {
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				IContratoBean iContratoBean = lookupBean();
				
				iContratoBean.asignarBackoffice(
					asignacionContratosUsuarioTO.getUsuario(), 
					asignacionContratosUsuarioTO.getMetadataConsulta(), 
					usuarioId
				);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@POST
	@Path("/asignarDistribuidor")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public void asignarDistribuidor(
		AsignacionContratosUsuarioTO asignacionContratosUsuarioTO, @Context HttpServletRequest request
	) {
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				IContratoBean iContratoBean = lookupBean();
				
				iContratoBean.asignarDistribuidor(
					asignacionContratosUsuarioTO.getUsuario(), 
					asignacionContratosUsuarioTO.getMetadataConsulta(), 
					usuarioId
				);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@POST
	@Path("/asignarActivador")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public void asignarActivador(
		AsignacionContratosUsuarioTO asignacionContratosUsuarioTO, @Context HttpServletRequest request
	) {
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				IContratoBean iContratoBean = lookupBean();
				
				iContratoBean.asignarActivador(
					asignacionContratosUsuarioTO.getUsuario(), 
					asignacionContratosUsuarioTO.getMetadataConsulta(), 
					usuarioId
				);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@POST
	@Path("/agendar")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public void agendar(
		Contrato contrato, @Context HttpServletRequest request
	) {
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long loggedUsuarioId = (Long) httpSession.getAttribute("sesion");
				
				IContratoBean iContratoBean = lookupBean();
				
				Date hoy = GregorianCalendar.getInstance().getTime();
				
				contrato.setFact(hoy);
				contrato.setTerm(Long.valueOf(1));
				contrato.setUact(loggedUsuarioId);
				
				iContratoBean.agendar(contrato);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@POST
	@Path("/rechazar")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public void rechazar(
		Contrato contrato, @Context HttpServletRequest request
	) {
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long loggedUsuarioId = (Long) httpSession.getAttribute("sesion");
				
				IContratoBean iContratoBean = lookupBean();
				
				Date hoy = GregorianCalendar.getInstance().getTime();
				
				contrato.setFact(hoy);
				contrato.setTerm(Long.valueOf(1));
				contrato.setUact(loggedUsuarioId);
				
				iContratoBean.rechazar(contrato);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@POST
	@Path("/posponer")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public void posponer(
		Contrato contrato, @Context HttpServletRequest request
	) {
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long loggedUsuarioId = (Long) httpSession.getAttribute("sesion");
				
				IContratoBean iContratoBean = lookupBean();
				
				Date hoy = GregorianCalendar.getInstance().getTime();
				
				contrato.setFact(hoy);
				contrato.setTerm(Long.valueOf(1));
				contrato.setUact(loggedUsuarioId);
				
				iContratoBean.posponer(contrato);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@POST
	@Path("/distribuir")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public void distribuir(
		Contrato contrato, @Context HttpServletRequest request
	) {
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long loggedUsuarioId = (Long) httpSession.getAttribute("sesion");
				
				IContratoBean iContratoBean = lookupBean();
				
				Date hoy = GregorianCalendar.getInstance().getTime();
				
				contrato.setFact(hoy);
				contrato.setTerm(Long.valueOf(1));
				contrato.setUact(loggedUsuarioId);
				
				iContratoBean.distribuir(contrato);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@POST
	@Path("/redistribuir")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public void redistribuir(
		Contrato contrato, @Context HttpServletRequest request
	) {
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long loggedUsuarioId = (Long) httpSession.getAttribute("sesion");
				
				IContratoBean iContratoBean = lookupBean();
				
				Date hoy = GregorianCalendar.getInstance().getTime();
				
				contrato.setFact(hoy);
				contrato.setTerm(Long.valueOf(1));
				contrato.setUact(loggedUsuarioId);
				
				iContratoBean.redistribuir(contrato);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@POST
	@Path("/telelink")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public void telelink(
		Contrato contrato, @Context HttpServletRequest request
	) {
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long loggedUsuarioId = (Long) httpSession.getAttribute("sesion");
				
				IContratoBean iContratoBean = lookupBean();
				
				Date hoy = GregorianCalendar.getInstance().getTime();
				
				contrato.setFact(hoy);
				contrato.setTerm(Long.valueOf(1));
				contrato.setUact(loggedUsuarioId);
				
				iContratoBean.telelink(contrato);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@POST
	@Path("/renovo")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public void renovo(
		Contrato contrato, @Context HttpServletRequest request
	) {
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long loggedUsuarioId = (Long) httpSession.getAttribute("sesion");
				
				IContratoBean iContratoBean = lookupBean();
				
				Date hoy = GregorianCalendar.getInstance().getTime();
				
				contrato.setFact(hoy);
				contrato.setTerm(Long.valueOf(1));
				contrato.setUact(loggedUsuarioId);
				
				iContratoBean.renovo(contrato);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@POST
	@Path("/reagendar")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public void reagendar(
		Contrato contrato, @Context HttpServletRequest request
	) {
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long loggedUsuarioId = (Long) httpSession.getAttribute("sesion");
				
				IContratoBean iContratoBean = lookupBean();
				
				Date hoy = GregorianCalendar.getInstance().getTime();
				
				contrato.setFact(hoy);
				contrato.setTerm(Long.valueOf(1));
				contrato.setUact(loggedUsuarioId);
				
				iContratoBean.reagendar(contrato);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@POST
	@Path("/activar")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public void activar(
		Contrato contrato, @Context HttpServletRequest request
	) {
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long loggedUsuarioId = (Long) httpSession.getAttribute("sesion");
				
				IContratoBean iContratoBean = lookupBean();
				
				Date hoy = GregorianCalendar.getInstance().getTime();
				
				contrato.setFact(hoy);
				contrato.setTerm(Long.valueOf(1));
				contrato.setUact(loggedUsuarioId);
				
				iContratoBean.activar(contrato);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@POST
	@Path("/noFirma")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public void noFirma(
		Contrato contrato, @Context HttpServletRequest request
	) {
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long loggedUsuarioId = (Long) httpSession.getAttribute("sesion");
				
				IContratoBean iContratoBean = lookupBean();
				
				Date hoy = GregorianCalendar.getInstance().getTime();
				
				contrato.setFact(hoy);
				contrato.setTerm(Long.valueOf(1));
				contrato.setUact(loggedUsuarioId);
				
				iContratoBean.noFirma(contrato);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@POST
	@Path("/recoordinar")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public void recoordinar(
		Contrato contrato, @Context HttpServletRequest request
	) {
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long loggedUsuarioId = (Long) httpSession.getAttribute("sesion");
				
				IContratoBean iContratoBean = lookupBean();
				
				Date hoy = GregorianCalendar.getInstance().getTime();
				
				contrato.setFact(hoy);
				contrato.setTerm(Long.valueOf(1));
				contrato.setUact(loggedUsuarioId);
				
				iContratoBean.recoordinar(contrato);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@POST
	@Path("/agendarActivacion")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public void agendarActivacion(
		Contrato contrato, @Context HttpServletRequest request
	) {
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long loggedUsuarioId = (Long) httpSession.getAttribute("sesion");
				
				IContratoBean iContratoBean = lookupBean();
				
				Date hoy = GregorianCalendar.getInstance().getTime();
				
				contrato.setFact(hoy);
				contrato.setTerm(Long.valueOf(1));
				contrato.setUact(loggedUsuarioId);
				
				iContratoBean.agendarActivacion(contrato);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@POST
	@Path("/enviarAAntel")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public void enviarAAntel(
		Contrato contrato, @Context HttpServletRequest request
	) {
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long loggedUsuarioId = (Long) httpSession.getAttribute("sesion");
				
				IContratoBean iContratoBean = lookupBean();
				
				Date hoy = GregorianCalendar.getInstance().getTime();
				
				contrato.setFact(hoy);
				contrato.setTerm(Long.valueOf(1));
				contrato.setUact(loggedUsuarioId);
				
				iContratoBean.enviarAAntel(contrato);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@POST
	@Path("/terminar")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public void terminar(
		Contrato contrato, @Context HttpServletRequest request
	) {
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long loggedUsuarioId = (Long) httpSession.getAttribute("sesion");
				
				IContratoBean iContratoBean = lookupBean();
				
				Date hoy = GregorianCalendar.getInstance().getTime();
				
				contrato.setFact(hoy);
				contrato.setTerm(Long.valueOf(1));
				contrato.setUact(loggedUsuarioId);
				
				iContratoBean.terminar(contrato);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@POST
	@Path("/faltaDocumentacion")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public void faltaDocumentacion(
		Contrato contrato, @Context HttpServletRequest request
	) {
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long loggedUsuarioId = (Long) httpSession.getAttribute("sesion");
				
				IContratoBean iContratoBean = lookupBean();
				
				Date hoy = GregorianCalendar.getInstance().getTime();
				
				contrato.setFact(hoy);
				contrato.setTerm(Long.valueOf(1));
				contrato.setUact(loggedUsuarioId);
				
				iContratoBean.faltaDocumentacion(contrato);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@POST
	@Path("/reActivar")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public void reActivar(
		Contrato contrato, @Context HttpServletRequest request
	) {
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long loggedUsuarioId = (Long) httpSession.getAttribute("sesion");
				
				IContratoBean iContratoBean = lookupBean();
				
				Date hoy = GregorianCalendar.getInstance().getTime();
				
				contrato.setFact(hoy);
				contrato.setTerm(Long.valueOf(1));
				contrato.setUact(loggedUsuarioId);
				
				iContratoBean.reActivar(contrato);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@POST
	@Path("/noRecoordina")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public void noRecoordina(
		Contrato contrato, @Context HttpServletRequest request
	) {
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long loggedUsuarioId = (Long) httpSession.getAttribute("sesion");
				
				IContratoBean iContratoBean = lookupBean();
				
				Date hoy = GregorianCalendar.getInstance().getTime();
				
				contrato.setFact(hoy);
				contrato.setTerm(Long.valueOf(1));
				contrato.setUact(loggedUsuarioId);
				
				iContratoBean.noRecoordina(contrato);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@POST
	@Path("/cerrar")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public void cerrar(
		Contrato contrato, @Context HttpServletRequest request
	) {
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long loggedUsuarioId = (Long) httpSession.getAttribute("sesion");
				
				IContratoBean iContratoBean = lookupBean();
				
				Date hoy = GregorianCalendar.getInstance().getTime();
				
				contrato.setFact(hoy);
				contrato.setTerm(Long.valueOf(1));
				contrato.setUact(loggedUsuarioId);
				
				iContratoBean.cerrar(contrato);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@POST
	@Path("/gestionInterna")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public void gestionInterna(
		Contrato contrato, @Context HttpServletRequest request
	) {
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long loggedUsuarioId = (Long) httpSession.getAttribute("sesion");
				
				IContratoBean iContratoBean = lookupBean();
				
				Date hoy = GregorianCalendar.getInstance().getTime();
				
				contrato.setFact(hoy);
				contrato.setTerm(Long.valueOf(1));
				contrato.setUact(loggedUsuarioId);
				
				iContratoBean.gestionInterna(contrato);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@POST
	@Path("/gestionDistribucion")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public void gestionDistribucion(
		Contrato contrato, @Context HttpServletRequest request
	) {
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long loggedUsuarioId = (Long) httpSession.getAttribute("sesion");
				
				IContratoBean iContratoBean = lookupBean();
				
				Date hoy = GregorianCalendar.getInstance().getTime();
				
				contrato.setFact(hoy);
				contrato.setTerm(Long.valueOf(1));
				contrato.setUact(loggedUsuarioId);
				
				iContratoBean.gestionDistribucion(contrato);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@POST
	@Path("/equipoPerdido")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public void equipoPerdido(
		Contrato contrato, @Context HttpServletRequest request
	) {
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long loggedUsuarioId = (Long) httpSession.getAttribute("sesion");
				
				IContratoBean iContratoBean = lookupBean();
				
				Date hoy = GregorianCalendar.getInstance().getTime();
				
				contrato.setFact(hoy);
				contrato.setTerm(Long.valueOf(1));
				contrato.setUact(loggedUsuarioId);
				
				iContratoBean.equipoPerdido(contrato);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@POST
	@Path("/facturaImpaga")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public void facturaImpaga(
		Contrato contrato, @Context HttpServletRequest request
	) {
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long loggedUsuarioId = (Long) httpSession.getAttribute("sesion");
				
				IContratoBean iContratoBean = lookupBean();
				
				Date hoy = GregorianCalendar.getInstance().getTime();
				
				contrato.setFact(hoy);
				contrato.setTerm(Long.valueOf(1));
				contrato.setUact(loggedUsuarioId);
				
				iContratoBean.facturaImpaga(contrato);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@POST
	@Path("/enviadoANucleo")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public void enviadoANucleo(
		Contrato contrato, @Context HttpServletRequest request
	) {
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long loggedUsuarioId = (Long) httpSession.getAttribute("sesion");
				
				IContratoBean iContratoBean = lookupBean();
				
				Date hoy = GregorianCalendar.getInstance().getTime();
				
				contrato.setFact(hoy);
				contrato.setTerm(Long.valueOf(1));
				contrato.setUact(loggedUsuarioId);
				
				iContratoBean.enviadoANucleo(contrato);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@POST
	@Path("/canceladoPorCliente")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public void canceladoPorCliente(
		Contrato contrato, @Context HttpServletRequest request
	) {
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long loggedUsuarioId = (Long) httpSession.getAttribute("sesion");
				
				IContratoBean iContratoBean = lookupBean();
				
				Date hoy = GregorianCalendar.getInstance().getTime();
				
				contrato.setFact(hoy);
				contrato.setTerm(Long.valueOf(1));
				contrato.setUact(loggedUsuarioId);
				
				iContratoBean.canceladoPorCliente(contrato);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@POST
	@Path("/equiposPagos")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public void equiposPagos(
		Contrato contrato, @Context HttpServletRequest request
	) {
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long loggedUsuarioId = (Long) httpSession.getAttribute("sesion");
				
				IContratoBean iContratoBean = lookupBean();
				
				Date hoy = GregorianCalendar.getInstance().getTime();
				
				contrato.setFact(hoy);
				contrato.setTerm(Long.valueOf(1));
				contrato.setUact(loggedUsuarioId);
				
				iContratoBean.equiposPagos(contrato);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@POST
	@Path("/equipoDevuelto")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public void equipoDevuelto(
		Contrato contrato, @Context HttpServletRequest request
	) {
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long loggedUsuarioId = (Long) httpSession.getAttribute("sesion");
				
				IContratoBean iContratoBean = lookupBean();
				
				Date hoy = GregorianCalendar.getInstance().getTime();
				
				contrato.setFact(hoy);
				contrato.setTerm(Long.valueOf(1));
				contrato.setUact(loggedUsuarioId);
				
				iContratoBean.equipoDevuelto(contrato);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@POST
	@Path("/noRecuperado")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public void noRecuperado(
		Contrato contrato, @Context HttpServletRequest request
	) {
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long loggedUsuarioId = (Long) httpSession.getAttribute("sesion");
				
				IContratoBean iContratoBean = lookupBean();
				
				Date hoy = GregorianCalendar.getInstance().getTime();
				
				contrato.setFact(hoy);
				contrato.setTerm(Long.valueOf(1));
				contrato.setUact(loggedUsuarioId);
				
				iContratoBean.noRecuperado(contrato);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@POST
	@Path("/pickUp")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public void pickUp(
		PickUpTO pickUpTO, @Context HttpServletRequest request
	) {
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long loggedUsuarioId = (Long) httpSession.getAttribute("sesion");
				
				IContratoBean iContratoBean = lookupBean();
				
				iContratoBean.pickUp(pickUpTO.getContratoId(), pickUpTO.getObservaciones(), loggedUsuarioId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@POST
	@Path("/cancelarTramite")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public void cancelarTramite(
		Contrato contrato, @Context HttpServletRequest request
	) {
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long loggedUsuarioId = (Long) httpSession.getAttribute("sesion");
				
				IContratoBean iContratoBean = lookupBean();
				
				iContratoBean.cancelarTramite(contrato.getId(), loggedUsuarioId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
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
				
				IContratoBean iContratoBean = lookupBean();
				
				String nombreArchivo = 
					iContratoBean.exportarAExcel(metadataConsulta, usuarioId);
				
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
	@Path("/exportarAExcelVentasCuentaAjena")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ResultadoExportacionArchivoTO exportarAExcelVentasCuentaAjena(
		MetadataConsulta metadataConsulta, @Context HttpServletRequest request
	) {
		ResultadoExportacionArchivoTO result = null;
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				IContratoBean iContratoBean = lookupBean();
				
				String nombreArchivo = 
					iContratoBean.exportarAExcelVentasCuentaAjena(metadataConsulta, usuarioId);
				
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
	@Path("/exportarAExcelVentasNuestroCredito")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ResultadoExportacionArchivoTO exportarAExcelVentasNuestroCredito(
		MetadataConsulta metadataConsulta, @Context HttpServletRequest request
	) {
		ResultadoExportacionArchivoTO result = null;
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				IContratoBean iContratoBean = lookupBean();
				
				MetadataCondicion metadataCondicion = new MetadataCondicion();
				metadataCondicion.setCampo("formaPago.id");
				metadataCondicion.setOperador(Constants.__METADATA_CONDICION_OPERADOR_IGUAL);
				
				Collection<String> valores = new LinkedList<String>();
				valores.add(Configuration.getInstance().getProperty("formaPago.NuestroCredito"));
				
				metadataCondicion.setValores(valores);
				
				metadataConsulta.getMetadataCondiciones().add(metadataCondicion);
				
				String nombreArchivo = 
					iContratoBean.exportarAExcelVentasNuestroCredito(metadataConsulta, usuarioId);
				
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
	@Path("/exportarAExcelNucleo")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ResultadoExportacionArchivoTO exportarAExcelNucleo(
		MetadataConsulta metadataConsulta, @Context HttpServletRequest request
	) {
		ResultadoExportacionArchivoTO result = null;
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				IContratoBean iContratoBean = lookupBean();
				
				String nombreArchivo = 
					iContratoBean.exportarAExcelNucleo(metadataConsulta, usuarioId);
				
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
	
	private IContratoBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = ContratoBean.class.getSimpleName();
		String remoteInterfaceName = IContratoBean.class.getName();
		String lookupName = 
			prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		
		javax.naming.Context context = new InitialContext();
				
		return (IContratoBean) context.lookup(lookupName);
	}
}