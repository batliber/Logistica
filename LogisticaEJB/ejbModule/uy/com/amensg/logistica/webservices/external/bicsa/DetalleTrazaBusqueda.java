package uy.com.amensg.logistica.webservices.external.bicsa;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DetalleTrazaBusqueda", propOrder = { "tipoConsultaProducto", "nombreInstitucionBusqueda",
		"usuarioBusqueda", "fechaHoraBusqueda" })
public class DetalleTrazaBusqueda implements Serializable {

	private static final long serialVersionUID = -9187495781541380354L;
	
	@XmlElement(name = "TipoConsultaProducto")
	protected TipoConsultaProducto tipoConsultaProducto;
	@XmlElement(name = "NombreInstitucionBusqueda")
	protected String nombreInstitucionBusqueda;
	@XmlElement(name = "UsuarioBusqueda")
	protected String usuarioBusqueda;
	@XmlElement(name = "FechaHoraBusqueda", required = true)
	@XmlSchemaType(name = "dateTime")
	protected XMLGregorianCalendar fechaHoraBusqueda;

	public TipoConsultaProducto getTipoConsultaProducto() {
		return tipoConsultaProducto;
	}

	public void setTipoConsultaProducto(TipoConsultaProducto value) {
		this.tipoConsultaProducto = value;
	}

	public String getNombreInstitucionBusqueda() {
		return nombreInstitucionBusqueda;
	}

	public void setNombreInstitucionBusqueda(String value) {
		this.nombreInstitucionBusqueda = value;
	}

	public String getUsuarioBusqueda() {
		return usuarioBusqueda;
	}

	public void setUsuarioBusqueda(String value) {
		this.usuarioBusqueda = value;
	}

	public XMLGregorianCalendar getFechaHoraBusqueda() {
		return fechaHoraBusqueda;
	}

	public void setFechaHoraBusqueda(XMLGregorianCalendar value) {
		this.fechaHoraBusqueda = value;
	}
}