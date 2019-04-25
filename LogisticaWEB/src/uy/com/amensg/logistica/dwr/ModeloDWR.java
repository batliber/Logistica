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
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.MetadataConsultaResultadoTO;
import uy.com.amensg.logistica.entities.MetadataConsultaTO;
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
	
	public Collection<ModeloTO> listVigentes() {
		Collection<ModeloTO> result = new LinkedList<ModeloTO>();
		
		try {
			IModeloBean iModeloBean = lookupBean();
			
			for (Modelo modelo : iModeloBean.listVigentes()) {
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
	
	public Collection<ModeloTO> listVigentesByMarcaId(Long marcaId) {
		Collection<ModeloTO> result = new LinkedList<ModeloTO>();
		
		try {
			IModeloBean iModeloBean = lookupBean();
			
			for (Modelo modelo : iModeloBean.listVigentesByMarcaId(marcaId)) {
				result.add(transform(modelo));
			}
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
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				IModeloBean iModeloBean = lookupBean();
				
				MetadataConsultaResultado metadataConsultaResultado = 
					iModeloBean.list(
						MetadataConsultaDWR.transform(
							metadataConsultaTO
						),
						usuarioId
					);
				
				Collection<Object> registrosMuestra = new LinkedList<Object>();
				
				for (Object modelo : metadataConsultaResultado.getRegistrosMuestra()) {
					registrosMuestra.add(ModeloDWR.transform((Modelo) modelo));
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
				
				IModeloBean iModeloBean = lookupBean();
				
				result = 
					iModeloBean.count(
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
		ModeloTO result = new ModeloTO();
		
		result.setDescripcion(modelo.getDescripcion());
		result.setFechaBaja(modelo.getFechaBaja());
		
		if (modelo.getEmpresaService() != null) {
			result.setEmpresaService(EmpresaServiceDWR.transform(modelo.getEmpresaService()));
		}
		
		if (modelo.getMarca() != null) {
			result.setMarca(MarcaDWR.transform(modelo.getMarca()));
		}
		
		result.setFcre(modelo.getFcre());
		result.setFact(modelo.getFact());
		result.setId(modelo.getId());
		result.setTerm(modelo.getTerm());
		result.setUact(modelo.getUact());
		result.setUcre(modelo.getUcre());
		
		return result;
	}
	
	public static Modelo transform(ModeloTO modeloTO) {
		Modelo result = new Modelo();
		
		result.setDescripcion(modeloTO.getDescripcion());
		result.setFechaBaja(modeloTO.getFechaBaja());
		
		if (modeloTO.getEmpresaService() != null) {
			EmpresaService empresaService = new EmpresaService();
			empresaService.setId(modeloTO.getEmpresaService().getId());
			
			result.setEmpresaService(empresaService);
		}
		
		if (modeloTO.getMarca() != null) {
			Marca marca = new Marca();
			marca.setId(modeloTO.getMarca().getId());
			
			result.setMarca(marca);
		}
		
		Date date = GregorianCalendar.getInstance().getTime();
		
		result.setFcre(modeloTO.getFcre());
		result.setFact(date);
		result.setId(modeloTO.getId());
		result.setTerm(new Long(1));
		
		HttpSession httpSession = WebContextFactory.get().getSession(false);
		Long usuarioId = (Long) httpSession.getAttribute("sesion");
		
		result.setUact(usuarioId);
		result.setUcre(modeloTO.getUcre());
		
		return result;
	}
}