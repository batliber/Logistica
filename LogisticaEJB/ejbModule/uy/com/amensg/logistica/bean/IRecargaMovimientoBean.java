package uy.com.amensg.logistica.bean;

import java.util.Collection;

import jakarta.ejb.Remote;

import uy.com.amensg.logistica.entities.PuntoVenta;
import uy.com.amensg.logistica.entities.RecargaEmpresa;
import uy.com.amensg.logistica.entities.RecargaMovimiento;

@Remote
public interface IRecargaMovimientoBean {

	public Collection<RecargaMovimiento> listSaldoByPuntoVenta(PuntoVenta puntoVenta);
	
	public Double getSaldoByPuntoVentaRecargaEmpresa(PuntoVenta puntoVenta, RecargaEmpresa recargaEmpresa);
	
	public RecargaMovimiento save(RecargaMovimiento recargaMovimiento);
}