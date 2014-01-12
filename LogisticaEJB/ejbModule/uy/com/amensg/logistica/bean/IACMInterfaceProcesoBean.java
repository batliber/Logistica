package uy.com.amensg.logistica.bean;

import java.util.Collection;

import javax.ejb.Remote;

import uy.com.amensg.logistica.entities.ACMInterfaceProceso;
import uy.com.amensg.logistica.entities.ACMInterfaceProcesoEstadistica;

@Remote
public interface IACMInterfaceProcesoBean {

	public Collection<ACMInterfaceProcesoEstadistica> listEstadisticas();
	
	public ACMInterfaceProceso save(ACMInterfaceProceso acmInterfaceProceso);
	
	public void finalizarProcesos();
}