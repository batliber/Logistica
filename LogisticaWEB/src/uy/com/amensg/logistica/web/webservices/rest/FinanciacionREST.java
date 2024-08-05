package uy.com.amensg.logistica.web.webservices.rest;

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
import uy.com.amensg.logistica.bean.FinanciacionBean;
import uy.com.amensg.logistica.bean.IFinanciacionBean;
import uy.com.amensg.logistica.entities.DatosElegibilidadFinanciacion;
import uy.com.amensg.logistica.entities.DatosFinanciacion;
import uy.com.amensg.logistica.entities.Empresa;
import uy.com.amensg.logistica.entities.Moneda;
import uy.com.amensg.logistica.entities.TipoTasaInteresEfectivaAnual;
import uy.com.amensg.logistica.util.Configuration;
import uy.com.amensg.logistica.web.entities.AnalizarElegibilidadFinanciacionTO;
import uy.com.amensg.logistica.web.entities.CalcularFinanciacionTO;
import uy.com.amensg.logistica.web.entities.ResultadoValidacionFinanciacionTO;
import uy.com.amensg.logistica.web.entities.ValidarFinanciacionTO;

@Path("/FinanciacionREST")
public class FinanciacionREST {

	@POST
	@Path("/analizarElegibilidadFinanaciacion")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public DatosElegibilidadFinanciacion analizarElegibilidadFinanaciacion(
		AnalizarElegibilidadFinanciacionTO analizarElegibilidadFinanciacionTO, @Context HttpServletRequest request
	) {
		DatosElegibilidadFinanciacion result = null;
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				IFinanciacionBean iFinanciacionBean = lookupBean();
				
				Empresa empresa = new Empresa();
				empresa.setId(analizarElegibilidadFinanciacionTO.getEmpresaId());
				
				result = 
					iFinanciacionBean.analizarElegibilidadFinanaciacion(
						empresa,
						analizarElegibilidadFinanciacionTO.getDocumento()
					);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@POST
	@Path("/calcularFinanciacion")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public DatosFinanciacion calcularFinanciacion(
		CalcularFinanciacionTO calcularFinanciacionTO, @Context HttpServletRequest request
	) {
		DatosFinanciacion result = null;
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				IFinanciacionBean iFinanciacionBean = lookupBean();
				
				Moneda moneda = new Moneda();
				moneda.setId(calcularFinanciacionTO.getMonedaId());
				
				TipoTasaInteresEfectivaAnual tipoTasaInteresEfectivaAnual = new TipoTasaInteresEfectivaAnual();
				tipoTasaInteresEfectivaAnual.setId(calcularFinanciacionTO.getTipoTasaInteresEfectivaAnualId());
				
				result = 
					iFinanciacionBean.calcularFinanciacion(
						moneda,
						tipoTasaInteresEfectivaAnual,
						calcularFinanciacionTO.getMonto(),
						calcularFinanciacionTO.getCuotas()
					);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@POST
	@Path("/validarFinanciacion")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ResultadoValidacionFinanciacionTO validarFinanciacion(
		ValidarFinanciacionTO validarFinanciacionTO, @Context HttpServletRequest request
	) {
		ResultadoValidacionFinanciacionTO result = null;
		
		try {
			Long formaPagoNuestroCreditoId = 
				Long.parseLong(Configuration.getInstance().getProperty("formaPago.NuestroCredito"));
			
			if (validarFinanciacionTO.getFormaPagoId().equals(formaPagoNuestroCreditoId)) {
				Long monedaPesosUruguayosId =
					Long.parseLong(Configuration.getInstance().getProperty("moneda.PesosUruguayos"));
				Long monedaDolaresAmericanosId =
					Long.parseLong(Configuration.getInstance().getProperty("moneda.DolaresAmericanos"));
				
				if (validarFinanciacionTO.getMonedaId().equals(monedaPesosUruguayosId)) {
					Double montoMinimoPesosUruguayos = 
						Double.valueOf(
							Configuration.getInstance().getProperty(
								"financiacion.creditoDeLaCasa.montoMinimo.PesosUruguayos"
							)
						);
					
					if (validarFinanciacionTO.getMonto() < montoMinimoPesosUruguayos) {
						result = new ResultadoValidacionFinanciacionTO();
						result.setMensaje(
							"No se puede financiar con Nuestro crédito por montos inferiores a $U 2500."
						);
					}
				} else if (validarFinanciacionTO.getMonedaId().equals(monedaDolaresAmericanosId)) {
					Double montoMinimoDolaresAmericanos =
						Double.valueOf(
							Configuration.getInstance().getProperty(
								"financiacion.creditoDeLaCasa.montoMinimo.DolaresAmericanos"
							)
						);
					
					if (validarFinanciacionTO.getMonto() < montoMinimoDolaresAmericanos) {
						result = new ResultadoValidacionFinanciacionTO();
						result.setMensaje(
							"No se puede financiar con Nuestro crédito por montos inferiores a U$S 100."
						);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	private IFinanciacionBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = FinanciacionBean.class.getSimpleName();
		String remoteInterfaceName = IFinanciacionBean.class.getName();
		String lookupName = 
			prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		
		javax.naming.Context context = new InitialContext();
		
		return (IFinanciacionBean) context.lookup(lookupName);
	}
}