package uy.com.amensg.logistica.web.entities;

import java.util.Collection;

public class ResultadoNotificarIZIBatchTO {

	private Collection<ResultadoNotificarIZITO> resultadoNotificarIZIs;

	public Collection<ResultadoNotificarIZITO> getResultadoNotificarIZIs() {
		return resultadoNotificarIZIs;
	}

	public void setResultadoNotificarIZIs(Collection<ResultadoNotificarIZITO> resultadoNotificarIZIs) {
		this.resultadoNotificarIZIs = resultadoNotificarIZIs;
	}
}