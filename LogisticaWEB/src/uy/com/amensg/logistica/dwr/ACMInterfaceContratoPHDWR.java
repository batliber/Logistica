package uy.com.amensg.logistica.dwr;

import java.util.Collection;
import java.util.LinkedList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpSession;

import org.directwebremoting.WebContextFactory;
import org.directwebremoting.annotations.RemoteProxy;

import uy.com.amensg.logistica.bean.ACMInterfaceContratoPHBean;
import uy.com.amensg.logistica.bean.IACMInterfaceContratoPHBean;
import uy.com.amensg.logistica.bean.IActivacionBean;
import uy.com.amensg.logistica.entities.ACMInterfaceContrato;
import uy.com.amensg.logistica.entities.ACMInterfaceContratoTO;
import uy.com.amensg.logistica.entities.EmpresaTO;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.MetadataConsultaResultadoTO;
import uy.com.amensg.logistica.entities.MetadataConsultaTO;
import uy.com.amensg.logistica.entities.TipoContrato;
import uy.com.amensg.logistica.entities.TipoContratoTO;

@RemoteProxy
public class ACMInterfaceContratoPHDWR {

	private IACMInterfaceContratoPHBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String remoteInterfaceName = IACMInterfaceContratoPHBean.class.getName();
		String beanName = ACMInterfaceContratoPHBean.class.getSimpleName();
		String lookupName = prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		Context context = new InitialContext();
		
		return (IACMInterfaceContratoPHBean) context.lookup(lookupName);
	}

	public MetadataConsultaResultadoTO list(MetadataConsultaTO metadataConsultaTO) {
		MetadataConsultaResultadoTO result = new MetadataConsultaResultadoTO();
		
		try {
			IACMInterfaceContratoPHBean iACMInterfaceContratoPHBean = lookupBean();
			
			MetadataConsultaResultado metadataConsultaResultado = 
				iACMInterfaceContratoPHBean.list(
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

	public Collection<TipoContratoTO> listTipoContratos(MetadataConsultaTO metadataConsultaTO) {
		Collection<TipoContratoTO> result = new LinkedList<TipoContratoTO>();
		
		try {
			IACMInterfaceContratoPHBean iACMInterfaceContratoPHBean = lookupBean();
			
			for (TipoContrato tipoContrato : 
				iACMInterfaceContratoPHBean.listTipoContratos(
					MetadataConsultaDWR.transform(
						metadataConsultaTO
					)
				)) {
				
				result.add(transform(tipoContrato));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public String exportarAExcel(MetadataConsultaTO metadataConsultaTO) {
		String result = "";
		
		try {
			HttpSession httpSession = WebContextFactory.get().getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				IACMInterfaceContratoPHBean iACMInterfaceContratoPHBean = lookupBean();
				
				result = iACMInterfaceContratoPHBean.exportarAExcel(MetadataConsultaDWR.transform(metadataConsultaTO), usuarioId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public String preprocesarExportacionByEmpresa(MetadataConsultaTO metadataConsultaTO, EmpresaTO empresaTO) {
		String result = null;
		
		try {
			IACMInterfaceContratoPHBean iACMInterfaceContratoPHBean = lookupBean();
			
			result = iACMInterfaceContratoPHBean.preprocesarExportacion(
				MetadataConsultaDWR.transform(
					metadataConsultaTO
				),
				EmpresaDWR.transform(empresaTO)
			);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public String exportarAExcelByEmpresa(MetadataConsultaTO metadataConsultaTO, EmpresaTO empresaTO, String observaciones) {
		String result = null;
		
		try {
			IACMInterfaceContratoPHBean iACMInterfaceContratoPHBean = lookupBean();
			
			result = iACMInterfaceContratoPHBean.exportarAExcel(
				MetadataConsultaDWR.transform(
					metadataConsultaTO
				),
				EmpresaDWR.transform(empresaTO),
				observaciones
			);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public void deshacerAsignacion(MetadataConsultaTO metadataConsultaTO) {
		try {
			IACMInterfaceContratoPHBean iACMInterfaceContratoPHBean = lookupBean();
			
			iACMInterfaceContratoPHBean.deshacerAsignacion(
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
		
		acmInterfaceContratoTO.setAgente(acmInterfaceContrato.getAgente());
		acmInterfaceContratoTO.setCodigoPostal(acmInterfaceContrato.getCodigoPostal());
		acmInterfaceContratoTO.setDireccion(acmInterfaceContrato.getDireccion());
		acmInterfaceContratoTO.setDocumentoTipo(acmInterfaceContrato.getDocumentoTipo());
		acmInterfaceContratoTO.setDocumento(acmInterfaceContrato.getDocumento());
		acmInterfaceContratoTO.setEquipo(acmInterfaceContrato.getEquipo());
		acmInterfaceContratoTO.setFechaFinContrato(acmInterfaceContrato.getFechaFinContrato());
		acmInterfaceContratoTO.setLocalidad(acmInterfaceContrato.getLocalidad());
		acmInterfaceContratoTO.setMid(acmInterfaceContrato.getMid());
		acmInterfaceContratoTO.setNombre(acmInterfaceContrato.getNombre());
		acmInterfaceContratoTO.setNumeroCliente(acmInterfaceContrato.getNumeroCliente());
		acmInterfaceContratoTO.setNumeroContrato(acmInterfaceContrato.getNumeroContrato());
		acmInterfaceContratoTO.setTipoContratoCodigo(acmInterfaceContrato.getTipoContratoCodigo());
		acmInterfaceContratoTO.setTipoContratoDescripcion(acmInterfaceContrato.getTipoContratoDescripcion());
		acmInterfaceContratoTO.setFechaExportacion(acmInterfaceContrato.getFechaExportacion());
		
		acmInterfaceContratoTO.setFact(acmInterfaceContrato.getFact());
		acmInterfaceContratoTO.setUact(acmInterfaceContrato.getUact());
		acmInterfaceContratoTO.setTerm(acmInterfaceContrato.getTerm());
		
		return acmInterfaceContratoTO;
	}

	public static TipoContratoTO transform(TipoContrato tipoContrato) {
		TipoContratoTO tipoContratoTO = new TipoContratoTO();
		
		tipoContratoTO.setTipoContratoCodigo(tipoContrato.getTipoContratoCodigo());
		tipoContratoTO.setTipoContratoDescripcion(tipoContrato.getTipoContratoDescripcion());
		
		return tipoContratoTO;
	}
}