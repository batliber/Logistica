package uy.com.amensg.logistica.bean;

import java.util.Collection;

import javax.ejb.Remote;

import uy.com.amensg.logistica.entities.Empresa;
import uy.com.amensg.logistica.entities.Marca;
import uy.com.amensg.logistica.entities.Modelo;
import uy.com.amensg.logistica.entities.Moneda;
import uy.com.amensg.logistica.entities.Precio;
import uy.com.amensg.logistica.entities.TipoProducto;

@Remote
public interface IPrecioBean {

	public Collection<Precio> listPreciosActuales();
	
	public Collection<Precio> listPreciosActualesByEmpresaId(Long empresaId);

	public Precio getById(Long id);
	
	public Precio getActualByEmpresaMarcaModeloMoneda(Empresa empresa, Marca marca, Modelo modelo, Moneda moneda);
	
	public Precio getActualByEmpresaTipoProductoMarcaModeloMoneda(
		Empresa empresa, 
		TipoProducto tipoProducto, 
		Marca marca, 
		Modelo modelo, 
		Moneda moneda
	);
	
	public void save(Precio precio);
	
	public void update(Precio precio);
}