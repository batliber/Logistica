package uy.com.amensg.logistica.dwr;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.directwebremoting.annotations.RemoteProxy;

import uy.com.amensg.logistica.bean.ACMInterfacePersonaBean;
import uy.com.amensg.logistica.bean.IACMInterfacePersonaBean;
import uy.com.amensg.logistica.entities.ACMInterfacePersona;
import uy.com.amensg.logistica.entities.ACMInterfacePersonaTO;

@RemoteProxy
public class ACMInterfacePersonaDWR {

	private IACMInterfacePersonaBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = ACMInterfacePersonaBean.class.getSimpleName();
		String remoteInterfaceName = IACMInterfacePersonaBean.class.getName();
		String lookupName = prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		Context context = new InitialContext();
		
		return (IACMInterfacePersonaBean) context.lookup(lookupName);
	}

	public ACMInterfacePersonaTO getById(Long id) {
		ACMInterfacePersonaTO result = null;
		
		try {
			IACMInterfacePersonaBean iACMInterfacePersonaBean = lookupBean();
			
			ACMInterfacePersona acmInterfacePersona = iACMInterfacePersonaBean.getById(id);
			if (acmInterfacePersona != null) {
				result = transform(acmInterfacePersona);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static ACMInterfacePersonaTO transform(ACMInterfacePersona acmInterfacePersona) {
		ACMInterfacePersonaTO result = new ACMInterfacePersonaTO();
		
		result.setIdCliente(acmInterfacePersona.getIdCliente());
		result.setPais(acmInterfacePersona.getPais());
		result.setTipoDocumento(acmInterfacePersona.getTipoDocumento());
		result.setDocumento(acmInterfacePersona.getDocumento());
		result.setApellido(acmInterfacePersona.getApellido());
		result.setNombre(acmInterfacePersona.getNombre());
		result.setRazonSocial(acmInterfacePersona.getRazonSocial());
		result.setTipoCliente(acmInterfacePersona.getTipoCliente());
		result.setActividad(acmInterfacePersona.getActividad());
		result.setFechaNacimiento(acmInterfacePersona.getFechaNacimiento());
		result.setSexo(acmInterfacePersona.getSexo());
		result.setCalle(acmInterfacePersona.getCalle());
		result.setNumero(acmInterfacePersona.getNumero());
		result.setBis(acmInterfacePersona.getBis());
		result.setApartamento(acmInterfacePersona.getApartamento());
		result.setEsquina(acmInterfacePersona.getEsquina());
		result.setBlock(acmInterfacePersona.getBlock());
		result.setManzana(acmInterfacePersona.getManzana());
		result.setSolar(acmInterfacePersona.getSolar());
		result.setLocalidad(acmInterfacePersona.getLocalidad());
		result.setCodigoPostal(acmInterfacePersona.getCodigoPostal());
		result.setDireccion(acmInterfacePersona.getDireccion());
		result.setDistribucion(acmInterfacePersona.getDistribucion());
		result.setTelefono(acmInterfacePersona.getTelefono());
		result.setEmail(acmInterfacePersona.getEmail());
		
		result.setFact(acmInterfacePersona.getFact());
		result.setId(acmInterfacePersona.getId());
		result.setTerm(acmInterfacePersona.getTerm());
		result.setUact(acmInterfacePersona.getUact());
		
		return result;
	}
}