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
	name = "Persona", 
	propOrder = { 
		"sectorEconomico", "idPersona", "documentos", "tipoPersona", "primerNombre", "segundoNombre", 
		"primerApellido", "segundoApellido", "razonSocial", "nombreCompleto", "fechaNacimiento", "estadoCivil", 
		"genero", "direcciones", "departamente", "fechaConsulta", "consolidadoActivo", "consolidadoCancelado",
		"operacionesActivasDeLaPersona", "operacionesCanceladasDeLaPersona", "trabajosDeLaPersona", "tarjetas",
		"detallesTrazasBusquedas", "pdf", "pdfResumido", "lInfoFuncionarioPublico", "lInfoAdicional", 
		"marcasAlertas", "marcaBusqueda"
	}
)
public class Persona implements Serializable {

	private static final long serialVersionUID = 6465789673504954001L;
	
	@XmlElement(name = "SectorEconomico")
	protected String sectorEconomico;
	@XmlElement(name = "IdPersona")
	protected int idPersona;
	@XmlElement(name = "Documentos")
	protected ArrayOfDocumento documentos;
	@XmlElement(name = "TipoPersona")
	protected String tipoPersona;
	@XmlElement(name = "PrimerNombre")
	protected String primerNombre;
	@XmlElement(name = "SegundoNombre")
	protected String segundoNombre;
	@XmlElement(name = "PrimerApellido")
	protected String primerApellido;
	@XmlElement(name = "SegundoApellido")
	protected String segundoApellido;
	@XmlElement(name = "RazonSocial")
	protected String razonSocial;
	@XmlElement(name = "NombreCompleto")
	protected String nombreCompleto;
	@XmlElement(name = "FechaNacimiento", required = true)
	@XmlSchemaType(name = "dateTime")
	protected XMLGregorianCalendar fechaNacimiento;
	@XmlElement(name = "EstadoCivil")
	protected String estadoCivil;
	@XmlElement(name = "Genero")
	protected String genero;
	@XmlElement(name = "Direcciones")
	protected ArrayOfDireccion direcciones;
	@XmlElement(name = "Departamente")
	protected String departamente;
	@XmlElement(name = "FechaConsulta", required = true)
	@XmlSchemaType(name = "dateTime")
	protected XMLGregorianCalendar fechaConsulta;
	@XmlElement(name = "ConsolidadoActivo")
	protected ArrayOfDeudaActiva consolidadoActivo;
	@XmlElement(name = "ConsolidadoCancelado")
	protected DeudaCancelada consolidadoCancelado;
	@XmlElement(name = "OperacionesActivasDeLaPersona")
	protected ArrayOfOperacionActivaDeLaPersona operacionesActivasDeLaPersona;
	@XmlElement(name = "OperacionesCanceladasDeLaPersona")
	protected ArrayOfOperacionCanceladaDeLaPersona operacionesCanceladasDeLaPersona;
	@XmlElement(name = "TrabajosDeLaPersona")
	protected ArrayOfTrabajoDeLaPersona trabajosDeLaPersona;
	@XmlElement(name = "Tarjetas")
	protected ArrayOfTarjeta tarjetas;
	@XmlElement(name = "DetallesTrazasBusquedas")
	protected ArrayOfDetalleTrazaBusqueda detallesTrazasBusquedas;
	@XmlElement(name = "PDF")
	protected byte[] pdf;
	@XmlElement(name = "PDF_Resumido")
	protected byte[] pdfResumido;
	@XmlElement(name = "LInfoFuncionarioPublico")
	protected ArrayOfInfoFuncionarioPublico lInfoFuncionarioPublico;
	@XmlElement(name = "LInfoAdicional")
	protected ArrayOfInfoAdicional lInfoAdicional;
	@XmlElement(name = "MarcasAlertas")
	protected ArrayOfAlertaNaranja marcasAlertas;
	@XmlElement(name = "MarcaBusqueda")
	protected boolean marcaBusqueda;

	/**
	 * Gets the value of the sectorEconomico property.
	 * 
	 */
	public String getSectorEconomico() {
		return sectorEconomico;
	}

	/**
	 * Sets the value of the sectorEconomico property.
	 * 
	 */
	public void setSectorEconomico(String value) {
		this.sectorEconomico = value;
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
	 * Gets the value of the documentos property.
	 * 
	 * @return possible object is {@link ArrayOfDocumento }
	 * 
	 */
	public ArrayOfDocumento getDocumentos() {
		return documentos;
	}

	/**
	 * Sets the value of the documentos property.
	 * 
	 * @param value allowed object is {@link ArrayOfDocumento }
	 * 
	 */
	public void setDocumentos(ArrayOfDocumento value) {
		this.documentos = value;
	}

	/**
	 * Gets the value of the tipoPersona property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getTipoPersona() {
		return tipoPersona;
	}

	/**
	 * Sets the value of the tipoPersona property.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setTipoPersona(String value) {
		this.tipoPersona = value;
	}

	/**
	 * Gets the value of the primerNombre property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getPrimerNombre() {
		return primerNombre;
	}

	/**
	 * Sets the value of the primerNombre property.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setPrimerNombre(String value) {
		this.primerNombre = value;
	}

	/**
	 * Gets the value of the segundoNombre property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getSegundoNombre() {
		return segundoNombre;
	}

	/**
	 * Sets the value of the segundoNombre property.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setSegundoNombre(String value) {
		this.segundoNombre = value;
	}

	/**
	 * Gets the value of the primerApellido property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getPrimerApellido() {
		return primerApellido;
	}

	/**
	 * Sets the value of the primerApellido property.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setPrimerApellido(String value) {
		this.primerApellido = value;
	}

	/**
	 * Gets the value of the segundoApellido property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getSegundoApellido() {
		return segundoApellido;
	}

	/**
	 * Sets the value of the segundoApellido property.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setSegundoApellido(String value) {
		this.segundoApellido = value;
	}

	/**
	 * Gets the value of the razonSocial property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getRazonSocial() {
		return razonSocial;
	}

	/**
	 * Sets the value of the razonSocial property.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setRazonSocial(String value) {
		this.razonSocial = value;
	}

	/**
	 * Gets the value of the nombreCompleto property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getNombreCompleto() {
		return nombreCompleto;
	}

	/**
	 * Sets the value of the nombreCompleto property.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setNombreCompleto(String value) {
		this.nombreCompleto = value;
	}

	/**
	 * Gets the value of the fechaNacimiento property.
	 * 
	 * @return possible object is {@link XMLGregorianCalendar }
	 * 
	 */
	public XMLGregorianCalendar getFechaNacimiento() {
		return fechaNacimiento;
	}

	/**
	 * Sets the value of the fechaNacimiento property.
	 * 
	 * @param value allowed object is {@link XMLGregorianCalendar }
	 * 
	 */
	public void setFechaNacimiento(XMLGregorianCalendar value) {
		this.fechaNacimiento = value;
	}

	/**
	 * Gets the value of the estadoCivil property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getEstadoCivil() {
		return estadoCivil;
	}

	/**
	 * Sets the value of the estadoCivil property.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setEstadoCivil(String value) {
		this.estadoCivil = value;
	}

	/**
	 * Gets the value of the genero property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getGenero() {
		return genero;
	}

	/**
	 * Sets the value of the genero property.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setGenero(String value) {
		this.genero = value;
	}

	/**
	 * Gets the value of the direcciones property.
	 * 
	 * @return possible object is {@link ArrayOfDireccion }
	 * 
	 */
	public ArrayOfDireccion getDirecciones() {
		return direcciones;
	}

	/**
	 * Sets the value of the direcciones property.
	 * 
	 * @param value allowed object is {@link ArrayOfDireccion }
	 * 
	 */
	public void setDirecciones(ArrayOfDireccion value) {
		this.direcciones = value;
	}

	/**
	 * Gets the value of the departamente property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getDepartamente() {
		return departamente;
	}

	/**
	 * Sets the value of the departamente property.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setDepartamente(String value) {
		this.departamente = value;
	}

	/**
	 * Gets the value of the fechaConsulta property.
	 * 
	 * @return possible object is {@link XMLGregorianCalendar }
	 * 
	 */
	public XMLGregorianCalendar getFechaConsulta() {
		return fechaConsulta;
	}

	/**
	 * Sets the value of the fechaConsulta property.
	 * 
	 * @param value allowed object is {@link XMLGregorianCalendar }
	 * 
	 */
	public void setFechaConsulta(XMLGregorianCalendar value) {
		this.fechaConsulta = value;
	}

	/**
	 * Gets the value of the consolidadoActivo property.
	 * 
	 * @return possible object is {@link ArrayOfDeudaActiva }
	 * 
	 */
	public ArrayOfDeudaActiva getConsolidadoActivo() {
		return consolidadoActivo;
	}

	/**
	 * Sets the value of the consolidadoActivo property.
	 * 
	 * @param value allowed object is {@link ArrayOfDeudaActiva }
	 * 
	 */
	public void setConsolidadoActivo(ArrayOfDeudaActiva value) {
		this.consolidadoActivo = value;
	}

	/**
	 * Gets the value of the consolidadoCancelado property.
	 * 
	 * @return possible object is {@link DeudaCancelada }
	 * 
	 */
	public DeudaCancelada getConsolidadoCancelado() {
		return consolidadoCancelado;
	}

	/**
	 * Sets the value of the consolidadoCancelado property.
	 * 
	 * @param value allowed object is {@link DeudaCancelada }
	 * 
	 */
	public void setConsolidadoCancelado(DeudaCancelada value) {
		this.consolidadoCancelado = value;
	}

	/**
	 * Gets the value of the operacionesActivasDeLaPersona property.
	 * 
	 * @return possible object is {@link ArrayOfOperacionActivaDeLaPersona }
	 * 
	 */
	public ArrayOfOperacionActivaDeLaPersona getOperacionesActivasDeLaPersona() {
		return operacionesActivasDeLaPersona;
	}

	/**
	 * Sets the value of the operacionesActivasDeLaPersona property.
	 * 
	 * @param value allowed object is {@link ArrayOfOperacionActivaDeLaPersona }
	 * 
	 */
	public void setOperacionesActivasDeLaPersona(ArrayOfOperacionActivaDeLaPersona value) {
		this.operacionesActivasDeLaPersona = value;
	}

	/**
	 * Gets the value of the operacionesCanceladasDeLaPersona property.
	 * 
	 * @return possible object is {@link ArrayOfOperacionCanceladaDeLaPersona }
	 * 
	 */
	public ArrayOfOperacionCanceladaDeLaPersona getOperacionesCanceladasDeLaPersona() {
		return operacionesCanceladasDeLaPersona;
	}

	/**
	 * Sets the value of the operacionesCanceladasDeLaPersona property.
	 * 
	 * @param value allowed object is {@link ArrayOfOperacionCanceladaDeLaPersona }
	 * 
	 */
	public void setOperacionesCanceladasDeLaPersona(ArrayOfOperacionCanceladaDeLaPersona value) {
		this.operacionesCanceladasDeLaPersona = value;
	}

	/**
	 * Gets the value of the trabajosDeLaPersona property.
	 * 
	 * @return possible object is {@link ArrayOfTrabajoDeLaPersona }
	 * 
	 */
	public ArrayOfTrabajoDeLaPersona getTrabajosDeLaPersona() {
		return trabajosDeLaPersona;
	}

	/**
	 * Sets the value of the trabajosDeLaPersona property.
	 * 
	 * @param value allowed object is {@link ArrayOfTrabajoDeLaPersona }
	 * 
	 */
	public void setTrabajosDeLaPersona(ArrayOfTrabajoDeLaPersona value) {
		this.trabajosDeLaPersona = value;
	}

	/**
	 * Gets the value of the tarjetas property.
	 * 
	 * @return possible object is {@link ArrayOfTarjeta }
	 * 
	 */
	public ArrayOfTarjeta getTarjetas() {
		return tarjetas;
	}

	/**
	 * Sets the value of the tarjetas property.
	 * 
	 * @param value allowed object is {@link ArrayOfTarjeta }
	 * 
	 */
	public void setTarjetas(ArrayOfTarjeta value) {
		this.tarjetas = value;
	}

	/**
	 * Gets the value of the detallesTrazasBusquedas property.
	 * 
	 * @return possible object is {@link ArrayOfDetalleTrazaBusqueda }
	 * 
	 */
	public ArrayOfDetalleTrazaBusqueda getDetallesTrazasBusquedas() {
		return detallesTrazasBusquedas;
	}

	/**
	 * Sets the value of the detallesTrazasBusquedas property.
	 * 
	 * @param value allowed object is {@link ArrayOfDetalleTrazaBusqueda }
	 * 
	 */
	public void setDetallesTrazasBusquedas(ArrayOfDetalleTrazaBusqueda value) {
		this.detallesTrazasBusquedas = value;
	}

	/**
	 * Gets the value of the pdf property.
	 * 
	 * @return possible object is byte[]
	 */
	public byte[] getPDF() {
		return pdf;
	}

	/**
	 * Sets the value of the pdf property.
	 * 
	 * @param value allowed object is byte[]
	 */
	public void setPDF(byte[] value) {
		this.pdf = value;
	}

	/**
	 * Gets the value of the pdfResumido property.
	 * 
	 * @return possible object is byte[]
	 */
	public byte[] getPDFResumido() {
		return pdfResumido;
	}

	/**
	 * Sets the value of the pdfResumido property.
	 * 
	 * @param value allowed object is byte[]
	 */
	public void setPDFResumido(byte[] value) {
		this.pdfResumido = value;
	}

	/**
	 * Gets the value of the lInfoFuncionarioPublico property.
	 * 
	 * @return possible object is {@link ArrayOfInfoFuncionarioPublico }
	 * 
	 */
	public ArrayOfInfoFuncionarioPublico getLInfoFuncionarioPublico() {
		return lInfoFuncionarioPublico;
	}

	/**
	 * Sets the value of the lInfoFuncionarioPublico property.
	 * 
	 * @param value allowed object is {@link ArrayOfInfoFuncionarioPublico }
	 * 
	 */
	public void setLInfoFuncionarioPublico(ArrayOfInfoFuncionarioPublico value) {
		this.lInfoFuncionarioPublico = value;
	}

	/**
	 * Gets the value of the lInfoAdicional property.
	 * 
	 * @return possible object is {@link ArrayOfInfoAdicional }
	 * 
	 */
	public ArrayOfInfoAdicional getLInfoAdicional() {
		return lInfoAdicional;
	}

	/**
	 * Sets the value of the lInfoAdicional property.
	 * 
	 * @param value allowed object is {@link ArrayOfInfoAdicional }
	 * 
	 */
	public void setLInfoAdicional(ArrayOfInfoAdicional value) {
		this.lInfoAdicional = value;
	}

	/**
	 * Gets the value of the marcasAlertas property.
	 * 
	 * @return possible object is {@link ArrayOfAlertaNaranja }
	 * 
	 */
	public ArrayOfAlertaNaranja getMarcasAlertas() {
		return marcasAlertas;
	}

	/**
	 * Sets the value of the marcasAlertas property.
	 * 
	 * @param value allowed object is {@link ArrayOfAlertaNaranja }
	 * 
	 */
	public void setMarcasAlertas(ArrayOfAlertaNaranja value) {
		this.marcasAlertas = value;
	}

	/**
	 * Gets the value of the marcaBusqueda property.
	 * 
	 */
	public boolean isMarcaBusqueda() {
		return marcaBusqueda;
	}

	/**
	 * Sets the value of the marcaBusqueda property.
	 * 
	 */
	public void setMarcaBusqueda(boolean value) {
		this.marcaBusqueda = value;
	}
}