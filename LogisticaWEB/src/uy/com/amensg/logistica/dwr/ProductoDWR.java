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
import uy.com.amensg.logistica.entities.Producto;
import uy.com.amensg.logistica.entities.ProductoTO;

@RemoteProxy
public class ProductoDWR {

	private IProductoBean lookupBean() throws NamingException {
		String EARName = "Logistica";
		String beanName = ProductoBean.class.getSimpleName();
		String remoteInterfaceName = IProductoBean.class.getName();
		String lookupName = EARName + "/" + beanName + "/remote-" + remoteInterfaceName;
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
			
			iProductoBean.save(transform(productoTO));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static ProductoTO transform(Producto producto) {
		ProductoTO productoTO = new ProductoTO();
		
		productoTO.setDescripcion(producto.getDescripcion());
		productoTO.setFechaBaja(producto.getFechaBaja());
		
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