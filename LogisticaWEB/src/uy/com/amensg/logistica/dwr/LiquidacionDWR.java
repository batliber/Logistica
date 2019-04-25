package uy.com.amensg.logistica.dwr;

import java.util.Collection;
import java.util.LinkedList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpSession;

import org.directwebremoting.WebContextFactory;
import org.directwebremoting.annotations.RemoteProxy;

import uy.com.amensg.logistica.bean.ILiquidacionBean;
import uy.com.amensg.logistica.bean.LiquidacionBean;
import uy.com.amensg.logistica.entities.Liquidacion;
import uy.com.amensg.logistica.entities.LiquidacionTO;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.MetadataConsultaResultadoTO;
import uy.com.amensg.logistica.entities.MetadataConsultaTO;

@RemoteProxy
public class LiquidacionDWR {

	private ILiquidacionBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = LiquidacionBean.class.getSimpleName();
		String remoteInterfaceName = ILiquidacionBean.class.getName();
		String lookupName = prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		Context context = new InitialContext();
		
		return (ILiquidacionBean) context.lookup(lookupName);
	}

	public MetadataConsultaResultadoTO listContextAware(MetadataConsultaTO metadataConsultaTO) {
		MetadataConsultaResultadoTO result = new MetadataConsultaResultadoTO();
		
		try {
			HttpSession httpSession = WebContextFactory.get().getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				ILiquidacionBean iLiquidacionBean = lookupBean();
				
				MetadataConsultaResultado metadataConsultaResultado = 
					iLiquidacionBean.list(
						MetadataConsultaDWR.transform(
							metadataConsultaTO
						),
						usuarioId
					);
				
				Collection<Object> registrosMuestra = new LinkedList<Object>();
				
				for (Object liquidacion : metadataConsultaResultado.getRegistrosMuestra()) {
					registrosMuestra.add(LiquidacionDWR.transform((Liquidacion) liquidacion));
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
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				ILiquidacionBean iLiquidacionBean = lookupBean();
				
				result = 
					iLiquidacionBean.count(
						MetadataConsultaDWR.transform(
							metadataConsultaTO
						),
						usuarioId
					);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public String procesarArchivo(String fileName) {
		String result = null;
		
		try {
			ILiquidacionBean iLiquidacionBean = lookupBean();
			
			HttpSession httpSession = WebContextFactory.get().getSession(false);
			Long usuarioId = (Long) httpSession.getAttribute("sesion");
			
			result = iLiquidacionBean.procesarArchivo(fileName, usuarioId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public void calcularPorcentajeActivacionPuntoVentas() {
		try {
			ILiquidacionBean iLiquidacionBean = lookupBean();
			
			HttpSession httpSession = WebContextFactory.get().getSession(false);
			Long usuarioId = (Long) httpSession.getAttribute("sesion");
			
			iLiquidacionBean.calcularPorcentajeActivacionPuntoVentas(usuarioId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void calcularPorcentajeActivacionSubLotes() {
		try {
			ILiquidacionBean iLiquidacionBean = lookupBean();
			
			HttpSession httpSession = WebContextFactory.get().getSession(false);
			Long usuarioId = (Long) httpSession.getAttribute("sesion");
			
			iLiquidacionBean.calcularPorcentajeActivacionSubLotes(usuarioId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static LiquidacionTO transform(Liquidacion liquidacion) {
		LiquidacionTO result = new LiquidacionTO();
		
		result.setIdRegistro(liquidacion.getIdRegistro());
		result.setNumeroContrato(liquidacion.getNumeroContrato());
		result.setFecha(liquidacion.getFecha());
		result.setPlan(liquidacion.getPlan());
		result.setMid(liquidacion.getMid());
		result.setSaid(liquidacion.getSaid());
		result.setSerie(liquidacion.getSerie());
		result.setDc(liquidacion.getDc());
		result.setImporte(liquidacion.getImporte());
		result.setCant(liquidacion.getCant());
		result.setFechaLiquidacion(liquidacion.getFechaLiquidacion());
		result.setIdConcepto(liquidacion.getIdConcepto());
		result.setNombreConcepto(liquidacion.getNombreConcepto());
		result.setModelo(liquidacion.getModelo());
		result.setFabricante(liquidacion.getFabricante());
		result.setIdClaseConcepto(liquidacion.getIdClaseConcepto());
		result.setNomClaseConcepto(liquidacion.getNomClaseConcepto());
		result.setRlid(liquidacion.getRlid());
		
		if (liquidacion.getEmpresa() != null) {
			result.setEmpresa(EmpresaDWR.transform(liquidacion.getEmpresa(), false));
		}
		
		if (liquidacion.getMoneda() != null) {
			result.setMoneda(MonedaDWR.transform(liquidacion.getMoneda()));
		}
		
		result.setFcre(liquidacion.getFcre());
		result.setFact(liquidacion.getFact());
		result.setId(liquidacion.getId());
		result.setTerm(liquidacion.getTerm());
		result.setUcre(liquidacion.getUcre());
		
		return result;
	}
}