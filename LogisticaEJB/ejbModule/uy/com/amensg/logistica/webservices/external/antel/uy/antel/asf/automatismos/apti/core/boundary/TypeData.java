package uy.com.amensg.logistica.webservices.external.antel.uy.antel.asf.automatismos.apti.core.boundary;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "typeData")
@XmlEnum
public enum TypeData {

	FTTH_SUBSTITUTION,
	FTTH_SUBSTITUTION_1_AP,
	FTTH_SUBSTITUTION_2_AP,
	FTTH_SUBSTITUTION_3_AP,
	AP_SUBSTITUTION,
	FTTH_INSTALLATION,
	FTTH_INSTALLATION_1_AP,
	FTTH_INSTALLATION_2_AP,
	FTTH_INSTALLATION_3_AP;

	public String value() {
		return name();
	}

	public static TypeData fromValue(String v) {
		return valueOf(v);
	}
}