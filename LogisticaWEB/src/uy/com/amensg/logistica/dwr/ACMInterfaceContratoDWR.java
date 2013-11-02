package uy.com.amensg.logistica.dwr;

import java.util.Collection;
import java.util.LinkedList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.directwebremoting.annotations.RemoteProxy;

import uy.com.amensg.logistica.bean.ACMInterfaceContratoBean;
import uy.com.amensg.logistica.bean.IACMInterfaceContratoBean;
import uy.com.amensg.logistica.entities.ACMInterfaceContrato;
import uy.com.amensg.logistica.entities.ACMInterfaceContratoTO;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.MetadataConsultaResultadoTO;
import uy.com.amensg.logistica.entities.MetadataConsultaTO;

@RemoteProxy
public class ACMInterfaceContratoDWR {

	private IACMInterfaceContratoBean lookupBean() throws NamingException {
		String EARName = "Logistica";
		String beanName = ACMInterfaceContratoBean.class.getSimpleName();
		String remoteInterfaceName = IACMInterfaceContratoBean.class.getName();
		String lookupName = EARName + "/" + beanName + "/remote-" + remoteInterfaceName;
		Context context = new InitialContext();
		
		return (IACMInterfaceContratoBean) context.lookup(lookupName);
	}
	
	public MetadataConsultaResultadoTO list(MetadataConsultaTO metadataConsultaTO) {
		MetadataConsultaResultadoTO result = new MetadataConsultaResultadoTO();
		
		try {
			IACMInterfaceContratoBean iACMInterfaceContratoBean = lookupBean();
			
			MetadataConsultaResultado metadataConsultaResultado = 
				iACMInterfaceContratoBean.list(
					MetadataConsultaDWR.transform(
						metadataConsultaTO
					)
				);
			
			Collection<Object> registrosMuestra = new LinkedList<Object>();
			
			for (Object acmInterfaceContrato : metadataConsultaResultado.getRegistrosMuestra()) {
				registrosMuestra.add(transform((ACMInterfaceContrato) acmInterfaceContrato));
			}
			
			result.setRegistrosMuestra(registrosMuestra);
			
			result.setCantidadRegistros(metadataConsultaResultado.getCantidadRegistros());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public String exportarAExcel(MetadataConsultaTO metadataConsultaTO) {
		String result = null;
		
		try {
			IACMInterfaceContratoBean iACMInterfaceContratoBean = lookupBean();
			
			result = iACMInterfaceContratoBean.exportarAExcel(
				MetadataConsultaDWR.transform(
					metadataConsultaTO
				)
			);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public void reprocesar(MetadataConsultaTO metadataConsultaTO) {
		try {
			IACMInterfaceContratoBean iACMInterfaceContratoBean = lookupBean();
			
			iACMInterfaceContratoBean.reprocesar(
				MetadataConsultaDWR.transform(
					metadataConsultaTO
				)
			);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static ACMInterfaceContratoTO transform(ACMInterfaceContrato acmInterfaceContrato) {
		ACMInterfaceContratoTO acmInterfaceContratoTO = new ACMInterfaceContratoTO();
		
		acmInterfaceContratoTO.setCodigoPostal(acmInterfaceContrato.getCodigoPostal());
		acmInterfaceContratoTO.setDireccion(acmInterfaceContrato.getDireccion());
		acmInterfaceContratoTO.setDocumentoTipo(acmInterfaceContrato.getDocumentoTipo());
		acmInterfaceContratoTO.setDocumento(acmInterfaceContrato.getDocumento());
		acmInterfaceContratoTO.setFechaFinContrato(acmInterfaceContrato.getFechaFinContrato());
		acmInterfaceContratoTO.setLocalidad(acmInterfaceContrato.getLocalidad());
		acmInterfaceContratoTO.setMid(acmInterfaceContrato.getMid());
		acmInterfaceContratoTO.setNombre(acmInterfaceContrato.getNombre());
		acmInterfaceContratoTO.setTipoContratoCodigo(acmInterfaceContrato.getTipoContratoCodigo());
		acmInterfaceContratoTO.setTipoContratoDescripcion(acmInterfaceContrato.getTipoContratoDescripcion());
		
		acmInterfaceContratoTO.setFact(acmInterfaceContrato.getFact());
		acmInterfaceContratoTO.setUact(acmInterfaceContrato.getUact());
		acmInterfaceContratoTO.setTerm(acmInterfaceContrato.getTerm());
		
		return acmInterfaceContratoTO;
	}
}