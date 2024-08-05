package uy.com.amensg.logistica.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "formato_importacion_archivo_columna")
public class FormatoImportacionArchivoColumna extends BaseEntity {

	private static final long serialVersionUID = -8953020506397615218L;
	
	@Column(name = "nombre")
	private String nombre;
	
	@Column(name = "orden")
	private Long orden;
	
	@Column(name = "largo")
	private Long largo;
	
	@Column(name = "nombre_columna_destino")
	private String nombreColumnaDestino;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "tipo_dato_id")
	private TipoDato tipoDato;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "formato_importacion_archivo_id")
	private FormatoImportacionArchivo formatoImportacionArchivo;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Long getOrden() {
		return orden;
	}

	public void setOrden(Long orden) {
		this.orden = orden;
	}

	public Long getLargo() {
		return largo;
	}

	public void setLargo(Long largo) {
		this.largo = largo;
	}

	public String getNombreColumnaDestino() {
		return nombreColumnaDestino;
	}

	public void setNombreColumnaDestino(String nombreColumnaDestino) {
		this.nombreColumnaDestino = nombreColumnaDestino;
	}

	public TipoDato getTipoDato() {
		return tipoDato;
	}

	public void setTipoDato(TipoDato tipoDato) {
		this.tipoDato = tipoDato;
	}

	public FormatoImportacionArchivo getFormatoImportacionArchivo() {
		return formatoImportacionArchivo;
	}

	public void setFormatoImportacionArchivo(FormatoImportacionArchivo formatoImportacionArchivo) {
		this.formatoImportacionArchivo = formatoImportacionArchivo;
	}
}