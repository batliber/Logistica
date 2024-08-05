package uy.com.amensg.logistica.web.webservices;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.UriInfo;
import uy.com.amensg.logistica.bean.ContratoANTELBean;
import uy.com.amensg.logistica.bean.ContratoBean;
import uy.com.amensg.logistica.bean.ContratoRoutingHistoryANTELBean;
import uy.com.amensg.logistica.bean.DepartamentoBean;
import uy.com.amensg.logistica.bean.IContratoANTELBean;
import uy.com.amensg.logistica.bean.IContratoBean;
import uy.com.amensg.logistica.bean.IContratoRoutingHistoryANTELBean;
import uy.com.amensg.logistica.bean.IDepartamentoBean;
import uy.com.amensg.logistica.entities.Contrato;
import uy.com.amensg.logistica.entities.ContratoANTEL;
import uy.com.amensg.logistica.entities.ContratoRoutingHistoryANTEL;
import uy.com.amensg.logistica.entities.DatosEstadisticasResultadoEntregasANTEL;
import uy.com.amensg.logistica.entities.DatosEstadisticasVentasANTEL;
import uy.com.amensg.logistica.entities.Departamento;
import uy.com.amensg.logistica.entities.Empresa;
import uy.com.amensg.logistica.entities.MetadataCondicion;
import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.MetadataOrdenacion;
import uy.com.amensg.logistica.entities.TipoDocumento;
import uy.com.amensg.logistica.util.Configuration;
import uy.com.amensg.logistica.util.Constants;
import uy.com.amensg.logistica.web.entities.AltaANTELTO;
import uy.com.amensg.logistica.web.entities.ContratoANTELTO;
import uy.com.amensg.logistica.web.entities.ContratoEstadisticasANTELTO;
import uy.com.amensg.logistica.web.entities.DatosGraficoResultadoEntregasANTELTO;
import uy.com.amensg.logistica.web.entities.DatosGraficoVentasANTELTO;
import uy.com.amensg.logistica.web.entities.EstadoANTELTO;
import uy.com.amensg.logistica.web.entities.HistoricoByNumeroTramiteANTELTO;
import uy.com.amensg.logistica.web.entities.ListDatosGraficoResultadoEntregasANTELTO;
import uy.com.amensg.logistica.web.entities.ListDatosGraficoVentasANTELTO;
import uy.com.amensg.logistica.web.entities.ResultadoAltaANTELTO;

@Path("/TramiteREST")
public class ContratoREST {
	
	@GET
	@Path("/listHistoricoByNumeroTramite/{numeroTramite}")
	@Produces("application/json")
	public Collection<HistoricoByNumeroTramiteANTELTO> listHistoricoByNumeroTramite(
		@PathParam("numeroTramite") Long numeroTramite
	) {
		Collection<HistoricoByNumeroTramiteANTELTO> result = new LinkedList<HistoricoByNumeroTramiteANTELTO>();
		
		try {
			IContratoRoutingHistoryANTELBean iContratoRoutingHistoryANTELBean = lookupContratoRoutingHistoryANTELBean();
			
			Collection<ContratoRoutingHistoryANTEL> historico = 
				iContratoRoutingHistoryANTELBean.listByNumeroTramite(numeroTramite);
			
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"); 
			
			for (ContratoRoutingHistoryANTEL contratoRoutingHistoryANTEL : historico) {
				HistoricoByNumeroTramiteANTELTO historicoByNumeroTramiteANTELTO = 
					new HistoricoByNumeroTramiteANTELTO();
				if (contratoRoutingHistoryANTEL.getUsuario() != null) {
					historicoByNumeroTramiteANTELTO.setAsignadoA(contratoRoutingHistoryANTEL.getUsuario().getNombre());
				}
				if (contratoRoutingHistoryANTEL.getEstado() != null) {
					historicoByNumeroTramiteANTELTO.setEstado(contratoRoutingHistoryANTEL.getEstado().getNombre());
				}
				if (contratoRoutingHistoryANTEL.getFecha() != null) {
					historicoByNumeroTramiteANTELTO.setFecha(
						format.format(contratoRoutingHistoryANTEL.getFecha())
					);
				}
				if (contratoRoutingHistoryANTEL.getUsuarioAct() != null) {
					historicoByNumeroTramiteANTELTO.setModificadoPor(
						contratoRoutingHistoryANTEL.getUsuarioAct().getNombre()
					);
				}
				if (historicoByNumeroTramiteANTELTO.getRol() != null) {
					historicoByNumeroTramiteANTELTO.setRol(contratoRoutingHistoryANTEL.getRol().getNombre());
				}
				
				result.add(historicoByNumeroTramiteANTELTO);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@GET
	@Path("/listEstadisticasANTEL")
	@Produces({ MediaType.APPLICATION_JSON })
	public Collection<ContratoEstadisticasANTELTO> listEstadisticasANTEL(@Context UriInfo uriInfo) {
		Collection<ContratoEstadisticasANTELTO> result = new LinkedList<ContratoEstadisticasANTELTO>();
		
		try {
			MultivaluedMap<String, String> queryParams = uriInfo.getQueryParameters();
			
			if (queryParams.size() >= 2) {
				String stringDesde = queryParams.get("desde").get(0);
				String stringHasta = queryParams.get("hasta").get(0);
				
				SimpleDateFormat parser = new SimpleDateFormat("ddMMyyyy");
				
				Date desde = parser.parse(stringDesde);
				Date hasta = parser.parse(stringHasta);
				
				IContratoANTELBean iContratoANTELBean = lookupContratoANTELBean();
				
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
				
				MetadataConsulta metadataConsulta = new MetadataConsulta();
				metadataConsulta.setTamanoMuestra(
					Long.valueOf(Configuration.getInstance().getProperty("estadisticasANTEL.cantidadRegistros"))
				);
				metadataConsulta.setTamanoSubconjunto(Long.valueOf(1));
				
				Collection<MetadataCondicion> metadataCondiciones = new LinkedList<MetadataCondicion>();
				MetadataCondicion metadataCondicion = new MetadataCondicion();
				metadataCondicion.setCampo("fcre");
				metadataCondicion.setOperador(Constants.__METADATA_CONDICION_OPERADOR_ENTRE);
				
				Collection<String> valores = new LinkedList<String>();
				valores.add(formatter.format(desde));
				valores.add(formatter.format(hasta));
				metadataCondicion.setValores(valores);
				
				metadataCondiciones.add(metadataCondicion);
				
				metadataConsulta.setMetadataCondiciones(metadataCondiciones);
				
				Collection<MetadataOrdenacion> metadataOrdenaciones = new LinkedList<MetadataOrdenacion>();
				MetadataOrdenacion metadataOrdenacion = new MetadataOrdenacion();
				metadataOrdenacion.setAscendente(false);
				metadataOrdenacion.setCampo("id");
				
				metadataOrdenaciones.add(metadataOrdenacion);
				
				metadataConsulta.setMetadataOrdenaciones(metadataOrdenaciones);
				
				Long usuarioId = Long.valueOf(Configuration.getInstance().getProperty("usuario.SupervisorVentasANTEL"));
				
				MetadataConsultaResultado metadataConsultaResultado = 
					iContratoANTELBean.list(metadataConsulta, usuarioId);
				
				for (Object object : metadataConsultaResultado.getRegistrosMuestra()) {
					ContratoANTEL contratoANTEL = (ContratoANTEL) object;
					
					ContratoEstadisticasANTELTO contratoEstadisticasANTELTO = new ContratoEstadisticasANTELTO();
					
					contratoEstadisticasANTELTO.setEmpresa(contratoANTEL.getEmpresa().getNombre());
					contratoEstadisticasANTELTO.setEstado(contratoANTEL.getEstado().getNombre());
					contratoEstadisticasANTELTO.setNumeroTramite(contratoANTEL.getNumeroTramite().toString());
					if (contratoANTEL.getDireccionEntregaDepartamento() != null) {
						contratoEstadisticasANTELTO.setDepartamento(contratoANTEL.getDireccionEntregaDepartamento().getNombre());
					} else {
						contratoEstadisticasANTELTO.setDepartamento("");	
					}
					if (contratoANTEL.getBarrio() != null) {
						contratoEstadisticasANTELTO.setBarrio(contratoANTEL.getBarrio().getNombre());
					} else {
						contratoEstadisticasANTELTO.setBarrio("");
					}
					if (contratoANTEL.getFechaActivacion() != null) {
						contratoEstadisticasANTELTO.setFechaDocumentacionFinalizada(
							formatter.format(contratoANTEL.getFechaActivacion())
						);
					} else {
						contratoEstadisticasANTELTO.setFechaDocumentacionFinalizada("");
					}
					if (contratoANTEL.getFcre() != null) {
						contratoEstadisticasANTELTO.setFechaCreacion(
							formatter.format(contratoANTEL.getFcre())
						);
					} else {
						contratoEstadisticasANTELTO.setFechaCreacion("");
					}
					if (contratoANTEL.getFechaEntregaDistribuidor() != null) {
						contratoEstadisticasANTELTO.setFechaEntregaDistribuidor(
							formatter.format(contratoANTEL.getFechaEntregaDistribuidor())
						);
					} else {
						contratoEstadisticasANTELTO.setFechaEntregaDistribuidor("");
					}
					if (contratoANTEL.getFechaPickUp() != null) {
						contratoEstadisticasANTELTO.setFechaPickUp(
							formatter.format(contratoANTEL.getFechaPickUp())
						);
					} else {
						contratoEstadisticasANTELTO.setFechaPickUp("");
					}
					if (contratoANTEL.getResultadoEntregaDistribucion() != null) {
						contratoEstadisticasANTELTO.setResultadoEntrega(
							contratoANTEL.getResultadoEntregaDistribucion().getDescripcion()
						);
					} else {
						contratoEstadisticasANTELTO.setResultadoEntrega("");
					}
					if (contratoANTEL.getResultadoEntregaDistribucionFecha() != null) {
						contratoEstadisticasANTELTO.setFechaResultadoEntregaDistribuidor(
							formatter.format(contratoANTEL.getResultadoEntregaDistribucionFecha())
						);
					} else {
						contratoEstadisticasANTELTO.setFechaResultadoEntregaDistribuidor("");
					}
					if (contratoANTEL.getAntelNroTrn() != null) {
						contratoEstadisticasANTELTO.setNumeroOrden(contratoANTEL.getAntelNroTrn());
					} else {
						contratoEstadisticasANTELTO.setNumeroOrden("");
					}
					
					result.add(contratoEstadisticasANTELTO);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@POST
	@Path("/listDatosGraficoVentasANTEL")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Collection<DatosGraficoVentasANTELTO> listDatosGraficoVentasANTEL(
		ListDatosGraficoVentasANTELTO listDatosGraficoVentasANTELTO, @Context HttpServletRequest request
	) {
		Collection<DatosGraficoVentasANTELTO> result = new LinkedList<DatosGraficoVentasANTELTO>();
		
		try {
			SimpleDateFormat parser = new SimpleDateFormat("ddMMyyyy");
			
			Date desde = parser.parse(listDatosGraficoVentasANTELTO.getDesde());
			Date hasta = parser.parse(listDatosGraficoVentasANTELTO.getHasta());
			
			IContratoANTELBean iContratoANTELBean = lookupContratoANTELBean();
			
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			
			for (DatosEstadisticasVentasANTEL datosEstadisticasVentasANTEL : 
				iContratoANTELBean.listEstadisticasVentasANTEL(
					listDatosGraficoVentasANTELTO.getEmpresas(), desde, hasta
				)) {
				DatosGraficoVentasANTELTO datosGraficoVentasANTELTO = new DatosGraficoVentasANTELTO();
				datosGraficoVentasANTELTO.setCantidad(datosEstadisticasVentasANTEL.getCantidad());
				datosGraficoVentasANTELTO.setStatus(datosEstadisticasVentasANTEL.getStatus());
				datosGraficoVentasANTELTO.setFecha(formatter.format(datosEstadisticasVentasANTEL.getFecha()));
				
				result.add(datosGraficoVentasANTELTO);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}	
		
		return result;
	}
	
	@POST
	@Path("/listDatosGraficoResultadoEntregasANTEL")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Collection<DatosGraficoResultadoEntregasANTELTO> listDatosGraficoResultadoEntregasANTEL(
		ListDatosGraficoResultadoEntregasANTELTO listDatosGraficoResultadoEntregasANTELTO, 
		@Context HttpServletRequest request
	) {
		Collection<DatosGraficoResultadoEntregasANTELTO> result = 
			new LinkedList<DatosGraficoResultadoEntregasANTELTO>();
		
		try {
			SimpleDateFormat parser = new SimpleDateFormat("ddMMyyyy");
			
			Date desde = parser.parse(listDatosGraficoResultadoEntregasANTELTO.getDesde());
			Date hasta = parser.parse(listDatosGraficoResultadoEntregasANTELTO.getHasta());
			
			IContratoANTELBean iContratoANTELBean = lookupContratoANTELBean();
			
			for (DatosEstadisticasResultadoEntregasANTEL datosEstadisticasResultadoEntregasANTEL : 
				iContratoANTELBean.listEstadisticasResultadoEntregasANTEL(
					listDatosGraficoResultadoEntregasANTELTO.getEmpresas(), desde, hasta
				)) {
				DatosGraficoResultadoEntregasANTELTO datosGraficoResultadoEntregasANTELTO = 
					new DatosGraficoResultadoEntregasANTELTO();
				datosGraficoResultadoEntregasANTELTO.setCantidad(
					datosEstadisticasResultadoEntregasANTEL.getCantidad()
				);
				datosGraficoResultadoEntregasANTELTO.setResultadoEntregaDistribucionDescripcion(
					datosEstadisticasResultadoEntregasANTEL.getResultadoEntregaDistribucion()
				);
				
				result.add(datosGraficoResultadoEntregasANTELTO);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}	
		
		return result;
	}
	
	@GET
	@Path("/getByNumeroTramite/{numeroTramite}")
	@Produces("application/json")
	public ContratoANTELTO getByNumeroTramite(@PathParam("numeroTramite") Long numeroTramite) {
		ContratoANTELTO result = new ContratoANTELTO();
		
		try {
			IContratoANTELBean iContratoANTELBean = lookupContratoANTELBean();
			
			ContratoANTEL contratoANTEL = iContratoANTELBean.getByNumeroTramite(numeroTramite);
			
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			
			result.setNumeroTramite(contratoANTEL.getNumeroTramite().toString());
			result.setNumeroOrden(
				contratoANTEL.getAntelNroTrn() != null ? contratoANTEL.getAntelNroTrn() : ""
			);
			result.setDireccionEntregaCalle(
				contratoANTEL.getDireccionEntregaCalle() != null ? contratoANTEL.getDireccionEntregaCalle() : ""
			);
			result.setDireccionEntregaNumero(
				contratoANTEL.getDireccionEntregaNumero() != null 
					? contratoANTEL.getDireccionEntregaNumero().toString() 
					: ""
			);
			result.setDireccionEntregaDepartamento(
				contratoANTEL.getDireccionEntregaDepartamento() != null 
					? contratoANTEL.getDireccionEntregaDepartamento().getNombre() 
					: ""
			);
			result.setDireccionEntregaLocalidad(
				contratoANTEL.getDireccionEntregaLocalidad() != null 
				? contratoANTEL.getDireccionEntregaLocalidad() 
				: ""
			);
			result.setFechaPickUp(
				contratoANTEL.getFechaPickUp() != null ? format.format(contratoANTEL.getFechaPickUp()) : ""
			);
			result.setFechaEntrega(
				contratoANTEL.getFechaEntrega() != null ? format.format(contratoANTEL.getFechaEntrega()) : ""
			);
			
			if (contratoANTEL.getEstado() != null) {
				EstadoANTELTO estadoANTELTO = new EstadoANTELTO();
				estadoANTELTO.setId(contratoANTEL.getEstado().getId());
				estadoANTELTO.setNombre(contratoANTEL.getEstado().getNombre());
				
				result.setEstado(estadoANTELTO);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@GET
	@Path("/getByNumeroOrden/{numeroOrden}")
	@Produces("application/json")
	public ContratoANTELTO getByNumeroOrden(@PathParam("numeroOrden") String numeroOrden) {
		ContratoANTELTO result = new ContratoANTELTO();
		
		try {
			IContratoANTELBean iContratoANTELBean = lookupContratoANTELBean();
			
			ContratoANTEL contratoANTEL = iContratoANTELBean.getByNumeroOrden(numeroOrden);
			
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			
			result.setNumeroTramite(contratoANTEL.getNumeroTramite().toString());
			result.setNumeroOrden(
				contratoANTEL.getAntelNroTrn() != null ? contratoANTEL.getAntelNroTrn() : ""
			);
			result.setDireccionEntregaCalle(
				contratoANTEL.getDireccionEntregaCalle() != null ? contratoANTEL.getDireccionEntregaCalle() : ""
			);
			result.setDireccionEntregaNumero(
				contratoANTEL.getDireccionEntregaNumero() != null 
					? contratoANTEL.getDireccionEntregaNumero().toString() 
					: ""
			);
			result.setDireccionEntregaDepartamento(
				contratoANTEL.getDireccionEntregaDepartamento() != null 
					? contratoANTEL.getDireccionEntregaDepartamento().getNombre() 
					: ""
			);
			result.setDireccionEntregaLocalidad(
				contratoANTEL.getDireccionEntregaLocalidad() != null 
				? contratoANTEL.getDireccionEntregaLocalidad() 
				: ""
			);
			result.setFechaPickUp(
				contratoANTEL.getFechaPickUp() != null ? format.format(contratoANTEL.getFechaPickUp()) : ""
			);
			result.setFechaEntrega(
				contratoANTEL.getFechaEntrega() != null ? format.format(contratoANTEL.getFechaEntrega()) : ""
			);
			
			if (contratoANTEL.getEstado() != null) {
				EstadoANTELTO estadoANTELTO = new EstadoANTELTO();
				estadoANTELTO.setId(contratoANTEL.getEstado().getId());
				estadoANTELTO.setNombre(contratoANTEL.getEstado().getNombre());
				
				result.setEstado(estadoANTELTO);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@POST
	@Path("/altaTramite")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ResultadoAltaANTELTO altaTramite(AltaANTELTO altaANTELTO, @Context HttpServletRequest request) {
		ResultadoAltaANTELTO result = new ResultadoAltaANTELTO();
		
		try {
			IContratoBean iContratoBean = lookupContratoBean();
			IDepartamentoBean iDepartamentoBean = lookupDepartamentoBean();
			
			Long empresaANTELPolo1Id = 
				Long.parseLong(
					Configuration.getInstance().getProperty("empresa.ANTELPolo1")
				);
			
			Long usuarioId = Long.valueOf(Configuration.getInstance().getProperty("usuario.SupervisorVentasANTEL"));
			
			SimpleDateFormat parser = new SimpleDateFormat("dd/MM/yyyy");
			
			Date fechaNacimiento = parser.parse(altaANTELTO.getFechaNacimiento());
			
			Long mid = Long.decode(altaANTELTO.getMid());
			
			Boolean direccionEntregaBis = Boolean.parseBoolean(altaANTELTO.getDireccionEntregaBis());
			Long direccionEntregaCodigoPostal = Long.decode(altaANTELTO.getDireccionEntregaCodigoPostal());
			
			Departamento direccionEntregaDepartamento = 
				iDepartamentoBean.getByNombre(altaANTELTO.getDireccionEntregaDepartamento());
			
			Long direccionEntregaManzana = Long.decode(altaANTELTO.getDireccionEntregaManzana());
			Long direccionEntregaNumero = Long.decode(altaANTELTO.getDireccionEntregaNumero());
			
			Contrato contrato = new Contrato();
			contrato.setAntelNroTrn(altaANTELTO.getNumeroOrden());
			contrato.setApellido(altaANTELTO.getApellido());
			
			contrato.setDireccionEntregaApto(altaANTELTO.getDireccionEntregaApto());
			contrato.setDireccionEntregaBis(direccionEntregaBis);
			contrato.setDireccionEntregaBlock(altaANTELTO.getDireccionEntregaBlock());
			contrato.setDireccionEntregaCalle(altaANTELTO.getDireccionEntregaCalle());
			contrato.setDireccionEntregaCodigoPostal(direccionEntregaCodigoPostal);
			contrato.setDireccionEntregaDepartamento(direccionEntregaDepartamento);
			contrato.setDireccionEntregaLocalidad(altaANTELTO.getDireccionEntregaLocalidad());
			contrato.setDireccionEntregaManzana(direccionEntregaManzana);
			contrato.setDireccionEntregaNumero(direccionEntregaNumero);
			contrato.setDireccionEntregaObservaciones(altaANTELTO.getDireccionEntregaObservaciones());
			contrato.setDireccionEntregaSolar(altaANTELTO.getDireccionEntregaSolar());
			
			contrato.setDocumento(altaANTELTO.getDocumento());
			contrato.setEmail(altaANTELTO.getFechaNacimiento());
			contrato.setFechaNacimiento(fechaNacimiento);
			contrato.setMid(mid);
			contrato.setNombre(altaANTELTO.getNombre());
			contrato.setObservaciones(altaANTELTO.getObservaciones());
			contrato.setTelefonoContacto(altaANTELTO.getTelefono());
			
			Empresa empresa = new Empresa();
			empresa.setId(empresaANTELPolo1Id);
			
			contrato.setEmpresa(empresa);
			
			TipoDocumento tipoDocumento = new TipoDocumento();
			tipoDocumento.setId(
				Long.valueOf(Configuration.getInstance().getProperty("tipoDocumento.cedulaDeIdentidad"))
			);
			
			contrato.setTipoDocumento(tipoDocumento);
			
			String salida = iContratoBean.addAsignacionManual(
				empresaANTELPolo1Id, 
				contrato, 
				usuarioId
			);
			
			Contrato contratoManaged = 
				iContratoBean.getById(Long.decode(salida.split(";")[0]), false);
			
			result.setNumeroOrden(altaANTELTO.getNumeroOrden());
			result.setNumeroTramite(contratoManaged.getNumeroTramite().toString());
			result.setObservaciones("Operación exitosa");
		} catch (Exception e) {
			result.setObservaciones("No se pudo completar la operación.");
			e.printStackTrace();
		}
		
		return result;
	}
	
	private IContratoBean lookupContratoBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = ContratoBean.class.getSimpleName();
		String remoteInterfaceName = IContratoBean.class.getName();
		String lookupName = prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		javax.naming.Context context = new InitialContext();
				
		return (IContratoBean) context.lookup(lookupName);
	}
	
	private IDepartamentoBean lookupDepartamentoBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = DepartamentoBean.class.getSimpleName();
		String remoteInterfaceName = IDepartamentoBean.class.getName();
		String lookupName = prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		javax.naming.Context context = new InitialContext();
				
		return (IDepartamentoBean) context.lookup(lookupName);
	}
	
	private IContratoANTELBean lookupContratoANTELBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = ContratoANTELBean.class.getSimpleName();
		String remoteInterfaceName = IContratoANTELBean.class.getName();
		String lookupName = prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		
		javax.naming.Context context = new InitialContext();
				
		return (IContratoANTELBean) context.lookup(lookupName);
	}
	
	private IContratoRoutingHistoryANTELBean lookupContratoRoutingHistoryANTELBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = ContratoRoutingHistoryANTELBean.class.getSimpleName();
		String remoteInterfaceName = IContratoRoutingHistoryANTELBean.class.getName();
		String lookupName = prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		
		javax.naming.Context context = new InitialContext();
				
		return (IContratoRoutingHistoryANTELBean) context.lookup(lookupName);
	}
}