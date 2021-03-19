package uy.com.amensg.logistica.webservices.external.tablero;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BCU.BCUItem", propOrder = { "bcufech", "bcudoc", "bcuesta", "bcucali" })
public class BCUBCUItem implements Serializable {

	private static final long serialVersionUID = -7612547076574946007L;

	@XmlElement(name = "BCUFECH", required = true, nillable = true)
	@XmlSchemaType(name = "date")
	protected XMLGregorianCalendar bcufech;

	@XmlElement(name = "BCUDOC", required = true)
	protected String bcudoc;

	@XmlElement(name = "BCUESTA")
	protected byte bcuesta;

	@XmlElement(name = "BCUCALI", required = true)
	protected String bcucali;

	public XMLGregorianCalendar getBCUFECH() {
		return bcufech;
	}

	public void setBCUFECH(XMLGregorianCalendar value) {
		this.bcufech = value;
	}

	public String getBCUDOC() {
		return bcudoc;
	}

	public void setBCUDOC(String value) {
		this.bcudoc = value;
	}

	public byte getBCUESTA() {
		return bcuesta;
	}

	public void setBCUESTA(byte value) {
		this.bcuesta = value;
	}

	public String getBCUCALI() {
		return bcucali;
	}

	public void setBCUCALI(String value) {
		this.bcucali = value;
	}
}