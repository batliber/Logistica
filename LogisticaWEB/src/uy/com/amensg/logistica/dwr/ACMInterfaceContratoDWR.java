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
import uy.com.amensg.logistica.entities.EmpresaTO;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.MetadataConsultaResultadoTO;
import uy.com.amensg.logistica.entities.MetadataConsultaTO;
import uy.com.amensg.logistica.entities.TipoContrato;
import uy.com.amensg.logistica.entities.TipoContratoTO;

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
	
	public String preprocesarExportacionByEmpresa(MetadataConsultaTO metadataConsultaTO, EmpresaTO empresaTO) {
		String result = null;
		
		try {
			IACMInterfaceContratoBean iACMInterfaceContratoBean = lookupBean();
			
			result = iACMInterfaceContratoBean.preprocesarExportacion(
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
			IACMInterfaceContratoBean iACMInterfaceContratoBean = lookupBean();
			
			result = iACMInterfaceContratoBean.exportarAExcel(
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
	
	public void reprocesar(MetadataConsultaTO metadataConsultaTO, String observaciones) {
		try {
			IACMInterfaceContratoBean iACMInterfaceContratoBean = lookupBean();
			
			iACMInterfaceContratoBean.reprocesar(
				MetadataConsultaDWR.transform(
					metadataConsultaTO
				),
				observaciones
			);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void deshacerAsignacion(MetadataConsultaTO metadataConsultaTO) {
		try {
			IACMInterfaceContratoBean iACMInterfaceContratoBean = lookupBean();
			
			iACMInterfaceContratoBean.deshacerAsignacion(
				MetadataConsultaDWR.transform(
					metadataConsultaTO
				)
			);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void agregarAListaNegra(MetadataConsultaTO metadataConsultaTO) {
		try {
			IACMInterfaceContratoBean iACMInterfaceContratoBean = lookupBean();
			
			iACMInterfaceContratoBean.agregarAListaNegra(
				MetadataConsultaDWR.transform(
					metadataConsultaTO
				)
			);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Collection<TipoContratoTO> listTipoContratos(MetadataConsultaTO metadataConsultaTO) {
		Collection<TipoContratoTO> result = new LinkedList<TipoContratoTO>();
		
		try {
			IACMInterfaceContratoBean iACMInterfaceContratoBean = lookupBean();
			
			for (TipoContrato tipoContrato : 
				iACMInterfaceContratoBean.listTipoContratos(
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