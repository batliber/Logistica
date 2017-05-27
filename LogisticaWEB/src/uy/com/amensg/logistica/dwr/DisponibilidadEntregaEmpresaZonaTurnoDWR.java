package uy.com.amensg.logistica.dwr;

import java.util.Collection;
import java.util.LinkedList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.directwebremoting.annotations.RemoteProxy;

import uy.com.amensg.logistica.bean.DisponibilidadEntregaEmpresaZonaTurnoBean;
import uy.com.amensg.logistica.bean.IDisponibilidadEntregaEmpresaZonaTurnoBean;
import uy.com.amensg.logistica.entities.DisponibilidadEntregaEmpresaZonaTurno;
import uy.com.amensg.logistica.entities.DisponibilidadEntregaEmpresaZonaTurnoTO;
import uy.com.amensg.logistica.entities.EmpresaTO;
import uy.com.amensg.logistica.entities.TurnoTO;
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

	public Collection<DisponibilidadEntregaEmpresaZonaTurnoTO> listByEmpresaZona(EmpresaTO empresaTO, ZonaTO zonaTO) {
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
	
	public Collection<DisponibilidadEntregaEmpresaZonaTurnoTO> listByEmpresaZonaTurno(EmpresaTO empresaTO, ZonaTO zonaTO, TurnoTO turnoTO) {
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

	private DisponibilidadEntregaEmpresaZonaTurnoTO transform(DisponibilidadEntregaEmpresaZonaTurno disponibilidadEntregaEmpresaZonaTurno) {
		DisponibilidadEntregaEmpresaZonaTurnoTO disponibilidadEntregaEmpresaZonaTurnoTO = new DisponibilidadEntregaEmpresaZonaTurnoTO();
		
		disponibilidadEntregaEmpresaZonaTurnoTO.setCantidad(disponibilidadEntregaEmpresaZonaTurno.getCantidad());
		disponibilidadEntregaEmpresaZonaTurnoTO.setDia(disponibilidadEntregaEmpresaZonaTurno.getDia());
		
		disponibilidadEntregaEmpresaZonaTurnoTO.setEmpresa(EmpresaDWR.transform(disponibilidadEntregaEmpresaZonaTurno.getEmpresa()));
		
		disponibilidadEntregaEmpresaZonaTurnoTO.setTurno(TurnoDWR.transform(disponibilidadEntregaEmpresaZonaTurno.getTurno()));
		
		disponibilidadEntregaEmpresaZonaTurnoTO.setZona(ZonaDWR.transform(disponibilidadEntregaEmpresaZonaTurno.getZona()));
		
		disponibilidadEntregaEmpresaZonaTurnoTO.setFact(disponibilidadEntregaEmpresaZonaTurno.getFact());
		disponibilidadEntregaEmpresaZonaTurnoTO.setId(disponibilidadEntregaEmpresaZonaTurno.getId());
		disponibilidadEntregaEmpresaZonaTurnoTO.setTerm(disponibilidadEntregaEmpresaZonaTurno.getTerm());
		disponibilidadEntregaEmpresaZonaTurnoTO.setUact(disponibilidadEntregaEmpresaZonaTurno.getUact());
		
		return disponibilidadEntregaEmpresaZonaTurnoTO;
	}
}