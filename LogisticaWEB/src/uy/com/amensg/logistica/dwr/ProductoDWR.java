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

import uy.com.amensg.logistica.bean.IMarcaBean;
import uy.com.amensg.logistica.bean.IProductoBean;
import uy.com.amensg.logistica.bean.ProductoBean;
import uy.com.amensg.logistica.entities.EmpresaService;
import uy.com.amensg.logistica.entities.Marca;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.MetadataConsultaResultadoTO;
import uy.com.amensg.logistica.entities.MetadataConsultaTO;
import uy.com.amensg.logistica.entities.Modelo;
import uy.com.amensg.logistica.entities.Producto;
import uy.com.amensg.logistica.entities.ProductoTO;

@RemoteProxy
public class ProductoDWR {

	private IProductoBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = ProductoBean.class.getSimpleName();
		String remoteInterfaceName = IProductoBean.class.getName();
		String lookupName = prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		Context context = new InitialContext();
		
		return (IProductoBean) context.lookup(lookupName);
	}
	
	public Collection<ProductoTO> list() {
		Collection<ProductoTO> result = new LinkedList<ProductoTO>();
		
		try {
			IProductoBean iProductoBean = lookupBean();
			
			for (Producto producto : iProductoBean.list()) {
				result.add(transform(producto));
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
				
				IProductoBean iProductoBean = lookupBean();
				
				MetadataConsultaResultado metadataConsultaResultado = 
					iProductoBean.list(
						MetadataConsultaDWR.transform(
							metadataConsultaTO
						),
						usuarioId
					);
				
				Collection<Object> registrosMuestra = new LinkedList<Object>();
				
				for (Object producto : metadataConsultaResultado.getRegistrosMuestra()) {
					registrosMuestra.add(ProductoDWR.transform((Producto) producto));
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
				
				IProductoBean iProductoBean = lookupBean();
				
				result = 
					iProductoBean.count(
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
	
	public ProductoTO getById(Long id) {
		ProductoTO result = null;
		
		try {
			IProductoBean iProductoBean = lookupBean();
			
			result = transform(iProductoBean.getById(id));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public ProductoTO getByIMEI(String imei) {
		ProductoTO result = null;
		
		try {
			IProductoBean iProductoBean = lookupBean();
			
			Producto producto = iProductoBean.getByIMEI(imei);
			if (producto != null) {
				result = transform(producto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Boolean existeIMEI(String imei) {
		Boolean result = false;
		
		try {
			IProductoBean iProductoBean = lookupBean();
			
			result = iProductoBean.existeIMEI(imei);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public void add(ProductoTO productoTO) {
		try {
			IProductoBean iProductoBean = lookupBean();
			
			iProductoBean.save(transform(productoTO));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void remove(ProductoTO productoTO) {
		try {
			IProductoBean iProductoBean = lookupBean();
			
			iProductoBean.remove(transform(productoTO));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void update(ProductoTO productoTO) {
		try {
			IProductoBean iProductoBean = lookupBean();
			
			iProductoBean.update(transform(productoTO));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static ProductoTO transform(Producto producto) {
		ProductoTO result = new ProductoTO();
		
		result.setDescripcion(producto.getDescripcion());
		result.setFechaBaja(producto.getFechaBaja());
		result.setImei(producto.getImei());
		
		if (producto.getEmpresaService() != null) {
			result.setEmpresaService(EmpresaServiceDWR.transform(producto.getEmpresaService()));
		}
		
		if (producto.getMarca() != null) {
			result.setMarca(MarcaDWR.transform(producto.getMarca()));
		}
		
		if (producto.getModelo() != null) {
			result.setModelo(ModeloDWR.transform(producto.getModelo()));
		}
		
		result.setFcre(producto.getFcre());
		result.setFact(producto.getFact());
		result.setId(producto.getId());
		result.setTerm(producto.getTerm());
		result.setUact(producto.getUact());
		result.setUcre(producto.getUcre());
		
		return result;
	}
	
	public static Producto transform(ProductoTO productoTO) {
		Producto result = new Producto();
		
		result.setDescripcion(productoTO.getDescripcion());
		result.setFechaBaja(productoTO.getFechaBaja());
		result.setImei(productoTO.getImei());
		
		if (productoTO.getEmpresaService() != null) {
			EmpresaService empresaService = new EmpresaService();
			empresaService.setId(productoTO.getEmpresaService().getId());
			
			result.setEmpresaService(empresaService);
		}
		
		if (productoTO.getMarca() != null) {
			Marca marca = new Marca();
			marca.setId(productoTO.getMarca().getId());
			
			result.setMarca(marca);
		}
		
		if (productoTO.getModelo() != null) {
			Modelo modelo = new Modelo();
			modelo.setId(productoTO.getModelo().getId());
			
			result.setModelo(modelo);
		}
		
		if (productoTO.getEmpresaService() != null) {
			EmpresaService empresaService = new EmpresaService();
			empresaService.setId(productoTO.getEmpresaService().getId());
			
			result.setEmpresaService(empresaService);
		}
		
		if (productoTO.getMarca() != null) {
			Marca marca = new Marca();
			marca.setId(productoTO.getMarca().getId());
			
			result.setMarca(marca);
		}
		
		Date date = GregorianCalendar.getInstance().getTime();
		
		result.setFcre(productoTO.getFcre());
		result.setFact(date);
		result.setId(productoTO.getId());
		result.setTerm(new Long(1));
		
		HttpSession httpSession = WebContextFactory.get().getSession(false);
		Long usuarioId = (Long) httpSession.getAttribute("sesion");
		
		result.setUact(usuarioId);
		result.setUcre(productoTO.getUcre());
		
		return result;
	}
}