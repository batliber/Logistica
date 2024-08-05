package uy.com.amensg.logistica.web.entities;

import java.util.Collection;

public class ResultadoNotificarInstalacionBatchTO {

	private Collection<ResultadoNotificarInstalacionTO> resultadoNotificarInstalaciones;

	public Collection<ResultadoNotificarInstalacionTO> getResultadoNotificarInstalaciones() {
		return resultadoNotificarInstalaciones;
	}

	public void setResultadoNotificarInstalaciones(
			Collection<ResultadoNotificarInstalacionTO> resultadoNotificarInstalaciones) {
		this.resultadoNotificarInstalaciones = resultadoNotificarInstalaciones;
	}
}