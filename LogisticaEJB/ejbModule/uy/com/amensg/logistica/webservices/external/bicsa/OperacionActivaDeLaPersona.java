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
	name = "OperacionActivaDeLaPersona", 
	propOrder = { 
		"institucion", "idPersona", "tipoOperacion", "idOperacionActiva", "fechaOperacion", "numeroOperacion", 
		"capitalOriginal", "interesOriginal", "capitalAdeudadoActual", "interesPendienteDeDevengar", 
		"capitalAtrasado", "interesAtrasado", "moraPendienteDePago", "moraPaga", "plazoTotalEnPeriodos", 
		"plazoRemanenteEnPeriodos", "periodoPrestamo", "diasAtraso", "moneda", "tipoTitular", "valorCuota", 
		"diasAtrasoPromedio", "diasAtrasoMaximo", "operacionConsolidada", "relacionadoTipoDoc", 
		"relacionadoPaisDoc", "relacionadoNroDoc", "relacionadoNombreCompleto", "tipoRelacion", "vencimiento", 
		"nroCheque", "banco", "comoCodeudor", "titularTipoDoc", "titularPaisDoc", "titularNroDoc", 
		"titularNombreCompleto" 
	}
)
public class OperacionActivaDeLaPersona implements Serializable {

	private static final long serialVersionUID = 2234172903026027658L;
	
	@XmlElement(name = "Institucion")
	protected Institucion institucion;
	@XmlElement(name = "IdPersona")
	protected int idPersona;
	@XmlElement(name = "TipoOperacion")
	protected TipoOperacion tipoOperacion;
	@XmlElement(name = "IdOperacionActiva")
	protected long idOperacionActiva;
	@XmlElement(name = "FechaOperacion", required = true)
	@XmlSchemaType(name = "dateTime")
	protected XMLGregorianCalendar fechaOperacion;
	@XmlElement(name = "NumeroOperacion")
	protected String numeroOperacion;
	@XmlElement(name = "CapitalOriginal")
	protected double capitalOriginal;
	@XmlElement(name = "InteresOriginal")
	protected double interesOriginal;
	@XmlElement(name = "CapitalAdeudadoActual")
	protected double capitalAdeudadoActual;
	@XmlElement(name = "InteresPendienteDeDevengar")
	protected double interesPendienteDeDevengar;
	@XmlElement(name = "CapitalAtrasado")
	protected double capitalAtrasado;
	@XmlElement(name = "InteresAtrasado")
	protected double interesAtrasado;
	@XmlElement(name = "MoraPendienteDePago")
	protected double moraPendienteDePago;
	@XmlElement(name = "MoraPaga")
	protected double moraPaga;
	@XmlElement(name = "PlazoTotalEnPeriodos")
	protected int plazoTotalEnPeriodos;
	@XmlElement(name = "PlazoRemanenteEnPeriodos")
	protected int plazoRemanenteEnPeriodos;
	@XmlElement(name = "PeriodoPrestamo")
	protected Periodo periodoPrestamo;
	@XmlElement(name = "DiasAtraso")
	protected int diasAtraso;
	@XmlElement(name = "Moneda")
	protected Moneda moneda;
	@XmlElement(name = "TipoTitular")
	protected TipoTitular tipoTitular;
	@XmlElement(name = "ValorCuota")
	protected double valorCuota;
	@XmlElement(name = "DiasAtrasoPromedio")
	protected int diasAtrasoPromedio;
	@XmlElement(name = "DiasAtrasoMaximo")
	protected int diasAtrasoMaximo;
	@XmlElement(name = "OperacionConsolidada")
	protected ArrayOfOperacionActivaConsolidada operacionConsolidada;
	@XmlElement(name = "Relacionado_TipoDoc")
	protected TipoDocumento relacionadoTipoDoc;
	@XmlElement(name = "Relacionado_PaisDoc")
	protected Pais relacionadoPaisDoc;
	@XmlElement(name = "Relacionado_NroDoc")
	protected String relacionadoNroDoc;
	@XmlElement(name = "Relacionado_NombreCompleto")
	protected String relacionadoNombreCompleto;
	@XmlElement(name = "TipoRelacion")
	protected TipoRelacion tipoRelacion;
	@XmlElement(name = "Vencimiento", required = true)
	@XmlSchemaType(name = "dateTime")
	protected XMLGregorianCalendar vencimiento;
	@XmlElement(name = "Nro_Cheque")
	protected String nroCheque;
	@XmlElement(name = "Banco")
	protected String banco;
	@XmlElement(name = "ComoCodeudor")
	protected boolean comoCodeudor;
	@XmlElement(name = "Titular_TipoDoc")
	protected TipoDocumento titularTipoDoc;
	@XmlElement(name = "Titular_PaisDoc")
	protected Pais titularPaisDoc;
	@XmlElement(name = "Titular_NroDoc")
	protected String titularNroDoc;
	@XmlElement(name = "Titular_NombreCompleto")
	protected String titularNombreCompleto;

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
	 * Gets the value of the idPersona property.
	 * 
	 */
	public int getIdPersona() {
		return idPersona;
	}

	/**
	 * Sets the value of the idPersona property.
	 * 
	 */
	public void setIdPersona(int value) {
		this.idPersona = value;
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
	 * Gets the value of the idOperacionActiva property.
	 * 
	 */
	public long getIdOperacionActiva() {
		return idOperacionActiva;
	}

	/**
	 * Sets the value of the idOperacionActiva property.
	 * 
	 */
	public void setIdOperacionActiva(long value) {
		this.idOperacionActiva = value;
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
	 * Gets the value of the interesOriginal property.
	 * 
	 */
	public double getInteresOriginal() {
		return interesOriginal;
	}

	/**
	 * Sets the value of the interesOriginal property.
	 * 
	 */
	public void setInteresOriginal(double value) {
		this.interesOriginal = value;
	}

	/**
	 * Gets the value of the capitalAdeudadoActual property.
	 * 
	 */
	public double getCapitalAdeudadoActual() {
		return capitalAdeudadoActual;
	}

	/**
	 * Sets the value of the capitalAdeudadoActual property.
	 * 
	 */
	public void setCapitalAdeudadoActual(double value) {
		this.capitalAdeudadoActual = value;
	}

	/**
	 * Gets the value of the interesPendienteDeDevengar property.
	 * 
	 */
	public double getInteresPendienteDeDevengar() {
		return interesPendienteDeDevengar;
	}

	/**
	 * Sets the value of the interesPendienteDeDevengar property.
	 * 
	 */
	public void setInteresPendienteDeDevengar(double value) {
		this.interesPendienteDeDevengar = value;
	}

	/**
	 * Gets the value of the capitalAtrasado property.
	 * 
	 */
	public double getCapitalAtrasado() {
		return capitalAtrasado;
	}

	/**
	 * Sets the value of the capitalAtrasado property.
	 * 
	 */
	public void setCapitalAtrasado(double value) {
		this.capitalAtrasado = value;
	}

	/**
	 * Gets the value of the interesAtrasado property.
	 * 
	 */
	public double getInteresAtrasado() {
		return interesAtrasado;
	}

	/**
	 * Sets the value of the interesAtrasado property.
	 * 
	 */
	public void setInteresAtrasado(double value) {
		this.interesAtrasado = value;
	}

	/**
	 * Gets the value of the moraPendienteDePago property.
	 * 
	 */
	public double getMoraPendienteDePago() {
		return moraPendienteDePago;
	}

	/**
	 * Sets the value of the moraPendienteDePago property.
	 * 
	 */
	public void setMoraPendienteDePago(double value) {
		this.moraPendienteDePago = value;
	}

	/**
	 * Gets the value of the moraPaga property.
	 * 
	 */
	public double getMoraPaga() {
		return moraPaga;
	}

	/**
	 * Sets the value of the moraPaga property.
	 * 
	 */
	public void setMoraPaga(double value) {
		this.moraPaga = value;
	}

	/**
	 * Gets the value of the plazoTotalEnPeriodos property.
	 * 
	 */
	public int getPlazoTotalEnPeriodos() {
		return plazoTotalEnPeriodos;
	}

	/**
	 * Sets the value of the plazoTotalEnPeriodos property.
	 * 
	 */
	public void setPlazoTotalEnPeriodos(int value) {
		this.plazoTotalEnPeriodos = value;
	}

	/**
	 * Gets the value of the plazoRemanenteEnPeriodos property.
	 * 
	 */
	public int getPlazoRemanenteEnPeriodos() {
		return plazoRemanenteEnPeriodos;
	}

	/**
	 * Sets the value of the plazoRemanenteEnPeriodos property.
	 * 
	 */
	public void setPlazoRemanenteEnPeriodos(int value) {
		this.plazoRemanenteEnPeriodos = value;
	}

	/**
	 * Gets the value of the periodoPrestamo property.
	 * 
	 * @return possible object is {@link Periodo }
	 * 
	 */
	public Periodo getPeriodoPrestamo() {
		return periodoPrestamo;
	}

	/**
	 * Sets the value of the periodoPrestamo property.
	 * 
	 * @param value allowed object is {@link Periodo }
	 * 
	 */
	public void setPeriodoPrestamo(Periodo value) {
		this.periodoPrestamo = value;
	}

	/**
	 * Gets the value of the diasAtraso property.
	 * 
	 */
	public int getDiasAtraso() {
		return diasAtraso;
	}

	/**
	 * Sets the value of the diasAtraso property.
	 * 
	 */
	public void setDiasAtraso(int value) {
		this.diasAtraso = value;
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
	 * Gets the value of the tipoTitular property.
	 * 
	 * @return possible object is {@link TipoTitular }
	 * 
	 */
	public TipoTitular getTipoTitular() {
		return tipoTitular;
	}

	/**
	 * Sets the value of the tipoTitular property.
	 * 
	 * @param value allowed object is {@link TipoTitular }
	 * 
	 */
	public void setTipoTitular(TipoTitular value) {
		this.tipoTitular = value;
	}

	/**
	 * Gets the value of the valorCuota property.
	 * 
	 */
	public double getValorCuota() {
		return valorCuota;
	}

	/**
	 * Sets the value of the valorCuota property.
	 * 
	 */
	public void setValorCuota(double value) {
		this.valorCuota = value;
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
	 * Gets the value of the operacionConsolidada property.
	 * 
	 * @return possible object is {@link ArrayOfOperacionActivaConsolidada }
	 * 
	 */
	public ArrayOfOperacionActivaConsolidada getOperacionConsolidada() {
		return operacionConsolidada;
	}

	/**
	 * Sets the value of the operacionConsolidada property.
	 * 
	 * @param value allowed object is {@link ArrayOfOperacionActivaConsolidada }
	 * 
	 */
	public void setOperacionConsolidada(ArrayOfOperacionActivaConsolidada value) {
		this.operacionConsolidada = value;
	}

	/**
	 * Gets the value of the relacionadoTipoDoc property.
	 * 
	 * @return possible object is {@link TipoDocumento }
	 * 
	 */
	public TipoDocumento getRelacionadoTipoDoc() {
		return relacionadoTipoDoc;
	}

	/**
	 * Sets the value of the relacionadoTipoDoc property.
	 * 
	 * @param value allowed object is {@link TipoDocumento }
	 * 
	 */
	public void setRelacionadoTipoDoc(TipoDocumento value) {
		this.relacionadoTipoDoc = value;
	}

	/**
	 * Gets the value of the relacionadoPaisDoc property.
	 * 
	 * @return possible object is {@link Pais }
	 * 
	 */
	public Pais getRelacionadoPaisDoc() {
		return relacionadoPaisDoc;
	}

	/**
	 * Sets the value of the relacionadoPaisDoc property.
	 * 
	 * @param value allowed object is {@link Pais }
	 * 
	 */
	public void setRelacionadoPaisDoc(Pais value) {
		this.relacionadoPaisDoc = value;
	}

	/**
	 * Gets the value of the relacionadoNroDoc property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getRelacionadoNroDoc() {
		return relacionadoNroDoc;
	}

	/**
	 * Sets the value of the relacionadoNroDoc property.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setRelacionadoNroDoc(String value) {
		this.relacionadoNroDoc = value;
	}

	/**
	 * Gets the value of the relacionadoNombreCompleto property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getRelacionadoNombreCompleto() {
		return relacionadoNombreCompleto;
	}

	/**
	 * Sets the value of the relacionadoNombreCompleto property.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setRelacionadoNombreCompleto(String value) {
		this.relacionadoNombreCompleto = value;
	}

	/**
	 * Gets the value of the tipoRelacion property.
	 * 
	 * @return possible object is {@link TipoRelacion }
	 * 
	 */
	public TipoRelacion getTipoRelacion() {
		return tipoRelacion;
	}

	/**
	 * Sets the value of the tipoRelacion property.
	 * 
	 * @param value allowed object is {@link TipoRelacion }
	 * 
	 */
	public void setTipoRelacion(TipoRelacion value) {
		this.tipoRelacion = value;
	}

	/**
	 * Gets the value of the vencimiento property.
	 * 
	 * @return possible object is {@link XMLGregorianCalendar }
	 * 
	 */
	public XMLGregorianCalendar getVencimiento() {
		return vencimiento;
	}

	/**
	 * Sets the value of the vencimiento property.
	 * 
	 * @param value allowed object is {@link XMLGregorianCalendar }
	 * 
	 */
	public void setVencimiento(XMLGregorianCalendar value) {
		this.vencimiento = value;
	}

	/**
	 * Gets the value of the nroCheque property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getNroCheque() {
		return nroCheque;
	}

	/**
	 * Sets the value of the nroCheque property.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setNroCheque(String value) {
		this.nroCheque = value;
	}

	/**
	 * Gets the value of the banco property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getBanco() {
		return banco;
	}

	/**
	 * Sets the value of the banco property.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setBanco(String value) {
		this.banco = value;
	}

	/**
	 * Gets the value of the comoCodeudor property.
	 * 
	 */
	public boolean isComoCodeudor() {
		return comoCodeudor;
	}

	/**
	 * Sets the value of the comoCodeudor property.
	 * 
	 */
	public void setComoCodeudor(boolean value) {
		this.comoCodeudor = value;
	}

	/**
	 * Gets the value of the titularTipoDoc property.
	 * 
	 * @return possible object is {@link TipoDocumento }
	 * 
	 */
	public TipoDocumento getTitularTipoDoc() {
		return titularTipoDoc;
	}

	/**
	 * Sets the value of the titularTipoDoc property.
	 * 
	 * @param value allowed object is {@link TipoDocumento }
	 * 
	 */
	public void setTitularTipoDoc(TipoDocumento value) {
		this.titularTipoDoc = value;
	}

	/**
	 * Gets the value of the titularPaisDoc property.
	 * 
	 * @return possible object is {@link Pais }
	 * 
	 */
	public Pais getTitularPaisDoc() {
		return titularPaisDoc;
	}

	/**
	 * Sets the value of the titularPaisDoc property.
	 * 
	 * @param value allowed object is {@link Pais }
	 * 
	 */
	public void setTitularPaisDoc(Pais value) {
		this.titularPaisDoc = value;
	}

	/**
	 * Gets the value of the titularNroDoc property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getTitularNroDoc() {
		return titularNroDoc;
	}

	/**
	 * Sets the value of the titularNroDoc property.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setTitularNroDoc(String value) {
		this.titularNroDoc = value;
	}

	/**
	 * Gets the value of the titularNombreCompleto property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getTitularNombreCompleto() {
		return titularNombreCompleto;
	}

	/**
	 * Sets the value of the titularNombreCompleto property.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setTitularNombreCompleto(String value) {
		this.titularNombreCompleto = value;
	}
}