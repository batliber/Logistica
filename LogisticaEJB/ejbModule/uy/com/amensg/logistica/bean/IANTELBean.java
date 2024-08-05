package uy.com.amensg.logistica.bean;

import java.util.Collection;

import jakarta.ejb.Remote;

import uy.com.amensg.logistica.webservices.external.antel.uy.antel.asf.automatismos.apti.core.boundary.DataMask;
import uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.uy.com.antel.pi.notification.nbr_v1.NotifyResponse;

@Remote
public interface IANTELBean {

	public Long notificarInstalacion(String idTicket, String telefono, String serieONT, int idTypeData, String info);
	
	public DataMask obtenerDatosANTEL(String idTicket);
	
	public NotifyResponse publicarNoticiaIZI(
		String idTicketIZI, String recordDate, String completionCode, String completionCodeDescription, String completionSubCode, 
		String completionSubCodeDescription, String completionLabel, String nombreReceptor, String documentoReceptor, String idRiverGreen
	);
	
	public String notificarAPIStock(Collection<String> series);
	
	public String obtenerAccessToken();
	
	public String publicarNota(String idTicket, String texto);
}