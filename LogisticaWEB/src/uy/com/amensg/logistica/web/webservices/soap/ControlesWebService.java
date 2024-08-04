package uy.com.amensg.logistica.webservices;

import java.text.SimpleDateFormat;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import uy.com.amensg.logistica.bean.ControlBean;
import uy.com.amensg.logistica.bean.IControlBean;
import uy.com.amensg.logistica.entities.Control;
import uy.com.amensg.logistica.entities.Empresa;
import uy.com.amensg.logistica.entities.EstadoControl;
import uy.com.amensg.logistica.entities.TipoControl;

@WebService
public class ControlesWebService {

	@WebMethod
	public String getSiguienteMidParaControlar() {
		String result = "";
		
		try {
			IControlBean iControlBean = lookupControlBean();
			
			Control control = iControlBean.getSiguienteMidParaControlar();
			
			if (control != null) {
				result = 
					control.getMid() + " " +
					control.getEmpresa().getId() + " " +
					control.getTipoControl().getId();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@WebMethod
	public void actualizarDatosControl(
		String empresaId,
		String tipoControlId,
		String mid, 
		String estadoControlId,
		String fechaActivacion,
		String fechaConexion,
		String fechaVencimiento,
		String cargaInicial,
		String montoCargar,
		String montoTotal
	) {
		try {
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			
			Control control = new Control();
			
			Empresa empresa = new Empresa();
			try {
				empresa.setId(Long.parseLong(empresaId));
			} catch (Exception e) {
				e.printStackTrace();
				empresa = null;
			}
			control.setEmpresa(empresa);
			
			TipoControl tipoControl = new TipoControl();
			try {
				tipoControl.setId(Long.parseLong(tipoControlId));
			} catch (Exception e) {
				e.printStackTrace();
				tipoControl = null;
			}
			control.setTipoControl(tipoControl);
			
			control.setMid(Long.parseLong(mid));
			
			EstadoControl estadoControl = new EstadoControl();
			try {
				estadoControl.setId(Long.parseLong(estadoControlId));
			} catch (Exception e) {
				e.printStackTrace();
				estadoControl = null;
			}
			control.setEstadoControl(estadoControl);
			
			try {
				control.setFechaActivacion(format.parse(fechaActivacion));
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			try {
				control.setFechaConexion(format.parse(fechaConexion));
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			try {
				control.setFechaVencimiento(format.parse(fechaVencimiento));
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			try {
				control.setCargaInicial(Long.parseLong("0" + cargaInicial));
			} catch (Exception e) {
				e.printStackTrace();
			}		
			
			try {
				control.setMontoCargar(Long.parseLong("0" + montoCargar));
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				control.setMontoTotal(Long.parseLong("0" + montoTotal));
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			IControlBean iControlBean = lookupControlBean();
			
			iControlBean.actualizarDatosControl(control);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private IControlBean lookupControlBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = ControlBean.class.getSimpleName();
		String remoteInterfaceName = IControlBean.class.getName();
		String lookupName = prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		Context context = new InitialContext();
				
		return (IControlBean) context.lookup(lookupName);
	}
}