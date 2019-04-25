package uy.com.amensg.logistica.dwr;

import java.util.Collection;
import java.util.LinkedList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpSession;

import org.directwebremoting.WebContextFactory;
import org.directwebremoting.annotations.RemoteProxy;

import uy.com.amensg.logistica.bean.ACMInterfacePrepagoBean;
import uy.com.amensg.logistica.bean.IACMInterfacePrepagoBean;
import uy.com.amensg.logistica.entities.ACMInterfacePrepago;
import uy.com.amensg.logistica.entities.ACMInterfacePrepagoTO;
import uy.com.amensg.logistica.entities.Empresa;
import uy.com.amensg.logistica.entities.EmpresaTO;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.MetadataConsultaResultadoTO;
import uy.com.amensg.logistica.entities.MetadataConsultaTO;
import uy.com.amensg.logistica.entities.TipoControlRiesgoCrediticio;

@RemoteProxy
public class ACMInterfacePrepagoDWR {

	private IACMInterfacePrepagoBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = ACMInterfacePrepagoBean.class.getSimpleName();
		String remoteInterfaceName = IACMInterfacePrepagoBean.class.getName();
		String lookupName = prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		Context context = new InitialContext();
		
		return (IACMInterfacePrepagoBean) context.lookup(lookupName);
	}
	
	public MetadataConsultaResultadoTO list(MetadataConsultaTO metadataConsultaTO) {
		MetadataConsultaResultadoTO result = new MetadataConsultaResultadoTO();
		
		try {
			IACMInterfacePrepagoBean iACMInterfacePrepagoBean = lookupBean();
			
			MetadataConsultaResultado metadataConsultaResultado = 
				iACMInterfacePrepagoBean.list(
					MetadataConsultaDWR.transform(
						metadataConsultaTO
					)
				);
			
			Collection<Object> registrosMuestra = new LinkedList<Object>();
			
			for (Object acmInterfacePrepago : metadataConsultaResultado.getRegistrosMuestra()) {
				registrosMuestra.add(transform((ACMInterfacePrepago) acmInterfacePrepago));
			}
			
			result.setRegistrosMuestra(registrosMuestra);
			
			result.setCantidadRegistros(metadataConsultaResultado.getCantidadRegistros());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public MetadataConsultaResultadoTO listContextAware(MetadataConsultaTO metadataConsultaTO) {
		MetadataConsultaResultadoTO result = new MetadataConsultaResultadoTO();
		
		try {
			HttpSession httpSession = WebContextFactory.get().getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
//				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				IACMInterfacePrepagoBean iACMInterfacePrepagoBean = lookupBean();
				
				MetadataConsultaResultado metadataConsultaResultado = 
					iACMInterfacePrepagoBean.list(
						MetadataConsultaDWR.transform(
							metadataConsultaTO
						)
					);
				
				Collection<Object> registrosMuestra = new LinkedList<Object>();
				
				for (Object acmInterfacePrepago : metadataConsultaResultado.getRegistrosMuestra()) {
					registrosMuestra.add(transform((ACMInterfacePrepago) acmInterfacePrepago));
				}
				
				result.setRegistrosMuestra(registrosMuestra);
				result.setCantidadRegistros(metadataConsultaResultado.getCantidadRegistros());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Long countContextAware(MetadataConsultaTO metadataConsultaTO) {
		Long result = null;
		
		try {
			HttpSession httpSession = WebContextFactory.get().getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
//				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				IACMInterfacePrepagoBean iACMInterfacePrepagoBean = lookupBean();
				
				result = 
					iACMInterfacePrepagoBean.count(
						MetadataConsultaDWR.transform(
							metadataConsultaTO
						)
					);
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
				
				IACMInterfacePrepagoBean iACMInterfacePrepagoBean = lookupBean();
				
				result = iACMInterfacePrepagoBean.exportarAExcel(MetadataConsultaDWR.transform(metadataConsultaTO), usuarioId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public String preprocesarExportacionByEmpresa(MetadataConsultaTO metadataConsultaTO, EmpresaTO empresaTO) {
		String result = null;
		
		try {
			IACMInterfacePrepagoBean iACMInterfacePrepagoBean = lookupBean();
			
			result = iACMInterfacePrepagoBean.preprocesarExportacion(
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
			IACMInterfacePrepagoBean iACMInterfacePrepagoBean = lookupBean();
			
			result = iACMInterfacePrepagoBean.exportarAExcel(
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
			IACMInterfacePrepagoBean iACMInterfacePrepagoBean = lookupBean();
			
			iACMInterfacePrepagoBean.deshacerAsignacion(
				MetadataConsultaDWR.transform(
					metadataConsultaTO
				)
			);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void reprocesar(MetadataConsultaTO metadataConsultaTO, String observaciones) {
		try {
			IACMInterfacePrepagoBean iACMInterfacePrepagoBean = lookupBean();
			
			iACMInterfacePrepagoBean.reprocesar(
				MetadataConsultaDWR.transform(
					metadataConsultaTO
				),
				observaciones
			);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void agregarAListaNegra(MetadataConsultaTO metadataConsultaTO) {
		try {
			IACMInterfacePrepagoBean iACMInterfacePrepagoBean = lookupBean();
			
			iACMInterfacePrepagoBean.agregarAListaNegra(
				MetadataConsultaDWR.transform(
					metadataConsultaTO
				)
			);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void controlarRiesgoCrediticio(
		MetadataConsultaTO metadataConsultaTO,
		Long empresaId,
		Long tipoControlRiesgoCrediticioId
	) {
		try {
			IACMInterfacePrepagoBean iACMInterfacePrepagoBean = lookupBean();
			
			Empresa empresa = new Empresa();
			empresa.setId(empresaId);
			
			TipoControlRiesgoCrediticio tipoControlRiesgoCrediticio = new TipoControlRiesgoCrediticio();
			tipoControlRiesgoCrediticio.setId(tipoControlRiesgoCrediticioId);
			
			iACMInterfacePrepagoBean.controlarRiesgoCrediticio(
				empresa,
				tipoControlRiesgoCrediticio,
				MetadataConsultaDWR.transform(
					metadataConsultaTO
				)
			);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static ACMInterfacePrepagoTO transform(ACMInterfacePrepago acmInterfacePrepago) {
		ACMInterfacePrepagoTO acmInterfacePrepagoTO = new ACMInterfacePrepagoTO();
		
		acmInterfacePrepagoTO.setAgente(acmInterfacePrepago.getAgente());
		acmInterfacePrepagoTO.setMesAno(acmInterfacePrepago.getMesAno());
		acmInterfacePrepagoTO.setMid(acmInterfacePrepago.getMid());
		acmInterfacePrepagoTO.setMontoMesActual(acmInterfacePrepago.getMontoMesActual());
		acmInterfacePrepagoTO.setMontoMesAnterior1(acmInterfacePrepago.getMontoMesAnterior1());
		acmInterfacePrepagoTO.setMontoMesAnterior2(acmInterfacePrepago.getMontoMesAnterior2());
		acmInterfacePrepagoTO.setMontoPromedio(acmInterfacePrepago.getMontoPromedio());
		acmInterfacePrepagoTO.setFechaExportacion(acmInterfacePrepago.getFechaExportacion());
		acmInterfacePrepagoTO.setFechaActivacionKit(acmInterfacePrepago.getFechaActivacionKit());
		
		if (acmInterfacePrepago.getAcmInterfacePersona() != null) {
			acmInterfacePrepagoTO.setAcmInterfacePersona(ACMInterfacePersonaDWR.transform(acmInterfacePrepago.getAcmInterfacePersona()));
		}
		
		acmInterfacePrepagoTO.setFact(acmInterfacePrepago.getFact());
		acmInterfacePrepagoTO.setUact(acmInterfacePrepago.getUact());
		acmInterfacePrepagoTO.setTerm(acmInterfacePrepago.getTerm());
		
		return acmInterfacePrepagoTO;
	}
}