package uy.com.amensg.logistica.entities;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "formato_importacion_archivo")
public class FormatoImportacionArchivo extends BaseEntity {

	private static final long serialVersionUID = 4052877918166440031L;
	
	@Column(name = "descripcion")
	private String descripcion;
	
	@Column(name = "separador_columnas")
	private String separadorColumnas;
	
	@Column(name = "nombre_tabla_destino")
	private String nombreTablaDestino;
	
	@Column(name = "lineas_encabezado")
	private Long lineasEncabezado;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "tipo_formato_importacion_archivo_id")
	private TipoFormatoImportacionArchivo tipoFormatoImportacionArchivo;

	@OneToMany(mappedBy = "formatoImportacionArchivo", fetch=FetchType.EAGER)
	private Set<FormatoImportacionArchivoColumna> formatoImportacionArchivoColumnas;
	
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getSeparadorColumnas() {
		return separadorColumnas;
	}

	public void setSeparadorColumnas(String separadorColumnas) {
		this.separadorColumnas = separadorColumnas;
	}

	public String getNombreTablaDestino() {
		return nombreTablaDestino;
	}

	public void setNombreTablaDestino(String nombreTablaDestino) {
		this.nombreTablaDestino = nombreTablaDestino;
	}

	public Long getLineasEncabezado() {
		return lineasEncabezado;
	}

	public void setLineasEncabezado(Long lineasEncabezado) {
		this.lineasEncabezado = lineasEncabezado;
	}

	public TipoFormatoImportacionArchivo getTipoFormatoImportacionArchivo() {
		return tipoFormatoImportacionArchivo;
	}

	public void setTipoFormatoImportacionArchivo(TipoFormatoImportacionArchivo tipoFormatoImportacionArchivo) {
		this.tipoFormatoImportacionArchivo = tipoFormatoImportacionArchivo;
	}

	public Set<FormatoImportacionArchivoColumna> getFormatoImportacionArchivoColumnas() {
		return formatoImportacionArchivoColumnas;
	}

	public void setFormatoImportacionArchivoColumnas(
			Set<FormatoImportacionArchivoColumna> formatoImportacionArchivoColumnas) {
		this.formatoImportacionArchivoColumnas = formatoImportacionArchivoColumnas;
	}
}