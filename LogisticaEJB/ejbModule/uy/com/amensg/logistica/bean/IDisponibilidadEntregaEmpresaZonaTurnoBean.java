package uy.com.amensg.logistica.bean;

import java.util.Collection;

import javax.ejb.Remote;

import uy.com.amensg.logistica.entities.DisponibilidadEntregaEmpresaZonaTurno;
import uy.com.amensg.logistica.entities.Empresa;
import uy.com.amensg.logistica.entities.Turno;
import uy.com.amensg.logistica.entities.Zona;

@Remote
public interface IDisponibilidadEntregaEmpresaZonaTurnoBean {

	public Collection<DisponibilidadEntregaEmpresaZonaTurno> listByEmpresaZona(Empresa empresa, Zona zona);
	
	public Collection<DisponibilidadEntregaEmpresaZonaTurno> listByEmpresaZonaTurno(Empresa empresa, Zona zona, Turno turno);
}