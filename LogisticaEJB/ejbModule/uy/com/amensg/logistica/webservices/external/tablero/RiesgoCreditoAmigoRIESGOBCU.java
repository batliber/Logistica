package uy.com.amensg.logistica.webservices.external.tablero;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "wusu", "wpass", "wip", "wdocu", "wriesgo" })
@XmlRootElement(name = "RiesgoCreditoAmigo.RIESGOBCU")
public class RiesgoCreditoAmigoRIESGOBCU {

	@XmlElement(name = "Wusu", required = true)
	protected String wusu;

	@XmlElement(name = "Wpass", required = true)
	protected String wpass;

	@XmlElement(name = "Wip", required = true)
	protected String wip;

	@XmlElement(name = "Wdocu", required = true)
	protected String wdocu;

	@XmlElement(name = "Wriesgo", required = true)
	protected String wriesgo;

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

	public String getWdocu() {
		return wdocu;
	}

	public void setWdocu(String value) {
		this.wdocu = value;
	}

	public String getWriesgo() {
		return wriesgo;
	}

	public void setWriesgo(String value) {
		this.wriesgo = value;
	}
}