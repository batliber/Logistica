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

import uy.com.amensg.logistica.bean.IProductoBean;
import uy.com.amensg.logistica.bean.ProductoBean;
import uy.com.amensg.logistica.entities.EmpresaService;
import uy.com.amensg.logistica.entities.Marca;
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
		ProductoTO productoTO = new ProductoTO();
		
		productoTO.setDescripcion(producto.getDescripcion());
		productoTO.setFechaBaja(producto.getFechaBaja());
		productoTO.setImei(producto.getImei());
		
		if (producto.getEmpresaService() != null) {
			productoTO.setEmpresaService(EmpresaServiceDWR.transform(producto.getEmpresaService()));
		}
		
		if (producto.getMarca() != null) {
			productoTO.setMarca(MarcaDWR.transform(producto.getMarca()));
		}
		
		if (producto.getModelo() != null) {
			productoTO.setModelo(ModeloDWR.transform(producto.getModelo()));
		}
		
		productoTO.setFact(producto.getFact());
		productoTO.setId(producto.getId());
		productoTO.setTerm(producto.getTerm());
		productoTO.setUact(producto.getUact());
		
		return productoTO;
	}
	
	public static Producto transform(ProductoTO productoTO) {
		Producto producto = new Producto();
		
		producto.setDescripcion(productoTO.getDescripcion());
		producto.setFechaBaja(productoTO.getFechaBaja());
		producto.setImei(productoTO.getImei());
		
		if (productoTO.getEmpresaService() != null) {
			EmpresaService empresaService = new EmpresaService();
			empresaService.setId(productoTO.getEmpresaService().getId());
			
			producto.setEmpresaService(empresaService);
		}
		
		if (productoTO.getMarca() != null) {
			Marca marca = new Marca();
			marca.setId(productoTO.getMarca().getId());
			
			producto.setMarca(marca);
		}
		
		if (productoTO.getModelo() != null) {
			Modelo modelo = new Modelo();
			modelo.setId(productoTO.getModelo().getId());
			
			producto.setModelo(modelo);
		}
		
		if (productoTO.getEmpresaService() != null) {
			EmpresaService empresaService = new EmpresaService();
			empresaService.setId(productoTO.getEmpresaService().getId());
			
			producto.setEmpresaService(empresaService);
		}
		
		if (productoTO.getMarca() != null) {
			Marca marca = new Marca();
			marca.setId(productoTO.getMarca().getId());
			
			producto.setMarca(marca);
		}
		
		Date date = GregorianCalendar.getInstance().getTime();
		
		producto.setFact(date);
		producto.setId(productoTO.getId());
		producto.setTerm(new Long(1));
		
		HttpSession httpSession = WebContextFactory.get().getSession(false);
		Long usuarioId = (Long) httpSession.getAttribute("sesion");
		
		producto.setUact(usuarioId);
		
		return producto;
	}
}