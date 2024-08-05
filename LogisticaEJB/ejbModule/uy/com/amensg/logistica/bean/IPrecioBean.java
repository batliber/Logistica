package uy.com.amensg.logistica.bean;

import java.util.Collection;

import jakarta.ejb.Remote;

import uy.com.amensg.logistica.entities.Empresa;
import uy.com.amensg.logistica.entities.Marca;
import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.Modelo;
import uy.com.amensg.logistica.entities.Moneda;
import uy.com.amensg.logistica.entities.Precio;
import uy.com.amensg.logistica.entities.TipoProducto;

@Remote
public interface IPrecioBean {

	public Collection<Precio> listPreciosActuales();
	
	public Collection<Precio> listPreciosActualesByEmpresaId(Long empresaId);

	public MetadataConsultaResultado listPreciosActuales(MetadataConsulta metadataConsulta, Long usuarioId);
	
	public Long countPreciosActuales(MetadataConsulta metadataConsulta, Long usuarioId);
	
	public Precio getById(Long id);
	
	public Precio getActualByEmpresaTipoProductoMarcaModeloMonedaCuotas(
		Empresa empresa, 
		TipoProducto tipoProducto, 
		Marca marca, 
		Modelo modelo, 
		Moneda moneda,
		Long cuotas
	);
	
	public Precio save(Precio precio);
	
	public void update(Precio precio);
}