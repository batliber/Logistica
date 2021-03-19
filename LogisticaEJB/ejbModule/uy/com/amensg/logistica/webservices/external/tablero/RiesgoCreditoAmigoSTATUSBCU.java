package uy.com.amensg.logistica.webservices.external.tablero;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "wusu", "wpass", "wip" })
@XmlRootElement(name = "RiesgoCreditoAmigo.STATUSBCU")
public class RiesgoCreditoAmigoSTATUSBCU {

	@XmlElement(name = "Wusu", required = true)
	protected String wusu;

	@XmlElement(name = "Wpass", required = true)
	protected String wpass;

	@XmlElement(name = "Wip", required = true)
	protected String wip;

	public String getWusu() {
		return wusu;
	}

	public void setWusu(String value) {
		this.wusu = value;
	}

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
}