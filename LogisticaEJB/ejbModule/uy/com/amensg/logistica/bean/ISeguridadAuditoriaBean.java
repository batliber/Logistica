package uy.com.amensg.logistica.bean;

import javax.ejb.Remote;

import uy.com.amensg.logistica.entities.SeguridadAuditoria;

@Remote
public interface ISeguridadAuditoriaBean {

	public void save(SeguridadAuditoria seguridadAuditoria);
}