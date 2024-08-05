package uy.com.amensg.logistica.bean;

import java.util.Collection;

import jakarta.ejb.Remote;

import uy.com.amensg.logistica.entities.DisponibilidadEntregaEmpresaZonaTurno;
import uy.com.amensg.logistica.entities.Empresa;
import uy.com.amensg.logistica.entities.Turno;
import uy.com.amensg.logistica.entities.Zona;

@Remote
public interface IDisponibilidadEntregaEmpresaZonaTurnoBean {

	public Collection<DisponibilidadEntregaEmpresaZonaTurno> listByEmpresaZona(Empresa empresa, Zona zona);
	
	public Collection<DisponibilidadEntregaEmpresaZonaTurno> listByEmpresaZonaTurno(Empresa empresa, Zona zona, Turno turno);
	
	public void generarDisponibilidadParaEmpresa(Empresa empresa);
	
	public void updateDisponibilidadByZona(
		Zona zona,
		Collection<DisponibilidadEntregaEmpresaZonaTurno> disponibilidadEntregaEmpresaZonaTurnos
	);
	
	public void updateDisponibilidadByEmpresaZona(
		Empresa empresa,
		Zona zona,
		Collection<DisponibilidadEntregaEmpresaZonaTurno> disponibilidadEntregaEmpresaZonaTurnos
	);
}