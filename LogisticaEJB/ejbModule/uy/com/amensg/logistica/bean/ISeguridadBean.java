package uy.com.amensg.logistica.bean;

import javax.ejb.Remote;

import uy.com.amensg.logistica.entities.SeguridadAuditoria;

@Remote
public interface ISeguridadBean {

	public SeguridadAuditoria login(String login, String contrsena);
	
	public void logout(Long usuarioId);
}