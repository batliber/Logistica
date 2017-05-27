package uy.com.amensg.logistica.bean;

import javax.ejb.Remote;

import uy.com.amensg.logistica.entities.DatosElegibilidadFinanciacion;
import uy.com.amensg.logistica.entities.DatosFinanciacion;
import uy.com.amensg.logistica.entities.Empresa;
import uy.com.amensg.logistica.entities.Moneda;

@Remote
public interface IFinanciacionBean {

	public DatosElegibilidadFinanciacion analizarElegibilidadFinanaciacion(Empresa empresa, String documento);
	
	public DatosFinanciacion calcularFinanciacion(Moneda moneda, Double monto, Long cuotas);
}