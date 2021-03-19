package uy.com.amensg.logistica.webservices;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

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

import uy.com.amensg.logistica.bean.ACMInterfaceBean;
import uy.com.amensg.logistica.bean.IACMInterfaceBean;
import uy.com.amensg.logistica.entities.ACMInterfacePersona;
import uy.com.amensg.logistica.entities.ActualizarDatosACMInterfacePersonaTO;

@Path("/ACMInterfaceREST")
public class ACMInterfaceREST {

	@GET
	@Path("/getSiguienteMidSinProcesar/")
	@Produces({ MediaType.APPLICATION_JSON })
	public String getSiguienteMidSinProcesar() {
		String result = "{";
		
		try {
			IACMInterfaceBean iACMInterfaceBean = lookupACMInterfaceBean();
			
			String data = iACMInterfaceBean.getSiguienteMidSinProcesar();
			
			if (data != null) {
				result += 
					" \"data\": \"" + data + "\"";
			} else {
				result += 
					" \"data\": \"\"";
			}
			
			result += "}";
		} catch (Exception e) {
			e.printStackTrace();
			
			result =
				"{"
					+ " \"error\": \"No se puede completar la operación.\""
				+ " }";
		}
		
		return result;
	}
	
	@GET
	@Path("/getSiguienteNumeroContratoSinProcesar/")
	@Produces({ MediaType.APPLICATION_JSON })
	public String getSiguienteNumeroContratoSinProcesar() {
		String result = "{";
		
		try {
			IACMInterfaceBean iACMInterfaceBean = lookupACMInterfaceBean();
			
			String data = iACMInterfaceBean.getSiguienteNumeroContratoSinProcesar();
			
			if (data != null) {
				result += 
					" \"data\": \"" + data + "\"";
			} else {
				result += 
					" \"data\": \"\"";
			}
			
			result += "}";
		} catch (Exception e) {
			e.printStackTrace();
			
			result =
				"{"
					+ " \"error\": \"No se puede completar la operación.\""
				+ " }";
		}
		
		return result;
	}
	
	@POST
	@Path("/actualizarDatosPersona")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public void actualizarDatosPersona(
		ActualizarDatosACMInterfacePersonaTO actualizarDatosPersonaTO, @Context HttpServletRequest request
	) {
		try {
			IACMInterfaceBean iACMInterfaceBean = lookupACMInterfaceBean();
			
			Date fact = GregorianCalendar.getInstance().getTime();
			
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			
			ACMInterfacePersona acmInterfacePersona = new ACMInterfacePersona();
			if (actualizarDatosPersonaTO.getActividad() != null 
				&& !actualizarDatosPersonaTO.getActividad().trim().isEmpty()) {
				acmInterfacePersona.setActividad(actualizarDatosPersonaTO.getActividad());
			}
			if (actualizarDatosPersonaTO.getDireccionApartamento() != null 
				&& !actualizarDatosPersonaTO.getDireccionApartamento().trim().isEmpty()) {
				acmInterfacePersona.setApartamento(actualizarDatosPersonaTO.getDireccionApartamento());
			}
			if (actualizarDatosPersonaTO.getApellido() != null 
				&& !actualizarDatosPersonaTO.getApellido().trim().isEmpty()) {
				acmInterfacePersona.setApellido(actualizarDatosPersonaTO.getApellido());
			}
			if (actualizarDatosPersonaTO.getDireccionBis() != null 
				&& !actualizarDatosPersonaTO.getDireccionBis().trim().isEmpty()) {
				acmInterfacePersona.setBis(actualizarDatosPersonaTO.getDireccionBis());
			}
			if (actualizarDatosPersonaTO.getDireccionBlock() != null 
				&& !actualizarDatosPersonaTO.getDireccionBlock().trim().isEmpty()) {
				acmInterfacePersona.setBlock(actualizarDatosPersonaTO.getDireccionBlock());
			}
			if (actualizarDatosPersonaTO.getDireccionCalle() != null 
				&& !actualizarDatosPersonaTO.getDireccionCalle().trim().isEmpty()) {
				acmInterfacePersona.setCalle(actualizarDatosPersonaTO.getDireccionCalle());
			}
			if (actualizarDatosPersonaTO.getDireccionCodigoPostal() != null 
				&& !actualizarDatosPersonaTO.getDireccionCodigoPostal().trim().isEmpty()) {
				acmInterfacePersona.setCodigoPostal(actualizarDatosPersonaTO.getDireccionCodigoPostal());
			}
			if (actualizarDatosPersonaTO.getDireccionCompleta() != null 
				&& !actualizarDatosPersonaTO.getDireccionCompleta().trim().isEmpty()) {
				acmInterfacePersona.setDireccion(actualizarDatosPersonaTO.getDireccionCompleta());
			}
			if (actualizarDatosPersonaTO.getDistribuidor() != null 
				&& !actualizarDatosPersonaTO.getDistribuidor().trim().isEmpty()) {
				acmInterfacePersona.setDistribucion(actualizarDatosPersonaTO.getDistribuidor());
			}
			if (actualizarDatosPersonaTO.getDocumento() != null 
				&& !actualizarDatosPersonaTO.getDocumento().trim().isEmpty()) {
				acmInterfacePersona.setDocumento(actualizarDatosPersonaTO.getDocumento());
			}
			if (actualizarDatosPersonaTO.getEmail() != null 
				&& !actualizarDatosPersonaTO.getEmail().trim().isEmpty()) {
				acmInterfacePersona.setEmail(actualizarDatosPersonaTO.getEmail());
			}
			if (actualizarDatosPersonaTO.getDireccionEsquina() != null 
				&& !actualizarDatosPersonaTO.getDireccionEsquina().trim().isEmpty()) {
				acmInterfacePersona.setEsquina(actualizarDatosPersonaTO.getDireccionEsquina());
			}
			if (actualizarDatosPersonaTO.getFechaNacimiento() != null 
				&& !actualizarDatosPersonaTO.getFechaNacimiento().trim().isEmpty()) {
				try {
					acmInterfacePersona.setFechaNacimiento(
						format.parse(actualizarDatosPersonaTO.getFechaNacimiento())
					);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (actualizarDatosPersonaTO.getIdCliente() != null 
				&& !actualizarDatosPersonaTO.getIdCliente().trim().isEmpty()) {
				acmInterfacePersona.setIdCliente(actualizarDatosPersonaTO.getIdCliente());
			}
			if (actualizarDatosPersonaTO.getDireccionLocalidad() != null 
				&& !actualizarDatosPersonaTO.getDireccionLocalidad().trim().isEmpty()) {
				acmInterfacePersona.setLocalidad(actualizarDatosPersonaTO.getDireccionLocalidad());
			}
			if (actualizarDatosPersonaTO.getDireccionManzana() != null 
				&& !actualizarDatosPersonaTO.getDireccionManzana().trim().isEmpty()) {
				acmInterfacePersona.setManzana(actualizarDatosPersonaTO.getDireccionManzana());
			}
			if (actualizarDatosPersonaTO.getNombre() != null 
				&& !actualizarDatosPersonaTO.getNombre().trim().isEmpty()) {
				acmInterfacePersona.setNombre(actualizarDatosPersonaTO.getNombre());
			}
			if (actualizarDatosPersonaTO.getDireccionNumero() != null 
				&& !actualizarDatosPersonaTO.getDireccionNumero().trim().isEmpty()) {
				acmInterfacePersona.setNumero(actualizarDatosPersonaTO.getDireccionNumero());
			}
			if (actualizarDatosPersonaTO.getPais() != null 
				&& !actualizarDatosPersonaTO.getPais().trim().isEmpty()) {
				acmInterfacePersona.setPais(actualizarDatosPersonaTO.getPais());
			}
			if (actualizarDatosPersonaTO.getRazonSocial() != null 
				&& !actualizarDatosPersonaTO.getRazonSocial().trim().isEmpty()) {
				acmInterfacePersona.setRazonSocial(actualizarDatosPersonaTO.getRazonSocial());
			}
			if (actualizarDatosPersonaTO.getSexo() != null 
				&& !actualizarDatosPersonaTO.getSexo().trim().isEmpty()) {
				acmInterfacePersona.setSexo(actualizarDatosPersonaTO.getSexo());
			}
			if (actualizarDatosPersonaTO.getDireccionSolar() != null 
				&& !actualizarDatosPersonaTO.getDireccionSolar().trim().isEmpty()) {
				acmInterfacePersona.setSolar(actualizarDatosPersonaTO.getDireccionSolar());
			}
			if (actualizarDatosPersonaTO.getTelefonosFijo() != null 
				&& actualizarDatosPersonaTO.getTelefonosFijo().trim().isEmpty()) {
				acmInterfacePersona.setTelefono(actualizarDatosPersonaTO.getTelefonosFijo());
			}
			if (actualizarDatosPersonaTO.getTipoCliente() != null 
				&& !actualizarDatosPersonaTO.getTipoCliente().trim().isEmpty()) {
				acmInterfacePersona.setTipoCliente(actualizarDatosPersonaTO.getTipoCliente());
			}
			if (actualizarDatosPersonaTO.getTipoDocumento() != null 
				&& !actualizarDatosPersonaTO.getTipoDocumento().trim().isEmpty()) {
				acmInterfacePersona.setTipoDocumento(actualizarDatosPersonaTO.getTipoDocumento());
			}
			
			acmInterfacePersona.setFact(fact);
			acmInterfacePersona.setFcre(fact);
			acmInterfacePersona.setTerm(Long.valueOf(1));
			acmInterfacePersona.setUact(Long.valueOf(1));
			acmInterfacePersona.setUcre(Long.valueOf(1));
			
			iACMInterfaceBean.update(
				acmInterfacePersona,
				Long.decode(actualizarDatosPersonaTO.getMid())
			);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private IACMInterfaceBean lookupACMInterfaceBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = ACMInterfaceBean.class.getSimpleName();
		String remoteInterfaceName = IACMInterfaceBean.class.getName();
		String lookupName = prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		
		javax.naming.Context context = new InitialContext();
				
		return (IACMInterfaceBean) context.lookup(lookupName);
	}
}