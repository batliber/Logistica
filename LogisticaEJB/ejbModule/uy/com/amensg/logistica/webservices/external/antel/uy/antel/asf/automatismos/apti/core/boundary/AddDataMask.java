package uy.com.amensg.logistica.webservices.external.antel.uy.antel.asf.automatismos.apti.core.boundary;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
	name = "", 
	propOrder = {
		"token",
		"serial",
		"phoneNumber",
		"idTypeData",
		"info"
	}
)
public class AddDataMask {

	protected String token;
	protected String serial;
	protected String phoneNumber;
	protected int idTypeData;
	protected String info;

	public String getToken() {
		return token;
	}

	public void setToken(String value) {
		this.token = value;
	}

	public String getSerial() {
		return serial;
	}

	public void setSerial(String value) {
		this.serial = value;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String value) {
		this.phoneNumber = value;
	}

	public int getIdTypeData() {
		return idTypeData;
	}

	public void setIdTypeData(int idTypeData) {
		this.idTypeData = idTypeData;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}
}