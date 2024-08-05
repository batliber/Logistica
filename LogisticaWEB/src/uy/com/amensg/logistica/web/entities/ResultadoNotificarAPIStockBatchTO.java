package uy.com.amensg.logistica.web.entities;

import java.util.Collection;

public class ResultadoNotificarAPIStockBatchTO {

	private Collection<ResultadoNotificarAPIStockTO> resultadoNotificarAPIStocks;

	public Collection<ResultadoNotificarAPIStockTO> getResultadoNotificarAPIStocks() {
		return resultadoNotificarAPIStocks;
	}

	public void setResultadoNotificarAPIStocks(Collection<ResultadoNotificarAPIStockTO> resultadoNotificarAPIStocks) {
		this.resultadoNotificarAPIStocks = resultadoNotificarAPIStocks;
	}
}