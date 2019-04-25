package uy.com.amensg.logistica.dwr;

import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpSession;

import org.directwebremoting.WebContextFactory;
import org.directwebremoting.annotations.RemoteProxy;

import uy.com.amensg.logistica.bean.ControlBean;
import uy.com.amensg.logistica.bean.IControlBean;
import uy.com.amensg.logistica.entities.Control;
import uy.com.amensg.logistica.entities.ControlTO;
import uy.com.amensg.logistica.entities.Empresa;
import uy.com.amensg.logistica.entities.EstadoControl;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.MetadataConsultaResultadoTO;
import uy.com.amensg.logistica.entities.MetadataConsultaTO;
import uy.com.amensg.logistica.entities.PuntoVenta;
import uy.com.amensg.logistica.entities.TipoControl;
import uy.com.amensg.logistica.entities.Usuario;

@RemoteProxy
public class ControlDWR {

	private IControlBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = ControlBean.class.getSimpleName();
		String remoteInterfaceName = IControlBean.class.getName();
		String lookupName = prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		Context context = new InitialContext();
		
		return (IControlBean) context.lookup(lookupName);
	}
	
	public MetadataConsultaResultadoTO listContextAware(MetadataConsultaTO metadataConsultaTO) {
		MetadataConsultaResultadoTO result = new MetadataConsultaResultadoTO();
		
		try {
			HttpSession httpSession = WebContextFactory.get().getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				IControlBean iControlBean = lookupBean();
				
				MetadataConsultaResultado metadataConsultaResultado = 
					iControlBean.list(
						MetadataConsultaDWR.transform(
							metadataConsultaTO
						),
						usuarioId
					);
				
				Collection<Object> registrosMuestra = new LinkedList<Object>();
				
				for (Object control : metadataConsultaResultado.getRegistrosMuestra()) {
					registrosMuestra.add(ControlDWR.transform((Control) control));
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
				
				IControlBean iControlBean = lookupBean();
				
				result = 
					iControlBean.count(
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
	
	public String procesarArchivoEmpresa(String fileName, Long empresaId, Long tipoControlId) {
		String result = null;
		
		try {
			IControlBean iControlBean = lookupBean();
			
			HttpSession httpSession = WebContextFactory.get().getSession(false);
			Long usuarioId = (Long) httpSession.getAttribute("sesion");
			
			result = iControlBean.procesarArchivoEmpresa(fileName, empresaId, tipoControlId, usuarioId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public ControlTO getById(Long id) {
		ControlTO result = null;
		
		try {
			IControlBean iControlBean = lookupBean();
			
			Control control = iControlBean.getById(id);
			if (control != null) {
				result = transform(control);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public void update(ControlTO controlTO) {
		try {
			IControlBean iControlBean = lookupBean();
			
			iControlBean.update(transform(controlTO));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String exportarAExcel(MetadataConsultaTO metadataConsultaTO) {
		String result = "";
		
		try {
			HttpSession httpSession = WebContextFactory.get().getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				IControlBean iControlBean = lookupBean();
				
				result = iControlBean.exportarAExcel(MetadataConsultaDWR.transform(metadataConsultaTO), usuarioId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static ControlTO transform(Control control) {
		ControlTO result = new ControlTO();
		
		result.setCargaInicial(control.getCargaInicial());
		result.setFechaActivacion(control.getFechaActivacion());
		result.setFechaAsignacionDistribuidor(control.getFechaAsignacionDistribuidor());
		result.setFechaAsignacionPuntoVenta(control.getFechaAsignacionPuntoVenta());
		result.setFechaConexion(control.getFechaConexion());
		result.setFechaControl(control.getFechaControl());
		result.setFechaImportacion(control.getFechaImportacion());
		result.setFechaVencimiento(control.getFechaVencimiento());
		result.setMesControl(control.getMesControl());
		result.setMid(control.getMid());
		result.setMontoCargar(control.getMontoCargar());
		result.setMontoTotal(control.getMontoTotal());
		
		if (control.getDistribuidor() != null) {
			result.setDistribuidor(UsuarioDWR.transform(control.getDistribuidor(), false));
		}
		
		if (control.getEmpresa() != null) {
			result.setEmpresa(EmpresaDWR.transform(control.getEmpresa(), false));
		}
		
		if (control.getEstadoControl() != null) {
			result.setEstadoControl(EstadoControlDWR.transform(control.getEstadoControl()));
		}
		
		if (control.getPuntoVenta() != null) {
			result.setPuntoVenta(PuntoVentaDWR.transform(control.getPuntoVenta()));
		}
		
		if (control.getTipoControl() != null) {
			result.setTipoControl(TipoControlDWR.transform(control.getTipoControl()));
		}
		
		result.setFcre(control.getFcre());
		result.setFact(control.getFact());
		result.setId(control.getId());
		result.setTerm(control.getTerm());
		result.setUact(control.getUact());
		result.setUcre(control.getUcre());
		
		return result;
	}

	public static Control transform(ControlTO controlTO) {
		Control result = new Control();
		
		result.setCargaInicial(controlTO.getCargaInicial());
		result.setFechaActivacion(controlTO.getFechaActivacion());
		result.setFechaAsignacionDistribuidor(controlTO.getFechaAsignacionDistribuidor());
		result.setFechaAsignacionPuntoVenta(controlTO.getFechaAsignacionPuntoVenta());
		result.setFechaConexion(controlTO.getFechaConexion());
		result.setFechaControl(controlTO.getFechaControl());
		result.setFechaImportacion(controlTO.getFechaImportacion());
		result.setFechaVencimiento(controlTO.getFechaVencimiento());
		result.setMesControl(controlTO.getMesControl());
		result.setMid(controlTO.getMid());
		result.setMontoCargar(controlTO.getMontoCargar());
		result.setMontoTotal(controlTO.getMontoTotal());
		
		if (controlTO.getDistribuidor() != null) {
			Usuario distribuidor = new Usuario();
			distribuidor.setId(controlTO.getDistribuidor().getId());
			
			result.setDistribuidor(distribuidor);
		}
		
		if (controlTO.getEmpresa() != null) {
			Empresa empresa = new Empresa();
			empresa.setId(controlTO.getEmpresa().getId());
			
			result.setEmpresa(empresa);
		}
		
		if (controlTO.getEstadoControl() != null) {
			EstadoControl estadoControl = new EstadoControl();
			estadoControl.setId(controlTO.getEstadoControl().getId());
			
			result.setEstadoControl(estadoControl);
		}
		
		if (controlTO.getPuntoVenta() != null) {
			PuntoVenta puntoVenta = new PuntoVenta();
			puntoVenta.setId(controlTO.getPuntoVenta().getId());
			
			result.setPuntoVenta(puntoVenta);
		}
		
		if (controlTO.getTipoControl() != null) {
			TipoControl tipoControl = new TipoControl();
			tipoControl.setId(controlTO.getTipoControl().getId());
			
			result.setTipoControl(tipoControl);
		}
		
		Date date = GregorianCalendar.getInstance().getTime();
		
		result.setFcre(controlTO.getFcre());
		result.setFact(date);
		result.setId(controlTO.getId());
		result.setTerm(new Long(1));
		
		HttpSession httpSession = WebContextFactory.get().getSession(false);
		Long usuarioId = (Long) httpSession.getAttribute("sesion");
		
		result.setUact(usuarioId);
		result.setUcre(controlTO.getUcre());

		return result;
	}
}