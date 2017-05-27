package uy.com.amensg.logistica.dwr;

import java.util.Collection;
import java.util.LinkedList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.directwebremoting.annotations.RemoteProxy;

import uy.com.amensg.logistica.bean.ACMInterfaceMidPHBean;
import uy.com.amensg.logistica.bean.IACMInterfaceMidPHBean;
import uy.com.amensg.logistica.entities.ACMInterfaceMid;
import uy.com.amensg.logistica.entities.ACMInterfaceMidTO;
import uy.com.amensg.logistica.entities.MetadataCondicionTO;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.MetadataConsultaResultadoTO;
import uy.com.amensg.logistica.entities.MetadataConsultaTO;
import uy.com.amensg.logistica.util.Configuration;
import uy.com.amensg.logistica.util.Constants;

@RemoteProxy
public class ACMInterfaceMidPHDWR {

	private IACMInterfaceMidPHBean lookupPHBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = ACMInterfaceMidPHBean.class.getSimpleName();
		String remoteInterfaceName = IACMInterfaceMidPHBean.class.getName();
		String lookupName = prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		Context context = new InitialContext();
		
		return (IACMInterfaceMidPHBean) context.lookup(lookupName);
	}

	public MetadataConsultaResultadoTO list(MetadataConsultaTO metadataConsultaTO) {
		MetadataConsultaResultadoTO result = new MetadataConsultaResultadoTO();
		
		try {
			IACMInterfaceMidPHBean iACMInterfaceMidPHBean = lookupPHBean();
			
			MetadataConsultaResultado metadataConsultaResultado = 
				iACMInterfaceMidPHBean.list(
					MetadataConsultaDWR.transform(
						metadataConsultaTO
					)
				);
			
			Collection<Object> registrosMuestra = new LinkedList<Object>();
			
			for (Object acmInterfaceMid : metadataConsultaResultado.getRegistrosMuestra()) {
				registrosMuestra.add(transform((ACMInterfaceMid) acmInterfaceMid));
			}
			
			result.setRegistrosMuestra(registrosMuestra);
			
			result.setCantidadRegistros(metadataConsultaResultado.getCantidadRegistros());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Long count(MetadataConsultaTO metadataConsultaTO) {
		Long result = null;
		
		try {
			IACMInterfaceMidPHBean iACMInterfaceMidPHBean = lookupPHBean();
			
			result = 
				iACMInterfaceMidPHBean.count(
					MetadataConsultaDWR.transform(
						metadataConsultaTO
					)
				);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public MetadataConsultaResultadoTO listEnProceso(MetadataConsultaTO metadataConsultaTO) {
		MetadataConsultaResultadoTO result = new MetadataConsultaResultadoTO();
		
		try {
			// Condicion de estado "En proceso"
			Collection<String> valores = new LinkedList<String>();
			valores.add(Configuration.getInstance().getProperty("acmInterfaceEstado.EnProceso"));
			
			MetadataCondicionTO metadataCondicionTO = new MetadataCondicionTO();
			metadataCondicionTO.setCampo("estado.id");
			metadataCondicionTO.setOperador(Constants.__METADATA_CONDICION_OPERADOR_IGUAL);
			metadataCondicionTO.setValores(valores);
			
			metadataConsultaTO.getMetadataCondiciones().add(metadataCondicionTO);
			
			return this.list(metadataConsultaTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public MetadataConsultaResultadoTO listSinDatos(MetadataConsultaTO metadataConsultaTO) {
		MetadataConsultaResultadoTO result = new MetadataConsultaResultadoTO();
		
		try {
			// Condicion de estado no "Procesado"
			Collection<String> valores = new LinkedList<String>();
			valores.add(Configuration.getInstance().getProperty("acmInterfaceEstado.Procesado"));
			
			MetadataCondicionTO metadataCondicionTO = new MetadataCondicionTO();
			metadataCondicionTO.setCampo("estado.id");
			metadataCondicionTO.setOperador(Constants.__METADATA_CONDICION_OPERADOR_NOT_IGUAL);
			metadataCondicionTO.setValores(valores);
			
			metadataConsultaTO.getMetadataCondiciones().add(metadataCondicionTO);
			
			return this.list(metadataConsultaTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Long countSinDatos(MetadataConsultaTO metadataConsultaTO) {
		Long result = null;
		
		try {
			// Condicion de estado no "Procesado"
			Collection<String> valores = new LinkedList<String>();
			valores.add(Configuration.getInstance().getProperty("acmInterfaceEstado.Procesado"));
			
			MetadataCondicionTO metadataCondicionTO = new MetadataCondicionTO();
			metadataCondicionTO.setCampo("estado.id");
			metadataCondicionTO.setOperador(Constants.__METADATA_CONDICION_OPERADOR_NOT_IGUAL);
			metadataCondicionTO.setValores(valores);
			
			metadataConsultaTO.getMetadataCondiciones().add(metadataCondicionTO);
			
			return this.count(metadataConsultaTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public static ACMInterfaceMidTO transform(ACMInterfaceMid acmInterfaceMid) {
		ACMInterfaceMidTO acmInterfaceMidTO = new ACMInterfaceMidTO();
		
		if (acmInterfaceMid.getEstado() != null) {
			acmInterfaceMidTO.setEstado(ACMInterfaceEstadoDWR.transform(acmInterfaceMid.getEstado()));
		}
		
		acmInterfaceMidTO.setMid(acmInterfaceMid.getMid());
		acmInterfaceMidTO.setProcesoId(acmInterfaceMid.getProcesoId());
		
		acmInterfaceMidTO.setUact(acmInterfaceMid.getUact());
		acmInterfaceMidTO.setFact(acmInterfaceMid.getFact());
		acmInterfaceMidTO.setTerm(acmInterfaceMid.getTerm());
		
		return acmInterfaceMidTO;
	}
}