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

import uy.com.amensg.logistica.bean.IStockMovimientoBean;
import uy.com.amensg.logistica.bean.StockMovimientoBean;
import uy.com.amensg.logistica.entities.Marca;
import uy.com.amensg.logistica.entities.Modelo;
import uy.com.amensg.logistica.entities.StockMovimiento;
import uy.com.amensg.logistica.entities.StockMovimientoTO;

@RemoteProxy
public class StockMovimientoDWR {

	private IStockMovimientoBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = StockMovimientoBean.class.getSimpleName();
		String remoteInterfaceName = IStockMovimientoBean.class.getName();
		String lookupName = prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		Context context = new InitialContext();
		
		return (IStockMovimientoBean) context.lookup(lookupName);
	}
	
	public Collection<StockMovimientoTO> listStockActual() {
		Collection<StockMovimientoTO> result = new LinkedList<StockMovimientoTO>();
		
		try {
			IStockMovimientoBean iStockMovimientoBean = lookupBean();
			
			for (StockMovimiento stockMovimiento : iStockMovimientoBean.listStockActual()) {
				result.add(transform(stockMovimiento));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Collection<StockMovimientoTO> listStockByEmpresaId(Long id) {
		Collection<StockMovimientoTO> result = new LinkedList<StockMovimientoTO>();
		
		try {
			IStockMovimientoBean iStockMovimientoBean = lookupBean();
			
			for (StockMovimiento stockMovimiento : iStockMovimientoBean.listStockByEmpresaId(id)) {
				result.add(transform(stockMovimiento));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Collection<StockMovimientoTO> listByIMEI(String IMEI) {
		Collection<StockMovimientoTO> result = new LinkedList<StockMovimientoTO>();
		
		try {
			IStockMovimientoBean iStockMovimientoBean = lookupBean();
			
			for (StockMovimiento stockMovimiento : iStockMovimientoBean.listByIMEI(IMEI)) {
				result.add(transform(stockMovimiento));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public StockMovimientoTO getLastByIMEI(String imei) {
		StockMovimientoTO result = null;
		
		try {
			IStockMovimientoBean iStockMovimientoBean = lookupBean();
			
			StockMovimiento stockMovimiento = iStockMovimientoBean.getLastByIMEI(imei);
			if (stockMovimiento != null) {
				result =  transform(stockMovimiento);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public void add(StockMovimientoTO stockMovimientoTO) {
		try {
			IStockMovimientoBean iStockMovimientoBean = lookupBean();
			
			Date date = GregorianCalendar.getInstance().getTime();
			
			stockMovimientoTO.setFecha(date);
			stockMovimientoTO.setCantidad(stockMovimientoTO.getCantidad() * stockMovimientoTO.getStockTipoMovimiento().getSigno());
			
			iStockMovimientoBean.save(transform(stockMovimientoTO));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void add(Collection<StockMovimientoTO> stockMovimientoTOs) {
		try {
			IStockMovimientoBean iStockMovimientoBean = lookupBean();
			
			Date date = GregorianCalendar.getInstance().getTime();
			
			Collection<StockMovimiento> stockMovimientos = new LinkedList<StockMovimiento>();
			for (StockMovimientoTO stockMovimientoTO : stockMovimientoTOs) {
				stockMovimientoTO.setFecha(date);
				stockMovimientoTO.setCantidad(stockMovimientoTO.getCantidad() * stockMovimientoTO.getStockTipoMovimiento().getSigno());
				
				stockMovimientos.add(transform(stockMovimientoTO));
			}
			
			iStockMovimientoBean.save(stockMovimientos);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void transferir(Collection<StockMovimientoTO> stockMovimientoTOs, Long empresaDestinoId) {
		try {
			IStockMovimientoBean iStockMovimientoBean = lookupBean();
			
			Collection<StockMovimiento> stockMovimientos = new LinkedList<StockMovimiento>();
			for (StockMovimientoTO stockMovimientoTO : stockMovimientoTOs) {
				stockMovimientos.add(transform(stockMovimientoTO));
			}
			
			iStockMovimientoBean.transferir(stockMovimientos, empresaDestinoId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static StockMovimientoTO transform(StockMovimiento stockMovimiento) {
		StockMovimientoTO result = new StockMovimientoTO();
		
		result.setCantidad(stockMovimiento.getCantidad());
		result.setFecha(stockMovimiento.getFecha());
		
		if (stockMovimiento.getEmpresa() != null) {
			result.setEmpresa(EmpresaDWR.transform(stockMovimiento.getEmpresa()));
		}

		if (stockMovimiento.getMarca() != null) {
			result.setMarca(MarcaDWR.transform(stockMovimiento.getMarca()));
		}
		
		if (stockMovimiento.getModelo() != null) {
			result.setModelo(ModeloDWR.transform(stockMovimiento.getModelo()));
		}
		
		if (stockMovimiento.getProducto() != null) {
			result.setProducto(ProductoDWR.transform(stockMovimiento.getProducto()));
		}
		
		if (stockMovimiento.getStockTipoMovimiento() != null) {
			result.setStockTipoMovimiento(StockTipoMovimientoDWR.transform(stockMovimiento.getStockTipoMovimiento()));
		}
		
		result.setFact(stockMovimiento.getFact());
		result.setId(stockMovimiento.getId());
		result.setTerm(stockMovimiento.getTerm());
		result.setUact(stockMovimiento.getUact());
		
		return result;
	}
	
	public static StockMovimiento transform(StockMovimientoTO stockMovimientoTO) {
		StockMovimiento result = new StockMovimiento();
		
		result.setCantidad(stockMovimientoTO.getCantidad());
		result.setFecha(stockMovimientoTO.getFecha());
		
		if (stockMovimientoTO.getEmpresa() != null) {
			result.setEmpresa(EmpresaDWR.transform(stockMovimientoTO.getEmpresa()));
		}
		
		if (stockMovimientoTO.getMarca() != null) {
			Marca marca = new Marca();
			marca.setId(stockMovimientoTO.getMarca().getId());
			
			result.setMarca(marca);
		}
		
		if (stockMovimientoTO.getModelo() != null) {
			Modelo modelo = new Modelo();
			modelo.setId(stockMovimientoTO.getModelo().getId());
			
			result.setModelo(modelo);
		}
		
		if (stockMovimientoTO.getProducto() != null) {
			result.setProducto(ProductoDWR.transform(stockMovimientoTO.getProducto()));
		}
		
		if (stockMovimientoTO.getStockTipoMovimiento() != null) {
			result.setStockTipoMovimiento(StockTipoMovimientoDWR.transform(stockMovimientoTO.getStockTipoMovimiento()));
		}
		
		Date date = GregorianCalendar.getInstance().getTime();
		
		result.setFact(date);
		result.setId(stockMovimientoTO.getId());
		result.setTerm(new Long(1));
		
		HttpSession httpSession = WebContextFactory.get().getSession(false);
		Long usuarioId = (Long) httpSession.getAttribute("sesion");
		
		result.setUact(usuarioId);
		
		return result;
	}
}