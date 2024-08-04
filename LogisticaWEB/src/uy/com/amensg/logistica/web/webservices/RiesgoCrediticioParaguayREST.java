package uy.com.amensg.logistica.webservices;

import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;

import com.fasterxml.jackson.databind.ObjectMapper;

import uy.com.amensg.logistica.bean.BICSABean;
import uy.com.amensg.logistica.bean.IBICSABean;
import uy.com.amensg.logistica.bean.IRiesgoCrediticioParaguayBICSABean;
import uy.com.amensg.logistica.bean.IRiesgoCrediticioParaguayBean;
import uy.com.amensg.logistica.bean.RiesgoCrediticioParaguayBICSABean;
import uy.com.amensg.logistica.bean.RiesgoCrediticioParaguayBean;
import uy.com.amensg.logistica.entities.ObtenerInformacionSystemMasterTO;
import uy.com.amensg.logistica.entities.ObtenerInformacionesSystemMasterTO;
import uy.com.amensg.logistica.entities.ResultadoObtenerInformacionSystemMasterTO;
import uy.com.amensg.logistica.entities.ResultadoObtenerInformacionesSystemMasterTO;
import uy.com.amensg.logistica.entities.RiesgoCrediticioParaguay;
import uy.com.amensg.logistica.entities.RiesgoCrediticioParaguayBICSA;
import uy.com.amensg.logistica.util.Configuration;
import uy.com.amensg.logistica.webservices.external.bicsa.Persona;
import uy.com.amensg.logistica.webservices.external.creditoamigo.WsBICSA;
import uy.com.amensg.logistica.webservices.external.creditoamigo.WsBICSASoap;

@Path("/RiesgoCrediticioParaguayREST")
public class RiesgoCrediticioParaguayREST {

	@GET
	@Path("/obtenerInformacionSystemMaster")
	@Produces({ MediaType.APPLICATION_JSON })
	public ResultadoObtenerInformacionSystemMasterTO obtenerInformacionSystemMaster(@Context UriInfo uriInfo) {
		ResultadoObtenerInformacionSystemMasterTO result = new ResultadoObtenerInformacionSystemMasterTO();
		
		try {
			String documento = null;
			String fNac = null;
			String situacion = null;
			
			MultivaluedMap<String, String> queryParams = uriInfo.getQueryParameters();
			
			if (queryParams.size() >= 3) {
				documento = queryParams.get("documento").get(0);
				fNac = queryParams.get("fnac").get(0);
				situacion = queryParams.get("situacion").get(0);
				
				ObtenerInformacionSystemMasterTO obtenerInformacionSystemMasterTO =
					new ObtenerInformacionSystemMasterTO();
				obtenerInformacionSystemMasterTO.setDocumento(documento);
				obtenerInformacionSystemMasterTO.setFechaNacimiento(fNac);
				obtenerInformacionSystemMasterTO.setSituacion(situacion);
				
				result = 
					obtenerSingleInformacionSystemMaster(
						obtenerInformacionSystemMasterTO, 
						null
					);
			}
		} catch (Exception e) {
			result.setResultadoEnviarDatosSystemMaster("No se han podido enviar datos a SystemMaster.");
			
			e.printStackTrace();
		}
		
		return result;
	}
	
	@POST
	@Path("/obtenerSingleInformacionSystemMaster")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ResultadoObtenerInformacionSystemMasterTO obtenerSingleInformacionSystemMaster(
		ObtenerInformacionSystemMasterTO obtenerInformacionSystemMasterTO, @Context HttpServletRequest request
	) {
		ResultadoObtenerInformacionSystemMasterTO result = new ResultadoObtenerInformacionSystemMasterTO();
		result.setDocumento(obtenerInformacionSystemMasterTO.getDocumento());
		result.setFechaNacimiento(obtenerInformacionSystemMasterTO.getFechaNacimiento());
		result.setSituacion(obtenerInformacionSystemMasterTO.getSituacion());
		
		SimpleDateFormat format = new SimpleDateFormat("ddMMyyyy");
		
		try {
			Date fechaNacimiento = format.parse(obtenerInformacionSystemMasterTO.getFechaNacimiento());
			Long situacionRiesgoCrediticioId = Long.parseLong(obtenerInformacionSystemMasterTO.getSituacion());
			
			IRiesgoCrediticioParaguayBean iRiesgoCrediticioParaguayBean = lookupBean();
			
			RiesgoCrediticioParaguay riesgoCrediticioParaguay = 
				iRiesgoCrediticioParaguayBean
					.getLastByDocumentoFechaNacimientoSituacionRiesgoCrediticioParaguayId(
						obtenerInformacionSystemMasterTO.getDocumento(), 
						fechaNacimiento, 
						situacionRiesgoCrediticioId
					);
			
			if (riesgoCrediticioParaguay == null) {
				result.setResultadoObtenerDatosLocales("No se encontraron datos para los parámetros especificados.");
			} else {
				result.setResultadoObtenerDatosLocales("Datos obtenidos correctamente.");
				
				IBICSABean iBICSABean = lookupBICSABean();
				
				// Obtener los datos del servicio web de BICSA.
				Persona persona = iBICSABean.obtenerPersona(
					Integer.parseInt(Configuration.getInstance().getProperty(
						"creditoAmigoPY.TipoDocumentoTodosBICSA")
					),
					Configuration.getInstance().getProperty("creditoAmigoPY.PaisTodosBICSA"), 
					riesgoCrediticioParaguay.getDocumento(), 
					Configuration.getInstance().getProperty("creditoAmigoPY.InstitucionBICSA")
				);
				
				if (persona == null) {
					result.setResultadoObtenerDatosBICSA("No se han recibido datos válidos desde el servicio BICSA.");
				} else {
					result.setResultadoObtenerDatosBICSA("Datos obtenidos correctamente desde el servicio BICSA.");
				
					StringWriter stringWriter = new StringWriter();
					
					ObjectMapper objectMapper = new ObjectMapper();
					objectMapper.writeValue(stringWriter, persona);
					
					// Grabar datos.
					Date hoy = GregorianCalendar.getInstance().getTime();
					
					RiesgoCrediticioParaguayBICSA riesgoCrediticioParaguayBICSA =
						new RiesgoCrediticioParaguayBICSA();
					
					riesgoCrediticioParaguayBICSA.setDatos(stringWriter.toString());
					riesgoCrediticioParaguayBICSA.setFact(hoy);
					riesgoCrediticioParaguayBICSA.setFcre(hoy);
					riesgoCrediticioParaguayBICSA.setRiesgoCrediticioParaguay(riesgoCrediticioParaguay);
					riesgoCrediticioParaguayBICSA.setTerm(Long.valueOf(1));
					riesgoCrediticioParaguayBICSA.setUact(Long.valueOf(1));
					riesgoCrediticioParaguayBICSA.setUcre(Long.valueOf(1));
					
					IRiesgoCrediticioParaguayBICSABean iRiesgoCrediticioParaguayBICSABean = 
						lookupRiesgoCrediticioParaguayBICSABean();
					
					iRiesgoCrediticioParaguayBICSABean.save(riesgoCrediticioParaguayBICSA);
				
					// Enviar los datos al servicio web de SystemMaster.
					
					WsBICSA wsBICSA = new WsBICSA();
					WsBICSASoap wsBICSASoap = wsBICSA.getWsBICSASoap12();
					
					wsBICSASoap.bicsaAdd(
						Integer.parseInt(riesgoCrediticioParaguay.getDocumento()), 
						stringWriter.toString()
					);
					
					result.setResultadoEnviarDatosSystemMaster("Datos enviados correctamente a SystemMaster");
				}
			}
		} catch (Exception e) {
			result.setResultadoEnviarDatosSystemMaster("No se han podido enviar datos a SystemMaster.");
			
			e.printStackTrace();
		}
		
		return result;
	}
	
	@POST
	@Path("/obtenerInformacionesSystemMaster")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ResultadoObtenerInformacionesSystemMasterTO obtenerInformacionesSystemMaster(
		ObtenerInformacionesSystemMasterTO obtenerInformacionesSystemMasterTO, 
		@Context HttpServletRequest request
	) {
		ResultadoObtenerInformacionesSystemMasterTO result = new ResultadoObtenerInformacionesSystemMasterTO();
		
		Collection<ResultadoObtenerInformacionSystemMasterTO> data = 
			new LinkedList<ResultadoObtenerInformacionSystemMasterTO>();
		
		try {
			for (ObtenerInformacionSystemMasterTO obtenerInformacionSystemMasterTO 
				: obtenerInformacionesSystemMasterTO.getData()) {
				data.add(obtenerSingleInformacionSystemMaster(obtenerInformacionSystemMasterTO, request));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		result.setData(data);
		
		return result;
	}
	
	private IRiesgoCrediticioParaguayBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = RiesgoCrediticioParaguayBean.class.getSimpleName();
		String remoteInterfaceName = IRiesgoCrediticioParaguayBean.class.getName();
		String lookupName = 
			prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		
		javax.naming.Context context = new InitialContext();
		
		return (IRiesgoCrediticioParaguayBean) context.lookup(lookupName);
	}
	
	private IRiesgoCrediticioParaguayBICSABean lookupRiesgoCrediticioParaguayBICSABean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = RiesgoCrediticioParaguayBICSABean.class.getSimpleName();
		String remoteInterfaceName = IRiesgoCrediticioParaguayBICSABean.class.getName();
		String lookupName = 
			prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		
		javax.naming.Context context = new InitialContext();
		
		return (IRiesgoCrediticioParaguayBICSABean) context.lookup(lookupName);
	}

	private IBICSABean lookupBICSABean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = BICSABean.class.getSimpleName();
		String remoteInterfaceName = IBICSABean.class.getName();
		String lookupName = 
			prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		
		javax.naming.Context context = new InitialContext();
		
		return (IBICSABean) context.lookup(lookupName);
	}
}