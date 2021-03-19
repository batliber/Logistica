package uy.com.amensg.logistica.bean;

import java.util.Collection;
import java.util.LinkedList;

import javax.ejb.Stateless;

import uy.com.amensg.logistica.webservices.external.bicsa.Consulta;
import uy.com.amensg.logistica.webservices.external.bicsa.ConsultaSoap;
import uy.com.amensg.logistica.webservices.external.bicsa.Pais;
import uy.com.amensg.logistica.webservices.external.bicsa.Persona;
import uy.com.amensg.logistica.webservices.external.bicsa.TipoDocumento;

@Stateless
public class BICSABean implements IBICSABean {

	public Collection<Pais> listadoPaises() {
		Collection<Pais> result = new LinkedList<Pais>();
		
		try {
			Consulta service1 = new Consulta();
			ConsultaSoap port1 = service1.getConsultaSoap12();
			for (Pais pais : port1.listadoPaises().getPais()) {
				result.add(pais);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Collection<TipoDocumento> listadoTipoDocumentos() {
		Collection<TipoDocumento> result = new LinkedList<TipoDocumento>();
		
		try {
			Consulta service1 = new Consulta();
			ConsultaSoap port1 = service1.getConsultaSoap12();
			for (TipoDocumento tipoDocumento : port1.listadoTipoDocumentos().getTipoDocumento()) {
				result.add(tipoDocumento);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Persona obtenerPersona(int idTipoDoc, String idPaisDoc, String nroDoc, String codInstitucion) {
		Persona result = null;
		
		try {
			Consulta service1 = new Consulta();
			ConsultaSoap port1 = service1.getConsultaSoap12();
			
			result = port1.obtenerPersona(idTipoDoc, idPaisDoc, nroDoc, codInstitucion);
			
//			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}