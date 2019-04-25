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
import uy.com.amensg.logistica.entities.Empresa;
import uy.com.amensg.logistica.entities.EmpresaTO;
import uy.com.amensg.logistica.entities.Marca;
import uy.com.amensg.logistica.entities.MarcaTO;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.MetadataConsultaResultadoTO;
import uy.com.amensg.logistica.entities.MetadataConsultaTO;
import uy.com.amensg.logistica.entities.Modelo;
import uy.com.amensg.logistica.entities.ModeloTO;
import uy.com.amensg.logistica.entities.Moneda;
import uy.com.amensg.logistica.entities.MonedaTO;
import uy.com.amensg.logistica.entities.Precio;
import uy.com.amensg.logistica.entities.PrecioTO;
import uy.com.amensg.logistica.entities.TipoProducto;
import uy.com.amensg.logistica.entities.TipoProductoTO;

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
	
	public MetadataConsultaResultadoTO listPreciosActualesContextAware(MetadataConsultaTO metadataConsultaTO) {
		MetadataConsultaResultadoTO result = new MetadataConsultaResultadoTO();
		
		try {
			HttpSession httpSession = WebContextFactory.get().getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				IPrecioBean iPrecioBean = lookupBean();
				
				MetadataConsultaResultado metadataConsultaResultado = 
					iPrecioBean.listPreciosActuales(
						MetadataConsultaDWR.transform(
							metadataConsultaTO
						),
						usuarioId
					);
				
				Collection<Object> registrosMuestra = new LinkedList<Object>();
				
				for (Object precio : metadataConsultaResultado.getRegistrosMuestra()) {
					registrosMuestra.add(PrecioDWR.transform((Precio) precio));
				}
				
				result.setRegistrosMuestra(registrosMuestra);
				result.setCantidadRegistros(metadataConsultaResultado.getCantidadRegistros());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Long countPreciosActualesContextAware(MetadataConsultaTO metadataConsultaTO) {
		Long result = null;
		
		try {
			HttpSession httpSession = WebContextFactory.get().getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				IPrecioBean iPrecioBean = lookupBean();
				
				result = 
					iPrecioBean.countPreciosActuales(
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
	
	public PrecioTO getActualByEmpresaTipoProductoMarcaModeloMonedaCuotas(
		EmpresaTO empresa, 
		TipoProductoTO tipoProducto, 
		MarcaTO marca, 
		ModeloTO modelo, 
		MonedaTO moneda,
		Long cuotas
	) {
		PrecioTO result = null;
		
		try {
			IPrecioBean iPrecioBean = lookupBean();
			
			Precio precio =
				iPrecioBean.getActualByEmpresaTipoProductoMarcaModeloMonedaCuotas(
					EmpresaDWR.transform(empresa),
					TipoProductoDWR.transform(tipoProducto),
					MarcaDWR.transform(marca),
					ModeloDWR.transform(modelo),
					MonedaDWR.transform(moneda),
					cuotas
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
		
		result.setCuotas(precio.getCuotas());
		result.setFechaHasta(precio.getFechaHasta());
		result.setPrecio(precio.getPrecio());
		
		if (precio.getEmpresa() != null) {
			result.setEmpresa(EmpresaDWR.transform(precio.getEmpresa(), false));
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
		
		if (precio.getTipoProducto() != null) {
			result.setTipoProducto(TipoProductoDWR.transform(precio.getTipoProducto()));
		}
		
		result.setFcre(precio.getFcre());
		result.setFact(precio.getFact());
		result.setId(precio.getId());
		result.setTerm(precio.getTerm());
		result.setUact(precio.getUact());
		result.setUcre(precio.getUcre());
		
		return result;
	}
	
	public static Precio transform(PrecioTO precioTO) {
		Precio result = new Precio();
		
		result.setCuotas(precioTO.getCuotas());
		result.setFechaHasta(precioTO.getFechaHasta());
		result.setPrecio(precioTO.getPrecio());
		
		if (precioTO.getEmpresa() != null) {
			Empresa empresa = new Empresa();
			empresa.setId(precioTO.getEmpresa().getId());
			
			result.setEmpresa(empresa);
		}
		
		if (precioTO.getMarca() != null) {
			Marca marca = new Marca();
			marca.setId(precioTO.getMarca().getId());
			
			result.setMarca(marca);
		}
		
		if (precioTO.getModelo() != null) {
			Modelo modelo = new Modelo();
			modelo.setId(precioTO.getModelo().getId());
			
			result.setModelo(modelo);
		}
		
		if (precioTO.getMoneda() != null) {
			Moneda moneda = new Moneda();
			moneda.setId(precioTO.getMoneda().getId());
			
			result.setMoneda(moneda);
		}
		
		if (precioTO.getTipoProducto() != null) {
			TipoProducto tipoProducto = new TipoProducto();
			tipoProducto.setId(precioTO.getTipoProducto().getId());
			
			result.setTipoProducto(tipoProducto);
		}
		
		Date date = GregorianCalendar.getInstance().getTime();
		
		result.setFcre(precioTO.getFcre());
		result.setFact(date);
		result.setId(precioTO.getId());
		result.setTerm(new Long(1));
		
		HttpSession httpSession = WebContextFactory.get().getSession(false);
		Long usuarioId = (Long) httpSession.getAttribute("sesion");
		
		result.setUact(usuarioId);
		result.setUcre(precioTO.getUcre());
		
		return result;
	}
}