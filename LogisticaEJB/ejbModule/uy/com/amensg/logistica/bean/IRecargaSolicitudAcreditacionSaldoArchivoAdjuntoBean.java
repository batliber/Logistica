package uy.com.amensg.logistica.bean;

import java.util.Collection;

import javax.ejb.Remote;

import uy.com.amensg.logistica.entities.RecargaSolicitudAcreditacionSaldoArchivoAdjunto;

@Remote
public interface IRecargaSolicitudAcreditacionSaldoArchivoAdjuntoBean {

	public Collection<RecargaSolicitudAcreditacionSaldoArchivoAdjunto> listByRecargaSolicitudAcreditacionSaldoId(Long id);
}