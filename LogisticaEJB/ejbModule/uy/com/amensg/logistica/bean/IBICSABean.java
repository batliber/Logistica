package uy.com.amensg.logistica.bean;

import java.util.Collection;

import jakarta.ejb.Remote;

import uy.com.amensg.logistica.webservices.external.bicsa.Pais;
import uy.com.amensg.logistica.webservices.external.bicsa.Persona;
import uy.com.amensg.logistica.webservices.external.bicsa.TipoDocumento;

@Remote
public interface IBICSABean {

	public Collection<Pais> listadoPaises();
	
	public Collection<TipoDocumento> listadoTipoDocumentos();
	
	public Persona obtenerPersona(int idTipoDoc, String idPaisDoc, String nroDoc, String codInstitucion);
}