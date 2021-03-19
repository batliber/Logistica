package uy.com.amensg.logistica.webservices.external.bicsa;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
	name = "OperacionCanceladaDeLaPersona", 
	propOrder = { 
		"numeroOperacion", "moneda", "institucion", "tipoOperacion", "tipoCancelacion", "idOperacion", 
		"fechaOperacion", "capitalOriginal", "montoMoraGenerada", "montoQuitaMora", "montoQuitaInteres", 
		"montoQuitaCapital", "montoInteresGenerado", "diasAtrasoMaximo", "diasAtrasoPromedio"
	}
)
public class OperacionCanceladaDeLaPersona implements Serializable {

	private static final long serialVersionUID = -4474333513004297928L;
	
	@XmlElement(name = "NumeroOperacion")
	protected String numeroOperacion;
	@XmlElement(name = "Moneda")
	protected Moneda moneda;
	@XmlElement(name = "Institucion")
	protected Institucion institucion;
	@XmlElement(name = "TipoOperacion")
	protected TipoOperacion tipoOperacion;
	@XmlElement(name = "TipoCancelacion")
	protected TipoCancelacion tipoCancelacion;
	@XmlElement(name = "IdOperacion")
	protected long idOperacion;
	@XmlElement(name = "FechaOperacion", required = true)
	@XmlSchemaType(name = "dateTime")
	protected XMLGregorianCalendar fechaOperacion;
	@XmlElement(name = "CapitalOriginal")
	protected double capitalOriginal;
	@XmlElement(name = "MontoMoraGenerada")
	protected double montoMoraGenerada;
	@XmlElement(name = "MontoQuitaMora")
	protected double montoQuitaMora;
	@XmlElement(name = "MontoQuitaInteres")
	protected double montoQuitaInteres;
	@XmlElement(name = "MontoQuitaCapital")
	protected double montoQuitaCapital;
	@XmlElement(name = "MontoInteresGenerado")
	protected double montoInteresGenerado;
	@XmlElement(name = "DiasAtrasoMaximo")
	protected int diasAtrasoMaximo;
	@XmlElement(name = "DiasAtrasoPromedio")
	protected int diasAtrasoPromedio;

	/**
	 * Gets the value of the numeroOperacion property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getNumeroOperacion() {
		return numeroOperacion;
	}

	/**
	 * Sets the value of the numeroOperacion property.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setNumeroOperacion(String value) {
		this.numeroOperacion = value;
	}

	/**
	 * Gets the value of the moneda property.
	 * 
	 * @return possible object is {@link Moneda }
	 * 
	 */
	public Moneda getMoneda() {
		return moneda;
	}

	/**
	 * Sets the value of the moneda property.
	 * 
	 * @param value allowed object is {@link Moneda }
	 * 
	 */
	public void setMoneda(Moneda value) {
		this.moneda = value;
	}

	/**
	 * Gets the value of the institucion property.
	 * 
	 * @return possible object is {@link Institucion }
	 * 
	 */
	public Institucion getInstitucion() {
		return institucion;
	}

	/**
	 * Sets the value of the institucion property.
	 * 
	 * @param value allowed object is {@link Institucion }
	 * 
	 */
	public void setInstitucion(Institucion value) {
		this.institucion = value;
	}

	/**
	 * Gets the value of the tipoOperacion property.
	 * 
	 * @return possible object is {@link TipoOperacion }
	 * 
	 */
	public TipoOperacion getTipoOperacion() {
		return tipoOperacion;
	}

	/**
	 * Sets the value of the tipoOperacion property.
	 * 
	 * @param value allowed object is {@link TipoOperacion }
	 * 
	 */
	public void setTipoOperacion(TipoOperacion value) {
		this.tipoOperacion = value;
	}

	/**
	 * Gets the value of the tipoCancelacion property.
	 * 
	 * @return possible object is {@link TipoCancelacion }
	 * 
	 */
	public TipoCancelacion getTipoCancelacion() {
		return tipoCancelacion;
	}

	/**
	 * Sets the value of the tipoCancelacion property.
	 * 
	 * @param value allowed object is {@link TipoCancelacion }
	 * 
	 */
	public void setTipoCancelacion(TipoCancelacion value) {
		this.tipoCancelacion = value;
	}

	/**
	 * Gets the value of the idOperacion property.
	 * 
	 */
	public long getIdOperacion() {
		return idOperacion;
	}

	/**
	 * Sets the value of the idOperacion property.
	 * 
	 */
	public void setIdOperacion(long value) {
		this.idOperacion = value;
	}

	/**
	 * Gets the value of the fechaOperacion property.
	 * 
	 * @return possible object is {@link XMLGregorianCalendar }
	 * 
	 */
	public XMLGregorianCalendar getFechaOperacion() {
		return fechaOperacion;
	}

	/**
	 * Sets the value of the fechaOperacion property.
	 * 
	 * @param value allowed object is {@link XMLGregorianCalendar }
	 * 
	 */
	public void setFechaOperacion(XMLGregorianCalendar value) {
		this.fechaOperacion = value;
	}

	/**
	 * Gets the value of the capitalOriginal property.
	 * 
	 */
	public double getCapitalOriginal() {
		return capitalOriginal;
	}

	/**
	 * Sets the value of the capitalOriginal property.
	 * 
	 */
	public void setCapitalOriginal(double value) {
		this.capitalOriginal = value;
	}

	/**
	 * Gets the value of the montoMoraGenerada property.
	 * 
	 */
	public double getMontoMoraGenerada() {
		return montoMoraGenerada;
	}

	/**
	 * Sets the value of the montoMoraGenerada property.
	 * 
	 */
	public void setMontoMoraGenerada(double value) {
		this.montoMoraGenerada = value;
	}

	/**
	 * Gets the value of the montoQuitaMora property.
	 * 
	 */
	public double getMontoQuitaMora() {
		return montoQuitaMora;
	}

	/**
	 * Sets the value of the montoQuitaMora property.
	 * 
	 */
	public void setMontoQuitaMora(double value) {
		this.montoQuitaMora = value;
	}

	/**
	 * Gets the value of the montoQuitaInteres property.
	 * 
	 */
	public double getMontoQuitaInteres() {
		return montoQuitaInteres;
	}

	/**
	 * Sets the value of the montoQuitaInteres property.
	 * 
	 */
	public void setMontoQuitaInteres(double value) {
		this.montoQuitaInteres = value;
	}

	/**
	 * Gets the value of the montoQuitaCapital property.
	 * 
	 */
	public double getMontoQuitaCapital() {
		return montoQuitaCapital;
	}

	/**
	 * Sets the value of the montoQuitaCapital property.
	 * 
	 */
	public void setMontoQuitaCapital(double value) {
		this.montoQuitaCapital = value;
	}

	/**
	 * Gets the value of the montoInteresGenerado property.
	 * 
	 */
	public double getMontoInteresGenerado() {
		return montoInteresGenerado;
	}

	/**
	 * Sets the value of the montoInteresGenerado property.
	 * 
	 */
	public void setMontoInteresGenerado(double value) {
		this.montoInteresGenerado = value;
	}

	/**
	 * Gets the value of the diasAtrasoMaximo property.
	 * 
	 */
	public int getDiasAtrasoMaximo() {
		return diasAtrasoMaximo;
	}

	/**
	 * Sets the value of the diasAtrasoMaximo property.
	 * 
	 */
	public void setDiasAtrasoMaximo(int value) {
		this.diasAtrasoMaximo = value;
	}

	/**
	 * Gets the value of the diasAtrasoPromedio property.
	 * 
	 */
	public int getDiasAtrasoPromedio() {
		return diasAtrasoPromedio;
	}

	/**
	 * Sets the value of the diasAtrasoPromedio property.
	 * 
	 */
	public void setDiasAtrasoPromedio(int value) {
		this.diasAtrasoPromedio = value;
	}
}