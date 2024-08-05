package uy.com.amensg.logistica.bean;

import java.util.Collection;

import jakarta.ejb.Remote;

import uy.com.amensg.logistica.entities.ProcesoImportacion;
import uy.com.amensg.logistica.entities.ProcesoImportacionLinea;

@Remote
public interface IProcesoImportacionLineaBean {

	public Collection<ProcesoImportacionLinea> listByProcesoImportacion(ProcesoImportacion procesoImportacion);
}