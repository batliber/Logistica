package uy.com.amensg.logistica.webservices.external.tablero;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "wpass", "wip", "wbcu" })
@XmlRootElement(name = "RiesgoCreditoAmigo.STATUSBCUResponse")
public class RiesgoCreditoAmigoSTATUSBCUResponse {

	@XmlElement(name = "Wpass", required = true)
	protected String wpass;

	@XmlElement(name = "Wip", required = true)
	protected String wip;

	@XmlElement(name = "Wbcu", required = true)
	protected BCU wbcu;

	public String getWpass() {
		return wpass;
	}

	public void setWpass(String value) {
		this.wpass = value;
	}

	public String getWip() {
		return wip;
	}

	public void setWip(String value) {
		this.wip = value;
	}

	public BCU getWbcu() {
		return wbcu;
	}

	public void setWbcu(BCU value) {
		this.wbcu = value;
	}
}