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

import uy.com.amensg.logistica.bean.DisponibilidadEntregaEmpresaZonaTurnoBean;
import uy.com.amensg.logistica.bean.IDisponibilidadEntregaEmpresaZonaTurnoBean;
import uy.com.amensg.logistica.entities.DisponibilidadEntregaEmpresaZonaTurno;
import uy.com.amensg.logistica.entities.DisponibilidadEntregaEmpresaZonaTurnoTO;
import uy.com.amensg.logistica.entities.Empresa;
import uy.com.amensg.logistica.entities.EmpresaTO;
import uy.com.amensg.logistica.entities.Turno;
import uy.com.amensg.logistica.entities.TurnoTO;
import uy.com.amensg.logistica.entities.Zona;
import uy.com.amensg.logistica.entities.ZonaTO;

@RemoteProxy
public class DisponibilidadEntregaEmpresaZonaTurnoDWR {

	private IDisponibilidadEntregaEmpresaZonaTurnoBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = DisponibilidadEntregaEmpresaZonaTurnoBean.class.getSimpleName();
		String remoteInterfaceName = IDisponibilidadEntregaEmpresaZonaTurnoBean.class.getName();
		String lookupName = prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		Context context = new InitialContext();
		
		return (IDisponibilidadEntregaEmpresaZonaTurnoBean) context.lookup(lookupName);
	}

	public Collection<DisponibilidadEntregaEmpresaZonaTurnoTO> listByEmpresaZona(
		EmpresaTO empresaTO, ZonaTO zonaTO
	) {
		Collection<DisponibilidadEntregaEmpresaZonaTurnoTO> result = new LinkedList<DisponibilidadEntregaEmpresaZonaTurnoTO>();
		
		try {
			IDisponibilidadEntregaEmpresaZonaTurnoBean iDisponibilidadEntregaEmpresaZonaTurnoBean = lookupBean();
			
			Collection<DisponibilidadEntregaEmpresaZonaTurno> disponibilidadEntregaEmpresaZonaTurnos = 
				iDisponibilidadEntregaEmpresaZonaTurnoBean.listByEmpresaZona(
					EmpresaDWR.transform(empresaTO), 
					ZonaDWR.transform(zonaTO)
				);
			
			for (DisponibilidadEntregaEmpresaZonaTurno disponibilidadEntregaEmpresaZonaTurno : disponibilidadEntregaEmpresaZonaTurnos) {
				result.add(transform(disponibilidadEntregaEmpresaZonaTurno));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Collection<DisponibilidadEntregaEmpresaZonaTurnoTO> listByEmpresaZonaTurno(
		EmpresaTO empresaTO, ZonaTO zonaTO, TurnoTO turnoTO
	) {
		Collection<DisponibilidadEntregaEmpresaZonaTurnoTO> result = new LinkedList<DisponibilidadEntregaEmpresaZonaTurnoTO>();
		
		try {
			IDisponibilidadEntregaEmpresaZonaTurnoBean iDisponibilidadEntregaEmpresaZonaTurnoBean = lookupBean();
			
			Collection<DisponibilidadEntregaEmpresaZonaTurno> disponibilidadEntregaEmpresaZonaTurnos = 
				iDisponibilidadEntregaEmpresaZonaTurnoBean.listByEmpresaZonaTurno(
					EmpresaDWR.transform(empresaTO), 
					ZonaDWR.transform(zonaTO), 
					TurnoDWR.transform(turnoTO)
				);
			
			for (DisponibilidadEntregaEmpresaZonaTurno disponibilidadEntregaEmpresaZonaTurno : disponibilidadEntregaEmpresaZonaTurnos) {
				result.add(transform(disponibilidadEntregaEmpresaZonaTurno));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public void updateDisponibilidadByZona(
		ZonaTO zonaTO, 
		Collection<DisponibilidadEntregaEmpresaZonaTurnoTO> disponibilidadEntregaEmpresaZonaTurnoTOs
	) {
			try {
				IDisponibilidadEntregaEmpresaZonaTurnoBean iDisponibilidadEntregaEmpresaZonaTurnoBean = lookupBean();
				
				Collection<DisponibilidadEntregaEmpresaZonaTurno> disponibilidadEntregaZonaTurnos =
					new LinkedList<DisponibilidadEntregaEmpresaZonaTurno>();
				
				for (DisponibilidadEntregaEmpresaZonaTurnoTO disponibilidadEntregaEmpresaZonaTurnoTO 
					: disponibilidadEntregaEmpresaZonaTurnoTOs) {
					disponibilidadEntregaZonaTurnos.add(transform(disponibilidadEntregaEmpresaZonaTurnoTO));
				}
				
				iDisponibilidadEntregaEmpresaZonaTurnoBean.updateDisponibilidadByZona(
					ZonaDWR.transform(zonaTO),
					disponibilidadEntregaZonaTurnos
				);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	
	public void updateDisponibilidadByEmpresaZona(
		EmpresaTO empresaTO, ZonaTO zonaTO, 
		Collection<DisponibilidadEntregaEmpresaZonaTurnoTO> disponibilidadEntregaEmpresaZonaTurnoTOs
	) {
		try {
			IDisponibilidadEntregaEmpresaZonaTurnoBean iDisponibilidadEntregaEmpresaZonaTurnoBean = lookupBean();
			
			Collection<DisponibilidadEntregaEmpresaZonaTurno> disponibilidadEntregaZonaTurnos =
				new LinkedList<DisponibilidadEntregaEmpresaZonaTurno>();
			
			for (DisponibilidadEntregaEmpresaZonaTurnoTO disponibilidadEntregaEmpresaZonaTurnoTO : disponibilidadEntregaEmpresaZonaTurnoTOs) {
				disponibilidadEntregaZonaTurnos.add(transform(disponibilidadEntregaEmpresaZonaTurnoTO));
			}
			
			iDisponibilidadEntregaEmpresaZonaTurnoBean.updateDisponibilidadByEmpresaZona(
				EmpresaDWR.transform(empresaTO),
				ZonaDWR.transform(zonaTO),
				disponibilidadEntregaZonaTurnos
			);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private DisponibilidadEntregaEmpresaZonaTurnoTO transform(
		DisponibilidadEntregaEmpresaZonaTurno disponibilidadEntregaEmpresaZonaTurno
	) {
		DisponibilidadEntregaEmpresaZonaTurnoTO result = new DisponibilidadEntregaEmpresaZonaTurnoTO();
		
		result.setCantidad(disponibilidadEntregaEmpresaZonaTurno.getCantidad());
		result.setDia(disponibilidadEntregaEmpresaZonaTurno.getDia());
		
		if (disponibilidadEntregaEmpresaZonaTurno.getEmpresa() != null) {
			result.setEmpresa(EmpresaDWR.transform(disponibilidadEntregaEmpresaZonaTurno.getEmpresa(), false));
		}
		
		if (disponibilidadEntregaEmpresaZonaTurno.getTurno() != null) {
			result.setTurno(TurnoDWR.transform(disponibilidadEntregaEmpresaZonaTurno.getTurno()));
		}
		
		if (disponibilidadEntregaEmpresaZonaTurno.getZona() != null) {
			result.setZona(ZonaDWR.transform(disponibilidadEntregaEmpresaZonaTurno.getZona()));
		}
		
		result.setFcre(disponibilidadEntregaEmpresaZonaTurno.getFcre());
		result.setFact(disponibilidadEntregaEmpresaZonaTurno.getFact());
		result.setId(disponibilidadEntregaEmpresaZonaTurno.getId());
		result.setTerm(disponibilidadEntregaEmpresaZonaTurno.getTerm());
		result.setUact(disponibilidadEntregaEmpresaZonaTurno.getUact());
		result.setUcre(disponibilidadEntregaEmpresaZonaTurno.getUcre());
		
		return result;
	}
	
	private DisponibilidadEntregaEmpresaZonaTurno transform(
		DisponibilidadEntregaEmpresaZonaTurnoTO disponibilidadEntregaEmpresaZonaTurnoTO
	) {
		DisponibilidadEntregaEmpresaZonaTurno result = new DisponibilidadEntregaEmpresaZonaTurno();
		
		result.setCantidad(disponibilidadEntregaEmpresaZonaTurnoTO.getCantidad());
		result.setDia(disponibilidadEntregaEmpresaZonaTurnoTO.getDia());
		
		if (disponibilidadEntregaEmpresaZonaTurnoTO.getEmpresa() != null) {
			Empresa empresa = new Empresa();
			
			empresa.setId(disponibilidadEntregaEmpresaZonaTurnoTO.getEmpresa().getId());
			
			result.setEmpresa(empresa);
		}
		
		if (disponibilidadEntregaEmpresaZonaTurnoTO.getTurno() != null) {
			Turno turno = new Turno();
			
			turno.setId(disponibilidadEntregaEmpresaZonaTurnoTO.getTurno().getId());
			
			result.setTurno(turno);
		}
		
		if (disponibilidadEntregaEmpresaZonaTurnoTO.getZona() != null) {
			Zona zona = new Zona();
			
			zona.setId(disponibilidadEntregaEmpresaZonaTurnoTO.getZona().getId());
			
			result.setZona(zona);
		}
		
		Date date = GregorianCalendar.getInstance().getTime();
		
		result.setFcre(disponibilidadEntregaEmpresaZonaTurnoTO.getFcre());
		result.setFact(date);
		result.setId(disponibilidadEntregaEmpresaZonaTurnoTO.getId());
		result.setTerm(new Long(1));
		
		HttpSession httpSession = WebContextFactory.get().getSession(false);
		Long usuarioId = (Long) httpSession.getAttribute("sesion");
		
		result.setUact(usuarioId);
		result.setUcre(disponibilidadEntregaEmpresaZonaTurnoTO.getUcre());
		
		return result;
	}
}