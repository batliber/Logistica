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

import uy.com.amensg.logistica.bean.IModeloBean;
import uy.com.amensg.logistica.bean.ModeloBean;
import uy.com.amensg.logistica.entities.EmpresaService;
import uy.com.amensg.logistica.entities.Marca;
import uy.com.amensg.logistica.entities.Modelo;
import uy.com.amensg.logistica.entities.ModeloTO;

@RemoteProxy
public class ModeloDWR {

	private IModeloBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = ModeloBean.class.getSimpleName();
		String remoteInterfaceName = IModeloBean.class.getName();
		String lookupName = prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		Context context = new InitialContext();
		
		return (IModeloBean) context.lookup(lookupName);
	}
	
	public Collection<ModeloTO> list() {
		Collection<ModeloTO> result = new LinkedList<ModeloTO>();
		
		try {
			IModeloBean iModeloBean = lookupBean();
			
			for (Modelo modelo : iModeloBean.list()) {
				result.add(transform(modelo));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Collection<ModeloTO> listByMarcaId(Long marcaId) {
		Collection<ModeloTO> result = new LinkedList<ModeloTO>();
		
		try {
			IModeloBean iModeloBean = lookupBean();
			
			for (Modelo modelo : iModeloBean.listByMarcaId(marcaId)) {
				result.add(transform(modelo));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public ModeloTO getById(Long id) {
		ModeloTO result = null;
		
		try {
			IModeloBean iModeloBean = lookupBean();
			
			result = transform(iModeloBean.getById(id));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public void add(ModeloTO modeloTO) {
		try {
			IModeloBean iModeloBean = lookupBean();
			
			iModeloBean.save(transform(modeloTO));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void remove(ModeloTO modeloTO) {
		try {
			IModeloBean iModeloBean = lookupBean();
			
			iModeloBean.remove(transform(modeloTO));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void update(ModeloTO modeloTO) {
		try {
			IModeloBean iModeloBean = lookupBean();
			
			iModeloBean.update(transform(modeloTO));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static ModeloTO transform(Modelo modelo) {
		ModeloTO modeloTO = new ModeloTO();
		
		modeloTO.setDescripcion(modelo.getDescripcion());
		modeloTO.setFechaBaja(modelo.getFechaBaja());
		
		if (modelo.getEmpresaService() != null) {
			modeloTO.setEmpresaService(EmpresaServiceDWR.transform(modelo.getEmpresaService()));
		}
		
		if (modelo.getMarca() != null) {
			modeloTO.setMarca(MarcaDWR.transform(modelo.getMarca()));
		}
		
		modeloTO.setFact(modelo.getFact());
		modeloTO.setId(modelo.getId());
		modeloTO.setTerm(modelo.getTerm());
		modeloTO.setUact(modelo.getUact());
		
		return modeloTO;
	}
	
	public static Modelo transform(ModeloTO modeloTO) {
		Modelo modelo = new Modelo();
		
		modelo.setDescripcion(modeloTO.getDescripcion());
		modelo.setFechaBaja(modeloTO.getFechaBaja());
		
		if (modeloTO.getEmpresaService() != null) {
			EmpresaService empresaService = new EmpresaService();
			empresaService.setId(modeloTO.getEmpresaService().getId());
			
			modelo.setEmpresaService(empresaService);
		}
		
		if (modeloTO.getMarca() != null) {
			Marca marca = new Marca();
			marca.setId(modeloTO.getMarca().getId());
			
			modelo.setMarca(marca);
		}
		
		Date date = GregorianCalendar.getInstance().getTime();
		
		modelo.setFact(date);
		modelo.setId(modeloTO.getId());
		modelo.setTerm(new Long(1));
		
		HttpSession httpSession = WebContextFactory.get().getSession(false);
		Long usuarioId = (Long) httpSession.getAttribute("sesion");
		
		modelo.setUact(usuarioId);
		
		return modelo;
	}
}