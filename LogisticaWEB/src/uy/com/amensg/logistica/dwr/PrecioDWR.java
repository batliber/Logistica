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

import uy.com.amensg.logistica.bean.IPrecioBean;
import uy.com.amensg.logistica.bean.PrecioBean;
import uy.com.amensg.logistica.entities.EmpresaTO;
import uy.com.amensg.logistica.entities.MarcaTO;
import uy.com.amensg.logistica.entities.ModeloTO;
import uy.com.amensg.logistica.entities.MonedaTO;
import uy.com.amensg.logistica.entities.Precio;
import uy.com.amensg.logistica.entities.PrecioTO;

@RemoteProxy
public class PrecioDWR {

	private IPrecioBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = PrecioBean.class.getSimpleName();
		String remoteInterfaceName = IPrecioBean.class.getName();
		String lookupName = prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		Context context = new InitialContext();
		
		return (IPrecioBean) context.lookup(lookupName);
	}
	
	public Collection<PrecioTO> listPreciosActuales() {
		Collection<PrecioTO> result = new LinkedList<PrecioTO>();
		
		try {
			IPrecioBean iPrecioBean = lookupBean();
			
			for (Precio Precio : iPrecioBean.listPreciosActuales()) {
				result.add(transform(Precio));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Collection<PrecioTO> listPreciosActualesByEmpresaId(Long id) {
		Collection<PrecioTO> result = new LinkedList<PrecioTO>();
		
		try {
			IPrecioBean iPrecioBean = lookupBean();
			
			for (Precio Precio : iPrecioBean.listPreciosActualesByEmpresaId(id)) {
				result.add(transform(Precio));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public PrecioTO getById(Long id) {
		PrecioTO result = null;
		
		try {
			IPrecioBean iPrecioBean = lookupBean();
			
			result = transform(iPrecioBean.getById(id));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public PrecioTO getActualByEmpresaMarcaModeloMoneda(EmpresaTO empresa, MarcaTO marca, ModeloTO modelo, MonedaTO moneda) {
		PrecioTO result = null;
		
		try {
			IPrecioBean iPrecioBean = lookupBean();
			
			Precio precio =
				iPrecioBean.getActualByEmpresaMarcaModeloMoneda(
					EmpresaDWR.transform(empresa),
					MarcaDWR.transform(marca),
					ModeloDWR.transform(modelo),
					MonedaDWR.transform(moneda)
				);
			
			if (precio != null) {
				result = transform(precio);
			} 	
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public void add(PrecioTO precioTO) {
		try {
			IPrecioBean iPrecioBean = lookupBean();
			
			iPrecioBean.save(transform(precioTO));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void update(PrecioTO precioTO) {
		try {
			IPrecioBean iPrecioBean = lookupBean();
			
			iPrecioBean.update(transform(precioTO));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static PrecioTO transform(Precio precio) {
		PrecioTO result = new PrecioTO();
		
		result.setFechaHasta(precio.getFechaHasta());
		result.setPrecio(precio.getPrecio());
		
		if (precio.getEmpresa() != null) {
			result.setEmpresa(EmpresaDWR.transform(precio.getEmpresa()));
		}
		
		if (precio.getMarca() != null) {
			result.setMarca(MarcaDWR.transform(precio.getMarca()));
		}
		
		if (precio.getModelo() != null) {
			result.setModelo(ModeloDWR.transform(precio.getModelo()));
		}
		
		if (precio.getMoneda() != null) {
			result.setMoneda(MonedaDWR.transform(precio.getMoneda()));
		}
		
		result.setFact(precio.getFact());
		result.setId(precio.getId());
		result.setTerm(precio.getTerm());
		result.setUact(precio.getUact());
		
		return result;
	}
	
	public static Precio transform(PrecioTO precioTO) {
		Precio result = new Precio();
		
		result.setFechaHasta(precioTO.getFechaHasta());
		result.setPrecio(precioTO.getPrecio());
		
		if (precioTO.getEmpresa() != null) {
			result.setEmpresa(EmpresaDWR.transform(precioTO.getEmpresa()));
		}
		
		if (precioTO.getMarca() != null) {
			result.setMarca(MarcaDWR.transform(precioTO.getMarca()));
		}
		
		if (precioTO.getModelo() != null) {
			result.setModelo(ModeloDWR.transform(precioTO.getModelo()));
		}
		
		if (precioTO.getMoneda() != null) {
			result.setMoneda(MonedaDWR.transform(precioTO.getMoneda()));
		}
		
		Date date = GregorianCalendar.getInstance().getTime();
		
		result.setFact(date);
		result.setId(precioTO.getId());
		result.setTerm(new Long(1));
		
		HttpSession httpSession = WebContextFactory.get().getSession(false);
		Long usuarioId = (Long) httpSession.getAttribute("sesion");
		
		result.setUact(usuarioId);
		
		return result;
	}
}