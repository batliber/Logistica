package uy.com.amensg.logistica.bean;

import javax.ejb.Remote;

import uy.com.amensg.logistica.entities.ProcesoExportacion;

@Remote
public interface IProcesoExportacionBean {

	public ProcesoExportacion save(ProcesoExportacion procesoExportacion);
	
	public void update(ProcesoExportacion procesoExportacion);
}