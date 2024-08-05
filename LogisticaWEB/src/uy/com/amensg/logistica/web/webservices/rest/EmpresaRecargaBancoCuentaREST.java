package uy.com.amensg.logistica.web.webservices.rest;

import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.LinkedList;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import uy.com.amensg.logistica.bean.EmpresaRecargaBancoCuentaBean;
import uy.com.amensg.logistica.bean.IEmpresaRecargaBancoCuentaBean;
import uy.com.amensg.logistica.entities.Empresa;
import uy.com.amensg.logistica.entities.EmpresaRecargaBancoCuenta;
import uy.com.amensg.logistica.entities.FormaPago;
import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.Moneda;
import uy.com.amensg.logistica.entities.RecargaBanco;
import uy.com.amensg.logistica.entities.Usuario;
import uy.com.amensg.logistica.util.Configuration;

@Path("/EmpresaRecargaBancoCuentaREST")
public class EmpresaRecargaBancoCuentaREST {

	@POST
	@Path("/listContextAware")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public MetadataConsultaResultado listContextAware(
		MetadataConsulta metadataConsulta, @Context HttpServletRequest request
	) {
		MetadataConsultaResultado result = new MetadataConsultaResultado();
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				IEmpresaRecargaBancoCuentaBean iEmpresaRecargaBancoCuentaBean = lookupBean();
				
				result = iEmpresaRecargaBancoCuentaBean.list(metadataConsulta, usuarioId);
				
				for (Object object : result.getRegistrosMuestra()) {
					EmpresaRecargaBancoCuenta empresaRecargaBancoCuenta = (EmpresaRecargaBancoCuenta) object;
					
					empresaRecargaBancoCuenta.getEmpresa().setEmpresaUsuarioContratos(new HashSet<Usuario>());
					empresaRecargaBancoCuenta.getEmpresa().setFormaPagos(new HashSet<FormaPago>());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@POST
	@Path("/countContextAware")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Long countContextAware(
		MetadataConsulta metadataConsulta, @Context HttpServletRequest request
	) {
		Long result = null;
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				IEmpresaRecargaBancoCuentaBean iEmpresaRecargaBancoCuentaBean = lookupBean();
				
				result = iEmpresaRecargaBancoCuentaBean.count(metadataConsulta, usuarioId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@GET
	@Path("/listByRecargaBancoId/{recargaBancoId}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Collection<EmpresaRecargaBancoCuenta> listByEmpresaById(
		@PathParam("recargaBancoId") Long recargaBancoId, @Context HttpServletRequest request
	) {
		Collection<EmpresaRecargaBancoCuenta> result = new LinkedList<EmpresaRecargaBancoCuenta>();
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				IEmpresaRecargaBancoCuentaBean iEmpresaRecargaBancoCuentaBean = lookupBean();
				
				Empresa empresa = new Empresa();
				empresa.setId(
					Long.decode(Configuration.getInstance().getProperty("empresa.ELARED"))
				);
				
				Moneda moneda = new Moneda();
				moneda.setId(
					Long.decode(Configuration.getInstance().getProperty("moneda.PesosUruguayos"))
				);
				
				RecargaBanco recargaBanco = new RecargaBanco();
				recargaBanco.setId(recargaBancoId);
				
				result = iEmpresaRecargaBancoCuentaBean.listByEmpresaRecargaBancoMoneda(empresa, recargaBanco, moneda);
				
				for (EmpresaRecargaBancoCuenta empresaRecargaBancoCuenta : result) {
					empresaRecargaBancoCuenta.getEmpresa().setEmpresaUsuarioContratos(new HashSet<Usuario>());
					empresaRecargaBancoCuenta.getEmpresa().setFormaPagos(new HashSet<FormaPago>());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@GET
	@Path("/getById/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public EmpresaRecargaBancoCuenta getById(@PathParam("id") Long id, @Context HttpServletRequest request) {
		EmpresaRecargaBancoCuenta result = null;
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				IEmpresaRecargaBancoCuentaBean iEmpresaRecargaBancoCuentaBean = lookupBean();
				
				result = iEmpresaRecargaBancoCuentaBean.getById(id);
				
				result.getEmpresa().setEmpresaUsuarioContratos(new HashSet<Usuario>());
				result.getEmpresa().setFormaPagos(new HashSet<FormaPago>());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	@POST
	@Path("/add")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public EmpresaRecargaBancoCuenta add(
		EmpresaRecargaBancoCuenta empresaRecargaBancoCuenta, @Context HttpServletRequest request
	) {
		EmpresaRecargaBancoCuenta result = null;
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long loggedUsuarioId = (Long) httpSession.getAttribute("sesion");
				
				IEmpresaRecargaBancoCuentaBean iEmpresaRecargaBancoCuentaBean = lookupBean();
				
				Date hoy = GregorianCalendar.getInstance().getTime();
				
				empresaRecargaBancoCuenta.setFact(hoy);
				empresaRecargaBancoCuenta.setFcre(hoy);
				empresaRecargaBancoCuenta.setTerm(Long.valueOf(1));
				empresaRecargaBancoCuenta.setUact(loggedUsuarioId);
				empresaRecargaBancoCuenta.setUcre(loggedUsuarioId);
				
				result = iEmpresaRecargaBancoCuentaBean.save(empresaRecargaBancoCuenta);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@POST
	@Path("/update")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public void update(EmpresaRecargaBancoCuenta empresaRecargaBancoCuenta, @Context HttpServletRequest request) {
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long loggedUsuarioId = (Long) httpSession.getAttribute("sesion");
				
				IEmpresaRecargaBancoCuentaBean iEmpresaRecargaBancoCuentaBean = lookupBean();
				
				Date hoy = GregorianCalendar.getInstance().getTime();
				
				empresaRecargaBancoCuenta.setFact(hoy);
				empresaRecargaBancoCuenta.setTerm(Long.valueOf(1));
				empresaRecargaBancoCuenta.setUact(loggedUsuarioId);
				
				iEmpresaRecargaBancoCuentaBean.update(empresaRecargaBancoCuenta);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@POST
	@Path("/remove")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public void remove(EmpresaRecargaBancoCuenta empresaRecargaBancoCuenta, @Context HttpServletRequest request) {
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long loggedUsuarioId = (Long) httpSession.getAttribute("sesion");
				
				IEmpresaRecargaBancoCuentaBean iEmpresaRecargaBancoCuentaBean = lookupBean();
				
				Date hoy = GregorianCalendar.getInstance().getTime();
				
				empresaRecargaBancoCuenta.setFact(hoy);
				empresaRecargaBancoCuenta.setTerm(Long.valueOf(1));
				empresaRecargaBancoCuenta.setUact(loggedUsuarioId);
				
				iEmpresaRecargaBancoCuentaBean.remove(empresaRecargaBancoCuenta);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private IEmpresaRecargaBancoCuentaBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = EmpresaRecargaBancoCuentaBean.class.getSimpleName();
		String remoteInterfaceName = IEmpresaRecargaBancoCuentaBean.class.getName();
		String lookupName = 
			prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		
		javax.naming.Context context = new InitialContext();
		
		return (IEmpresaRecargaBancoCuentaBean) context.lookup(lookupName);
	}
}