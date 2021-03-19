package uy.com.amensg.logistica.bean;

import java.util.Collection;

import javax.ejb.Remote;

import uy.com.amensg.logistica.entities.Empresa;
import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.StockMovimiento;
import uy.com.amensg.logistica.entities.TipoProducto;

@Remote
public interface IStockMovimientoBean {

	public Collection<StockMovimiento> listStockActual();
	
	public MetadataConsultaResultado listStockActual(MetadataConsulta metadataConsulta);

	public Long countStockActual(MetadataConsulta metadataConsulta);
	
	public Collection<StockMovimiento> listStockByEmpresaId(Long id);
	
	public Collection<StockMovimiento> listStockByEmpresaTipoProducto(Empresa empresa, TipoProducto tipoProducto);
	
	public Collection<StockMovimiento> listByIMEI(String imei);
	
	public StockMovimiento getLastByIMEI(String imei);
	
	public StockMovimiento save(StockMovimiento stockMovimiento);
	
	public void save(Collection<StockMovimiento> stockMovimientos);
	
	public void transferir(Collection<StockMovimiento> stockMovimientos, Long empresaDestinoId);
}