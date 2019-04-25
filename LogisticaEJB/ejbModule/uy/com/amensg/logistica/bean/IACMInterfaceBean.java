package uy.com.amensg.logistica.bean;

import javax.ejb.Remote;

import uy.com.amensg.logistica.entities.ACMInterfaceContrato;
import uy.com.amensg.logistica.entities.ACMInterfaceMid;
import uy.com.amensg.logistica.entities.ACMInterfaceNumeroContrato;
import uy.com.amensg.logistica.entities.ACMInterfacePersona;
import uy.com.amensg.logistica.entities.ACMInterfacePrepago;

@Remote
public interface IACMInterfaceBean {

	public String getSiguienteMidSinProcesar();
	
	public String getSiguienteNumeroContratoSinProcesar();
	
	public void remove(ACMInterfaceContrato acmInterfaceContrato);
	
	public void removeByNumeroContrato(ACMInterfaceContrato acmInterfaceContrato);
	
	public void remove(ACMInterfacePrepago acmInterfacePrepago);
	
	public void update(ACMInterfaceContrato acmInterfaceContrato);
	
	public void update(ACMInterfacePrepago acmInterfacePrepago);

	public void update(ACMInterfaceMid acmInterfaceMid);
	
	public void update(ACMInterfaceNumeroContrato acmInterfaceNumeroContrato);
	
	public void update(ACMInterfacePersona acmInterfacePersona, Long mid);
	
	public void actualizarDatosMidListaVacia(Long mid);
	
	public void actualizarDatosNumeroContratoListaVacia(Long numeroContrato);
}