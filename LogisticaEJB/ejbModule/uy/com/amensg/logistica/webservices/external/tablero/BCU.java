package uy.com.amensg.logistica.webservices.external.tablero;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BCU", propOrder = { "bcubcuItem" })
public class BCU {

	@XmlElement(name = "BCU.BCUItem")
	protected List<BCUBCUItem> bcubcuItem;

	public List<BCUBCUItem> getBCUBCUItem() {
		if (bcubcuItem == null) {
			bcubcuItem = new ArrayList<BCUBCUItem>();
		}
		return this.bcubcuItem;
	}
}