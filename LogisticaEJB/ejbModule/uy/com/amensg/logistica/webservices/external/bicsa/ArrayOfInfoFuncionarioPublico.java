package uy.com.amensg.logistica.webservices.external.bicsa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfInfoFuncionarioPublico", propOrder = { "infoFuncionarioPublico" })
public class ArrayOfInfoFuncionarioPublico implements Serializable {

	private static final long serialVersionUID = -2398280475415101042L;
	
	@XmlElement(name = "InfoFuncionarioPublico", nillable = true)
	protected List<InfoFuncionarioPublico> infoFuncionarioPublico;

	public List<InfoFuncionarioPublico> getInfoFuncionarioPublico() {
		if (infoFuncionarioPublico == null) {
			infoFuncionarioPublico = new ArrayList<InfoFuncionarioPublico>();
		}
		return this.infoFuncionarioPublico;
	}
}