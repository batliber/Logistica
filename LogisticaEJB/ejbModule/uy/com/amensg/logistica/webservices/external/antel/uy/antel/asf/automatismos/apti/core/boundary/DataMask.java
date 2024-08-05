	package uy.com.amensg.logistica.webservices.external.antel.uy.antel.asf.automatismos.apti.core.boundary;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
	name = "dataMask", 
	propOrder = {
		"info",
		"phoneNumber",
		"serial",
		"token",
		"typeData"
	}
)
public class DataMask implements Serializable {

	private static final long serialVersionUID = -7140076220146287113L;
	
	protected String info;
	protected String phoneNumber;
	protected String serial;
	protected String token;
	protected TypeData typeData; 

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String value) {
		this.phoneNumber = value;
	}

	public String getSerial() {
		return serial;
	}

	public void setSerial(String value) {
		this.serial = value;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String value) {
		this.token = value;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public TypeData getTypeData() {
		return typeData;
	}

	public void setTypeData(TypeData typeData) {
		this.typeData = typeData;
	}
}