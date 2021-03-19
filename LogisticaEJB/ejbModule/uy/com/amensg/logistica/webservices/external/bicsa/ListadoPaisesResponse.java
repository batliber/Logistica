package uy.com.amensg.logistica.webservices.external.bicsa;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "listadoPaisesResult" })
@XmlRootElement(name = "ListadoPaisesResponse")
public class ListadoPaisesResponse implements Serializable {

	private static final long serialVersionUID = 4470889887188104704L;
	
	@XmlElement(name = "ListadoPaisesResult")
	protected ArrayOfPais listadoPaisesResult;

	public ArrayOfPais getListadoPaisesResult() {
		return listadoPaisesResult;
	}

	public void setListadoPaisesResult(ArrayOfPais value) {
		this.listadoPaisesResult = value;
	}
}