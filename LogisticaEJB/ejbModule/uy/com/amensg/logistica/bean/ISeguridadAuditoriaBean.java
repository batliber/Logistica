package uy.com.amensg.logistica.bean;

import java.util.Collection;
import java.util.Date;

import javax.ejb.Remote;

import uy.com.amensg.logistica.entities.SeguridadAuditoria;

@Remote
public interface ISeguridadAuditoriaBean {

	public Collection<SeguridadAuditoria> list(Date fechaDesde, Date fechaHasta);
	
	public void save(SeguridadAuditoria seguridadAuditoria);
}