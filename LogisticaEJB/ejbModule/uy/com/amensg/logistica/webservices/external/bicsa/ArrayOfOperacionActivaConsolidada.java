package uy.com.amensg.logistica.webservices.external.bicsa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfOperacionActiva_Consolidada", propOrder = { "operacionActivaConsolidada" })
public class ArrayOfOperacionActivaConsolidada implements Serializable {

	private static final long serialVersionUID = 204494177060956133L;
	
	@XmlElement(name = "OperacionActiva_Consolidada", nillable = true)
	protected List<OperacionActivaConsolidada> operacionActivaConsolidada;

	public List<OperacionActivaConsolidada> getOperacionActivaConsolidada() {
		if (operacionActivaConsolidada == null) {
			operacionActivaConsolidada = new ArrayList<OperacionActivaConsolidada>();
		}
		return this.operacionActivaConsolidada;
	}
}