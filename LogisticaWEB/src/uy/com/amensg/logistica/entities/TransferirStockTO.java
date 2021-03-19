package uy.com.amensg.logistica.entities;

import java.util.Collection;

public class TransferirStockTO {

	private Long empresaDestinoId;
	private Collection<StockMovimiento> stockMovimientos;

	public Long getEmpresaDestinoId() {
		return empresaDestinoId;
	}

	public void setEmpresaDestinoId(Long empresaDestinoId) {
		this.empresaDestinoId = empresaDestinoId;
	}

	public Collection<StockMovimiento> getStockMovimientos() {
		return stockMovimientos;
	}

	public void setStockMovimientos(Collection<StockMovimiento> stockMovimientos) {
		this.stockMovimientos = stockMovimientos;
	}
}