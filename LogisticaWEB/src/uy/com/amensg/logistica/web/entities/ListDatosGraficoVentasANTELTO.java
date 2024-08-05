package uy.com.amensg.logistica.web.entities;

import java.util.Collection;

public class ListDatosGraficoVentasANTELTO {

	private String desde;
	private String hasta;
	private Collection<Long> empresas;

	public String getDesde() {
		return desde;
	}

	public void setDesde(String desde) {
		this.desde = desde;
	}

	public String getHasta() {
		return hasta;
	}

	public void setHasta(String hasta) {
		this.hasta = hasta;
	}

	public Collection<Long> getEmpresas() {
		return empresas;
	}

	public void setEmpresas(Collection<Long> empresas) {
		this.empresas = empresas;
	}
}