package uy.com.amensg.logistica.webservices.external.tablero;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "wpass", "wip", "wdocu", "wriesgo", "resp" })
@XmlRootElement(name = "RiesgoCreditoAmigo.RIESGOBCUResponse")
public class RiesgoCreditoAmigoRIESGOBCUResponse {

	@XmlElement(name = "Wpass", required = true)
	protected String wpass;

	@XmlElement(name = "Wip", required = true)
	protected String wip;

	@XmlElement(name = "Wdocu", required = true)
	protected String wdocu;

	@XmlElement(name = "Wriesgo", required = true)
	protected String wriesgo;

	@XmlElement(name = "Resp")
	protected byte resp;

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

	public byte getResp() {
		return resp;
	}

	public void setResp(byte value) {
		this.resp = value;
	}
}